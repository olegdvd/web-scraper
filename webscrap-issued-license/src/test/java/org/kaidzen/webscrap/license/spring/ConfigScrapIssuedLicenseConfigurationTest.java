package org.kaidzen.webscrap.license.spring;

import org.junit.Test;
import org.kaidzen.webscrap.license.scraper.IssuedLicenseScraper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

public class ConfigScrapIssuedLicenseConfigurationTest {

    @Test
    public void testConfiguration() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ConfigScrapIssuedLicenseConfiguration.class);
        IssuedLicenseScraper testBean = context.getBean("issuedLicenseScraper", IssuedLicenseScraper.class);

        assertNotNull(testBean);
    }

}