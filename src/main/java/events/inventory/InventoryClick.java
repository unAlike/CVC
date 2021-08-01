package events.inventory;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import guns.gun;
import guns.gunTypes;
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
    public void changeItem(InventoryClickEvent e) throws CloneNotSupportedException {
        mcgoPlayer p = Artid.mcPlayers.get(e.getWhoClicked().getUniqueId().toString());
        if(e.getWhoClicked().getGameMode()!= GameMode.CREATIVE){
            e.setCancelled(true);
        }
        if(e.getView().getTitle().equals("Weapon Selector")) {
            e.setCancelled(true);
            if(e.getCurrentItem().getType() != null) {
                switch (e.getCurrentItem().getType()) {
                    //////////////////////////////////////MAIN GUNS////////////////////////////
                    case NETHERITE_SWORD:
                        p.setMain((gun) gunTypes.AWP.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case GOLDEN_SHOVEL:
                        p.setMain((gun) gunTypes.P90.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case STONE_SHOVEL:
                        p.setMain((gun) gunTypes.MP5.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case STONE_HOE:
                        p.setMain((gun) gunTypes.AK.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case IRON_AXE:
                        p.setMain((gun) gunTypes.M4.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case GOLDEN_AXE:
                        p.setMain((gun) gunTypes.AUG.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case DIAMOND_SHOVEL:
                        p.setMain((gun) gunTypes.PUMP.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case WOODEN_AXE:
                        p.setMain((gun) gunTypes.SPAS.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case NETHERITE_SHOVEL:
                        p.setMain((gun) gunTypes.FAMAS.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                        /////////////////////////////////PISTOLS/////////////////////////////
                    case WOODEN_PICKAXE:
                        p.setOffhand((gun) gunTypes.USP.getGun().clone());
                        p.getOffhand().setPlayerUUID(p.player.getUniqueId());
                        p.giveOffhandGun();
                        e.setCancelled(true);
                        break;
                    case STONE_PICKAXE:
                        p.setOffhand((gun) gunTypes.HK.getGun().clone());
                        p.getOffhand().setPlayerUUID(p.player.getUniqueId());
                        p.giveOffhandGun();
                        e.setCancelled(true);
                        break;
                    case GOLDEN_PICKAXE:
                        p.setOffhand((gun) gunTypes.DEAGLE.getGun().clone());
                        p.getOffhand().setPlayerUUID(p.player.getUniqueId());
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