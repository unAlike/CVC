package events.player;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import inventories.shop;
import net.minecraft.server.v1_16_R3.EntitySnowball;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
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

import java.util.Set;

public class PlayerInteract implements Listener {
    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        shop shop = new shop();
        mcgoPlayer p = Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString());
        if (e.getItem() != null) {
            if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))&&e.getHand().equals(EquipmentSlot.HAND)) {
                //Starts runnables & canncels events
                switch (e.getItem().getType()) {
                    case GHAST_TEAR:

                        e.getPlayer().openInventory(shop.getInv());
                        break;
                    case COMPASS:
                        e.getPlayer().setBedSpawnLocation(e.getPlayer().getLocation(), true);
                        e.getPlayer().sendMessage("Spawn has been set.");
                        break;
                        //########################## HEAVY GUNS #######################################################
                    case NETHERITE_SWORD:
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain()!=null) {
                            p.getMain().shootPing();
                            e.setCancelled(true);
                        }

                        break;
                    case IRON_AXE: case STONE_SHOVEL: case GOLDEN_SHOVEL: case STONE_HOE: case GOLDEN_AXE:
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain()!=null) {
                            e.setCancelled(true);
                            p.getMain().setTimeSinceClick(0);
                        }


                        break;
                    case DIAMOND_SHOVEL:
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain()!=null) {
                            e.setCancelled(true);
                            p.getMain().shoot(9);
                        }
                        break;
                    case WOODEN_AXE:
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain()!=null) {
                            e.setCancelled(true);
                            p.getMain().shoot(6);
                        };
                        break;
                        //############################ PISTOLS #########################################################
                    case WOODEN_PICKAXE: case GOLDEN_PICKAXE:
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getOffhand()!=null) {
                            e.setCancelled(true);
                            p.getOffhand().shootPing();
                        }
                        break;
                    case STONE_PICKAXE:
                        if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getOffhand()!=null) {
                            e.setCancelled(true);
                            if(!p.getOffhand().getCooldown()) {
                                p.getOffhand().shootPing();
                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        p.getOffhand().setCooldown(false);
                                        p.getOffhand().shootPing();
                                    }
                                }.runTaskLater(Artid.plug, 4);
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
                switch(e.getPlayer().getInventory().getItemInMainHand().getType()){
                    case WOODEN_AXE: case IRON_AXE: case STONE_SHOVEL: case GOLDEN_SHOVEL: case NETHERITE_SWORD: case STONE_HOE: case GOLDEN_AXE: case DIAMOND_SHOVEL:
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
                        if(e.getPlayer().getExp()+.24999 >= 1){
                            e.getPlayer().setExp(0.00000000000000000001f);
                        }
                        else e.getPlayer().setExp(e.getPlayer().getExp()+.2499999999999f);
                }
            }
            ////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        else {e.setCancelled(false);}
    }
    @EventHandler
    public void animationEvent(PlayerAnimationEvent e){
//
//        Block focused = e.getPlayer().getTargetBlock((Set<Material>) null, 5);
//        if(e.getAnimationType() == PlayerAnimationType.ARM_SWING && focused.getType() != Material.AIR) {
//            e.setCancelled(true);
//
//            switch(e.getPlayer().getInventory().getItemInMainHand().getType()){
//                    case WOODEN_AXE:
//                        break;
//                case IRON_AXE:
//                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getMain().reload();
//                    break;
//                        /////////////////PISTOLS////////////////////////////
//                case WOODEN_PICKAXE: case STONE_PICKAXE: case GOLDEN_PICKAXE:
//                    if(Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getOffhand()!=null)Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getOffhand().reload();
//                    break;
//                    //////////////////////////NADES//////////////////////////////
//                    case OAK_SAPLING: case BIRCH_SAPLING: case DARK_OAK_SAPLING: case ACACIA_SAPLING: case JUNGLE_SAPLING:
//                        if(e.getPlayer().getExp()+.24999 >= 1){
//                            e.getPlayer().setExp(0.00000000000000000001f);
//                        }
//                        else e.getPlayer().setExp(e.getPlayer().getExp()+.2499999999999f);
//                }
//        }
    }
    public void throwNade(PlayerInteractEvent e, Player p, Material m){
        e.setCancelled(true);

        Snowball ball = e.getPlayer().getWorld().spawn(p.getEyeLocation(), Snowball.class);
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
        ball.setVelocity(e.getPlayer().getLocation().getDirection().multiply(e.getPlayer().getExp()*1.25).add(e.getPlayer().getVelocity()));
    }
//    @EventHandler
//    public void idk(Player)

}