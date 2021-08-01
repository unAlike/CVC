package game;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import net.minecraft.server.v1_16_R3.SoundEffect;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import javax.script.ScriptEngine;
import java.util.ArrayList;
import java.util.UUID;

public class game {
    World map;
    int round = 0;
    public int timer = 61;
    ArrayList<mcgoPlayer> mcgoPlayers = new ArrayList<mcgoPlayer>();
    ArrayList<mcgoPlayer> crimPlayers = new ArrayList<mcgoPlayer>();
    ArrayList<mcgoPlayer> copPlayers = new ArrayList<mcgoPlayer>();
    boolean preGame = true;
    public UUID gameId;

    public game(World w){
        gameId = UUID.randomUUID();
        Bukkit.broadcastMessage(gameId.toString());
        map = w;
        new BukkitRunnable() {
            @Override
            public void run() {
                for(mcgoPlayer pla: mcgoPlayers){
                    pla.gamesb.updateTimer(timer);
                    pla.lobbysb.updateTimer(timer);
                }
                timer--;
                if(timer%10==0){
                    messageAll(ChatColor.YELLOW + "The game will being in: " + ChatColor.GREEN + timer + ChatColor.YELLOW + " seconds.");
                }
                switch(timer){
                    case 0:
                        if(map.getPlayers().size()>1) startGame();
                        else {timer = 61; messageAll("Not enough players to start");}
                        break;
                    case 5: case 4: case 3: case 2: case 1:
                        messageAll(Sound.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON);
                        messageAll(ChatColor.YELLOW + "The game will being in: " + ChatColor.RED + timer + ChatColor.YELLOW + " seconds.");
                        break;
                }


            }
        }.runTaskTimer(Artid.plug, 0, 20);

    }
    public void addPlayer(Player p){
        p.teleport(map.getSpawnLocation());
        Artid.mcPlayers.get(p.getUniqueId().toString()).setGameUUID(this.gameId);
    }
    public void startGame(){
        ///create teams
        preGame=false;
        for(mcgoPlayer mp : mcgoPlayers){
            if(crimPlayers.size()==copPlayers.size()){
                if(Math.random()>.5){
                    crimPlayers.add(mp);
                }
                else{
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
        ///
        for(mcgoPlayer cp : crimPlayers){

        }
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
}
