package org.kaidzen.webscrap.document.scraper;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.kaidzen.webscrap.document.mapper.PermitDocumentToCsvMapper;
import org.kaidzen.webscrap.document.model.FormFilterData;
import org.kaidzen.webscrap.document.model.PermitDocument;
import org.kaidzen.webscrap.document.service.PermitDocumentService;
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

public class PermitDocumentScraper {

    private static final Logger LOG = LoggerFactory.getLogger(ElementScraper.class);
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .writeTimeout(2, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build();
    private final Function<Collection<PermitDocument>, List<String>> objToCsvMapper;
    private final ElementsPermitDocument elementsPermitDocument;
    private final PermitDocumentService permitDocumentService;
    private final String permitDocumentsUrl;
    private String cookie;

    public PermitDocumentScraper(String permitDocumentsUrl,
                                 ElementsPermitDocument elementsPermitDocument,
                                 PermitDocumentService permitDocumentService) {
        this.permitDocumentsUrl = permitDocumentsUrl;
        this.elementsPermitDocument = elementsPermitDocument;
        this.permitDocumentService = permitDocumentService;
        this.objToCsvMapper = new PermitDocumentToCsvMapper();
    }

    public void scrap(FormFilterData filterData) {
        Optional<Document> document = filterPageToDocument(permitDocumentsUrl, filterData);
        if (document.isPresent()) {
            Stream<List<PermitDocument>> baseListStream = Stream.of(elementsPermitDocument
                    .takeElements("tr", document.get(), filterData));
            int lastPageNumber = getLastPage(document.get());
//                    String presentPageFilter = getPresentPageFilter(document);
            if (lastPageNumber != 0) {
                String permitsRequestUrl = permitDocumentsUrl.concat("&&page=%s");
                Stream<List<PermitDocument>> restListStream = restOfFilteredStream(permitsRequestUrl,
                        lastPageNumber, filterData, cookie);
                Stream.concat(baseListStream, restListStream)
                        .forEach(permitDocumentService::saveAll);
//                                .forEach(collectionList ->
//                                        permitDocumentService.saveToFile(
//                                        fileName, collectionList,
//                                        presentPageFilter.concat("| " + String.format(permitsRequestUrl, lastPageNumber)))
                ;
            } else {
                baseListStream
                        .forEach(permitDocumentService::saveAll);
//                                .forEach(collectionList -> permitDocumentService.saveToFile(fileName, collectionList, presentPageFilter.concat("| " + permitDocumentsUrl)));
            }
        }

    }

    private String getPresentPageFilter(Document document) {
        return document.body().childNodes().get(6).toString().replace("&nbsp;", "");
    }

    private Stream<List<PermitDocument>> restOfFilteredStream(String baseUrl, int lastPageNumber, FormFilterData filterData, String cookie) {
        LOG.info("There is [{}] pages to scrap with {}", lastPageNumber, filterData);
        return IntStream.rangeClosed(2, lastPageNumber).boxed()
                .map(integer -> elementsPermitDocument.getPagetoDocument(baseUrl, integer, cookie))
                .map(document -> elementsPermitDocument.takeElements("tr", document, filterData));
    }

    private Optional<Document> filterPageToDocument(String url, FormFilterData filterData) {
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
                cookie = response.headers().get("Set-Cookie");
                setCookie(cookie);
                html = Objects.requireNonNull(response.body()).string();
                return Optional.ofNullable(Jsoup.parse(html));
            } catch (IOException e) {
                LOG.error("Source server is unreachable or changed/wrong URL: {} with parameters: {}", url, filterData);
            }
            return Optional.empty();
        }
        LOG.warn("Failed to scrap from URL: {}", url);
        return Optional.empty();
    }

    private int getLastPage(Document document) {
        Optional<String> maybeHrefString = Optional.ofNullable(document.select("#pages, a").last().attr("href"));
        int length = maybeHrefString
                .map(String::length)
                .orElse(0);
        if (length <= 0) return 0;
        return Integer.parseInt(maybeHrefString
                .map(str -> str.substring(str.lastIndexOf("page=") + 5))
                .orElse("0"));
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

    public String getCookie() {
        return cookie;
    }

    private void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
