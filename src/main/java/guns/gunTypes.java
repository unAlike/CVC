package guns;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum gunTypes {
    USP(5, 40, 42,17,83,52, 13, 13, new ItemStack(Material.WOODEN_PICKAXE, 13), 2f, 1, "mcgo.weapons.pistolshot", "USP", "銏",1,0.05f,300),
    HK(10, 40, 45,20,89,58, 10, 10, new ItemStack(Material.STONE_PICKAXE, 10), 2f, 1, "mcgo.weapons.pistolshot", "HK", "銟", 1,.1f,300),
    DEAGLE(8, 45, 51,33,123,100, 7, 7, new ItemStack(Material.GOLDEN_PICKAXE, 7), 2f, 1, "mcgo.weapons.magnumshot", "Desert Eagle", "銎",1,.1f,300),
    //
    AWP(20, 60, 125,95,1337,1337, 10, 10, new ItemStack(Material.NETHERITE_SWORD, 10), 10, 15, "mcgo.weapons.snipershot", "50 Cal", "銄銅",0,.01f,300),
    P90(2, 50, 25,18,47,35, 35, 35, new ItemStack(Material.GOLDEN_SHOVEL, 35), 3, 2, "mcgo.weapons.smgshot", "P90", "銔銕",0,.1f,300),
    MP5(1, 40, 12,8,40,28, 40, 40, new ItemStack(Material.STONE_SHOVEL, 40), 5, 7, "mcgo.weapons.smgshot", "MP5", "銍",0,.1f,600),
    AK(4, 40, 45,38,123,88, 30, 30, new ItemStack(Material.STONE_HOE, 30), 5, 4, "mcgo.weapons.akshot", "AK-47", "銆銇",0,.1f,300),
    M4(2, 50, 34, 23,105,64,30, 30, new ItemStack(Material.IRON_AXE, 30), 4, 2.5f, "mcgo.weapons.carbineshot", "M4", "銈銉",0,.1f,300),
    AUG(2, 50, 37,30,115,80, 30, 30, new ItemStack(Material.GOLDEN_AXE, 30), 8, 12, "mcgo.weapons.akshot", "AUG", "銘銙",0,.1f,300),
    FAMAS(5,40,30,20,90,60,30,30, new ItemStack(Material.NETHERITE_SHOVEL, 30),5,2, "mcgo.weapons.carbineshot", "FAMAS", "銈銉", 0, .1f,300),
    PUMP(20, 40, 30,15,60,40, 8, 8, new ItemStack(Material.DIAMOND_SHOVEL, 8), 0, 10, "mcgo.weapons.shotgunshot", "Pump", "銊銋", 0,.1f,900),
    SPAS(10, 40, 30,15,60,40, 10, 10, new ItemStack(Material.WOODEN_AXE, 10), 0, 15, "mcgo.weapons.shotgunshot", "Spas", "銜銝",0,.1f,600);

    private gun g;
    gunTypes(int fireRate, float reloadTime, float damageBody, float damageBodyArmor, float damageHead, float damageHeadAmor, int maxAmmo, int ammo, ItemStack item, float maxRecoil, float spread, String soundEffect, String name, String gunImg, int slot, float fallOffAmount, int m){
        this.g = new gun(fireRate, reloadTime, damageBody, damageBodyArmor, damageHead, damageHeadAmor, maxAmmo, ammo, item, maxRecoil, spread, soundEffect, name, gunImg, slot, fallOffAmount, m);
    }
    public gun getGun() {
        try {
            return (gun) g.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
