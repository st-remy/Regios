package me.Adamki11s.Regios;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.bukkit.util.config.Configuration;

import couk.Adamki11s.EconomyHandler.EconomyCore;
import couk.Adamki11s.WorldConfiguration.ConfigurationSettings;

public class RegiosPlayerListener extends PlayerListener {
	
	public static Map<Player, Boolean> settingRegion = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> regionSetFirstPoint = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> regionSetSecondPoint = new HashMap<Player, Boolean>();
	public static Map<Player, Location> regionFirstPointLocation = new HashMap<Player, Location>();
	public static Map<Player, Location> regionSecondPointLocation = new HashMap<Player, Location>();
	public static Map<Player, Location> playerLocation = new HashMap<Player, Location>();
	public static Map<Player, Location> playerTele = new HashMap<Player, Location>();
	public static Map<Player, Location> regionEntryLocation = new HashMap<Player, Location>();
	
	public static Map<Player, Location> prevLoc = new HashMap<Player, Location>();
	public static Map<Player, Location> prevLocSub = new HashMap<Player, Location>();
	
	public static Map<Player, Boolean> playerInsideRegion = new HashMap<Player, Boolean>();
	
	public static Map<Player, Boolean> playerWelcomeMsgSent = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> playerLeaveMsgSent = new HashMap<Player, Boolean>();
	public static Map<Player, Integer> playerRegionIndex = new HashMap<Player, Integer>();
	public static Map<Player, Integer> oldSubIndex = new HashMap<Player, Integer>();
	public static Map<Player, Boolean> playerMsgSentWithinLoop = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> playerCanEnter = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> prohibitMsgSent = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> playerTeled = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> playerTeled2 = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> unlimitedHealth = new HashMap<Player, Boolean>();
	
	public static Map<Player, Integer> oldItemStack = new HashMap<Player, Integer>();
	public static Map<Player, Integer> oldItemStackSize = new HashMap<Player, Integer>();
	public static Map<Player, Integer> oldItemStackLocation = new HashMap<Player, Integer>();
	
	static Map<Player, Long> playerTimes = new HashMap<Player, Long>();
	static Map<Player, Integer> playerTimesDiff = new HashMap<Player, Integer>();
	static Map<Player, Integer> playerSeconds = new HashMap<Player, Integer>();
	static Map<Player, Long> playerTimes2 = new HashMap<Player, Long>();
	static Map<Player, Integer> playerTimesDiff2 = new HashMap<Player, Integer>();
	static Map<Player, Integer> playerSeconds2 = new HashMap<Player, Integer>();
	static Map<Player, Long> playerTimes3 = new HashMap<Player, Long>();
	static Map<Player, Integer> playerTimesDiff3 = new HashMap<Player, Integer>();
	static Map<Player, Integer> playerSeconds3 = new HashMap<Player, Integer>();
	
	
	/*public static Map<Player, Double> halfDistXLoc = new HashMap<Player, Double>();
	public static Map<Player, Double> halfDistX = new HashMap<Player, Double>();
	public static Map<Player, Double> halfDistYLoc = new HashMap<Player, Double>();
	public static Map<Player, Double> halfDistY = new HashMap<Player, Double>();
	public static Map<Player, Double> halfDistZLoc = new HashMap<Player, Double>();
	public static Map<Player, Double> halfDistZ = new HashMap<Player, Double>();*/
	
	public static Map<Player, Boolean> playerCanEnterDefault = new HashMap<Player, Boolean>();
	
	public static Map<Player, Boolean> playerInsideSubRegion1 = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> enteredSubRegion1 = new HashMap<Player, Boolean>();
	public static Map<Player, Integer> subRegionIndex1 = new HashMap<Player, Integer>();
	public static Map<Player, Boolean> playerWelcomeMsgSentSubRegion1 = new HashMap<Player, Boolean>();
	public static Map<Player, Boolean> playerLeaveMsgSentSubRegion1 = new HashMap<Player, Boolean>();
	
	public static Map<Player, Boolean> playerModifyingRegion = new HashMap<Player, Boolean>();
	
	public static Map<Player, Boolean> regionPreventExit = new HashMap<Player, Boolean>();
	
	public static Map<Player, Boolean> playerModifyingOriginalRegion = new HashMap<Player, Boolean>();
	public static Map<Player, Player> movingPlayer = new HashMap<Player, Player>();
	
	public static Map<Player, Boolean> modifyVelocity = new HashMap<Player, Boolean>();
	
	public static Map<Player, Boolean> usePlayerIndex = new HashMap<Player, Boolean>();
	public static Map<Player, Integer> regionHashCode = new HashMap<Player, Integer>();
	public static Map<Player, Integer> regionHashCode2 = new HashMap<Player, Integer>();
	
	public static Map<Player, Integer> playerHealth = new HashMap<Player, Integer>();
	public static Map<Player, Integer> playerIncrement = new HashMap<Player, Integer>();
	
	public static Regios plugin;
	
	public RegiosPlayerListener(Regios instance) {
		plugin = instance;
	}
	
	
	
	
	
	Sign sign = null;
	
