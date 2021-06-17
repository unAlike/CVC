package guns;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import events.entity.damageEvent;
import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import projectile.rayTracer;

import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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
    boolean isReloading = false;
    int recoilCount = 0;
    float timeSinceClick=10;
    int snipeState=0;
    int slot=0;
    String gunImg;
    float fallOffAmount = 1;


    public gun(){

    }
    public gun(int fireRate, float reloadTime, float damage, int maxAmmo, int ammo, ItemStack item, float maxRecoil, float spread, String soundEffect, String name, String gunImg, Player p, int slot, float fallOffAmount){
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
        this.gunImg = gunImg;
        this.slot=slot;
        this.fallOffAmount = fallOffAmount;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + name);
        item.setItemMeta(meta);

        new BukkitRunnable() {
            @Override
            public void run() {
                timeSinceClick++;
                recoilCount--;
                if(recoilCount<=0)recoil=0;
            }
        }.runTaskTimer(Artid.plug, 0,1);
        new BukkitRunnable(){
            @Override
            public void run() {
                if(p.getPlayer().getInventory().getItemInMainHand().equals(item) && timeSinceClick<5) {
                    shootPing();
                }
            }
        }.runTaskTimer(Artid.plug, 0, fireRate);
    }

    public void shoot(){
        if(isReloading && ammo>0){
            isReloading=false;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if(!cooldown && !isReloading) {
                    if(item.getType().equals(Material.DIAMOND_SHOVEL)) {

                    }
                    else {
                        cooldown = true;
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                cooldown = false;
                            }
                        }.runTaskLater(Artid.plug, fireRate);
                    }
                    if (ammo >= 1) {
                        ammo--;
                        if(ammo!=0) item.setAmount(ammo); else reload();
                        World w = Bukkit.getPlayer(playerUUID).getWorld();
                        Player p = Bukkit.getPlayer(playerUUID);
                        p.getInventory().setItem(slot,item);
                        Location loc = p.getPlayer().getLocation();
                        double rand;

                        if (p.getPlayer().isSneaking()) loc.add(0, p.getEyeHeight(), 0);
                        if (!p.getPlayer().isSneaking()) loc.add(0, p.getEyeHeight(), 0);
                        if(!p.getPlayer().isOnGround()) {
                            rand = (Math.random() - .5) * (spread +5);
                            loc.setYaw(loc.getYaw() + (float) rand);
                            loc.setPitch(loc.getPitch() - (recoil));
                        }
                        else{

                            switch(item.getType()){
                                default:
                                    rand = (Math.random() - .5) * (spread);
                                    loc.setYaw(loc.getYaw() + (float) rand);
                                    loc.setPitch(loc.getPitch() - recoil);
                                    break;
                                case NETHERITE_SWORD:
                                    if(snipeState>0){

                                    } else{
                                        rand = (Math.random() - .5) * (spread);
                                        loc.setYaw(loc.getYaw() + (float) rand);
                                        loc.setPitch(loc.getPitch() - recoil);
                                    }
                                    break;
                                case DIAMOND_SHOVEL:
                                    rand = (ThreadLocalRandom.current().nextFloat()-.5) * spread;
                                    loc.setYaw(loc.getYaw() + (float) rand);
                                    loc.setPitch(loc.getPitch() + (float) rand);
                                    break;
                            }
                        }


                        rayTracer ray = new rayTracer(loc.toVector(), loc.getDirection());
                        ray.highlight(w, p, 100, 1);
                        w.playSound(loc, soundEffect, 5f, 1f);
                        new BukkitRunnable() {
                            boolean killed = false;
                            boolean headShot =false;
                            @Override
                            public void run() {
                                for (Entity ent : w.getNearbyEntities(loc, 100, 100, 100)) {
                                    if (ray.intersects(ent.getBoundingBox(), 200, .05, w,p)) {
                                        if (ent.getName() != p.getName() && ent instanceof Player) {
                                            BoundingBox head = ent.getBoundingBox().clone();
                                            head.shift(0,((LivingEntity) ent).getEyeHeight()-.15,0);
                                            if(head.contains(ray.getPos()) || ray.intersects(head,100,.05,w,p)){
                                                ((LivingEntity) ent).damage((damage*2 / 5) - ray.getWalls()/20);
                                                headShot = true;
                                            }else {
                                                ((LivingEntity) ent).damage((damage / 5) - ray.getWalls()/20);
                                                if(((LivingEntity) ent).getHealth()<=0) killed = true;
                                            }
                                            ((LivingEntity) ent).setNoDamageTicks(0);
                                            p.playSound(loc, soundEffect, 1f, 1f);
                                            p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);

                                            if(killed){
                                                Bukkit.broadcastMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE +" " +gunImg+" " + ChatColor.GREEN + ent.getName()));
                                                p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                            }
                                            if(headShot){
                                                Bukkit.broadcastMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE +" " +gunImg+"鉰 " + ChatColor.GREEN + ent.getName()));
                                                p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                            }

                                        }
                                    }

                                }
                                if(p.getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_SWORD)) {
                                    snipeState = 0;
                                    unScope();
                                }

                            }
                        }.runTask(Artid.plug);
                        if(recoil < maxRecoil){
                            recoil += .5;
                        }
                        recoilCount = 20;
                        if(ammo<1){
                            reload();
                        }
                    }
                }
            }
        }.runTaskAsynchronously(Artid.plug);


    }
    public void reload(){
        if(!isReloading && ammo!=maxAmmo) {
            World w = Bukkit.getPlayer(playerUUID).getWorld();
            Player p = Bukkit.getPlayer(playerUUID);
            w.playSound(p.getLocation(), "mcgo.gamesounds.reloading",1,1);
            w.playSound(p.getLocation(), "mcgo.random.reload_start", 1, 1);
            isReloading=true;
            new BukkitRunnable() {
                int counter = 1;
                int inc = item.getType().getMaxDurability() / 10;

                @Override
                public void run() {
                        if (isReloading) {
                            if (Bukkit.getPlayer(playerUUID).getInventory().getItemInMainHand().getType().equals(item.getType())) {
                                item.setDurability((short) (item.getType().getMaxDurability() - (inc * counter)));
                                updateItem();
                                if (counter >= 11) {
                                    isReloading = false;
                                    item.setDurability((short) (0));
                                    item.setAmount(maxAmmo);
                                    ammo = maxAmmo;
                                    w.playSound(p.getLocation(), "mcgo.random.reload_end", 1, 1);
                                    updateItem();
                                    this.cancel();
                                }
                                w.playSound(p.getLocation(), "mcgo.random.reload_fire", 1, 1);
                                counter++;
                            }
                        } else {
                            item.setDurability((short) (0));
                            updateItem();
                            this.cancel();
                        }

                }

            }.runTaskTimer(Artid.plug, 0, (long) this.reloadTime / 10);
        }


    }
    public void unScope(){
        Player p = Bukkit.getPlayer(playerUUID);
        snipeState = 0;
        p.getInventory().setHelmet(new ItemStack(Material.AIR));

        p.setWalkSpeed(0.2f);
        PacketContainer FOV = new PacketContainer(PacketType.Play.Server.ABILITIES);
        FOV.getFloat().write(0,.05f);
        FOV.getBooleans().write(2, p.getAllowFlight());
        FOV.getFloat().write(1, .1f);


        if(p.getGameMode().equals(GameMode.CREATIVE)) FOV.getBooleans().write(2, true);
        else FOV.getBooleans().write(2, false);
        try {
            Artid.protocolManager.sendServerPacket(p, FOV);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(
                    "Cannot send packet " + FOV, ex);
        }

    }
    public void pump(int numShots){
        for(int i=0; i<numShots; i++){
            shoot();
        }
    }

    public void printStats(){
        Bukkit.getPlayer(playerUUID).sendMessage("Ammo: "+ammo +"\nMax Ammo: "+maxAmmo+"\nDamage: "+damage);
    }

    public void setSpread(float s){
        spread = s;
    }
    public void setMaxRecoil(float r){
        maxRecoil = r;
    }
    public int getSnipeState(){
        return snipeState;
    }
    public void setSnipeState(int i){
        snipeState = i;
    }
    public void setTimeSinceClick(float time){
        timeSinceClick = time;
    }

    public void updateItem(){
        Bukkit.getPlayer(playerUUID).getInventory().setItem(slot,item);
    }

    public int getAmmo() {
        return ammo;
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
    public void setAmmo(int ammo){
        this.ammo=ammo;
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
    public boolean getCooldown(){
        return cooldown;
    }
    public void setCooldown(boolean c){
        cooldown = c;
    }
    public boolean getIsReloading(){
        return isReloading;
    }
    public void setIsReloading(boolean s){
        isReloading = s;
    }


    public void shoot(int numShots){
        if(isReloading && ammo>0){
            isReloading=false;
        }
        if (!cooldown && !isReloading) {
            cooldown=true;
            if (ammo >= 1) {
                for (int i = 0; i < numShots; i++) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {


                            if (ammo != 0) item.setAmount(ammo);
                            else reload();
                            World w = Bukkit.getPlayer(playerUUID).getWorld();
                            Player p = Bukkit.getPlayer(playerUUID);
                            p.getInventory().setItemInMainHand(item);
                            Location loc = p.getPlayer().getLocation();
                            double rand;

                            if (p.getPlayer().isSneaking()) loc.add(0, 1.35, 0);
                            if (!p.getPlayer().isSneaking()) loc.add(0, 1.75, 0);

                            rand = (ThreadLocalRandom.current().nextFloat() - .5) * spread;
                            loc.setYaw(loc.getYaw() + (float) rand);
                            rand = (ThreadLocalRandom.current().nextFloat() - .5) * spread;
                            loc.setPitch(loc.getPitch() + (float) rand);

                            rayTracer ray = new rayTracer(loc.toVector(), loc.getDirection());
                            ray.highlight(w, p, 100, 1);

                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    for (Entity ent : w.getNearbyEntities(loc, 100, 100, 100)) {
                                        if (ray.intersects(ent.getBoundingBox(), 200, .05, w,p)) {
                                            if (ent.getName() != p.getName() && ent instanceof LivingEntity) {

                                                ((LivingEntity) ent).damage(damage / 5);
                                                ((LivingEntity) ent).setNoDamageTicks(0);
                                                p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                                if (ent.isDead()) {
                                                    p.getPlayer().sendMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE + " 銈銉 " + ChatColor.GREEN + ent.getName()));
                                                    p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                                }
                                            }
                                        }
                                    }
                                }
                            }.runTask(Artid.plug);
                        }
                    }.runTaskAsynchronously(Artid.plug);
                }
                Bukkit.getPlayer(playerUUID).getWorld().playSound(Bukkit.getPlayer(playerUUID).getLocation(), soundEffect, 5f, 1f);
                ammo--;
                if (ammo < 1) {
                    reload();
                }
            }
            new BukkitRunnable(){
                @Override
                public void run() {
                    cooldown=false;
                }
            }.runTaskLater(Artid.plug, fireRate);
        }
    }



    public void glow(BoundingBox b){
        World w = Bukkit.getPlayer(playerUUID).getWorld();
        w.spawnParticle(Particle.FLAME, b.getMax().toLocation(w), 0);
        w.spawnParticle(Particle.FLAME, b.getMin().toLocation(w), 0);

    }


    public void shootPing(){
        if(isReloading && ammo>0){
            isReloading=false;
        }
        if(!isReloading){
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!cooldown && !isReloading) {
                        if (!item.getType().equals(Material.DIAMOND_SHOVEL)) {
                            cooldown = true;
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    cooldown = false;
                                }
                            }.runTaskLater(Artid.plug, fireRate);
                        }
                        if (ammo >= 1) {
                            ammo--;
                            if (ammo != 0) item.setAmount(ammo);
                            else reload();
                            World w = Bukkit.getPlayer(playerUUID).getWorld();
                            Player p = Bukkit.getPlayer(playerUUID);
                            p.getInventory().setItem(slot, item);
                            Location loc = p.getPlayer().getLocation();
                            double rand;

                            if (p.getPlayer().isSneaking()) loc.add(0, p.getEyeHeight(), 0);
                            if (!p.getPlayer().isSneaking()) loc.add(0, p.getEyeHeight(), 0);
                            if (!p.getPlayer().isOnGround()) {
                                rand = (Math.random() - .5) * (spread + 10);
                                loc.setYaw(loc.getYaw() + (float) rand);
                                rand = (Math.random() - .5) * (spread + 10);
                                loc.setPitch(loc.getPitch() - (recoil) + (float) rand);
                            } else {
                                SecureRandom rando = new SecureRandom();
                                switch (item.getType()) {

                                    default:
                                        rand = (Math.random() - .5) * (spread);
                                        loc.setYaw(loc.getYaw() + (float) rand);
                                        loc.setPitch(loc.getPitch() - recoil);
                                        break;
                                    case NETHERITE_SWORD:
                                        if (snipeState > 0) {

                                        } else {
                                            rand = (Math.random() - .5) * (spread + 5);
                                            loc.setYaw(loc.getYaw() + (float) rand);
                                            rand = (Math.random() - .5) * (spread + 5);
                                            loc.setPitch(loc.getPitch() + (float) rand);
                                        }
                                        break;
                                    case DIAMOND_SHOVEL:
                                        rand = (ThreadLocalRandom.current().nextFloat() - .5) * spread;
                                        loc.setYaw(loc.getYaw() + (float) rand);
                                        loc.setPitch(loc.getPitch() + (float) rand);
                                        break;
                                    case GOLDEN_AXE:
                                        rand = (rando.nextFloat() - .5) * (spread);
                                        loc.setYaw(loc.getYaw() + (float) rand);
                                        rand = (rando.nextFloat() - .5) * (spread);
                                        loc.setPitch((float) (loc.getPitch() - recoil + rand));
                                        break;
                                }
                            }


                            rayTracer ray = new rayTracer(loc.toVector(), loc.getDirection());
                            ray.highlight(w, p, 100, 1);
                            w.playSound(loc, soundEffect, 5f, 1f);
                            new BukkitRunnable() {
                                boolean killed = false;
                                boolean headShot = false;

                                @Override
                                public void run() {
                                    double fallOff;
                                    for (Map.Entry<String, mcgoPlayer> entry : Artid.mcPlayers.entrySet()) {
                                        mcgoPlayer mc = entry.getValue();
                                        Player ent = mc.player;
                                        fallOff=Math.ceil(ent.getLocation().distance(p.getLocation()) * fallOffAmount);
                                        if (!ent.isDead()) {
                                            int ping = (((CraftPlayer) Bukkit.getPlayer(playerUUID)).getHandle().ping);
                                            BoundingBox box;
                                            if (mc.getBox((ping / 50) + 3) != null) {
                                                box = mc.getBox((ping / 50) + 4);
                                            } else return;
                                            if (ray.intersects(box, 200, .05, w, p)) {
                                                if (ent.getName() != p.getName() && ent instanceof Player) {
                                                    BoundingBox head = box.clone();
                                                    head.shift(0, ((LivingEntity) ent).getEyeHeight() - .2, 0);
                                                    if (head.contains(ray.getPos()) || ray.intersects(head, 100, .05, w, p)) {
                                                            if(ray.getWalls()>0){
                                                                ((LivingEntity) ent).damage((((damage-fallOff) * 2 *(Math.pow(.5,ray.getWalls())))/ 5));
                                                                damageEvent.damageMarker(ent.getLocation(),((CraftWorld)w).getHandle(),p,(float)((damage-fallOff) * 2 *(Math.pow(.5,ray.getWalls()))));
                                                            }
                                                            else {
                                                                ((LivingEntity) ent).damage(((damage-fallOff) * 2)/ 5);
                                                                damageEvent.damageMarker(ent.getLocation(),((CraftWorld)w).getHandle(),p,((float) (damage-fallOff) * 2));
                                                            };
                                                            ent.setNoDamageTicks(0);
                                                            headShot = true;
                                                            p.playSound(p.getLocation(), "mcgo.random.headshot_shooter", 1, 1);
                                                            ent.playSound(ent.getLocation(), "mcgo.random.headshot_victim", 1, 1);
                                                    } else {
                                                        if(ray.getWalls()>0){
                                                            ((LivingEntity) ent).damage(((damage-fallOff) *(Math.pow(.5,ray.getWalls())))/ 5);
                                                            damageEvent.damageMarker(ent.getLocation(),((CraftWorld)w).getHandle(),p,(float) ((damage-fallOff) *(Math.pow(.5,ray.getWalls()))));
                                                        }
                                                        else {
                                                            ((LivingEntity) ent).damage(((damage-fallOff))/ 5);
                                                            damageEvent.damageMarker(ent.getLocation(),((CraftWorld)w).getHandle(),p,((float) (damage-fallOff)));
                                                        }
                                                            ent.setNoDamageTicks(0);
                                                    }
                                                    if (((LivingEntity) ent).getHealth() <= 0) {
                                                        killed = true;
                                                    }
                                                    ((LivingEntity) ent).setNoDamageTicks(0);
                                                    p.playSound(loc, soundEffect, 1f, 1f);
                                                    p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);


                                                    if (killed) {
                                                        mc.clearBoxs();
                                                        if (headShot) {
                                                            if (ray.getWalls() > 0) {
                                                                Bukkit.broadcastMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE + " " + gunImg + "鈀鉰 " + ChatColor.GREEN + ent.getName()));
                                                                p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                                            } else {
                                                                Bukkit.broadcastMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE + " " + gunImg + "鉰 " + ChatColor.GREEN + ent.getName()));
                                                                p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                                            }
                                                        } else {
                                                            if (ray.getWalls() > 0) {
                                                                Bukkit.broadcastMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE + " " + gunImg + "鈀 " + ChatColor.GREEN + ent.getName()));
                                                                p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                                            } else {
                                                                Bukkit.broadcastMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE + " " + gunImg + " " + ChatColor.GREEN + ent.getName()));
                                                                p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                    if (p.getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_SWORD)) {
                                        snipeState = 0;
                                        unScope();
                                    }

                                }
                            }.runTask(Artid.plug);
                            if (recoil < maxRecoil) {
                                recoil += .5;
                            }
                            recoilCount = 20;
                            if (ammo < 1) {
                                reload();
                            }
                        }
                    }
                }
            }.runTaskAsynchronously(Artid.plug);
        }

    }

    public void shootPing(int numShots){
        if(isReloading && ammo>0){
            isReloading=false;
        }
        final boolean[] allKilled = {false};
        for(int i = 0; i<numShots; i++) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!cooldown && !isReloading) {
                        if (!item.getType().equals(Material.DIAMOND_SHOVEL)) {
                            cooldown = true;
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    cooldown = false;
                                }
                            }.runTaskLater(Artid.plug, fireRate);
                        }
                        if (ammo >= 1) {
                            ammo--;
                            if (ammo != 0) item.setAmount(ammo);
                            else reload();
                            World w = Bukkit.getPlayer(playerUUID).getWorld();
                            Player p = Bukkit.getPlayer(playerUUID);
                            p.getInventory().setItemInMainHand(item);
                            Location loc = p.getPlayer().getLocation();
                            double rand;

                            if (p.getPlayer().isSneaking()) loc.add(0, p.getEyeHeight(), 0);
                            if (!p.getPlayer().isSneaking()) loc.add(0, p.getEyeHeight(), 0);
                            if (!p.getPlayer().isOnGround()) {
                                rand = (Math.random() - .5) * (spread + 5);
                                loc.setYaw(loc.getYaw() + (float) rand);
                                loc.setPitch(loc.getPitch() - (recoil));
                            } else {
                                SecureRandom rando = new SecureRandom();
                                switch (item.getType()) {

                                    default:
                                        rand = (Math.random() - .5) * (spread);
                                        loc.setYaw(loc.getYaw() + (float) rand);
                                        loc.setPitch(loc.getPitch() - recoil);
                                        break;
                                    case NETHERITE_SWORD:
                                        if (snipeState > 0) {

                                        } else {
                                            rand = (Math.random() - .5) * (spread + 5);
                                            loc.setYaw(loc.getYaw() + (float) rand);
                                            rand = (Math.random() - .5) * (spread + 5);
                                            loc.setPitch(loc.getPitch() + (float) rand);
                                        }
                                        break;
                                    case DIAMOND_SHOVEL:
                                        rand = (ThreadLocalRandom.current().nextFloat() - .5) * spread;
                                        loc.setYaw(loc.getYaw() + (float) rand);
                                        loc.setPitch(loc.getPitch() + (float) rand);
                                        break;
                                    case GOLDEN_AXE:
                                        rand = (rando.nextFloat() - .5) * (spread);
                                        loc.setYaw(loc.getYaw() + (float) rand);
                                        rand = (rando.nextFloat() - .5) * (spread);
                                        loc.setPitch((float) (loc.getPitch() - recoil + rand));
                                        break;
                                }
                            }


                            rayTracer ray = new rayTracer(loc.toVector(), loc.getDirection());
                            ray.highlight(w, p, 100, 1);
                            w.playSound(loc, soundEffect, 5f, 1f);
                            new BukkitRunnable() {
                                boolean killed = false;
                                boolean headShot = false;

                                @Override
                                public void run() {
                                    for (Map.Entry<String, mcgoPlayer> entry : Artid.mcPlayers.entrySet()) {
                                        mcgoPlayer mc = entry.getValue();
                                        Player ent = mc.player;
                                        if (!ent.isDead()) {
                                            int ping = (((CraftPlayer) Bukkit.getPlayer(playerUUID)).getHandle().ping);
                                            BoundingBox box;
                                            if (mc.getBox((ping / 50) + 4) != null) {
                                                box = mc.getBox((ping / 50) + 4);
                                            } else return;
                                            if (ray.intersects(box, 200, .05, w,p)) {
                                                if (ent.getName() != p.getName() && ent instanceof Player) {
                                                    BoundingBox head = box.clone();
                                                    head.shift(0, ((LivingEntity) ent).getEyeHeight() - .15, 0);
                                                    if (head.contains(ray.getPos()) || ray.intersects(head, 100, .05,w,p)) {
                                                        ((LivingEntity) ent).damage((damage * 2 / 5) - (ray.getWalls() / 20));
                                                        //damageEvent.damageMarker(ent.getLocation(),((CraftWorld)w).getHandle(),p,damage*2/20);
                                                        ent.setNoDamageTicks(0);
                                                        headShot = true;
                                                        p.playSound(p.getLocation(), "mcgo.random.headshot_shooter", 1, 1);
                                                        ent.playSound(ent.getLocation(), "mcgo.random.headshot_victim", 1, 1);
                                                    } else {
                                                        ((LivingEntity) ent).damage((damage / 5) - ray.getWalls() / 20);
                                                        //damageEvent.damageMarker(ent.getLocation(),((CraftWorld)w).getHandle(),p,damage/20);
                                                        ent.setNoDamageTicks(0);
                                                    }
                                                    if (((LivingEntity) ent).getHealth() <= 0) {
                                                        killed = true;
                                                    }
                                                    ((LivingEntity) ent).setNoDamageTicks(0);
                                                    p.playSound(loc, soundEffect, 1f, 1f);
                                                    p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);

                                                    if (killed && !allKilled[0]) {
                                                        if (headShot) {
                                                            if (ray.getWalls() > 0) {
                                                                Bukkit.broadcastMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE + " " + gunImg + "鈀鉰 " + ChatColor.GREEN + ent.getName()));
                                                                p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                                                allKilled[0] =true;
                                                            } else {
                                                                Bukkit.broadcastMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE + " " + gunImg + "鉰 " + ChatColor.GREEN + ent.getName()));
                                                                p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                                                allKilled[0] =true;
                                                            }
                                                        } else {
                                                            if (ray.getWalls() > 0) {
                                                                Bukkit.broadcastMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE + " " + gunImg + "鈀 " + ChatColor.GREEN + ent.getName()));
                                                                p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                                                allKilled[0] =true;
                                                            } else {
                                                                Bukkit.broadcastMessage(ChatColor.BOLD + (ChatColor.AQUA + p.getPlayer().getName() + ChatColor.WHITE + " " + gunImg + " " + ChatColor.GREEN + ent.getName()));
                                                                p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                                                allKilled[0] =true;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                    }
                                    if (p.getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_SWORD)) {
                                        snipeState = 0;
                                        unScope();
                                    }

                                }
                            }.runTask(Artid.plug);
                            if (recoil < maxRecoil) {
                                recoil += .5;
                            }
                            recoilCount = 20;
                            if (ammo < 1) {
                                reload();
                            }
                        }
                    }
                }
            }.runTaskAsynchronously(Artid.plug);
        }

    }

}

