package com.micutne.odik.utils;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class TimeUtils {
    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
    public final static ZoneId koreaZoneId = ZoneId.of("Asia/Seoul");

    // Instant -> LocalDataTime 변환
    public static String getLocalTime(Instant instant) {
        return instant.atZone(koreaZoneId).toLocalDateTime().format(formatter);
    }
}
