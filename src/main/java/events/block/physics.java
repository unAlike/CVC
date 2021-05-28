package events.block;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class physics implements Listener {
    @EventHandler
    public void physics(BlockPhysicsEvent e){
        if(e.getSourceBlock().getType().equals(Material.WHEAT)){
            e.setCancelled(true);
        }
    }
}
