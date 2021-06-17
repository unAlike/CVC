package groupid.artid;

import guns.gun;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.LinkedList;

public class mcgoPlayer {
    public String name;
    public String UUID;
    public Player player;
    LinkedList<BoundingBox> boxs = new LinkedList<BoundingBox>();

    public gun main;
    public gun offhand;
    public boolean onFire;


    public mcgoPlayer(Player p){
        this.name = p.getName();
        this.UUID = p.getUniqueId().toString();
        this.player = p;
        onFire = false;
        main=null;
        offhand=null;
        new BukkitRunnable(){
            @Override
            public void run() {
                boxs.push(player.getBoundingBox());
                if(boxs.size()>100){
                    boxs.remove(boxs.getLast());
                }
            }
        }.runTaskTimer(Artid.plug,0,1);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(player.getFireTicks()>0){
                    player.damage((Double)1.0);
                    player.setNoDamageTicks(0);
                    player.setFireTicks(1);
                    onFire = false;
                }
            }
        }.runTaskTimer(Artid.plug, 0,5);

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
    public BoundingBox getBox(int ticks){
        return boxs.get(ticks);
    }
    public void clearBoxs(){
        boxs.clear();
    }
}
