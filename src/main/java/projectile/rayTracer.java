package projectile;

import groupid.artid.Artid;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class rayTracer {

    //origin = start position
    //direction = direction in which the raytrace will go
    Vector origin, direction;

    public rayTracer(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    //get a point on the raytrace at X blocks away
    public Vector getPosition(double blocksAway) {
        return origin.clone().add(direction.clone().multiply(blocksAway));
    }

    //checks if a position is on contained within the position
    public boolean isOnLine(Vector position) {
        double t = (position.getX() - origin.getX()) / direction.getX();

        if (position.getBlockY() == origin.getY() + (t * direction.getY()) && position.getBlockZ() == origin.getZ() + (t * direction.getZ())) {
            return true;
        }
        return false;
    }

    //get all postions on a raytrace
    public ArrayList<Vector> traverse(double blocksAway, double accuracy) {
        ArrayList<Vector> positions = new ArrayList<>();
        for (double d = 0; d <= blocksAway; d += accuracy) {

            positions.add(getPosition(d));

        }
        return positions;
    }
    public ArrayList<Vector> traverse(double blocksAway, double accuracy, World world) {
        ArrayList<Vector> positions = new ArrayList<>();
        for (double d = 0; d <= blocksAway; d += accuracy) {
            Vector pos = getPosition(d);

            Block block = world.getBlockAt((int)Math.floor(pos.getX()),(int)Math.floor(pos.getY()),(int)Math.floor(pos.getZ()));
            switch (block.getType()){
                case OAK_LOG: case COAL_ORE: case OAK_LEAVES: case IRON_ORE: case DIAMOND_ORE: case ACACIA_LEAVES: case BIRCH_LEAVES: case DARK_OAK_LEAVES: case SPRUCE_LEAVES:
                case WHITE_STAINED_GLASS_PANE: case GLASS_PANE: case HAY_BLOCK: case GLASS:
                    Material type = block.getType();
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            block.setType(type);
                        }
                    }.runTaskLater(Artid.plug, 20);
                    block.setType(Material.AIR);

                default:
                    if(block.getBoundingBox().contains(pos.getX(),pos.getY(),pos.getZ())) return positions;
                case AIR:
                case CAVE_AIR:
                case WATER:
                case GRASS:
                case TALL_GRASS:
                case SNOW:
                case FIRE:
                case WHEAT:
                case BARRIER:
                case SPRUCE_SIGN:
                case OAK_SIGN:
                case BIRCH_SIGN:
                case JUNGLE_SIGN:
                case DARK_OAK_SIGN:



            }
            positions.add(getPosition(d));


        }
        return positions;
    }

    //intersection detection for current raytrace with return
    public Vector positionOfIntersection(Vector min, Vector max, double blocksAway, double accuracy) {
        ArrayList<Vector> positions = traverse(blocksAway, accuracy);
        for (Vector position : positions) {
            if (intersects(position, min, max)) {
                return position;
            }
        }
        return null;
    }

    //intersection detection for current raytrace
    public boolean intersects(Vector min, Vector max, double blocksAway, double accuracy) {
        ArrayList<Vector> positions = traverse(blocksAway, accuracy);
        for (Vector position : positions) {
            if (intersects(position, min, max)) {
                return true;
            }
        }
        return false;
    }

    //bounding box instead of vector
    public Vector positionOfIntersection(BoundingBox boundingBox, double blocksAway, double accuracy) {
        ArrayList<Vector> positions = traverse(blocksAway, accuracy);
        for (Vector position : positions) {
            if (intersects(position, boundingBox.getMin(), boundingBox.getMax())) {
                return position;
            }
        }
        return null;
    }

    //bounding box instead of vector
    public boolean intersects(BoundingBox boundingBox, double blocksAway, double accuracy) {
        ArrayList<Vector> positions = traverse(blocksAway, accuracy);
        for (Vector position : positions) {
            if (intersects(position, boundingBox.getMin(), boundingBox.getMax())) {
                return true;
            }
        }
        return false;
    }
    public boolean intersects(BoundingBox boundingBox, double blocksAway, double accuracy, World world) {
        ArrayList<Vector> positions = traverse(blocksAway, accuracy, world);
        for (Vector position : positions) {
            if (intersects(position, boundingBox.getMin(), boundingBox.getMax())) {
                return true;
            }
        }
        return false;
    }

    //general intersection detection
    public static boolean intersects(Vector position, Vector min, Vector max) {
        if (position.getX() < min.getX() || position.getX() > max.getX()) {
            return false;
        } else if (position.getY() < min.getY() || position.getY() > max.getY()) {
            return false;
        } else if (position.getZ() < min.getZ() || position.getZ() > max.getZ()) {
            return false;
        }
        return true;
    }

    //debug / effects
    public void highlight(World world, double blocksAway, double accuracy){
        for(Vector position : traverse(blocksAway,accuracy, world)){
            world.spawnParticle(Particle.FLAME, position.toLocation(world), 0);
        }
    }

    public boolean getEntityOnLine(Entity e, World world){
        Vector x1, x2, z1, z2;
        x1 = origin.clone().add(direction.clone().multiply((e.getBoundingBox().getMinX() - origin.clone().getX())/direction.clone().getX()));
        x2 = origin.clone().add(direction.clone().multiply((e.getBoundingBox().getMaxX() - origin.clone().getX())/direction.clone().getX()));
        z1 = origin.clone().add(direction.clone().multiply((e.getBoundingBox().getMinZ() - origin.clone().getZ())/direction.clone().getZ()));
        z2 = origin.clone().add(direction.clone().multiply((e.getBoundingBox().getMaxZ() - origin.clone().getZ())/direction.clone().getZ()));

        if(intersects(x1, e.getBoundingBox().getMin(), e.getBoundingBox().getMax())) return true;
        if(intersects(x2, e.getBoundingBox().getMin(), e.getBoundingBox().getMax())) return true;
        if(intersects(z1, e.getBoundingBox().getMin(), e.getBoundingBox().getMax())) return true;
        if(intersects(z2, e.getBoundingBox().getMin(), e.getBoundingBox().getMax())) return true;
        Bukkit.getConsoleSender().sendMessage("Missed");
        return false;
    }

}