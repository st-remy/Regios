package me.Adamki11s.Regios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class RegiosScheduler implements Runnable {

	static Map<Integer, Integer> lspsCounter = new HashMap<Integer, Integer>();
	static Map<Player, Integer> playerCounter = new HashMap<Player, Integer>();
	
	public static Regios plugin;
	
	private int blockFlowCheck = 0;
	
	public RegiosScheduler(Regios instance) {
		plugin = instance;
	}
	
	public static ArrayList<Player> onlinePlayers = new ArrayList<Player>();
	
	private static int mobRemovalCount = 0;
	
	private static double regionXWidth(int index){
		double x1 = RegiosFileManager.regionx1[index - 1], x2 = RegiosFileManager.regionx2[index - 1];
		if(x1 > x2){
			return (x1 - x2) / 2;
		} else {
			return (x2 - x1) / 2;
		}
	}
	
	private static double regionZWidth(int index){
		double z1 = RegiosFileManager.regionz1[index - 1], z2 = RegiosFileManager.regionz2[index - 1];
		if(z1 > z2){
			return (z1 - z2) / 2;
		} else {
			return (z2 - z1) / 2;
		}
	}
	
	private static double regionYWidth(int index){
		double y1 = RegiosFileManager.regiony1[index - 1], y2 = RegiosFileManager.regiony2[index - 1];
		if(y1 > y2){
			return (y1 - y2) / 2;
		} else {
			return (y2 - y1) / 2;
		}
	}
	
	private static boolean x1Bigger(int index){
		double x1 = RegiosFileManager.regionx1[index - 1], x2 = RegiosFileManager.regionx2[index - 1];
		if(x1 > x2){
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean y1Bigger(int indey){
		double y1 = RegiosFileManager.regiony1[indey - 1], y2 = RegiosFileManager.regiony2[indey - 1];
		if(y1 > y2){
			return true;
		} else {
			return false;
		}
	}
	
	private static boolean z1Bigger(int indez){
		double z1 = RegiosFileManager.regionz1[indez - 1], z2 = RegiosFileManager.regionz2[indez - 1];
		if(z1 > z2){
			return true;
		} else {
			return false;
		}
	}
	
	//public static Thread fluidThread = new Thread();
	
	public static int concurrentCheck = -1;
	
	@Override
	public void run() {
		
		/*blockFlowCheck++;
		
		if(blockFlowCheck >= 5){
		
			//fluidThread = new FluidCheck(new FluidCheck());
			if(!RegiosBlockListener.blockFlowEvents.isEmpty()){
				(new Thread(new FluidCheck())).start();
				for(Location loc : RegiosBlockListener.blockFlowEvents){
					FluidCheck.loc = loc;
					concurrentCheck++;
					//fluidThread.start();
				}
				(new Thread(new FluidCheck())).stop();
			}
			blockFlowCheck = 0;
		}*/
		
		
		// TODO Auto-generated method stub
		
		/*mobRemovalCount++;
		
		if(mobRemovalCount == 5){
			for(int i = 1; i <= RegiosFileManager.regionCount; i++){
				if(!RegiosFileManager.regionCreaturesSpawn[i - 1]){
					for(Player player : Regios.server.getOnlinePlayers()){
						Player fakePlayer = player;
						if(x1Bigger(i)){ fakePlayer.getLocation().setX(RegiosFileManager.regionx2[i - 1] + regionXWidth(i)); } else { fakePlayer.getLocation().setX(RegiosFileManager.regionx1[i - 1] + regionXWidth(i)); }
						if(y1Bigger(i)){ fakePlayer.getLocation().setY(RegiosFileManager.regiony2[i - 1] + regionYWidth(i)); } else { fakePlayer.getLocation().setY(RegiosFileManager.regiony1[i - 1] + regionYWidth(i)); }
						if(z1Bigger(i)){ fakePlayer.getLocation().setZ(RegiosFileManager.regionz2[i - 1] + regionZWidth(i)); } else { fakePlayer.getLocation().setZ(RegiosFileManager.regionz1[i - 1] + regionZWidth(i)); }
						List<Entity> nearbyMobs = fakePlayer.getNearbyEntities(fakePlayer.getLocation().getX(), fakePlayer.getLocation().getY(), fakePlayer.getLocation().getZ());
					}
				}
			}
			mobRemovalCount = 0;
		}*/
		
		for(int i = 1; i <= RegiosFileManager.regionCount; i++){
			
					for(Player player : onlinePlayers){
						RegiosPlayerListener.playerHealth.put(player, player.getHealth());
						//Regios.server.broadcastMessage("" + player);
						if(RegiosPlayerListener.playerInsideRegion.containsKey(player) && RegiosPlayerListener.playerRegionIndex.containsKey(player)){
							
						if(RegiosFileManager.healthgenFactor[RegiosPlayerListener.playerRegionIndex.get(player) - 1] != 0){
						if(RegiosPlayerListener.playerInsideRegion.get(player)){
							
							
							if(RegiosFileManager.healthgenFactor[RegiosPlayerListener.playerRegionIndex.get(player) - 1] < 0){
								playerCounter.put(player, playerCounter.get(player) + 1);
								
					
								if(playerCounter.get(player) >= ((RegiosFileManager.healthgenFactor[RegiosPlayerListener.playerRegionIndex.get(player) - 1] * -1))){
									if(RegiosFileManager.isOwner(RegiosFileManager.regionNames[RegiosPlayerListener.playerRegionIndex.get(player) - 1], player)){
										playerCounter.put(player, 0);
									} else {
										playerCounter.put(player, 0);
										//player.sendMessage("Player seconds reached " + RegiosFileManager.healthgenFactor[playerRegionIndex.get(player) - 1] * -1);
										//player.sendMessage(ChatColor.RED + "Player health de-creased");
										if(player.getHealth() > 0){
											player.setHealth(RegiosPlayerListener.playerHealth.get(player) - 1);
										}
										//player.sendMessage(ChatColor.YELLOW + "Player health = " + player.getHealth());
									}
								}
							}
							
							if(RegiosFileManager.healthgenFactor[RegiosPlayerListener.playerRegionIndex.get(player) - 1] > 0){
								playerCounter.put(player, playerCounter.get(player) + 1);
								
								//player.sendMessage(ChatColor.GREEN + "Region health setting is greater than 0!");
								//player.sendMessage(ChatColor.RED + "" + playerIncrement.get(player));
								if(playerCounter.get(player) >= ((RegiosFileManager.healthgenFactor[RegiosPlayerListener.playerRegionIndex.get(player) - 1]))){
									playerCounter.put(player, 0);
									//player.sendMessage("Player seconds reached " + RegiosFileManager.healthgenFactor[playerRegionIndex.get(player) - 1] * -1);
									//player.sendMessage(ChatColor.RED + "Player health de-creased");
									if(player.getHealth() < 20){
										//player.sendMessage("Health added!");
										player.setHealth(RegiosPlayerListener.playerHealth.get(player) + 1);
									}
									//player.sendMessage(ChatColor.YELLOW + "Player health = " + player.getHealth());
								}
						}
						}
						}
						//player.sendMessage("Player counter = " + playerCounter.get(player));
					}
			}
			
			if(RegiosFileManager.lsps[i - 1] > 0){
				lspsCounter.put(i, lspsCounter.get(i) + 1);
				//Regios.server.broadcastMessage("lspsCounter = " + lspsCounter.get(i));
				if(lspsCounter.get(i) >= RegiosFileManager.lsps[i - 1]){
					//Strike down lightning in random location.
					strike(RegiosFileManager.regionx1[i - 1], RegiosFileManager.regionx2[i - 1], RegiosFileManager.regionz1[i - 1], RegiosFileManager.regionz2[i - 1], RegiosFileManager.regiony1[i - 1], RegiosFileManager.regiony2[i - 1], RegiosFileManager.regionWorldName[i - 1]);
					lspsCounter.put(i, 0);
				}
			}
		}
		
		
	}
	
	private static void strike(double x1, double x2, double z1, double z2, double y1, double y2, String world){
		double xdiff, zdiff, xStrike, yStrike, zStrike;
		boolean x1bigger = false, z1bigger = false;
		
		if(x1 > x2){ xdiff = x1 - x2; x1bigger = true; } else { xdiff = x2 - x1; x1bigger = false; }
		if(z1 > z2){ zdiff = z1 - z2; z1bigger = true; } else { zdiff = z2 - z1; z1bigger = false; }
		if(y1 > y2){ yStrike = y2; } else { yStrike = y1; }
		
		Random gen = new Random();
		int randXStrike = gen.nextInt((int) ((xdiff) + 1));
		int randZStrike = gen.nextInt((int) ((zdiff) + 1));
		
		if(x1bigger){
			xStrike = (x2 + randXStrike);
		} else {
			xStrike = (x1 + randXStrike);
		}		
		if(z1bigger){
			zStrike = (z2 + randZStrike);
		} else {
			zStrike = (z1 + randZStrike);
		}
		
		World wl = Regios.server.getWorld(world);
		
		Location STRIKE = new Location(wl, xStrike, yStrike, zStrike, 0, 0);
		
		wl.strikeLightning(STRIKE);
		

	}


}
