package org.kaidzen.webscrap.document.spring;

import org.junit.Test;
import org.kaidzen.webscrap.document.scraper.PermitDocumentScraper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

public class ConfigScrapPermitDocumentConfigurationTest {

    @Test
    public void testConfiguration() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ConfigScrapPermitDocumentConfiguration.class);
        PermitDocumentScraper testBean = context.getBean("permitDocumentScraper", PermitDocumentScraper.class);

        assertNotNull(testBean);
    }

}