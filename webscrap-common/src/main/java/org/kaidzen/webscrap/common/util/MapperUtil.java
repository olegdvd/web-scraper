package org.kaidzen.webscrap.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MapperUtil {

    private static final Logger LOG = LoggerFactory.getLogger(MapperUtil.class);

    private MapperUtil() {
    }

    public static LocalDate getDateOrMax(Date date) {
        return MapperUtil.getDateOrMax(date.toLocalDate().toString());
    }

    public static LocalDate getDateOrMax(String date) {
        try {
            return LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            LOG.warn("Date mapped to \"2038-01-01\"", e);
            return LocalDate.parse("2038-01-01");
        }
    }
}
