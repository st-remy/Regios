package couk.Adamki11s.Regios.Commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Regions.GlobalRegionManager;
import couk.Adamki11s.Regios.Regions.Region;

public class WarpCommands extends CommandLink {
	
	public void warpToRegion(String region, Player p){
		Region reg = GlobalRegionManager.getRegion(region);
		if(reg == null){
			p.sendMessage(ChatColor.RED + "[Regios] The region " + ChatColor.BLUE + region + ChatColor.RED + " does not exist!");
			return;
		} else {
			warpTo(p, reg);
		}
	}
	
	private void warpTo(Player p, Region r){
		if(r.warp.getBlockX() == 0 && r.warp.getBlockY() == 0 && r.warp.getBlockZ() == 0){
			p.sendMessage(ChatColor.RED + "[Regios] No warp has been set for region : " + ChatColor.BLUE + r.getName());
			return;
		} else {
			if(r.isPreventingEntry() && !r.canEnter(p)){
				p.sendMessage(ChatColor.RED + "[Regios] No are not permitted to warp to region : " + ChatColor.BLUE + r.getName());
				return;
			} else {
				p.teleport(r.warp);
				return;
			}
		}
	}

}
