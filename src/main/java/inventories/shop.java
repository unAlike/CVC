package inventories;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class shop {
    private final Inventory inv;
    public shop(){
        this.inv = Bukkit.createInventory(null, 36, "Weapon Selector");
        inv.setItem(1, new ItemStack(Material.WOODEN_PICKAXE));
        inv.setItem(2, new ItemStack(Material.STONE_PICKAXE));
        inv.setItem(3, new ItemStack(Material.GOLDEN_PICKAXE));
        inv.setItem(4, new ItemStack(Material.NETHERITE_SWORD));
        inv.setItem(5, new ItemStack(Material.GOLDEN_SHOVEL));
        inv.setItem(6, new ItemStack(Material.STONE_SHOVEL));
        inv.setItem(7, new ItemStack(Material.STONE_HOE));
        inv.setItem(11, new ItemStack(Material.IRON_AXE));
        inv.setItem(12, new ItemStack(Material.GOLDEN_AXE));
        inv.setItem(14, new ItemStack(Material.DIAMOND_SHOVEL));
        inv.setItem(15, new ItemStack(Material.WOODEN_AXE));
        inv.setItem(29, new ItemStack(Material.ACACIA_SAPLING));
        inv.setItem(30, new ItemStack(Material.OAK_SAPLING));
        inv.setItem(31, new ItemStack(Material.JUNGLE_SAPLING));
        inv.setItem(32, new ItemStack(Material.DARK_OAK_SAPLING));
        inv.setItem(33, new ItemStack(Material.BIRCH_SAPLING));

    }
    public Inventory getInv(){
        return inv;
    }
}
