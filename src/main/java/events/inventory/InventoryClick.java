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

            if(e.getCurrentItem().getType()!=null) {
                switch (e.getCurrentItem().getType()) {
                    //////////////////////////////////////MAIN GUNS////////////////////////////
                    case NETHERITE_SWORD:
                        p.setMain(new gun(20, 125, 10, 10, new ItemStack(Material.NETHERITE_SWORD, 10), 10, 5, "snipershot", "50 Cal", p.player));
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case GOLDEN_SHOVEL:
                        p.player.sendMessage("Not Implemented yet");
                        e.setCancelled(true);
                        break;
                    case STONE_SHOVEL:
                        p.setMain(new gun(1, 10, 40, 40, new ItemStack(Material.STONE_SHOVEL, 40), 5, 7, "mcgo.weapons.smgshot", "MP5", p.player));
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case STONE_HOE:
                        p.player.sendMessage("Not Implemented yet");
                        e.setCancelled(true);
                        break;
                    case IRON_AXE:
                        p.setMain(new gun(2, 36, 30, 30, new ItemStack(Material.IRON_AXE, 30), 10, 5, "mcgo.weapons.carbineshot", "M4", p.player));
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case GOLDEN_AXE:
                        p.player.sendMessage("Not Implemented yet");
                        e.setCancelled(true);
                        break;
                    case DIAMOND_SHOVEL:
                        p.player.sendMessage("Not Implemented yet");
                        e.setCancelled(true);
                        break;
                    case WOODEN_AXE:
                        p.player.sendMessage("Not Implemented yet");
                        e.setCancelled(true);
                        break;
                    /////////////////////////////////PISTOLS/////////////////////////////
                    case WOODEN_PICKAXE:
                        p.setOffhand(new gun(6, 20, 13, 13, new ItemStack(Material.WOODEN_PICKAXE, 13), 5, 1, "mcgo.weapons.pistolshot", "USP", p.player));
                        p.giveOffhandGun();
                        e.setCancelled(true);
                        break;
                    case STONE_PICKAXE:
                        p.player.sendMessage("Not Implemented yet");
                        e.setCancelled(true);
                        break;
                    case GOLDEN_PICKAXE:
                        p.player.sendMessage("Not Implemented yet");
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
                }
            }
        }
    }
}
