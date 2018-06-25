package org.kaidzen.webscrap.scraper;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.kaidzen.webscrap.map.ElementsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public abstract class ElementScraper<T> {

    private static final Logger LOG = LoggerFactory.getLogger(ElementScraper.class);
    private static final OkHttpClient CLIENT = new OkHttpClient();

    ElementScraper() {
    }

    public abstract int pagesToScrap(String baseUrl);

    public abstract List<T> scrapElements (String pagedUrl, int pageIndex);

    protected List requestForElements(String pagedUrl, int pageIndex) {
        Optional<HttpUrl> url = Optional.ofNullable(HttpUrl.parse(pagedUrl));
        if (url.isPresent()){
            Request request = new Request.Builder().url(url.get()).get().build();
            String html = null;
            try {
                html = Objects.requireNonNull(CLIENT.newCall(request).execute().body()).string();
            } catch (IOException e) {
                LOG.error("Source server is unreachable or changed/wrong URL: {}", pagedUrl);
            }

            Elements elements = Jsoup.parse(html).select("tr");
            return elements;
        }
        LOG.warn("Failed to scrap from URL: {}", pagedUrl);
        return Collections.emptyList();
    }
}
