package guns;

import com.mysql.jdbc.Buffer;
import groupid.artid.Artid;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import projectile.rayTracer;
import java.util.UUID;
import java.util.function.BiConsumer;

public class gun {
    int fireRate;
    float reloadTime;
    float damage;
    int maxAmmo;
    int ammo;
    ItemStack item;

    float maxRecoil;
    float spread;
    String soundEffect;
    String name;
    UUID playerUUID;
    String UUID;
    float recoil;
    boolean cooldown = false;
    int recoilCount = 0;

    public gun(){

    }
    public gun(int fireRate, float reloadTime, float damage, int maxAmmo, int ammo, ItemStack item, float maxRecoil, float spread, String soundEffect, String name, Player p){
        this.fireRate = fireRate;
        this.reloadTime = reloadTime;
        this.damage = damage;
        this.maxAmmo = maxAmmo;
        this.ammo = ammo;
        this.item = item;
        this.maxRecoil = maxRecoil;
        this.spread = spread;
        this.soundEffect = soundEffect;
        this.name = name;
        this.playerUUID = p.getUniqueId();
        new BukkitRunnable() {
            @Override
            public void run() {
                recoilCount--;
                if(recoilCount<=0)recoil=0;
            }
        }.runTaskTimer(Artid.plug, 0,1);
    }

    public void shoot(){
        if(!cooldown) {
            cooldown = true;
            new BukkitRunnable() {
                @Override
                public void run() {
                    cooldown = false;
                }
            }.runTaskLater(Artid.plug, fireRate);
            if (ammo >= 1) {
                ammo--;
                if(ammo!=0) item.setAmount(ammo); else reload();
                World w = Bukkit.getPlayer(playerUUID).getWorld();
                Player p = Bukkit.getPlayer(playerUUID);
                p.getInventory().setItemInMainHand(item);
                Location loc = p.getPlayer().getLocation();
                if (p.getPlayer().isSneaking()) loc.add(0, 1.35, 0);
                if (!p.getPlayer().isSneaking()) loc.add(0, 1.75, 0);

                double rand = (Math.random() - .5) * this.spread;
                loc.setYaw(loc.getYaw() + (float) rand);
                loc.setPitch(loc.getPitch() - this.recoil);

                rayTracer ray = new rayTracer(loc.toVector(), loc.getDirection());
                ray.highlight(w, 100, 1);
                w.playSound(loc, soundEffect, 1f, 1f);
                for (Entity ent : w.getNearbyEntities(loc, 100, 100, 100)) {
                    if (ray.intersects(ent.getBoundingBox(), 200, .05, w)) {
                        if (ent.getName() != p.getName() && ent instanceof LivingEntity) {

                            ((LivingEntity) ent).damage(damage/5);
                            ((LivingEntity) ent).setNoDamageTicks(0);
                            w.playSound(loc, soundEffect, 1f, 1f);
                            w.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                            if (ent.isDead()) {
                                p.getPlayer().sendMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE + " 銈銉 " + ChatColor.GREEN + ent.getName()));
                                w.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                            }
                        }
                    }
                }
                if(recoil < maxRecoil){
                    this.recoil += .5;
                }
                recoilCount = 20;
                if(ammo<1){
                    reload();
                }
            }
        }
    }
    public void reload(){
//        ammo = maxAmmo;
        short step = (short)(100/this.reloadTime);
//        item.setAmount(ammo);
//        Bukkit.getPlayer(playerUUID).getInventory().setItemInMainHand(item);
        new BukkitRunnable(){
            int counter = 0;
            @Override
            public void run() {
                if(Bukkit.getPlayer(playerUUID).getInventory().getItemInMainHand().equals(item)){
                    switch(counter){
                        case 0:
                            Bukkit.getPlayer(playerUUID).sendMessage("Counter at: 0" + counter);

                            break;
                        case 1:
                            Bukkit.getPlayer(playerUUID).sendMessage("Counter at: 0" + counter);
                            this.cancel();
                            break;
                    }
                    counter++;
                }
            }
        }.runTaskTimer(Artid.plug, 0, (long)this.reloadTime/10);


    }
    public void printStats(){
        Bukkit.getPlayer(playerUUID).sendMessage("Ammo: "+ammo +"\nMax Ammo: "+maxAmmo+"\nDamage: "+damage);
    }






    public float getFireRate() {
        return fireRate;
    }
    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }
    public float getDamage() {
        return damage;
    }
    public void setDamage(float damage) {
        this.damage = damage;
    }
    public int getMaxAmmo() {
        return maxAmmo;
    }
    public void setMaxAmmo(int maxAmmo) {
        this.maxAmmo = maxAmmo;
    }
    public ItemStack getItem() {
        return item;
    }
    public void setItem(ItemStack item) {
        this.item = item;
    }
    public float getRecoil() {
        return recoil;
    }
    public void setRecoil(float recoil) {
        this.recoil = recoil;
    }
    public String getSoundEffect() {
        return soundEffect;
    }
    public void setSoundEffect(String soundEffect) {
        this.soundEffect = soundEffect;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


}
