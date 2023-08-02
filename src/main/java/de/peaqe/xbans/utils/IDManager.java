package de.peaqe.xbans.utils;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: IDManager
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 02.08.2023 / 12:26
 *
 */

import de.peaqe.xbans.XBans;

@SuppressWarnings(value = "unused")
public class IDManager {

    private String banID;

    public BanID getBanID(String input) {

        var identifier = 1;

        try {
            identifier = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return BanID.nine;
        }

        for(var id : BanID.values()) {
            if (id.getIdentifier() == identifier) {
                return id;
            }
        }

        return BanID.nine;
    }

    public BanID getBanID(int input) {

        for(var id : BanID.values()) {
            if (id.getIdentifier() == input) {
                return id;
            }
        }

        return BanID.nine;
    }

    public boolean idExists(String input) {

        var identifier = 1;

        try {
            identifier = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return false;
        }

        for(var id : BanID.values()) {
            if (id.getIdentifier() == identifier) {
                return true;
            }
        }

        return false;
    }

    public boolean idExists(int input) {

        for(var id : BanID.values()) {
            if (id.getIdentifier() == input) {
                return true;
            }
        }

        return false;
    }

    public String getExpiry(BanID banID) {
        if (banID.getBanDuration().equalsIgnoreCase("-1")) {
            return (XBans.instance.getColor() +  "PERMANENT");
        }

        return banID.getBanDuration();
    }

}
