package events.player;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import inventories.shop;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
import org.bukkit.util.EulerAngle;

public class PlayerInteract implements Listener {
    @EventHandler
    public void rightClick(PlayerInteractEvent e) {
        shop shop = new shop();
        mcgoPlayer p = Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString());
        if (e.getItem() != null) {
            if ((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
                //Starts runnables & canncels events
                switch (e.getItem().getType()) {
                    case GHAST_TEAR:

                        e.getPlayer().openInventory(shop.getInv());
                        break;
                    case NETHERITE_SWORD:
                        e.setCancelled(true);
                        p.getMain().printStats();
                        p.getMain().shoot();

                        break;
                    case WOODEN_AXE:
                        e.setCancelled(true);

                        break;
                    case NETHERITE_SHOVEL:
                        e.setCancelled(true);

                        break;
                    case STONE_SHOVEL:
                        e.setCancelled(true);

                        break;
                    case DIAMOND_HOE:
                        e.setCancelled(true);

                        break;
                    case GOLDEN_AXE:
                        e.setCancelled(true);

                        break;
                        //############################ PISTOLS #########################################################
                    case WOODEN_PICKAXE:
                        e.setCancelled(true);
                        p.getOffhand().shoot();
                        p.getOffhand().printStats();
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
            //////////////////////////////////////////////////////////////////////////////////////////////////////////
//            if ((e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
//                switch(e.getItem().getType()){
//                    case WOODEN_AXE:
//
//                        break;
//                    case OAK_SAPLING: case BIRCH_SAPLING: case DARK_OAK_SAPLING: case ACACIA_SAPLING: case JUNGLE_SAPLING:
//                        if(e.getPlayer().getExp()+.24999 >= 1){
//                            e.getPlayer().setExp(0.00000000000000000001f);
//                        }
//                        else e.getPlayer().setExp(e.getPlayer().getExp()+.2499999999999f);
//                }
//            }
            //////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        else {e.setCancelled(false);}
    }
    @EventHandler
    public void animationEvent(PlayerAnimationEvent e){
        if(e.getAnimationType().equals(PlayerAnimationType.ARM_SWING)){
            e.setCancelled(true);
            switch(e.getPlayer().getInventory().getItemInMainHand().getType()){
                    case WOODEN_AXE:

                        break;
                case WOODEN_PICKAXE:
                    Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).getOffhand().reload();
                    break;
                    case OAK_SAPLING: case BIRCH_SAPLING: case DARK_OAK_SAPLING: case ACACIA_SAPLING: case JUNGLE_SAPLING:
                        if(e.getPlayer().getExp()+.24999 >= 1){
                            e.getPlayer().setExp(0.00000000000000000001f);
                        }
                        else e.getPlayer().setExp(e.getPlayer().getExp()+.2499999999999f);
                }
        }
    }
    public void throwNade(PlayerInteractEvent e, Player p, Material m){
        e.setCancelled(true);

        Snowball ball = e.getPlayer().getWorld().spawn(e.getPlayer().getEyeLocation(), Snowball.class);
        ItemStack item = new ItemStack(m);

        ball.setShooter(e.getPlayer());
        ball.setBounce(true);
        ball.setItem(new ItemStack(Material.CLAY_BALL));

        ArmorStand as = e.getPlayer().getWorld().spawn(e.getPlayer().getEyeLocation().add(0,-100,0), ArmorStand.class);
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
        ball.setVelocity(e.getPlayer().getLocation().getDirection().multiply(e.getPlayer().getExp()*1.25));
    }
}