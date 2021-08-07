package events.player;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class playerDropItem implements Listener {
    @EventHandler
    public void playerDropItems(PlayerDropItemEvent e){
        mcgoPlayer mc = Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString());
        switch(e.getItemDrop().getItemStack().getType()){
            default:
                e.setCancelled(true);
                break;
            case WOODEN_PICKAXE: case GOLDEN_PICKAXE: case STONE_PICKAXE:
                e.getItemDrop().getItemStack().setAmount(e.getPlayer().getInventory().getItem(1).getAmount()+1);
                Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setOffhand(null);
                e.getPlayer().getInventory().setItem(1,new ItemStack(Material.AIR));
                break;
            case STONE_SHOVEL: case GOLDEN_SHOVEL: case IRON_AXE: case NETHERITE_SWORD: case GOLDEN_AXE: case STONE_HOE: case DIAMOND_SHOVEL: case WOODEN_AXE: case NETHERITE_SHOVEL:
                e.getItemDrop().getItemStack().setAmount(e.getPlayer().getInventory().getItem(0).getAmount()+1);
                mc.getMain().setIsReloading(false);
                Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).setMain(null);
                e.getPlayer().getInventory().setItem(0,new ItemStack(Material.AIR));
                break;
            case QUARTZ:
                Artid.games.get(mc.gameUUID).playerWithBomb=null;
                for(mcgoPlayer m : Artid.games.get(mc.gameUUID).crimPlayers){
                    m.player.playSound(m.player.getLocation(),"mcgo.gamesounds.bombdroppedyourteam", 1,1);
                }
                for(mcgoPlayer m : Artid.games.get(mc.gameUUID).copPlayers){
                    m.player.playSound(m.player.getLocation(),"mcgo.gamesounds.bombdroppedenemyteam", 1,1);
                }
                break;
        }
    }
}
