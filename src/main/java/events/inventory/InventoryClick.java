package events.inventory;

import groupid.artid.Artid;
import groupid.artid.mcgoPlayer;
import guns.gun;
import guns.gunTypes;
import inventories.bigBomb;
import inventories.shop;
import inventories.smallBomb;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {
    @EventHandler
    public void changeItem(InventoryClickEvent e) throws CloneNotSupportedException {
        mcgoPlayer p = Artid.mcPlayers.get(e.getWhoClicked().getUniqueId().toString());
        if(e.getWhoClicked().getGameMode()!= GameMode.CREATIVE){
            e.setCancelled(true);
        }
        if(e.getView().getTitle().equals("Weapon Selector")) {
            e.setCancelled(true);
            if(e.getCurrentItem().getType() != null) {
                switch (e.getCurrentItem().getType()) {
                    //////////////////////////////////////MAIN GUNS////////////////////////////
                    case NETHERITE_SWORD:
                        p.setMain((gun) gunTypes.AWP.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case GOLDEN_SHOVEL:
                        p.setMain((gun) gunTypes.P90.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case STONE_SHOVEL:
                        p.setMain((gun) gunTypes.MP5.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case STONE_HOE:
                        p.setMain((gun) gunTypes.AK.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case IRON_AXE:
                        p.setMain((gun) gunTypes.M4.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case GOLDEN_AXE:
                        p.setMain((gun) gunTypes.AUG.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case DIAMOND_SHOVEL:
                        p.setMain((gun) gunTypes.PUMP.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case WOODEN_AXE:
                        p.setMain((gun) gunTypes.SPAS.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                    case NETHERITE_SHOVEL:
                        p.setMain((gun) gunTypes.FAMAS.getGun().clone());
                        p.getMain().setPlayerUUID(p.player.getUniqueId());
                        p.giveMainGun();
                        e.setCancelled(true);
                        break;
                        /////////////////////////////////PISTOLS/////////////////////////////
                    case WOODEN_PICKAXE:
                        p.setOffhand((gun) gunTypes.USP.getGun().clone());
                        p.getOffhand().setPlayerUUID(p.player.getUniqueId());
                        p.giveOffhandGun();
                        e.setCancelled(true);
                        break;
                    case STONE_PICKAXE:
                        p.setOffhand((gun) gunTypes.HK.getGun().clone());
                        p.getOffhand().setPlayerUUID(p.player.getUniqueId());
                        p.giveOffhandGun();
                        e.setCancelled(true);
                        break;
                    case GOLDEN_PICKAXE:
                        p.setOffhand((gun) gunTypes.DEAGLE.getGun().clone());
                        p.getOffhand().setPlayerUUID(p.player.getUniqueId());
                        p.giveOffhandGun();
                        e.setCancelled(true);
                        break;
                    ////////////////////////////DMG NADES////////////////////////////////
                    case ACACIA_SAPLING:
                    case OAK_SAPLING:
                        e.getWhoClicked().getInventory().setItem(3, e.getCurrentItem());
                        e.setCancelled(true);
                        break;
                    //////////////////////////////////UTIL NADES///////////////////////////
                    case JUNGLE_SAPLING:
                    case DARK_OAK_SAPLING:
                    case BIRCH_SAPLING:
                        e.getWhoClicked().getInventory().setItem(4, e.getCurrentItem());
                        e.setCancelled(true);
                        break;
                    case BONE:
                        e.getWhoClicked().getInventory().setItem(2, e.getCurrentItem());
                        e.setCancelled(true);
                        break;
                }
            }
        }
        if(e.getView().getTitle().equals("Shop")) {
            mcgoPlayer mc = Artid.mcPlayers.get(e.getWhoClicked().getUniqueId().toString());
            e.setCancelled(true);
            if(e.getCurrentItem().getType() != null) {
                switch (e.getCurrentItem().getType()) {
                    //////////////////////////////////////MAIN GUNS////////////////////////////
                    case NETHERITE_SWORD:
                        if(mc.money>=2450) {
                            p.setMain((gun) gunTypes.AWP.getGun().clone());
                            p.getMain().setPlayerUUID(p.player.getUniqueId());
                            p.giveMainGun();
                            e.setCancelled(true);
                            mc.money-=2450;
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case GOLDEN_SHOVEL:
                        if(mc.money>=2250) {
                            mc.money-=2250;
                            p.setMain((gun) gunTypes.P90.getGun().clone());
                            p.getMain().setPlayerUUID(p.player.getUniqueId());
                            p.giveMainGun();
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case STONE_SHOVEL:
                        if(mc.money>=1700) {
                            mc.money-=1700;
                            p.setMain((gun) gunTypes.MP5.getGun().clone());
                            p.getMain().setPlayerUUID(p.player.getUniqueId());
                            p.giveMainGun();
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case STONE_HOE:
                        if(mc.money>=2900) {
                            mc.money-=2900;
                            p.setMain((gun) gunTypes.AK.getGun().clone());
                            p.getMain().setPlayerUUID(p.player.getUniqueId());
                            p.giveMainGun();
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case IRON_AXE:
                        if(mc.money>=3100) {
                            mc.money-=3100;
                            p.setMain((gun) gunTypes.M4.getGun().clone());
                            p.getMain().setPlayerUUID(p.player.getUniqueId());
                            p.giveMainGun();
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case GOLDEN_AXE:
                        if(mc.money>=3000) {
                            mc.money-=3000;
                            p.setMain((gun) gunTypes.AUG.getGun().clone());
                            p.getMain().setPlayerUUID(p.player.getUniqueId());
                            p.giveMainGun();
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case DIAMOND_SHOVEL:
                        if(mc.money>=1200) {
                            mc.money-=1200;
                            p.setMain((gun) gunTypes.PUMP.getGun().clone());
                            p.getMain().setPlayerUUID(p.player.getUniqueId());
                            p.giveMainGun();
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case WOODEN_AXE:
                        if(mc.money>=2000) {
                            mc.money-=2000;
                            p.setMain((gun) gunTypes.SPAS.getGun().clone());
                            p.getMain().setPlayerUUID(p.player.getUniqueId());
                            p.giveMainGun();
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case NETHERITE_SHOVEL:
                        if(mc.money>=2450) {
                            mc.money-=2450;
                            p.setMain((gun) gunTypes.FAMAS.getGun().clone());
                            p.getMain().setPlayerUUID(p.player.getUniqueId());
                            p.giveMainGun();
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    /////////////////////////////////PISTOLS/////////////////////////////
                    case WOODEN_PICKAXE:
                        if(mc.money>=200) {
                            mc.money-=200;
                            p.setOffhand((gun) gunTypes.USP.getGun().clone());
                            p.getOffhand().setPlayerUUID(p.player.getUniqueId());
                            p.giveOffhandGun();
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case STONE_PICKAXE:
                        if(mc.money>=500) {
                            mc.money-=500;
                            p.setOffhand((gun) gunTypes.HK.getGun().clone());
                            p.getOffhand().setPlayerUUID(p.player.getUniqueId());
                            p.giveOffhandGun();
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case GOLDEN_PICKAXE:
                        if(mc.money>=700) {
                            mc.money-=700;
                            p.setOffhand((gun) gunTypes.DEAGLE.getGun().clone());
                            p.getOffhand().setPlayerUUID(p.player.getUniqueId());
                            p.giveOffhandGun();
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    ////////////////////////////DMG NADES////////////////////////////////
                    case ACACIA_SAPLING:
                        if(mc.money>=600) {
                            mc.money-=600;
                            e.getWhoClicked().getInventory().setItem(3, e.getCurrentItem());
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case OAK_SAPLING:
                        if(mc.money>=300) {
                            mc.money-=300;
                            e.getWhoClicked().getInventory().setItem(3, e.getCurrentItem());
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    //////////////////////////////////UTIL NADES///////////////////////////
                    case JUNGLE_SAPLING:
                        if(mc.money>=100) {
                            mc.money-=100;
                            e.getWhoClicked().getInventory().setItem(4, e.getCurrentItem());
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case DARK_OAK_SAPLING:
                        if(mc.money>=300) {
                            mc.money-=300;
                            e.getWhoClicked().getInventory().setItem(4, e.getCurrentItem());
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case BIRCH_SAPLING:
                        if(mc.money>=200) {
                            mc.money-=200;
                            e.getWhoClicked().getInventory().setItem(4, e.getCurrentItem());
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    ///////////////////////////////// ARMOR ///////////////////////////////////
                    case DIAMOND_HELMET:
                        if(mc.money>=350){
                            mc.money-=350;
                            mc.headArmor=true;
                            mc.player.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.boughtarmor" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case DIAMOND_CHESTPLATE:
                        if(mc.money>=550){
                            mc.money-=550;
                            mc.bodyArmor=true;
                            mc.player.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.boughtarmor" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                        break;
                    case SHEARS:
                        if(mc.money>=400){
                            mc.money-=400;
                            e.getWhoClicked().getInventory().setItem(5, e.getCurrentItem());
                            e.setCancelled(true);
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopbuyitem" , 1,1);
                        }
                        else {
                            mc.player.sendMessage(ChatColor.RED + "You do not have enough money.");
                            mc.player.playSound(mc.player.getLocation(), "mcgo.shop.shopcantbuy" , 1,1);
                        }
                }
                mc.updateGameShop();
            }
        }
        if(e.getView().getTitle()=="Cut all the red wires"){
            if(e.getClick().isLeftClick()) {
                if (e.getCurrentItem().getType() == Material.RED_DYE) {
                    e.getCurrentItem().setType(Material.BLACK_DYE);
                    if (!e.getClickedInventory().contains(Material.RED_DYE)) {
                        Artid.games.get(p.gameUUID).bombDefused = true;
                    }
                } else {
                    bigBomb bb = new bigBomb();
                    e.getInventory().setContents(bb.getInv().getContents());
                    e.getWhoClicked().closeInventory();
                }
            }
        }
        if(e.getView().getTitle()=="Cut all the red wires "){
            if(e.getClick().isLeftClick()) {
                if (e.getCurrentItem().getType() == Material.RED_DYE) {
                    e.getCurrentItem().setType(Material.BLACK_DYE);
                    if (!e.getClickedInventory().contains(Material.RED_DYE)) {
                        Artid.games.get(p.gameUUID).bombDefused = true;
                        for(mcgoPlayer player: Artid.games.get(p.gameUUID).mcgoPlayers){
                            player.player.sendTitle(ChatColor.AQUA + player.player.getDisplayName() + " defused the bomb", "", 100,0,0);
                        }
                        e.getWhoClicked().closeInventory();
                    }
                } else {
                    smallBomb ss = new smallBomb();
                    e.getInventory().setContents(ss.getInv().getContents());
                    e.getWhoClicked().closeInventory();
                }
            }
        }
    }
}