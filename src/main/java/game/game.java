package game;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import javax.script.ScriptEngine;
import java.util.ArrayList;

public class game {
    World map;
    int round = 0;
    ArrayList<mcgoPlayer> mcgoPlayers = new ArrayList<mcgoPlayer>();
    ArrayList<mcgoPlayer> crimPlayers = new ArrayList<mcgoPlayer>();
    ArrayList<mcgoPlayer> copPlayers = new ArrayList<mcgoPlayer>();
    boolean preGame = true;

    public game(World w, String name){
        WorldCreator wc = new WorldCreator(name);
        wc.copy(w);
        map = wc.createWorld();
        Artid.worlds.put(name, wc.createWorld());
    }
    public void startGame(){
        ///create teams
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
}
