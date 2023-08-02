package de.peaqe.xbans.config;

/*
 *
 *  Class by peaqe created in 2023
 *  Class: DatabaseConfig
 *
 *  Information's:
 *  Type: Java-Class
 *  Created: 02.08.2023 / 11:40
 *
 */

import de.peaqe.xbans.XBans;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@SuppressWarnings(value = "unused")
public class DatabaseConfig {

    private final File file = new File(XBans.instance.getDataFolder().getAbsolutePath(), "database.yml");
    private Configuration config;
    private final String path = "Database.";

    @SuppressWarnings(value = "all")
    public void create() {
        if (file.exists()) {
            loadConfig();
            return;
        }

        try {
            XBans.instance.getDataFolder().mkdirs();
            file.createNewFile();

            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

            config.set(this.path + ".username", "USER");
            config.set(this.path + ".hostname", "HOST");
            config.set(this.path + ".password", "PASSWORD");
            config.set(this.path + ".database", "DATABASE");

            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);

        } catch (IOException e) {
            // Log the error and print the stack trace
            e.printStackTrace();
            throw new RuntimeException("Error loading or saving the configuration file: " + e.getMessage());
        }
    }

    private void loadConfig() {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            // Log the error and print the stack trace
            e.printStackTrace();
            throw new RuntimeException("Error loading the configuration file: " + e.getMessage());
        }
    }


    public String get(String input) {
        if (config == null) {
            loadConfig();
        }
        return config.getString(this.path + "." + input);
    }
}