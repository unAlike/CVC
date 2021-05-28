package guns;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import projectile.rayTracer;
import java.util.UUID;

public class gun {
    float fireRate;
    float damage;
    int maxAmmo;
    int ammo;
    ItemStack item;
    float recoil;
    float spread;
    String soundEffect;
    String name;
    UUID playerUUID;
    String UUID;
    public gun(){

    }
    public gun(float fireRate, float damage, int maxAmmo, int ammo, ItemStack item, float recoil, float spread, String soundEffect, String name, Player p){
        this.fireRate = fireRate;
        this.damage = damage;
        this.maxAmmo = maxAmmo;
        this.ammo = ammo;
        this.item = item;
        this.recoil = recoil;
        this.spread = spread;
        this.soundEffect = soundEffect;
        this.name = name;
        this.playerUUID = p.getUniqueId();
    }
    public void shoot(){
        if(ammo>=1) {
            ammo--;
            item.setAmount(ammo);
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

                        ((LivingEntity) ent).damage(damage);
                        ((LivingEntity) ent).setNoDamageTicks(0);
                        w.playSound(loc, soundEffect, 1f, 1f);
                        w.playSound(loc, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                        if(ent.isDead()){
                            p.getPlayer().sendMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE + " 銈銉 " + ChatColor.GREEN + ent.getName()));
                            w.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                        }
                    }
                }
            }
            this.recoil += .5;

            this.recoil = 0;


        }
        else{
            reload();
        }
    }
    public void reload(){
        item.setAmount(maxAmmo);
    }
    public void printStats(){
        Bukkit.getPlayer(playerUUID).sendMessage("Ammo: "+ammo +"\nMax Ammo: "+maxAmmo+"\nDamage: "+damage);
    }






    public float getFireRate() {
        return fireRate;
    }
    public void setFireRate(float fireRate) {
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
