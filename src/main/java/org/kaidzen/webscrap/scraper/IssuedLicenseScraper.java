package org.kaidzen.webscrap.scraper;

import org.jsoup.nodes.Element;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;
import java.util.stream.IntStream;

public class IssuedLicenseScraper {

    private static final Logger LOG = LoggerFactory.getLogger(IssuedLicenseScraper.class);
    private final ElementsIssueLicenses elementsIssueLicenses;
    private final Function<Element, IssuedLicense> licenseMapper;
    private final String baseUrl;

    public IssuedLicenseScraper(String baseUrl, ElementsIssueLicenses elementsIssueLicenses,
                                Function<Element, IssuedLicense> licenseMapper) {
        this.baseUrl = baseUrl;
        this.elementsIssueLicenses = elementsIssueLicenses;
        this.licenseMapper = licenseMapper;
    }

    public void scrap() {
        int lastPageNumber = elementsIssueLicenses.pagesToScrap(baseUrl);
        LOG.info("There is [{}] pages to scrap", lastPageNumber);
        IntStream.rangeClosed(1, 2).boxed()
                .map(integer -> elementsIssueLicenses.documentForPage(baseUrl, integer))
                .map(licenseMapper::apply)
                .forEach(issuedLicense -> LOG.info("We got it: \\n {}", issuedLicense));
    }

}
