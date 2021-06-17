package events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class PlayerOpenInventory implements Listener {
    @EventHandler
    public void openInv(InventoryOpenEvent e){
        switch(e.getView().getTitle()){
            default:
                e.setCancelled(true);
                break;
            case "Weapon Selector":
                break;
        }

    }
}
