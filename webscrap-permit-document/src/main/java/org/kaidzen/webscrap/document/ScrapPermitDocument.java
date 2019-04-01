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
        System.out.println("Run with 2 optional parameters: YEAR and/or REGION, to specify exactly one region or smaller list of years");
        ConfigurableApplicationContext context = SpringApplication.run(ScrapPermitDocument.class, args);
        PermitsScrapper bean = context.getBean("permitsScrapper", PermitsScrapper.class);
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
