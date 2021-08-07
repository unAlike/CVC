package events.player;

import inventories.bigBomb;
import inventories.smallBomb;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class PlayerOpenInventory implements Listener {
    @EventHandler
    public void openInv(InventoryOpenEvent e){
        bigBomb bb = new bigBomb();
        smallBomb ss = new smallBomb();
        switch(e.getView().getTitle()){
            default:
                e.setCancelled(true);
                break;
            case "Chest":
                e.setCancelled(true);
                if(e.getPlayer().getInventory().getItemInMainHand().getType()== Material.GOLD_NUGGET) {
                    e.getPlayer().openInventory(bb.getInv());
                }
                else if(e.getPlayer().getInventory().getItemInMainHand().getType()== Material.SHEARS){
                    e.getPlayer().openInventory(ss.getInv());
                }
                break;
            case "Weapon Selector":
            case "Shop":
            case "Cut all the red wires":
            case "Cut all the red wires ":
                break;

        }

    }
}
