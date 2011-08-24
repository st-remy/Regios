package me.Adamki11s.Regios;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Creature;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

import couk.Adamki11s.WorldConfiguration.ConfigurationSettings;

public class RegiosCreatureSpawnEvent extends EntityListener {

	public static Regios plugin;
	public static CreatureType latestCreature;
	public static Entity latestEntity;
	private static Map<Entity, Location> entityLocation = new HashMap<Entity, Location>();
	private static Map<Entity, Integer> entityRegionIndex = new HashMap<Entity, Integer>();
	private static Map<Entity, Integer> entitySubRegionIndex = new HashMap<Entity, Integer>();
	
	boolean useSubIndex = false;
	
	public RegiosCreatureSpawnEvent(Regios instance) {
		plugin = instance;
	}
	
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		World w = event.getEntity().getLocation().getWorld();
		
		latestCreature = event.getCreatureType();
		latestEntity = event.getEntity();
		
		entityLocation.put(latestEntity, latestEntity.getLocation());
		
		int index = RegiosWeatherListener.getIndex(w);
				
		if(latestEntity instanceof Monster){
			if(!ConfigurationSettings.monstersSpawn[index - 1]){
				event.setCancelled(true);
				//Regios.server.broadcastMessage("Mob spawn prevented : " + latestEntity);
				return;
			}
		} else if(latestEntity instanceof Creature){
			if(!ConfigurationSettings.mobsSpawn[index - 1]){
				event.setCancelled(true);
				//Regios.server.broadcastMessage("Mob spawn prevented : " + latestEntity);
				return;
			}
		}
		
		if(latestEntity instanceof LivingEntity){
			if(!(latestEntity instanceof Player)){
				for(int i = 1; i <= RegiosFileManager.regionCount; i++){
					//if(RegiosFileManager.regionCreaturesSpawn[i - 1] == false){
						if( (((entityLocation.get(latestEntity).getY() <= RegiosFileManager.regiony1[i - 1] + 2) && (entityLocation.get(latestEntity).getY() >= RegiosFileManager.regiony2[i - 1] - 1)) || ((entityLocation.get(latestEntity).getY() >= RegiosFileManager.regiony1[i - 1] - 1) && (entityLocation.get(latestEntity).getY() <= RegiosFileManager.regiony2[i - 1] + 2))) && (((entityLocation.get(latestEntity).getZ() <= RegiosFileManager.regionz1[i - 1]) && (entityLocation.get(latestEntity).getZ() >= RegiosFileManager.regionz2[i - 1])) || ((entityLocation.get(latestEntity).getZ() >= RegiosFileManager.regionz1[i - 1]) && (entityLocation.get(latestEntity).getZ() <= RegiosFileManager.regionz2[i - 1])))  &&  (((entityLocation.get(latestEntity).getX() <= RegiosFileManager.regionx1[i - 1]) && (entityLocation.get(latestEntity).getX() >= RegiosFileManager.regionx2[i - 1])) || ((entityLocation.get(latestEntity).getX() >= RegiosFileManager.regionx1[i - 1]) && (entityLocation.get(latestEntity).getX() <= RegiosFileManager.regionx2[i - 1]))) && (((entityLocation.get(latestEntity).getX() <= RegiosFileManager.regionx1[i - 1]) && (entityLocation.get(latestEntity).getX() >= RegiosFileManager.regionx2[i - 1])) || ((entityLocation.get(latestEntity).getX() >= RegiosFileManager.regionx1[i - 1]) && (entityLocation.get(latestEntity).getX() <= RegiosFileManager.regionx2[i - 1])))   ){								
							if(!entityRegionIndex.containsKey(latestEntity)){
								entityRegionIndex.put(latestEntity, i - 1);		
								//Regios.server.broadcastMessage("MobSpawned in Region!");
							} else {
								entitySubRegionIndex.put(latestEntity, i - 1);
								//Regios.server.broadcastMessage("MobSpawned in SUB Region!");
							}
						}
					//}
				}
			}
		}	
		
		
		if(entitySubRegionIndex.containsKey(latestEntity) && entityRegionIndex.containsKey(latestEntity)){
		if(    ( (RegiosFileManager.regionx1[entityRegionIndex.get(latestEntity)] < RegiosFileManager.regionx1[entitySubRegionIndex.get(latestEntity)] &&
				RegiosFileManager.regionx1[entityRegionIndex.get(latestEntity)] > RegiosFileManager.regionx2[entitySubRegionIndex.get(latestEntity)]) || 
				RegiosFileManager.regionx1[entityRegionIndex.get(latestEntity)] > RegiosFileManager.regionx1[entitySubRegionIndex.get(latestEntity)] &&
				RegiosFileManager.regionx1[entityRegionIndex.get(latestEntity)] < RegiosFileManager.regionx2[entitySubRegionIndex.get(latestEntity)])  &&
				( (RegiosFileManager.regionz1[entityRegionIndex.get(latestEntity)] < RegiosFileManager.regionz1[entitySubRegionIndex.get(latestEntity)] &&
						RegiosFileManager.regionz1[entityRegionIndex.get(latestEntity)] > RegiosFileManager.regionz2[entitySubRegionIndex.get(latestEntity)]) || 
						RegiosFileManager.regionz1[entityRegionIndex.get(latestEntity)] > RegiosFileManager.regionz1[entitySubRegionIndex.get(latestEntity)] &&
						RegiosFileManager.regionz1[entityRegionIndex.get(latestEntity)] < RegiosFileManager.regionz2[entitySubRegionIndex.get(latestEntity)])){
			useSubIndex = false;
		} else {
			useSubIndex = true;
		}
		} else {
			useSubIndex = false;
		}
		
		
		if(useSubIndex){
			if(!RegiosFileManager.regionCreaturesSpawn[entitySubRegionIndex.get(latestEntity)]){
				event.setCancelled(true);
			}
		} else {
			if(entityRegionIndex.containsKey(latestEntity)){
				if(!RegiosFileManager.regionCreaturesSpawn[entityRegionIndex.get(latestEntity)]){
					event.setCancelled(true);
				}
			}
		}
		
		entityRegionIndex.clear();
		entitySubRegionIndex.clear();
		
		/*if(latestCreature == CreatureType.CREEPER){
			if(!RegiosFileManager.spawnCreepers){
				event.setCancelled(true);
			}
		}*/
		
		
	}
	
	
	
}
