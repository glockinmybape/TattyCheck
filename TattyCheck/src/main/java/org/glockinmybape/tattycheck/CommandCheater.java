package org.glockinmybape.tattycheck;

import java.util.Iterator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class CommandCheater implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Only for Players");
            return true;
        } else {
            Player p = (Player)sender;
            if (args.length >= 0) {
                if (!CommandCheck.checks.containsValue(p)) {
                    p.sendMessage(Main.prefix + "Вас никто не проверяет!");
                    return true;
                }

                BukkitTask tk = (BukkitTask)CommandCheck.runbs.get(p);
                tk.cancel();
                CommandCheck.runbs.remove(p);
                Player o = (Player)CommandCheck.checks2.get(p);
                CommandCheck.checks2.remove(p, o);
                CommandCheck.checks.remove(o, p);
                Iterator var9 = Main.inst.getConfig().getStringList("events.cheater.commands").iterator();
                if (var9.hasNext()) {
                    String s = (String)var9.next();
                    o.performCommand(s.replace("%player%", p.getName()));
                    o.sendMessage(Main.prefix + "Игрок §b" + p.getName() + "§f признался в читах");
                    return true;
                }
            }

            return false;
        }
    }
}
