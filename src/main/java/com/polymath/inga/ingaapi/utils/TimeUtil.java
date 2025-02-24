package com.polymath.inga.ingaapi.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtil {
    public static  long calculateExpirationTimeInSec(LocalDateTime dateTime){
        return Math.max(0,dateTime.toEpochSecond(ZoneOffset.UTC)-LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));

    }
}
