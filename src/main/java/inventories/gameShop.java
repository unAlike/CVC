package inventories;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class gameShop {
    private final Inventory inv;
    private mcgoPlayer p;
    public gameShop(){
        //Setup Weapon Select
        this.inv = Bukkit.createInventory(null, 45, "Shop");
        inv.setItem(2, customItem(Material.WOODEN_PICKAXE, "USP", lore(2,3,7,5,2,"Secondary",200)));
        inv.setItem(3, customItem(Material.STONE_PICKAXE, "HK45", lore(2,3,7,4,3, "Secondary", 500)));
        inv.setItem(4, customItem(Material.GOLDEN_PICKAXE, "Desert Eagle", lore(5,2,5,3,3,"Secondary", 700)));
        inv.setItem(5, customItem(Material.DIAMOND_SHOVEL, "Pump Action", lore(6,1,6,1,1, "Primary", 1200)));
        inv.setItem(6, customItem(Material.WOODEN_AXE, "SPAS-12", lore(5,2,6,2,1, "Primary", 2000)));

        inv.setItem(11, customItem(Material.STONE_SHOVEL, "MP5", lore(1,7,5,2,2, "Primary", 1700)));
        inv.setItem(12, customItem(Material.GOLDEN_SHOVEL, "P90", lore(2,6,5,3,3, "Primary", 2250)));
        inv.setItem(13, customItem(Material.STONE_HOE, "AK47", lore(4,5,4,6,5, "Primary", 2900)));
        inv.setItem(14, customItem(Material.IRON_AXE, "M4", lore(3,6,6,5,4, "Primary", 3100)));
        inv.setItem(15, customItem(Material.GOLDEN_AXE, "AUG", lore(4,2,4,5,5, "Primary", 3000)));
        //inv.setItem(13, customItem(Material.NETHERITE_SHOVEL, "FAMAS", lore(20,3,7,6,5)));
        inv.setItem(22, customItem(Material.NETHERITE_SWORD, "50 Cal", lore(7,1,6,7,7, "Primary", 2450)));
        ArrayList<String> cost = new ArrayList<>();
        cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$200");
        inv.setItem(27, customItem(Material.ACACIA_SAPLING, "Firebomb",cost));
        inv.setItem(28, customItem(Material.OAK_SAPLING, "Frag Grenade",cost));
        //Armor
        cost.clear();
        cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$350");
        inv.setItem(34, customItem(Material.DIAMOND_HELMET, "Head Armor", cost));
        cost.clear();
        cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$450");
        inv.setItem(35, customItem(Material.DIAMOND_CHESTPLATE, "Body Armor", cost));
        cost.clear();
        cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$350");
        inv.setItem(36, customItem(Material.JUNGLE_SAPLING, "Decoy Grenade", cost));
        cost.clear();
        cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$350");
        inv.setItem(37, customItem(Material.DARK_OAK_SAPLING, "Smoke Grenade", cost));
        cost.clear();
        cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$300");
        inv.setItem(38, customItem(Material.BIRCH_SAPLING, "Flash Grenade", cost));
    }
    public gameShop(mcgoPlayer p) {
        this.p =p;
        this.inv = Bukkit.createInventory(null, 45, "Shop");
        inv.setItem(2, customItem(Material.WOODEN_PICKAXE, "USP", lore(2,3,7,5,2,"Secondary",200)));
        inv.setItem(3, customItem(Material.STONE_PICKAXE, "HK45", lore(2,3,7,4,3, "Secondary", 500)));
        inv.setItem(4, customItem(Material.GOLDEN_PICKAXE, "Desert Eagle", lore(5,2,5,3,3,"Secondary", 700)));
        inv.setItem(5, customItem(Material.DIAMOND_SHOVEL, "Pump Action", lore(6,1,6,1,1, "Primary", 1200)));
        inv.setItem(6, customItem(Material.WOODEN_AXE, "SPAS-12", lore(5,2,6,2,1, "Primary", 2000)));

        inv.setItem(11, customItem(Material.STONE_SHOVEL, "MP5", lore(1,7,5,2,2, "Primary", 1700)));
        inv.setItem(12, customItem(Material.GOLDEN_SHOVEL, "P90", lore(2,6,5,3,3, "Primary", 2250)));
        inv.setItem(13, customItem(Material.STONE_HOE, "AK47", lore(4,5,4,6,5, "Primary", 2900)));
        inv.setItem(14, customItem(Material.IRON_AXE, "M4", lore(3,6,6,5,4, "Primary", 3100)));
        inv.setItem(15, customItem(Material.GOLDEN_AXE, "AUG", lore(4,2,4,5,5, "Primary", 3000)));
        //inv.setItem(13, customItem(Material.NETHERITE_SHOVEL, "FAMAS", lore(20,3,7,6,5)));
        inv.setItem(22, customItem(Material.NETHERITE_SWORD, "50 Cal", lore(7,1,6,7,7, "Primary", 2450)));
        ArrayList<String> cost = new ArrayList<>();
        if(p!=null && p.money>=600) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$600");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$600");
        inv.setItem(27, customItem(Material.ACACIA_SAPLING, "Firebomb",cost));
        cost.clear();

        if(p!=null && p.money>=300) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$300");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$300");
        inv.setItem(28, customItem(Material.OAK_SAPLING, "Frag Grenade",cost));
        //Armor
        cost.clear();
        if(p!=null && p.money>=350) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$350");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$350");
        inv.setItem(34, customItem(Material.DIAMOND_HELMET, "Head Armor", cost));
        cost.clear();

        if(p!=null && p.money>=550) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$550");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$550");
        inv.setItem(35, customItem(Material.DIAMOND_CHESTPLATE, "Body Armor", cost));
        cost.clear();

        if(p!=null && p.money>=100) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$100");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$100");
        inv.setItem(36, customItem(Material.JUNGLE_SAPLING, "Decoy Grenade", cost));
        cost.clear();

        if(p!=null && p.money>=300) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$300");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$300");
        inv.setItem(37, customItem(Material.DARK_OAK_SAPLING, "Smoke Grenade", cost));
        cost.clear();

        if(p!=null && p.money>=300) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$200");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$200");
        inv.setItem(38, customItem(Material.BIRCH_SAPLING, "Flash Grenade", cost));
        cost.clear();
        if(p!=null && p.hasGameId() && Artid.games.get(p.gameUUID).copPlayers.contains(p)){
            if(p!=null && p.money>=400) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$400");
            else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$400");
            inv.setItem(44, customItem(Material.SHEARS, "Defuse Kit", cost));
        }
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
    public Inventory getInv(){
        return inv;
    }
    public ArrayList<String> lore(int damage, int firerate, int stab, int acc, int range, String type, int cost){
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
        arr.add("");
        arr.add(ChatColor.GRAY + "Slot: " + ChatColor.WHITE + "" + type);
        arr.add("");
        if(p!=null) {
            if (p.money >= cost) {
                arr.add(ChatColor.GRAY + "Cost: " + ChatColor.GOLD + "$" + cost);
            } else {
                arr.add(ChatColor.GRAY + "Cost: " + ChatColor.RED + "$" + cost);
            }
        }
        else{
            arr.add(ChatColor.GRAY + "Cost: " + ChatColor.GOLD + "$" + cost);
        }
        return arr;
    }
    public void update(){
        inv.clear();
        inv.setItem(2, customItem(Material.WOODEN_PICKAXE, "USP", lore(2,3,7,5,2,"Secondary",200)));
        inv.setItem(3, customItem(Material.STONE_PICKAXE, "HK45", lore(2,3,7,4,3, "Secondary", 500)));
        inv.setItem(4, customItem(Material.GOLDEN_PICKAXE, "Desert Eagle", lore(5,2,5,3,3,"Secondary", 700)));
        inv.setItem(5, customItem(Material.DIAMOND_SHOVEL, "Pump Action", lore(6,1,6,1,1, "Primary", 1200)));
        inv.setItem(6, customItem(Material.WOODEN_AXE, "SPAS-12", lore(5,2,6,2,1, "Primary", 2000)));

        inv.setItem(11, customItem(Material.STONE_SHOVEL, "MP5", lore(1,7,5,2,2, "Primary", 1700)));
        inv.setItem(12, customItem(Material.GOLDEN_SHOVEL, "P90", lore(2,6,5,3,3, "Primary", 2250)));
        inv.setItem(13, customItem(Material.STONE_HOE, "AK47", lore(4,5,4,6,5, "Primary", 2900)));
        inv.setItem(14, customItem(Material.IRON_AXE, "M4", lore(3,6,6,5,4, "Primary", 3100)));
        inv.setItem(15, customItem(Material.GOLDEN_AXE, "AUG", lore(4,2,4,5,5, "Primary", 3000)));
        //inv.setItem(13, customItem(Material.NETHERITE_SHOVEL, "FAMAS", lore(20,3,7,6,5)));
        inv.setItem(22, customItem(Material.NETHERITE_SWORD, "50 Cal", lore(7,1,6,7,7, "Primary", 2450)));
        ArrayList<String> cost = new ArrayList<>();
        if(p!=null && p.money>=600) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$600");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$600");
        inv.setItem(27, customItem(Material.ACACIA_SAPLING, "Firebomb",cost));
        cost.clear();

        if(p!=null && p.money>=300) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$300");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$300");
        inv.setItem(28, customItem(Material.OAK_SAPLING, "Frag Grenade",cost));
        //Armor
        cost.clear();
        if(p!=null && p.money>=350) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$350");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$350");
        inv.setItem(34, customItem(Material.DIAMOND_HELMET, "Head Armor", cost));
        cost.clear();

        if(p!=null && p.money>=550) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$550");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$550");
        inv.setItem(35, customItem(Material.DIAMOND_CHESTPLATE, "Body Armor", cost));
        cost.clear();

        if(p!=null && p.money>=100) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$100");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$100");
        inv.setItem(36, customItem(Material.JUNGLE_SAPLING, "Decoy Grenade", cost));
        cost.clear();

        if(p!=null && p.money>=300) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$300");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$300");
        inv.setItem(37, customItem(Material.DARK_OAK_SAPLING, "Smoke Grenade", cost));
        cost.clear();

        if(p!=null && p.money>=300) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$200");
        else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$200");
        inv.setItem(38, customItem(Material.BIRCH_SAPLING, "Flash Grenade", cost));
        cost.clear();

        if(p!=null && p.hasGameId() && Artid.games.get(p.gameUUID).copPlayers.contains(p)){
            if(p!=null && p.money>=400) cost.add(ChatColor.GREEN + "Cost: " + ChatColor.GOLD + "$400");
            else cost.add(ChatColor.GREEN + "Cost: " + ChatColor.RED + "$400");
            inv.setItem(44, customItem(Material.SHEARS, "Defuse Kit", cost));
        }
    }
}
