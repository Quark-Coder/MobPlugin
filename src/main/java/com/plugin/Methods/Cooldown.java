package com.plugin.Methods;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Cooldown {
    public void creeperCooldown(Player p){
        p.setCooldown(Material.CREEPER_SPAWN_EGG, 200);
//        HashMap<String, Long> startTimes = new HashMap<String, Long>;
//
//
//        long start = startTimes.get(player.getName()),
//                end = start + duration;
//        if(end < System.currentTimeMillis()) {
//
//            startTimes.put(player.getName(), System.currentTimeMillis());
//        } else {
//            long timeToWait = end - System.currentTimeMillis();
//
//        }
    }
}
