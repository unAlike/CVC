package groupid.artid;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length==0) p.sendMessage("Type '/cvc hub' to return to hub");
            if(args.length==1) {
                switch(args[0]){
                    default:
                        p.sendMessage("Unknown Command");
                    case "hub":
                        p.getInventory().clear();
                        p.teleport(new Location(Bukkit.getWorld("world"), 0.5, 63, 0.5));
                        break;
                }
            }
        }
        return true;
    }
}