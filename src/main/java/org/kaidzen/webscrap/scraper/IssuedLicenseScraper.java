package org.kaidzen.webscrap.scraper;

import org.kaidzen.webscrap.mapper.ObjectToCsvMapper;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.kaidzen.webscrap.service.IssuedLicenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
public class IssuedLicenseScraper {

    private static final Logger LOG = LoggerFactory.getLogger(IssuedLicenseScraper.class);
    private final Function<Collection<IssuedLicense>, List<String>> objToCsvMapper;
    private final ElementsIssueLicenses elementsIssueLicenses;
    private final String baseUrl;
    @Autowired
    private IssuedLicenseService licenseService;

    public IssuedLicenseScraper(String baseUrl, ElementsIssueLicenses elementsIssueLicenses) {
        this.baseUrl = baseUrl;
        this.elementsIssueLicenses = elementsIssueLicenses;
        objToCsvMapper = new ObjectToCsvMapper();
    }

    public void scrap() {
        scrapedStream()
                .forEach(issuedLicenses -> licenseService.saveAll(issuedLicenses));
    }

    public void scrapToCsv(String fileName) {
        scrapedStream()
                .map(objToCsvMapper::apply)
                .forEach(collectionList -> licenseService.saveToFile(fileName, collectionList));
    }

    private Stream<List<IssuedLicense>> scrapedStream() {
//        int lastPageNumber = elementsIssueLicenses.pagesToScrap(baseUrl);
        int lastPageNumber = 1861;
        LOG.info("There is [{}] pages to scrap", lastPageNumber);
        return IntStream.rangeClosed(1, lastPageNumber).boxed()
                .map(integer -> elementsIssueLicenses.documentForPage(baseUrl, integer))
                .map(document -> elementsIssueLicenses.takeElements("tr", document));
    }
}
