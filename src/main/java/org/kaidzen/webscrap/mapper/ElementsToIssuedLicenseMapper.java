package org.kaidzen.webscrap.mapper;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.kaidzen.webscrap.util.StandardTimeClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ElementsToIssuedLicenseMapper implements Function<Element, Optional<IssuedLicense>> {

    private static final Logger LOG = LoggerFactory.getLogger(ElementsToIssuedLicenseMapper.class);
    private static final int FIELDS_TO_MAP = 8; //As for IssuedLicense.class
    private final StandardTimeClock clock;
    private List<String> stringList;

    public ElementsToIssuedLicenseMapper(StandardTimeClock clock) {
        this.clock = clock;
    }

    @Override
    public Optional<IssuedLicense> apply(Element element) {
        Elements innerElements = element.children();
        stringList = innerElements.stream()
                .map(Element::text)
                .map(string -> string.replace("&nbsp;", ""))
                .collect(Collectors.toList());
        if (checkList(stringList)) {
            return Optional.ofNullable(new IssuedLicense.Builder(clock)
                    .id(Integer.valueOf(getText(0)))
                    .type(getText(1))
                    .license(getText(2))
                    .edrpo(getText(3))
                    .theLicensee(getText(4))
                    .address(getText(5))
                    .issueDate(getDateOrNow(6))
                    .validToDate(getDateOrNow(7))
                    .timestamp()
                    .build());
        }
        /* TODO Add Dao with mapped columns; */
        LOG.warn("Skipped  scrapped element");
        return Optional.empty();

    }

    private Long getEdrpoOrMock(int i) {
        try {
            return Long.valueOf(getText(i).trim());
        } catch (NumberFormatException e) {
            LOG.warn("EDRPO mapped to zero with id:{}", getText(0), e);
            return Long.valueOf("11111111");
        }
    }

    private LocalDate getDateOrNow(int i) {
        try {
            return LocalDate.parse(getText(i));
        } catch (DateTimeParseException e) {
            LOG.warn("Date mapped to now() with id:{}", getText(0), e);
            return LocalDate.now();
        }
    }

    private boolean checkList(List<String> stringList) {
        String first = stringList.get(0);
        if (stringList.size() != FIELDS_TO_MAP) return false;
        if (StringUtils.isNumericSpace(first)) return false;
        if (StringUtils.isEmpty(first)) return false;
        return !"№ ".equals(first);
    }

    private String getText(int index) {
        String str = stringList.get(index);
        return str.substring(0, str.length() - 1);
    }
}
