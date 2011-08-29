package couk.Adamki11s.API;

import me.Adamki11s.Regios.RegiosFileManager;
import me.Adamki11s.Regios.RegiosPlayerListener;

import org.bukkit.entity.Player;

public class Interface {
	
	public boolean insideRegion(Player p){
		if(RegiosPlayerListener.playerInsideRegion.containsKey(p)){
			if(RegiosPlayerListener.playerInsideRegion.get(p)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isOwner(Player p, String region){
		if(RegiosFileManager.doesRegionExist(region, p)){
			if(RegiosFileManager.regionOwner[RegiosFileManager.regionIndex].equalsIgnoreCase(p.getName())){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	
	
	

}
