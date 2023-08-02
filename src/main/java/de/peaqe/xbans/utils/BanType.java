package de.peaqe.xbans.utils;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: BanType
 *
 *  Information's:
 *  Type: Enum-Class
 *  Created: 02.08.2023 / 11:32
 *
 */

import lombok.Getter;

@SuppressWarnings(value = "unused")
@Getter
public enum BanType {

    BAN_PERMANENTLY("ban_permanently"),
    BAN_TEMPORARILY("ban_temporarily"),
    MUTE_PERMANENTLY("mute_permanently"),
    MUTE_TEMPORARILY("mute_temporarily");

    private final String identifier;

    BanType(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
