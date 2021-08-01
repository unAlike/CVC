package bot;

import com.mojang.authlib.GameProfile;
import groupid.artid.Artid;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.net.Socket;
import java.util.UUID;

public class CVCBot extends EntityPlayer {
    public CVCBot(World w, Player p){
        super(((CraftServer)Bukkit.getServer()).getServer(), ((CraftWorld)w).getHandle(), new GameProfile(UUID.randomUUID(), "Bot"), new PlayerInteractManager(((CraftWorld)w).getHandle()));
        playerInteractManager.b(EnumGamemode.SURVIVAL);
        playerConnection = new PlayerConnection(((CraftServer)Bukkit.getServer()).getServer(), new NetworkManager(EnumProtocolDirection.CLIENTBOUND), this);
        this.glowing = true;
        this.setLocation(p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ(),p.getLocation().getYaw(),p.getLocation().getPitch());
        for(Player all : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer)all).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this));
            connection.sendPacket(new PacketPlayOutEntityEffect(this.getId(),new MobEffect(MobEffectList.fromId(24), 100000, 1,true,true)));
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(this));
        }
    }
    public void destroy(){
        for(Player all : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer)all).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutEntityDestroy(this.getId()));
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, this));
        }
        Artid.mcPlayers.remove(this.getUniqueID().toString());
    }
    public void attack(){
        for(Player all : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer)all).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutEntityStatus(this,(byte)2));
        }
    }
    public void setGlowing(){
        this.addEffect(new MobEffect(MobEffectList.fromId(24),10,10000000));
        this.glowing = true;
        for(Player all : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer)all).getHandle().playerConnection;
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, this));
            connection.sendPacket(new PacketPlayOutEntityEffect(this.getId(),new MobEffect(MobEffectList.fromId(24), 100000, 10000000,true,true)));
        }
    }
}
