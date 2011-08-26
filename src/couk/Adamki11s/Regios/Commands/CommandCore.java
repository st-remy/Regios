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
	
	final AuthenticationCommands auth = new AuthenticationCommands();
	final CreationCommands creation = new CreationCommands();
	final DebugCommands debug = new DebugCommands();
	final ExceptionCommands excep = new ExceptionCommands();
	final FunCommands fun = new FunCommands();
	final MessageCommands msg = new MessageCommands();
	final MobCommands mobs = new MobCommands();
	final ModificationCommands mod = new ModificationCommands();
	final ProtectionCommands protect = new ProtectionCommands();
	final WarpCommands warps = new WarpCommands();

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
			
			/*
			 * Region Modification
			 */
			
			if(args.length == 2 && args[0].equalsIgnoreCase("modify") && !args[1].equalsIgnoreCase("confirm"))
				mod.startModification(GlobalRegionManager.getRegion(args[1]), args[1], args[1], p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("modify") && args[1].equalsIgnoreCase("confirm"))
				mod.setModifyPoints(CreationCommands.mod1.get(p), CreationCommands.mod2.get(p), p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("delete"))
				mod.setDelete(GlobalRegionManager.getRegion(args[1]), args[1], args[1], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("expand-down"))
				mod.setExpandDown(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("expand-max"))
				mod.setExpandMax(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("expand-up"))
				mod.setExpandUp(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("expand-out"))
				mod.setExpandOut(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("rename"))
				mod.setRename(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("shrink-down"))
				mod.setShrinkDown(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("shrink-up"))
				mod.setShrinkUp(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("shrink-in"))
				mod.setShrinkIn(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			/*
			 * Region Modification
			 */
			
			/*
			 * Exceptions
			 */
			
			if(args.length == 3 && args[0].equalsIgnoreCase("addex") || args[0].equalsIgnoreCase("add-ex"))
				excep.addPlayerException(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("remex") || args[0].equalsIgnoreCase("rem-ex"))
				excep.removePlayerException(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("addnodeex") || args[0].equalsIgnoreCase("add-node-ex"))
				excep.addNodeException(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("remnodeex") || args[0].equalsIgnoreCase("rem-node-ex"))
				excep.removeNodeException(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("erase-player-exceptions"))
				excep.erasePlayerExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("erase-node-exceptions"))
				excep.erasePlayerExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("list-player-exceptions"))
				excep.listPlayerExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("list-node-exceptions"))
				excep.listNodeExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			/*
			 * Exceptions
			 */
			
			/*
			 * Fun
			 */
			
			if(args.length == 3 && args[0].equalsIgnoreCase("lsps"))
				fun.setLSPS(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("pvp"))
				fun.setPvP(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("healthregen") || args[0].equalsIgnoreCase("health-regen"))
				fun.setHealthRegen(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("health") || args[0].equalsIgnoreCase("health-enabled"))
				fun.setHealthEnabled(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 3 && args[0].equalsIgnoreCase("vel-warp") || args[0].equalsIgnoreCase("velocity-warp"))
				fun.setVelocityWarp(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			
			if(args.length == 1 && args[0].equalsIgnoreCase("set-warp"))
				fun.setWarp(p);
			
			if(args.length == 2 && args[0].equalsIgnoreCase("reset-warp"))
				fun.resetWarp(GlobalRegionManager.getRegion(args[1]), args[1], p);
			
			if(tag){
				p.sendMessage(ChatColor.RED + "[Regios] Use " + ChatColor.BLUE + "/regios help" + ChatColor.RED + " for help");
			}
			
			return true;
		}

		return false;
	}

}
