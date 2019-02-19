package org.kaidzen.webscrap.document.scraper;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public abstract class ElementScraper<T> {

    private static final Logger LOG = LoggerFactory.getLogger(ElementScraper.class);
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build();

    ElementScraper() {
    }

    public abstract int pagesToScrap(String baseUrl);

    public abstract List<T> takeElements(String pagedUrl, Document document);

    protected Document getPagetoDocument(String pagedUrl, int pageNumber, String cookie) {
        String fullUrl = getFullUrl(pagedUrl, pageNumber);
        Optional<HttpUrl> url = Optional.ofNullable(HttpUrl.parse(fullUrl));
        if (url.isPresent()) {
            boolean isCookie = Objects.isNull(cookie);
            Request request = new Request.Builder().url(url.get())
                    .addHeader("Cookie", isCookie ? "" : cookie)
                    .get().build();
            String html = null;
            try {
                Response response = CLIENT.newCall(request).execute();
                LOG.info("Response from [{}] with: {}", fullUrl, response.code());
                html = Objects.requireNonNull(response.body()).string();
            } catch (IOException e) {
                LOG.error("Source server is unreachable or changed/wrong URL: {}", fullUrl);
            }
            if(isEmpty(html)) return new Document("");
            return Jsoup.parse(html);
        }
        LOG.warn("Failed to scrap from URL: {}", fullUrl);
        throw new RuntimeException("Failed to parse url: " + fullUrl);
    }

    private String getFullUrl(String pagedUrl, int pageIndex) {
        return String.format(pagedUrl, pageIndex);
    }
}
