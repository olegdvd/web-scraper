package org.kaidzen.webscrap;

import org.kaidzen.webscrap.repository.IssuedLicenseRepository;
import org.kaidzen.webscrap.scraper.IssuedLicenseScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication
@EnableJpaRepositories("org.kaidzen.webscrap.repository")
@EntityScan("org.kaidzen.webscrap.model")
public class WebscrapApplication implements CommandLineRunner {

    @Autowired
    IssuedLicenseRepository repository;
    @Autowired
    private DataSource dataSourceLocal;
    @Resource(name = "issuedLicenseScraper")
    private IssuedLicenseScraper issuedLicenseScraper;

    public static void main(String[] args) {
        SpringApplication.run(WebscrapApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        issuedLicenseScraper.scrapToCsv("megoDB.csv");
    }
}
