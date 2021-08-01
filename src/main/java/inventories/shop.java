package inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class shop {
    private final Inventory inv;
    public shop(){
        //Setup Weapon Select
        this.inv = Bukkit.createInventory(null, 36, "Weapon Selector");
        inv.setItem(1, customItem(Material.WOODEN_PICKAXE, "USP", lore(2,3,7,5,2)));
        inv.setItem(2, customItem(Material.STONE_PICKAXE, "HK45", lore(2,3,7,4,3)));
        inv.setItem(3, customItem(Material.GOLDEN_PICKAXE, "Desert Eagle", lore(5,2,5,3,3)));
        inv.setItem(4, customItem(Material.NETHERITE_SWORD, "50 Cal", lore(7,1,6,7,7)));
        inv.setItem(5, customItem(Material.GOLDEN_SHOVEL, "P90", lore(2,6,5,3,3)));
        inv.setItem(6, customItem(Material.STONE_SHOVEL, "MP5", lore(1,7,5,2,2)));
        inv.setItem(7, customItem(Material.STONE_HOE, "AK47", lore(4,5,4,6,5)));
        inv.setItem(11, customItem(Material.IRON_AXE, "M4", lore(3,6,6,5,4)));
        inv.setItem(12, customItem(Material.GOLDEN_AXE, "AUG", lore(4,2,4,5,5)));
        inv.setItem(13, customItem(Material.NETHERITE_SHOVEL, "FAMAS", lore(20,3,7,6,5)));
        inv.setItem(14, customItem(Material.DIAMOND_SHOVEL, "Pump Action", lore(6,1,6,1,1)));
        inv.setItem(15, customItem(Material.WOODEN_AXE, "SPAS-12", lore(5,2,6,2,1)));

        inv.setItem(29, customItem(Material.ACACIA_SAPLING, "Firebomb"));
        inv.setItem(30, customItem(Material.OAK_SAPLING, "Frag Grenade"));
        inv.setItem(31, customItem(Material.JUNGLE_SAPLING, "Decoy Grenade"));
        inv.setItem(32, customItem(Material.DARK_OAK_SAPLING, "Smoke Grenade"));
        inv.setItem(33, customItem(Material.BIRCH_SAPLING, "Flash Grenade"));
        inv.setItem(22, customItem(Material.BONE, "Knife"));

    }
    //Custom Item
    public static ItemStack customItem(Material m, String name, ArrayList<String> lore){
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack customItem(Material m, String name){
        ItemStack item = new ItemStack(m);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + name);
        item.setItemMeta(meta);
        return item;
    }
    public Inventory getInv(){
        return inv;
    }
    public ArrayList<String> lore(int damage, int firerate, int stab, int acc, int range){
        ArrayList<String> arr = new ArrayList<String>();
        String edit = "";

        //DMG
        for(int i=0;i<7;i++){
            if(damage>i) edit = edit + "█";
            else edit = edit + "▓";
        }
        arr.add(ChatColor.DARK_GRAY + edit + ChatColor.GRAY + " Damage");
        edit = "";

        //FIRERATE
        for(int i=0;i<7;i++){
            if(firerate>i) edit = edit + "█";
            else edit = edit + "▓";
        }
        arr.add(ChatColor.DARK_GRAY + edit + ChatColor.GRAY + " Firerate");
        edit = "";

        //STABILITY
        for(int i=0;i<7;i++){
            if(stab>i) edit = edit + "█";
            else edit = edit + "▓";
        }
        arr.add(ChatColor.DARK_GRAY + edit + ChatColor.GRAY + " Stability");
        edit = "";

        //ACCURACY
        for(int i=0;i<7;i++){
            if(acc>i) edit = edit + "█";
            else edit = edit + "▓";
        }
        arr.add(ChatColor.DARK_GRAY + edit + ChatColor.GRAY + " Accuracy");
        edit = "";

        //RANGE
        for(int i=0;i<7;i++){
            if(range>i) edit = edit + "█";
            else edit = edit + "▓";
        }
        arr.add(ChatColor.DARK_GRAY + edit + ChatColor.GRAY + " Range");
        return arr;
    }
}
