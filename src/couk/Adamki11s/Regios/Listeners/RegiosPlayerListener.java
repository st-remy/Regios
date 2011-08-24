package couk.Adamki11s.Regios.Listeners;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.material.Door;

import couk.Adamki11s.Extras.Events.ExtrasEvents;
import couk.Adamki11s.Extras.Regions.ExtrasRegions;
import couk.Adamki11s.Regios.Checks.PermChecks;
import couk.Adamki11s.Regios.Commands.CreationCommands;
import couk.Adamki11s.Regios.Regions.GlobalRegionManager;
import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.Regios.Regions.RegionLocation;
import couk.Adamki11s.Regios.Regions.SubRegionManager;
import couk.Adamki11s.Regios.Scheduler.HealthRegeneration;
import couk.Adamki11s.Regios.SpoutInterface.SpoutInterface;

public class RegiosPlayerListener extends PlayerListener {
	
	private static final GlobalRegionManager grm = new GlobalRegionManager();
	private static final ExtrasEvents extEvt = new ExtrasEvents();
	private static final ExtrasRegions extReg = new ExtrasRegions();
	private static final SubRegionManager srm = new SubRegionManager();
	private static final PermChecks permChecks = new PermChecks();
	private static final CreationCommands creationCommands = new CreationCommands();
	
	private static HashMap<Player, Region> regionBinding = new HashMap<Player, Region>();
	
	private static HashMap<Player, Location> outsideRegionLocation = new HashMap<Player, Location>();
	private static HashMap<Player, Location> insideRegionLocation = new HashMap<Player, Location>();
	
	public void onPlayerJoin(PlayerJoinEvent evt){
		SpoutInterface.spoutEnabled.put(evt.getPlayer(), false);
	}
	
	public void onPlayerInteract(PlayerInteractEvent evt){
		
		if(evt.getClickedBlock() == null){ return; }
		
		Location l = evt.getClickedBlock().getLocation();
		Player p = evt.getPlayer();
		World w = p.getWorld();
		Chunk c = w.getChunkAt(l);
		Region r;
		ArrayList<Region> regionSet = new ArrayList<Region>();
		
		Block b = evt.getClickedBlock();
		
		
		if(creationCommands.isSetting(p)){
			Action act = evt.getAction();
			if(act == Action.LEFT_CLICK_BLOCK){
				creationCommands.setFirst(p, evt.getClickedBlock().getLocation());
				return;
			} else if(act == Action.RIGHT_CLICK_BLOCK){
				creationCommands.setSecond(p, evt.getClickedBlock().getLocation());
				return;
			}
		}	
		
		for(Region region : GlobalRegionManager.getRegions()){
			for(Chunk chunk : region.getChunkGrid().getChunks()){
				if(chunk.getWorld() == w){
					if(areChunksEqual(chunk, c)){
						if(!regionSet.contains(region)){
							regionSet.add(region);
						}
					}
				}
			}
		}
		
		if(regionSet.isEmpty()){
			return;
		}
		
		ArrayList<Region> currentRegionSet = new ArrayList<Region>();
		
		for(Region reg : regionSet){
			if(extReg.isInsideCuboid(l, reg.getL1().toBukkitLocation(), reg.getL2().toBukkitLocation())){
				currentRegionSet.add(reg);
			}
		}
		
		if(currentRegionSet.isEmpty()){ //If player is in chunk range but not inside region then cancel the check.
			return;
		}
		
		if(currentRegionSet.size() > 1){
			r = srm.getCurrentRegion(p, currentRegionSet);
		} else {
			r = currentRegionSet.get(0);
		}
		
		if(r.isPreventingInteraction()){
			if(!r.canBuild(p, r)){
				p.sendMessage(ChatColor.RED + "[Regios] You cannot interact within this region!");
				return;
			}
		}
		
		if(b.getTypeId() == 71 || b.getTypeId() == 64){
			if(r.areDoorsLocked()){
				if(!r.canBuild(p, r)){
					p.sendMessage(ChatColor.RED + "[Regios] Doors are locked for this region!");
					Door d = new Door(b.getType());
					d.setOpen(false);
					evt.setCancelled(true);
				}
			}
		}
		
		
	}
	
	public boolean areChunksEqual(Chunk c1, Chunk c2){
		return (c1.getX() == c2.getX() && c1.getZ() == c2.getZ());
	}
	
