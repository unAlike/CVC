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
            p.player.getInventory().setItem(0, e.getCurrentItem());

            switch (e.getCurrentItem().getType()) {
                //////////////////////////////////////MAIN GUNS////////////////////////////
                case NETHERITE_SWORD:
                    p.setMain(new gun(20, 125, 10, 10, new ItemStack(Material.NETHERITE_SWORD,10), 10, 5, "snipershot01", "50 Cal", p.player));
                    p.giveMainGun();
                    break;
                case GOLDEN_SHOVEL:

                    break;
                case STONE_SHOVEL:

                    break;
                case STONE_HOE:

                    break;
                case IRON_AXE:

                    break;
                case GOLDEN_AXE:

                    break;
                case DIAMOND_SHOVEL:

                    break;
                case WOODEN_AXE:

                    break;
                    /////////////////////////////////PISTOLS/////////////////////////////
                case WOODEN_PICKAXE:
                case STONE_PICKAXE:
                case GOLDEN_PICKAXE:
                    e.getWhoClicked().getInventory().setItem(1, e.getCurrentItem());
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
