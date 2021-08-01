package events.player;

import groupid.artid.Artid;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class playerEat implements Listener {
    @EventHandler
    public void eat(PlayerItemConsumeEvent e){
        e.getPlayer().sendMessage("Hi");
        switch(e.getItem().getType()){
            default:
                break;
            case GOLDEN_APPLE:

                if(!e.getPlayer().getLocation().subtract(0,1,0).getBlock().getType().equals(Material.AIR) && e.getPlayer().isOnGround()){
                    bombPlant(e);
                }
                break;
        }
    }
    public void bombPlant(PlayerItemConsumeEvent e){
        Block bomb = e.getPlayer().getWorld().getBlockAt(e.getPlayer().getLocation());
        e.getPlayer().getWorld().getBlockAt(e.getPlayer().getLocation()).setType(Material.CHEST);
        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), "mcgo.gamesounds.bombplanted", 100, 1);
        new BukkitRunnable() {
            int i = 60*4;
            int beepCount = 0;
            int beepCountMax = 4;
            @Override
            public void run() {
                if(bomb.getType().equals(Material.CHEST)) {
                    if (beepCount < beepCountMax) {
                        beepCount++;
                    } else {
                        beepCount = 0;
                        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), "mcgo.gamesounds.bombbeep", 100, 1);
                    }
                    switch (i) {
                        case 120:
                            e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), "mcgo.gamesounds.30secondsremain", 100, 1);
                            break;
                        case 40:
                            beepCountMax = 2;
                            break;
                        case 20:
                            beepCountMax = 1;
                            break;
                        case 0:
                            e.getPlayer().getWorld().createExplosion(bomb.getLocation(), 2, false, false);
                            bomb.setType(Material.AIR);
                            this.cancel();
                    }
                    i--;
                }
                else{
                    e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), "mcgo.gamesounds.bombdefused", 100, 1);
                    this.cancel();
                }
            }
        }.runTaskTimer(Artid.plug,0,5);
    }
}
