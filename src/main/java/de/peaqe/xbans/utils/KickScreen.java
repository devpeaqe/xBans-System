package de.peaqe.xbans.utils;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: KickScreen
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 02.08.2023 / 20:09
 *
 */

import de.peaqe.xbans.XBans;

@SuppressWarnings(value = "unused")
public class KickScreen {

    XBans main = XBans.instance;

    private final String reason;
    private final String autor;

    public KickScreen(String reason, String autor) {
        this.reason = reason;
        this.autor = autor;
    }

    public String get() {

        var prefix = ("§8« " + main.getColor() + "SYSTEM §8»");

        var empty = "\n";

        var screen_component_1 = main.getColor() + "Du wurdest gekickt!";
        var screen_component_2 = "§7Grund: " + main.getColor() + this.reason;
        var screen_component_3 = "§7Von: " + main.getColor() + this.autor;
        var screen_component_4 = "§e§oDu wurdest zu unrecht bestraft?";
        var screen_component_5 = main.getColor() + "§ohttps://serversystem.de/go/ea";

        return prefix +
                empty +
                screen_component_1 +
                empty + empty +
                screen_component_2 +
                empty +
                screen_component_3 +
                empty +
                empty +
                screen_component_4 +
                empty +
                screen_component_5;

    }

}
