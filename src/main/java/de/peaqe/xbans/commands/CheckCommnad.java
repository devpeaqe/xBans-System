package de.peaqe.xbans.commands;

import de.peaqe.xbans.XBans;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/*
 *
 *  Class by peaqe created in 2023
 *  Class: CheckCommnad
 *
 *  Information's:
 *  Type: Java-Class | Bungeecord Command
 *  Created: 03.08.2023 / 17:41
 *
 */

@SuppressWarnings(value = "unused")
public class CheckCommnad extends Command implements TabExecutor {

    public CheckCommnad(String name) {
        super(name);
    }

	XBans main = XBans.instance;

    @SuppressWarnings("deprecation")
    @Override
    public void execute(CommandSender sender, String[] args) {

		// Check senders permission
		if (!sender.hasPermission("system.check")) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Du hast nicht die benötigte Berechtigung, um diesen Command ausführen zu können!"));
			return;
		}

		// Check arguments
		if (!(args.length == 1)) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Bitte verwende: /check (Spieler)"));
			return;
		}

		// Check if the player exists
		if (!(main.getPlayerDatabase().isRegistered(args[0]))) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Der Spieler " + main.getColor() + args[0] + "§7 konnte nicht gefunden werden!"));
			return;
		}

		// Get ban information's
		var banned = main.getBanDatabase().isBanned(args[0]);
		var bans_history = main.getBanHistoryDatabase().getPlayerBans(args[0]);
		var bans_amount = bans_history.size();

		var siu = ((bans_amount == 0) ? "§c0" : "§a" + bans_amount);

		// Create clickable ban history
		TextComponent component = new TextComponent(TextComponent.fromLegacyText(main.prefix + "Bans: "));
		TextComponent clickable = new TextComponent(TextComponent.fromLegacyText(siu));

		clickable.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/history " + args[0]));
		clickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§8§o» §7§oKlicke um die " + main.getColor() + ChatColor.ITALIC + "Ban-Historie§7, von " + main.getColor() + ChatColor.ITALIC + args[0] + "§7 anzeigen zu lassen.")));

		component.addExtra(clickable);

		// Send feedback to the sender
		sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Informationen über den Spieler " + main.getColor() + args[0] + "§7:"));
		sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Gebannt: " + (banned ? "§aJa" : "§cNein")));
		sender.sendMessage(component);

    }


    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

		List<String> matches = new ArrayList<>();

		if (!(sender.hasPermission("system.check"))) return matches;

		if (args.length == 1) {
			ProxyServer.getInstance().getPlayers().forEach(player -> {
				if (player.getName().equalsIgnoreCase(sender.getName())) return;
				matches.add(player.getName());
			});
		}

        return matches;
    }
}