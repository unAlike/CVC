package events.player;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class playerEat implements Listener {
    @EventHandler
    public void eat(PlayerItemConsumeEvent e){
        switch(e.getItem().getType()){
            default:
                break;
            case GOLDEN_APPLE:
                e.setCancelled(true);
                if(e.getPlayer().getLocation().subtract(0,.25,0).getBlock().getBoundingBox().getVolume()==1 && e.getPlayer().isOnGround()){
                    e.getPlayer().getInventory().setItem(5,new ItemStack(Material.AIR));
                    bombPlant(e);
                }
                break;
        }
    }
    public void bombPlant(PlayerItemConsumeEvent e){
        Block bomb = e.getPlayer().getWorld().getBlockAt(e.getPlayer().getLocation());
        bomb.setType(Material.CHEST);
        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), "mcgo.gamesounds.bombplanted", 100, 1);
        Artid.games.get(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).gameUUID).timer = 45;
        Artid.games.get(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).gameUUID).bombPlanted = true;
        Artid.games.get(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).gameUUID).playerWithBomb = null;
        Artid.games.get(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).gameUUID).bombLocation = bomb;
        ArmorStand stand = (ArmorStand) e.getPlayer().getWorld().spawnEntity(bomb.getLocation().add(.5,1,.5), EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setMarker(true);
        stand.setInvisible(true);
        stand.setCustomNameVisible(true);
        new BukkitRunnable() {
            int i = 45*4;
            int beepCount = 0;
            int beepCountMax = 4;
            @Override
            public void run() {
                stand.setCustomName(ChatColor.RED + "" + (i/4));
                if(bomb.getType().equals(Material.CHEST)) {
                    if(!Artid.games.get(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).gameUUID).bombDefused) {
                        if (beepCount < beepCountMax) {
                            beepCount++;
                        } else {
                            beepCount = 0;
                            e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), "mcgo.gamesounds.bombbeep", 100, 1);
                        }
                        switch (i) {
                            case 30 * 4:
                                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), "mcgo.gamesounds.30secondsremain", 100, 1);
                                break;
                            case 15 * 4:
                                beepCountMax = 2;
                                break;
                            case 5 * 4:
                                beepCountMax = 1;
                                break;
                            case 0:
                                e.getPlayer().getWorld().createExplosion(bomb.getLocation(), 2, false, false);
                                bomb.setType(Material.AIR);
                                Artid.games.get(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).gameUUID).bombLocation = null;
                                stand.remove();
                                this.cancel();
                        }
                        i--;
                    }
                    else{
                        for(mcgoPlayer mc :Artid.games.get(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).gameUUID).mcgoPlayers){
                            mc.player.playSound(mc.player.getLocation(),"mcgo.gamesounds.bombdefused",1,1);
                            Artid.games.get(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).gameUUID).bombDefused=true;
                            stand.remove();
                            this.cancel();
                        }
                    }
                }
                else{
                    stand.remove();
                    this.cancel();
                }
            }
        }.runTaskTimer(Artid.plug,10,5);
    }
}
