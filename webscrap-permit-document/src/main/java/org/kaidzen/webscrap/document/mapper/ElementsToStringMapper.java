package org.kaidzen.webscrap.document.mapper;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kaidzen.webscrap.document.util.MapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class ElementsToStringMapper implements Function<Element, Optional<String>> {

    private static final Logger LOG = LoggerFactory.getLogger(ElementsToStringMapper.class);
    private static final int FIELDS_TO_MAP = 11; //As for PermitDocument.class
    private List<String> stringList;

    public ElementsToStringMapper() {

    }

    @Override
    public Optional<String> apply(Element element) {
        getElementsList(element);
        if (checkList(stringList)) {
            return Optional.ofNullable(
                    stringList.stream()
                            .collect(joining("| "))
            );
        }
        /* TODO Add Dao with mapped columns; */
        LOG.info("Skipped  scrapped element");
        return Optional.empty();
    }

    private void getElementsList(Element element) {
        Elements innerElements = element.children();
        stringList = innerElements.stream()
                .map(Element::text)
                .map(string -> string.replace("&nbsp;", ""))
                .collect(Collectors.toList());
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
