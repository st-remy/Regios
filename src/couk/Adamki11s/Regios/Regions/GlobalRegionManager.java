package couk.Adamki11s.Regios.Regions;

import java.util.ArrayList;

import org.bukkit.Location;

import couk.Adamki11s.Checks.ChunkGrid;
import couk.Adamki11s.Regios.Data.Saveable;

public class GlobalRegionManager {
	
	public static ArrayList<Region> regions = new ArrayList<Region>();
	
	public ArrayList<Region> getRegions(){
		return regions;
	}
	
	public boolean doesExist(String name){
		for(Region r : regions){
			if(r.name.equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	public Region getRegion(String name){
		for(Region r : regions){
			if(r.getName().equalsIgnoreCase(name)){
				return r;
			}
		}
		return null;
	}
	
	public ChunkGrid getChunkGrid(Region r){
		return r.getChunkGrid();
	}
	
	public void addRegion(Region reg){
		regions.add(reg);
	}
	
}