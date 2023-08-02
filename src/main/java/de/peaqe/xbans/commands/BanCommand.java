package de.peaqe.xbans.commands;

import de.peaqe.xbans.XBans;
import de.peaqe.xbans.utils.BanID;
import de.peaqe.xbans.utils.BanScreen;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.List;


/*
 *
 *  Class by peaqe created in 2023
 *  Class: BanCommand
 *
 *  Information's:
 *  Type: Java-Class | Bungeecord Command
 *  Created: 02.08.2023 / 12:34
 *
 */

@SuppressWarnings(value = "unused")
public class BanCommand extends Command implements TabExecutor {

    public BanCommand(String name) {
        super(name);
    }

	XBans main = XBans.instance;

    @Override
    public void execute(CommandSender sender, String[] args) {

		// Check senders permission
		if (!sender.hasPermission("system.ban")) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Du hast nicht die benötigte Berechtigung, um diesen Command ausführen zu können!"));
			return;
		}

		// Check the Syntax
		if (!(args.length == 2)) {

			// Check if it is a help command
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Hier bekommst du eine übersicht über unsere " + main.getColor() + "Banngründe§7:"));
				for (var id : BanID.values()) {
					sender.sendMessage(TextComponent.fromLegacyText(main.prefix + main.getColor() + id.getIdentity() + "§8: " + main.getColor() + id.getReason() + "§7 » " + main.getColor() + main.getIdManager().getExpiry(id)));
				}
				return;
			}

			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Bitte verwende: /ban (Spieler) (ID)"));
			return;
		}

		// Check if the target exists
		if (!main.getPlayerDatabase().isRegistered(args[0])) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Der Spieler " + main.getColor() + args[0] + "§7 konnte nicht gefunden werden!"));
			return;
		}

		// Check if the target is already banned
		if (main.getBanDatabase().isBanned(args[0])) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Dieser Spieler ist bereits gebannt"));
			return;
		}

		// Sender has Permission and the right syntax
		// Target is not banned and registered

		// Check if the banid exists
		if (!main.getIdManager().idExists(args[1])) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Die Ban-ID \"" + main.getColor() + args[1] +"§7\" konnte nicht gefunden werden!"));
			return;
		}

		// Get the banid from the argument
		var banid = main.getIdManager().getBanID(args[1]);

		// Check if the sender hasn't permission to execute the ban
		if (!sender.hasPermission(banid.getBanLevel().getPermission())) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Für den angegebenen Grund darfst du nicht bannen!"));
			return;
		}

		// Ban the target
		main.getBanDatabase().banPlayer(sender.getName(), args[0], banid);

		// Kick the target if he is on the network
		if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
			ProxyServer.getInstance().getPlayer(args[0]).disconnect(TextComponent.fromLegacyText(new BanScreen(args[0]).get()));
		}

		// Send feedback to the sender
		sender.sendMessage(TextComponent.fromLegacyText(main.getColor() + "§lBAN §8» §7" + "Du hast den Spieler " + main.getColor() + args[0] + "§7 für die ID " + main.getColor() + banid.getIdentifier() + " §7gebannt."));

		// Send feedback to the team
		ProxyServer.getInstance().getPlayers().forEach(player -> {
			if (!player.hasPermission("system.ban.notify")) return;
			player.sendMessage(TextComponent.fromLegacyText(main.getColor() + "§lBAN §8» " + main.getColor() + args[0] + "§7 von " + main.getColor() + sender.getName() + "§7für " + main.getColor() + banid.getReason() + "§7 (" + main.getColor() + main.getIdManager().getExpiry(banid) + "§7)"));
		});

    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

		List<String> matches = new ArrayList<>();

		if (!sender.hasPermission("system.ban")) return matches;

		if (args.length == 1) {

			matches.add("help");

			ProxyServer.getInstance().getPlayers().forEach(player -> {
				if (player.hasPermission("system.ban.bypass")) return;
				if (player.getName().equalsIgnoreCase(sender.getName())) return;
				matches.add(player.getName());
			});
		}

		if (args.length == 2 && !args[0].equalsIgnoreCase("help")) {
			for (var banid : BanID.values()) {
				if (sender.hasPermission(banid.getBanLevel().getPermission())) {
					matches.add(banid.getIdentity());
				}
			}

		}

        return matches;
    }
}