package mcgoScoreboard;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;

public class lobbyScoreboard {
    private final Scoreboard scoreboard;
    private mcgoPlayer mcPlayer;

    public int timer = 0;
    public Objective objective;

    int i=0;
    Score[] scores = new Score[13];


    public lobbyScoreboard(mcgoPlayer p) {
        this.mcPlayer= p;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("scoreboard","dummy", ChatColor.BOLD + "" + ChatColor.GOLD + "Beta Testing Server");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        //9
        objective.getScore("                   ").setScore(9);
        //8
        Team map = scoreboard.registerNewTeam("map");
        map.addEntry("Map: ");
        map.setSuffix(ChatColor.GREEN + mcPlayer.player.getWorld().getName());
        objective.getScore("Map: ").setScore(8);
        //7
        Team players = scoreboard.registerNewTeam("players");
        players.addEntry("Players: ");
        players.setSuffix(ChatColor.GREEN + "0/0 ready");
        objective.getScore("Players: ").setScore(7);
        //6
        Team timer = scoreboard.registerNewTeam("timer");
        timer.addEntry("Starting in: ");
        timer.setSuffix(ChatColor.GREEN + "");
        objective.getScore("Starting in: ").setScore(6);
        //5
        Team ready = scoreboard.registerNewTeam("ready");
        ready.addEntry("Ready: ");
        ready.setSuffix(ChatColor.DARK_RED + "Not Ready");
        objective.getScore("Ready: ").setScore(5);
        //4
        objective.getScore("                 ").setScore(4);
        //3
        Team kills = scoreboard.registerNewTeam("kills");
        kills.addEntry("Kills: ");
        kills.setSuffix(ChatColor.GREEN + "" + mcPlayer.kills);
        objective.getScore("Kills: ").setScore(3);
        //2
        Team killStreak = scoreboard.registerNewTeam("killstreak");
        killStreak.addEntry("Kill Streak: ");
        killStreak.setSuffix(ChatColor.GREEN + "" + mcPlayer.killStreak);
        objective.getScore("Kill Streak: ").setScore(2);
        //1
        objective.getScore("                   ").setScore(1);
        //0
        objective.getScore(ChatColor.BOLD + "" + ChatColor.GOLD + "Beta Testing Server").setScore(0);

        map.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
        players.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
        timer.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
        ready.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
        kills.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
        killStreak.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OWN_TEAM);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
    public void update(){
        scoreboard.getTeam("map").setSuffix(ChatColor.GREEN + mcPlayer.player.getWorld().getName());
        if(mcPlayer.ready) scoreboard.getTeam("ready").setSuffix(ChatColor.GREEN + "Ready!");
        else scoreboard.getTeam("ready").setSuffix(ChatColor.RED + "Not ready. /cvc r");
        scoreboard.getTeam("kills").setSuffix(ChatColor.GREEN + "" + mcPlayer.kills);
        scoreboard.getTeam("killstreak").setSuffix(ChatColor.GREEN + "" + mcPlayer.killStreak);
        int i=0;
        for(mcgoPlayer pla: Artid.games.get(mcPlayer.gameUUID).mcgoPlayers){
            if(pla.ready)i++;
        }
        scoreboard.getTeam("players").setSuffix(ChatColor.GREEN + ""+ i + "/" + Artid.games.get(mcPlayer.gameUUID.toString()).mcgoPlayers.size() + " ready");
    }
    public void hideAllNicknames() {
        Set<String> list = Artid.cops.getEntries();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!list.contains(player.getName()))
                Artid.cops.addEntry(player.getName());
        }
    }
    public void updateTimer(int i){
        scoreboard.getTeam("timer").setSuffix(ChatColor.GREEN +"Not Ready");
    }
}
