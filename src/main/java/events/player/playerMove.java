package events.player;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

public class playerMove implements Listener {
    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent e) {
        mcgoPlayer mc = Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString());
        if(mc.hasGameId()){
            if(Artid.games.get(mc.gameUUID).buyPhase){
                if(Artid.games.get(mc.gameUUID).copPlayers.contains(mc)){
                    if(!Artid.games.get(mc.gameUUID).getSpawnLocations(mc.player.getWorld().getName()).getCopSpawn().contains(mc.player.getBoundingBox())){
                        mc.player.teleport(Artid.games.get(mc.gameUUID).getSpawnLocations(mc.player.getWorld().getName()).getCopSpawnpoint());
                    }
                }
                if(Artid.games.get(mc.gameUUID).crimPlayers.contains(mc)){
                    if(!Artid.games.get(mc.gameUUID).getSpawnLocations(mc.player.getWorld().getName()).getCrimSpawn().contains(mc.player.getBoundingBox())){
                        mc.player.teleport(Artid.games.get(mc.gameUUID).getSpawnLocations(mc.player.getWorld().getName()).getCrimSpawnpoint());
                    }
                }
            }
        }
    }
}
