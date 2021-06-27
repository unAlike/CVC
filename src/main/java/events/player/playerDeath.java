package events.player;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class playerDeath implements Listener {
    @EventHandler
    public void death(PlayerDeathEvent e){
        mcgoPlayer mc = Artid.mcPlayers.get(e.getEntity().getPlayer().getUniqueId().toString());
        if(mc.getMain()!=null){
            mc.getMain().setAmmo(mc.getMain().getMaxAmmo());
            mc.getMain().getItem().setAmount(mc.getMain().getMaxAmmo());
            mc.getMain().setCooldown(false);
            mc.getMain().setIsReloading(false);
            mc.getMain().updateItem();
            mc.player.getInventory().setHelmet(new ItemStack(Material.AIR));
            if(mc.getMain().getItem().getType().equals(Material.GOLDEN_AXE)){
                mc.getMain().setFireRate(2);
                mc.getMain().setMaxRecoil(8f);
                mc.getMain().setSpread(12);
                mc.player.setWalkSpeed(0.2f);
            }
        }
        if(mc.getOffhand()!=null){
            mc.getOffhand().setAmmo(mc.getOffhand().getAmmo());
            mc.getOffhand().getItem().setAmount(mc.getOffhand().getMaxAmmo());
            mc.getOffhand().setCooldown(false);
            mc.getOffhand().setIsReloading(false);
            mc.getOffhand().updateItem();
        }
    }
}
