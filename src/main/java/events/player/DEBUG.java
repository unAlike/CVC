package events.player;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DEBUG implements Listener {
    @EventHandler
    public void idk(EntityDamageByEntityEvent e){
        CraftWorld cw = (CraftWorld) Bukkit.getWorld("world");
        cw.createExplosion(e.getDamager().getLocation(), 3,false,false);
    }
}
