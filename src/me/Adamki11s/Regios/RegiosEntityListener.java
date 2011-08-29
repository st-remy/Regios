package me.Adamki11s.Regios;


import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.painting.PaintingBreakEvent;

import couk.Adamki11s.WorldConfiguration.ConfigurationSettings;

public class RegiosEntityListener extends EntityListener {

	
	public static Regios plugin;
	public static Entity latestEntity;
	public RegiosEntityListener(Regios instance) {
		plugin = instance;
	}
	
	
	private static Location entityLocation;
	
	public void onExplosionPrime(ExplosionPrimeEvent event){
		
		World w = event.getEntity().getLocation().getWorld();	
		int index = RegiosWeatherListener.getIndex(w);
		
		Entity e = event.getEntity();
		
		if(e instanceof Creeper){
			if(!ConfigurationSettings.creepersExplode[index - 1]){
				//Regios.server.broadcastMessage("Creeper Explosion prime cancelled!");
				event.setCancelled(true);
				return;
			}
		}
		
	}
	
	public void onEntityExplode(EntityExplodeEvent event){
		/*if(!RegiosFileManager.creepersExplode){
			event.setCancelled(true);
		}*/
		
		Location loc = event.getEntity().getLocation();
		Entity entity = event.getEntity();
		
		if(entity instanceof LivingEntity){
		
			for(int i = 1; i <= RegiosFileManager.regionCount; i++){
				if( (((loc.getY() <= RegiosFileManager.regiony1[i - 1] + 10) && (loc.getY() >= RegiosFileManager.regiony2[i - 1] - 10)) || ((loc.getY() >= RegiosFileManager.regiony1[i - 1] - 10) && (loc.getY() <= RegiosFileManager.regiony2[i - 1] + 10))) && (((loc.getZ() <= RegiosFileManager.regionz1[i - 1] + 10) && (loc.getZ() >= RegiosFileManager.regionz2[i - 1] - 10)) || ((loc.getZ() >= RegiosFileManager.regionz1[i - 1] - 10) && (loc.getZ() <= RegiosFileManager.regionz2[i - 1] + 10)))  &&  (((loc.getX() <= RegiosFileManager.regionx1[i - 1] + 10) && (loc.getX() >= RegiosFileManager.regionx2[i - 1] - 10)) || ((loc.getX() >= RegiosFileManager.regionx1[i - 1] - 10) && (loc.getX() <= RegiosFileManager.regionx2[i - 1] + 10))) && (((loc.getX() <= RegiosFileManager.regionx1[i - 1] + 10) && (loc.getX() >= RegiosFileManager.regionx2[i - 1] - 10)) || ((loc.getX() >= RegiosFileManager.regionx1[i - 1] - 10) && (loc.getX() <= RegiosFileManager.regionx2[i - 1] + 10)))   ){
						if(RegiosFileManager.regionProtected[i - 1]){
							event.setCancelled(true);
							entity.remove();
						}
				}
			}
		
		}
		
	}
	
	
	public void onEntityDamage(EntityDamageEvent event){	
		
		if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent entEvent = (EntityDamageByEntityEvent)event;
			if ( (entEvent.getDamager() instanceof Player) && (entEvent.getEntity() instanceof Player) ) {
				
				Player attacker = (Player)entEvent.getDamager();
				Player defender = (Player)entEvent.getEntity();
				
				if(attacker != null && defender != null){
					
						if(RegiosPlayerListener.playerInsideRegion.get(defender)){
							if(RegiosFileManager.regionPVPEnabled[RegiosPlayerListener.playerRegionIndex.get(defender) - 1] == false){
								event.setDamage(0);
								event.setCancelled(true);
							}
						}
						if(RegiosPlayerListener.playerInsideRegion.get(attacker)){
							if(RegiosFileManager.regionPVPEnabled[RegiosPlayerListener.playerRegionIndex.get(attacker) - 1] == false){
								event.setDamage(0);
								attacker.sendMessage(ChatColor.RED + "PVP has been disabled for this Region!");
								event.setCancelled(true);
							}
						}
					
					
					if(RegiosFileManager.regionPVPEnabled[RegiosPlayerListener.playerRegionIndex.get(defender) - 1] && RegiosFileManager.regionPVPEnabled[RegiosPlayerListener.playerRegionIndex.get(attacker) - 1]){
						if(RegiosPlayerListener.playerInsideRegion.get(attacker) && RegiosPlayerListener.playerInsideRegion.get(defender)){
							if(RegiosPlayerListener.playerRegionIndex.get(attacker) == RegiosPlayerListener.playerRegionIndex.get(defender)){
								defender.damage(event.getDamage());
							}
						}
					}
					
				}
				
			}
			
			
		}
				
				
		if(event.getEntity() instanceof Player){
		
		/*if(event.getCause() == DamageCause.ENTITY_EXPLOSION && (!RegiosFileManager.creepersExplode)){
			event.setDamage(0);
			event.setCancelled(true);
		}
		*/
		Player player = (Player) event.getEntity();
		
			
		if(RegiosFileManager.regionCount > 0){
		if(RegiosPlayerListener.playerInsideRegion.containsKey(player)){
		if(RegiosPlayerListener.playerInsideRegion.get(player)){
			if(!RegiosFileManager.regionHealthEnabled[RegiosPlayerListener.playerRegionIndex.get(player) - 1]){
					event.setDamage(0);
					event.setCancelled(true);
				}
		}
		}
		}
		}
	}
	
	
}
