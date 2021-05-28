package events.entity;

import groupid.artid.Artid;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftSnowball;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import runnables.molly;
import runnables.nadeBlow;

public class entityHit implements Listener {
    Material lastMaterial;
    boolean molley = false;
    @EventHandler
    public void snowballHit(ProjectileHitEvent e){
        molley = false;
        if(e.getHitEntity() instanceof Player){
            for (Entity ar : e.getEntity().getPassengers()) {
                if (ar instanceof ArmorStand) {
                    switch(((ArmorStand) ar).getEquipment().getItemInMainHand().getType()){
                        case OAK_SAPLING:
                            nadeBlow.nade(((ArmorStand) ar), ar.getWorld());
                            ar.remove();
                            break;
                        case BIRCH_SAPLING:
                            nadeBlow.flash(((ArmorStand) ar),ar.getWorld());
                            ar.remove();
                            break;
                        case DARK_OAK_SAPLING:
                            nadeBlow.smoke(((ArmorStand) ar),ar.getWorld());
                            ar.remove();
                            break;
                        case JUNGLE_SAPLING:
                            nadeBlow.decoy(((ArmorStand) ar),ar.getWorld());
                            ar.remove();
                            break;
                        case ACACIA_SAPLING:
                            BukkitRunnable mol = new molly((ArmorStand) ar);
                            if (((ArmorStand) ar).getEquipment().getItemInMainHand().getType().equals(Material.ACACIA_SAPLING)) {
                                ar.getWorld().playSound(ar.getLocation(), "mcgo.throwables.explodefirebomb", 1, 1);
                                ar.remove();
                                mol.runTaskTimer(Artid.plug, 0, 5);
                                molley = true;
                            }
                    }
                }
            }
        }
        if(e.getEntity().getTicksLived()>65)e.getEntity().remove();
        if(e.getHitBlockFace()!=null){
            if(e.getEntity().getVelocity().getY() < .2 && e.getEntity().getVelocity().getY() > -.2 && e.getHitBlockFace().equals(BlockFace.UP)){
                //e.getEntity().getPassengers().remove(0);
                e.getEntity().remove();
            }
            else {
                for (Entity ent : e.getEntity().getPassengers()) {
                    if (ent instanceof ArmorStand)
                        lastMaterial = ((ArmorStand) ent).getEquipment().getItemInMainHand().getType();
                    ent.remove();
                }
                Snowball ball = null;
                switch (e.getHitBlockFace()) {
                    default:
                        ball = e.getEntity().getWorld().spawn(e.getEntity().getLocation(), Snowball.class);
                        break;
                    case EAST:
                        ball = e.getEntity().getWorld().spawn(e.getEntity().getLocation().add(new Vector(.5, 0, 0)), Snowball.class);
                        break;
                    case WEST:
                        ball = e.getEntity().getWorld().spawn(e.getEntity().getLocation().add(new Vector(-.5, 0, 0)), Snowball.class);
                        break;
                    case NORTH:
                        ball = e.getEntity().getWorld().spawn(e.getEntity().getLocation().add(new Vector(0, 0, -.5)), Snowball.class);
                        break;
                    case SOUTH:
                        ball = e.getEntity().getWorld().spawn(e.getEntity().getLocation().add(new Vector(0, 0, .5)), Snowball.class);
                        break;
                    case UP:
                        for (Entity ent : e.getEntity().getPassengers()) {
                            if (ent instanceof ArmorStand) {
                                BukkitRunnable mol = new molly((ArmorStand) ent);
                                if (((ArmorStand) ent).getEquipment().getItemInMainHand().getType().equals(Material.ACACIA_SAPLING)) {
                                    ent.getWorld().playSound(ent.getLocation(), "mcgo.throwables.explodefirebomb", 1, 1);
                                    ent.remove();
                                    mol.runTaskTimer(Artid.plug, 0, 5);
                                    molley = true;
                                }
                            }

                        }
                        ball = e.getEntity().getWorld().spawn(e.getEntity().getLocation().add(new Vector(0, .1, 0)), Snowball.class);
                        break;
                    case DOWN:
                        ball = e.getEntity().getWorld().spawn(e.getEntity().getLocation().add(new Vector(0, -.1, 0)), Snowball.class);
                        break;

                }
                if (!molley) {

                    ItemStack item = new ItemStack(lastMaterial);
                    ball.setShooter(e.getEntity().getShooter());
                    ball.setTicksLived(e.getEntity().getTicksLived());
                    ball.setItem(new ItemStack(Material.CLAY_BALL));
                    ball.setRotation(90, 90);


                    ArmorStand as = e.getEntity().getWorld().spawn(e.getEntity().getLocation(), ArmorStand.class);
                    as.setTicksLived(ball.getTicksLived());
                    as.setVisible(false);
                    as.setInvulnerable(true);
                    as.setMarker(true);
                    as.setInvulnerable(true);
                    as.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.REMOVING_OR_CHANGING);
                    as.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING_OR_CHANGING);
                    as.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);
                    as.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
                    as.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
                    as.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
                    as.setRightArmPose(new EulerAngle(0, 45, 50));
                    as.setLeftArmPose(new EulerAngle(0, 45, -50));
                    as.setArms(true);
                    as.setCollidable(false);
                    as.setRotation(90, 90);
                    as.getEquipment().setItemInMainHand(item);
                    ball.addPassenger(as);


                    switch (e.getHitBlockFace()) {
                        default:
                            ball.setVelocity(new Vector(e.getEntity().getVelocity().getX() * -.8, e.getEntity().getVelocity().getY(), e.getEntity().getVelocity().getZ() * -.8));
                        case UP:
                        case DOWN:
                            ball.setVelocity(new Vector(e.getEntity().getVelocity().getX(), e.getEntity().getVelocity().getY() * -.8, e.getEntity().getVelocity().getZ()));
                            break;
                        case EAST:
                        case WEST:
                            ball.setVelocity(new Vector(e.getEntity().getVelocity().getX() * -.8, e.getEntity().getVelocity().getY(), e.getEntity().getVelocity().getZ()));
                            break;
                        case NORTH:
                        case SOUTH:
                            ball.setVelocity(new Vector(e.getEntity().getVelocity().getX(), e.getEntity().getVelocity().getY(), e.getEntity().getVelocity().getZ() * -.8));

                    }
                }
            }
        }
    }
}
