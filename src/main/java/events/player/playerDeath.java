package events.player;

import game.spawnLocations;
import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.security.SecureRandom;

public class playerDeath implements Listener {
    @EventHandler
    public void death(PlayerDeathEvent e){
        mcgoPlayer mc = Artid.mcPlayers.get(e.getEntity().getPlayer().getUniqueId().toString());
        mc.killStreak = 0;
        mc.deaths++;
        mc.lobbysb.update();
        mc.gamesb.update();
        if(Artid.games.get(mc.gameUUID.toString()).preGame) {
            switch (mc.player.getWorld().getName()) {
                case "Sandstorm":
                    e.getEntity().getPlayer().setBedSpawnLocation(spawnLocations.SANDSTORM.getRandomLocation(), true);
                    break;
            }
        }
        if(!Artid.games.get(mc.gameUUID.toString()).preGame) {
            e.getEntity().getPlayer().getInventory().clear();
            e.getEntity().getPlayer().setBedSpawnLocation(e.getEntity().getLocation(), true);
            e.getEntity().getPlayer().setGameMode(GameMode.SPECTATOR);
        }
        if(mc.getMain()!=null){
            mc.getMain().unScope();
            mc.getMain().setAmmo(mc.getMain().getMaxAmmo());
            mc.getMain().getItem().setAmount(mc.getMain().getMaxAmmo());
            mc.getMain().setCooldown(false);
            mc.getMain().setIsReloading(false);
            mc.getMain().updateItem();
            mc.player.getInventory().setHelmet(new ItemStack(Material.AIR));
            mc.famasOnCooldown=false;
            if(mc.getMain().getItem().getType().equals(Material.GOLDEN_AXE)){
                mc.getMain().setFireRate(2);
                mc.getMain().setMaxRecoil(8f);
                mc.getMain().setSpread(12);
                mc.player.setWalkSpeed(0.2f);
            }
        }
        if(mc.getOffhand()!=null){
            mc.hkOnCooldown=false;
            mc.getOffhand().setAmmo(mc.getOffhand().getMaxAmmo());
            mc.getOffhand().getItem().setAmount(mc.getOffhand().getMaxAmmo());
            mc.getOffhand().setCooldown(false);
            mc.getOffhand().setIsReloading(false);
            mc.getOffhand().updateItem();
        }
        new BukkitRunnable(){
            @Override
            public void run() {
                e.getEntity().setHealth(20);
            }
        }.runTaskLater(Artid.plug,10);
    }
}
