package events.entity;


import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;


public class damageEvent implements Listener {
    @EventHandler
    public void explosion(EntityDamageEvent e){
        if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)){
            e.setCancelled(true);
            e.getEntity().getVelocity().setY(0);
        }
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            e.setCancelled(true);
            ((CraftPlayer)e.getEntity()).damage(e.getEntity().getFallDistance()-5);
        }
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FIRE)){
            e.setDamage(4);
            e.getEntity().setFireTicks(0);
        }
    }
    @EventHandler
    public void EntityDamageEnt(EntityDamageByEntityEvent e){
        if(e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK)) e.setCancelled(true);
    }
}
