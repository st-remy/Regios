package couk.Adamki11s.Regios.API;

import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Commands.CreationCommands;
import couk.Adamki11s.Regios.CustomExceptions.InvalidDataSetException;
import couk.Adamki11s.Regios.CustomExceptions.RegionExistanceException;
import couk.Adamki11s.Regios.Mutable.MutableModification;
import couk.Adamki11s.Regios.Regions.GlobalRegionManager;
import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.Regios.SpoutInterface.SpoutInterface;

public class RegiosAPI {
	
	//Really not much to do. Most of the features people will be interested in are easily accessible through the Region object.
	
	/**
	 * Get a region by it's name.
	 * @param region The Region name.
	 * @return Returns the Region object with the corresponding name. Will return null if no region with that name exists.
	 */
	public static Region getRegion(String region){
		return GlobalRegionManager.getRegion(region);
	}
	
	/**
	 * Get the region a player is in.
	 * @param p Player.
	 * @return Returns the region that the specified player is in. Will return null if the player is not in a region.
	 */
	public static Region getRegion(Player p){
		return GlobalRegionManager.getRegion(p);
	}
	
	/**
	 * Check whether a player is in a region.
	 * @param p Player.
	 * @return Whether the player is in a region or not.
	 */
	public static boolean isInRegion(Player p){
		return (GlobalRegionManager.getRegion(p) == null ? false : true);
	}
	
	/**
	 * Check if the player is using Spoutcraft.
	 * @param p Player
	 * @return Whether the player is running the Spoutcraft launcher.
	 */
	public static boolean isSpoutEnabled(Player p){
		return SpoutInterface.doesPlayerHaveSpout(p);
	}
	
	/**
	 * Check if Spout is installed on the server.
	 * @return Whether Regios is running with Spout support.
	 */
	public static boolean isSpoutEnabled(){
		return SpoutInterface.global_spoutEnabled;
	}
	
	/**
	 * Create a Region
	 * @param rds Region Data Set.
	 * @throws InvalidDataSetException 
	 */
	public static void createRegion(RegionDataSet rds) throws InvalidDataSetException{
		CreationCommands.createRegion(rds);
	}
	
	/**
	 * Delete a Region.
	 * @param name Name of the Region.
	 * @throws RegionExistanceException
	 */
	public static void deleteRegion(String name) throws RegionExistanceException{
		MutableModification.deleteRegion(name);
	}

}
