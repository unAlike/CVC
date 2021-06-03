package runnables;

import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class reload extends BukkitRunnable {
    ItemStack item;
    public reload(ItemStack item){
        this.item = item;
    }
    @Override
    public void run() {

    }
}
