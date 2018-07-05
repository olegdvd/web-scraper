package org.kaidzen.webscrap.spring;

import org.jsoup.nodes.Element;
import org.kaidzen.webscrap.map.ElementsToIssuedLicenseMapper;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.kaidzen.webscrap.scraper.ElementsIssueLicenses;
import org.kaidzen.webscrap.scraper.IssuedLicenseScraper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.function.Function;

@Configuration
@PropertySource("scraper.properties")
public class ConfigWebScrapConfiguration {

    @Value("${web.issuedLicenseUrl}")
    private String issuedLicenseUrl;

    @Value("${datasource.url}")
    private String url;

    @Value("${datasource.username}")
    private String username;

    @Value("${datasource.password}")
    private String password;

    @Value("${datasource.driver}")
    private String driver;

    @Bean
    public Function<Element, IssuedLicense> issuedLicenseElementsMapper() {
        return new ElementsToIssuedLicenseMapper();
    }

    @Bean
    public ElementsIssueLicenses elementsIssueLicenses() {
        return new ElementsIssueLicenses(issuedLicenseElementsMapper());
    }

    @Bean
    public IssuedLicenseScraper issuedLicenseScraper() {
        return new IssuedLicenseScraper(issuedLicenseUrl, elementsIssueLicenses());
    }

    @Bean
    public DataSource dataSourceLocal(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }
}
