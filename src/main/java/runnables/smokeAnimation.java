package runnables;

import groupid.artid.Artid;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class smokeAnimation extends BukkitRunnable {
    int smokeStage = 0;
    int radius=0;
    ArrayList<Block> sphere = new ArrayList<Block>();
    ArmorStand ar;
    smokeAnimation(ArmorStand ar){
        this.ar=ar;
    }
    @Override
    public void run() {
        switch(smokeStage){
            case 0:
                radius = 2;
                for (int Y = -radius; Y < radius; Y++)
                    for (int X = -radius; X < radius; X++)
                        for (int Z = -radius; Z < radius; Z++)
                            if (Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) {
                                final Block block = ar.getWorld().getBlockAt(X + ar.getLocation().getBlockX(), Y + ar.getLocation().getBlockY(), Z + ar.getLocation().getBlockZ());
                                sphere.add(block);
                            }
                for(Block b : sphere){
                    if(b.getType().equals(Material.AIR) || b.getType().equals(Material.FIRE)) b.setType(Material.WHEAT,false);
                }
                break;
            case 1:
                radius = 3;
                for (int Y = -radius; Y < radius; Y++)
                    for (int X = -radius; X < radius; X++)
                        for (int Z = -radius; Z < radius; Z++)
                            if (Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) {
                                final Block block = ar.getWorld().getBlockAt(X + ar.getLocation().getBlockX(), Y + ar.getLocation().getBlockY(), Z + ar.getLocation().getBlockZ());
                                sphere.add(block);
                            }
                for(Block b : sphere){
                    if(b.getType().equals(Material.AIR) || b.getType().equals(Material.FIRE)) b.setType(Material.WHEAT,false);
                }
                break;
            case 3:
                radius = 5;
                for (int Y = -radius; Y < radius; Y++)
                    for (int X = -radius; X < radius; X++)
                        for (int Z = -radius; Z < radius; Z++)
                            if (Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) {
                                final Block block = ar.getWorld().getBlockAt(X + ar.getLocation().getBlockX(), Y + ar.getLocation().getBlockY(), Z + ar.getLocation().getBlockZ());
                                sphere.add(block);
                            }
                for(Block b : sphere){
                    if(b.getType().equals(Material.AIR) || b.getType().equals(Material.FIRE)) b.setType(Material.WHEAT,false);
                }
                break;
            case 40:
                for(Block b : sphere){
                    if(b.getType().equals(Material.WHEAT)) b.setType(Material.AIR,false);
                }
                this.cancel();
                break;
        }
        smokeStage++;
    }
}
