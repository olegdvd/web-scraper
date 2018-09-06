package org.kaidzen.webscrap.scraper;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.kaidzen.webscrap.mapper.PermitDocumentToCsvMapper;
import org.kaidzen.webscrap.model.FormFilterData;
import org.kaidzen.webscrap.model.PermitDocument;
import org.kaidzen.webscrap.service.PermitDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.kaidzen.webscrap.model.FormFilterConstants.*;

public class PermitDocumentScraper {

    private static final Logger LOG = LoggerFactory.getLogger(ElementScraper.class);
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build();
    private final Function<Collection<PermitDocument>, List<String>> objToCsvMapper;
    private final ElementsPermitDocument elementsPermitDocument;
    private final PermitDocumentService permitDocumentService;
    private final String permitDocumentsUrl;

    public PermitDocumentScraper(String permitDocumentsUrl,
                                 ElementsPermitDocument elementsPermitDocument,
                                 PermitDocumentService permitDocumentService) {
        this.permitDocumentsUrl = permitDocumentsUrl;
        this.elementsPermitDocument = elementsPermitDocument;
        this.permitDocumentService = permitDocumentService;
        this.objToCsvMapper = new PermitDocumentToCsvMapper();
    }

    public void scrapToCsv(String fileName) {
        for (String region : getRegions()) {
            for (String year : getYears()) {
                for (String month : getMonths()) {
                    FormFilterData filterData = new FormFilterData.Builder()
                            .month(month)
                            .year(year)
                            .region(region)
                            .build();
                    Document document = filterPageToDocument(permitDocumentsUrl, filterData);
                    Stream<List<PermitDocument>> baseListStream = Stream.of(elementsPermitDocument
                            .takeFilteredElements("tr", document, filterData));
                    int lastPageNumber = getLastPage(document);
                    if (lastPageNumber != 0){
                        String permitsRequestUrl = permitDocumentsUrl.concat("&&page=%s");
                        Stream<List<PermitDocument>> restListStream = restOfFilteredStream(permitsRequestUrl,
                                lastPageNumber, filterData);
                        Stream.concat(baseListStream, restListStream)
                                .map(objToCsvMapper::apply)
                                .forEach(collectionList -> permitDocumentService.saveToFile(fileName, collectionList));
                    }else {
                        baseListStream
                                .map(objToCsvMapper::apply)
                                .forEach(collectionList -> permitDocumentService.saveToFile(fileName, collectionList));
                    }

                }
            }
        }
    }

    private Stream<List<PermitDocument>> restOfFilteredStream(String baseUrl, int lastPageNumber, FormFilterData filterData) {
        LOG.info("There is [{}] pages to scrap with {}", lastPageNumber, filterData);
        return IntStream.rangeClosed(2, lastPageNumber).boxed()
                .map(integer -> elementsPermitDocument.getPagetoDocument(baseUrl, integer))
                .map(document -> elementsPermitDocument.takeFilteredElements("tr", document, filterData));
    }

    private Document filterPageToDocument(String url, FormFilterData filterData) {
        Optional<HttpUrl> targetUrl = Optional.ofNullable(HttpUrl.parse(url));
        if (targetUrl.isPresent()) {
            RequestBody formBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("filter[date]", filterData.getYear())
                    .addFormDataPart("filter[date2]", filterData.getMonth())
                    .addFormDataPart("filter[regob]", filterData.getRegion())
                    .build();
            Request request = new Request.Builder()
                    .url(targetUrl.get())
                    .post(formBody)
                    .build();
            String html = null;
            try {
                Response response = CLIENT.newCall(request).execute();
                LOG.info("Response from [{}] with: {}", url, response.code());
                html = Objects.requireNonNull(response.body()).string();
            } catch (IOException e) {
                LOG.error("Source server is unreachable or changed/wrong URL: {} with parameters: {}", url, filterData);
            }
            return Jsoup.parse(html);
        }
        LOG.warn("Failed to scrap from URL: {}", url);
        throw new RuntimeException("Failed to parse url: " + url);
    }

    private int getLastPage(Document document) {
        String hrefString = document.select("#pages, a").last().attr("href");
        int length = hrefString.length();
        if (length <= 0) return 0;
        return Integer.parseInt(Character.toString(hrefString.charAt(length - 1)));
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
