package groupid.artid;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import events.block.blockBreak;
import events.block.physics;
import events.entity.entityHit;
import events.entity.damageEvent;
import events.player.*;
import events.inventory.InventoryClick;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import runnables.adventure;
import runnables.nadeBlow;
import scheduler.scheduler;

import java.util.HashMap;

public final class Artid extends JavaPlugin implements Listener {

    public static Artid plug;
    public static HashMap<Player, Integer> shots;
    public static ProtocolManager protocolManager;
    public static HashMap<String, mcgoPlayer> mcPlayers;
    public static HashMap<String, String> isShooting;

    @Override
    public void onEnable() {
        plug = this;
        shots = new HashMap<Player, Integer>();
        mcPlayers = new HashMap<String, mcgoPlayer>();
        isShooting = new HashMap<String, String>();

        // FOR SHOOTING RESET
        new BukkitRunnable(){
            @Override
            public void run() {
                isShooting.clear();
            }
        }.runTaskTimer(this,0,5);
        for(Player p : this.getServer().getOnlinePlayers()){

            mcPlayers.put(p.getUniqueId().toString(), new mcgoPlayer(p));
            if(p.getGameMode()!=GameMode.CREATIVE) {
                p.setGameMode(GameMode.SURVIVAL);
            }
            if(!p.getWorld().equals("world")){
                p.getInventory().clear();
                p.getInventory().setItem(8,new ItemStack(Material.GHAST_TEAR));
                p.getInventory().setItem(2, new ItemStack(Material.BONE));
                p.getInventory().setItem(7, new ItemStack(Material.COMPASS));
            }
        }
        for(World w : Bukkit.getWorlds()){
            w.setGameRule(GameRule.DO_FIRE_TICK, false);
            w.setGameRule(GameRule.KEEP_INVENTORY, true);
            w.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            w.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
            w.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            w.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            w.setGameRule(GameRule.NATURAL_REGENERATION, false);
            w.setGameRule(GameRule.RANDOM_TICK_SPEED, 0);
            w.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            w.setTime(6000);
        }
        BukkitRunnable ad = new adventure();
        BukkitRunnable runner = new scheduler();
        BukkitRunnable nades = new nadeBlow();
        protocolManager = ProtocolLibrary.getProtocolManager();

        ad.runTaskTimer(Artid.plug, 0, 3);
        nades.runTaskTimer(Artid.plug, 1,1);
        runner.runTaskTimer(Artid.plug, 10, 10);
        //Register Events
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new playerJoin(), this);
        getServer().getPluginManager().registerEvents(new playerLeave(), this);
        getServer().getPluginManager().registerEvents(new playerToggleSneakEvent(), this);
        getServer().getPluginManager().registerEvents(new playerChangeItem(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new entityHit(), this);
        getServer().getPluginManager().registerEvents(new playerAttack(), this);
        getServer().getPluginManager().registerEvents(new playerInteractEntity(), this);
        getServer().getPluginManager().registerEvents(new physics(), this);
        getServer().getPluginManager().registerEvents(new damageEvent(), this);
        getServer().getPluginManager().registerEvents(new playerDropItem(), this);
        getServer().getPluginManager().registerEvents(new playerSleep(), this);
        getServer().getPluginManager().registerEvents(new playerPickupItem(), this);
        getServer().getPluginManager().registerEvents(new blockBreak(), this);
        getServer().getPluginManager().registerEvents(new playerDeath(), this);


        //Register Commands
        getCommand("cvc").setExecutor(new commands());

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "CVC MOD STARTED");
    }

    @Override
    public void onDisable() {

    }

}
