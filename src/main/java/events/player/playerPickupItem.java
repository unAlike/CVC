package events.player;

import groupid.artid.Artid;
import guns.gun;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class playerPickupItem implements Listener {
    @EventHandler
    public void pickupItem(PlayerPickupItemEvent e) {
        if(e.getPlayer().getInventory().getItem(0)!=null){
            e.setCancelled(true);
        }

        if (e.getPlayer().getInventory().getItem(1) == null) {
            switch (e.getItem().getItemStack().getType()) {
                default:
                    e.setCancelled(true);
                    break;
                case WOODEN_PICKAXE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(1, e.getItem().getItemStack());
                    e.getItem().remove();
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setOffhand(new gun(6, 30, 20, 13, e.getItem().getItemStack().getAmount(), new ItemStack(Material.WOODEN_PICKAXE, 13), 2.5f, 1, "mcgo.weapons.pistolshot", "USP", "銏",e.getPlayer(),1,.2f));
                    break;
                case STONE_PICKAXE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(1, e.getItem().getItemStack());
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setOffhand(new gun(12, 30, 25, 10, e.getItem().getItemStack().getAmount(), new ItemStack(Material.STONE_PICKAXE, 10), 2f, 1, "mcgo.weapons.pistolshot", "HK", "銞", e.getPlayer(),1,.1f));
                    e.getItem().remove();
                    break;
                case GOLDEN_PICKAXE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(1, e.getItem().getItemStack());
                    e.getItem().remove();
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setOffhand(new gun(10, 40, 40, 7, e.getItem().getItemStack().getAmount(), new ItemStack(Material.GOLDEN_PICKAXE, 7), 2.5f, 1, "mcgo.weapons.magnumshot", "Deagle", "鉡",e.getPlayer(),1,1f));
                    break;

            }
        }
        if(e.getPlayer().getInventory().getItem(0) == null){
            switch (e.getItem().getItemStack().getType()) {
                default:
                    e.setCancelled(true);
                    break;
                    //50 Cal
                case NETHERITE_SWORD:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setMain(new gun(20, 60, 125, 10, e.getItem().getItemStack().getAmount(), new ItemStack(Material.NETHERITE_SWORD, 10), 10, 15, "mcgo.weapons.snipershot", "50 Cal", "銄銅",e.getPlayer(),0,.01f));
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                    //P90
                case GOLDEN_SHOVEL:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setMain(new gun(2, 50, 20, 35, e.getItem().getItemStack().getAmount(), new ItemStack(Material.GOLDEN_SHOVEL, 35), 3, 2, "mcgo.weapons.smgshot", "P90", "銒銓", e.getPlayer(),0,2));
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                    //MPP%
                case STONE_SHOVEL:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setMain(new gun(1, 40, 10, 40, e.getItem().getItemStack().getAmount(), new ItemStack(Material.STONE_SHOVEL, 40), 5, 7, "mcgo.weapons.smgshot", "MP5", "鉢",e.getPlayer(),0,2));
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                case STONE_HOE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setMain(new gun(3, 40, 40, 30, e.getItem().getItemStack().getAmount(), new ItemStack(Material.STONE_HOE, 30), 5, 4, "mcgo.weapons.akshot", "AK-47", "銆銇",e.getPlayer(),0,.1f));
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                case IRON_AXE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setMain(new gun(2, 50, 36, 30, e.getItem().getItemStack().getAmount(), new ItemStack(Material.IRON_AXE, 30), 3, 2.5f, "mcgo.weapons.carbineshot", "M4", "銈銉",e.getPlayer(),0,2));
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                case GOLDEN_AXE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setMain(new gun(2, 50, 30, 30, e.getItem().getItemStack().getAmount(), new ItemStack(Material.GOLDEN_AXE, 30), 8, 12, "mcgo.weapons.akshot", "AUG", "銘銙",e.getPlayer(),0,1));
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                case DIAMOND_SHOVEL:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setMain(new gun(20, 40, 30, 8, 8, new ItemStack(Material.DIAMOND_SHOVEL, 8), 0, 10, "mcgo.weapons.shotgunshot", "Pump", "銊銋", e.getPlayer(),0,1));
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                case WOODEN_AXE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setMain(new gun(10, 40, 30, 10, 10, new ItemStack(Material.WOODEN_AXE, 10), 0, 15, "mcgo.weapons.shotgunshot", "Spas", "銜銝",e.getPlayer(),0,2));
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
            }
        }
    }
}
