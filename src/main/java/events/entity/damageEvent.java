package events.entity;


import groupid.artid.Artid;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.security.SecureRandom;
import java.util.Objects;


public class damageEvent implements Listener {
    @EventHandler
    public void explosion(EntityDamageEvent e){
        switch(e.getCause()){
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
                break;
            case FIRE_TICK:
                e.setCancelled(true);
                break;
            case ENTITY_ATTACK:
                if(e instanceof EntityDamageByEntityEvent) {
                    if (e.getEntity() instanceof Player && ((EntityDamageByEntityEvent) e).getDamager() instanceof Player && ((Player) ((EntityDamageByEntityEvent) e).getDamager()).getInventory().getItemInMainHand().getType().equals(Material.BONE)){
                        e.setDamage(4);
                        ((Player) e.getEntity()).getPlayer().sendMessage("set the dmg");
                    }
                }
        }

    }
    public static void damageMarker(Location loc, World w, Player p, float dmg){
        WorldServer s = ((CraftWorld)w.getWorld()).getHandle();
        EntityArmorStand arm = new EntityArmorStand(EntityTypes.ARMOR_STAND, s);
        arm.setLocation(loc.getX(),loc.getY(),loc.getZ(),loc.getPitch(), loc.getYaw());
        arm.setInvisible(true);
        arm.setInvulnerable(true);
        arm.setMarker(true);
        arm.setCustomName(new ChatMessage(ChatColor.RED + "" + Math.ceil(dmg * 5)));
        arm.setCustomNameVisible(true);
        arm.setNoGravity(true);
        s.addEntity(arm);
        PacketPlayOutSpawnEntityLiving pack = new PacketPlayOutSpawnEntityLiving(arm);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pack);


        new BukkitRunnable() {
            @Override
            public void run() {
                arm.setCustomNameVisible(false);
                arm.killEntity();
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pack);
            }
        }.runTaskLater(Artid.plug, 20);

    }
}
