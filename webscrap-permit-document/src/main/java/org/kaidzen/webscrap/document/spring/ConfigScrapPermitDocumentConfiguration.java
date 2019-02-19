package org.kaidzen.webscrap.document.spring;

import org.jsoup.nodes.Element;
import org.kaidzen.webscrap.document.mapper.ElementsToPermitDocumentMapper;
import org.kaidzen.webscrap.document.mapper.ElementsToStringMapper;
import org.kaidzen.webscrap.document.mapper.PermitDocumentToCsvMapper;
import org.kaidzen.webscrap.document.model.FormFilterData;
import org.kaidzen.webscrap.document.model.PermitDocument;
import org.kaidzen.webscrap.document.scraper.ElementsPermitDocument;
import org.kaidzen.webscrap.document.scraper.PermitDocumentScraper;
import org.kaidzen.webscrap.document.scraper.PermitsScrapper;
import org.kaidzen.webscrap.document.service.PermitDocumentService;
import org.kaidzen.webscrap.document.util.StandardTimeClock;
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
public class ConfigScrapPermitDocumentConfiguration {

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
    public BiFunction<Element, FormFilterData, Optional<PermitDocument>> permitDocumentElementsMapper() {
        return new ElementsToPermitDocumentMapper(clock());
    }

    @Bean
    public Function<Collection<PermitDocument>, List<String>> objToCsvMapper() {
        return new PermitDocumentToCsvMapper();
    }

    @Bean
    public ElementsToStringMapper toStringMapper() {
        return new ElementsToStringMapper();
    }

    @Bean
    public ElementsPermitDocument elementsPermitDocument() {
        return new ElementsPermitDocument(toStringMapper());
    }

    @Bean
    public PermitDocumentService permitDocumentService() {
        return new PermitDocumentService();
    }

    @Bean
    public PermitDocumentScraper permitDocumentScraper() {
        return new PermitDocumentScraper(permitsUrl, elementsPermitDocument(), permitDocumentService());
    }

    @Bean
    public PermitsScrapper permitsScrapper() {
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
