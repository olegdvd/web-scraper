package org.kaidzen.webscrap.scraper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.kaidzen.webscrap.map.ElementsToIssuedLicenseMapper;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class IssuedLicenseScraper {

    private static final Logger LOG = LoggerFactory.getLogger(IssuedLicenseScraper.class);
    private static final String ISSUED_LICENSE_URL = "http://dabi.gov.ua/license/list.php?&&page=%s";


    public IssuedLicenseScraper() {

    }

    public List<IssuedLicense> scrapLicenses() {
        return null;
    }

    public List issuedLicences(String pagedUrl) {
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
            long rowsScraped = elements.stream().count();
            return elements.stream()
                    .map(elementsMapper::mapFromElements)
                    .collect(toList());
        }
        LOG.warn("Failed to scrap from URL: {}", pagedUrl);
        return Collections.emptyList();
    }
}
