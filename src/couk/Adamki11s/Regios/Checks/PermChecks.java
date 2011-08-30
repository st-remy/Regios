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
			for(String subOwner : r.getSubOwners()){
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
			for(String excep : r.getExceptionNodes()){
				if(r.getProtectionMode() == MODE.Whitelist){
					if(excep.equals(p.getName())){
						return true;	
					}
				} else if(r.getProtectionMode() == MODE.Blacklist){
					if(excep.equals(p.getName())){
						return false;	
					}
				}
			}
			if(r.getProtectionMode() == MODE.Whitelist){
				return false;
			} else if(r.getProtectionMode() == MODE.Blacklist){
				return true;
			}
			return false;
		}
	}
	
	public boolean canItemBePlaced(Player p, Material m, Region r){
		if(canBypass(p, r)){ return true; }
		if(isSuper(p, r)){ return true; }
		if(r.getItemMode() == MODE.Whitelist){
			return r.getItems().contains(m.getId());
		} else if(r.getItemMode() == MODE.Blacklist){
			return !r.getItems().contains(m.getId());
		}
		return false;
	}
	
	public boolean canBuild(Player p, Region r){
		if(canBypass(p, r)){ return true; }
		if(isSuper(p, r)){ return true; }
		for(String excep : r.getExceptions()){
			if(r.getProtectionMode() == MODE.Whitelist){
				if(excep.equals(p.getName())){
					return true;	
				}
			} else if(r.getProtectionMode() == MODE.Blacklist){
				if(excep.equals(p.getName())){
					return false;	
				}
			}
		}
		if(r.getProtectionMode() == MODE.Whitelist){
			return false;
		} else if(r.getProtectionMode() == MODE.Blacklist){
			return true;
		}
		return false;
	}
	
	public boolean canEnter(Player p, Region r){
		if(canBypass(p, r)){ return true; }
		if(isSuper(p, r)){ return true; }
		for(String excep : r.getExceptions()){
			if(r.getPreventEntryMode() == MODE.Whitelist){
				if(excep.equals(p.getName())){
					return true;	
				}
			} else if(r.getPreventEntryMode() == MODE.Blacklist){
				if(excep.equals(p.getName())){
					return false;	
				}
			}
		}
		if(r.getPreventEntryMode() == MODE.Whitelist){
			return false;
		} else if(r.getPreventEntryMode() == MODE.Blacklist){
			return true;
		}
		return false;
	}
	
	public boolean canExit(Player p, Region r){
		if(canBypass(p, r)){ return true; }
		if(isSuper(p, r)){ return true; }
		for(String excep : r.getExceptions()){
			if(r.getPreventExitMode() == MODE.Whitelist){
				if(excep.equals(p.getName())){
					return true;	
				}
			} else if(r.getPreventExitMode() == MODE.Blacklist){
				if(excep.equals(p.getName())){
					return false;	
				}
			}
		}
		if(r.getPreventExitMode() == MODE.Whitelist){
			return false;
		} else if(r.getPreventExitMode() == MODE.Blacklist){
			return true;
		}
		return false;
	}
	
	public boolean isSuper(Player p, Region r){
		if(p.isOp()){ return true; }
		return r.getOwner().equals(p.getName());
	}

}