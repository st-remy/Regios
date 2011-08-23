package couk.Adamki11s.Checks;

import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Regions.Region;

public class PermChecks {
	
	public boolean canOverride(Player p, Region r){
		if(p.isOp()){
			return true;
		} else if(r.getOwner().equals(p.getName())){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean canBypass(Player p, Region r){
		for(String excep : r.exceptions){
			if(excep.equals(p.getName())){
				return true;	
			}
		}	
	    if(canOverride(p, r)){
			return true;
		} else {
			return false;
		}
	}

}
