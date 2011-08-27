package couk.Adamki11s.Regios.Regions;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;

import couk.Adamki11s.Regios.Checks.ChunkGrid;
import couk.Adamki11s.Regios.Data.Saveable;

public class GlobalRegionManager {
	
	private static ArrayList<Region> regions = new ArrayList<Region>();
	private static ArrayList<GlobalWorldSetting> worldSettings = new ArrayList<GlobalWorldSetting>();
	
	public static ArrayList<Region> getRegions(){
		return regions;
	}
	
	public static ArrayList<GlobalWorldSetting> getWorldSettings(){
		return worldSettings;
	}
	
	public static boolean doesExist(String name){
		for(Region r : regions){
			if(r.getName().equalsIgnoreCase(name)){
				return true;
			}
		}
		return false;
	}
	
	public static void purgeRegions(){
		regions.clear();
		worldSettings.clear();
		System.gc();
	}
	
	public static Region getRegion(String name){
		for(Region r : regions){
			if(r.getName().equalsIgnoreCase(name)){
				return r;
			}
		}
		return null;
	}
	
	public static GlobalWorldSetting getGlobalWorldSetting(World w){
		for(GlobalWorldSetting gws : worldSettings){
			if(gws.world.equals(w.getName())){
				return gws;
			}
		}
		return null;
	}
	
	public static ChunkGrid getChunkGrid(Region r){
		return r.getChunkGrid();
	}
	
	public static void addRegion(Region reg){
		regions.add(reg);
	}
	
	public static void addWorldSetting(GlobalWorldSetting gws){
		worldSettings.add(gws);
	}
	
}