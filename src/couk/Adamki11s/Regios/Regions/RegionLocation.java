package couk.Adamki11s.Regios.Regions;

import org.bukkit.Location;
import org.bukkit.World;

public class RegionLocation {
	
	private double x, y, z;
	private World w;
	
	public RegionLocation(World w, double x, double y, double z){
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public double getZ(){
		return this.z;
	}
	
	public World getWorld(){
		return this.w;
	}
	
	public Location toBukkitLocation(){
		return new Location(w, x, y, z);
	}

}
