package org.kaidzen.webscrap.document.scraper;

import org.apache.commons.lang3.StringUtils;
import org.kaidzen.webscrap.document.model.FormFilterConstants;
import org.kaidzen.webscrap.document.model.FormFilterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.kaidzen.webscrap.document.model.FormFilterConstants.getRegions;
import static org.kaidzen.webscrap.document.model.FormFilterConstants.getReversedYears;

public class PermitsScrapper {

    private static final Logger LOG = LoggerFactory.getLogger(PermitsScrapper.class);

    private final PermitDocumentScraper permitDocumentScraper;


    public PermitsScrapper(PermitDocumentScraper permitDocumentScraper) {
        this.permitDocumentScraper = permitDocumentScraper;
    }

    public void scrapPermits(String year, String region) {
        FormFilterConstants.getMonths().stream()
                .sorted()
                .peek(month -> grabAllByMonths(month, year, region))
                .count();
    }

    private void grabAllByMonths(String inMonth, String year, String region) {
        List<String> scrapYears = yearsFromOrCurrent(year);
        scrapYears.stream()
                .forEach(inYear -> grabAllByRegions(inMonth, inYear, region));
    }

    private void grabAllByRegions(String inMonth, String inYear, String inRegion) {
        if (StringUtils.isEmpty(inRegion)){
            getRegions().stream()
                    .forEach(region -> permitDocumentScraper.scrap(
                            new FormFilterData.Builder()
                                    .month(inMonth)
                                    .year(inYear)
                                    .region(region)
                                    .build()
                    ));
        } else {
            permitDocumentScraper.scrap(
                    new FormFilterData.Builder()
                            .month(inMonth)
                            .year(inYear)
                            .region(inRegion)
                            .build()
            );
        }
    }

    private List<String> yearsFromOrCurrent(String year) {
        String filteredYear = Optional.ofNullable(year)
                .orElse(String.valueOf(LocalDateTime.now().getYear()));
        return Optional.of(getReversedYears().stream()
                .filter(presentYear -> presentYear.compareTo(filteredYear) <= 0)
                .collect(Collectors.toList()))
                .orElse(getReversedYears());
    }
}