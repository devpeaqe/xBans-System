package de.peaqe.xbans.utils;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: BanScreen
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 02.08.2023 / 12:54
 *
 */

import de.peaqe.xbans.XBans;

@SuppressWarnings(value = "unused")
public class BanScreen {

    private final XBans main = XBans.instance;
    private final BanID banID;
    private final String autor;

    public BanScreen(String name) {

        this.banID = main.getBanDatabase().getBanID(name);
        this.autor = main.getBanDatabase().getAutor(name);

    }

    public String get() {

        var prefix = ("§8« " + main.getColor() + "SYSTEM §8»");

        var empty = "\n";

        var screen_component_1 = main.getColor() + "Du wurdest gebannt!";
        var screen_component_2 = "§7Grund: " + main.getColor() + this.banID.getReason();
        var screen_component_3 = "§7Laufzeit: " + main.getColor() + main.getIdManager().getExpiry(this.banID);
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
