package couk.Adamki11s.Regios.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Listeners.RegiosPlayerListener;
import couk.Adamki11s.Regios.Mutable.MutableInventory;
import couk.Adamki11s.Regios.Mutable.Zippable;
import couk.Adamki11s.Regios.RBF.RBF_Core;
import couk.Adamki11s.Regios.RBF.RBF_Save;
import couk.Adamki11s.Regios.Regions.GlobalRegionManager;

public class CommandCore implements CommandExecutor {

	final AuthenticationCommands auth = new AuthenticationCommands();
	final CreationCommands creation = new CreationCommands();
	final DebugCommands debug = new DebugCommands();
	final ExceptionCommands excep = new ExceptionCommands();
	final FunCommands fun = new FunCommands();
	final InfoCommands info = new InfoCommands();
	final MessageCommands msg = new MessageCommands();
	final MiscCommands miscCmd = new MiscCommands();
	final MobCommands mobs = new MobCommands();
	final ModeCommands mode = new ModeCommands();
	final ModificationCommands mod = new ModificationCommands();
	final InventoryCommands invent = new InventoryCommands();
	final PermissionsCommands perms = new PermissionsCommands();
	final ProtectionCommands protect = new ProtectionCommands();
	final ProtectionMiscCommands misc = new ProtectionMiscCommands();
	final SpoutCommands spout = new SpoutCommands();
	final WarpCommands warps = new WarpCommands();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (label.equalsIgnoreCase("regios")) {

			Player p = (Player) sender;

			if (args.length == 1 && args[0].equalsIgnoreCase("set")) {
				creation.giveTool(p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("create")) {
				creation.createRegion(p, args[1]);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("cancel")) {
				CreationCommands.clearAll(p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("auth")) {
				auth.sendPassword(p, args[1], RegiosPlayerListener.regionBinding.get(p));
			}

			if (args.length >= 2 && args[0].equalsIgnoreCase("warp")) {
				warps.warpToRegion(args[1], p);
			}

			/*
			 * Info
			 */

			if (args.length == 2 && args[0].equalsIgnoreCase("info")) {
				info.showInfo(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			/*
			 * Info
			 */

			/*
			 * Messages
			 */

			if (args.length >= 3 && args[0].equalsIgnoreCase("set-welcome")) {
				msg.setWelcome(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			}

			if (args.length >= 3 && args[0].equalsIgnoreCase("set-leave")) {
				msg.setLeave(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			}

			if (args.length >= 3 && args[0].equalsIgnoreCase("set-prevent-exit")) {
				msg.setPreventExit(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			}

			if (args.length >= 3 && args[0].equalsIgnoreCase("set-prevent-entry")) {
				msg.setPreventEntry(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			}

			if (args.length >= 3 && args[0].equalsIgnoreCase("set-protection")) {
				msg.setProtection(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("show-welcome")) {
				msg.setShowWelcome(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("show-leave")) {
				msg.setShowLeave(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("show-prevent-entry")) {
				msg.setShowPreventEntry(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("show-prevent-exit")) {
				msg.setShowPreventExit(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("show-protection")) {
				msg.setShowProtection(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("show-pvp")) {
				msg.setShowPvpWarning(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			/*
			 * Messages
			 */

			/*
			 * Mobs
			 */

			if (args.length == 3 && args[0].equalsIgnoreCase("set-mob-spawns")) {
				mobs.setMobSpawn(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("set-monster-spawns")) {
				mobs.setMonsterSpawn(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			/*
			 * Mobs
			 */

			/*
			 * Protection
			 */

			if (args.length == 2 && args[0].equalsIgnoreCase("protect")) {
				protect.setProtected(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("unprotect")) {
				protect.setUnProtected(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("prevent-exit")) {
				protect.setPreventExit(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("prevent-entry")) {
				protect.setPreventEntry(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("allow-exit")) {
				protect.setAllowExit(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("allow-entry")) {
				protect.setAllowEntry(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			/*
			 * Protection
			 */

			/*
			 * Region Modification
			 */

			if (args.length == 2 && (args[0].equalsIgnoreCase("modify") && !args[1].equalsIgnoreCase("confirm"))) {
				mod.startModification(GlobalRegionManager.getRegion(args[1]), args[1], args[1], p);
			}

			if (args.length == 2 && (args[0].equalsIgnoreCase("modify") && args[1].equalsIgnoreCase("confirm"))) {
				mod.setModifyPoints(CreationCommands.mod1.get(p), CreationCommands.mod2.get(p), p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
				mod.setDelete(GlobalRegionManager.getRegion(args[1]), args[1], args[1], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("expand-down")) {
				mod.setExpandDown(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("expand-max")) {
				mod.setExpandMax(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("expand-up")) {
				mod.setExpandUp(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("expand-out")) {
				mod.setExpandOut(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("rename")) {
				mod.setRename(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("shrink-down")) {
				mod.setShrinkDown(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("shrink-up")) {
				mod.setShrinkUp(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("shrink-in")) {
				mod.setShrinkIn(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			/*
			 * Region Modification
			 */

			/*
			 * Exceptions
			 */

			if (args.length == 3 && (args[0].equalsIgnoreCase("addex") || args[0].equalsIgnoreCase("add-ex"))) {
				excep.addPlayerException(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("remex") || args[0].equalsIgnoreCase("rem-ex"))) {
				excep.removePlayerException(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("additemex") || args[0].equalsIgnoreCase("add-item-ex"))) {
				excep.addItemException(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("remitemex") || args[0].equalsIgnoreCase("rem-item-ex"))) {
				excep.removeItemException(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("addnodeex") || args[0].equalsIgnoreCase("add-node-ex"))) {
				excep.addNodeException(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("remnodeex") || args[0].equalsIgnoreCase("rem-node-ex"))) {
				excep.removeNodeException(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("addsubex") || args[0].equalsIgnoreCase("add-sub-ex"))) {
				excep.addSubOwner(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("remsubex") || args[0].equalsIgnoreCase("rem-sub-ex"))) {
				excep.removeSubowner(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 2 && (args[0].equalsIgnoreCase("erase-player-exceptions") || args[0].equalsIgnoreCase("erase-player-ex"))) {
				excep.erasePlayerExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && (args[0].equalsIgnoreCase("erase-node-exceptions") || args[0].equalsIgnoreCase("erase-node-ex"))) {
				excep.erasePlayerExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && (args[0].equalsIgnoreCase("erase-item-exceptions") || args[0].equalsIgnoreCase("erase-item-ex"))) {
				excep.eraseItemExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && (args[0].equalsIgnoreCase("erase-sub-exceptions") || args[0].equalsIgnoreCase("erase-item-ex"))) {
				excep.eraseSubOwnerExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && (args[0].equalsIgnoreCase("list-player-exceptions") || args[0].equalsIgnoreCase("list-player-ex"))) {
				excep.listPlayerExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && (args[0].equalsIgnoreCase("list-node-exceptions") || args[0].equalsIgnoreCase("list-node-ex"))) {
				excep.listNodeExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && ((args[0].equalsIgnoreCase("list-item-exceptions") || args[0].equalsIgnoreCase("list-item-ex")))) {
				excep.listItemExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && ((args[0].equalsIgnoreCase("list-item-exceptions") || args[0].equalsIgnoreCase("list-item-ex")))) {
				excep.listSubOwnerExceptions(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			/*
			 * Exceptions
			 */

			/*
			 * Fun
			 */

			if (args.length == 3 && args[0].equalsIgnoreCase("lsps")) {
				fun.setLSPS(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("pvp")) {
				fun.setPvP(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("healthregen") || args[0].equalsIgnoreCase("health-regen"))) {
				fun.setHealthRegen(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("health") || args[0].equalsIgnoreCase("health-enabled"))) {
				fun.setHealthEnabled(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("vel-warp") || args[0].equalsIgnoreCase("velocity-warp"))) {
				fun.setVelocityWarp(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 1 && args[0].equalsIgnoreCase("set-warp")) {
				fun.setWarp(p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("reset-warp")) {
				fun.resetWarp(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			/*
			 * Fun
			 */

			/*
			 * Misc. Protection
			 */

			if (args.length == 3 && (args[0].equalsIgnoreCase("preventinteraction") || args[0].equalsIgnoreCase("prevent-interaction"))) {
				misc.setInteraction(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("doorslocked") || args[0].equalsIgnoreCase("doors-locked"))) {
				misc.setDoorsLocked(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("chestslocked") || args[0].equalsIgnoreCase("chests-locked"))) {
				misc.setChestsLocked(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("setpassword") || args[0].equalsIgnoreCase("set-password"))) {
				misc.setPassword(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("usepassword") || args[0].equalsIgnoreCase("use-password"))) {
				misc.setPasswordEnabled(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("fireprotection") || args[0].equalsIgnoreCase("fire-protection"))) {
				misc.setFireProtection(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			/*
			 * Misc. Protection
			 */

			/*
			 * Modes
			 */

			if (args.length == 3 && (args[0].equalsIgnoreCase("protection-mode") || args[0].equalsIgnoreCase("protectionmode"))) {
				mode.setProtectionMode(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("prevent-entry-mode") || args[0].equalsIgnoreCase("prevententry"))) {
				mode.setPreventEntryMode(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("prevent-exit-mode") || args[0].equalsIgnoreCase("preventexit"))) {
				mode.setPreventExitMode(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("item-mode") || args[0].equalsIgnoreCase("itemmode"))) {
				mode.setItemControlMode(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			/*
			 * Modes
			 */

			/*
			 * Inventory
			 */

			if (args.length == 3 && (args[0].equalsIgnoreCase("perm-wipe-entry") || args[0].equalsIgnoreCase("perm-wipe-enter"))) {
				invent.setPermWipeOnEntry(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("perm-wipe-exit") || args[0].equalsIgnoreCase("perm-wipe-leave"))) {
				invent.setPermWipeOnExit(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("cache-wipe-entry") || args[0].equalsIgnoreCase("cache-wipe-enter"))) {
				invent.setWipeAndCacheOnEntry(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("cache-wipe-exit") || args[0].equalsIgnoreCase("cache-wipe-leave"))) {
				invent.setWipeAndCacheOnExit(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			/*
			 * Inventory
			 */

			/*
			 * Permissions
			 */

			if (args.length == 3 && (args[0].equalsIgnoreCase("perm-cache-add"))) {
				perms.addToTempCache(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("perm-add-add"))) {
				perms.addToPermAddCache(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("perm-rem-add"))) {
				perms.addToPermRemCache(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("perm-cache-rem"))) {
				perms.removeFromTempCache(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("perm-add-rem"))) {
				perms.removeFromPermAddCache(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && (args[0].equalsIgnoreCase("perm-rem-rem"))) {
				perms.removeFromPermRemCache(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 2 && (args[0].equalsIgnoreCase("perm-cache-reset"))) {
				perms.resetTempAddCache(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && (args[0].equalsIgnoreCase("perm-add-reset"))) {
				perms.resetPermAddCache(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && (args[0].equalsIgnoreCase("perm-rem-reset"))) {
				perms.resetPermRemCache(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			/*
			 * Permissions
			 */

			/*
			 * Spout
			 */

			if (args.length >= 3 && args[0].equalsIgnoreCase("set-spout-welcome")) {
				spout.setWelcome(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			}

			if (args.length >= 3 && args[0].equalsIgnoreCase("set-spout-leave")) {
				spout.setLeave(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("spout-welcome-id")) {
				spout.setWelcomeMaterial(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("spout-leave-id")) {
				spout.setLeaveMaterial(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("spout-texture-url")) {
				spout.setTexturePackURL(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("use-texture-url")) {
				spout.setUseTextures(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("use-music-url")) {
				spout.setUseMusic(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("add-music-url")) {
				spout.setAddMusic(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("rem-music-url")) {
				spout.setRemoveMusic(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("reset-music-url")) {
				spout.setResetMusic(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			/*
			 * Spout
			 */

			/*
			 * Misc. Cmd
			 */

			if (args.length >= 3 && args[0].equalsIgnoreCase("add-cmd-set")) {
				miscCmd.addToCommandSet(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			}

			if (args.length >= 3 && args[0].equalsIgnoreCase("rem-cmd-set")) {
				miscCmd.removeFromCommandSet(GlobalRegionManager.getRegion(args[1]), args[1], args, p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("reset-cmd-set")) {
				miscCmd.resetCommandSet(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("list-cmd-set")) {
				miscCmd.listCommandSet(GlobalRegionManager.getRegion(args[1]), args[1], p);
			}

			if (args.length == 3 && args[0].equalsIgnoreCase("set-cmd-set")) {
				miscCmd.setForceCommand(GlobalRegionManager.getRegion(args[1]), args[1], args[2], p);
			}

			/*
			 * Misc. Cmd
			 */

			/*
			 * Administration
			 */
			
			if(args.length == 3 && (args[0].equalsIgnoreCase("backup-region") || args[0].equalsIgnoreCase("save-region"))){
				RBF_Core.rbf_save.saveRegion(GlobalRegionManager.getRegion(args[1]), args[2], p);
			}
			
			if(args.length == 3 && (args[0].equalsIgnoreCase("restore-region") || args[0].equalsIgnoreCase("load-region"))){
				try {
					RBF_Core.rbf_load.loadRegion(GlobalRegionManager.getRegion(args[1]), args[2], p);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (args.length == 2 && args[0].equalsIgnoreCase("backup-database")) {
				try {
					Zippable.zipDir(new File("plugins" + File.separator + "Regios" + File.separator + "Database"), new File("plugins" + File.separator + "Regios"
							+ File.separator + "Backups" + File.separator + args[1] + ".zip"), args[1], p);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return true;
		}

		return false;
	}
}
