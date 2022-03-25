package org.glockinmybape.tattycheck;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
    private static FileConfiguration config;

    public static FileConfiguration getConfig() {
        return config;
    }

    public static void init() {
        config = create("config.yml");
    }

    public static FileConfiguration get(String name) {
        File f = new File(Main.inst.getDataFolder(), name);
        if (Main.inst.getResource(name) == null) {
            return save(YamlConfiguration.loadConfiguration(f), name);
        } else {
            if (!f.exists()) {
                Main.inst.saveResource(name, false);
            }

            return YamlConfiguration.loadConfiguration(f);
        }
    }

    private static FileConfiguration create(String name) {
        File file = new File(Main.inst.getDataFolder(), name);
        if (Main.inst.getResource(name) == null) {
            return save(YamlConfiguration.loadConfiguration(file), name);
        } else {
            if (!file.exists()) {
                Main.inst.saveResource(name, false);
            }

            return YamlConfiguration.loadConfiguration(file);
        }
    }

    public static FileConfiguration save(FileConfiguration config, String name) {
        try {
            config.save(new File(Main.inst.getDataFolder(), name));
        } catch (IOException var3) {
            Bukkit.getConsoleSender().sendMessage(var3.getMessage());
        }

        return config;
    }
}

