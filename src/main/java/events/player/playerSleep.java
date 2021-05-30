package events.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

public class playerSleep implements Listener {
    @EventHandler
    void sleepEnter(PlayerBedEnterEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void sleepLeave(PlayerBedLeaveEvent e){
        e.setSpawnLocation(false);
    }

}
