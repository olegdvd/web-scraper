package org.kaidzen.webscrap.document;

import org.kaidzen.webscrap.document.scraper.PermitsScrapper;
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
        PermitsScrapper bean = context.getBean("permitsScrapper", PermitsScrapper.class);
        System.out.println("Scrapper for permit documents");
        System.out.println("Run without parameters: scarp all the regions from current year to 2011");
        System.out.println("Run with 1 optional parameter: [YEAR], to scrap all the regions withing YEAR");
        System.out.println("Run with 2 optional parameters: [YEAR REGION], to specify exactly one region withing YEAR");
        if (args.length == 0) {
            bean.scrapPermits(null, null);
            context.stop();
        } else {
            int length = args.length;
            if(length == 1){
                bean.scrapPermits(args[0], null);
            } else {
                bean.scrapPermits(args[0], args[1]);
            }
        }
    }

    @Override
    public void run(String... strings) {
    }
}
