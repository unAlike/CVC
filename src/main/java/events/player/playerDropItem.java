package events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class playerDropItem implements Listener {
    @EventHandler
    public void playerDropItems(PlayerDropItemEvent e){
        switch(e.getItemDrop().getItemStack().getType()){
            case GHAST_TEAR:
                e.setCancelled(true);
                break;
            case WOODEN_PICKAXE:
                e.setCancelled(true);
                e.getPlayer().getInventory().getItemInMainHand().setAmount(0);
        }
    }
}
