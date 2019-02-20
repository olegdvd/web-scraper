package org.kaidzen.webscrap.license.spring;

import org.jsoup.nodes.Element;
import org.kaidzen.webscrap.license.dao.IssuedLicenseDao;
import org.kaidzen.webscrap.license.mapper.ElementsToIssuedLicenseMapper;
import org.kaidzen.webscrap.license.mapper.ElementsToStringMapper;
import org.kaidzen.webscrap.license.model.IssuedLicense;
import org.kaidzen.webscrap.license.scraper.ElementsIssueLicenses;
import org.kaidzen.webscrap.license.scraper.IssuedLicenseScraper;
import org.kaidzen.webscrap.license.service.IssuedLicenseService;
import org.kaidzen.webscrap.license.util.StandardTimeClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.function.Function;

@Configuration
@PropertySource("scraper.properties")
public class ConfigScrapIssuedLicenseConfiguration {

    @Value("${web.issuedLicenseUrl}")
    private String issuedLicenseUrl;

    @Value("${web.permitsUrl}")
    private String permitsUrl;

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
    public ElementsIssueLicenses elementsIssueLicenses() {
        return new ElementsIssueLicenses(issuedLicenseElementsMapper());
    }

    @Bean
    public ElementsToStringMapper toStringMapper() {
        return new ElementsToStringMapper();
    }

    @Bean
    public IssuedLicenseService issuedLicenseService() {
        return new IssuedLicenseService(issuedLiceseDao());
    }


    @Bean
    public IssuedLicenseDao issuedLiceseDao() {
        return new IssuedLicenseDao(jdbcTemplate(), clock());
    }

    @Bean
    public IssuedLicenseScraper issuedLicenseScraper() {
        return new IssuedLicenseScraper(issuedLicenseUrl, elementsIssueLicenses(), issuedLicenseService());
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
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSourceLocal());
    }
}
