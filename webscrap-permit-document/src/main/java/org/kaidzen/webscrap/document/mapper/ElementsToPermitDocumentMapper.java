package org.kaidzen.webscrap.document.mapper;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kaidzen.webscrap.common.util.MapperUtil;
import org.kaidzen.webscrap.common.util.StandardTimeClock;
import org.kaidzen.webscrap.document.model.FormFilterData;
import org.kaidzen.webscrap.document.model.PermitDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class ElementsToPermitDocumentMapper implements BiFunction<Element, FormFilterData, Optional<PermitDocument>> {

    private static final Logger LOG = LoggerFactory.getLogger(ElementsToPermitDocumentMapper.class);
    private static final int FIELDS_TO_MAP = 11; //As for PermitDocument.class
    private final StandardTimeClock clock;
    private List<String> stringList;

    public ElementsToPermitDocumentMapper(StandardTimeClock clock) {
        this.clock = clock;
    }

    @Override
    public Optional<PermitDocument> apply(Element element, FormFilterData filterData) {
        Elements innerElements = element.children();
        stringList = innerElements.stream()
                .map(Element::text)
                .map(string -> string.replace("&nbsp;", ""))
                .collect(Collectors.toList());
        if (checkList(stringList)) {
            return buildFromElements(filterData);
        }
        /* TODO Add Dao with mapped columns; */
        return Optional.empty();

    }

    private Optional<PermitDocument> buildFromElements(FormFilterData filterData) {
        try {
            return Optional.ofNullable(new PermitDocument.Builder(clock)
                    .documentId(getText(0))
                    .region(getText(1))
                    .documentType(getText(2))
                    .subject(getText(3))
                    .category(Short.valueOf(getText(4)))
                    .customer(getText(5))
                    .techSupervision(getText(6))
                    .designer(getText(7))
                    .supervision(getText(8))
                    .contractor(getText(9))
                    .landInfo(getText(10))
                    .month(Short.valueOf(filterData.getMonth()))
                    .year(Short.valueOf(filterData.getYear()))
                    .timestamp()
                    .build());
        } catch (NumberFormatException e) {
            LOG.info("Skipped for id:[{}], with [{}]", getText(0), filterData);
        }
        return Optional.empty();
    }

    private LocalDate getDateOrNow(int i) {
        return MapperUtil.getDateOrMax(getText(i));
    }

    private boolean checkList(List<String> stringList) {
        String first = stringList.get(0);
        if (stringList.size() != FIELDS_TO_MAP) return false;
        if (StringUtils.isNumericSpace(first)) return false;
        if (StringUtils.isEmpty(first)) return false;
        return !first.contains("№");
    }

    private String getText(int index) {
        String str = stringList.get(index);
        return str.substring(0, str.length() - 1);
    }
}
