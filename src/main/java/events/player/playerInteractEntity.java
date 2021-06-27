package events.player;

import inventories.shop;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.Objects;

public class playerInteractEntity implements Listener {
    @EventHandler
    public void clickEntity(PlayerInteractEntityEvent e){
        switch(e.getRightClicked().getType()){
            case VILLAGER:
                switch((ChatColor.stripColor(e.getRightClicked().getCustomName()))){
                    case "Sandstorm":
                        e.setCancelled(true);
                        e.getPlayer().teleport(new Location(Bukkit.getWorld("Sandstorm"), -794.5, 86, -202.5));
                        e.getPlayer().setGameMode(GameMode.SURVIVAL);
                        giveItems(e.getPlayer());
                        break;
                    case "Alleyway":
                        e.setCancelled(true);
                        e.getPlayer().teleport(new Location(Bukkit.getWorld("Alleyway"), -843,79,-143));
                        e.getPlayer().setGameMode(GameMode.SURVIVAL);
                        giveItems(e.getPlayer());
                        break;
                    case "Atomic":
                        e.setCancelled(true);
                        e.getPlayer().teleport(new Location(Bukkit.getWorld("Atomic"), -882,102,-202));
                        e.getPlayer().setGameMode(GameMode.SURVIVAL);
                        giveItems(e.getPlayer());
                        break;
                    case "Junction":
                        e.setCancelled(true);
                        e.getPlayer().teleport(new Location(Bukkit.getWorld("Junction"), -2,74,-47));
                        e.getPlayer().setGameMode(GameMode.SURVIVAL);
                        giveItems(e.getPlayer());
                        break;
                    case "Bazaar":
                        e.setCancelled(true);
                        e.getPlayer().teleport(new Location(Bukkit.getWorld("Bazaar"), 147,74,-57));
                        e.getPlayer().setGameMode(GameMode.SURVIVAL);
                        giveItems(e.getPlayer());
                        break;
                    case "Reserve":
                        e.setCancelled(true);
                        e.getPlayer().teleport(new Location(Bukkit.getWorld("Reserve"), 85,44,-276));
                        e.getPlayer().setGameMode(GameMode.SURVIVAL);
                        giveItems(e.getPlayer());
                        break;
                    case "Derailed":
                        e.setCancelled(true);
                        e.getPlayer().teleport(new Location(Bukkit.getWorld("Derailed"), 717,53,-2165));
                        e.getPlayer().setGameMode(GameMode.SURVIVAL);
                        giveItems(e.getPlayer());
                        break;
                    case "Temple":
                        e.setCancelled(true);
                        e.getPlayer().teleport(new Location(Bukkit.getWorld("Temple"), -811,73,-123));
                        e.getPlayer().setGameMode(GameMode.SURVIVAL);
                        giveItems(e.getPlayer());
                        break;
                    case "Carrier":
                        e.setCancelled(true);
                        e.getPlayer().teleport(new Location(Bukkit.getWorld("Carrier"), -21,5,-50));
                        e.getPlayer().setGameMode(GameMode.SURVIVAL);
                        giveItems(e.getPlayer());
                        break;
                    case "Overgrown":
                        e.setCancelled(true);
                        e.getPlayer().teleport(new Location(Bukkit.getWorld("Overgrown"), -28,3,-14));
                        e.getPlayer().setGameMode(GameMode.SURVIVAL);
                        giveItems(e.getPlayer());
                        break;
                }
                break;
            default:
                e.setCancelled(true);
        }

    }
    public void giveItems(Player p){
        p.getInventory().setItem(8, shop.customItem(Material.GHAST_TEAR,ChatColor.WHITE + "Shop"));
        p.getInventory().setItem(2, shop.customItem(Material.BONE,ChatColor.WHITE + "Knife"));
        p.getInventory().setItem(7, shop.customItem(Material.COMPASS,ChatColor.WHITE + "Set Spawn"));
    }
}
