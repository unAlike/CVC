package events.player;

import com.mojang.datafixers.util.Pair;
import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import inventories.bigBomb;
import inventories.gameShop;
import inventories.shop;
import inventories.smallBomb;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.EntitySnowball;
import net.minecraft.server.v1_16_R3.EnumItemSlot;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityEquipment;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftSnowball;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerInteract implements Listener {
    @EventHandler
    public void rightClick(PlayerInteractEvent e){
        shop shop = new shop();
        gameShop gameShop = new gameShop();
        bigBomb bigBomb = new bigBomb();
        smallBomb smallBomb = new smallBomb();
        mcgoPlayer p = Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString());


        if (e.getItem() != null) {
            if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))&&e.getHand().equals(EquipmentSlot.HAND)) {

                switch (e.getItem().getType()) {
                    default:
                        if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.setCancelled(true);
                        break;
                    case GOLDEN_APPLE:
                        break;
                    case GOLD_NUGGET:
                        if(p.hasGameId()){
                            if(e.getClickedBlock()!=null) {
                                if (e.getClickedBlock().getType() == Material.CHEST) {
                                    p.player.openInventory(bigBomb.getInv());
                                }
                            }
                        }
                        break;
                    case SHEARS:
                        if(p.hasGameId()){
                            if(e.getClickedBlock()!=null) {
                                if (e.getClickedBlock() == Artid.games.get(p.gameUUID).bombLocation) {
                                    p.player.openInventory(smallBomb.getInv());
                                }
                            }
                        }
                        break;
                    case GRAY_DYE:
                        if(p.hasGameId()){
                            p.ready = true;
                            p.player.getInventory().setItem(7,new ItemStack(Material.LIME_DYE));
                        }
                        break;
                    case LIME_DYE:
                        if(p.hasGameId()){
                            p.ready = false;
                            p.player.getInventory().setItem(7,new ItemStack(Material.GRAY_DYE));
                        }
                        break;
                    case GHAST_TEAR:
                        if(p.hasGameId()) {
                            if(Artid.games.get(p.gameUUID).copPlayers.contains(p)){
                                if(!Artid.games.get(p.gameUUID).preGame && Artid.games.get(p.gameUUID).getSpawnLocations(p.player.getWorld().getName()).getCopSpawn().contains(p.player.getBoundingBox()) && Artid.games.get(p.gameUUID).canBuyWeapon) {
                                    e.getPlayer().openInventory(p.gameShop.getInv());
                                    p.player.playSound(p.player.getLocation(),"mcgo.shop.shopmenuopen",1,1);
                                }else p.player.sendMessage(ChatColor.RED + "You must be in your team's spawn to buy.");
                            }
                            else {
                                e.getPlayer().openInventory(shop.getInv());
                                p.player.playSound(p.player.getLocation(),"mcgo.shop.shopmenuopen",1,1);
                            }
                        }
                        else{
                            e.getPlayer().openInventory(shop.getInv());
                            p.player.playSound(p.player.getLocation(),"mcgo.shop.shopmenuopen",1,1);
                        }
                        break;
                    case COMPASS:
                        if(e.getItem().getItemMeta().getDisplayName()=="Set Spawn") {
                            e.getPlayer().setBedSpawnLocation(e.getPlayer().getLocation(), false);
                            e.getPlayer().sendMessage("Spawn has been set.");
                        }
                        else{
                            e.getPlayer().sendMessage("Follow the compass to find the bomb!");
                        }
                        break;
                        //########################## HEAVY GUNS #######################################################
                    case NETHERITE_SWORD:
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain()!=null) {
                            if(!Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain().getCooldown() && !Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain().getIsReloading()) {
                                p.getMain().shootPing(e.getPlayer().getWorld(),e.getPlayer());
                                e.setCancelled(true);
                            }
                        }


                        break;
                    case IRON_AXE: case STONE_SHOVEL: case GOLDEN_SHOVEL: case STONE_HOE: case GOLDEN_AXE:
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain()!=null) {
                            p.getMain().setTimeSinceClick(0);
                            e.setCancelled(true);
                        }
                        break;
                    case NETHERITE_SHOVEL:
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain()!=null) {
                            if(!p.famasOnCooldown){
                                p.famasOnCooldown = true;
                                new BukkitRunnable(){
                                    int i = 0;
                                    @Override
                                    public void run() {
                                        if(i<3){
                                            p.getMain().shootPing(p.player.getWorld(), p.player);
                                            p.getMain().setCooldown(false);
                                            i++;
                                        }
                                        else{
                                            new BukkitRunnable(){
                                                @Override
                                                public void run() {
                                                    p.getMain().setRecoil(0);
                                                    p.famasOnCooldown = false;
                                                }
                                            }.runTaskLater(Artid.plug,2);
                                            this.cancel();
                                        }
                                    }
                                }.runTaskTimer(Artid.plug, 0, 2);
                            }
                        }
                        break;
                    case DIAMOND_SHOVEL:
                        e.setCancelled(true);
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain()!=null && !Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain().getCooldown()) {
                            p.getMain().shootPing(9);
                        }
                        break;
                    case WOODEN_AXE:
                        e.setCancelled(true);
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain()!=null && !Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain().getCooldown()) {
                            p.getMain().shootPing(6);
                        };
                        break;
                        //############################ PISTOLS #########################################################
                    case WOODEN_PICKAXE: case GOLDEN_PICKAXE:
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getOffhand()!=null) {
                            e.setCancelled(true);
                            p.getOffhand().shootPing(e.getPlayer().getWorld(),e.getPlayer());
                        }
                        break;
                    case STONE_PICKAXE:
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getOffhand()!=null) {
                            e.setCancelled(true);
                            if(!p.hkOnCooldown) {
                                p.hkOnCooldown = true;
                                p.getOffhand().setCooldown(false);
                                p.getOffhand().shootPing(e.getPlayer().getWorld(),e.getPlayer());
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        p.getOffhand().setCooldown(false);
                                        p.getOffhand().shootPing(e.getPlayer().getWorld(),e.getPlayer());
                                        p.getOffhand().setCooldown(false);
                                    }
                                }.runTaskLater(Artid.plug, 4);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        p.hkOnCooldown = false;
                                    }
                                }.runTaskLater(Artid.plug, 12);
                            }
                        }
                        break;
