package org.kaidzen.webscrap.scraper;

import okhttp3.HttpUrl;
import okhttp3.Request;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
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
    private final ElementsIssueLicenses elementsIssueLicenses;
    private final String baseUrl;

    public IssuedLicenseScraper(String baseUrl, ElementsIssueLicenses elementsIssueLicenses) {
        this.baseUrl = baseUrl;
        this.elementsIssueLicenses = elementsIssueLicenses;
    }

    public List<IssuedLicense> scrap() {
        elementsIssueLicenses.pagesToScrap(baseUrl);
        return null;
    }

}