	public void onPlayerInteract(PlayerInteractEvent event){
		Player player = event.getPlayer();
	
		//Packet51MapChunk myPacket; new Packet51MapChunk(i, j, k, l, i1, j1, this.playerManager.world)
		//((CraftPlayer)vplayer.getPlayer()).getHandle().netServerHandler.sendPacket(myPacket);
		Block block = event.getClickedBlock();
		
		if(block != null && event.getAction() == Action.RIGHT_CLICK_BLOCK){
			if((block.getType() == Material.SIGN || block.getType() == Material.SIGN_POST || block.getTypeId() == 68)){
				sign = (Sign)block.getState();
				if(sign.getLine(0).contains("[Regios]")){
					RegiosFileManager.doesRegionExist(sign.getLine(1), player);
					if(RegiosFileManager.regionOwner[RegiosFileManager.regionIndex].equalsIgnoreCase(player.getName())){
						player.sendMessage(ChatColor.RED + "[Regios] You cannot buy your own region!");
						return;
					}
					String regionToBuy = sign.getLine(1);
					double price = Integer.parseInt(sign.getLine(2));
					try {
						EconomyCore.buyRegion(regionToBuy, price, player, block);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		}
		
		if(playerModifyingOriginalRegion.containsKey(player)){
			if(playerModifyingOriginalRegion.get(player)){
				
				
				if(player.getItemInHand().getTypeId() == RegiosFileManager.selectionToolID){
				
				if(event.getAction() == Action.LEFT_CLICK_BLOCK){
					
						player.sendMessage(ChatColor.GREEN + "First Point Modified!");
					
					regionSetFirstPoint.put(player, true);
					regionFirstPointLocation.put(player, event.getClickedBlock().getLocation());
				}
			
				if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
					
					player.sendMessage(ChatColor.GREEN + "Second Point Modified!");
					
					regionSetSecondPoint.put(player, true);
					regionSecondPointLocation.put(player, event.getClickedBlock().getLocation());
				}
				} else {
					player.sendMessage(ChatColor.RED + "You must be holding the selection tool to set points : " + ChatColor.AQUA + Material.getMaterial(RegiosFileManager.selectionToolID));
					event.setCancelled(true);
				}
				
			}
		}
		
		if(settingRegion.containsKey(player)){//SETTING CHECKS
			if(settingRegion.get(player)){		
				
				
				if(player.getItemInHand().getTypeId() == RegiosFileManager.selectionToolID){
			
				
				if(event.getAction() == Action.LEFT_CLICK_BLOCK){
					
					if(!regionFirstPointLocation.containsKey(player)){
						player.sendMessage(ChatColor.GREEN + "First Point Set!");
					} else {
						regionFirstPointLocation.remove(player);
						player.sendMessage(ChatColor.GREEN + "First Point Modified!");
					}
					
					regionSetFirstPoint.put(player, true);
					regionFirstPointLocation.put(player, event.getClickedBlock().getLocation());
				}
			
				if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
					if(!regionSecondPointLocation.containsKey(player)){
						player.sendMessage(ChatColor.GREEN + "Second Point Set!");
					} else {
						regionSecondPointLocation.remove(player);
						player.sendMessage(ChatColor.GREEN + "Second Point Modified!");
					}
					
					regionSetSecondPoint.put(player, true);
					regionSecondPointLocation.put(player, event.getClickedBlock().getLocation());
				}
				} else {
					player.sendMessage(ChatColor.RED + "You must be holding the selection tool to set points : " + ChatColor.AQUA + Material.getMaterial(RegiosFileManager.selectionToolID));
					event.setCancelled(true);
				}
				
				
		
			}
		} //END OF SETTING CHECKS
		
		
	}//END OF INTERACT LISTENER
	
	public static void initValues(Player player){
		
		playerRegionIndex.put(player, 1);
		oldSubIndex.put(player, 1);
		
		
		
	}
	
	
	
	public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event){
		Player player = event.getPlayer();
		Block clicked = event.getBlockClicked();
		Location location = clicked.getLocation();
		World w = location.getWorld();
		
		int index = RegiosWeatherListener.getIndex(w);
		if(event.getBucket() == Material.LAVA_BUCKET){
			if(!player.isOp() && !(Regios.hasPermissions && Regios.permissionHandler.has(player, "regios.bypass-protection"))){
				if(!ConfigurationSettings.lavaEnabled[index - 1]){
					event.setCancelled(true);
					player.sendMessage(ChatColor.RED + "[Regios] Lava is banned on this world!");
					return;
				}
			}
		}
		
		if(event.getBucket() == Material.WATER_BUCKET){
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
		
		
		
		
		playerLocation.put(player, location);		
		playerInsideRegion.put(player, false);
		
		RegiosBlockListener.playerCanBuild.put(player, false);
		
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
			if( (((playerLocation.get(player).getY() <= 130) && (playerLocation.get(player).getY() >= RegiosFileManager.regiony2[i - 1] - 1)) || ((playerLocation.get(player).getY() >= RegiosFileManager.regiony1[i - 1] - 1) && (playerLocation.get(player).getY() <= 130))) && (((playerLocation.get(player).getZ() <= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() >= RegiosFileManager.regionz2[i - 1])) || ((playerLocation.get(player).getZ() >= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() <= RegiosFileManager.regionz2[i - 1])))  &&  (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1]))) && (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1])))   ){
					playerInsideRegion.put(player, true);
					playerRegionIndex.put(player, i);

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
					RegiosBlockListener.playerCanBuild.put(player, true);
					//player.sendMessage("you have regio.bypass");
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.override")))){
					RegiosBlockListener.playerCanBuild.put(player, true);
					//player.sendMessage("you have regio.override");
				}
				
				if(RegiosFileManager.formatPlayer(player).equalsIgnoreCase(RegiosFileManager.regionOwner[playerRegionIndex.get(player) - 1])){
					RegiosBlockListener.playerCanBuild.put(player, true);
					//player.sendMessage("you own the region");
				}
				
				if(player.isOp()){
					RegiosBlockListener.playerCanBuild.put(player, true);
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
						RegiosBlockListener.playerCanBuild.put(player, true);
						//player.sendMessage("you are an exception");
					}
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios." + player.getWorld().getName() + ".bypass")))){
					RegiosBlockListener.playerCanBuild.put(player, true);
				}
				
				
				if(!RegiosBlockListener.playerCanBuild.get(player)){
					
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
						//Block b = event.getBlockPlaced();
						//b.setTypeId(0);
						event.setCancelled(true);
						if(event.getBucket() == Material.WATER_BUCKET){
							//player.getInventory().remove(new ItemStack(326, 1));
							player.setItemInHand(null);
							player.setItemInHand(new ItemStack(Material.BUCKET, 1));
							//player.setItemInHand(new ItemStack(326, 1));
						} else if(event.getBucket() == Material.LAVA_BUCKET){
							player.setItemInHand(null);
							player.setItemInHand(new ItemStack(Material.BUCKET, 1));
							//player.getInventory().remove(new ItemStack(327, 1));
							//player.setItemInHand(new ItemStack(327, 1));
						}
						return;
					
				}
			}
		}
		
		
		
	}
	
	
	
