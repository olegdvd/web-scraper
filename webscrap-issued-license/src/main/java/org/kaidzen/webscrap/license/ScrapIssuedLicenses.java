package org.kaidzen.webscrap.license;

import org.kaidzen.webscrap.license.scraper.IssuedLicenseScraper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EntityScan("org.kaidzen.webscrap.license.model")
public class ScrapIssuedLicenses implements CommandLineRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ScrapIssuedLicenses.class, args);
        context.getBean("issuedLicenseScraper", IssuedLicenseScraper.class)
                .scrap();
//                .scrapToCsv("issued-licenses.csv");
        context.stop();

    }

    @Override
    public void run(String... strings) {
    }
}
