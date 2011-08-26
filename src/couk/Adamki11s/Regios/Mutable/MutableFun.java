package couk.Adamki11s.Regios.Mutable;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.config.Configuration;

import couk.Adamki11s.Regios.Regions.Region;

public class MutableFun {
	
	public void editLSPS(Region r, int val){
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Other.LSPS");
		for(Entry<String, Object> entry : all.entrySet()){
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Other.LSPS", val);
		r.LSPS = val;
		c.save();
	}
	
	public void editHealthRegen(Region r, int val){
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Other.HealthRegen");
		for(Entry<String, Object> entry : all.entrySet()){
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Other.HealthRegen", val);
		r.healthRegen = val;
		c.save();
	}
	
	public void editVelocityWarp(Region r, double val){
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Other.VelocityWarp");
		for(Entry<String, Object> entry : all.entrySet()){
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Other.VelocityWarp", val);
		r.velocityWarp = val;
		c.save();
	}
	
	public void editWarpLocation(Region r, Location val){
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Teleportation.Warp.Location");
		for(Entry<String, Object> entry : all.entrySet()){
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Teleportation.Warp.Location", val.getWorld() + "," + val.getBlockX() + "," + val.getBlockY() + "," + val.getBlockZ());
		r.warp = val;
		c.save();
	}
	
	public void editRemoveWarpLocation(Region r){
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Teleportation.Warp.Location");
		for(Entry<String, Object> entry : all.entrySet()){
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Teleportation.Warp.Location", r.world + ",0,0,0");
		r.warp = new Location(Bukkit.getServer().getWorld(r.world), 0, 0, 0);
		c.save();
	}
	
	public void editHealthEnabled(Region r, boolean val){
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Other.HealthEnabled");
		for(Entry<String, Object> entry : all.entrySet()){
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Other.HealthEnabled", val);
		r.healthEnabled = val;
		c.save();
	}
	
	public void editPvPEnabled(Region r, boolean val){
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Other.PvP");
		for(Entry<String, Object> entry : all.entrySet()){
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Other.PvP", val);
		r.pvp = val;
		c.save();
	}

}
