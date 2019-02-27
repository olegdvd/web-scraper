package org.kaidzen.webscrap.document;

import org.kaidzen.webscrap.document.scraper.PermitDocumentScraper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EntityScan("org.kaidzen.webscrap.document.model")
public class ScrapPermitDocument implements CommandLineRunner {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ScrapPermitDocument.class, args);
        context.getBean("permitDocumentScraper", PermitDocumentScraper.class)
                .scrap("");
        context.stop();

    }

    @Override
    public void run(String... strings) {
    }
}
