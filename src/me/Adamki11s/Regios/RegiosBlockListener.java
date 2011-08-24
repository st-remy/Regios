package me.Adamki11s.Regios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

import couk.Adamki11s.EconomyHandler.EconomyDatabase;
import couk.Adamki11s.WorldConfiguration.ConfigurationSettings;

public class RegiosBlockListener extends BlockListener{
	
	public static Regios plugin;
	

	public static Map<Player, Location> playerLocation = new HashMap<Player, Location>();
	
	public static Map<Player, Boolean> playerInsideRegion = new HashMap<Player, Boolean>();
	public static Map<Player, Integer> playerRegionIndex = new HashMap<Player, Integer>();
	
	public static Map<Player, Boolean> playerInsideSubRegion1 = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> enteredSubRegion1 = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> playerCanBuild = new HashMap<Player, Boolean>();
	public static Map<Player, Integer> subRegionIndex1 = new HashMap<Player, Integer>();
	
	public RegiosBlockListener(Regios instance) {
		plugin = instance;
	}
	
	public static List<Location> blockFlowEvents = new ArrayList<Location>();
	
	double x1, x2, y1, y2, z1, z2;
	
	/*public void onBlockFromTo(BlockFromToEvent event){
		if(event.getToBlock().getType() == Material.AIR && !blockFlowEvents.contains(event.getToBlock().getLocation())){
			blockFlowEvents.add(event.getToBlock().getLocation());
			//blockFlowEvents.add(event.getBlock().getLocation());
		}
		
		Location toLoc = event.getToBlock().getLocation();
		
			int count = 0;
			Block blockFrom = event.getBlock();
	        Block blockTo = event.getToBlock();
	        Location from = blockFrom.getLocation(), to = blockTo.getLocation();
	        
	      
			
				for(Location loc : RegiosBlockListener.blockFlowEvents){
					count++;
					for(int i = 1; i <= RegiosFileManager.regionCount; i++){	
						if(blockFrom.getType() == Material.WATER || blockFrom.getType() == Material.LAVA ||
						blockTo.getType() == Material.WATER || blockTo.getType() == Material.LAVA){
						x1 = RegiosFileManager.regionx1[i - 1]; x2 = RegiosFileManager.regionx2[i - 1];
						y1 = RegiosFileManager.regiony1[i - 1]; y2 = RegiosFileManager.regiony2[i - 1];
						z1 = RegiosFileManager.regionz1[i - 1]; z2 = RegiosFileManager.regionz2[i - 1];
						
						if((   ((x1 == to.getBlockX() || x1 == to.getBlockX() + 1 || x1 == to.getBlockX() - 1) ||  (x2 == to.getBlockX() || x2 == to.getBlockX() + 1 || x2 == to.getBlockX() - 1)) &&
								((z1 == to.getBlockZ() || z1 == to.getBlockZ() + 1 || z1 == to.getBlockZ() - 1) ||  (z2 == to.getBlockZ() || z2 == to.getBlockZ() + 1 || z2 == to.getBlockZ() - 1))) ||
								(   ((x1 == from.getBlockX() || x1 == from.getBlockX() + 1 || x1 == from.getBlockX() - 1) ||  (x2 == from.getBlockX() || x2 == from.getBlockX() + 1 || x2 == from.getBlockX() - 1)) &&
										((z1 == from.getBlockZ() || z1 == from.getBlockZ() + 1 || z1 == from.getBlockZ() - 1) ||  (z2 == from.getBlockZ() || z2 == from.getBlockZ() + 1 || z2 == from.getBlockZ() - 1)))){
											
					    clearFluids(i, count, blockTo);
					    
						} else {												
							if(blockFlowEvents.contains(count - 1)){
								blockFlowEvents.remove(count - 1);
							}
						}
						
						}
						
					}
					
				}
				
	}*/
	
