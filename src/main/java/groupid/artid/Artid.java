package groupid.artid;

import bot.CVCBot;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import events.block.blockBreak;
import events.block.physics;
import events.entity.entityHit;
import events.entity.damageEvent;
import events.packet.PlayerWalkPacket;
import events.player.*;
import events.inventory.InventoryClick;
import game.game;
import inventories.shop;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import runnables.adventure;
import runnables.nadeBlow;
import scheduler.scheduler;

import java.util.ArrayList;
import java.util.HashMap;

public final class Artid extends JavaPlugin implements Listener {

    public static Artid plug;
    public static HashMap<Player, Integer> shots;
    public static ProtocolManager protocolManager;
    public static HashMap<String, mcgoPlayer> mcPlayers;
    public static HashMap<String, String> isShooting;
    public static ArrayList<CVCBot> bots;
    public static HashMap<String, game> games;
    public static Team cops, crims;


    @Override
    public void onEnable() {
        if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Cops") == null) {
            cops = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("Cops");
        } else {
            cops = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Cops");
        }
        if (Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Crims") == null) {
            crims = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("Crims");
        } else {
            crims = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Crims");
        }
        bots = new ArrayList<CVCBot>();
        plug = this;
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "\n\n\n POGGGGGGGG    " + this.getName() + "\n\n\n POGGGGGGGGGGGGGGG");

        shots = new HashMap<Player, Integer>();
        mcPlayers = new HashMap<String, mcgoPlayer>();
        isShooting = new HashMap<String, String>();
        games = new HashMap<String, game>();

        for (World w : Bukkit.getWorlds()) {
            if (w.getName() != "world") {
                game g = new game(w);
                games.put(g.gameId.toString(), g);
            }
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

        // FOR SHOOTING RESET
        new BukkitRunnable() {
            @Override
            public void run() {
                isShooting.clear();
            }
        }.runTaskTimer(this, 0, 5);
        for (Player p : this.getServer().getOnlinePlayers()) {
            p.getPlayer().setWalkSpeed(.2f);
            mcPlayers.put(p.getUniqueId().toString(), new mcgoPlayer(p));
            if (p.getGameMode() != GameMode.CREATIVE) {
                p.setGameMode(GameMode.SURVIVAL);
            }
            if (!p.getWorld().equals("world")) {
                games.forEach((k, v) -> {
                    if (v.map.getName() == p.getWorld().getName()) {
                        v.addPlayer(p);
                    }
                });
                mcPlayers.get(p.getUniqueId().toString()).player.setScoreboard(mcPlayers.get(p.getUniqueId().toString()).lobbysb.getScoreboard());
                p.getInventory().clear();
                p.getInventory().setItem(8, shop.customItem(Material.GHAST_TEAR, ChatColor.WHITE + "Shop"));
                p.getInventory().setItem(2, shop.customItem(Material.BONE, ChatColor.WHITE + "Knife"));
                p.getInventory().setItem(7, shop.customItem(Material.GRAY_DYE, ChatColor.WHITE + "Not Ready"));
            }


        }
        BukkitRunnable ad = new adventure();
        BukkitRunnable runner = new scheduler();
        BukkitRunnable nades = new nadeBlow();
        protocolManager = ProtocolLibrary.getProtocolManager();


        ad.runTaskTimer(Artid.plug, 0, 3);
        nades.runTaskTimer(Artid.plug, 1, 1);
        runner.runTaskTimer(Artid.plug, 10, 10);

        //Register Events
        getServer().getPluginManager().registerEvents(new PlayerInteract(), this);
        getServer().getPluginManager().registerEvents(new playerJoin(), this);
        getServer().getPluginManager().registerEvents(new playerLeave(), this);
        getServer().getPluginManager().registerEvents(new playerToggleSneakEvent(), this);
        getServer().getPluginManager().registerEvents(new playerChangeItem(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new entityHit(), this);
        getServer().getPluginManager().registerEvents(new playerInteractEntity(), this);
        getServer().getPluginManager().registerEvents(new physics(), this);
        getServer().getPluginManager().registerEvents(new damageEvent(), this);
        getServer().getPluginManager().registerEvents(new playerDropItem(), this);
        getServer().getPluginManager().registerEvents(new playerSleep(), this);
        getServer().getPluginManager().registerEvents(new playerPickupItem(), this);
        getServer().getPluginManager().registerEvents(new blockBreak(), this);
        getServer().getPluginManager().registerEvents(new PlayerOpenInventory(), this);
        getServer().getPluginManager().registerEvents(new playerDeath(), this);
        getServer().getPluginManager().registerEvents(new playerEat(), this);
        getServer().getPluginManager().registerEvents(new playerMove(), this);

        protocolManager.addPacketListener(new PlayerWalkPacket(this,PacketType.Play.Server.NAMED_SOUND_EFFECT));


    }

    @Override
    public void onDisable() {
        for(Player p: Bukkit.getOnlinePlayers()){
            mcPlayers.get(p.getUniqueId().toString()).player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        }
        mcPlayers.clear();
        for(CVCBot bot : bots){
           bot.destroy();
        }
    }

}
