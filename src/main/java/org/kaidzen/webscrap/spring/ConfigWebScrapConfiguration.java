package org.kaidzen.webscrap.spring;

import org.jsoup.nodes.Element;
import org.kaidzen.webscrap.dao.IssuedLicenseDao;
import org.kaidzen.webscrap.mapper.ElementsToIssuedLicenseMapper;
import org.kaidzen.webscrap.mapper.ElementsToPermitDocumentMapper;
import org.kaidzen.webscrap.mapper.ElementsToStringMapper;
import org.kaidzen.webscrap.mapper.PermitDocumentToCsvMapper;
import org.kaidzen.webscrap.model.FormFilterData;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.kaidzen.webscrap.model.PermitDocument;
import org.kaidzen.webscrap.scraper.*;
import org.kaidzen.webscrap.service.IssuedLicenseService;
import org.kaidzen.webscrap.service.PermitDocumentService;
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
import java.util.function.BiFunction;
import java.util.function.Function;

@Configuration
@PropertySource("scraper.properties")
public class ConfigWebScrapConfiguration {

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
    public BiFunction<Element, FormFilterData, Optional<PermitDocument>> permitDocumentElementsMapper() {
        return new ElementsToPermitDocumentMapper(clock());
    }

    @Bean
    public Function<Collection<PermitDocument>, List<String>> objToCsvMapper() {
        return new PermitDocumentToCsvMapper();
    }

    @Bean
    public ElementsIssueLicenses elementsIssueLicenses() {
        return new ElementsIssueLicenses(issuedLicenseElementsMapper());
    }

    @Bean
    public ElementsToStringMapper toStringMapper(){
        return new ElementsToStringMapper();
    }

    @Bean
    public ElementsPermitDocument elementsPermitDocument(){
        return new ElementsPermitDocument(toStringMapper());
    }

    @Bean
    public IssuedLicenseService issuedLicenseService(){
        return new IssuedLicenseService(issuedLiceseDao());
    }

    @Bean
    public PermitDocumentService permitDocumentService(){
        return new PermitDocumentService();
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
    public PermitDocumentScraper permitDocumentScraper(){
        return new PermitDocumentScraper(permitsUrl, elementsPermitDocument(), permitDocumentService());
    }

    @Bean
    public PermitsScrapper permitsScrapper(){
        return new PermitsScrapper(permitsUrl, permitDocumentScraper());
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
