package de.peaqe.xbans.commands;

import de.peaqe.xbans.XBans;
import de.peaqe.xbans.provider.BanHistoryDatabase;
import de.peaqe.xbans.utils.IDUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;


/*
 *
 *  Class by peaqe created in 2023
 *  Class: HistoryCommnad
 *
 *  Information's:
 *  Type: Java-Class | Bungeecord Command
 *  Created: 02.08.2023 / 20:33
 *
 */

@SuppressWarnings(value = "unused")
public class HistoryCommnad extends Command implements TabExecutor {

    public HistoryCommnad(String name) {
        super(name);
    }

	XBans main = XBans.instance;
	BanHistoryDatabase historyDatabase = main.getBanHistoryDatabase();

    @Override
    public void execute(CommandSender sender, String[] args) {

		if (!(sender.hasPermission("system.ban.history"))) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Du hast nicht die benötigte Berechtigung, um diesen Command ausführen zu können!"));
			return;
		}

		if (!(args.length == 1)) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Bitte verwende: /history (Spieler)"));
			return;
		}

		if (!main.getPlayerDatabase().isRegistered(args[0])) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Der Spieler " + main.getColor() + "§7 konnte nicht gefunden werden!"));
			return;
		}

		if (historyDatabase.getPlayerBanSize(args[0]) <= 0) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Der Spieler " + main.getColor() + args[0] + "§7 wurde in der Vergangenheit nicht bestraft!"));
			return;
		}

		var list = historyDatabase.getPlayerBans(args[0]);

		sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Die Vergangenheit von " + main.getColor() + args[0] + "§7:"));
		sender.sendMessage(TextComponent.fromLegacyText(""));

		sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Gebannt: " + (main.getBanDatabase().isBanned(args[0]) ? "§aJa" : "§cNein")));
		sender.sendMessage(TextComponent.fromLegacyText(""));

		sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Bans: "));
		list.forEach(document -> {
			sender.sendMessage(TextComponent.fromLegacyText(
					"§8» §c§lBAN " +
							main.getColor() +
							document.getString("ban_reason") +
							"§7 von " +
							main.getColor() +
							document.getString("ban_autor") +
							"§7 Laufzeit " +
							main.getColor() +
							main.idManager.getExpiry(main.idManager.getBanID(document.getInteger("ban_id")))
			));
		});


        return;
    }


    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}