	private void clearFluids(int i, int count, Block blockTo){
		if(RegiosFileManager.regionProtected[i - 1]){
			blockTo.setTypeId(0);
		} else {
			if(blockFlowEvents.contains(count - 1)){
				blockFlowEvents.remove(count - 1);
			}
		}
	}
	
	
	public void onBlockIgnite(BlockIgniteEvent event){
		Player p = event.getPlayer();
		World w = event.getBlock().getLocation().getWorld();
		
		int index = RegiosWeatherListener.getIndex(w);
		if(p != null){
			if(!p.isOp() && !(Regios.hasPermissions && Regios.permissionHandler.has(p, "regios.bypass-protection"))){
				if(!ConfigurationSettings.fireEnabled[index - 1]){
					ExtinguishFire(event.getBlock().getFace(BlockFace.NORTH));
					ExtinguishFire(event.getBlock().getFace(BlockFace.EAST));
					ExtinguishFire(event.getBlock().getFace(BlockFace.SOUTH));
					ExtinguishFire(event.getBlock().getFace(BlockFace.WEST));
					ExtinguishFire(event.getBlock().getFace(BlockFace.UP));
					ExtinguishFire(event.getBlock().getFace(BlockFace.DOWN));
					event.setCancelled(true);
					ExtinguishFire(event.getBlock().getFace(BlockFace.NORTH));
					ExtinguishFire(event.getBlock().getFace(BlockFace.EAST));
					ExtinguishFire(event.getBlock().getFace(BlockFace.SOUTH));
					ExtinguishFire(event.getBlock().getFace(BlockFace.WEST));
					ExtinguishFire(event.getBlock().getFace(BlockFace.UP));
					ExtinguishFire(event.getBlock().getFace(BlockFace.DOWN));
					//p.sendMessage(ChatColor.RED + "[Regios] Fire is disabled on this world!");
					return;
				}
			}
		}
		
		Location blockLoc = event.getBlock().getLocation();
		
		String wn = event.getBlock().getWorld().getName();
		
		for(int i = 1; i <= RegiosFileManager.regionCount; i++){
			
			if( (((blockLoc.getY() <= RegiosFileManager.regiony1[i - 1] + 2) && (blockLoc.getY() >= RegiosFileManager.regiony2[i - 1] - 1)) || ((blockLoc.getY() >= RegiosFileManager.regiony1[i - 1] - 1) && (blockLoc.getY() <= RegiosFileManager.regiony2[i - 1] + 2))) && (((blockLoc.getZ() <= RegiosFileManager.regionz1[i - 1]) && (blockLoc.getZ() >= RegiosFileManager.regionz2[i - 1])) || ((blockLoc.getZ() >= RegiosFileManager.regionz1[i - 1]) && (blockLoc.getZ() <= RegiosFileManager.regionz2[i - 1])))  &&  (((blockLoc.getX() <= RegiosFileManager.regionx1[i - 1]) && (blockLoc.getX() >= RegiosFileManager.regionx2[i - 1])) || ((blockLoc.getX() >= RegiosFileManager.regionx1[i - 1]) && (blockLoc.getX() <= RegiosFileManager.regionx2[i - 1]))) && (((blockLoc.getX() <= RegiosFileManager.regionx1[i - 1]) && (blockLoc.getX() >= RegiosFileManager.regionx2[i - 1])) || ((blockLoc.getX() >= RegiosFileManager.regionx1[i - 1]) && (blockLoc.getX() <= RegiosFileManager.regionx2[i - 1])))   ){
					if(RegiosFileManager.regionProtected[i - 1] && wn.equalsIgnoreCase(RegiosFileManager.regionWorldName[i - 1])){
						event.setCancelled(true);
						ExtinguishFire(event.getBlock().getFace(BlockFace.NORTH));
						ExtinguishFire(event.getBlock().getFace(BlockFace.EAST));
						ExtinguishFire(event.getBlock().getFace(BlockFace.SOUTH));
						ExtinguishFire(event.getBlock().getFace(BlockFace.WEST));
						ExtinguishFire(event.getBlock().getFace(BlockFace.UP));
						ExtinguishFire(event.getBlock().getFace(BlockFace.DOWN));
						event.setCancelled(true);
					}
			}
		}		
		
	}
	
