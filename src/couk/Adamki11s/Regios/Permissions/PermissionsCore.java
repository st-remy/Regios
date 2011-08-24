package couk.Adamki11s.Regios.Permissions;

import org.bukkit.entity.Player;

import com.nijiko.permissions.PermissionHandler;

public class PermissionsCore {
	
	public static PermissionHandler permissionHandler = null;
    public static boolean hasPermissions = false, iConomyEnabled = false;
    
    public static boolean doesHaveNode(Player p, String node){
    	return (hasPermissions ? (permissionHandler.has(p, node) || p.isOp()) : p.isOp());
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
