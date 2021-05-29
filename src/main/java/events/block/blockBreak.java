package events.block;

import groupid.artid.Artid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class blockBreak implements Listener {
    @EventHandler
    public void blockBreak(BlockBreakEvent e){
        //e.setCancelled(true);
    }
}