public void onPlayerMove(PlayerMoveEvent event){
	//event.getPlayer().sendMessage("�0 black");
	if(RegiosFileManager.regionCount > 0){
	    
		Player player = event.getPlayer();
		Location location = event.getPlayer().getLocation();
		
		String pwn = location.getWorld().getName();
		
		usePlayerIndex.put(player, false);
		regionHashCode.remove(player);
		regionHashCode2.remove(player);
		
		int i;
		playerHealth.put(player, player.getHealth());

		modifyVelocity.put(player, false);
		movingPlayer.put(player, player);
		
		if(!playerTimes.containsKey(player)){
			playerTimes.put(player, System.currentTimeMillis());
			playerTimesDiff.put(player, 0);
		} else {
			playerTimesDiff.put(player, (int) Math.ceil(System.currentTimeMillis() - playerTimes.get(player)));
			
			playerTimes.put(player, System.currentTimeMillis());
		}
		
		if(!playerTimes2.containsKey(player)){
			playerTimes2.put(player, System.currentTimeMillis());
			playerTimesDiff2.put(player, 0);
		} else {
			playerTimesDiff2.put(player, (int) Math.ceil(System.currentTimeMillis() - playerTimes2.get(player)));
			
			playerTimes2.put(player, System.currentTimeMillis());
		}
		
		if(!playerTimes3.containsKey(player)){
			playerTimes3.put(player, System.currentTimeMillis());
			playerTimesDiff3.put(player, 0);
		} else {
			playerTimesDiff3.put(player, (int) Math.ceil(System.currentTimeMillis() - playerTimes3.get(player)));
			
			playerTimes3.put(player, System.currentTimeMillis());
		}
		
		if(playerSeconds.containsKey(player)){
			playerSeconds.put(player, playerSeconds.get(player) + Math.round( (playerTimesDiff.get(player))));
			//player.sendMessage(ChatColor.GREEN + "playerSeconds" + ChatColor.RED + playerSeconds.get(player));
		} else {
			playerSeconds.put(player, Math.round(playerTimesDiff.get(player)));
		}
		
		if(playerSeconds2.containsKey(player)){
			playerSeconds2.put(player, playerSeconds2.get(player) + Math.round( (playerTimesDiff2.get(player))));
			//player.sendMessage(ChatColor.GREEN + "playerSeconds" + ChatColor.RED + playerSeconds.get(player));
		} else {
			playerSeconds2.put(player, Math.round(playerTimesDiff2.get(player)));
		}
		
		if(playerSeconds3.containsKey(player)){
			playerSeconds3.put(player, playerSeconds3.get(player) + Math.round( (playerTimesDiff3.get(player))));
			//player.sendMessage(ChatColor.GREEN + "playerSeconds3 = " + ChatColor.RED + playerSeconds3.get(player));
		} else {
			playerSeconds3.put(player, Math.round(playerTimesDiff3.get(player)));
		}
		
		if(!playerRegionIndex.containsKey(player)){
			playerRegionIndex.put(player, 0);
		}
		if(!subRegionIndex1.containsKey(player)){
			subRegionIndex1.put(player, 1);
		}
		
		
		playerLocation.put(player, location);
		
		if(!playerWelcomeMsgSent.containsKey(player)){
			playerWelcomeMsgSent.put(player, false);
		}
		if(!playerLeaveMsgSent.containsKey(player)){
			playerLeaveMsgSent.put(player, false);
		}
		if(!playerWelcomeMsgSentSubRegion1.containsKey(player)){
			playerWelcomeMsgSentSubRegion1.put(player, false);
		}
		if(!playerLeaveMsgSentSubRegion1.containsKey(player)){
			playerLeaveMsgSentSubRegion1.put(player, false);
		}
		
		playerInsideRegion.put(player, false);
		prohibitMsgSent.put(player, true);
		playerCanEnter.put(player, false);
		
		
		
		for(i = 1; i <= RegiosFileManager.regionCount; i++){
			//REGION CHECKS
			if( (((playerLocation.get(player).getY() <= RegiosFileManager.regiony1[i - 1] + 2) && (playerLocation.get(player).getY() >= RegiosFileManager.regiony2[i - 1] - 1)) || ((playerLocation.get(player).getY() >= RegiosFileManager.regiony1[i - 1] - 1) && (playerLocation.get(player).getY() <= RegiosFileManager.regiony2[i - 1] + 2))) && (((playerLocation.get(player).getZ() <= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() >= RegiosFileManager.regionz2[i - 1])) || ((playerLocation.get(player).getZ() >= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() <= RegiosFileManager.regionz2[i - 1])))  &&  (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1]))) && (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1])))   ){
				if(pwn.equalsIgnoreCase(RegiosFileManager.regionWorldName[i - 1])){
					playerInsideRegion.put(player, true);
					regionEntryLocation.put(player, player.getLocation());
					playerRegionIndex.put(player, i);
					
					if(!regionHashCode.containsKey(player)){
						regionHashCode.put(player, i);
					}
					if(!regionHashCode2.containsKey(player) && regionHashCode.containsKey(player)){
						regionHashCode2.put(player, i);
					}
					//player.sendMessage("Region Index = " + i);
				}			

			}
			
			
			if(playerInsideRegion.get(player)){
				playerSeconds.put(player, 0);
				for(int e = 1; e <= RegiosFileManager.exceptionCount[playerRegionIndex.get(player) - 1]; e++){
					if(RegiosFileManager.regionExceptions[playerRegionIndex.get(player) - 1][e - 1].equalsIgnoreCase(RegiosFileManager.formatPlayer(player))){
						//player.sendMessage(ChatColor.GREEN + "you're an exception!");
						playerCanEnter.put(player, true);
					}
				}
			}
			
			

			//REGION CHECKS
			
			
			
			
			
			
		}//END OF FOR LOOP!
		
		

	
	if(!RegiosFileManager.regionPreventEntry[playerRegionIndex.get(player) - 1] || player.isOp() || (Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.override"))) || player.getDisplayName().equalsIgnoreCase(RegiosFileManager.regionOwner[playerRegionIndex.get(player) - 1]) ){
		playerCanEnter.put(player, true);
		playerTeled.put(player, false);
	}
	
	if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.bypass")))){
		playerCanEnter.put(player, true);
	}
	
	if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios." + player.getWorld().getName() + ".bypass")))){
		playerCanEnter.put(player, true);
	}

	
	Location loc = player.getLocation();
	
	
	if(playerInsideRegion.get(player)){
		playerSeconds.put(player, 0);
	}
	
	if(!playerCanEnter.get(player) && !playerTeled.get(player) && playerInsideRegion.get(player)){
		
				player.sendMessage(ChatColor.RED + "You do not have the required permissions to enter this region!");
				repositionPlayer(player, loc, event);

	} 
	
	//playerHealth.put(player, player.getHealth());
	//player.setHealth(playerHealth.get(player) + 1);
	//player.sendMessage("SECONDS = " + playerSeconds3.get(player));//1000 = 1 second
	


	
	/*else if(playerCanEnter.get(player)){
		if(Regios.hasPermissions && (Regios.permissionHandler.has(player, RegiosFileManager.regionBuildBlanketEntryNodes[RegiosFileManager.regionBuildBlanket[playerRegionIndex.get(player) - 1]]))){
			playerCanEnter.put(player, true);
		} else if(Regios.hasPermissions) {
			player.sendMessage(ChatColor.RED + "You do not have the required permissions to enter this region!");
			playerCanEnter.put(player, false);
			repositionPlayer(player.getLocation(), player);
			return;
		}
	}*/

	
	if(!playerInsideRegion.get(player)){
		
		playerTeled.put(player, false);
		
	}
	
	if(RegiosFileManager.movementFactor[playerRegionIndex.get(player) - 1] != 1){
		modifyVelocity.put(player, true);
	}

	playerInsideSubRegion1.put(player, false);
	
	if(playerCanEnter.get(player)){	
		
		
		if(playerInsideRegion.get(player)){
			
			playerCanEnter.put(player, false);
			
			
			//SUBREGION1
			for(i = 1; i <= RegiosFileManager.regionCount; i++){
				if( (((playerLocation.get(player).getY() <= RegiosFileManager.regiony1[i - 1] + 2) && (playerLocation.get(player).getY() >= RegiosFileManager.regiony2[i - 1] - 1)) || ((playerLocation.get(player).getY() >= RegiosFileManager.regiony1[i - 1] - 1) && (playerLocation.get(player).getY() <= RegiosFileManager.regiony2[i - 1] + 2))) && (((playerLocation.get(player).getZ() <= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() >= RegiosFileManager.regionz2[i - 1])) || ((playerLocation.get(player).getZ() >= RegiosFileManager.regionz1[i - 1]) && (playerLocation.get(player).getZ() <= RegiosFileManager.regionz2[i - 1])))  &&  (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1]))) && (((playerLocation.get(player).getX() <= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() >= RegiosFileManager.regionx2[i - 1])) || ((playerLocation.get(player).getX() >= RegiosFileManager.regionx1[i - 1]) && (playerLocation.get(player).getX() <= RegiosFileManager.regionx2[i - 1])))   ){
							if(i != playerRegionIndex.get(player)){
								if(pwn.equalsIgnoreCase(RegiosFileManager.regionWorldName[i - 1])){
									playerInsideSubRegion1.put(player, true);
									subRegionIndex1.put(player, i);
									//oldSubIndex.put(player, playerRegionIndex.get(player));
									playerCanEnter.put(player, false);
								}
							}				

				}
				}
			
			/*player.sendMessage(ChatColor.GREEN + "playerRegionIndex = " + playerRegionIndex.get(player));
			player.sendMessage(ChatColor.RED + "subRegionIndex = " + subRegionIndex1.get(player));
			player.sendMessage(ChatColor.GREEN + "oldSubIndex = " + oldSubIndex.get(player));*/
			

				for(int e = 1; e <= RegiosFileManager.exceptionCount[subRegionIndex1.get(player) - 1]; e++){
					if(RegiosFileManager.regionExceptions[subRegionIndex1.get(player) - 1][e - 1].equalsIgnoreCase(RegiosFileManager.formatPlayer(player))){
						//player.sendMessage(ChatColor.GREEN + "you're an exception baby!");
						playerSeconds.put(player, 0);
						playerCanEnter.put(player, true);
					}
				}
				
				if(!RegiosFileManager.regionPreventEntry[subRegionIndex1.get(player) - 1] || player.isOp() || (Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.override"))) || RegiosFileManager.formatPlayer(player).equalsIgnoreCase(RegiosFileManager.regionOwner[subRegionIndex1.get(player) - 1]) ){
					playerCanEnter.put(player, true);
					playerTeled.put(player, false);
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.bypass")))){
					playerCanEnter.put(player, true);
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios." + player.getWorld().getName() + ".bypass")))){
					playerCanEnter.put(player, true);
				}
				
				if(!playerCanEnter.get(player) && !playerTeled.get(player) && playerInsideSubRegion1.get(player)){
					
					player.sendMessage(ChatColor.RED + "You do not have the required permissions to enter this region!");
					repositionPlayerSub(player, loc, event);

				} 
			
			//SUBREGION1
				
			if(playerInsideSubRegion1.get(player)){
				
				/*if(regionHashCode.get(player) < regionHashCode2.get(player)){
					
				}*/
				
				if(    ( (RegiosFileManager.regionx1[playerRegionIndex.get(player) - 1] < RegiosFileManager.regionx1[subRegionIndex1.get(player) - 1] &&
						RegiosFileManager.regionx1[playerRegionIndex.get(player) - 1] > RegiosFileManager.regionx2[subRegionIndex1.get(player) - 1]) || 
						RegiosFileManager.regionx1[playerRegionIndex.get(player) - 1] > RegiosFileManager.regionx1[subRegionIndex1.get(player) - 1] &&
						RegiosFileManager.regionx1[playerRegionIndex.get(player) - 1] < RegiosFileManager.regionx2[subRegionIndex1.get(player) - 1])  &&
						( (RegiosFileManager.regionz1[playerRegionIndex.get(player) - 1] < RegiosFileManager.regionz1[subRegionIndex1.get(player) - 1] &&
								RegiosFileManager.regionz1[playerRegionIndex.get(player) - 1] > RegiosFileManager.regionz2[subRegionIndex1.get(player) - 1]) || 
								RegiosFileManager.regionz1[playerRegionIndex.get(player) - 1] > RegiosFileManager.regionz1[subRegionIndex1.get(player) - 1] &&
								RegiosFileManager.regionz1[playerRegionIndex.get(player) - 1] < RegiosFileManager.regionz2[subRegionIndex1.get(player) - 1])){
					usePlayerIndex.put(player, true);
					oldSubIndex.put(player, playerRegionIndex.get(player));
				} else {
					usePlayerIndex.put(player, false);
					oldSubIndex.put(player, subRegionIndex1.get(player));
				}
				
			}
			
			
			//MAIn REGION
			if(RegiosFileManager.regionHealthEnabled[playerRegionIndex.get(player) - 1]){
				unlimitedHealth.put(player, false);
			} else {
				unlimitedHealth.put(player, true);
			}
			if(!playerWelcomeMsgSent.get(player)){
				if(RegiosFileManager.regionWelcome[playerRegionIndex.get(player) - 1]){
					player.sendMessage(ChatColor.GREEN + RegiosFileManager.regionWelcomeMsg[playerRegionIndex.get(player) - 1]);
				}
				playerWelcomeMsgSent.put(player, true);
			}
			playerLeaveMsgSent.put(player, false);
			//MAIn REGION
			
			
			if(playerInsideSubRegion1.get(player)){
				playerSeconds2.put(player, 0);
				//player.sendMessage("You are in a sub regiion");
				if(!playerWelcomeMsgSentSubRegion1.get(player)){
					//if(    ( (RegiosFileManager.regionx1[playerRegionIndex.get(player)] < RegiosFileManager.regionx1[subRegionIndex1.get(player)] || RegiosFileManager.regionx2[playerRegionIndex.get(player)] < RegiosFileManager.regionx2[subRegionIndex1.get(player)] )&& RegiosFileManager.regionz1[playerRegionIndex.get(player)] < RegiosFileManager.regionz2[subRegionIndex1.get(player)])    ){
					//	
					//}
					
					if(!usePlayerIndex.get(player)){
						if(RegiosFileManager.regionWelcome[subRegionIndex1.get(player) - 1]){//was playerRegionIndex
							player.sendMessage(ChatColor.GREEN + RegiosFileManager.regionWelcomeMsg[subRegionIndex1.get(player) - 1]);
						}
					playerWelcomeMsgSentSubRegion1.put(player, true);
					
					} else {
						if(RegiosFileManager.regionWelcome[playerRegionIndex.get(player) - 1]){//was playerRegionIndex
							player.sendMessage(ChatColor.GREEN + RegiosFileManager.regionWelcomeMsg[playerRegionIndex.get(player) - 1]);
						}
					playerWelcomeMsgSentSubRegion1.put(player, true);
					
					}
					
			}
				playerLeaveMsgSentSubRegion1.put(player, false);
			}
			
			if(!playerInsideSubRegion1.get(player)){
				//player.sendMessage("You are not in a sub regiion");
				if(RegiosFileManager.regionHealthEnabled[oldSubIndex.get(player) - 1]){
					unlimitedHealth.put(player, false);
				} else {
					unlimitedHealth.put(player, true);
				}
				if(!playerLeaveMsgSentSubRegion1.get(player)){
					
					
					//if(usePlayerIndex.get(player)){
						if(RegiosFileManager.regionLeave[oldSubIndex.get(player) - 1]){//was playerRegionIndex
							player.sendMessage(ChatColor.GREEN + RegiosFileManager.regionLeaveMsg[oldSubIndex.get(player) - 1]);
						}
					playerLeaveMsgSentSubRegion1.put(player, true);
					
					//} else {
					//	if(RegiosFileManager.regionLeave[playerRegionIndex.get(player) - 1]){//was playerRegionIndex
					//		player.sendMessage(ChatColor.GREEN + RegiosFileManager.regionLeaveMsg[playerRegionIndex.get(player) - 1]);
					//	}
					//playerLeaveMsgSentSubRegion1.put(player, true);
					
					//}
					
					
					//if(RegiosFileManager.regionLeave[subRegionIndex1.get(player) - 1]){
					//	player.sendMessage(ChatColor.GREEN + RegiosFileManager.regionLeaveMsg[subRegionIndex1.get(player) - 1]);
					//}
					//playerLeaveMsgSentSubRegion1.put(player, true);
				}
				playerWelcomeMsgSentSubRegion1.put(player, false);
			}
			
			
			
		}

		
		
		
		if(!playerInsideRegion.get(player)){
			unlimitedHealth.put(player, false);
			playerTeled.put(player, false);
			
			if(!playerLeaveMsgSent.get(player)){
				if(RegiosFileManager.regionLeave[playerRegionIndex.get(player) - 1]){
					player.sendMessage(ChatColor.GREEN + RegiosFileManager.regionLeaveMsg[playerRegionIndex.get(player) - 1]);
				}
				playerLeaveMsgSent.put(player, true);
			}
			playerWelcomeMsgSent.put(player, false);
			
			//inside region
			
			
			if(playerInsideSubRegion1.get(player)){
			unlimitedHealth.put(player, false);
			playerTeled.put(player, false);
			
			/*if(!playerLeaveMsgSentSubRegion1.get(player)){
				if(subRegionIndex1.get(player) < playerRegionIndex.get(player)){
					if(RegiosFileManager.regionLeave[playerRegionIndex.get(player) - 1]){
						player.sendMessage(ChatColor.RED + RegiosFileManager.regionLeaveMsg[playerRegionIndex.get(player) - 1]);
					}
				} else {
					if(RegiosFileManager.regionLeave[subRegionIndex1.get(player) - 1]){
						player.sendMessage(ChatColor.RED + RegiosFileManager.regionLeaveMsg[subRegionIndex1.get(player) - 1]);
					}
				}
				playerLeaveMsgSentSubRegion1.put(player, true);
			}*/
			//playerWelcomeMsgSentSubRegion1.put(player, false);
			}
			
			
			
			
			if(!playerInsideSubRegion1.get(player)){
				unlimitedHealth.put(player, false);
				playerTeled.put(player, false);
				
				if(!playerLeaveMsgSent.get(player)){
					if(subRegionIndex1.get(player) < playerRegionIndex.get(player)){
						if(RegiosFileManager.regionLeave[playerRegionIndex.get(player) - 1]){
							player.sendMessage(ChatColor.RED + RegiosFileManager.regionLeaveMsg[playerRegionIndex.get(player) - 1]);
						}
					} else {
						if(RegiosFileManager.regionLeave[subRegionIndex1.get(player) - 1]){
							player.sendMessage(ChatColor.RED + RegiosFileManager.regionLeaveMsg[subRegionIndex1.get(player) - 1]);
						}
					}
					playerLeaveMsgSent.put(player, true);
				}
				playerWelcomeMsgSent.put(player, false);
				}
			
			
			//Inside region
			
			
			
		}
		if(modifyVelocity.get(player) && RegiosFileManager.regionCount > 0 && playerInsideRegion.get(player)){
			Vector v = player.getVelocity().multiply(RegiosFileManager.movementFactor[playerRegionIndex.get(player) - 1]).setY(0);
			player.setVelocity(v);
		}
		
		
		//player.sendMessage(ChatColor.GREEN + "Current index = " + playerRegionIndex.get(player));
		//player.sendMessage(ChatColor.DARK_GREEN + "Sub index = " + subRegionIndex1.get(player));
	//END OF LISTENER	
	}
	
	if( (playerSeconds.get(player) >= 3000) && !playerInsideRegion.get(player)){
		prevLoc.put(player, player.getLocation());
		playerInsideRegion.put(player, false);
		playerSeconds.put(player, 0);
		//player.sendMessage("2 secs passed!");
	}
	
	if( (playerSeconds2.get(player) >= 3000) && !playerInsideSubRegion1.get(player)){
		prevLocSub.put(player, player.getLocation());
		playerInsideSubRegion1.put(player, false);
		playerSeconds2.put(player, 0);
		//player.sendMessage("Sub incremented");
	}
	
	/*if(playerInsideRegion.get(player)){
		if(RegiosFileManager.healthgenFactor[playerRegionIndex.get(player) - 1] != 0){
			
			//player.sendMessage(ChatColor.GREEN + "Region health setting is not 0!");
			
			//player.sendMessage(ChatColor.GREEN + "Seconds elapsed = " + playerSeconds3.get(player));
			//player.sendMessage(ChatColor.GREEN + "healthgenFactor = " + RegiosFileManager.healthgenFactor[playerRegionIndex.get(player) - 1]);
			//player.sendMessage(ChatColor.BLUE + "healthgenFactor = " + RegiosFileManager.healthgenFactor[playerRegionIndex.get(player) - 1] * -1);
			
			if(RegiosFileManager.healthgenFactor[playerRegionIndex.get(player) - 1] < 0){
				//player.sendMessage(ChatColor.GREEN + "Region health setting is less than 0!");
				//player.sendMessage(ChatColor.RED + "" + playerIncrement.get(player));
				if(playerSeconds3.get(player) >= ((RegiosFileManager.healthgenFactor[playerRegionIndex.get(player) - 1] * -1) * 1000)){
					if(Regios.hasPermissions && ((Regios.permissionHandler.has(player, "regios.override") || Regios.permissionHandler.has(player, "regios.bypass"))) || player.isOp()){
						playerSeconds3.put(player, 0);
						//player.sendMessage(ChatColor.BLACK + "Op override instantiated!");
					} else {
						playerSeconds3.put(player, 0);
						//player.sendMessage("Player seconds reached " + RegiosFileManager.healthgenFactor[playerRegionIndex.get(player) - 1] * -1);
						//player.sendMessage(ChatColor.RED + "Player health de-creased");
						if(player.getHealth() > 0){
							player.setHealth(playerHealth.get(player) - 1);
						}
						//player.sendMessage(ChatColor.YELLOW + "Player health = " + player.getHealth());
					}
				}
			} else if(RegiosFileManager.healthgenFactor[playerRegionIndex.get(player) - 1] > 0){
				//player.sendMessage(ChatColor.GREEN + "Region health setting is greater than 0!");
				//player.sendMessage(ChatColor.RED + "" + playerIncrement.get(player));
				if(playerSeconds3.get(player) >= ((RegiosFileManager.healthgenFactor[playerRegionIndex.get(player) - 1]) * 1000)){
					playerSeconds3.put(player, 0);
					//player.sendMessage("Player seconds reached " + RegiosFileManager.healthgenFactor[playerRegionIndex.get(player) - 1] * -1);
					//player.sendMessage(ChatColor.RED + "Player health de-creased");
					if(player.getHealth() < 20){
						player.setHealth(playerHealth.get(player) + 1);
					}
					//player.sendMessage(ChatColor.YELLOW + "Player health = " + player.getHealth());
				}
			}
			
			
		}
	}*/
	}	
}
	
	public void repositionPlayer(Player player, Location loc, PlayerMoveEvent event){
		prevLoc.get(player).setWorld(player.getWorld());
		player.teleport(prevLoc.get(player));
		prevLoc.put(player, prevLoc.get(player));
		playerTeled.put(player, false);
		player.setFallDistance(0);
		event.setCancelled(true);
	}
	
	public void repositionPlayerSub(Player player, Location loc, PlayerMoveEvent event){
		prevLoc.get(player).setWorld(player.getWorld());
		player.teleport(prevLocSub.get(player));
		prevLocSub.put(player, prevLocSub.get(player));
		playerTeled.put(player, false);
		player.setFallDistance(0);
		event.setCancelled(true);
	}


	public void onPlayerQuit(PlayerQuitEvent event){
		RegiosScheduler.onlinePlayers.remove(event.getPlayer());
	}
	
	public static void reloadPlugin(Player player){
		RegiosScheduler.onlinePlayers.add(player);
		RegiosScheduler.playerCounter.put(player, 0);
		
		prevLoc.put(player, player.getLocation());
		prevLocSub.put(player, player.getLocation());
		playerIncrement.put(player, 0);
		playerRegionIndex.put(player, 1);
		playerInsideRegion.put(player, false);
		oldSubIndex.put(player, 1);
		playerSeconds.put(player, 0);
		playerSeconds2.put(player, 0);
		playerSeconds3.put(player, 0);
		playerLeaveMsgSent.put(player, true);
		playerLeaveMsgSentSubRegion1.put(player, true);
		playerTeled.put(player, false);
		playerTeled2.put(player, false);
	}
	
	public void onPlayerTeleport(PlayerTeleportEvent event){
		
		/*Player player = event.getPlayer();
		//player.sendMessage(ChatColor.AQUA + "teleport triggered");
		prevLoc.put(player, player.getLocation());
		prevLocSub.put(player, player.getLocation());
		playerIncrement.put(player, 0);
		playerRegionIndex.put(player, 1);
		playerInsideRegion.put(player, false);
		oldSubIndex.put(player, 1);
		playerSeconds.put(player, 0);
		playerSeconds2.put(player, 0);
		playerSeconds3.put(player, 0);
		//playerLeaveMsgSent.put(player, true);
		//playerLeaveMsgSentSubRegion1.put(player, true);
		playerTeled.put(player, false);
		playerTeled2.put(player, false);
		//playerLeaveMsgSent.put(player, false);
		playerWelcomeMsgSent.put(player, false);*/
	}
	
	public void onPlayerJoin(PlayerJoinEvent event){
	
	/*File configFile = new File("plugins/Permissions/" + event.getPlayer().getWorld().getName() + ".yml");
	event.getPlayer().sendMessage("Permissions location = " + configFile);
	Configuration config = new Configuration(configFile);
	config.load();
	config.setProperty("groups.Moderator", "hello boy!");
	config.save();*/
	
	RegiosScheduler.onlinePlayers.add(event.getPlayer());
	RegiosScheduler.playerCounter.put(event.getPlayer(), 0);
	
	prevLoc.put(event.getPlayer(), event.getPlayer().getLocation());
	prevLocSub.put(event.getPlayer(), event.getPlayer().getLocation());
	playerIncrement.put(event.getPlayer(), 0);
	playerRegionIndex.put(event.getPlayer(), 1);
	playerInsideRegion.put(event.getPlayer(), false);
	oldSubIndex.put(event.getPlayer(), 1);
	playerSeconds.put(event.getPlayer(), 0);
	playerSeconds2.put(event.getPlayer(), 0);
	playerSeconds3.put(event.getPlayer(), 0);
	playerLeaveMsgSent.put(event.getPlayer(), true);
	playerLeaveMsgSentSubRegion1.put(event.getPlayer(), true);
	playerTeled.put(event.getPlayer(), false);
	playerTeled2.put(event.getPlayer(), false);
	if(RegiosFileManager.check4Updates){
		if(event.getPlayer().isOp() || ((Regios.hasPermissions && (Regios.permissionHandler.has(event.getPlayer(), "regios.version"))))){
			if(!Regios.core.checkVersion(Regios.currentVersion, Regios.currentSubVersion, "Regios")){
				event.getPlayer().sendMessage(ChatColor.BLACK + "--------------------" + ChatColor.RED + "Regios Update" + ChatColor.BLACK + "---------------------");
				event.getPlayer().sendMessage(ChatColor.RED + "Running Version : " + ChatColor.AQUA + Regios.currentVersion + "_" + Regios.currentSubVersion + ChatColor.RED + " | Latest Version : " + ChatColor.AQUA + Regios.currentVersion + "_" + Regios.currentSubVersion);
				event.getPlayer().sendMessage(ChatColor.BLACK + "--------------------" + ChatColor.RED + "Regios Update" + ChatColor.BLACK + "---------------------");
			} else {
				event.getPlayer().sendMessage(ChatColor.GREEN + "Regio's is running the latest version.");
			}
		}
	}
}
	
	
}
