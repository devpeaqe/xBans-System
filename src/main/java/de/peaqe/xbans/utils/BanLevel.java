package de.peaqe.xbans.utils;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: BanLevel
 *
 *  Information's:
 *  Type: Enum-Class
 *  Created: 02.08.2023 / 12:11
 *
 */

@SuppressWarnings(value = "unused")
public enum BanLevel {

    SUP("system.ban.sup"),
    SUP_PLUS("system.ban.sup+"),
    MOD("system.ban.mod"),
    MOD_PLUS("system.ban.mod+"),
    ADMIN("system.ban.*");

    private final String permission;

    BanLevel(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
