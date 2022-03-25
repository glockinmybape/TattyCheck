package org.glockinmybape.tattycheck;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class CommandCheck implements CommandExecutor {
    public static Map<Player, Player> checks = new HashMap();
    public static Map<Player, Player> checks2 = new HashMap();
    public static Map<Player, BukkitTask> runbs = new HashMap();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Only for Players");
            return true;
        } else {
            final Player p = (Player)sender;
            if (!p.hasPermission("tatty.check")) {
                p.sendMessage(Main.prefix + "У вас недостаточно прав.");
                return true;
            } else {
                if (args.length == 0) {
                    Iterator var7 = Main.inst.getConfig().getStringList("help").iterator();

                    while(var7.hasNext()) {
                        String s = (String)var7.next();
                        sender.sendMessage(s.replace("&", "§"));
                    }
                }

                Iterator var8;
                final Player o;
                Player o1;
                String s;
                if (args.length == 1) {
                    o1 = Bukkit.getPlayerExact(args[0]);
                    if (checks.containsKey(p)) {
                        p.sendMessage(Main.prefix + "Вы уже вызываете другого игрока на проверку.");
                        return true;
                    }

                    if (o1 == null) {
                        p.sendMessage(Main.prefix + "Игрока нету на сервере.");
                        return true;
                    }

                    checks.put(p, o1);
                    checks2.put(o1, p);
                    var8 = Main.inst.getConfig().getStringList("commands-check").iterator();

                    while(var8.hasNext()) {
                        s = (String)var8.next();
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%admin%", p.getName()).replace("%player%", o1.getName()));
                    }

                    p.sendMessage(Main.prefix + "Вы вызвали на проверку игрока " + args[0]);
                    Runnable r = new Runnable() {
                        public void run() {
                            String s;
                            Iterator var2;
                            if (Main.data.getString("type-check." + p.getName()).equals("discord")) {
                                var2 = Main.inst.getConfig().getStringList("messages.check-discord").iterator();

                                while(var2.hasNext()) {
                                    s = (String)var2.next();
                                    o1.sendMessage(s.replace("&", "§").replace("%admin%", p.getName()));
                                }
                            }

                            if (Main.data.getString("type-check." + p.getName()).equals("anydesk")) {
                                var2 = Main.inst.getConfig().getStringList("messages.check-anydesk").iterator();

                                while(var2.hasNext()) {
                                    s = (String)var2.next();
                                    o1.sendMessage(s.replace("&", "§").replace("%admin%", p.getName()));
                                }
                            }

                            String[] title = Main.inst.getConfig().getString("messages.title-check").split("%nl%");
                            o1.sendTitle(title[0].replace("&", "§"), title[1].replace("&", "§"));
                        }
                    };
                    runbs.put(o1, Bukkit.getScheduler().runTaskTimer(Main.inst, r, 0L, 40L));
                }

                if (args.length > 1) {
                    o = Bukkit.getPlayerExact(args[0]);
                    BukkitTask tk;
                    if (args[1].equals("cancel")) {
                        if (!checks.containsKey(p)) {
                            p.sendMessage(Main.prefix + "Вы никого не проверяете");
                            return true;
                        }

                        if (checks.get(p) != o) {
                            p.sendMessage(Main.prefix + "Вы проверяете другого игрока");
                            return true;
                        }

                        checks2.remove(o, p);
                        checks.remove(p, o);
                        var8 = Main.inst.getConfig().getStringList("events.cancel.commands").iterator();

                        while(var8.hasNext()) {
                            s = (String)var8.next();
                            p.performCommand(s.replace("%player%", args[0]));
                        }

                        tk = (BukkitTask)runbs.get(o);
                        tk.cancel();
                        runbs.remove(o);
                        p.sendMessage(Main.prefix + "Игрок §b" + o.getName() + " §fне прошел проверку на читы");
                    }

                    if (args[1].equals("deleted")) {
                        if (!checks.containsKey(p)) {
                            p.sendMessage(Main.prefix + "Вы никого не проверяете");
                            return true;
                        }

                        if (checks.get(p) != o) {
                            p.sendMessage(Main.prefix + "Вы проверяете другого игрока");
                            return true;
                        }

                        checks2.remove(o, p);
                        checks.remove(p, o);
                        var8 = Main.inst.getConfig().getStringList("events.deleted.commands").iterator();

                        while(var8.hasNext()) {
                            s = (String)var8.next();
                            p.performCommand(s.replace("%player%", o.getName()));
                        }

                        tk = (BukkitTask)runbs.get(o);
                        tk.cancel();
                        runbs.remove(o);
                        p.sendMessage(Main.prefix + "Игрок §b" + o.getName() + " §fбыл наказан за удалённые читы");
                    }

                    if (args[1].equals("ignore")) {
                        if (!checks.containsKey(p)) {
                            p.sendMessage(Main.prefix + "Вы никого не проверяете");
                            return true;
                        }

                        if (checks.get(p) != o) {
                            p.sendMessage(Main.prefix + "Вы проверяете другого игрока");
                            return true;
                        }

                        checks2.remove(o, p);
                        checks.remove(p, o);
                        var8 = Main.inst.getConfig().getStringList("events.ignore.commands").iterator();

                        while(var8.hasNext()) {
                            s = (String)var8.next();
                            p.performCommand(s.replace("%player%", o.getName()));
                        }

                        tk = (BukkitTask)runbs.get(o);
                        tk.cancel();
                        runbs.remove(o);
                        p.sendMessage(Main.prefix + "Игрок §b" + o.getName() + " §fбыл наказан за игнор проверки");
                    }

                    if (args[1].equals("leave")) {
                        if (!checks.containsKey(p)) {
                            p.sendMessage(Main.prefix + "Вы никого не проверяете");
                            return true;
                        }

                        if (checks.get(p) != o) {
                            p.sendMessage(Main.prefix + "Вы проверяете другого игрока");
                            return true;
                        }

                        checks2.remove(o, p);
                        checks.remove(p, o);
                        var8 = Main.inst.getConfig().getStringList("events.leave.commands").iterator();

                        while(var8.hasNext()) {
                            s = (String)var8.next();
                            p.performCommand(s.replace("%player%", o.getName()));
                        }

                        tk = (BukkitTask)runbs.get(o);
                        tk.cancel();
                        runbs.remove(o);
                        p.sendMessage(Main.prefix + "Игрок §b" + o.getName() + " §fбыл наказан за лив с проверки");
                    }

                    if (args[1].equals("confirm")) {
                        if (!checks.containsKey(p)) {
                            p.sendMessage(Main.prefix + "Вы никого не проверяете");
                            return true;
                        }

                        if (checks.get(p) != o) {
                            p.sendMessage(Main.prefix + "Вы проверяете другого игрока");
                            return true;
                        }

                        checks2.remove(o, p);
                        checks.remove(p, o);
                        tk = (BukkitTask)runbs.get(o);
                        tk.cancel();
                        runbs.remove(o);
                        p.sendMessage(Main.prefix + "Игрок §b" + o.getName() + " §fуспешно прошел проверку!");
                        o.sendMessage(Main.prefix + "Вы успешно прошли проверку от Администрации §b" + p.getName());
                    }
                }

                return false;
            }
        }
    }
}
