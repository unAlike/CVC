package groupid.artid;

import bot.CVCBot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length==0) p.sendMessage("Type '/cvc hub' to return to hub");
            if(args.length==1) {
                switch(args[0]){
                    default:
                        p.sendMessage("Unknown Command\n'/cvc hub' to return to hub");
                        break;
                    case "hub":
                        p.getInventory().clear();
                        p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 63, 0.5));
                        break;
                    case "fix":
                        for(Player pla : p.getWorld().getPlayers()){
                            pla.setInvulnerable(false);
                        }
                        break;
                    case "reset": case "r":
                        break;
                    case "ping":
                        for(Player pla : p.getWorld().getPlayers()){
                            p.sendMessage(pla.getDisplayName() + "'s Ping is: " + ((CraftPlayer)pla).getHandle().ping);
                        }
                        break;
                    case "bot":
                        p.sendMessage("Spawning Bot");
                        CVCBot bot = new CVCBot(p.getWorld(),p);
                        Artid.bots.add(bot);
                        mcgoPlayer mc = new mcgoPlayer(bot.getBukkitEntity());
                        mc.player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100000, 10000000));
                        Artid.mcPlayers.put(bot.getUniqueID().toString(),mc);
                        break;
                    case "botglowing":

                        break;
                    case "delbot":
                        for(CVCBot cbot : Artid.bots){
                            cbot.destroy();
                        }
                        break;
                    case "dmgbot":
                        for(CVCBot cbot : Artid.bots){
                            cbot.attack();
                        }
                        break;
                }
            }
        }
        return true;
    }
}