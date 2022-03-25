package org.glockinmybape.tattycheck;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
    public static Map<Player, Integer> runbs = new HashMap();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (CommandCheck.checks.containsValue(p)) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void Interact(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            Player o = (Player)e.getRightClicked();
            if (CommandCheck.checks.containsValue(o)) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void death(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (CommandCheck.checks.containsValue(p)) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onHeld(PlayerItemHeldEvent e) {
        Player o = e.getPlayer();
        if (CommandCheck.checks.containsValue(o)) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player o = e.getPlayer();
        if (CommandCheck.checks.containsValue(o)) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player o = e.getPlayer();
        if (CommandCheck.checks.containsValue(o)) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (CommandCheck.checks.containsValue(p)) {
            Player o = (Player)CommandCheck.checks2.get(p);
            CommandCheck.checks2.remove(p, o);
            CommandCheck.checks.remove(o, p);
            Iterator var5 = Main.inst.getConfig().getStringList("events.cheater.commands").iterator();

            while(var5.hasNext()) {
                String s = (String)var5.next();
                o.performCommand(s.replace("%player%", p.getName()));
                o.sendMessage(Main.prefix + "Игрок §b" + p.getName() + "§f вышел во время проверки");
            }

        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("tatty.check")) {
            if (!Main.data.contains("type-check." + p.getName())) {
                Main.data.set("type-check." + p.getName(), "discord");
                Config.save(Main.data, "data.yml");
            }
        }
    }
}
