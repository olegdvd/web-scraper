package org.kaidzen.webscrap.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class MapperUtil {

    private static final Logger LOG = LoggerFactory.getLogger(MapperUtil.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private MapperUtil() {
    }

    public static List<String> toPrettyString(List<IssuedLicense> themes) {
        return themes.stream()
                .map(GSON::toJson)
                .collect(toList());
    }

    public static Long getEdrpoOrMock(Long edrpo) {
        try {
            return Long.valueOf(edrpo);
        } catch (NumberFormatException e) {
            LOG.warn("EDRPO mapped to 11111111", e.getMessage());
            return Long.valueOf("11111111");
        }
    }

    public static LocalDate getDateOrNow(Date date) {
        try {
            return LocalDate.parse(date.toLocalDate().toString());
        } catch (DateTimeParseException e) {
            LOG.warn("Date mapped to now()", e.getMessage());
            return LocalDate.now();
        }
    }
}
