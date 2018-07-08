package org.kaidzen.webscrap.util;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StandardTimeClock {

    private final Clock clock = Clock.systemUTC();

    private LocalDateTime timeNow() {
        return LocalDateTime.now(clock);
    }

    public Timestamp createTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Timestamp.valueOf(timeNow().format(formatter));
    }
}
