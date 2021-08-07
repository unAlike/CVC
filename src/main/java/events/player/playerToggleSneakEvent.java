package events.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
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
        switch (e.getPlayer().getInventory().getItemInMainHand().getType()){
            case NETHERITE_SWORD:
                if (!e.getPlayer().isSneaking() && mcPlayer.getMain().getSnipeState()==0) {
                    isSneaking = true;
                    CraftPlayer player = (CraftPlayer) e.getPlayer();

                    PacketContainer FOV = new PacketContainer(PacketType.Play.Server.ABILITIES);

                    FOV.getFloat().write(0,.05f);
                    if(player.getGameMode().equals(GameMode.CREATIVE)) FOV.getBooleans().write(2, true);
                    else FOV.getBooleans().write(2, false);

                    player.setWalkSpeed(0.1f);
                    FOV.getFloat().write(1, .15f);
                    e.getPlayer().getInventory().setHelmet(new ItemStack(Material.CARVED_PUMPKIN));
                    mcPlayer.getMain().setSnipeState(1);

                    try {
                        Artid.protocolManager.sendServerPacket(player, FOV);
                    } catch (InvocationTargetException ex) {
                        throw new RuntimeException(
                                "Cannot send packet " + FOV, ex);
                    }
                }
                else {
                    isSneaking = false;
                    mcPlayer.getMain().setSnipeState(0);
                    mcPlayer.getMain().unScope();
                }
                break;
            case GOLDEN_AXE:
                CraftPlayer player = (CraftPlayer) e.getPlayer();
                PacketContainer FOV = new PacketContainer(PacketType.Play.Server.ABILITIES);
                if (!e.getPlayer().isSneaking() && mcPlayer.getMain().getSnipeState()==0) {
                    mcPlayer.getMain().setSnipeState(1);
                    mcPlayer.getMain().setFireRate(3);
                    mcPlayer.getMain().setMaxRecoil(0f);
                    mcPlayer.getMain().setSpread(1);
                    mcPlayer.getMain().setSnipeState(1);

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
                    mcPlayer.getMain().setFireRate(2);
                    mcPlayer.getMain().setMaxRecoil(8f);
                    mcPlayer.getMain().setSpread(12);
                    player.setWalkSpeed(0.17f);
                    mcPlayer.getMain().setSnipeState(0);
                    e.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
                    FOV.getFloat().write(1, .1f);
                    //mcPlayer.sr.resetSnipe(mcPlayer.player);
                    try {
                        Artid.protocolManager.sendServerPacket(player, FOV);
                    } catch (InvocationTargetException ex) {
                        throw new RuntimeException(
                                "Cannot send packet " + FOV, ex);
                    }
                }
                break;
            case OAK_SAPLING: case ACACIA_SAPLING: case BIRCH_SAPLING: case JUNGLE_SAPLING: case DARK_OAK_SAPLING:
                if(!e.getPlayer().isSneaking()) {
                    mcPlayer.player.playSound(mcPlayer.player.getLocation(), Sound.BLOCK_LEVER_CLICK, 1, 1);
                    if( mcPlayer.hypixelNades) {
                        mcPlayer.hypixelNades = false;
                        mcPlayer.player.setExp(.499999999f);
                    }
                    else {
                        mcPlayer.hypixelNades=true;
                        mcPlayer.player.setExp(.000000000001f);
                    }
                    mcPlayer.player.sendMessage(ChatColor.GOLD + "Hypixel nades: " + mcPlayer.hypixelNades);
                }
        }
    }
}
