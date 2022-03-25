package org.glockinmybape.tattycheck;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetType implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Only for Players");
            return true;
        } else {
            Player p = (Player)sender;
            if (!p.hasPermission("tatty.check")) {
                p.sendMessage(Main.prefix + "У вас недостаточно прав.");
                return true;
            } else {
                if (args.length >= 1) {
                    if (args[0].equals("discord") || args[0].equals("Discord")) {
                        Main.data.set("type-check." + p.getName(), "discord");
                        Config.save(Main.data, "data.yml");
                        p.sendMessage(Main.prefix + " Теперь вы проверяете по Discord.");
                    }

                    if (args[0].equals("anydesk") || args[0].equals("AnyDesk") || args[0].equals("Anydesk")) {
                        Main.data.set("type-check." + p.getName(), "anydesk");
                        Config.save(Main.data, "data.yml");
                        p.sendMessage(Main.prefix + " Теперь вы проверяете по AnyDesk.");
                    }
                }

                return false;
            }
        }
    }
}
