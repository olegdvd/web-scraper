package org.kaidzen.webscrap.util;

import java.time.Clock;
import java.time.LocalDateTime;

public class StandardTimeClock {

    private static final Clock clock = Clock.systemUTC();

    private StandardTimeClock() {
    }

    public static LocalDateTime timeNow(){
        return LocalDateTime.now(clock);
    }
}
