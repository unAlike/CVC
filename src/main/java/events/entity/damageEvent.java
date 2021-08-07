package events.entity;


import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import game.game;
import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
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
                    mcgoPlayer p = Artid.mcPlayers.get(e.getEntity().getUniqueId().toString());
                    mcgoPlayer dmgr = Artid.mcPlayers.get(((EntityDamageByEntityEvent) e).getDamager().getUniqueId().toString());
                    game g = Artid.games.get(p.gameUUID);

                    if (e.getEntity() instanceof Player && ((EntityDamageByEntityEvent) e).getDamager() instanceof Player && ((Player) ((EntityDamageByEntityEvent) e).getDamager()).getInventory().getItemInMainHand().getType().equals(Material.BONE)){
                        if(p.hasGameId() && !p.getTeam().contains(dmgr)){
                            if (((Player) e.getEntity()).getHealth() - 4 >= 0) {
                                ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getHealth() - 4);
                            }
                            else{
                                Artid.mcPlayers.get(((EntityDamageByEntityEvent) e).getDamager().getUniqueId().toString()).kills++;
                                Artid.mcPlayers.get(((EntityDamageByEntityEvent) e).getDamager().getUniqueId().toString()).killStreak++;
                                dmgr.money+=1500;
                                ((Player) e.getEntity()).damage((double)20);
                                Bukkit.broadcastMessage(ChatColor.DARK_AQUA + ((Player) ((EntityDamageByEntityEvent) e).getDamager()).getDisplayName() + ChatColor.WHITE + " 銌 " + ChatColor.GREEN + ((Player) e.getEntity()).getDisplayName());
                            }
                        }
                        if(p.hasGameId() && Artid.games.get(p.gameUUID).preGame){
                            if (((Player) e.getEntity()).getHealth() - 4 >= 0) {
                                ((Player) e.getEntity()).setHealth(((Player) e.getEntity()).getHealth() - 4);
                            }
                            else{
                                Artid.mcPlayers.get(((EntityDamageByEntityEvent) e).getDamager().getUniqueId().toString()).kills++;
                                Artid.mcPlayers.get(((EntityDamageByEntityEvent) e).getDamager().getUniqueId().toString()).killStreak++;
                                dmgr.money+=1500;
                                ((Player) e.getEntity()).damage((double)20);
                                Bukkit.broadcastMessage(ChatColor.DARK_AQUA + ((Player) ((EntityDamageByEntityEvent) e).getDamager()).getDisplayName() + ChatColor.WHITE + " 銌 " + ChatColor.GREEN + ((Player) e.getEntity()).getDisplayName());
                            }
                        }
                        damageMarker(e.getEntity().getLocation(), ((CraftWorld)(e.getEntity().getWorld())).getHandle(), ((Player) ((EntityDamageByEntityEvent) e).getDamager()).getPlayer(), 5*5);
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
                PacketPlayOutEntityDestroy kill = new PacketPlayOutEntityDestroy(arm.getId());
                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(kill);

            }
        }.runTaskLater(Artid.plug, 20);

    }
}
