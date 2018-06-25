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
        IssuedLicense issuedLicense = new IssuedLicense();
        //TODO Add Dao with mapped columns;
        issuedLicense.setId(Integer.valueOf(getText(0)));
        issuedLicense.setType(getText(1));
        issuedLicense.setLicense(getText(2));
        issuedLicense.setEdrpo(Integer.valueOf(getText(3)));
        issuedLicense.setTheLicensee(getText(4));
        issuedLicense.setAdress(getText(5));
        issuedLicense.setIssueDate(LocalDate.parse(getText(6)));
        issuedLicense.setValidToDate(LocalDate.parse(getText(7)));

        return issuedLicense;
    }

    private String getText(int index) {
        return innerElements.get(index).text();
    }
}
