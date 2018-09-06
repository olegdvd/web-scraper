package org.kaidzen.webscrap.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.kaidzen.webscrap.model.FormFilterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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

    public void scrapPermits() {
        for (String region : regions) {
            for (String year : years) {
                for (String month : months) {
                    filterData = new FormFilterData.Builder()
                            .month(month)
                            .year(year)
                            .region(region)
                            .build();
                    Document document = permitDocumentScraper.filterDocuments(baseUrl, filterData);
                    List<String> list = takeElements("tr", document);
                    int lastPage = getLastPage(document);
                    System.out.println(list);
                }
            }
        }
    }

    private int getLastPage(Document document) {
        String hrefString = document.select("#pages, a").last().attr("href");
        int length = hrefString.length();
        return Integer.parseInt(Character.toString(hrefString.charAt(length-1)));
    }

    private List<String> takeElements(String selection, Document document) {
        return document.select(selection).stream()
                .map(getTextNodesAsString())
                .collect(toList());
    }

    private Function<Element, String> getTextNodesAsString() {
        return element -> element.textNodes().stream()
                .map(TextNode::text)
                .collect(Collectors.joining());
    }
}
