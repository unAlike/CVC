package events.entity;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import groupid.artid.Artid;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.Objects;


public class damageEvent implements Listener {
    @EventHandler
    public void explosion(EntityDamageEvent e){
        switch(e.getCause()){
            default:
                break;
            case ENTITY_EXPLOSION:
                e.setCancelled(true);
                e.getEntity().getVelocity().setY(0);
                break;
            case FALL:
                double fall = e.getEntity().getFallDistance();
                if(e.getEntity().getFallDistance()>5) {
                    e.setDamage(fall-5);
                }
                else e.setCancelled(true);
                break;
            case FIRE:
                e.setCancelled(true);
                Artid.mcPlayers.get(e.getEntity().getUniqueId().toString()).onFire = true;
                e.getEntity().setFireTicks(5);
                break;
            case FIRE_TICK:
                e.setCancelled(true);
                break;
            case ENTITY_ATTACK:
                if(e instanceof EntityDamageByEntityEvent) {
                    if (e.getEntity() instanceof Player && ((EntityDamageByEntityEvent) e).getDamager() instanceof Player && ((Player) ((EntityDamageByEntityEvent) e).getDamager()).getInventory().getItemInMainHand().getType().equals(Material.BONE)){
                        if(((Player) e.getEntity()).getHealth()-4 >= 0) {
                            ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getHealth() - 4);
                        }
                        else ((Player) e.getEntity()).damage((double)20);
                        damageMarker(e.getEntity().getLocation(), ((CraftWorld)(e.getEntity().getWorld())).getHandle(), ((Player) ((EntityDamageByEntityEvent) e).getDamager()).getPlayer(), 4*5);
                    }
                    else e.setCancelled(true);
                }
        }

    }
    public static void damageMarker(Location loc, World w, Player p, float dmg){




        EntityArmorStand arm = new EntityArmorStand(w,loc.getX(),loc.getY()+2,loc.getZ());
        arm.setInvisible(true);
        arm.setInvulnerable(true);
        arm.setMarker(true);
        arm.setCustomName(new ChatMessage(ChatColor.RED + "" + ((int) Math.ceil(dmg))));
        arm.setCustomNameVisible(true);
        arm.setNoGravity(true);

        final PacketPlayOutEntityMetadata[] pack = {new PacketPlayOutEntityMetadata(arm.getId(), arm.getDataWatcher(), true)};
        final PacketPlayOutSpawnEntityLiving[] pack2 = {new PacketPlayOutSpawnEntityLiving(arm)};
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pack2[0]);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pack[0]);


        new BukkitRunnable() {
            @Override
            public void run() {
                arm.setCustomNameVisible(false);
                arm.killEntity();
                pack[0] = new PacketPlayOutEntityMetadata(arm.getId(), arm.getDataWatcher(), true);
                pack2[0] = new PacketPlayOutSpawnEntityLiving(arm);
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pack2[0]);
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pack[0]);

            }
        }.runTaskLater(Artid.plug, 20);

    }
}
