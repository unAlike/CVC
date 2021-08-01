package mcgoScoreboard;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
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


    int i=0;
    Score[] scores = new Score[13];


    public mcgoScoreboard(mcgoPlayer p) {
        headArmor = false;
        bodyArmor = false;
        this.mcPlayer=p;

        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("scoreboard","dummy", ChatColor.BOLD + "" + ChatColor.GOLD + "Beta Testing Server");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        //10
        Team timer = scoreboard.registerNewTeam("timer");
        timer.addEntry("Objective: "  + ChatColor.GREEN);
        timer.setSuffix(ChatColor.GREEN + "");
        objective.getScore("Objective: "  + ChatColor.GREEN).setScore(10);

        //9
        Team obj = scoreboard.registerNewTeam("Objective");
        obj.addEntry(ChatColor.GREEN+" ");
        objective.getScore(ChatColor.GREEN+" ").setScore(9);

        //8
        objective.getScore("           ").setScore(8);

        //7
        Team crimsAlive = scoreboard.registerNewTeam("crimsAlive");
        crimsAlive.addEntry(ChatColor.DARK_RED+"銑 "+ChatColor.WHITE+"Alive: ");
        //suff
        objective.getScore(ChatColor.DARK_RED+"銑 "+ChatColor.WHITE+"Alive: ").setScore(7);

        //6
        Team copsAlive = scoreboard.registerNewTeam("copsAlive");
        copsAlive.addEntry(ChatColor.AQUA + "銐 " + ChatColor.WHITE + "Alive: ");
        //suffix
        objective.getScore(ChatColor.AQUA + "銐 " + ChatColor.WHITE + "Alive: ").setScore(6);

        //5
        objective.getScore("                   ").setScore(5);

        //4
        String str = "";
        if(headArmor){
            str= str.concat(ChatColor.BLUE + "鉿");
        } else {
            str= str.concat(ChatColor.GRAY + "鉿");
        }
        if(bodyArmor){
            str= str.concat(ChatColor.BLUE + "鉾");
        } else {
            str= str.concat(ChatColor.GRAY + "鉾");
        }
        Bukkit.broadcastMessage(str);
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

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
    public void update(){

    }
    public void updateTimer(int i){
        scoreboard.getTeam("timer").setSuffix(ChatColor.GREEN +"" + i);
    }
}
