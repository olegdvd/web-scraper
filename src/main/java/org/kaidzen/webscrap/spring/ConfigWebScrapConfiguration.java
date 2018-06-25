package org.kaidzen.webscrap.spring;

import org.kaidzen.webscrap.map.ElementsMapper;
import org.kaidzen.webscrap.map.ElementsToIssuedLicenseMapper;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.kaidzen.webscrap.scraper.ElementsIssueLicenses;
import org.kaidzen.webscrap.scraper.IssuedLicenseScraper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("scraper.properties")
public class ConfigWebScrapConfiguration {

    @Value("web.issuedLicenseUrl")
    private String issuedLicenseUrl;

    @Bean
    public ElementsMapper<IssuedLicense> issuedLicenseElementsMapper(){
        return new ElementsToIssuedLicenseMapper();
    }

    @Bean
    public ElementsIssueLicenses elementsIssueLicenses(){
        return new ElementsIssueLicenses(issuedLicenseElementsMapper());
    }

    @Bean
    public IssuedLicenseScraper issuedLicenseScraper() {
        return new IssuedLicenseScraper();
    }
}