	public void ExtinguishFire (Block block) {
		if (block.getType() == Material.FIRE) {
			block.setType(Material.AIR);
		}
	}
	
	/*public void onBlockBurn(BlockBurnEvent event){
		
		World w = event.getBlock().getLocation().getWorld();
		int index = RegiosWeatherListener.getIndex(w);
		
		if(!ConfigurationSettings.fireEnabled[index - 1]){
			ExtinguishFire(event.getBlock().getFace(BlockFace.NORTH));
			ExtinguishFire(event.getBlock().getFace(BlockFace.EAST));
			ExtinguishFire(event.getBlock().getFace(BlockFace.SOUTH));
			ExtinguishFire(event.getBlock().getFace(BlockFace.WEST));
			ExtinguishFire(event.getBlock().getFace(BlockFace.UP));
			ExtinguishFire(event.getBlock().getFace(BlockFace.DOWN));
			event.setCancelled(true);
			ExtinguishFire(event.getBlock().getFace(BlockFace.NORTH));
			ExtinguishFire(event.getBlock().getFace(BlockFace.EAST));
			ExtinguishFire(event.getBlock().getFace(BlockFace.SOUTH));
			ExtinguishFire(event.getBlock().getFace(BlockFace.WEST));
			ExtinguishFire(event.getBlock().getFace(BlockFace.UP));
			ExtinguishFire(event.getBlock().getFace(BlockFace.DOWN));
		}
		
		Location blockLoc = event.getBlock().getLocation();
		
		String wn = event.getBlock().getWorld().getName();
		
		for(int i = 1; i <= RegiosFileManager.regionCount; i++){
			
			//REGION CHECKS
			if( (((blockLoc.getY() <= RegiosFileManager.regiony1[i - 1] + 2) && (blockLoc.getY() >= RegiosFileManager.regiony2[i - 1] - 1)) || ((blockLoc.getY() >= RegiosFileManager.regiony1[i - 1] - 1) && (blockLoc.getY() <= RegiosFileManager.regiony2[i - 1] + 2))) && (((blockLoc.getZ() <= RegiosFileManager.regionz1[i - 1]) && (blockLoc.getZ() >= RegiosFileManager.regionz2[i - 1])) || ((blockLoc.getZ() >= RegiosFileManager.regionz1[i - 1]) && (blockLoc.getZ() <= RegiosFileManager.regionz2[i - 1])))  &&  (((blockLoc.getX() <= RegiosFileManager.regionx1[i - 1]) && (blockLoc.getX() >= RegiosFileManager.regionx2[i - 1])) || ((blockLoc.getX() >= RegiosFileManager.regionx1[i - 1]) && (blockLoc.getX() <= RegiosFileManager.regionx2[i - 1]))) && (((blockLoc.getX() <= RegiosFileManager.regionx1[i - 1]) && (blockLoc.getX() >= RegiosFileManager.regionx2[i - 1])) || ((blockLoc.getX() >= RegiosFileManager.regionx1[i - 1]) && (blockLoc.getX() <= RegiosFileManager.regionx2[i - 1])))   ){
					if(RegiosFileManager.regionProtected[i - 1] && wn.equalsIgnoreCase(RegiosFileManager.regionWorldName[i - 1])){
						event.setCancelled(true);
						ExtinguishFire(event.getBlock().getFace(BlockFace.NORTH));
						ExtinguishFire(event.getBlock().getFace(BlockFace.EAST));
						ExtinguishFire(event.getBlock().getFace(BlockFace.SOUTH));
						ExtinguishFire(event.getBlock().getFace(BlockFace.WEST));
						ExtinguishFire(event.getBlock().getFace(BlockFace.UP));
						ExtinguishFire(event.getBlock().getFace(BlockFace.DOWN));
						event.setCancelled(true);
					}
			}
		}
			
			
	}*/
	
	
	
	
public void onBlockPlace(BlockPlaceEvent event){
		
		Player player = event.getPlayer();
		Material block = event.getBlock().getType();
		Block blockPlaced = event.getBlockPlaced();
		Location location = event.getBlock().getLocation();
		World w = event.getBlock().getLocation().getWorld();
		
		int index = RegiosWeatherListener.getIndex(w);
		if(event.getBlockPlaced().getType() == Material.TNT){
			if(!player.isOp() && !(Regios.hasPermissions && Regios.permissionHandler.has(player, "regios.bypass-protection"))){
				if(!ConfigurationSettings.TNTEnabled[index - 1]){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "[Regios] TNT is banned on this world!");
					return;
				}
			}
		}
		
