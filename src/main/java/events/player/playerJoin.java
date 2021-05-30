package events.player;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class playerJoin implements Listener{
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e){
        Artid.mcPlayers.put(e.getPlayer().getUniqueId().toString(), new mcgoPlayer(e.getPlayer()));
        e.getPlayer().setGameMode(GameMode.ADVENTURE);
        e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0.5,63,0.5));
        Bukkit.getConsoleSender().sendMessage(e.getPlayer().getDisplayName() + " joined!");
        e.getPlayer().setGameMode(GameMode.ADVENTURE);
        e.getPlayer().getInventory().clear();


    }
}