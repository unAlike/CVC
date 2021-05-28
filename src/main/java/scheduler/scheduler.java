package scheduler;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.scheduler.BukkitRunnable;

public class scheduler extends BukkitRunnable {

    @Override
    public void run() {
        if(Artid.mcPlayers!=null) {
            for (mcgoPlayer player : Artid.mcPlayers.values()) {

            }
        }
    }
}