		if(event.getBlockPlaced().getType() == Material.LAVA || event.getBlockPlaced().getType() == Material.STATIONARY_LAVA){
			if(!player.isOp() && !(Regios.hasPermissions && Regios.permissionHandler.has(player, "regios.bypass-protection"))){
				if(!ConfigurationSettings.lavaEnabled[index - 1]){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "[Regios] Lava is banned on this world!");
					return;
				}
			}
		}
		
		if(event.getBlockPlaced().getType() == Material.WATER || event.getBlockPlaced().getType() == Material.STATIONARY_WATER){
			if(!player.isOp() && !(Regios.hasPermissions && Regios.permissionHandler.has(player, "regios.bypass-protection"))){
				if(!ConfigurationSettings.waterEnabled[index - 1]){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "[Regios] Water is banned on this world!");
					return;
				}
			}
		}
		
		int i;
		
		if(!playerRegionIndex.containsKey(player)){
		playerRegionIndex.put(player, 1);
		}
		
		String wn = event.getBlockPlaced().getWorld().getName();
		String pwn = event.getPlayer().getLocation().getWorld().getName();
		
		playerLocation.put(player, location);		
		playerInsideRegion.put(player, false);
		
		playerCanBuild.put(player, false);
		
		boolean triedLava = false, triedWater = false;
		
		/*if(!player.isOp() || (Regios.hasPermissions && !(Regios.permissionHandler.has(player, "regios.override")))){
		for(Material material : RegiosFileManager.IllegalBlocks){
			  if(block == material){
				  player.sendMessage(ChatColor.RED + "You are prohibited from using " + ChatColor.AQUA + block);
				  if(block == Material.TNT){
					  event.getBlock().setType(Material.AIR);
				  }
				  event.setCancelled(true);
			  }
			}
		}*/
		
		
		for(i = 1; i <= RegiosFileManager.regionCount; i++){
			
			//REGION CHECKS
			if( (((playerLocation.get(player).getY() <= RegiosFileManager.regiony1[i - 1] + 2) && (playerLocation.get(player).getY() >= RegiosFileManager.regiony2[i - 1] - 1)) || ((playerLocation.get(player).getY() >= RegiosFileManager.regiony1[i - 1] - 1) && (playerLocation.get(player).getY() <= RegiosFileManager.regiony2[i - 1] + 2))) && (((playerLocation.get(player).getZ() <= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() >= RegiosFileManager.regionz2[i - 1])) || ((playerLocation.get(player).getZ() >= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() <= RegiosFileManager.regionz2[i - 1])))  &&  (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1]))) && (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1])))   ){
				if(pwn.equalsIgnoreCase(RegiosFileManager.regionWorldName[i - 1])){		
					playerInsideRegion.put(player, true);
					playerRegionIndex.put(player, i);
						//player.sendMessage(ChatColor.GREEN + "Block placed in world " + event.getPlayer().getWorld().getName());
				}

			}
			//REGION CHECKS
			
			
			
			/*if( (((playerLocation.get(player).getY() <= RegiosFileManager.regiony1[i - 1] + 2) && (playerLocation.get(player).getY() >= RegiosFileManager.regiony2[i - 1] - 1)) || ((playerLocation.get(player).getY() >= RegiosFileManager.regiony1[i - 1] - 1) && (playerLocation.get(player).getY() <= RegiosFileManager.regiony2[i - 1] + 2))) && (((playerLocation.get(player).getZ() <= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() >= RegiosFileManager.regionz2[i - 1])) || ((playerLocation.get(player).getZ() >= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() <= RegiosFileManager.regionz2[i - 1])))  &&  (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1]))) && (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1])))   ){
					if(event.getItemInHand().getType() == Material.WATER_BUCKET && event.getBlockPlaced().getType() == Material.WATER_BUCKET ||
				   event.getItemInHand().getType() == Material.WATER && event.getBlockPlaced().getType() == Material.WATER){
					playerInsideRegion.put(player, true);
					playerRegionIndex.put(player, i);
					triedWater = true;
				}
				if(event.getItemInHand().getType() == Material.LAVA  && event.getBlockPlaced().getType() == Material.LAVA ||
				   event.getItemInHand().getType() == Material.LAVA_BUCKET  && event.getBlockPlaced().getType() == Material.LAVA_BUCKET){
					playerInsideRegion.put(player, true);
					playerRegionIndex.put(player, i);
					triedLava = true;
				}
		}*/
			
			
			
		}//END OF FOR LOOP!
		
		
		
		if(playerInsideRegion.get(player)){
			if(RegiosFileManager.regionProtected[playerRegionIndex.get(player) - 1]){
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.bypass")))){
					playerCanBuild.put(player, true);
					//player.sendMessage("you have regio.bypass");
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.override")))){
					playerCanBuild.put(player, true);
					//player.sendMessage("you have regio.override");
				}
				
				if(RegiosFileManager.formatPlayer(player).equalsIgnoreCase(RegiosFileManager.regionOwner[playerRegionIndex.get(player) - 1])){
					playerCanBuild.put(player, true);
					//player.sendMessage("you own the region");
				}
				
				if(player.isOp()){
					playerCanBuild.put(player, true);
					//player.sendMessage("you are an op");
				}
				
				/*player.sendMessage(""+ RegiosFileManager.exceptionCount[playerRegionIndex.get(player) - 1]);
				player.sendMessage(""+ playerRegionIndex.get(player));
				player.sendMessage(""+ RegiosFileManager.exceptionCount[playerRegionIndex.get(player) - 1]);
				player.sendMessage(""+ RegiosFileManager.regionExceptions[playerRegionIndex.get(player) - 1][0]);
				player.sendMessage(""+ player.getDisplayName());*/
				
				for(int a = 1; a <= RegiosFileManager.exceptionCount[playerRegionIndex.get(player) - 1]; a++){
					//player.sendMessage(""+ RegiosFileManager.regionExceptions[playerRegionIndex.get(player) - 1][a - 1]);
					//player.sendMessage(""+ player.getDisplayName());
					if(RegiosFileManager.regionExceptions[playerRegionIndex.get(player) - 1][a - 1].equalsIgnoreCase(RegiosFileManager.formatPlayer(player))){
						playerCanBuild.put(player, true);
						//player.sendMessage("you are an exception");
					}
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios." + player.getWorld().getName() + ".bypass")))){
					playerCanBuild.put(player, true);
				}
				
				
				if(!playerCanBuild.get(player)){
					if(event.getBlockPlaced().getType() != Material.MINECART){
						/*if(triedLava){
							player.sendMessage(ChatColor.RED + "You cannot damage a protected area with lava! Nice try though.");
							Block b = event.getBlockPlaced();
							b.setTypeId(0);
							event.setCancelled(true);
							return;
						}
						if(triedWater){
							player.sendMessage(ChatColor.RED + "You cannot damage a protected area with water! Nice try though.");
							Block b = event.getBlockPlaced();
							b.setTypeId(0);
							event.setCancelled(true);
							return;
						}*/
						player.sendMessage(ChatColor.RED + "You do not have the required permissions to build here!");
						Block b = event.getBlockPlaced();
						b.setTypeId(0);
						event.setCancelled(true);
						return;
					}
				}
			}
		}
		
	}





