package events.player;

import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;

public class playerInteractEntity implements Listener {
    @EventHandler
    public void clickEntity(PlayerInteractEntityEvent e){
        switch(ChatColor.stripColor(e.getRightClicked().getCustomName())){
            case "Sandstorm":
                e.setCancelled(true);
                e.getPlayer().teleport(new Location(Bukkit.getWorld("Sandstorm"), -794.5, 86, -202.5));
                e.getPlayer().setGameMode(GameMode.ADVENTURE);
                e.getPlayer().getInventory().setItem(8,new ItemStack(Material.GHAST_TEAR));
                break;
            case "Alleyway":
                e.setCancelled(true);
                e.getPlayer().teleport(new Location(Bukkit.getWorld("Alleyway"), -843,79,-143));
                e.getPlayer().setGameMode(GameMode.ADVENTURE);
                e.getPlayer().getInventory().setItem(8,new ItemStack(Material.GHAST_TEAR));
                break;
            case "Atomic":
                e.setCancelled(true);
                e.getPlayer().teleport(new Location(Bukkit.getWorld("Atomic"), -882,102,-202));
                e.getPlayer().setGameMode(GameMode.ADVENTURE);
                e.getPlayer().getInventory().setItem(8,new ItemStack(Material.GHAST_TEAR));
                break;
            case "Junction":
                e.setCancelled(true);
                e.getPlayer().teleport(new Location(Bukkit.getWorld("Junction"), -2,74,-47));
                e.getPlayer().setGameMode(GameMode.ADVENTURE);
                e.getPlayer().getInventory().setItem(8,new ItemStack(Material.GHAST_TEAR));
                break;
            case "Bazaar":
                e.setCancelled(true);
                e.getPlayer().teleport(new Location(Bukkit.getWorld("Bazaar"), 147,74,-57));
                e.getPlayer().setGameMode(GameMode.ADVENTURE);
                e.getPlayer().getInventory().setItem(8,new ItemStack(Material.GHAST_TEAR));
                break;
            case "Reserve":
                e.setCancelled(true);
                e.getPlayer().teleport(new Location(Bukkit.getWorld("Reserve"), 83,44,-247));
                e.getPlayer().setGameMode(GameMode.ADVENTURE);
                e.getPlayer().getInventory().setItem(8,new ItemStack(Material.GHAST_TEAR));
                break;
            case "Derailed":
                e.setCancelled(true);
                e.getPlayer().teleport(new Location(Bukkit.getWorld("Derailed"), 717,53,-2165));
                e.getPlayer().setGameMode(GameMode.ADVENTURE);
                e.getPlayer().getInventory().setItem(8,new ItemStack(Material.GHAST_TEAR));
                break;
            case "Temple":
                e.setCancelled(true);
                e.getPlayer().teleport(new Location(Bukkit.getWorld("Temple"), -811,73,-123));
                e.getPlayer().setGameMode(GameMode.ADVENTURE);
                e.getPlayer().getInventory().setItem(8,new ItemStack(Material.GHAST_TEAR));
                break;
            case "Carrier":
                e.setCancelled(true);
                e.getPlayer().teleport(new Location(Bukkit.getWorld("Carrier"), -21,5,-50));
                e.getPlayer().setGameMode(GameMode.ADVENTURE);
                e.getPlayer().getInventory().setItem(8,new ItemStack(Material.GHAST_TEAR));
                break;
            case "Overgrown":
                e.setCancelled(true);
                e.getPlayer().teleport(new Location(Bukkit.getWorld("Overgrown"), -28,3,-14));
                e.getPlayer().setGameMode(GameMode.ADVENTURE);
                e.getPlayer().getInventory().setItem(8,new ItemStack(Material.GHAST_TEAR));
                break;
        }
    }
}
