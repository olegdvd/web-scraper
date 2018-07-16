package org.kaidzen.webscrap.model;

import org.junit.Before;
import org.junit.Test;
import org.kaidzen.webscrap.util.StandardTimeClock;

import java.sql.Timestamp;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class IssuedLicenseTest {

    private static final Integer ID = 2013052487;
    private static final String TYPE = "Type info";
    private static final String LICENSE = "2013021917";
    private static final Long EDRPO = 40013303L;
    private static final String THE_LICENSEE = "Roga LTD";
    private static final String ADDRESS = "City, str. office #5";
    private static final LocalDate ISSUE_DATE = LocalDate.parse("2018-06-15");
    private static final LocalDate VALID_DATE = LocalDate.parse("2018-10-05");
    private static final String EXP_MD5 = "3188EE97883CA93006E6D5D525A0FBC2";

    private StandardTimeClock clock;
    private Timestamp expectedTimestamp;

    private IssuedLicense issuedLicense;

    @Before
    public void setUp() {
        clock = new StandardTimeClock();
        issuedLicense = new IssuedLicense.Builder(clock)
                .id(ID)
                .type(TYPE)
                .license(LICENSE)
                .edrpo(EDRPO)
                .theLicensee(THE_LICENSEE)
                .address(ADDRESS)
                .issueDate(ISSUE_DATE)
                .validToDate(VALID_DATE)
                .timestamp()
                .build();
        expectedTimestamp = clock.createTimestamp();

    }

    @Test
    public void testBuilder(){
        String md5 = issuedLicense.getMd5();
        Timestamp timestamp = issuedLicense.getTimestamp();

        assertEquals(EXP_MD5, md5);
        assertEquals(expectedTimestamp, timestamp);
        System.out.println(issuedLicense);
        System.out.println(expectedTimestamp);
        System.out.println(issuedLicense.getMd5());
        System.out.println(issuedLicense.toCsv());
    }

}