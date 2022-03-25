package org.glockinmybape.tattycheck;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    public static FileConfiguration data;
    public static FileConfiguration config;
    public static Main inst;
    public static String prefix;
    public static String code;

    public void onEnable() {
        inst = this;
        Logger.info("§b");
        Logger.info("§b .----------------------------------------------------------. ");
        Logger.info("§b| .-------------------------------------------------------. |");
        Logger.info("§b| |             \t\t\t\t\t\t");
        Logger.info("§b| |            §7Плагин: §bTattyCheck§8| §7Версия: §b2.0                ");
        Logger.info("§b| |        §7Создан для §bTattyWorld §8- §7Разработал: §bglockinmybape\t");
        Logger.info("§b| |                    §bvk.com/TattyWorld");
        Logger.info("§b| |             \t\t\t\t\t\t");
        Logger.info("§b| '-------------------------------------------------------'§b|");
        Logger.info("§b'-----------------------------------------------------------'");
        Logger.info("§b");
        this.getCommand("check").setExecutor(new CommandCheck());
        this.getCommand("cheater").setExecutor(new CommandCheater());
        this.getCommand("settype").setExecutor(new CommandSetType());
        Bukkit.getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        prefix = this.getConfig().getString("prefix");
        code = this.getConfig().getString("main-color");
        config = Config.get("config.yml");
        data = Config.get("data.yml");
        Config.init();
    }

    public static boolean isInteger(String s) {
        if (s.startsWith("-")) {
            s = s.substring(1);
        }

        if (s.length() > 10) {
            return false;
        } else {
            for(int i = 0; i < s.length(); ++i) {
                if (Character.digit(s.charAt(i), 10) < 0) {
                    return false;
                }
            }

            if (s.length() == 10) {
                return Long.parseLong(s) <= 2147483647L;
            } else {
                return true;
            }
        }
    }

    public static String getCode() {
        return code;
    }
}
