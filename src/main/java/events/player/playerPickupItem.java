package events.player;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import guns.gun;
import guns.gunTypes;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class playerPickupItem implements Listener {
    @EventHandler
    public void pickupItem(PlayerPickupItemEvent e) throws CloneNotSupportedException {
        mcgoPlayer mc = Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString());
        if(e.getItem().getItemStack().getType()==Material.QUARTZ){
            e.setCancelled(true);
            e.getItem().remove();
            e.getPlayer().getInventory().setItem(6,new ItemStack(Material.QUARTZ));
            Artid.games.get(mc.gameUUID).playerWithBomb = mc;
            mc.player.playSound(mc.player.getLocation(),"mcgo.gamesounds.pickedupthebomb",1,1);
        }
        if(e.getPlayer().getInventory().getItem(0)!=null){
            e.setCancelled(true);
        }
        if(e.getPlayer().getInventory().getItem(1)!=null){
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
                    mc.setOffhand((gun) gunTypes.USP.getGun().clone());
                    mc.giveOffhandGun();
                    break;
                case STONE_PICKAXE:
                    e.setCancelled(true);
                    e.getItem().remove();
                    e.getPlayer().getInventory().setItem(1, e.getItem().getItemStack());
                    mc.setOffhand((gun) gunTypes.HK.getGun().clone());
                    mc.giveOffhandGun();
                    break;
                case GOLDEN_PICKAXE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(1, e.getItem().getItemStack());
                    e.getItem().remove();
                    mc.setOffhand((gun) gunTypes.DEAGLE.getGun().clone());
                    mc.giveOffhandGun();
                    break;
            }
            if(mc.getOffhand()!=null) mc.getOffhand().setIsReloading(false);
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
                    mc.setMain((gun) gunTypes.AWP.getGun().clone());
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                //P90
                case GOLDEN_SHOVEL:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    mc.setMain((gun) gunTypes.P90.getGun().clone());
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                //MPP%
                case STONE_SHOVEL:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    mc.setMain((gun) gunTypes.MP5.getGun().clone());
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                //Ak47
                case STONE_HOE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    mc.setMain((gun) gunTypes.AK.getGun().clone());
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                //M4
                case IRON_AXE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    mc.setMain((gun) gunTypes.M4.getGun().clone());
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                //AUG
                case GOLDEN_AXE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    mc.setMain((gun) gunTypes.AUG.getGun().clone());
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                //PUMP
                case DIAMOND_SHOVEL:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    mc.setMain((gun) gunTypes.PUMP.getGun().clone());
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                //Spas
                case WOODEN_AXE:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    mc.setMain((gun) gunTypes.SPAS.getGun().clone());
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
                //Famas
                case NETHERITE_SHOVEL:
                    e.setCancelled(true);
                    e.getPlayer().getInventory().setItem(0, e.getItem().getItemStack());
                    e.getItem().remove();
                    mc.setMain((gun) gunTypes.FAMAS.getGun().clone());
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).giveMainGun();
                    break;
            }
            if(mc.getMain().getItem().getAmount()-1<=0 && mc.getMain()!=null) mc.getMain().setIsReloading(true);
            else mc.getMain().setIsReloading(false);
        }
    }
}