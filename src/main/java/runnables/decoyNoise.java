package runnables;

import org.bukkit.Effect;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

public class decoyNoise extends BukkitRunnable {
    String sound;
    ArmorStand ar;
    double time = System.currentTimeMillis();
    public decoyNoise(ArmorStand ar, String sound){
        this.sound = sound;
        this.ar = ar;
    }
    @Override
    public void run() {
        if(System.currentTimeMillis()-time>10000){
            this.cancel();
            ar.remove();
        }else{
            ar.getWorld().playSound(ar.getLocation(), sound, 1,1);
            ar.getWorld().playEffect(ar.getLocation(), Effect.SMOKE, 1);
        }

    }
}
