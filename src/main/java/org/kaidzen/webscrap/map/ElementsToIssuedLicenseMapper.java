package org.kaidzen.webscrap.map;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.function.Function;

public class ElementsToIssuedLicenseMapper implements Function<Element, IssuedLicense> {

    private static final Logger LOG = LoggerFactory.getLogger(ElementsToIssuedLicenseMapper.class);
    private static final int FIELDS_TO_MAP = 8; //As for IssuedLicense.class
    private Elements innerElements;

    @Override
    public IssuedLicense apply(Element element) {
        innerElements = element.children();
        if (innerElements.size() != FIELDS_TO_MAP) {
            LOG.error("Please, check scrapped element for children amounts: {}", element);
            throw new IllegalArgumentException("Can't map with this: " + element);
        }
        //TODO Add Dao with mapped columns;

        return new IssuedLicense.Builder()
                .id(Integer.valueOf(getText(0)))
                .type(getText(1))
                .license(getText(2))
                .edrpo(Integer.valueOf(getText(3)))
                .theLicensee(getText(4))
                .address(getText(5))
                .issueDate(LocalDate.parse(getText(6)))
                .validToDate(LocalDate.parse(getText(7)))
                .timestamp()
                .build();
    }

    private String getText(int index) {
        return innerElements.get(index).text();
    }
}
