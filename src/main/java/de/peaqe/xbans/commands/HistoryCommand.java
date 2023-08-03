package de.peaqe.xbans.commands;

import de.peaqe.xbans.XBans;
import de.peaqe.xbans.provider.BanHistoryDatabase;
import de.peaqe.xbans.utils.DateUtils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;


/*
 *
 *  Class by peaqe created in 2023
 *  Class: HistoryCommand
 *
 *  Information's:
 *  Type: Java-Class | Bungeecord Command
 *  Created: 02.08.2023 / 20:33
 *
 */

@SuppressWarnings(value = "unused")
public class HistoryCommand extends Command implements TabExecutor {

    public HistoryCommand(String name) {
        super(name);
    }

	XBans main = XBans.instance;
	BanHistoryDatabase historyDatabase = main.getBanHistoryDatabase();

	@SuppressWarnings(value = "deprecation")
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

		sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Der " + main.getColor() + "Ban-Verlauf §7von " + main.getColor() + args[0] + "§7:"));
		sender.sendMessage(TextComponent.fromLegacyText(""));

		sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Gebannt: " + (main.getBanDatabase().isBanned(args[0]) ? "§aJa" : "§cNein")));
		sender.sendMessage(TextComponent.fromLegacyText(""));

		sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Bans: "));
		list.forEach(document -> {

			var text_normal = ("§8» §c§lBAN " +
					main.getColor() +
					document.getString("ban_reason") +
					"§7 Laufzeit " +
					main.getColor() +
					main.idManager.getExpiry(main.idManager.getBanID(document.getInteger("ban_id"))));

			var text_hover = ("§8» §c§lINFO §7Gebannt von " +
					main.getColor() +
					document.getString("ban_autor") +
					"§7 Zeit (" +
					main.getColor() +
					((document.getLong("ban_date") != null) ? "Unbekannt" : new DateUtils(document.getLong("ban_date")).getDate()) +
					" §8» " +
					main.getColor() +
					((document.getLong("ban_date") != null) ? "Unbekannt" : new DateUtils(document.getLong("ban_expiry")).getDate()) );


			TextComponent component = new TextComponent(TextComponent.fromLegacyText(text_normal));
			component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText((text_hover))));

			sender.sendMessage(component);

		});

    }


	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

		List<String> matches = new ArrayList<>();

		if (!(sender.hasPermission("system.history"))) return matches;

		if (args.length == 1) {
			ProxyServer.getInstance().getPlayers().forEach(player -> {
				if (player.getName().equalsIgnoreCase(sender.getName())) return;
				matches.add(player.getName());
			});
		}

		return matches;
	}
}