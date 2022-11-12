package com.plugin.Handlers;

import com.plugin.QuarkTestPlugin;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreeperHeadHandler implements Listener {
    Player p;

    QuarkTestPlugin plugin;

    double yaw;
    double pitch;
    double x;
    double y;

    boolean isFlyingByAbility;
    boolean cooldownIsActive;
    boolean isThrown;

    public CreeperHeadHandler(QuarkTestPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHeadUse(PlayerInteractEvent e) {

        p = e.getPlayer();

        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            e.setCancelled(true);
        }

        yaw = ((p.getLocation().getYaw() + 90) * Math.PI) / 180;
        pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;

        x = Math.sin(pitch) * Math.cos(yaw);
        y = Math.sin(pitch) * Math.sin(yaw);

        World world = p.getWorld();
        Location loc = p.getLocation();

        if (p.getLevel() != 0) {
            if (p.getItemInHand().getType() == Material.CREEPER_SPAWN_EGG && (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
                isFlyingByAbility = false;
                world.createExplosion(loc, 2.3F, false, true);
                isFlyingByAbility = true;

                p.setLevel(p.getLevel() - 1);


                final int[] i = {10};    //cooldown seconds
                if(cooldownIsActive == false && p.getItemInHand().getType() == Material.CREEPER_SPAWN_EGG) {
                    new BukkitRunnable() {
                        public void run() {
                            cooldownIsActive = true;
                            if ((p.getLevel() != 3)) {
                                p.sendMessage(ChatColor.RED + "Cooldown " + i[0]);
                                i[0]--;
                                if (i[0] == 0) {
                                    p.sendMessage(ChatColor.GREEN + "Cooldown reseted");
                                    if (p.getLevel() == 3) {
                                        this.cancel();
                                        cooldownIsActive = false;
                                        i[0] = 10;
                                    } else {
                                        p.setLevel(p.getLevel() + 1);
                                        i[0] = 10;
                                    }
                                }
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 20L);

                }
            }
        }
    }

  @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if(e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
            if (String.valueOf(e.getEntity()).equals("CraftPlayer{name=" + p.getName() + "}") && String.valueOf(e.getEntity().getType()).equals("PLAYER")) {
                e.setCancelled(true);
                e.getEntity().setVelocity(new Vector(x, 1.35, y));
            }
        }

       if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL) && isFlyingByAbility == true) {
           e.setCancelled(true);
           isFlyingByAbility = false;
       }
    }

    //HashMap<String, BukkitTask> playerSchedulers = new HashMap<String, BukkitTask>();

    //Listener Class
//    @EventHandler
//    public void onEvent1(Event1 event){
//        Player player = event.getPlayer();
//        BukkitTask task = new BukkitRunnable() {
//            public void run() {
//                //your method....
//            }
//        }.runTaskTimer(this, 0, 20);
//        plugin.playerSchedulers.put(player.getName(), task);
//    }
//
//    public void onEvent2(Event2 event){
//        plugin.get(p.getName()).cancel();
//
//    }



//    @EventHandler
//    public void onPlayerDroped(PlayerDropItemEvent ev){
//        isThrown = true;
//        Player p = ev.getPlayer();
//        ItemStack item = new ItemStack(Material.CREEPER_SPAWN_EGG);
//        ev.getItemDrop().remove();
//        p.setItemInHand(item);
//    }

//    @EventHandler
//    public void onClick(InventoryMoveItemEvent e) {
//        ItemStack item = new ItemStack(Material.CREEPER_SPAWN_EGG);
//        if(e.getItem() == item){
//            e.setCancelled(true);
//        }
//    }
}


