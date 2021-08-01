package events.player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

public class playerChangeItem implements Listener {
    @EventHandler
    public void changeItem(PlayerItemHeldEvent e){
        mcgoPlayer mplayer = Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString());
        if(e.getNewSlot()==0 && mplayer.getMain()!=null){
            mplayer.getMain().setCooldown(true);
            if(mplayer.getMain().getItem().getType().equals(Material.NETHERITE_SWORD) || mplayer.getMain().getItem().getType().equals(Material.GOLDEN_AXE)){

            }
            new BukkitRunnable(){
                @Override
                public void run() {
                    mplayer.getMain().setCooldown(false);
                }
            }.runTaskLater(Artid.plug, ((long) mplayer.getMain().getFireRate()/2));
        }
        if(e.getNewSlot()==1 && mplayer.getOffhand()!=null){
            mplayer.getOffhand().setCooldown(true);
            new BukkitRunnable(){
                @Override
                public void run() {
                    mplayer.getOffhand().setCooldown(false);
                }
            }.runTaskLater(Artid.plug, ((long) mplayer.getOffhand().getFireRate()/2));
        }
        if(e.getPlayer().getInventory().getItem(e.getNewSlot()) != null) {
            switch (e.getPlayer().getInventory().getItem(e.getNewSlot()).getType()) {
                default:
                    e.getPlayer().setExp(0.0001f);
                    e.getPlayer().setWalkSpeed(.2f);
                    break;
                case OAK_SAPLING: case DARK_OAK_SAPLING: case BIRCH_SAPLING: case ACACIA_SAPLING: case JUNGLE_SAPLING:
                    if(!Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString()).hypixelNades) {
                        e.getPlayer().setExp(.5f);
                    }
                    break;
                    //m4 SR
                case IRON_AXE: case GOLDEN_AXE:
                    ((CraftPlayer)e.getPlayer()).setWalkSpeed(.178f);
                    break;
                    //p90 Scout AK
                case GOLDEN_SHOVEL: case NETHERITE_SWORD: case STONE_HOE:
                    ((CraftPlayer)e.getPlayer()).setWalkSpeed(.18f);
                    break;
                    //PUMP SPAS
                case DIAMOND_SHOVEL: case WOODEN_AXE:
                    ((CraftPlayer)e.getPlayer()).setWalkSpeed(.182f);
                    break;
                    //DEAG MP5
                case GOLDEN_PICKAXE: case STONE_SHOVEL:
                    ((CraftPlayer)e.getPlayer()).setWalkSpeed(.184f);
                    break;
                    //USP HK
                case WOODEN_PICKAXE: case STONE_PICKAXE:
                    ((CraftPlayer)e.getPlayer()).setWalkSpeed(.19f);
                    break;
                    //Knife
                case BONE:
                    ((CraftPlayer)e.getPlayer()).setWalkSpeed(.205f);
                    break;


            }
        }else {
            e.getPlayer().setExp(0.0001f);
            e.getPlayer().setWalkSpeed(.2f);
        }

        mcgoPlayer mcPlayer = Artid.mcPlayers.get(e.getPlayer().getUniqueId().toString());
        CraftPlayer player = (CraftPlayer) e.getPlayer();
        PacketContainer FOV = new PacketContainer(PacketType.Play.Server.ABILITIES);
        FOV.getFloat().write(0,.05f);
        FOV.getBooleans().write(2, player.getAllowFlight());
        FOV.getFloat().write(1, .1f);
        //TODO RESET SNIPE
        e.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
        try {
            Artid.protocolManager.sendServerPacket(player, FOV);
        } catch (InvocationTargetException ex) {
            throw new RuntimeException(
                    "Cannot send packet " + FOV, ex);
        }


    }
    @EventHandler
    public void altHandSwap(PlayerSwapHandItemsEvent e){
        e.setCancelled(true);
    }
}
