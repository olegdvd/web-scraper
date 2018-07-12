package org.kaidzen.webscrap.spring;

import org.jsoup.nodes.Element;
import org.kaidzen.webscrap.mapper.ElementsToIssuedLicenseMapper;
import org.kaidzen.webscrap.mapper.ObjectToCsvMapper;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.kaidzen.webscrap.scraper.ElementsIssueLicenses;
import org.kaidzen.webscrap.scraper.IssuedLicenseScraper;
import org.kaidzen.webscrap.util.StandardTimeClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
    public StandardTimeClock clock() {
        return new StandardTimeClock();
    }

    @Bean
    public Function<Element, Optional<IssuedLicense>> issuedLicenseElementsMapper() {
        return new ElementsToIssuedLicenseMapper(clock());
    }

    @Bean
    public Function<Collection<IssuedLicense>, List<String>> objToCsvMapper(){
        return new ObjectToCsvMapper();
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
    public DataSource dataSourceLocal() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSourceLocal());
    }
}
