package com.plugin.Handlers;

import com.plugin.QuarkTestPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class CreeperHeadHandler implements Listener {
    Player p;

    double yaw;
    double pitch;
    double x;
    double y;

    boolean isFlyingByAbility;

    public CreeperHeadHandler(QuarkTestPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHeadUse(PlayerInteractEvent e) {
        p = e.getPlayer();

        yaw = ((p.getLocation().getYaw() + 90)  * Math.PI) / 180;
        pitch = ((p.getLocation().getPitch() + 90) * Math.PI) / 180;

        x = Math.sin(pitch) * Math.cos(yaw);
        y = Math.sin(pitch) * Math.sin(yaw);

        World world = p.getWorld();
        Location loc = p.getLocation();
        if(p.getItemInHand().getType() == Material.CREEPER_SPAWN_EGG) {
            world.createExplosion(loc, 2.3F, false, true);
            isFlyingByAbility = true;
        }
    }

  @EventHandler
    public void onEntityDamage(EntityDamageEvent e) throws InterruptedException {
       if(e.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) ) {
           if(p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.AIR || p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.WATER) {
               e.setCancelled(true);
               e.getEntity().setVelocity(new Vector(x,1.35,y));  // z=1.35
           } else {
               e.getEntity().setVelocity(new Vector(0,-0.1,0));
               e.setCancelled(true);
           }
       }

       if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL) && isFlyingByAbility == true) {
           e.setCancelled(true);
           e.wait(500);
           isFlyingByAbility = false;
       }
    }
}


