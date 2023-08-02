package de.peaqe.xbans.listener;
/*
 *
 *  Class by peaqe created in 2023
 *  Class: ProxyJoinListener
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 02.08.2023 / 11:54
 *
 */

import de.peaqe.xbans.XBans;
import de.peaqe.xbans.utils.BanScreen;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@SuppressWarnings(value = "unused")
public class ProxyJoinListener implements Listener {

    XBans main = XBans.instance;

    @EventHandler
    public void onConnect(PostLoginEvent event) {

        XBans.instance.getPlayerDatabase().registerPlayer(event.getPlayer());

        if (main.getBanDatabase().isBanned(event.getPlayer().getName())) {

            BanScreen banScreen = new BanScreen(event.getPlayer().getName());
            event.getPlayer().disconnect(TextComponent.fromLegacyText(banScreen.get()));

        }

    }

}
