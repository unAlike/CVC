package game;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import guns.gun;
import guns.gunTypes;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.UUID;

public class game {
    public String gameId;
    public World map;
    int round = 0;
    public int timer = 21;
    public ArrayList<mcgoPlayer> mcgoPlayers;
    public ArrayList<mcgoPlayer> crimPlayers;
    public ArrayList<mcgoPlayer> copPlayers;
    public boolean preGame = true;
    public boolean bombPlanted;
    public boolean bombDefused;
    public boolean buyPhase;
    public boolean roundOngoing;
    public boolean canBuyWeapon;
    public boolean gameOngoing;
    public int copWonRounds;
    public int crimWonRounds;
    public int copsAlive;
    public int crimsAlive;
    public mcgoPlayer playerWithBomb;
    public Block bombLocation;
    PacketPlayOutEntityEffect glow;



    public game(World w){
        Artid.cops.setAllowFriendlyFire(false);
        Artid.crims.setAllowFriendlyFire(false);
        Artid.cops.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
        Artid.crims.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
        copWonRounds=0;
        crimWonRounds=0;
        copsAlive=0;
        crimsAlive=0;
        bombPlanted = false;
        preGame = true;
        buyPhase = false;
        gameOngoing=false;
        canBuyWeapon = true;
        bombLocation = null;
        mcgoPlayers = new ArrayList<mcgoPlayer>();
        crimPlayers = new ArrayList<mcgoPlayer>();
        copPlayers = new ArrayList<mcgoPlayer>();

        gameId = w.getName();
        Bukkit.broadcastMessage(gameId.toString());
        map = w;
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!mcgoPlayers.isEmpty()) {
                    for (mcgoPlayer pla : mcgoPlayers) {
                        try {
                            pla.gamesb.updateTimer(timer);
                            pla.lobbysb.updateTimer(timer);
                        }catch(NullPointerException e){

                        }
                    }
                    int i=0;
                    if(preGame) {
                        for (mcgoPlayer mc : mcgoPlayers) {
                            if (mc.ready) i++;
                            if (i == mcgoPlayers.size() && mcgoPlayers.size() > 1) {
                                startGame();
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(Artid.plug, 0, 20);
        Bukkit.broadcastMessage("Game Players: " + mcgoPlayers.toString());
    }
    public void addPlayer(Player p){
        p.teleport(getSpawnLocations(map.getName()).getRandomLocation());
        mcgoPlayers.add(Artid.mcPlayers.get(p.getUniqueId().toString()));
        Artid.mcPlayers.get(p.getUniqueId().toString()).setGameUUID(this.gameId);
        removeDuplates();

        ScoreboardTeam invis = ((CraftScoreboard)Bukkit.getScoreboardManager().getMainScoreboard()).getHandle().createTeam(UUID.randomUUID().toString().substring(0,5));
        invis.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);
        for(mcgoPlayer pla: mcgoPlayers) {
            invis.getPlayerNameSet().add(pla.player.getName());
        }
        PacketPlayOutScoreboardTeam iv = new PacketPlayOutScoreboardTeam(invis,0);
        ((CraftScoreboard)Bukkit.getScoreboardManager().getMainScoreboard()).getHandle().removeTeam(invis);
        for(mcgoPlayer pla: mcgoPlayers) {
            ((CraftPlayer)pla.player).getHandle().playerConnection.sendPacket(iv);
        }
    }
    public void removePlayer(mcgoPlayer mc){
        if(mcgoPlayers.contains(mc)) mcgoPlayers.remove(mc);
        if(crimPlayers.contains(mc)) crimPlayers.remove(mc);
        if(copPlayers.contains(mc)) copPlayers.remove(mc);
    }

    public void startGame(){
        gameOngoing=true;
        ///create teams
        preGame=false;

        for(mcgoPlayer mp : mcgoPlayers){
            mp.money=900;
            resetInventory(mp);
            mp.player.setScoreboard(mp.gamesb.getScoreboard());
            if(crimPlayers.size()==copPlayers.size()){
                if(Math.random()>.5){
                    Artid.crims.addEntry(mp.player.getDisplayName());
                    crimPlayers.add(mp);
                }
                else{
                    Artid.cops.addEntry(mp.player.getDisplayName());
                    copPlayers.add(mp);
                }
            }
            else if(crimPlayers.size()>copPlayers.size()){
                copPlayers.add(mp);
            }
            else if(copPlayers.size()>crimPlayers.size()){
                crimPlayers.add(mp);
            }
        }
        copsAlive=copPlayers.size();
        crimsAlive=crimPlayers.size();
        ///
        new BukkitRunnable() {
            @Override
            public void run() {
                if (copWonRounds >= 5) {
                    gameOngoing = false;
                    preGame = true;
                    for(mcgoPlayer m : mcgoPlayers){
                        m.player.setScoreboard(m.lobbysb.getScoreboard());
                    }
                    this.cancel();
                }
                else if (crimWonRounds >= 5) {
                    gameOngoing = false;
                    preGame = true;
                    for(mcgoPlayer m : mcgoPlayers){
                        m.player.setScoreboard(m.lobbysb.getScoreboard());
                    }
                    this.cancel();
                }
                else if(round==5||round==9){
                    ArrayList<mcgoPlayer> swap = new ArrayList<>();
                    swap = copPlayers;
                    copPlayers = crimPlayers;
                    crimPlayers = swap;
                    int roundswap;
                    roundswap = copWonRounds;
                    copWonRounds =crimWonRounds;
                    copWonRounds = roundswap;
                    for(mcgoPlayer mcp : mcgoPlayers){
                        resetInventory(mcp);
                        mcp.money=900;
                    }
                    startRound();
                }
                else {
                    if (!roundOngoing) {
                        startRound();
                    }
                }
            }
        }.runTaskTimer(Artid.plug,0,20);
    }
    public void startRound(){
        ScoreboardTeam invis = ((CraftScoreboard)Bukkit.getScoreboardManager().getMainScoreboard()).getHandle().createTeam(UUID.randomUUID().toString().substring(0,5));
        invis.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);
        for(mcgoPlayer p: mcgoPlayers) {
            invis.getPlayerNameSet().add(p.player.getName());
        }
        PacketPlayOutScoreboardTeam iv = new PacketPlayOutScoreboardTeam(invis,0);
        ((CraftScoreboard)Bukkit.getScoreboardManager().getMainScoreboard()).getHandle().removeTeam(invis);
        for(mcgoPlayer p: mcgoPlayers) {
            ((CraftPlayer)p.player).getHandle().playerConnection.sendPacket(iv);
        }

        round++;
        if(bombLocation!=null)bombLocation.setType(Material.AIR);
        bombLocation = null;
        bombPlanted= false;
        bombDefused = false;
        for(Entity e:map.getEntities()){
            if(e.getType()==EntityType.DROPPED_ITEM){
                e.remove();
            }
        }
        canBuyWeapon=true;
        roundOngoing = true;
        timer = 180;
        buyPhase=true;
        for(mcgoPlayer p: copPlayers){
            p.gameShop.update();
            for(mcgoPlayer mcp: copPlayers){
                glow = new PacketPlayOutEntityEffect(mcp.player.getEntityId(),new MobEffect(MobEffectList.fromId(24)));
                PacketContainer pc = new PacketContainer(PacketType.Play.Server.ENTITY_EFFECT, glow);
                pc.getBytes().write(1,(byte)1);
                pc.getIntegers().write(1,200);
                ((CraftPlayer)p.player).getHandle().playerConnection.sendPacket(glow);
            }
            p.player.setHealth(20);
            if(p.player.getGameMode() == GameMode.SPECTATOR) resetInventory(p);
            p.player.setGameMode(GameMode.SURVIVAL);
            p.player.teleport(getSpawnLocations(map.getName()).getCopSpawnpoint());
        }
        for(mcgoPlayer p: crimPlayers){
            p.gameShop.update();
            for(mcgoPlayer mcp: crimPlayers){
                glow = new PacketPlayOutEntityEffect(mcp.player.getEntityId(),new MobEffect(MobEffectList.fromId(24)));
                ((CraftPlayer)p.player).getHandle().playerConnection.sendPacket(glow);
            }
            p.player.setHealth(20);
            if(p.player.getGameMode() == GameMode.SPECTATOR) resetInventory(p);
            p.player.setGameMode(GameMode.SURVIVAL);
            p.player.teleport(getSpawnLocations(map.getName()).getCrimSpawnpoint());
            p.player.getInventory().setItem(7,new ItemStack(Material.COMPASS));
        }
        copsAlive=copPlayers.size();
        crimsAlive=crimPlayers.size();
        SecureRandom rand = new SecureRandom();
        playerWithBomb = crimPlayers.get(rand.nextInt(crimPlayers.size()));
        playerWithBomb.player.getInventory().setItem(5,new ItemStack(Material.QUARTZ));
        new BukkitRunnable(){
            int spawnTimer = 10;
            @Override
            public void run() {
                for(mcgoPlayer mc: mcgoPlayers){
                    mc.player.sendTitle(ChatColor.GREEN + "" + spawnTimer, ChatColor.GREEN + "Buy a weapon",0,21,0);
                }

                if(spawnTimer==0){
                    buyPhase=false;
                    this.cancel();
                }
                spawnTimer--;
            }
        }.runTaskTimer(Artid.plug,0,20);
        new BukkitRunnable(){
            boolean bombPlantedThisRound = false;
            @Override
            public void run() {
                if(timer<=150)canBuyWeapon=false;
                if(bombPlanted && !bombPlantedThisRound) {
                    timer=60;
                    bombPlantedThisRound=true;
                }
                if(!buyPhase)timer--;
                int i = 0;
                for (mcgoPlayer m : crimPlayers) {
                    if (m.player.getGameMode() == GameMode.SURVIVAL) i++;
                    if(playerWithBomb!=null){
                        m.player.setCompassTarget(playerWithBomb.player.getLocation());
                    }
                    else{
                        for(Entity e :map.getEntities()){
                            if(e.getType()== EntityType.DROPPED_ITEM && e.getName()==Material.QUARTZ.name()){
                                m.player.setCompassTarget(e.getLocation());
                            }
                        }
                    }
                }
                crimsAlive = i;
                if (crimsAlive == 0 && !bombPlanted) {
                    copWonRounds++;
                    for (mcgoPlayer m : crimPlayers) {
                        m.player.playSound(m.player.getLocation(), "mcgo.gamesounds.winteamocelots" ,1,1);
                        m.money+=1000;
                    }
                    for(mcgoPlayer m : copPlayers){
                        m.player.playSound(m.player.getLocation(), "mcgo.gamesounds.winteamocelots" ,1,1);
                        m.money+=2000;
                    }
                    roundOngoing = false;
                    this.cancel();
                }
                i=0;
                for (mcgoPlayer m : copPlayers) {
                    if (m.player.getGameMode() == GameMode.SURVIVAL) i++;
                }
                copsAlive=i;
                if (copsAlive == 0) {
                    crimWonRounds++;
                    for (mcgoPlayer m : crimPlayers) {
                        m.money+=2000;
                        m.player.playSound(m.player.getLocation(), "mcgo.gamesounds.winteamcreepers" ,1,1);
                    }
                    for(mcgoPlayer m : copPlayers){
                        m.money+=1000;
                        m.player.playSound(m.player.getLocation(), "mcgo.gamesounds.winteamcreepers" ,1,1);
                    }
                    roundOngoing = false;
                    this.cancel();
                }
                if(timer<=0 && !bombPlanted){
                    copWonRounds++;
                    for (mcgoPlayer m : crimPlayers) {
                        m.player.playSound(m.player.getLocation(), "mcgo.gamesounds.winteamocelots" ,1,1);
                        m.money+=1000;
                    }
                    for(mcgoPlayer m : copPlayers){
                        m.player.playSound(m.player.getLocation(), "mcgo.gamesounds.winteamocelots" ,1,1);
                        m.money+=2000;
                    }
                    roundOngoing = false;
                    this.cancel();
                }
                if(timer<0 && bombPlanted){
                    crimWonRounds++;
                    for (mcgoPlayer m : crimPlayers) {
                        m.player.playSound(m.player.getLocation(), "mcgo.gamesounds.winteamcreepers" ,1,1);
                        m.money+=2000;
                    }
                    for(mcgoPlayer m : copPlayers){
                        m.player.playSound(m.player.getLocation(), "mcgo.gamesounds.winteamcreepers" ,1,1);
                        m.money+=1000;
                    }
                    roundOngoing = false;
                    this.cancel();
                }
                if(playerWithBomb!=null) {
                    if (getSpawnLocations(map.getName()).getASite().contains(playerWithBomb.player.getBoundingBox()) || getSpawnLocations(map.getName()).getBSite().contains(playerWithBomb.player.getBoundingBox())) {
                        if (playerWithBomb.player.getLocation().subtract(0, .1, 0).getBlock().getBoundingBox().getVolume() != 1 && playerWithBomb.player.getPlayer().isOnGround()) {
                            playerWithBomb.player.getInventory().setItem(5, new ItemStack(Material.QUARTZ));
                            map.playSound(playerWithBomb.player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1, 1);
                        } else {
                            if (playerWithBomb.player.getInventory().getItem(5).getType() == Material.QUARTZ) {
                                playerWithBomb.player.getInventory().setItem(5, new ItemStack(Material.GOLDEN_APPLE));
                                map.playSound(playerWithBomb.player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_ON, 1, 1);
                            }
                        }
                    } else {
                        if (playerWithBomb.player.getInventory().getItem(5).getType() == Material.GOLDEN_APPLE) {
                            playerWithBomb.player.getInventory().setItem(5, new ItemStack(Material.QUARTZ));
                            map.playSound(playerWithBomb.player.getLocation(), Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 1, 1);
                        }

                    }
                }
                if(bombDefused){
                    copWonRounds++;
                    for (mcgoPlayer m : crimPlayers) {
                        m.player.playSound(m.player.getLocation(), "mcgo.gamesounds.winteamocelots" ,1,1);
                        m.money+=1000;
                    }
                    for(mcgoPlayer m : copPlayers){
                        m.player.playSound(m.player.getLocation(), "mcgo.gamesounds.winteamocelots" ,1,1);
                        m.money+=2000;
                    }
                    roundOngoing = false;
                    this.cancel();
                }
            }
        }.runTaskTimer(Artid.plug,0,20);
    }

    private void resetInventory(mcgoPlayer p){
        for(mcgoPlayer mcp : mcgoPlayers){
            mcp.player.hidePlayer(Artid.plug,p.player);
            mcp.player.showPlayer(Artid.plug,p.player);
        }
        p.bodyArmor=false;
        p.headArmor=false;
        p.player.getInventory().clear();
        p.setMain(null);
        try {
            p.setOffhand((gun) gunTypes.USP.getGun().clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        p.getOffhand().setPlayerUUID(p.player.getUniqueId());
        p.giveOffhandGun();
        if(copPlayers.contains(p)){
            ItemStack shop = new ItemStack(Material.GOLD_NUGGET, 1);
            ItemMeta im = shop.getItemMeta();
            im.setDisplayName("Wire Cutters");
            shop.setItemMeta(im);
            p.player.getInventory().setItem(5, shop);
        }
        if(crimPlayers.contains(p)) {
            ItemStack shop = new ItemStack(Material.COMPASS, 1);
            ItemMeta im = shop.getItemMeta();
            im.setDisplayName("Bomb Locator");
            shop.setItemMeta(im);
            p.player.getInventory().setItem(7, shop);
        }
        ItemStack shop = new ItemStack(Material.GHAST_TEAR);
        ItemMeta sm = shop.getItemMeta();
        sm.setDisplayName(ChatColor.stripColor("Shop"));
        shop.setItemMeta(sm);
        p.player.getInventory().setItem(8,shop);

        ItemStack knife = new ItemStack(Material.BONE,1);
        ItemMeta im2 = knife.getItemMeta();
        im2.setDisplayName("Knife");
        knife.setItemMeta(im2);
        p.player.getInventory().setItem(2,knife);
    }

    public void messageAll(String s){
        for(Player p : map.getPlayers()){
            p.sendMessage(s);
        }
    }
    public void messageAll(Sound s){
        for(Player p : map.getPlayers()){
            p.playSound(p.getLocation(),s,1,1);
        }
    }
    public void messageCops(String s){
        for(mcgoPlayer p: copPlayers){
            p.player.sendMessage(s);
        }
    }
    public void messageCrims(String s){
        for(mcgoPlayer p: crimPlayers){
            p.player.sendMessage(s);
        }
    }
    public void removeDuplates(){
        ArrayList<mcgoPlayer> newList = new ArrayList<>();
        for(mcgoPlayer p: mcgoPlayers){
            if(!newList.contains(p)){
                newList.add(p);
            }
        }
        mcgoPlayers = newList;
    }
    public spawnLocations getSpawnLocations(String s){
        switch(s){
            default:
                return null;
            case "Sandstorm":
                return spawnLocations.SANDSTORM;
        }
    }
}
