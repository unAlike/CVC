package guns;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum gunTypes {
    USP(5, 40, 20, 13, 13, new ItemStack(Material.WOODEN_PICKAXE, 13), 2f, 1, "mcgo.weapons.pistolshot", "USP", "銏",1,0.05f),
    HK(10, 40, 20, 10, 10, new ItemStack(Material.STONE_PICKAXE, 10), 2f, 1, "mcgo.weapons.pistolshot", "HK", "銟", 1,.1f),
    DEAGLE(8, 45, 40, 7, 7, new ItemStack(Material.GOLDEN_PICKAXE, 7), 2f, 1, "mcgo.weapons.magnumshot", "Desert Eagle", "銎",1,.1f),
    //
    AWP(20, 60, 125, 10, 10, new ItemStack(Material.NETHERITE_SWORD, 10), 10, 15, "mcgo.weapons.snipershot", "50 Cal", "銄銅",0,.01f),
    P90(2, 50, 20, 35, 35, new ItemStack(Material.GOLDEN_SHOVEL, 35), 3, 2, "mcgo.weapons.smgshot", "P90", "銔銕",0,.1f),
    MP5(1, 40, 10, 40, 40, new ItemStack(Material.STONE_SHOVEL, 40), 5, 7, "mcgo.weapons.smgshot", "MP5", "銍",0,.1f),
    AK(4, 40, 40, 30, 30, new ItemStack(Material.STONE_HOE, 30), 5, 4, "mcgo.weapons.akshot", "AK-47", "銆銇",0,.1f),
    M4(2, 50, 36, 30, 30, new ItemStack(Material.IRON_AXE, 30), 4, 2.5f, "mcgo.weapons.carbineshot", "M4", "銈銉",0,.1f),
    AUG(2, 50, 30, 30, 30, new ItemStack(Material.GOLDEN_AXE, 30), 8, 12, "mcgo.weapons.akshot", "AUG", "銘銙",0,.1f),
    FAMAS(5,40,20,30,30, new ItemStack(Material.NETHERITE_SHOVEL, 30),5,2, "mcgo.weapons.carbineshot", "FAMAS", "銈銉", 0, .1f),
    PUMP(20, 40, 30, 8, 8, new ItemStack(Material.DIAMOND_SHOVEL, 8), 0, 10, "mcgo.weapons.shotgunshot", "Pump", "銊銋", 0,.1f),
    SPAS(10, 40, 30, 10, 10, new ItemStack(Material.WOODEN_AXE, 10), 0, 15, "mcgo.weapons.shotgunshot", "Spas", "銜銝",0,.1f);

    private gun g;
    gunTypes(int fireRate, float reloadTime, float damage, int maxAmmo, int ammo, ItemStack item, float maxRecoil, float spread, String soundEffect, String name, String gunImg, int slot, float fallOffAmount){
        this.g = new gun(fireRate, reloadTime, damage, maxAmmo, ammo, item, maxRecoil, spread, soundEffect, name, gunImg, slot, fallOffAmount);
    }
    public gun getGun() throws CloneNotSupportedException {
        return (gun) g.clone();
    }

}
