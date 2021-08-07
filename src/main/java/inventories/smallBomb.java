package inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.security.SecureRandom;

public class smallBomb {
    private Inventory inv;
    public smallBomb(){
        inv = Bukkit.createInventory(null,27,"Cut all the red wires ");
        populate();
    }
    public Inventory getInv(){return inv;}
    public void populate(){
        SecureRandom rand = new SecureRandom();
        for(int i = 0; i<inv.getSize();i++){
            inv.setItem(i,randomItem(rand.nextInt(4)));
        }
    }
    public ItemStack randomItem(int i){
        switch(i){
            default:
                return new ItemStack(Material.AIR);
            case 0:
                return new ItemStack(Material.RED_DYE);
            case 1:
                return new ItemStack(Material.BLUE_DYE);
            case 2:
                return new ItemStack(Material.YELLOW_DYE);
            case 3:
                return new ItemStack(Material.PURPLE_DYE);
            case 4:
                return new ItemStack(Material.GREEN_DYE);
        }
    }
}
