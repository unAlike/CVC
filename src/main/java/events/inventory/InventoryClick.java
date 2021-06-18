package events.inventory;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import guns.gun;
import inventories.shop;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {
    @EventHandler
    public void changeItem(InventoryClickEvent e){
        mcgoPlayer p = Artid.mcPlayers.get(e.getWhoClicked().getUniqueId().toString());
        if(e.getWhoClicked().getGameMode()!= GameMode.CREATIVE){
            e.setCancelled(true);
        }
        if(e.getView().getTitle().equals("Weapon Selector")) {
            e.setCancelled(true);
            if(!e.getCurrentItem().getType().equals(null)) {
                switch (e.getCurrentItem().getType()) {
                    //////////////////////////////////////MAIN GUNS////////////////////////////
                    case NETHERITE_SWORD:
                        p.setMain(new gun(20, 60, 125, 10, 10, new ItemStack(Material.NETHERITE_SWORD, 10), 10, 15, "mcgo.weapons.snipershot", "50 Cal", "銄銅",p.player,0,.01f));
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case GOLDEN_SHOVEL:
                        p.setMain(new gun(2, 50, 20, 35, 35, new ItemStack(Material.GOLDEN_SHOVEL, 35), 3, 2, "mcgo.weapons.smgshot", "P90", "銔銕",p.player,0,.1f));
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case STONE_SHOVEL:
                        p.setMain(new gun(1, 40, 10, 40, 40, new ItemStack(Material.STONE_SHOVEL, 40), 5, 7, "mcgo.weapons.smgshot", "MP5", "銍",p.player,0,.1f));
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case STONE_HOE:
                        p.setMain(new gun(3, 40, 40, 30, 30, new ItemStack(Material.STONE_HOE, 30), 5, 4, "mcgo.weapons.akshot", "AK-47", "銆銇",p.player,0,.1f));
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case IRON_AXE:
                        p.setMain(new gun(2, 50, 36, 30, 30, new ItemStack(Material.IRON_AXE, 30), 4, 2.5f, "mcgo.weapons.carbineshot", "M4", "銈銉",p.player,0,.1f));
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case GOLDEN_AXE:
                        p.setMain(new gun(2, 50, 30, 30, 30, new ItemStack(Material.GOLDEN_AXE, 30), 8, 12, "mcgo.weapons.akshot", "AUG", "銘銙",p.player,0,.1f));
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case DIAMOND_SHOVEL:
                        p.setMain(new gun(20, 40, 30, 8, 8, new ItemStack(Material.DIAMOND_SHOVEL, 8), 0, 10, "mcgo.weapons.shotgunshot", "Pump", "銊銋", p.player,0,.1f));
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case WOODEN_AXE:
                        p.setMain(new gun(10, 40, 30, 10, 10, new ItemStack(Material.WOODEN_AXE, 10), 0, 15, "mcgo.weapons.shotgunshot", "Spas", "銜銝",p.player,0,.1f));
                        p.giveMainGun();
                        e.setCancelled(true);
                    /////////////////////////////////PISTOLS/////////////////////////////
                    case WOODEN_PICKAXE:
                        p.setOffhand(new gun(6, 30, 20, 13, 13, new ItemStack(Material.WOODEN_PICKAXE, 13), 2f, 1, "mcgo.weapons.pistolshot", "USP", "銏", p.player,1,0.05f));
                        p.giveOffhandGun();
                        e.setCancelled(true);
                        break;
                    case STONE_PICKAXE:
                        p.setOffhand(new gun(12, 30, 20, 10, 10, new ItemStack(Material.STONE_PICKAXE, 10), 2f, 1, "mcgo.weapons.pistolshot", "HK", "銟", p.player,1,.1f));
                        p.giveOffhandGun();
                        e.setCancelled(true);
                        break;
                    case GOLDEN_PICKAXE:
                        p.setOffhand(new gun(10, 40, 40, 7, 7, new ItemStack(Material.GOLDEN_PICKAXE, 7), 2f, 1, "mcgo.weapons.magnumshot", "Desert Eagle", "銎", p.player,1,.1f));
                        p.giveOffhandGun();
                        e.setCancelled(true);
                        break;
                    ////////////////////////////DMG NADES////////////////////////////////
                    case ACACIA_SAPLING:
                    case OAK_SAPLING:
                        e.getWhoClicked().getInventory().setItem(3, e.getCurrentItem());
                        e.setCancelled(true);
                        break;
                    //////////////////////////////////UTIL NADES///////////////////////////
                    case JUNGLE_SAPLING:
                    case DARK_OAK_SAPLING:
                    case BIRCH_SAPLING:
                        e.getWhoClicked().getInventory().setItem(4, e.getCurrentItem());
                        e.setCancelled(true);
                        break;
                    case BONE:
                        e.getWhoClicked().getInventory().setItem(2, e.getCurrentItem());
                        e.setCancelled(true);
                        break;
                }
            }
        }
    }
}
