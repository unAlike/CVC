package events.player;

import groupid.artid.Artid;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class playerDeath implements Listener {
    @EventHandler
    public void death(PlayerDeathEvent e){
        if(e.getEntity() instanceof Player){
            Player p = e.getEntity().getPlayer();
            if(Artid.mcPlayers.get(p).getMain()!=null) Artid.mcPlayers.get(p).getMain().unScope();
        }
    }
}
