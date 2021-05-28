package events.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class playerToggleSneakEvent implements Listener {
    boolean isSneaking = false;
    @EventHandler
    public void shift(PlayerToggleSneakEvent e) throws IOException {
        mcgoPlayer mcPlayer = Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString());
        if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHERITE_SWORD)) {
            if (!e.getPlayer().isSneaking()) {
                isSneaking = true;
                CraftPlayer player = (CraftPlayer) e.getPlayer();

                PacketContainer FOV = new PacketContainer(PacketType.Play.Server.ABILITIES);

                FOV.getFloat().write(0,.05f);
                FOV.getBooleans().write(2, true);

//                switch (sniper.snipeState){
//                    case 0:
//                        player.setWalkSpeed(0.1f);
//                        FOV.getFloat().write(1, .15f);
//                        e.getPlayer().getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));
//                        //mcPlayer.mcSniper.snipeState++;
//                        break;
//                    case 1:
//                        player.setWalkSpeed(0.05f);
//                        FOV.getFloat().write(1, 10f);
//                        //mcPlayer.mcSniper.snipeState++;
//                        break;
//                    case 2:
//                        player.setWalkSpeed(0.2f);
//                        FOV.getFloat().write(1, .1f);
//                        e.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
//                        //mcPlayer.mcSniper.snipeState = 0;
//                }
//                try {
//                    Artid.protocolManager.sendServerPacket(player, FOV);
//                } catch (InvocationTargetException ex) {
//                    throw new RuntimeException(
//                            "Cannot send packet " + FOV, ex);
//                }
            }
            else{
                isSneaking = false;
            }
        }

        // SCOPED RIFLE
        if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.GOLDEN_AXE)) {
            if (!e.getPlayer().isSneaking()) {
                isSneaking = true;
                CraftPlayer player = (CraftPlayer) e.getPlayer();

                PacketContainer FOV = new PacketContainer(PacketType.Play.Server.ABILITIES);

                FOV.getFloat().write(1, .15f);
                e.getPlayer().getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));

                try {
                    Artid.protocolManager.sendServerPacket(player, FOV);
                } catch (InvocationTargetException ex) {
                    throw new RuntimeException(
                            "Cannot send packet " + FOV, ex);
                }
            }
            else{
                isSneaking = false;
                //mcPlayer.sr.resetSnipe(mcPlayer.player);
            }
        }
    }
}
