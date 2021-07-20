package me.cobble.obsidianchat.obsidianchat;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

public class Config {
    private static final Logger log = Bukkit.getLogger();
    private static File file;
    private static YamlConfiguration config;

    public static void setup() { // NO_UCD (use default)
        file = new File(
                Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin(ObsidianChat.getPlugin().getName())).getDataFolder(),
                "config.yml");
        if (!file.exists()) {
            log.info("No config found, Generating...");
            try {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            } catch (final IOException e) {
                // that's a lot of damage
                e.printStackTrace();
            }
            fileExists();
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return config;
    }

    public static void save() { // NO_UCD (use default)
        try {
            config.save(file);
        } catch (final IOException e) {
            System.err.println("Couldn't save file");
        }
    }

    public static void reload() { // NO_UCD (unused code)
        config = YamlConfiguration.loadConfiguration(file);
        log.info("Obsidian Chat config reloaded");
    }

    public static void fileExists() { // NO_UCD (use private)
        try {
            @SuppressWarnings("unused") final String filesString = Arrays
                    .toString(Objects.requireNonNull(Bukkit.getServer().getPluginManager().getPlugin("Graphite"))
                            .getDataFolder().listFiles());

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }
}
