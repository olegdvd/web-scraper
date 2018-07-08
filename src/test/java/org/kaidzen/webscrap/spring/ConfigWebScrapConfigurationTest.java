package org.kaidzen.webscrap.spring;

import org.junit.Test;
import org.kaidzen.webscrap.scraper.IssuedLicenseScraper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;


public class ConfigWebScrapConfigurationTest {

    @Test
    public void testConfiguration() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ConfigWebScrapConfiguration.class);
        IssuedLicenseScraper testBean = context.getBean("issuedLicenseScraper", IssuedLicenseScraper.class);

        assertNotNull(testBean);
    }

}