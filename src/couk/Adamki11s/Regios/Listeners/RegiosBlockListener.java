package couk.Adamki11s.Regios.Listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import couk.Adamki11s.Checks.PermChecks;
import couk.Adamki11s.Extras.Events.ExtrasEvents;
import couk.Adamki11s.Extras.Regions.ExtrasRegions;
import couk.Adamki11s.Regios.Commands.CreationCommands;
import couk.Adamki11s.Regios.Regions.GlobalRegionManager;
import couk.Adamki11s.Regios.Regions.GlobalWorldSetting;
import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.Regios.Regions.SubRegionManager;

public class RegiosBlockListener extends BlockListener{
	
	private static final GlobalRegionManager grm = new GlobalRegionManager();
	private static final ExtrasEvents extEvt = new ExtrasEvents();
	private static final ExtrasRegions extReg = new ExtrasRegions();
	private static final SubRegionManager srm = new SubRegionManager();
	private static final PermChecks permChecks = new PermChecks();
	private static final CreationCommands creationCommands = new CreationCommands();
	
	public boolean areChunksEqual(Chunk c1, Chunk c2){
		return (c1.getX() == c2.getX() && c1.getZ() == c2.getZ());
	}
	
	public void extinguish (Block b) {
		if (b.getType() == Material.FIRE) {
			b.setType(Material.AIR);
		}
	}
	
	public void onBlockIgnite(BlockIgniteEvent evt){
		World w = evt.getBlock().getWorld();
		if(!GlobalRegionManager.getGlobalWorldSetting(w).fireEnabled){
			Block b = evt.getBlock();
			extinguish(b.getRelative(1, 0, 0));
			extinguish(b.getRelative(-1, 0, 0));
			extinguish(b.getRelative(0, 1, 0));
			extinguish(b.getRelative(0, -1, 0));
			extinguish(b.getRelative(0, 0, 1));
			extinguish(b.getRelative(0, 0, -1));
			evt.setCancelled(true);
		}
	}

	public void onBlockPlace(BlockPlaceEvent evt){
		Player p = evt.getPlayer();
		Block b = evt.getBlock();
		Location l = b.getLocation();
		World w = b.getWorld();
		Chunk c = w.getChunkAt(l);
		
		GlobalWorldSetting gws = GlobalRegionManager.getGlobalWorldSetting(w);
		
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
			if(gws.invert_protection){
				if(!gws.canBypassWorldChecks(p)){
					p.sendMessage(ChatColor.RED + "[Regios] You are not permitted to build in this area!");
					evt.setCancelled(true);
					return;
				}
			}
			return;
		}
		
		ArrayList<Region> currentRegionSet = new ArrayList<Region>();
		
		for(Region reg : regionSet){
			if(extReg.isInsideCuboid(l, reg.getL1().toBukkitLocation(), reg.getL2().toBukkitLocation())){
				currentRegionSet.add(reg);
			}
		}
		
		if(currentRegionSet.isEmpty()){ //If player is in chunk range but not inside region then cancel the check.
			if(gws.invert_protection){
				if(!gws.canBypassWorldChecks(p)){
					p.sendMessage(ChatColor.RED + "[Regios] You are not permitted to build in this area!");
					evt.setCancelled(true);
					return;
				}
			}
			return;
		}
		
		if(currentRegionSet.size() > 1){
			r = srm.getCurrentRegion(p, currentRegionSet);
		} else {
			r = currentRegionSet.get(0);
		}
		
		if(r.items.isEmpty() && r.isProtected()){
			if(r.canBuild(p, r)){
				return;
			} else {
				evt.setCancelled(true);
				r.sendBuildMessage(p);
				return;
			}
		}
		
		if(!r.items.isEmpty()){
			if(r.canItemBePlaced(p, b.getType(), r)){
				return;
			} else {
				if(!r.canBypass(p, r)){
					evt.setCancelled(true);
					p.sendMessage(ChatColor.RED + "[Regios] You cannot place this item in this region!");
					return;
				}
			}
		}
		
		if(r.isProtected()){
			if(!r.canBuild(p, r)){
				evt.setCancelled(true);
				r.sendBuildMessage(p);
				return;
			}
		}
		
		
		
	}
	
	public void onBlockBreak(BlockBreakEvent evt){
		Player p = evt.getPlayer();
		Block b = evt.getBlock();
		Location l = b.getLocation();
		World w = b.getWorld();
		Chunk c = w.getChunkAt(l);
		
		GlobalWorldSetting gws = GlobalRegionManager.getGlobalWorldSetting(w);
		
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
			if(gws.invert_protection){
				if(!gws.canBypassWorldChecks(p)){
					p.sendMessage(ChatColor.RED + "[Regios] You are not permitted to build in this area!");
					evt.setCancelled(true);
					return;
				}
			}
			return;
		}
		
		ArrayList<Region> currentRegionSet = new ArrayList<Region>();
		
		for(Region reg : regionSet){
			if(extReg.isInsideCuboid(l, reg.getL1().toBukkitLocation(), reg.getL2().toBukkitLocation())){
				currentRegionSet.add(reg);
			}
		}
		
		if(currentRegionSet.isEmpty()){ //If player is in chunk range but not inside region then cancel the check.
			if(gws.invert_protection){
				if(!gws.canBypassWorldChecks(p)){
					p.sendMessage(ChatColor.RED + "[Regios] You are not permitted to build in this area!");
					evt.setCancelled(true);
					return;
				}
			}
			return;
		}
		
		if(currentRegionSet.size() > 1){
			r = srm.getCurrentRegion(p, currentRegionSet);
		} else {
			r = currentRegionSet.get(0);
		}
		
		if(r.isProtected()){
			if(!r.canBuild(p, r)){
				evt.setCancelled(true);
				r.sendBuildMessage(p);
				return;
			}
		}
		
	}

}
