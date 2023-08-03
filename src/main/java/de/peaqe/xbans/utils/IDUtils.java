package de.peaqe.xbans.utils;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: IDUtils
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 02.08.2023 / 16:05
 *
 */

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@SuppressWarnings(value = "unused")
public class IDUtils {

    private final BanID banID;

    public IDUtils(BanID banID) {
        this.banID = banID;
    }

    public int getIdentifier() {
        return banID.getIdentifier();
    }

    public String getReason() {
        return banID.getReason();
    }

    public String getBanDuration() {
        return banID.getBanDuration();
    }

    public String getBanLevel() {
        return banID.getBanLevel().name();
    }

    public String getBanType() {
        return banID.getBanType().name();
    }

    public long getBanExpiry() {
        return banID.getTimeStamp();
    }

    public String getBanExpiryDate() {
        LocalDateTime dateTime = Instant.ofEpochMilli(this.getBanExpiry()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (dateTime.getYear() <= 2000) return "PERMANENT";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy ':' HH.mm 'Uhr'");

        return dateTime.format(formatter);
    }

}
