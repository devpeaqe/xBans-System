package de.peaqe.xbans.commands;

import de.peaqe.xbans.XBans;
import de.peaqe.xbans.provider.BanDatabase;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;


/*
 *
 *  Class by peaqe created in 2023
 *  Class: UnbanCommand
 *
 *  Information's:
 *  Type: Java-Class | Bungeecord Command
 *  Created: 02.08.2023 / 19:11
 *
 */

@SuppressWarnings(value = "unused")
public class UnbanCommand extends Command implements TabExecutor {

    public UnbanCommand(String name) {
        super(name);
    }

	XBans main = XBans.instance;
    BanDatabase banDatabase = main.getBanDatabase();

    @Override
    public void execute(CommandSender sender, String[] args) {

		if (!(sender.hasPermission("system.unban"))) {
			sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Du hast nicht die benötigte Berechtigung, um diesen Command ausführen zu können!"));
			return;
		}

		if (!(args.length == 1)) {
            sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Bitte verwende: /unban (Spieler)"));
            return;
        }

        if (!main.getPlayerDatabase().isRegistered(args[0])) {
            sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Der Spieler " + main.getColor() + args[0] + " §7konnte nicht gefunden werden!"));
            return;
        }

        if (!banDatabase.isBanned(args[0])) {
            sender.sendMessage(TextComponent.fromLegacyText(main.prefix + "Der Spieler " + main.getColor() + args[0] + "§7 ist derzeit nicht gebannt!"));
            return;
        }

        banDatabase.unbanPlayer(args[0]);
        sender.sendMessage(TextComponent.fromLegacyText("§a§lUNBAN §7Du hast den Spieler " + main.getColor() + args[0] + " §7entbannt."));

        ProxyServer.getInstance().getPlayers().forEach(player -> {
            if (!player.hasPermission("system.notify")) return;
            player.sendMessage(TextComponent.fromLegacyText("§a§lUNBAN §8» §a" + args[0] + "§7 von §a" + sender.getName()));
        });

    }


    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return new ArrayList<>();
    }
}