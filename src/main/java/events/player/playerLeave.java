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
        mcgoPlayer mc = Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString());
        if(Artid.mcPlayers.containsKey(e.getPlayer().getUniqueId().toString())){
            if(mc.hasGameId()){
                Artid.games.get(mc.gameUUID).removePlayer(mc);
            }
            Artid.mcPlayers.remove(e.getPlayer().getUniqueId().toString());
        }
        Bukkit.getConsoleSender().sendMessage(""+ Artid.mcPlayers);

    }
}