	public void onPlayerMove(PlayerMoveEvent evt){

		if(!extEvt.didMove(evt)){
			return; //Cancel the check if the player did not move
		}
		
		Player p = evt.getPlayer();
		World w = p.getWorld();
		Chunk c = w.getChunkAt(evt.getPlayer().getLocation());
		Region r;
		
		ArrayList<Region> regionSet = new ArrayList<Region>();
		
		for(Region region : GlobalRegionManager.getRegions()){
			for(Chunk chunk : region.getChunkGrid().getChunks()){
				if(chunk.getWorld() == w){
					if(areChunksEqual(chunk, c)){
						if(!regionSet.contains(region)){
							regionSet.add(region);
						}
					}
				}
			}
		}
		
		if(regionSet.isEmpty()){
			return;
		}
		
		if(regionBinding.containsKey(p)){
				Region binding = regionBinding.get(p);
				if(binding == null) { return; }
				if(binding.isPreventingEntry() && extReg.isInsideCuboid(p, binding.getL1().toBukkitLocation(), binding.getL2().toBukkitLocation())){
					if(!binding.canEnter(p, binding)){
						if(!binding.isPasswordEnabled()){
							p.teleport(outsideRegionLocation.get(p));
							binding.sendPreventEntryMessage(p);
							return;
						} else {
							if(!binding.isAuthenticated(p)){
								binding.sendAuthenticationMessage(p);
								p.teleport(outsideRegionLocation.get(p));
								binding.sendPreventExitMessage(p);
								return;
							}
						}
					}
				}
				
				if(binding.isPreventingExit() && !extReg.isInsideCuboid(p, binding.getL1().toBukkitLocation(), binding.getL2().toBukkitLocation())){
					if(!binding.canExit(p, binding)){
						if(!binding.isPasswordEnabled()){
							p.teleport(insideRegionLocation.get(p));
							binding.sendPreventExitMessage(p);
							return;
						} else {
							if(!binding.isAuthenticated(p)){
								binding.sendAuthenticationMessage(p);
								p.teleport(insideRegionLocation.get(p));
								binding.sendPreventExitMessage(p);
								return;
							}
						}
					}
				}
				
				if(!extReg.isInsideCuboid(p, binding.getL1().toBukkitLocation(), binding.getL2().toBukkitLocation())){
					if(HealthRegeneration.isRegenerator(p)){
						HealthRegeneration.removeRegenerator(p);
					}
					binding.sendLeaveMessage(p);
				}
				
		}
		
		ArrayList<Region> currentRegionSet = new ArrayList<Region>();
		
		for(Region reg : regionSet){
			if(extReg.isInsideCuboid(p, reg.getL1().toBukkitLocation(), reg.getL2().toBukkitLocation())){
				currentRegionSet.add(reg);
				insideRegionLocation.put(p, p.getLocation());
			}
		}
		
		if(currentRegionSet.isEmpty()){ //If player is in chunk range but not inside region then cancel the check.
			if(evt.getFrom().getBlockY() == evt.getTo().getBlockY()){ //To prevent people getting stuck if jumping into a region
				outsideRegionLocation.put(p, p.getLocation());
			}
			return;
		}
		
		if(currentRegionSet.size() > 1){
			r = srm.getCurrentRegion(p, currentRegionSet);
			regionBinding.put(p, r);
		} else {
			r = currentRegionSet.get(0);
			regionBinding.put(p, r);
		}
		
		if(r.isPreventingEntry()){
			if(!r.canEnter(p)){
				p.teleport(outsideRegionLocation.get(p));
				r.sendPreventEntryMessage(p);
			}
		}
		
		r.sendWelcomeMessage(p);
		if(!HealthRegeneration.isRegenerator(p)){
			HealthRegeneration.addRegenerator(p, r.healthRegen);
		}
		
		//__________________________________
		//^^^^ Messages & Entry control ^^^^
		//__________________________________
		
		if(r.getVelocityWarp() != 0){
			p.setVelocity(p.getLocation().getDirection().multiply(((r.getVelocityWarp())*(0.3))/2).setY(0.1));
		}
		
		
		
	}
	
	public String getLocation(Location l){
		return l.getX() + " | " + l.getY() + " | " + l.getZ();
	}

}
