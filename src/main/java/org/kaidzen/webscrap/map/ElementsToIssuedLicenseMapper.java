package org.kaidzen.webscrap.map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ElementsToIssuedLicenseMapper implements Function<Element, Optional<IssuedLicense>> {

    private static final Logger LOG = LoggerFactory.getLogger(ElementsToIssuedLicenseMapper.class);
    private static final int FIELDS_TO_MAP = 8; //As for IssuedLicense.class
    private Elements innerElements;
    private List<String> stringList;

    @Override
    public Optional<IssuedLicense> apply(Element element) {
        innerElements = element.children();
        stringList = innerElements.stream()
                .map(Element::text)
                .map(string -> string.replace("&nbsp;", ""))
                .collect(Collectors.toList());
        if (checkList(stringList)) {
            Optional<IssuedLicense> license = Optional.ofNullable(new IssuedLicense.Builder()
                    .id(getInteger(0))
                    .type(getText(1))
                    .license(getText(2))
                    .edrpo(Integer.valueOf(getText(3).replaceAll("\\s", "")))
                    .theLicensee(getText(4))
                    .address(getText(5))
                    .issueDate(LocalDate.parse(getText(6)))
                    .validToDate(LocalDate.parse(getText(7)))
                    .timestamp()
                    .build());
            return license;
        }
        //TODO Add Dao with mapped columns;
        LOG.warn("Skipped  scrapped element: {}", element);
        return Optional.empty();

    }

    private Integer getInteger(int index) {
        return NumberUtils.createInteger(getText(index).replaceAll("\\s", ""));
    }

    private boolean checkList(List<String> stringList) {
        String first = stringList.get(0);
        if (stringList.size() != FIELDS_TO_MAP) return false;
        if (StringUtils.isNumericSpace(first)) return false;
        if (StringUtils.isEmpty(first)) return false;
        if ("№ ".equals(first)) return false;
        return true;
    }

    private String cleanText(int i) {
        return getText(i).replace("&nbsp;", "");
    }

    private boolean elementsFilter() {
        if (innerElements.size() != FIELDS_TO_MAP) return false;
        if (StringUtils.isNumericSpace(innerElements.first().text())) return false;
        if (StringUtils.isNotEmpty(innerElements.first().text())) return false;
        return true;
    }

    private String getText(int index) {
        return stringList.get(index);
    }
}
