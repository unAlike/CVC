package events.player;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class playerLeave implements Listener{
    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent e){
        if(Artid.mcPlayers.containsKey(e.getPlayer().getUniqueId().toString())){
            Artid.mcPlayers.remove(e.getPlayer().getUniqueId().toString());
        }
        Bukkit.getConsoleSender().sendMessage(""+ Artid.mcPlayers);

    }
}