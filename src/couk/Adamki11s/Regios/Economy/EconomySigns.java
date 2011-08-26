package couk.Adamki11s.Regios.Economy;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import couk.Adamki11s.Regios.Regions.Region;

public class EconomySigns {
	
	public HashMap<UUID, RegionSign> signs = new HashMap<UUID, RegionSign>();

	public void addSign(Region r, Location l){
		UUID uuid = UUID.randomUUID();
		RegionSign rs = new RegionSign(r, l, uuid);
		signs.put(uuid, rs);
	}
	
	public boolean removeSign(Location l){
		World w = l.getWorld();
		Block block = w.getBlockAt(l);
		if((block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getTypeId() == 68)){
			UUID signUUID = getSignByLocation(l).getUUID();
			if(signUUID != null){
				signs.remove(signUUID);
				return true;
			} else { return false; }
		} else {
			return false;
		}
	}
	
	public RegionSign getSignByUUID(UUID uuid){
		return signs.get(uuid);
	}
	
	public RegionSign getSignByLocation(Location loc){
		for(Entry<UUID, RegionSign> entry : signs.entrySet()){
			RegionSign rs = entry.getValue();
			Location l = rs.getLocation();
			if(l.getBlockX() == loc.getBlockX() && l.getBlockY() == loc.getBlockY() && l.getBlockZ() == loc.getBlockZ()){
				return rs;
			}
		}
		return null;
	}
}
