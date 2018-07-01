package org.kaidzen.webscrap.scraper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IssuedLicenseScraper {

    private static final Logger LOG = LoggerFactory.getLogger(IssuedLicenseScraper.class);
    private final ElementsIssueLicenses elementsIssueLicenses;
    private final String baseUrl;

    public IssuedLicenseScraper(String baseUrl, ElementsIssueLicenses elementsIssueLicenses) {
        this.baseUrl = baseUrl;
        this.elementsIssueLicenses = elementsIssueLicenses;
    }

    public void scrap() {
        int lastPageNumber = elementsIssueLicenses.pagesToScrap(baseUrl);
        LOG.info("There is [{}] pages to scrap", lastPageNumber);
    }

}