/*	public void onBlockPhysics(BlockPhysicsEvent event){
		Location loc = event.getBlock().getLocation();	
		//Regios.server.broadcastMessage("CHANGED = " + event.getChangedTypeId());
	}*/

	public void onSignChange(SignChangeEvent event){
		if(Regios.iConomyEnabled){
			boolean regionSign = false;
			int price = 0; int rentPeriod = 0;
			String region = null;
			Player player = event.getPlayer();
				if(event.getLine(0).contains("[Regios]")){
					regionSign = true;
					//event.setLine(0, "�a[Regios]");
                                        event.setLine(0, ChatColor.GREEN + "[Regios]");
					region = event.getLine(1);
					try{
						price = Integer.parseInt(event.getLine(2));
					}catch (NumberFormatException ex){
						player.sendMessage(ChatColor.RED + "[Regios] The price must be an integer!");
						return;
					}
				}
				
			if(regionSign){
				if(RegiosFileManager.doesRegionExist(region, player)){
					if(RegiosFileManager.isOwner(region, player)){
						RegiosFileManager.doesRegionExist(region, player);
						EconomyDatabase.addSign(region, price, RegiosFileManager.regionOwner[RegiosFileManager.regionIndex]);
						player.sendMessage(ChatColor.GREEN + "[Regios] Sign created successfully!");
					} else {
						player.sendMessage(ChatColor.RED + "[Regios] You do not have permission to sell this Region!");
						event.getBlock().setTypeId(0);
						return;
					}
				} else {
					player.sendMessage(ChatColor.RED + "[Regios] That region does not exist!");
					event.getBlock().setTypeId(0);
					return;
				}
			}
		}
		
	}
	
	public void onBlockBreak(BlockBreakEvent event){
		
		Player player = event.getPlayer();
		Material block = event.getBlock().getType();
		Location location = event.getBlock().getLocation();
		
		String wn = event.getBlock().getWorld().getName();
		String pwn = event.getPlayer().getLocation().getWorld().getName();
		
		int i;
		
		if(!playerRegionIndex.containsKey(player)){
		playerRegionIndex.put(player, 1);
		}
		
		//player.sendMessage(ChatColor.RED + wn + ChatColor.GREEN + event.getPlayer().getWorld().getName());
		
		playerLocation.put(player, location);		
		playerInsideRegion.put(player, false);
		
		playerCanBuild.put(player, false);
		
		/*if(!player.isOp() || (Regios.hasPermissions && !(Regios.permissionHandler.has(player, "regios.override")))){
		for(Material material : RegiosFileManager.IllegalBlocks){
			  if(block == material){
				  player.sendMessage(ChatColor.RED + "You are prohibited from using " + ChatColor.AQUA + block);
				  if(block == Material.TNT){
					  event.getBlock().setType(Material.AIR);
				  }
				  event.setCancelled(true);
			  }
			}
		}*/
		
		
		for(i = 1; i <= RegiosFileManager.regionCount; i++){
			
			//REGION CHECKS
			if( (((playerLocation.get(player).getY() <= RegiosFileManager.regiony1[i - 1] + 2) && (playerLocation.get(player).getY() >= RegiosFileManager.regiony2[i - 1] - 1)) || ((playerLocation.get(player).getY() >= RegiosFileManager.regiony1[i - 1] - 1) && (playerLocation.get(player).getY() <= RegiosFileManager.regiony2[i - 1] + 2))) && (((playerLocation.get(player).getZ() <= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() >= RegiosFileManager.regionz2[i - 1])) || ((playerLocation.get(player).getZ() >= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() <= RegiosFileManager.regionz2[i - 1])))  &&  (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1]))) && (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1])))   ){
				if(pwn.equalsIgnoreCase(RegiosFileManager.regionWorldName[i - 1])){			
					playerInsideRegion.put(player, true);
						playerRegionIndex.put(player, i);
				}
			}
			
			
			
			//REGION CHECKS
			
			
			
			
			
			
		}//END OF FOR LOOP!
		
		
		
		if(playerInsideRegion.get(player)){
			if(RegiosFileManager.regionProtected[playerRegionIndex.get(player) - 1]){
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.bypass")))){
					playerCanBuild.put(player, true);
					//player.sendMessage("you have regio.bypass");
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.override")))){
					playerCanBuild.put(player, true);
					//player.sendMessage("you have regio.override");
				}
				
				if(RegiosFileManager.formatPlayer(player).equalsIgnoreCase(RegiosFileManager.regionOwner[playerRegionIndex.get(player) - 1])){
					playerCanBuild.put(player, true);
					//player.sendMessage("you own the region");
				}
				
				if(player.isOp()){
					playerCanBuild.put(player, true);
					//player.sendMessage("you are an op");
				}
				
				/*player.sendMessage(""+ RegiosFileManager.exceptionCount[playerRegionIndex.get(player) - 1]);
				player.sendMessage(""+ playerRegionIndex.get(player));
				player.sendMessage(""+ RegiosFileManager.exceptionCount[playerRegionIndex.get(player) - 1]);
				player.sendMessage(""+ RegiosFileManager.regionExceptions[playerRegionIndex.get(player) - 1][0]);
				player.sendMessage(""+ player.getDisplayName());*/
				
				for(int a = 1; a <= RegiosFileManager.exceptionCount[playerRegionIndex.get(player) - 1]; a++){
					//player.sendMessage(""+ RegiosFileManager.regionExceptions[playerRegionIndex.get(player) - 1][a - 1]);
					//player.sendMessage(""+ player.getDisplayName());
					if(RegiosFileManager.regionExceptions[playerRegionIndex.get(player) - 1][a - 1].equalsIgnoreCase(RegiosFileManager.formatPlayer(player))){
						playerCanBuild.put(player, true);
						//player.sendMessage("you are an exception");
					}
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios." + player.getWorld().getName() + ".bypass")))){
					playerCanBuild.put(player, true);
				}
				
				
				if(!playerCanBuild.get(player)){
					player.sendMessage(ChatColor.RED + "You do not have the required permissions to build here!");
					event.setCancelled(true);
				}
			}
			
			/*for(int p = 1; i <= RegiosFileManager.regionCount; i++){
				if( (((playerLocation.get(player).getY() <= RegiosFileManager.regiony1[p - 1] + 10) && (playerLocation.get(player).getY() >= RegiosFileManager.regiony2[p - 1] - 10)) || ((playerLocation.get(player).getY() >= RegiosFileManager.regiony1[p - 1] - 10) && (playerLocation.get(player).getY() <= RegiosFileManager.regiony2[p - 1] + 10))) && (((playerLocation.get(player).getZ() <= RegiosFileManager.regionz1[p - 1] + 10) && (playerLocation.get(player).getZ() >= RegiosFileManager.regionz2[p - 1] - 10)) || ((playerLocation.get(player).getZ() >= RegiosFileManager.regionz1[p - 1] - 10) && (playerLocation.get(player).getZ() <= RegiosFileManager.regionz2[p - 1] + 10)))  &&  (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[p - 1] + 10) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[p - 1] - 10)) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[p - 1] - 10) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[p - 1] + 10))) && (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[p - 1] + 10) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[p - 1] - 10)) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[p - 1] - 10) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[p - 1] + 10)))   ){
					if(playerLocation.get(player).getBlock().getType() == Material.TNT){
						if(!player.isOp() && !RegiosFileManager.formatPlayer(player).equalsIgnoreCase(RegiosFileManager.regionOwner[playerRegionIndex.get(player) - 1])){
							if(event.getPlayer().getWorld().getName().equalsIgnoreCase(wn)){	
								player.sendMessage(ChatColor.RED + "You do not have the required permissions to build here!");
								Block b = event.getBlock();
								b.setTypeId(0);
								event.setCancelled(true);
								return;
							}
						}
					}
				}
			}*/
			
		}
		
		
		
	}
	
}
