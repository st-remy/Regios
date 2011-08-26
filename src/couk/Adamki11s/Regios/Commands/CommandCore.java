package couk.Adamki11s.Regios.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Listeners.RegiosPlayerListener;
import couk.Adamki11s.Regios.Regions.GlobalRegionManager;

public class CommandCore implements CommandExecutor {
	
	public static boolean tag = false;
	
	AuthenticationCommands auth = new AuthenticationCommands();
	CreationCommands creation = new CreationCommands();
	DebugCommands debug = new DebugCommands();
	MessageCommands msg = new MessageCommands();
	MobCommands mobs = new MobCommands();
	ProtectionCommands protect = new ProtectionCommands();
	WarpCommands warps = new WarpCommands();

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
			
			if(args.length == 2 && args[0].equalsIgnoreCase("auth"))
				auth.sendPassword(p, args[1], RegiosPlayerListener.regionBinding.get(p));
			
			if(args.length >= 2 && args[0].equalsIgnoreCase("warp"))
				warps.warpToRegion(args[1], p);
			
			/*
			 * Messages
			 */
			
			if(args.length == 3 && args[0].equalsIgnoreCase("set-welcome"))
				msg.setWelcome(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("set-leave"))
				msg.setLeave(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("set-prevent-exit"))
				msg.setPreventExit(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("set-prevent-entry"))
				msg.setPreventEntry(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("set-protection"))
				msg.setProtection(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("show-welcome"))
				msg.setShowWelcome(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("show-leave"))
				msg.setShowLeave(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("show-prevent-entry"))
				msg.setShowPreventEntry(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);		
			
			if(args.length == 3 && args[0].equalsIgnoreCase("show-prevent-exit"))
				msg.setShowPreventExit(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("show-protection"))
				msg.setShowProtection(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("show-pvp"))
				msg.setShowPvpWarning(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			/*
			 * Messages
			 */
			
			/*
			 * Mobs
			 */
			
			if(args.length == 3 && args[0].equalsIgnoreCase("set-mob-spawns"))
				mobs.setMobSpawn(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("set-monster-spawns"))
				mobs.setMonsterSpawn(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			/*
			 * Mobs
			 */
			
			/*
			 * Protection
			 */
			
			if(args.length == 2 && args[0].equalsIgnoreCase("protect"))
				protect.setProtected(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("unprotect"))
				protect.setUnProtected(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("prevent-exit"))
				protect.setPreventExit(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("prevent-entry"))
				protect.setPreventEntry(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("allow-exit"))
				protect.setAllowExit(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("allow-entry"))
				protect.setAllowEntry(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			/*
			 * Protection
			 */
			
			if(tag){
				p.sendMessage(ChatColor.RED + "[Regios] Use " + ChatColor.BLUE + "/regios help" + ChatColor.RED + " for help");
			}
			
			return true;
		}

		return false;
	}

}
