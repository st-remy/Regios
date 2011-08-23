package couk.Adamki11s.Regios.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Regions.GlobalRegionManager;

public class CommandCore implements CommandExecutor {
	
	public static boolean tag = false;
	
	CreationCommands creation = new CreationCommands();
	DebugCommands debug = new DebugCommands();
	GlobalRegionManager grm = new GlobalRegionManager();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if(label.equalsIgnoreCase("regios")){
			
			tag = false;
			
			Player p = (Player)sender;
			
			if(args.length == 1 && args[0].equalsIgnoreCase("set"))
				creation.giveTool(p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("create"))
				creation.createRegion(p, args[1]);
			
			if(args.length >= 1 && args[0].equalsIgnoreCase("debug")){
				if(args.length == 2){
					if(args[1].equalsIgnoreCase("chunk")){
						debug.printChunk(p);
					} else
						System.out.println(grm.doesExist(args[1]));
				}
				if(args.length == 1 && args[0].equalsIgnoreCase("loc")){
					debug.printLocation(p);
				}
				if(args.length == 3 && args[2].equalsIgnoreCase("chunkgrid")){
					for(Chunk c : grm.getRegion(args[1]).getChunkGrid().getChunks()){
						System.out.println(c.toString());
					}
				}
			}
			
			if(tag){
				p.sendMessage(ChatColor.RED + "[Regios] Use " + ChatColor.BLUE + "/regios help" + ChatColor.RED + " for help");
			}
			
			return true;
		}

		return false;
	}

}
