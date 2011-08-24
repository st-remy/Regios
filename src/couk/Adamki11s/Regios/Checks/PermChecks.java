package couk.Adamki11s.Regios.Checks;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Data.MODE;
import couk.Adamki11s.Regios.Permissions.PermissionsCore;
import couk.Adamki11s.Regios.Regions.Region;

public class PermChecks {
	
	public boolean canOverride(Player p, Region r){
		if(PermissionsCore.doesHaveNode(p, ("regios.override." + r.getName())) || PermissionsCore.doesHaveNode(p, "regios.override.all")){
			return true;
		}
		if(p.isOp()){
			return true;
		} else if(r.getOwner().equals(p.getName())){
			return true;
		} else {
			for(String subOwner : r.subOwners){
				if(subOwner.equals(p.getName())){
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean canBypass(Player p, Region r){
		if(PermissionsCore.doesHaveNode(p, ("regios.bypass." + r.getName())) || PermissionsCore.doesHaveNode(p, "regios.bypass.all")){
			return true;
		}
	    if(canOverride(p, r)){
			return true;
		} else {
			for(String excep : r.exceptions){
				if(r.protectionMode == MODE.Whitelist){
					if(excep.equals(p.getName())){
						return true;	
					}
				} else if(r.protectionMode == MODE.Blacklist){
					if(excep.equals(p.getName())){
						return false;	
					}
				}
			}
			if(r.protectionMode == MODE.Whitelist){
				return false;
			} else if(r.protectionMode == MODE.Blacklist){
				return true;
			}
			return false;
		}
	}
	
	public boolean canItemBePlaced(Player p, Material m, Region r){
		if(canBypass(p, r)){ return true; }
		if(isSuper(p, r)){ return true; }
		if(r.itemMode == MODE.Whitelist){
			return r.items.contains(m.getId());
		} else if(r.itemMode == MODE.Blacklist){
			return !r.items.contains(m.getId());
		}
		return false;
	}
	
	public boolean canBuild(Player p, Region r){
		if(canBypass(p, r)){ return true; }
		if(isSuper(p, r)){ return true; }
		for(String excep : r.exceptions){
			if(r.protectionMode == MODE.Whitelist){
				if(excep.equals(p.getName())){
					return true;	
				}
			} else if(r.protectionMode == MODE.Blacklist){
				if(excep.equals(p.getName())){
					return false;	
				}
			}
		}
		if(r.protectionMode == MODE.Whitelist){
			return false;
		} else if(r.protectionMode == MODE.Blacklist){
			return true;
		}
		return false;
	}
	
	public boolean canEnter(Player p, Region r){
		if(canBypass(p, r)){ return true; }
		if(isSuper(p, r)){ return true; }
		for(String excep : r.exceptions){
			if(r.preventEntryMode == MODE.Whitelist){
				if(excep.equals(p.getName())){
					return true;	
				}
			} else if(r.preventEntryMode == MODE.Blacklist){
				if(excep.equals(p.getName())){
					return false;	
				}
			}
		}
		if(r.preventEntryMode == MODE.Whitelist){
			return false;
		} else if(r.preventEntryMode == MODE.Blacklist){
			return true;
		}
		return false;
	}
	
	public boolean canExit(Player p, Region r){
		if(canBypass(p, r)){ return true; }
		if(isSuper(p, r)){ return true; }
		for(String excep : r.exceptions){
			if(r.preventExitMode == MODE.Whitelist){
				if(excep.equals(p.getName())){
					return true;	
				}
			} else if(r.preventExitMode == MODE.Blacklist){
				if(excep.equals(p.getName())){
					return false;	
				}
			}
		}
		if(r.preventExitMode == MODE.Whitelist){
			return false;
		} else if(r.preventExitMode == MODE.Blacklist){
			return true;
		}
		return false;
	}
	
	public boolean isSuper(Player p, Region r){
		if(p.isOp()){ return true; }
		return r.getOwner().equals(p.getName());
	}

}
