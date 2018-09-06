package org.kaidzen.webscrap.scraper;

import org.kaidzen.webscrap.model.FormFilterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class PermitsScrapper {

    private static final Logger LOG = LoggerFactory.getLogger(PermitsScrapper.class);
    private final String baseUrl;
    private final PermitDocumentScraper permitDocumentScraper;
    private final List<String> years = Arrays.asList(
            "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018"
    );
    private final List<String> months = Arrays.asList(
      "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
    );
    private final List<String> regions = Arrays.asList(
            "99", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27"
    );
    private FormFilterData filterData;


    public PermitsScrapper(String baseUrl, PermitDocumentScraper permitDocumentScraper) {
        this.baseUrl = baseUrl;
        this.permitDocumentScraper = permitDocumentScraper;
    }

    public void scrapPermits() {}

}
