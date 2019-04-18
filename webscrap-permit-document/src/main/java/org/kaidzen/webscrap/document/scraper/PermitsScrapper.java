package org.kaidzen.webscrap.document.scraper;

import org.kaidzen.webscrap.document.model.FormFilterConstants;
import org.kaidzen.webscrap.document.model.FormFilterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.kaidzen.webscrap.document.model.FormFilterConstants.getRegions;
import static org.kaidzen.webscrap.document.model.FormFilterConstants.getReversedYears;

public class PermitsScrapper {

    private static final Logger LOG = LoggerFactory.getLogger(PermitsScrapper.class);

    private final PermitDocumentScraper permitDocumentScraper;


    public PermitsScrapper(PermitDocumentScraper permitDocumentScraper) {
        this.permitDocumentScraper = permitDocumentScraper;
    }

    public void scrapPermits(String year, String region) {
        List<String> scrapYears = yearsListOrThis(year);
        scrapYears.stream()
                .forEach(inYear -> grabAllByRegions(inYear, region));


    }

    private void grabAllByMonths(String inYear, String inRegion) {
        long result = FormFilterConstants.getMonths().stream()
                .sorted()
                .peek(month -> permitDocumentScraper.scrap(
                        new FormFilterData.Builder()
                                .month(month)
                                .year(inYear)
                                .region(inRegion)
                                .build()
                ))
                .count();
        LOG.info("There were {} months scrapped", result);
    }

    private void grabAllByRegions(String inYear, String inRegion) {
        String filteredRegion = Optional.ofNullable(inRegion)
                .filter(this::checkIfNumber)
                .orElse("");

        List<String> regions = Optional.of(getRegions().stream()
                .filter(presentRegion -> presentRegion.compareTo(filteredRegion) == 0)
                .collect(toList()))
                .orElse(getRegions());

        if (!regions.isEmpty()) {
            regions.forEach(region -> grabAllByMonths(inYear, region));
        } else {
            getRegions().stream()
                    .forEach(region -> grabAllByMonths(inYear, region));
        }
        System.exit(0);
    }

    private boolean checkIfNumber(String region) {
        for (char ch : region.toCharArray()) {
            if (!Character.isDigit(ch)) return false;
        }
        return true;
    }

    private List<String> yearsListOrThis(String year) {
        String filteredYear = Optional.ofNullable(year)
                .orElse("");
        List<String> list = getReversedYears().stream()
                .filter(presentYear -> presentYear.compareTo(filteredYear) == 0)
                .collect(toList());
        return list.isEmpty() ? getReversedYears() : list;
    }
}