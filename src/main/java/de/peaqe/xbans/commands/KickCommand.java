package de.peaqe.xbans.commands;

import de.peaqe.xbans.XBans;
import de.peaqe.xbans.utils.KickScreen;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/*
 *
 *  Class by peaqe created in 2023
 *  Class: KickCommand
 *
 *  Information's:
 *  Type: Java-Class | Bungeecord Command
 *  Created: 02.08.2023 / 20:11
 *
 */

@SuppressWarnings(value = "unused")
public class KickCommand extends Command implements TabExecutor {

    public KickCommand(String name) {
        super(name);
    }

	XBans main = XBans.instance;

    @Override
    public void execute(CommandSender sender, String[] args) {

		if (!(sender.hasPermission("system.kick"))) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Du hast nicht die benötigte Berechtigung, um diesen Befehl ausführen zu können!"));
			return;
		}

		if (args.length < 2) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Bitte verwende: /kick (Spieler) (Grund)"));
			return;
		}

		var target = ProxyServer.getInstance().getPlayer(args[0]);

		if (target == null) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Der Spieler " + main.getColor() + args[0] + "§7 ist derzeit nicht auf dem Netzwerk!"));
			return;
		}

		StringBuilder reasonBuilder = new StringBuilder();

		for (int i = 1; i < args.length; i++) {
			reasonBuilder.append(args[i]).append(" ");
		}

		var reason = reasonBuilder.toString().trim();

		target.disconnect(TextComponent.fromLegacyText(new KickScreen(reason, sender.getName()).get()));

        return;
    }


    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

		List<String> matches = new ArrayList<>();

		if (!sender.hasPermission("system.kick")) return matches;

		if (args.length == 1) {
			ProxyServer.getInstance().getPlayers().forEach(player -> {
				if (player.getName().equalsIgnoreCase(sender.getName())) return;
				matches.add(player.getName());
			});
		}

        return matches;
    }

}