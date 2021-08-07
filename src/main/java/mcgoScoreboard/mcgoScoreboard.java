package mcgoScoreboard;

import game.game;
import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutScoreboardTeam;
import net.minecraft.server.v1_16_R3.ScoreboardTeam;
import net.minecraft.server.v1_16_R3.ScoreboardTeamBase;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.scoreboard.CraftScoreboard;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;

public class mcgoScoreboard {
    private final Scoreboard scoreboard;
    private mcgoPlayer mcPlayer;
    private Objective objective;

    public boolean headArmor=false;
    public boolean bodyArmor=false;
    public int money = 0;
    public String objString="";
    public int timer = 0;
    public Team cops,crims;


    int i=0;
    Score[] scores = new Score[13];


    public mcgoScoreboard(mcgoPlayer p) {
        cops = Artid.cops;
        crims = Artid.crims;
        headArmor = false;
        bodyArmor = false;
        this.mcPlayer=p;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("scoreboard","dummy", ChatColor.BOLD + "" + ChatColor.GOLD + "Beta Testing Server");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        //10
        Team timer = scoreboard.registerNewTeam("timer");
        timer.addEntry("Objective: "  + ChatColor.GREEN);
        //timer.setSuffix(ChatColor.GREEN + "" + Artid.games.get(mcPlayer.gameUUID.toString()).timer);
        objective.getScore("Objective: "  + ChatColor.GREEN).setScore(10);

        //9
        Team obj = scoreboard.registerNewTeam("objective");
        obj.addEntry(ChatColor.GREEN+"");
        objective.getScore(ChatColor.GREEN+"").setScore(9);

        //8
        objective.getScore("           ").setScore(8);

        //7
        Team crimsAlive = scoreboard.registerNewTeam("crimsAlive");
        crimsAlive.addEntry(ChatColor.DARK_RED+"銑 "+ChatColor.WHITE+"Alive: ");
        //crimsAlive.setSuffix(ChatColor.DARK_RED + "" + Artid.games.get(mcPlayer.gameUUID.toString()).crimPlayers.size());
        objective.getScore(ChatColor.DARK_RED+"銑 "+ChatColor.WHITE+"Alive: ").setScore(7);

        //6
        Team copsAlive = scoreboard.registerNewTeam("copsAlive");
        copsAlive.addEntry(ChatColor.DARK_AQUA + "銐 " + ChatColor.WHITE + "Alive: ");
        //crimsAlive.setSuffix(ChatColor.DARK_AQUA + "" + Artid.games.get(mcPlayer.gameUUID.toString()).copPlayers.size());
        objective.getScore(ChatColor.DARK_AQUA + "銐 " + ChatColor.WHITE + "Alive: ").setScore(6);

        //5
        objective.getScore("                   ").setScore(5);

        //4
        String str = "";
        if(mcPlayer.headArmor){
            str= str.concat(ChatColor.BLUE + "鉿");
        } else {
            str= str.concat(ChatColor.GRAY + "鉿");
        }
        if(mcPlayer.bodyArmor){
            str= str.concat(ChatColor.BLUE + "鉾");
        } else {
            str= str.concat(ChatColor.GRAY + "鉾");
        }
        Team armor = scoreboard.registerNewTeam("armor");
        armor.addEntry("Armor: ");
        armor.setSuffix(str);
        objective.getScore("Armor: ").setScore(4);

        //3
        Team money = scoreboard.registerNewTeam("money");
        money.addEntry("Money: "+ChatColor.GREEN);
        //money.setSuffix("$" + mcPlayer.getMoney());
        objective.getScore("Money: "+ChatColor.GREEN).setScore(3);

        //2
        objective.getScore("                ").setScore(2);

        //1
        Team team = scoreboard.registerNewTeam("team");
        team.addEntry("Team: "+ ChatColor.GREEN);
        objective.getScore("Team: "+ ChatColor.GREEN).setScore(1);

        //0
        objective.getScore(ChatColor.BOLD + "" + ChatColor.GOLD + "Beta Testing Server").setScore(0);

    }
    public void hideAllNicknames() {
        Player p = mcPlayer.player; //Player to target

        ScoreboardTeam team = new ScoreboardTeam(((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle(), p.getName());

        team.setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility.NEVER);

        ArrayList<String> playerToAdd = new ArrayList<>();

        playerToAdd.add(""); //Add the fake player so this player will not have a nametag

        ((CraftPlayer)mcPlayer.player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, 1));
        ((CraftPlayer)mcPlayer.player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, 0));
        ((CraftPlayer)mcPlayer.player).getHandle().playerConnection.sendPacket(new PacketPlayOutScoreboardTeam(team, playerToAdd, 3));
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
    public void update(){

    }
    public void updateTimer(int i){
        hideAllNicknames();
        cops = Artid.cops;
        crims = Artid.crims;
        game game = Artid.games.get(mcPlayer.gameUUID.toString());
        objective.setDisplayName(ChatColor.DARK_AQUA + "" + game.copWonRounds + " 銐  " + ChatColor.DARK_RED + "  銑 "+ game.crimWonRounds);
        scoreboard.getTeam("money").setSuffix(ChatColor.GREEN + "$" + mcPlayer.money);
        scoreboard.getTeam("crimsAlive").setSuffix(ChatColor.DARK_RED + "" + game.crimsAlive);
        scoreboard.getTeam("copsAlive").setSuffix(ChatColor.DARK_AQUA + "" + game.copsAlive);
        scoreboard.getTeam("timer").setSuffix(ChatColor.GREEN + stringifyTimer(i));
        if(Artid.games.get(mcPlayer.gameUUID.toString()).crimPlayers.contains(mcPlayer)) scoreboard.getTeam("team").setSuffix(ChatColor.DARK_RED + "A - 銑 Crims");
        if(Artid.games.get(mcPlayer.gameUUID.toString()).copPlayers.contains(mcPlayer)) scoreboard.getTeam("team").setSuffix(ChatColor.DARK_AQUA + "B - 銐 Cops");
        if(game.crimPlayers.contains(mcPlayer)){
            if(game.bombPlanted) scoreboard.getTeam("objective").setSuffix(ChatColor.GREEN + "Defend the bomb.");
            else scoreboard.getTeam("objective").setSuffix(ChatColor.GREEN + "Plant the Bomb.");
        }
        if(game.copPlayers.contains(mcPlayer)){
            if(game.bombPlanted) scoreboard.getTeam("objective").setSuffix(ChatColor.GREEN + "Defuse the bomb.");
            else scoreboard.getTeam("objective").setSuffix(ChatColor.GREEN + "Protect the bomb sites.");
        }

        String str = "";
        if(mcPlayer.headArmor) str= str.concat(ChatColor.GREEN + "鉿");
        else str = str.concat(ChatColor.GRAY + "鉿");
        if(mcPlayer.bodyArmor) str= str.concat(ChatColor.GREEN + "鉾");
        else str= str.concat(ChatColor.GRAY + "鉾");
        scoreboard.getTeam("armor").setSuffix(str);
    }
    public String stringifyTimer(int timer){
        String str = ChatColor.GREEN +"";
        long minutes = (int) Math.floor(timer/60);
        long seconds = timer%60;
        str = str.concat(String.format("%01d:%02d", minutes, seconds));
        return str;
    }
}
