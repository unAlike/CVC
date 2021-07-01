package groupid.artid;

import com.mojang.datafixers.util.Pair;
import guns.gun;
import guns.gunTypes;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.ItemStack;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class mcgoPlayer {
    public String name;
    public String UUID;
    public Player player;
    LinkedList<BoundingBox> boxs = new LinkedList<BoundingBox>();
    LinkedList<Location> locs = new LinkedList<Location>();

    public gun main;
    public gun offhand;
    public boolean blind = false;
    public boolean onFire;
    public boolean hypixelNades = false;


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
                final Pair<net.minecraft.server.v1_16_R3.EnumItemSlot,net.minecraft.server.v1_16_R3.ItemStack> pr = new Pair<net.minecraft.server.v1_16_R3.EnumItemSlot,net.minecraft.server.v1_16_R3.ItemStack>(EnumItemSlot.HEAD, new net.minecraft.server.v1_16_R3.ItemStack(Blocks.AIR));
                for (final Player d : player.getWorld().getPlayers()) {
                    if(p!=d) {
                        final PacketPlayOutEntityEquipment pt = new PacketPlayOutEntityEquipment(((CraftPlayer) d).getHandle().getId(), Arrays.asList(pr));
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(pt);
                    }
                }
            }
        }.runTaskTimer(Artid.plug,0,1);
        new BukkitRunnable(){
            @Override
            public void run() {
                boxs.push(player.getBoundingBox());
                if(boxs.size()>100){
                    boxs.remove(boxs.getLast());
                }
                locs.push(player.getLocation());
                if(locs.size()>5){
                    locs.remove(locs.getLast());
                }
            }
        }.runTaskTimer(Artid.plug,0,1);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(player.getFireTicks()>0){
                    player.damage((Double)1.0);
                    player.setNoDamageTicks(0);
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
        main.updateItem();
    }
    public void giveOffhandGun(){player.getInventory().setItem(1, offhand.getItem());
        offhand.updateItem();
    }
    public BoundingBox getBox(int ticks){
        return boxs.get(ticks);
    }
    public Location getLoc(int ticks){
        return locs.get(ticks);
    }
    public void clearBoxs(){
        boxs.clear();
    }
}
