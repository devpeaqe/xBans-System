package de.peaqe.xbans;

import de.peaqe.xbans.commands.BanCommand;
import de.peaqe.xbans.commands.UnbanCommand;
import de.peaqe.xbans.config.DatabaseConfig;
import de.peaqe.xbans.listener.ProxyJoinListener;
import de.peaqe.xbans.provider.BanDatabase;
import de.peaqe.xbans.provider.PlayerDatabase;
import de.peaqe.xbans.utils.IDManager;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public final class XBans extends Plugin {

    public static XBans instance;
    public DatabaseConfig databaseConfig;
    public PlayerDatabase playerDatabase;
    public BanDatabase banDatabase;
    public IDManager idManager;
    public String prefix;
    public ChatColor color;
    public Logger logger;

    @Override
    public void onEnable() {

        this.init();

        this.registerEvents();
        this.registerCommand();

        this.getBanDatabase().check();

        logger.log(Level.INFO, getPrefix() + "Das Plugin wurde erfolgreich geladen!");

    }

    private void init(){

        instance = this;

        color = ChatColor.RED;
        prefix = this.color + "§lSYSTEM §8» §7";

        databaseConfig = new DatabaseConfig();
        databaseConfig.create();

        banDatabase = new BanDatabase();
        playerDatabase = new PlayerDatabase();

        idManager = new IDManager();

        logger = getProxy().getLogger();

    }

    private void registerEvents() {
        getProxy().getPluginManager().registerListener(this, new ProxyJoinListener());
    }

    private void registerCommand() {
        getProxy().getPluginManager().registerCommand(this, new BanCommand("ban"));
        getProxy().getPluginManager().registerCommand(this, new UnbanCommand("unban"));
    }

}
