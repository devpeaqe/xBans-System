package de.peaqe.xbans.utils;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: BanID
 *
 *  Information's:
 *  Type: Enum-Class
 *  Created: 02.08.2023 / 12:07
 *
 */

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SuppressWarnings(value = "unused")
public enum BanID {

    zero(0, "0", "Entwickler-Ban", "10 Sekunden", BanLevel.ADMIN, BanType.BAN_TEMPORARILY),
    one(1, "1", "Supporter-Ban", "12 Stunden", BanLevel.SUP, BanType.BAN_TEMPORARILY),
    two(2, "2", "Unerlaubte-Clientmodifikation", "30 Tage", BanLevel.SUP_PLUS, BanType.BAN_TEMPORARILY),
    three(3, "3", "Bugusing", "30 Tage", BanLevel.SUP_PLUS, BanType.BAN_TEMPORARILY),
    four(4, "4", "Beleidigung", "12 Stunden", BanLevel.SUP_PLUS, BanType.BAN_TEMPORARILY),
    five(5, "5", "Datenschutz", "-1", BanLevel.MOD, BanType.BAN_PERMANENTLY),
    six(6, "6", "Diskriminierung", "-1", BanLevel.MOD, BanType.BAN_PERMANENTLY),
    seven(7, "7", "Rechtsextrem", "-1", BanLevel.MOD, BanType.BAN_PERMANENTLY),
    eight(8, "8", "Sicherheit", "-1", BanLevel.MOD_PLUS, BanType.BAN_PERMANENTLY),
    nine(9, "9", "Politische-Äußerungen", "-1", BanLevel.MOD_PLUS, BanType.BAN_PERMANENTLY),
    ten(10, "10", "Unerwünscht", "-1", BanLevel.ADMIN, BanType.BAN_PERMANENTLY),
    eleven(11, "11", "Hausverbot", "-1", BanLevel.ADMIN, BanType.BAN_PERMANENTLY);

    private final int identifier;
    private final String identity;
    private final String reason;
    private final String banDuration;
    private final BanLevel banLevel;
    private final BanType banType;

    BanID(int identifier, String identity, String reason, String banDuration, BanLevel banLevel, BanType banType) {
        this.identifier = identifier;
        this.identity = identity;
        this.reason = reason;
        this.banDuration = banDuration;
        this.banLevel = banLevel;
        this.banType = banType;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getBanDuration() {
        return banDuration;
    }

    public BanLevel getBanLevel() {
        return banLevel;
    }

    public BanType getBanType() {
        return banType;
    }

    public String getReason() {
        return reason;
    }

    public String getIdentity() {
        return identity;
    }

    public long getTimeStamp() {
        Instant now = Instant.now();

        if ("-1".equals(banDuration)) {
            return -1L;
        } else {
            int durationValue = Integer.parseInt(banDuration.split(" ")[0]);
            String unit = banDuration.split(" ")[1].toLowerCase();

            Instant futureTime;
            switch (unit) {
                case "sekunden":
                    futureTime = now.plus(durationValue, ChronoUnit.SECONDS);
                    break;
                case "stunden":
                    futureTime = now.plus(durationValue, ChronoUnit.HOURS);
                    break;
                case "tage":
                    futureTime = now.plus(durationValue, ChronoUnit.DAYS);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid time unit: " + unit);
            }

            Duration duration = Duration.between(now, futureTime);
            return System.currentTimeMillis() + duration.toMillis();
        }
    }

}