//#################################################### START GRENADES ##################################################
                    case OAK_SAPLING:
                        throwNade(e, e.getPlayer(), Material.OAK_SAPLING);
                        e.getPlayer().getInventory().remove(Material.OAK_SAPLING);
                        e.getPlayer().setExp(0.00000000000000000001f);
                        break;
                    case DARK_OAK_SAPLING:
                        throwNade(e, e.getPlayer(), Material.DARK_OAK_SAPLING);
                        e.getPlayer().getInventory().remove(Material.DARK_OAK_SAPLING);
                        e.getPlayer().setExp(0.00000000000000000001f);
                        break;
                    case ACACIA_SAPLING:
                        throwNade(e, e.getPlayer(), Material.ACACIA_SAPLING);
                        e.getPlayer().getInventory().remove(Material.ACACIA_SAPLING);
                        e.getPlayer().setExp(0.00000000000000000001f);
                        break;
                    case BIRCH_SAPLING:
                        throwNade(e, e.getPlayer(), Material.BIRCH_SAPLING);
                        e.getPlayer().getInventory().remove(Material.BIRCH_SAPLING);
                        e.getPlayer().setExp(0.00000000000000000001f);
                        break;
                    case JUNGLE_SAPLING:
                        throwNade(e, e.getPlayer(), Material.JUNGLE_SAPLING);
                        e.getPlayer().getInventory().remove(Material.JUNGLE_SAPLING);
                        e.getPlayer().setExp(0.00000000000000000001f);
                        break;

                }
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ////////////////////////////////////////// LEFT CLICK //////////////////////////////////////////////////////
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////
            if ((e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) && e.getHand().equals(EquipmentSlot.HAND)) {
                            /////////////// HEAVY GUNS ///////////////////////
                //Bukkit.broadcastMessage("Left Clcik!");
                switch(e.getPlayer().getInventory().getItemInMainHand().getType()){
                    case WOODEN_AXE: case IRON_AXE: case STONE_SHOVEL: case GOLDEN_SHOVEL: case NETHERITE_SWORD: case STONE_HOE: case GOLDEN_AXE: case DIAMOND_SHOVEL: case NETHERITE_SHOVEL:
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain().reload();
                    break;
                        /////////////////PISTOLS////////////////////////////
                case WOODEN_PICKAXE: case STONE_PICKAXE: case GOLDEN_PICKAXE:
                    if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getOffhand()!=null){
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getOffhand().getAmmo()!=Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getOffhand().getMaxAmmo()) {
                            Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getOffhand().reload();
                        }
                    }
                    break;
                    //////////////////////////NADES//////////////////////////////
                    case OAK_SAPLING: case BIRCH_SAPLING: case DARK_OAK_SAPLING: case ACACIA_SAPLING: case JUNGLE_SAPLING:
                        if(!Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).hypixelNades) {
                            if (e.getPlayer().getExp() + .24999 >= 1) {
                                e.getPlayer().setExp(0.00000000000000000001f);
                            } else e.getPlayer().setExp(e.getPlayer().getExp() + .2499999999999f);
                        }
                        break;
                }
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        else {e.setCancelled(false);}
    }

    public void throwNade(PlayerInteractEvent e, Player p, Material m){
        e.setCancelled(true);

        Snowball ball = e.getPlayer().getWorld().spawn(p.getEyeLocation(), Snowball.class);
        ball.setCustomName(e.getPlayer().getDisplayName());
        ItemStack item = new ItemStack(m);

        ball.setShooter(e.getPlayer());
        ball.setBounce(true);
        ball.setItem(new ItemStack(Material.CLAY_BALL));

        ArmorStand as = e.getPlayer().getWorld().spawn(p.getEyeLocation().add(0,-10,0), ArmorStand.class);
        as.setVisible(false);
        as.setMarker(true);
        as.setInvulnerable(true);
        as.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.REMOVING_OR_CHANGING);
        as.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING_OR_CHANGING);
        as.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);
        as.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        as.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        as.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
        as.setRightArmPose(new EulerAngle(0,45,50));
        as.setLeftArmPose(new EulerAngle(0,45,-50));
        as.setArms(true);
        as.setCollidable(false);
        as.getEquipment().setItemInMainHand(item);
        ball.addPassenger(as);
        if(!Artid.mcPlayers.get(p.getUniqueId().toString()).hypixelNades) {
            ball.setVelocity(e.getPlayer().getLocation().getDirection().multiply(e.getPlayer().getExp() * 1.25).add(e.getPlayer().getVelocity()));
        }
        else{
            ball.setVelocity(e.getPlayer().getLocation().getDirection().multiply(1).add(e.getPlayer().getVelocity()));
        }
    }
}