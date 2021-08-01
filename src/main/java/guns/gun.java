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
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class gun implements Cloneable {
    int fireRate;
    int maxAmmo;
    int ammo;
    int recoilCount = 0;
    int snipeState=0;
    int slot=0;

    float reloadTime;
    float damage;
    float maxRecoil;
    float spread;
    float recoil;
    float timeSinceClick=10;
    float fallOffAmount = 1;

    boolean cooldown = false;
    boolean isReloading = false;

    ItemStack item;
    String soundEffect;
    String name;
    //String UUID;
    String gunImg;

    java.util.UUID playerUUID;



    public Object clone() throws CloneNotSupportedException {
        gun g = (gun) super.clone();
        g.setItem(g.getItem().clone());
        return g;
    }

    public gun(int fireRate, float reloadTime, float damage, int maxAmmo, int ammo, ItemStack item, float maxRecoil, float spread, String soundEffect, String name, String gunImg, int slot, float fallOffAmount){
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
        this.gunImg = gunImg;
        this.slot=slot;
        this.fallOffAmount = fallOffAmount;
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + name);
        item.setItemMeta(meta);
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
        this.gunImg = gunImg;
        this.playerUUID = p.getUniqueId();
        this.slot=slot;
        this.fallOffAmount = fallOffAmount;
        this.playerUUID = java.util.UUID.randomUUID();
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
            final Player p = Bukkit.getPlayer(playerUUID);
            final World w = Bukkit.getPlayer(playerUUID).getWorld();
            @Override
            public void run() {
                if(Bukkit.getPlayer(playerUUID).getInventory().getItemInMainHand().equals(item) && timeSinceClick<5) {
                    shootPing(w,p);
                }
            }
        }.runTaskTimer(Artid.plug, 0, fireRate);
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
        p.setWalkSpeed(0.18f);
        PacketContainer FOV = new PacketContainer(PacketType.Play.Server.ABILITIES);
        FOV.getFloat().write(0,.05f);
        FOV.getBooleans().write(2, p.getAllowFlight());
        FOV.getFloat().write(1, .1f);
        try {
            Artid.protocolManager.sendServerPacket(p, FOV);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(
                    "Cannot send packet " + FOV, ex);
        }

    }

    public void shootPing(World w, Player p){
        if(isReloading && ammo>0){
            isReloading=false;
        }
        if(!isReloading){
            Location loc = p.getLocation();
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
//                            World w = Bukkit.getPlayer(playerUUID).getWorld();
//                            Player p = Bukkit.getPlayer(playerUUID);
                            p.getInventory().setItem(slot, item);
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
//                                        rand = (rando.nextFloat() - .5) * (spread);
//                                        loc.setYaw(loc.getYaw() + (float) rand);
//                                        rand = (rando.nextFloat() - .5) * (spread);
//                                        loc.setPitch((float) (loc.getPitch() - recoil + rand));
                                        p.getLocation().setPitch(p.getLocation().getPitch()+1);
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
                                    for (Map.Entry<String, mcgoPlayer> entry : Artid.mcPlayers.entrySet() ) {
                                        mcgoPlayer mc = entry.getValue();
                                        Player ent = mc.player;


                                        if (!ent.isDead()) {
                                            int ping = (((CraftPlayer) Bukkit.getPlayer(playerUUID)).getHandle().ping);
                                            BoundingBox box;
                                            if (mc.getBox((ping / 50) + 4) != null) {
                                                box = mc.getBox((ping / 50) + 4);
                                            } else return;
                                            if (ray.intersects(box, 200, .05, w, p)) {
                                                fallOff=Math.floor(ent.getLocation().distance(p.getLocation()) * fallOffAmount);
                                                if (ent.getName() != p.getName() && ent instanceof Player) {
                                                    BoundingBox head = box.clone();
                                                    head.shift(0, ((LivingEntity) ent).getEyeHeight() - .2, 0);
                                                    if (head.contains(ray.getPos()) || ray.intersects(head, 100, .05, w, p)) {
                                                            if(ray.getWalls()>0 && ray.getHitBlocks().get(0).getLocation().distance(p.getLocation()) > ent.getLocation().distance(p.getLocation())){
                                                                if(ent.getDisplayName().equals("Bot")){
                                                                    p.sendMessage(ChatColor.RED + "" + ((((damage) * 2 * (Math.pow(.5, ray.getWalls()))) - fallOff))+ " Damage"+ ". Walls: "+ ray.getWalls() + ChatColor.GOLD);
                                                                    damageEvent.damageMarker(ent.getLocation(),((CraftWorld)w).getHandle(),p,(float)(((damage) * 2 *(Math.pow(.5,ray.getWalls())))-fallOff));
                                                                    ((LivingEntity) ent).damage(0.00001);
                                                                }
                                                                else {
                                                                    ((LivingEntity) ent).damage(((((damage) * 2 * (Math.pow(.5, ray.getWalls()))) - fallOff) / 5));
                                                                    damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, (float) (((damage) * 2 * (Math.pow(.5, ray.getWalls()))) - fallOff));
                                                                }
                                                            }
                                                            else {
                                                                if(ent.getDisplayName().equals("Bot")) {
                                                                    p.sendMessage(ChatColor.RED + "" + (((damage) * 2) - fallOff) + " Damage");
                                                                    damageEvent.damageMarker(ent.getLocation(),((CraftWorld)w).getHandle(),p,((float) (((damage) * 2)-fallOff)));
                                                                    ((LivingEntity) ent).damage(0.00001);
                                                                }
                                                                else {
                                                                    ((LivingEntity) ent).damage((((damage) * 2) - fallOff) / 5);
                                                                    damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, ((float) (((damage) * 2) - fallOff)));
                                                                }
                                                            };
                                                            ent.setNoDamageTicks(0);
                                                            headShot = true;
                                                            p.playSound(p.getLocation(), "mcgo.random.headshot_shooter", 1, 1);
                                                            ent.playSound(ent.getLocation(), "mcgo.random.headshot_victim", 1, 1);
                                                    } else {
                                                        if(ray.getWalls()>0 && ray.getHitBlocks().get(0).getLocation().distance(p.getLocation()) > ent.getLocation().distance(p.getLocation())){
                                                            if(ent.getDisplayName().equals("Bot")) {
                                                                p.sendMessage(ChatColor.RED + "" + (((damage) * (Math.pow(.5, ray.getWalls()))) - fallOff)+ " Damage. Walls: "+ ray.getWalls() + ChatColor.GOLD);
                                                                damageEvent.damageMarker(ent.getLocation(),((CraftWorld)w).getHandle(),p,(float) (((damage) *(Math.pow(.5,ray.getWalls())))-fallOff));
                                                                ((LivingEntity) ent).damage(0.00001);
                                                            }
                                                            else {
                                                                ((LivingEntity) ent).damage((((damage) * (Math.pow(.5, ray.getWalls()))) - fallOff) / 5);
                                                                damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, (float) (((damage) * (Math.pow(.5, ray.getWalls()))) - fallOff));
                                                            }
                                                        }
                                                        else {
                                                            if(ent.getDisplayName().equals("Bot")) {
                                                                p.sendMessage(ChatColor.RED + "" + (((damage)) - fallOff)+ " Damage");
                                                                damageEvent.damageMarker(ent.getLocation(),((CraftWorld)w).getHandle(),p,((float) (damage-fallOff)));
                                                                ((LivingEntity) ent).damage(0.00001);
                                                            }
                                                            else {
                                                                ((LivingEntity) ent).damage((((damage)) - fallOff) / 5);
                                                                damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, ((float) (damage - fallOff)));
                                                            }
                                                        }
                                                            ent.setNoDamageTicks(0);
                                                    }
                                                    if (((LivingEntity) ent).getHealth() <= 0) {
                                                        killed = true;
                                                        if(ent.getDisplayName().equals("Bot")){
                                                            ent.setHealth(100f);
                                                        }
                                                    }
                                                    ((LivingEntity) ent).setNoDamageTicks(0);
                                                    p.playSound(loc, soundEffect, 1f, 1f);
                                                    p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);


                                                    if (killed) {
                                                        mcgoPlayer killer = Artid.mcPlayers.get(p.getUniqueId().toString());
                                                        killer.kills++;
                                                        killer.killStreak++;
                                                        killer.lobbysb.update();
                                                        killer.gamesb.update();
                                                        String deathMsg = ChatColor.AQUA + p.getDisplayName() +" ";
                                                        if(mc.blind) deathMsg = deathMsg.concat(ChatColor.WHITE +"鈃");
                                                        if((gunImg.equals("銄銅") || gunImg.equals("銘銙")) && snipeState == 0) deathMsg = deathMsg.concat(ChatColor.WHITE + "鈄");
                                                        deathMsg = deathMsg.concat(ChatColor.WHITE + gunImg);
                                                        if(ray.isThroughSmoke()) deathMsg = deathMsg.concat("鈂");
                                                        if(ray.getWalls()>0) deathMsg = deathMsg.concat("鈀");
                                                        if(headShot) deathMsg = deathMsg.concat("鉰");

                                                        deathMsg = deathMsg.concat(" "+ChatColor.GREEN + ent.getDisplayName());
                                                        Bukkit.broadcastMessage(deathMsg);
                                                        p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                                        mc.clearBoxs();
                                                    }
                                                }
                                            }
                                        }

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

    public void shootPing(int numshots){
        final boolean[] killed = new boolean[1];
        final boolean[] walled = new boolean[1];
        final boolean[] headshot = new boolean[1];
        final boolean[] smoke = new boolean[1];
        final boolean[] blind = new boolean[1];
        ArrayList<Player> ents = new ArrayList<Player>();
        if (isReloading && ammo > 0) {
            isReloading = false;
        }

        if(!cooldown) {
            cooldown = true;
            new BukkitRunnable() {
                @Override
                public void run() {
                    cooldown = false;
                }
            }.runTaskLater(Artid.plug, fireRate);

            if (!isReloading) {
                Bukkit.getPlayer(this.playerUUID).getWorld().playSound(Bukkit.getPlayer(this.playerUUID).getLocation(), soundEffect, 5f, 1f);
                for (int i = 0; i < numshots; i++) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!isReloading) {
                                if (true) {
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
                                        SecureRandom rando2 = new SecureRandom();
                                        switch (item.getType()) {

                                            default:
                                                rand = (Math.random() - .5) * (spread);
                                                loc.setYaw(loc.getYaw() + (float) rand);
                                                loc.setPitch(loc.getPitch() - recoil);
                                                break;
                                            case DIAMOND_SHOVEL:
                                            case WOODEN_AXE:
                                                rand = (rando.nextFloat() - .5) * spread;
                                                loc.setYaw(loc.getYaw() + (float) rand);
                                                rand = (rando2.nextFloat() - .5) * spread;
                                                loc.setPitch(loc.getPitch() + (float) rand);
                                                break;
                                        }
                                    }


                                    rayTracer ray = new rayTracer(loc.toVector(), loc.getDirection());
                                    ray.highlight(w, p, 100, 1);
                                    new BukkitRunnable() {
                                        @Override
                                        public void run() {
                                            double fallOff;
                                            for (Map.Entry<String, mcgoPlayer> entry : Artid.mcPlayers.entrySet()) {
                                                mcgoPlayer mc = entry.getValue();
                                                Player ent = mc.player;


                                                if (!ent.isDead()) {
                                                    int ping = (((CraftPlayer) Bukkit.getPlayer(playerUUID)).getHandle().ping);
                                                    BoundingBox box;
                                                    if (mc.getBox((ping / 50) + 4) != null) {
                                                        box = mc.getBox((ping / 50) + 4);
                                                    } else return;
                                                    if (ray.intersects(box, 200, .05, w, p)) {
                                                        fallOff = Math.floor(ent.getLocation().distance(p.getLocation()) * fallOffAmount);
                                                        if (ent.getName() != p.getName() && ent instanceof Player) {
                                                            BoundingBox head = box.clone();
                                                            head.shift(0, ((LivingEntity) ent).getEyeHeight() - .2, 0);
                                                            if (head.contains(ray.getPos()) || ray.intersects(head, 100, .05, w, p)) {
                                                                if (ray.getWalls() > 0) {
                                                                    if(ent.getDisplayName().equals("Bot")){
                                                                        damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, (float) (((damage) * 2 * (Math.pow(.5, ray.getWalls()))) - fallOff));
                                                                    }
                                                                    else {
                                                                        ((LivingEntity) ent).damage(((((damage) * 2 * (Math.pow(.5, ray.getWalls()))) - fallOff) / 5));
                                                                        damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, (float) (((damage) * 2 * (Math.pow(.5, ray.getWalls()))) - fallOff));
                                                                    }
                                                                } else {
                                                                    if(ent.getDisplayName().equals("Bot")){
                                                                        damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, ((float) (((damage) * 2) - fallOff)));
                                                                    }
                                                                    else {
                                                                        ((LivingEntity) ent).damage((((damage) * 2) - fallOff) / 5);
                                                                        damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, ((float) (((damage) * 2) - fallOff)));
                                                                    }
                                                                }

                                                                ent.setNoDamageTicks(0);
                                                                headshot[0] = true;
                                                                p.playSound(p.getLocation(), "mcgo.random.headshot_shooter", 1, 1);
                                                                ent.playSound(ent.getLocation(), "mcgo.random.headshot_victim", 1, 1);
                                                            } else {
                                                                if (ray.getWalls() > 0) {
                                                                    if(ent.getDisplayName().equals("Bot")){
                                                                        damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, (float) (((damage) * (Math.pow(.5, ray.getWalls()))) - fallOff));
                                                                    }
                                                                    else {
                                                                        ((LivingEntity) ent).damage((((damage) * (Math.pow(.5, ray.getWalls()))) - fallOff) / 5);
                                                                        damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, (float) (((damage) * (Math.pow(.5, ray.getWalls()))) - fallOff));
                                                                    }
                                                                } else {
                                                                    if(ent.getDisplayName().equals("Bot")){
                                                                        damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, ((float) (damage - fallOff)));
                                                                    }
                                                                    else {
                                                                        ((LivingEntity) ent).damage((((damage)) - fallOff) / 5);
                                                                        damageEvent.damageMarker(ent.getLocation(), ((CraftWorld) w).getHandle(), p, ((float) (damage - fallOff)));
                                                                    }
                                                                }
                                                                ent.setNoDamageTicks(0);
                                                            }
                                                            if (((LivingEntity) ent).getHealth() <= 0) {
                                                                killed[0] = true;
                                                            }
                                                            ((LivingEntity) ent).setNoDamageTicks(0);
                                                            p.playSound(loc, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);


                                                            if (killed[0]) {
                                                                mcgoPlayer killer = Artid.mcPlayers.get(p.getUniqueId().toString());
                                                                killer.kills++;
                                                                killer.killStreak++;
                                                                killer.lobbysb.update();
                                                                killer.gamesb.update();
                                                                if (!ents.contains(ent)) ents.add(ent);
                                                                if (mc.blind) blind[1] = true;
                                                                if (ray.isThroughSmoke()) smoke[0] = true;
                                                                if (ray.getWalls() > 0) walled[0] = true;
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
                                }
                            }
                        }
                    }.runTaskAsynchronously(Artid.plug);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Player ent : ents) {
                                ents.remove(ent);
                                Player p = Bukkit.getPlayer(playerUUID);
                                String deathMsg = ChatColor.AQUA + Bukkit.getPlayer(playerUUID).getDisplayName() + " ";
                                if (blind[0]) deathMsg = deathMsg.concat(ChatColor.WHITE + "鈃");
                                if ((gunImg.equals("銄銅") || gunImg.equals("銘銙")) && snipeState == 0)
                                    deathMsg = deathMsg.concat(ChatColor.WHITE + "鈄");
                                deathMsg = deathMsg.concat(ChatColor.WHITE + gunImg);
                                if (smoke[0]) deathMsg = deathMsg.concat("鈂");
                                if (walled[0]) deathMsg = deathMsg.concat("鈀");
                                if (headshot[0]) deathMsg = deathMsg.concat("鉰");

                                deathMsg = deathMsg.concat(" " + ChatColor.GREEN + ent.getDisplayName());
                                Bukkit.broadcastMessage(deathMsg);
                                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f);
                                Artid.mcPlayers.get(ent.getUniqueId().toString()).clearBoxs();
                            }
                        }
                    }.runTaskLater(Artid.plug, 5);
                }
            }
        }
        if (ammo >= 1) {
            ammo--;
            if (ammo != 0) item.setAmount(ammo);
        }
        if(ammo==0){
            new BukkitRunnable() {
                @Override
                public void run() {
                    reload();
                }
            }.runTaskLater(Artid.plug, 2);

        }
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
    public void setPlayerUUID(UUID uid){
        this.playerUUID = uid;
        new BukkitRunnable() {
            @Override
            public void run() {
                timeSinceClick++;
                recoilCount--;
                if(recoilCount<=0)recoil=0;
            }
        }.runTaskTimer(Artid.plug, 0,1);
        new BukkitRunnable(){
            final Player p = Bukkit.getPlayer(playerUUID);
            final World w = Bukkit.getPlayer(playerUUID).getWorld();
            @Override
            public void run() {
                if(Bukkit.getPlayer(playerUUID).getInventory().getItemInMainHand().equals(item) && timeSinceClick<5) {
                    shootPing(w,p);
                }
            }
        }.runTaskTimer(Artid.plug, 0, fireRate);
    }
    public void updateItem(){
        Bukkit.getPlayer(playerUUID).getInventory().setItem(slot,item);
    }
    public void updateItem(int slot){
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
}

