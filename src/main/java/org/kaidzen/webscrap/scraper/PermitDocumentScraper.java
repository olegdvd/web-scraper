package org.kaidzen.webscrap.scraper;

import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.kaidzen.webscrap.model.FormFilterData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class PermitDocumentScraper {

    private static final Logger LOG = LoggerFactory.getLogger(ElementScraper.class);
    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build();

    public PermitDocumentScraper() {
    }

    protected Document filterDocuments(String url, FormFilterData filterData) {
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

}
