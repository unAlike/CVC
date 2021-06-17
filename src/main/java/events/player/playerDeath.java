package events.player;

import groupid.artid.Artid;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class playerDeath implements Listener {
    @EventHandler
    public void death(PlayerDeathEvent e){
    }
}
