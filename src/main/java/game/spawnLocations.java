package game;


import net.bytebuddy.asm.Advice;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.BoundingBox;

import java.lang.invoke.LambdaConversionException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public enum spawnLocations {
    SANDSTORM("Sandstorm", new Location[]{
            new Location(Bukkit.getWorld("Sandstorm"),-795.5,86,-203.5,0,0),
            new Location(Bukkit.getWorld("Sandstorm"),-754.5,86,-203.5,45,0),
            new Location(Bukkit.getWorld("Sandstorm"),-757.5,83,-107.5,145,0),
            new Location(Bukkit.getWorld("Sandstorm"),-816.5,80,-116.5,-180,0),
            new Location(Bukkit.getWorld("Sandstorm"),-845.5,86,-99.5,-180,0),
            new Location(Bukkit.getWorld("Sandstorm"),-809.5,84,-138.5,-146,0),
            new Location(Bukkit.getWorld("Sandstorm"),-852.5,85,-165.5,-45,0),
            new Location(Bukkit.getWorld("Sandstorm"),-837.5,84,-172.5,0,0),
        },  new BoundingBox(-823,80,-124,-809,85,-114), new BoundingBox(-799,86,-207, -786,90,-196),
            new BoundingBox(-841,86,-116,-832,89,-107), new BoundingBox(-774,82,-119,-765,85,-107));



    private ArrayList<Location> loc;
    private BoundingBox copSpawn, crimSpawn, aSite, bSite;
    private String worldName;
    spawnLocations(String w, Location[] locs, BoundingBox copSpawn, BoundingBox crimSpawn, BoundingBox aSite, BoundingBox bSite){
        this.worldName=w;
        this.copSpawn = copSpawn;
        this.crimSpawn = crimSpawn;
        this.aSite = aSite;
        this.bSite = bSite;
        loc = new ArrayList<>();
        for(Location l: locs){
            this.loc.add(l);
        }
    }
    public Location getRandomLocation(){
        Random random = new Random();
        return loc.get(random.nextInt(loc.size()));
    }
    public Location getCopSpawnpoint(){
        Location loc = new Location(Bukkit.getWorld(worldName),copSpawn.getCenterX(),copSpawn.getMinY(), copSpawn.getCenterZ(),-180f,0);
        return loc;
    }
    public BoundingBox getCopSpawn(){
        return copSpawn;
    }
    public BoundingBox getCrimSpawn(){
        return crimSpawn;
    }
    public BoundingBox getASite(){return aSite;}
    public BoundingBox getBSite(){return bSite;}
    public Location getCrimSpawnpoint(){
        Location loc = new Location(Bukkit.getWorld(worldName),crimSpawn.getCenterX(),crimSpawn.getMinY(), crimSpawn.getCenterZ());
        return loc;
    }

}
