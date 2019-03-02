package org.kaidzen.webscrap.common.util;

import org.junit.Test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

public class MapperUtilTest {

    @Test
    public void getDateOrMax() throws ParseException {
        java.util.Date parsedDate = new SimpleDateFormat("yyyy-dd-mm").parse("0000-00-00");
        Date date = new Date(parsedDate.getTime());
        LocalDate result = MapperUtil.getDateOrMax(date);

        assertNotNull(result.toString());
    }
}