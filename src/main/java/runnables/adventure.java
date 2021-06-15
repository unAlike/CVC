package runnables;

import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;


public class adventure extends BukkitRunnable {

    @Override
    public void run() {
        for(World w : Bukkit.getWorlds()) {
            for (Player p : w.getPlayers()) {
                if(p.getGameMode()!=GameMode.CREATIVE) {
                    p.setGameMode(GameMode.SURVIVAL);

                }
                p.setSaturation(2000000000);
            }
        }
    }
}
