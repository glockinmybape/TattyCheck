package org.glockinmybape.tattycheck;

import org.bukkit.Bukkit;

public class Logger {
    public static void info(String text) {
        Bukkit.getConsoleSender().sendMessage("§b(" + Main.inst.getDescription().getName() + "/INFO) " + text);
    }

    public static void warn(String text) {
        Bukkit.getConsoleSender().sendMessage("§b(" + Main.inst.getDescription().getName() + "/WARN) " + text);
    }

    public static void error(String text) {
        Bukkit.getConsoleSender().sendMessage("§4(" + Main.inst.getDescription().getName() + "/ERROR) " + text);
    }
}
