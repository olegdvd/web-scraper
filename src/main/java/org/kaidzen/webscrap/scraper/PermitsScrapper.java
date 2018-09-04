package org.kaidzen.webscrap.scraper;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PermitsScrapper {

    private static final Logger LOG = LoggerFactory.getLogger(PermitsScrapper.class);
    private final String baseUrl;
    private final PermitDocumentScraper permitDocumentScraper;
    private String form = "{\n" +
            "  \"filter[class]\": {\n" +
            "    \"filter[date]\": \"2018\",\n" +
            "    \"filter[date2]\": \"04\",\n" +
            "    \"filter[regob]\": \"12\"\n" +
            "  }\n" +
            "}";


    public PermitsScrapper(String baseUrl, PermitDocumentScraper permitDocumentScraper) {
        this.baseUrl = baseUrl;
        this.permitDocumentScraper = permitDocumentScraper;
    }

    public void scrapPermits() {
        Document document = permitDocumentScraper.getDocument(baseUrl, form);
        List<String> list = takeElements("tr", document);
        int lastPage = getLastPage(document);
        System.out.println(list);
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
