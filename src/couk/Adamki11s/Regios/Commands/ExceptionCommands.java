package couk.Adamki11s.Regios.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Mutable.MutableExceptions;
import couk.Adamki11s.Regios.Regions.Region;

public class ExceptionCommands {

	MutableExceptions mutable = new MutableExceptions();

	public void addPlayerException(Region r, String region, String ex, Player p) {
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			if (!mutable.checkPlayerException(r, ex)) {
				p.sendMessage(ChatColor.GREEN + "[Regios] Exception added : " + ChatColor.BLUE + ex);
				mutable.addPlayerException(r, ex);
			} else {
				p.sendMessage(ChatColor.RED + "[Regios] Player " + ChatColor.BLUE + ex + ChatColor.RED + " is already an exception!");
			}
		}
	}

	public void removePlayerException(Region r, String region, String ex, Player p) {
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			if (mutable.checkPlayerException(r, ex)) {
				p.sendMessage(ChatColor.GREEN + "[Regios] Exception removed : " + ChatColor.BLUE + ex);
				mutable.removePlayerException(r, ex);
			} else {
				p.sendMessage(ChatColor.RED + "[Regios] Player " + ChatColor.BLUE + ex + ChatColor.RED + " is not an exception!");
			}
		}
	}

	public void addNodeException(Region r, String region, String ex, Player p) {
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			if (!mutable.checkPlayerException(r, ex)) {
				p.sendMessage(ChatColor.GREEN + "[Regios] Exception Node added : " + ChatColor.BLUE + ex);
				mutable.addNodeException(r, ex);
			} else {
				p.sendMessage(ChatColor.RED + "[Regios] Node " + ChatColor.BLUE + ex + ChatColor.RED + " is already an exception!");
			}
		}
	}

	public void removeNodeException(Region r, String region, String ex, Player p) {
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			if (mutable.checkPlayerException(r, ex)) {
				p.sendMessage(ChatColor.GREEN + "[Regios] Exception Node added : " + ChatColor.BLUE + ex);
				mutable.removeNodeException(r, ex);
			} else {
				p.sendMessage(ChatColor.RED + "[Regios] Node " + ChatColor.BLUE + ex + ChatColor.RED + " is already an exception!");
			}
		}
	}

	public void erasePlayerExceptions(Region r, String region, Player p) {
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			p.sendMessage(ChatColor.GREEN + "[Regios] All player exceptions removed for region : " + ChatColor.BLUE + region);
			mutable.eraseAllPlayerExceptions(r);
			return;
		}
	}

	public void eraseNodeExceptions(Region r, String region, Player p) {
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			p.sendMessage(ChatColor.GREEN + "[Regios] All noe exceptions removed for region : " + ChatColor.BLUE + region);
			mutable.eraseAllNodeExceptions(r);
			return;
		}
	}

	public void listPlayerExceptions(Region r, String region, Player p) {
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			String regionSet = mutable.listPlayerExceptions(r);
			p.sendMessage(ChatColor.GREEN + "Regios Player Exception List : " + ChatColor.BLUE + region);
			p.sendMessage(regionSet);
		}
	}
	
	public void listNodeExceptions(Region r, String region, Player p) {
		if (r == null) {
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " doesn't exist!");
			return;
		} else {
			String regionSet = mutable.listNodeExceptions(r);
			p.sendMessage(ChatColor.GREEN + "Regios Node Exception List : " + ChatColor.BLUE + region);
			p.sendMessage(regionSet);
		}
	}

}
