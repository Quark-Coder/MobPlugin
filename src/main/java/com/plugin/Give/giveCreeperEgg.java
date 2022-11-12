package com.plugin.Give;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class giveCreeperEgg {
    ItemStack creeperEgg = new ItemStack(Material.CREEPER_SPAWN_EGG, 1);
    public giveCreeperEgg(){
        ItemMeta creeperEggMeta = creeperEgg.getItemMeta();
        creeperEggMeta.setDisplayName("Creeper");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Allows you to get power of a CREEPER!");
        creeperEggMeta.setLore(lore);
        creeperEgg.setItemMeta(creeperEggMeta);
    }

    public ItemStack ItemStackCreeperEgg(ItemStack creeperEgg){
        this.creeperEgg = creeperEgg;
        return creeperEgg;
    }
}
