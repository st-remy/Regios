package couk.Adamki11s.Regios.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Mutable.MutableMisc;
import couk.Adamki11s.Regios.Permissions.PermissionsCore;
import couk.Adamki11s.Regios.Regions.Region;

public class MiscCommands extends PermissionsCore {

	MutableMisc mutable = new MutableMisc();

	public void addToCommandSet(Region r, String region, String[] message, Player p) {
		String msg = "";
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			if (!super.canModifyBasic(r, p)) {
				p.sendMessage(ChatColor.RED + "[Regios] You are not permitted to modify this region!");
				return;
			}
			StringBuilder build = new StringBuilder();
			for (String m : message) {
				build.append(m).append(" ");
			}
			msg = build.toString();
			boolean nodeMatch = false;
			for (String s : r.getCommandSet()) {
				if (s.equalsIgnoreCase(msg)) {
					nodeMatch = true;
				}
			}
			if (nodeMatch) {
				p.sendMessage(ChatColor.RED + "[Regios] The Command " + ChatColor.BLUE + message + ChatColor.RED + " already exists!");
				return;
			}
			p.sendMessage(ChatColor.GREEN + "[Regios] Command " + ChatColor.BLUE + message + ChatColor.GREEN + " added to region set " + ChatColor.BLUE + region);
		}
		mutable.editAddToForceCommandSet(r, msg);
	}

	public void removeFromCommandSet(Region r, String region, String[] message, Player p) {
		String msg = "";
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			if(!super.canModifyBasic(r, p)){
				p.sendMessage(ChatColor.RED + "[Regios] You are not permitted to modify this region!");
				return;
			}
			StringBuilder build = new StringBuilder();
			for (String m : message) {
				build.append(m).append(" ");
			}
			msg = build.toString();
			boolean nodeMatch = false;
			for (String s : r.getCommandSet()) {
				if (s.equalsIgnoreCase(msg)) {
					nodeMatch = true;
				}
			}
			if (!nodeMatch) {
				p.sendMessage(ChatColor.RED + "[Regios] The command " + ChatColor.BLUE + message + ChatColor.RED + " did not match any in the set!");
				return;
			} else {
				p.sendMessage(ChatColor.GREEN + "[Regios] Command " + ChatColor.BLUE + message + ChatColor.GREEN + " removed from region set " + ChatColor.BLUE + region);
			}
		}
		mutable.editRemoveFromForceCommandSet(r, msg);
	}

	public void resetCommandSet(Region r, String region, Player p) {
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			if(!super.canModifyBasic(r, p)){
				p.sendMessage(ChatColor.RED + "[Regios] You are not permitted to modify this region!");
				return;
			}
			p.sendMessage(ChatColor.GREEN + "[Regios] Command Set reset for region " + ChatColor.BLUE + region);
		}
		mutable.editResetForceCommandSet(r);
	}

	public void listCommandSet(Region r, String region, Player p) {
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			String regionSet = mutable.listCommandSet(r);
			p.sendMessage(ChatColor.GREEN + "Force Command Set List : " + ChatColor.BLUE + region);
			p.sendMessage(regionSet);
		}
	}

	public void setForceCommand(Region r, String region, String input, Player p) {
		boolean val;
		try {
			val = Boolean.parseBoolean(input);
		} catch (Exception bfe) {
			p.sendMessage(ChatColor.RED + "[Regios] The value for the 2nd paramteter must be boolean!");
			return;
		}
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			if(!super.canModifyBasic(r, p)){
				p.sendMessage(ChatColor.RED + "[Regios] You are not permitted to modify this region!");
				return;
			}
			if (val) {
				p.sendMessage(ChatColor.GREEN + "[Regios] Force Commands enabled for region " + ChatColor.BLUE + region);
			} else {
				p.sendMessage(ChatColor.GREEN + "[Regios] Force Commands disabled for region " + ChatColor.BLUE + region);
			}
		}
		mutable.editSetForceCommand(r, val);
	}

}
