package de.peaqe.xbans.utils;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: DateUtils
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 03.08.2023 / 17:59
 *
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@SuppressWarnings(value = "unused")
public class DateUtils {

    private final Long time;

    public DateUtils(Long time) {
        this.time = time;
    }

    public String getDate() {
        LocalDateTime dateTime = Instant.ofEpochMilli(this.time).atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (dateTime.getYear() <= 2000) return "PERMANENT";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy '|' HH.mm 'Uhr'");

        return dateTime.format(formatter);
    }

}
