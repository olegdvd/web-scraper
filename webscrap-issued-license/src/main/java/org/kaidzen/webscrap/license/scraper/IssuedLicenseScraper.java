package org.kaidzen.webscrap.license.scraper;

import org.kaidzen.webscrap.license.mapper.IssuedLicenseToCsvMapper;
import org.kaidzen.webscrap.license.model.IssuedLicense;
import org.kaidzen.webscrap.license.service.IssuedLicenseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final IssuedLicenseService licenseService;

    public IssuedLicenseScraper(String baseUrl,
                                ElementsIssueLicenses elementsIssueLicenses,
                                IssuedLicenseService issuedLicenseService) {
        this.baseUrl = baseUrl;
        this.elementsIssueLicenses = elementsIssueLicenses;
        this.licenseService = issuedLicenseService;
        objToCsvMapper = new IssuedLicenseToCsvMapper();
    }

    public void scrap() {
        scrapedStream()
                .forEach(licenseService::saveAll);
    }

    public void scrapToCsv(String fileName) {
        scrapedStream()
                .map(objToCsvMapper::apply)
                .forEach(collectionList -> licenseService.saveToFile(fileName, collectionList));
    }

    private Stream<List<IssuedLicense>> scrapedStream() {
//        int lastPageNumber = elementsIssueLicenses.pagesToScrap(baseUrl);
        int lastPageNumber = 1879;
        LOG.info("There is [{}] pages to scrap", lastPageNumber);
        return IntStream.rangeClosed(1, 1879).boxed()
                .map(integer -> elementsIssueLicenses.getPagetoDocument(baseUrl, integer, null))
                .map(document -> elementsIssueLicenses.takeElements("tr", document));
    }
}
