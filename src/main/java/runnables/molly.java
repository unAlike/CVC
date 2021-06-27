package runnables;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class molly extends BukkitRunnable {
    int radius = 0;
    int fireStage = 0;
    ArrayList<Block> sphere = new ArrayList<Block>();
    ArmorStand ar;
    public molly(ArmorStand ar){
        this.ar = ar;
    }
    @Override
    public void run() {
        switch(fireStage){
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
                    if(b.getType().equals(Material.AIR)) b.setType(Material.FIRE,true);
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
                    if(b.getType().equals(Material.AIR)) b.setType(Material.FIRE,true);
                }
                break;
            case 3:
                radius = 4;
                for (int Y = -radius; Y < radius; Y++)
                    for (int X = -radius; X < radius; X++)
                        for (int Z = -radius; Z < radius; Z++)
                            if (Math.sqrt((X * X) + (Y * Y) + (Z * Z)) <= radius) {
                                final Block block = ar.getWorld().getBlockAt(X + ar.getLocation().getBlockX(), Y + ar.getLocation().getBlockY(), Z + ar.getLocation().getBlockZ());
                                sphere.add(block);
                            }
                for(Block b : sphere){
                    if(b.getType().equals(Material.AIR)) b.setType(Material.FIRE,true);
                }
                break;
            case 40:
                for(Block b : sphere){
                    if(b.getType().equals(Material.FIRE)) b.setType(Material.AIR,true);
                }
                this.cancel();
                break;
        }
        fireStage++;
    }
}
