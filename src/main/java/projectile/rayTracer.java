package projectile;

import bot.CVCBot;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import groupid.artid.Artid;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class rayTracer {

    //origin = start position
    //direction = direction in which the raytrace will go
    Vector origin, direction;
    Vector posit;
    Player hitPlayer;
    int walls = 0;
    boolean throughSmoke = false;
    ArrayList<Block> hitBlocks = new ArrayList<Block>();

    public rayTracer(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    //get a point on the raytrace at X blocks away
    public Vector getPosition(double blocksAway) {
        return origin.clone().add(direction.clone().multiply(blocksAway));
    }

    //get all postions on a raytrace
    public ArrayList<Vector> traverse(double blocksAway, double accuracy) {
        ArrayList<Vector> positions = new ArrayList<>();
        for (double d = 0; d <= blocksAway; d += accuracy) {
            positions.add(getPosition(d));
        }
        return positions;
    }
    public ArrayList<Vector> traverse(double blocksAway, double accuracy, World world, Player player) {
        ArrayList<Vector> positions = new ArrayList<>();
        int count = 0;
        boolean hitEntity = false;
        outer: for (double d = 0; d <= blocksAway; d += accuracy) {
            Vector pos = getPosition(d);
            for(Player p : world.getPlayers()){
                if(Artid.mcPlayers.get(p.getUniqueId().toString()).player.getBoundingBox().contains(pos) && Artid.mcPlayers.get(p.getUniqueId().toString()).player!=player) {
                    for(int i=0; i<100; i++){
                        positions.add(getPosition(d+(accuracy*i)));
                    }
                    return positions;
                }
            }
            for(CVCBot bot : Artid.bots){
                if(bot.getBoundingBox().d(new Vec3D(pos.getX(),pos.getY(),pos.getZ())) && bot!=player) {
                    positions.add(getPosition(d+accuracy));
                    positions.add(getPosition(d+(accuracy*2)));
                    return positions;
                }
            }
            Block block = world.getBlockAt((int)Math.floor(pos.getX()),(int)Math.floor(pos.getY()),(int)Math.floor(pos.getZ()));


            switch (block.getType()){


                case COAL_ORE: case OAK_LEAVES: case IRON_ORE: case DIAMOND_ORE: case ACACIA_LEAVES: case BIRCH_LEAVES: case DARK_OAK_LEAVES: case SPRUCE_LEAVES:
                case WHITE_STAINED_GLASS_PANE: case GLASS_PANE: case HAY_BLOCK: case GLASS: case EMERALD_ORE: case JUNGLE_LEAVES: case LIGHT_GRAY_STAINED_GLASS_PANE:
                case BLACK_STAINED_GLASS_PANE: case BLUE_STAINED_GLASS_PANE: case BROWN_STAINED_GLASS_PANE: case PINK_STAINED_GLASS_PANE: case CYAN_STAINED_GLASS_PANE: case PURPLE_STAINED_GLASS_PANE:
                case GRAY_STAINED_GLASS_PANE: case LIGHT_BLUE_STAINED_GLASS_PANE: case MAGENTA_STAINED_GLASS_PANE: case GREEN_STAINED_GLASS_PANE: case LIME_STAINED_GLASS_PANE:
                case ORANGE_STAINED_GLASS_PANE: case RED_STAINED_GLASS_PANE: case YELLOW_STAINED_GLASS_PANE:
                    Material type;
                    BlockData data = block.getBlockData();
                    type = block.getType();
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            block.setType(type);
                            block.setBlockData(data);
                        }
                    }.runTaskLater(Artid.plug, 200);
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            block.setType(Material.AIR, false);
                        }
                    }.runTaskLater(Artid.plug,1);

                    return positions;


                default:
                    if(block.getBoundingBox().contains(pos.getX(),pos.getY(),pos.getZ())){
                        new BukkitRunnable(){
                            @Override
                            public void run() {
                                PacketPlayOutBlockBreakAnimation pack = new PacketPlayOutBlockBreakAnimation(new Random().nextInt(2000), new BlockPosition(block.getX(),block.getY(),block.getZ()), 3);
                                for(Player p : block.getWorld().getPlayers()) {
                                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(pack);
                                }
                            }
                        }.runTask(Artid.plug);
                        return positions;
                    }
                case ACACIA_LOG: case BIRCH_LOG: case DARK_OAK_LOG: case OAK_LOG: case JUNGLE_LOG: case SPRUCE_LOG: case ACACIA_PLANKS: case BIRCH_PLANKS: case CRIMSON_PLANKS:
                case DARK_OAK_PLANKS: case OAK_PLANKS: case JUNGLE_PLANKS: case SPRUCE_PLANKS: case JUNGLE_WOOD: case ACACIA_WOOD: case BIRCH_WOOD: case DARK_OAK_WOOD:
                case OAK_WOOD: case SPRUCE_WOOD: case STRIPPED_ACACIA_WOOD: case STRIPPED_BIRCH_WOOD: case STRIPPED_DARK_OAK_WOOD: case STRIPPED_JUNGLE_WOOD: case STRIPPED_OAK_WOOD:
                case STRIPPED_SPRUCE_WOOD: case SPRUCE_SLAB: case BIRCH_SLAB: case DARK_OAK_SLAB: case ACACIA_SLAB: case JUNGLE_SLAB: case OAK_SLAB: case PETRIFIED_OAK_SLAB:
                case SPRUCE_STAIRS: case OAK_STAIRS: case JUNGLE_STAIRS: case BIRCH_STAIRS: case ACACIA_STAIRS: case DARK_OAK_STAIRS: case CRIMSON_STAIRS: case WARPED_STAIRS:
                    if(hitBlocks!=null) {
                        if(block.getBoundingBox().contains(pos)) {
                            if (!hitEntity && !hitBlocks.contains(block)) {
                                walls++;
                                hitBlocks.add(block);
                                new BukkitRunnable(){
                                    @Override
                                    public void run() {
                                        PacketContainer packet1 = Artid.protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
                                        packet1.getIntegers().write(0, new Random().nextInt(2000)); //entityid
                                        packet1.getIntegers().write(1, 9); //stage

                                        PacketPlayOutBlockBreakAnimation pack = new PacketPlayOutBlockBreakAnimation(new Random().nextInt(2000), new BlockPosition(block.getX(),block.getY(),block.getZ()), 8);

                                        try {
                                            for(Player p : block.getWorld().getPlayers()) {
                                                Artid.protocolManager.sendServerPacket(p, packet1);
                                                ((CraftPlayer)p).getHandle().playerConnection.sendPacket(pack);
                                            }
                                        } catch (InvocationTargetException ex) {
                                            throw new RuntimeException(
                                                    "Cannot send packet " + packet1, ex);
                                        }
                                    }
                                }.runTask(Artid.plug);

                            }
                        }
                    }else{
                        hitBlocks.add(block);
                    }

                    break;

                case WHEAT:
                    throughSmoke = true;
                    break;
                case AIR: case CAVE_AIR: case WATER: case GRASS: case TALL_GRASS: case SNOW: case FIRE: case BARRIER: case SPRUCE_SIGN: case OAK_SIGN: case BIRCH_SIGN:
                case JUNGLE_SIGN: case DARK_OAK_SIGN: case BLACK_CARPET: case BLUE_CARPET: case BROWN_CARPET: case CYAN_CARPET: case GRAY_CARPET: case GREEN_CARPET: case LIGHT_BLUE_CARPET:
                case LIGHT_GRAY_CARPET: case LIME_CARPET: case MAGENTA_CARPET: case ORANGE_CARPET: case PINK_CARPET: case PURPLE_CARPET: case RED_CARPET: case WHITE_CARPET:
                case YELLOW_CARPET: case TRIPWIRE: case TRIPWIRE_HOOK: case IRON_BARS:



            }

            Location loc = new Location(world,pos.getX(),pos.getY(),pos.getZ());

            new BukkitRunnable(){
                @Override
                public void run() {

                    for(Entity e : world.getNearbyEntities(loc, 2,3,2)){
                        if(e.getBoundingBox().contains(pos)){
                            posit = pos;
                        }
                    }
                }
            }.runTask(Artid.plug);

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
    public boolean intersects(BoundingBox boundingBox, double blocksAway, double accuracy, World world,Player p) {
        ArrayList<Vector> positions = traverse(blocksAway, accuracy, world,p);
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
    public void highlight(World world, Player p, double blocksAway, double accuracy){
        int i = 0;
        for(Vector position : traverse(blocksAway,.1, world,p)){
            if(i>10){
                p.spawnParticle(Particle.FLAME, position.toLocation(world), 0);
                i=0;
            }
            i++;
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
    public Vector getPos(){
        return posit;
    }
    public int getWalls(){
        return walls;
    }
    public Player getHitEnt(){
        return hitPlayer;
    }
    public boolean isThroughSmoke(){
        return throughSmoke;
    }

}