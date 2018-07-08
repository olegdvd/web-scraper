package org.kaidzen.webscrap.scraper;

import org.kaidzen.webscrap.service.IssuedLicenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class IssuedLicenseScraper {

    private static final Logger LOG = LoggerFactory.getLogger(IssuedLicenseScraper.class);
    private final ElementsIssueLicenses elementsIssueLicenses;
    private final String baseUrl;
    @Autowired
    private IssuedLicenseService licenseService;

    public IssuedLicenseScraper(String baseUrl, ElementsIssueLicenses elementsIssueLicenses) {
        this.baseUrl = baseUrl;
        this.elementsIssueLicenses = elementsIssueLicenses;
    }

    public void scrap() {
//        int lastPageNumber = elementsIssueLicenses.pagesToScrap(baseUrl);
        int lastPageNumber = 2;
        LOG.info("There is [{}] pages to scrap", lastPageNumber);
        IntStream.rangeClosed(1, lastPageNumber).boxed()
                .map(integer -> elementsIssueLicenses.documentForPage(baseUrl, integer))
                .map(document -> elementsIssueLicenses.scrapElements("tr", document))
                .forEach(issuedLicenses -> licenseService.saveAll(issuedLicenses));

    }

}
