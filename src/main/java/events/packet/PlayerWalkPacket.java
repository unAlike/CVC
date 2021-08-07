package events.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.*;
import groupid.artid.Artid;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;

public class PlayerWalkPacket extends PacketAdapter implements Listener {
    public PlayerWalkPacket(Plugin p, PacketType g) {
        super(p,g);
    }
    Player sender;
    @Override
    public void onPacketReceiving(PacketEvent event) {
        PacketContainer pc = event.getPacket();
        if(String.valueOf(pc.getSoundEffects().read(0)).contains("STEP")){
            if(!event.getPlayer().isSprinting()){

            }
        }
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        PacketContainer pc = event.getPacket();
        float x,y,z;
        x=pc.getIntegers().read(0)/8;
        y=pc.getIntegers().read(1)/8;
        z=pc.getIntegers().read(2)/8;

        if(String.valueOf(pc.getSoundEffects().read(0)).contains("STEP") && !String.valueOf(pc.getSoundEffects().read(0)).contains("LADDER")){
            for(Player p : event.getPlayer().getWorld().getPlayers()){
                if(p.getBoundingBox().expand(5).contains(new Vector(x,y,z)) && p != event.getPlayer()){
                    if(!p.isSprinting()){
                        event.setCancelled(true);
                    }
                    else{
                        event.getPacket().getFloat().write(0,event.getPacket().getFloat().read(0)*2);
                    }
                }
            }
        }
        else if(String.valueOf(pc.getSoundEffects().read(0)).contains("STEP") && String.valueOf(pc.getSoundEffects().read(0)).contains("LADDER")){
            for(Player p : event.getPlayer().getWorld().getPlayers()){
                if(p.getBoundingBox().expand(5).contains(new Vector(x,y,z)) && p != event.getPlayer()){
                    if(p.isSneaking()){
                        event.setCancelled(true);
                    }
                    else{
                        event.getPacket().getFloat().write(0,event.getPacket().getFloat().read(0)*2);
                    }
                }
            }
        }
    }
}
