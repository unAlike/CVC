package groupid.artid;

import guns.gun;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class mcgoPlayer {
    public String name;
    public String UUID;
    public Player player;

    public gun main;
    public gun offhand;


    public mcgoPlayer(Player p){
        this.name = p.getName();
        this.UUID = p.getUniqueId().toString();
        this.player = p;
        main=null;
        offhand=null;
    }
    public void setMain(gun m){
        main = m;
    }
    public void setOffhand(gun o){
        offhand = o;
    }
    public gun getMain(){
        return main;
    }
    public gun getOffhand(){
        return offhand;
    }
    public void giveMainGun(){
        player.getInventory().setItem(0, main.getItem());
    }
    public void giveOffhandGun(){player.getInventory().setItem(1, offhand.getItem());}
}
