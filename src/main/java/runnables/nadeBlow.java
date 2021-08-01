package runnables;

import groupid.artid.Artid;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class nadeBlow extends BukkitRunnable {
    @Override
    public void run() {
        for(World w : Bukkit.getWorlds()) {
            for (ArmorStand ar : w.getEntitiesByClass(ArmorStand.class)) {
                if (ar.getTicksLived() >= 50) {
                    switch(ar.getEquipment().getItemInMainHand().getType()){
                        case OAK_SAPLING:
                            if(ar.getVehicle()!=null)ar.getVehicle().remove();
                            nade(ar, w);
                            ar.remove();
                            break;
                        case BIRCH_SAPLING:
                            flash(ar,w);
                            break;
                        case DARK_OAK_SAPLING:
                            if(ar.getTicksLived()>=55) {
                                smoke(ar, w);
                            }
                            break;
                        case JUNGLE_SAPLING:
                            decoy(ar,w);
                            break;
                        case ACACIA_SAPLING:
                            if(ar.getTicksLived()>80){
                                ar.remove();
                            }
                    }
                }
                if(ar.getEquipment().getItemInMainHand().getType().equals(Material.DARK_OAK_SAPLING)){
                    if(ar.getLocation().getBlock().getType().equals(Material.FIRE)){
                        smoke(ar,w);
                    }
                }
            }
        }
    }

    public static void nade(ArmorStand ar, World w){
        Location Loc = ar.getLocation();
        if(ar.getVehicle()!=null) ar.getVehicle().remove();
        ar.getWorld().playSound(ar.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 1);
        ar.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, ar.getLocation(), 1);
        ar.remove();;
        for(Entity e : Loc.getWorld().getNearbyEntities(Loc,14,10,14)){
            if(e instanceof Player) {
                if (e.getLocation().distance(Loc) < 7) {
                    if(((CraftPlayer) e).getHealth() - (25 - (e.getLocation().distance(Loc) * 4.0)) > 0){
                        ((CraftPlayer) e).damage(25 - (e.getLocation().distance(Loc) * 4.0));
                    }
                    else{
                        ((CraftPlayer) e).damage(20);
                        Bukkit.broadcastMessage(ChatColor.AQUA + ar.getName() + ChatColor.WHITE + " 銃 " + ChatColor.GREEN + ((CraftPlayer) e).getDisplayName());
                    }
                }
            }
        }

    }
    public static void smoke(ArmorStand ar, World w){
        BukkitRunnable scheduler = new smokeAnimation(ar);
        w.playSound(ar.getLocation(), "mcgo.throwables.explodesmoke", 5f, 1f);
        scheduler.runTaskTimer(Artid.plug, 0, 5);
        if(ar.getVehicle()!=null) ar.getVehicle().remove();
        ar.remove();
    }
    public static void flash(ArmorStand ar, World w){
        final Firework f = (Firework) ar.getWorld().spawn(ar.getLocation(), Firework.class);
        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(FireworkEffect.builder().flicker(false).trail(false).with(FireworkEffect.Type.BURST).withColor(Color.WHITE, Color.WHITE).build());
        fm.setPower(0);
        f.setFireworkMeta(fm);
        f.detonate();
        ar.getWorld().playSound(ar.getLocation(), "mcgo.throwables.explodeflashbang", 5, 1);
        for(Player player : ar.getWorld().getPlayers()){
            if(player.getLocation().distance(ar.getLocation())<10){
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60,1000));
                Artid.mcPlayers.get(player.getUniqueId().toString()).blind = true;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Artid.mcPlayers.get(player.getUniqueId().toString()).blind = false;
                    }
                }.runTaskLater(Artid.plug, 20);
            }

        }
        if(ar.getVehicle()!=null) ar.getVehicle().remove();
        ar.remove();
    }
    public static void decoy(ArmorStand ar, World w){
        BukkitRunnable decoys;
        if(Artid.mcPlayers.get(ar.getCustomName()).getMain()!=null){
            decoys = new decoyNoise(ar,Artid.mcPlayers.get(ar.getCustomName()).getMain().getSoundEffect());
        }
        else if(Artid.mcPlayers.get(ar.getCustomName()).getOffhand()!=null){
            decoys = new decoyNoise(ar,Artid.mcPlayers.get(ar.getCustomName()).getOffhand().getSoundEffect());
        }
        else {
            decoys = new decoyNoise(ar, Artid.mcPlayers.get(ar.getCustomName()).getOffhand().getSoundEffect());
        }
        decoys.runTaskTimer(Artid.plug, 3, 3);
        if(ar.getVehicle()!=null) ar.getVehicle().remove();
        ar.remove();
    }

}
