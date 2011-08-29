package me.Adamki11s.Regios;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import couk.Adamki11s.EconomyHandler.EconomyCore;
import couk.Adamki11s.WorldConfiguration.ConfigurationLoader;
import org.bukkit.command.ConsoleCommandSender;

public class RegiosCommandManager implements CommandExecutor {
	
	public static Map<Player, Integer> modificationIndex = new HashMap<Player, Integer>();

	public static Regios plugin;
	
	public RegiosCommandManager(Regios instance) {
		plugin = instance;
	}
        
        public static boolean doesBackupExist(String region){
            if(new File(RegiosBackups.mainDir + File.separator + region + ".MATRIX").exists()){
                return true;
            } else {
                return false;
            }
        }
        
        public static boolean warpIsValid(int index, String region){
		
		
		
		Location warp = RegiosFileManager.warpLocations[index];
		
		if(warp.getX() == 0 && warp.getY() == 0 && warp.getZ() == 0 && warp.getYaw() == 0 && warp.getPitch() == 0){
			
			return false;
		}
		
		return true;
	}
	
	public static boolean warpIsValid(int index, String region, Player player){
		
		
		
		Location warp = RegiosFileManager.warpLocations[index];
		
		if(warp.getX() == 0 && warp.getY() == 0 && warp.getZ() == 0 && warp.getYaw() == 0 && warp.getPitch() == 0){
			player.sendMessage(ChatColor.BLUE + "[Regios][Warp] " + ChatColor.RED + " No warp has been set for region " + ChatColor.YELLOW + region.toLowerCase());
			return false;
		}
		
		if(RegiosFileManager.regionPreventEntry[index]){
			for(int e = 1; e <= RegiosFileManager.exceptionCount[index]; e++){
				if(RegiosFileManager.regionExceptions[index][e - 1].equalsIgnoreCase(RegiosFileManager.formatPlayer(player)) || player.isOp() || RegiosFileManager.regionOwner[index].equalsIgnoreCase(RegiosFileManager.formatPlayer(player))){
					player.sendMessage(ChatColor.BLUE + "[Regios][Warp] " + ChatColor.GREEN + " Warp to region " + ChatColor.LIGHT_PURPLE + region.toLowerCase() + ChatColor.GREEN + " was successful!");
					return true;
				}
			}
			player.sendMessage(ChatColor.BLUE + "[Regios][Warp] " + ChatColor.RED + " Your are prevented from warping to region " + ChatColor.YELLOW + region.toLowerCase());
			return false;
		}
		
		player.sendMessage(ChatColor.BLUE + "[Regios][Warp] " + ChatColor.GREEN + " Warp to region " + ChatColor.LIGHT_PURPLE + region.toLowerCase() + ChatColor.GREEN + " was successful!");
		return true;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("regios")){
                        if(sender instanceof ConsoleCommandSender){
                           if(args.length == 1 && args[0].equalsIgnoreCase("start-gui")){
                               //GUIMain.main(null);
                              // RegiosFileManager.log.info("[Regios][GUI] Regios GUI started successfully!");
                               //return true;
                           } 
                        }
			if(sender instanceof Player){
				
				Player player = (Player)sender;
				
				//player.sendMessage(ChatColor.YELLOW + "[DEBUG] Regios command executed!");
				
				if(args.length == 1 && args[0].equalsIgnoreCase("heap")){
					player.sendMessage(ChatColor.GREEN + "Objects : " + ChatColor.BLUE + RegiosBlockListener.blockFlowEvents.size());
				}
				
				//RELOAD DB
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.reload-db") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 1 && args[0].equalsIgnoreCase("reload-database")){
						player.sendMessage(ChatColor.GREEN + "Reloading...");
						RegiosFileManager.loadDatabase();
						player.sendMessage(ChatColor.GREEN + "Database reloaded successfully!");
						return true;
					}
				}
				//RELOAD DB
				
				//EXCEPTIONS
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.exception") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					
					if(  args.length == 3 && (args[0].equalsIgnoreCase("addex"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								RegiosFileManager.pushAddToExceptions(args[1], player, args[2]);
								return true;
							} else if(  args.length == 3 && (args[0].equalsIgnoreCase("addex"))) {
								player.sendMessage(ChatColor.RED + "You can't modify someone elses region!");
								return true;
							}
					} else if(  args.length == 3 && (args[0].equalsIgnoreCase("addex"))) {
						player.sendMessage(ChatColor.RED + "That region does not exist!");
						return true;
					}
						
				} 
				}else if(  args.length == 3 && (args[0].equalsIgnoreCase("addex"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to set whitelists.");
						return true;
					}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.here") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 1 && args[0].equalsIgnoreCase("here")){
						return true;
					}
				}else if(args.length == 1 && args[0].equalsIgnoreCase("here")){
					player.sendMessage(ChatColor.RED + "You are not allowed to check your region.");
					return true;
				}
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.exception") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					
					if(args.length == 3 && (args[0].equalsIgnoreCase("remex"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								RegiosFileManager.pushRemoveFromExceptions(args[1], player, args[2]);
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "You can't modify someone elses region!");
								return true;
							}
					} else if(args.length == 3 && (args[0].equalsIgnoreCase("remex"))){
						player.sendMessage(ChatColor.RED + "That region does not exist!");
						return true;
					}
						
				} 
				}else if(args.length == 3 && (args[0].equalsIgnoreCase("remex"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to set whitelists.");
						return true;
					}
				
				//EXCEPTIONS
				
				//RELOAD CONFIG
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.reload-config") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 1 && args[0].equalsIgnoreCase("reload-config")){
						RegiosFileManager.reloadConfiguration(player);
						try {
							ConfigurationLoader.loadWorldConfiguration(Regios.server);
						} catch (IOException e) {
							e.printStackTrace();
							player.sendMessage(ChatColor.RED + "[Regios] Error whilst reloading configuration. Error outputted to console.");
							return true;
						}
						return true;
					}
				} else if(args.length == 1 && args[0].equalsIgnoreCase("reload-config")){
					player.sendMessage(ChatColor.RED + "You are not allowed to reload the configuration!");
					return true;
				}
				//RELOAD CONFIG
				
				//CANCEL
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.createdelete") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 1 && args[0].equalsIgnoreCase("cancel")){
						RegiosPlayerListener.settingRegion.put(player, false);
						RegiosPlayerListener.playerModifyingRegion.put(player, false);
						RegiosPlayerListener.playerModifyingOriginalRegion.put(player, false);
						sender.sendMessage(ChatColor.RED + "Region setting cancelled.");

						player.getInventory().removeItem(new ItemStack(RegiosFileManager.selectionToolID, 1));
						
						return true;
					}
				} else {
					if(args.length == 1 && args[0].equalsIgnoreCase("cancel")){
						player.sendMessage(ChatColor.RED + "You are not allowed to create Regions.");
						return true;
					}
				}
				//CANCEL
				
				
				//EXPANDING REGIONS PACKAGE
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.expand") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if( args.length == 2  && (args[0].equalsIgnoreCase("expandmax") )){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								RegiosFileManager.pushExpandRegionMAX(args[1], 0, player);
								return true;
							} else if( args.length == 2  && (args[0].equalsIgnoreCase("expandmax") )){
								player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
								return true;
							}
						} else if( args.length == 2  && (args[0].equalsIgnoreCase("expandmax") )){
							player.sendMessage(ChatColor.RED + "That region does not exist.");
							return true;
						}
					}
				} else if( args.length == 2  && (args[0].equalsIgnoreCase("expandmax") )){
						player.sendMessage(ChatColor.RED + "You are not allow to expand regions!");
						return true;
				}
				
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.setwarp") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if( args.length == 1  && (args[0].equalsIgnoreCase("setwarp") )){
						if(RegiosPlayerListener.playerInsideRegion.get(player)){
							if(RegiosFileManager.isOwner(RegiosFileManager.regionNames[RegiosPlayerListener.playerRegionIndex.get(player) - 1], player)){
								RegiosFileManager.pushWarpLocation(RegiosFileManager.regionNames[RegiosFileManager.regionIndex], player, player.getLocation());
								
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "You do not have permissions to change the warp for this region!");
								return true;
							}
						} else {
							player.sendMessage(ChatColor.RED + "You must be in a region to define a warp!");
							return true;
						}
					}
				} else if( args.length == 1  && (args[0].equalsIgnoreCase("setwarp") )){
					player.sendMessage(ChatColor.RED + "You are not allow to set warps for regions!");
					return true;
				}
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.warpto") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if( args.length == 2  && (args[0].equalsIgnoreCase("warpto") )){
						
						if(!RegiosFileManager.doesRegionExist(args[1], player)){
							player.sendMessage(ChatColor.RED + "The region specified does not exist!");
							return true;
						}

								if(warpIsValid((RegiosFileManager.regionIndex), args[1].toLowerCase(), player)){
									RegiosFileManager.doesRegionExist(args[1].toLowerCase(), player);
										player.teleport(RegiosFileManager.warpLocations[RegiosFileManager.regionIndex]);
										return true;
								}
								
						return true;
						
					}
				} else if( args.length == 1  && (args[0].equalsIgnoreCase("setwarp") )){
					player.sendMessage(ChatColor.RED + "You are not allow to set warps for regions!");
					return true;
				}
				
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.expand") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if( args.length == 3  && (args[0].equalsIgnoreCase("expandup") )){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								int value;
								try{
									value = Integer.parseInt(args[2]);
								} catch (NumberFormatException ex){
									player.sendMessage(ChatColor.RED + "The value to expand must be an integer!");
									return true;
								}
								RegiosFileManager.expandRegionUp(args[1], value, player);
								return true;
							
							} else if( args.length == 3  && (args[0].equalsIgnoreCase("expandup") )){
								player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
								return true;
							}
						} else if( args.length == 3  && (args[0].equalsIgnoreCase("expandup") )){
							player.sendMessage(ChatColor.RED + "That region does not exist.");
							return true;
						}
					}
				} else if( args.length == 3  && (args[0].equalsIgnoreCase("expandup") )){
						player.sendMessage(ChatColor.RED + "You are not allow to expand regions!");
						return true;
				}
				
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.expand") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if( args.length == 3  && (args[0].equalsIgnoreCase("expanddown") )){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								int value;
								try{
									value = Integer.parseInt(args[2]);
								} catch (NumberFormatException ex){
									player.sendMessage(ChatColor.RED + "The value to expand must be an integer!");
									return true;
								}
								RegiosFileManager.expandRegionDown(args[1], value, player);
								return true;
							
							} else if( args.length == 3  && (args[0].equalsIgnoreCase("expanddown") )){
								player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
								return true;
							}
						} else if( args.length == 3  && (args[0].equalsIgnoreCase("expanddown") )){
							player.sendMessage(ChatColor.RED + "That region does not exist.");
							return true;
						}
					}
				} else if( args.length == 3  && (args[0].equalsIgnoreCase("expanddown") )){
						player.sendMessage(ChatColor.RED + "You are not allow to expand regions!");
						return true;
				}
				//EXPANDING REGIONS PACKAGE
				
				//EXPANDING REGIONS PACKAGE
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.lsps") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if( args.length == 3  && (args[0].equalsIgnoreCase("lsps") )){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								int lsps = 0;
								try{
									lsps = Integer.parseInt(args[2]);
									RegiosFileManager.pushLSPS(args[1], lsps, player);
								} catch (NumberFormatException ex){
									player.sendMessage(ChatColor.RED + "The lsps setting must be an integer!");
									return true;
								}
								return true;
							} else if( args.length == 3  && (args[0].equalsIgnoreCase("lsps") )){
								player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
								return true;
							}
						} else if( args.length == 3  && (args[0].equalsIgnoreCase("lsps") )){
							player.sendMessage(ChatColor.RED + "That region does not exist.");
							return true;
						}
					}
				} else if( args.length == 3  && (args[0].equalsIgnoreCase("lsps") )){
						player.sendMessage(ChatColor.RED + "You are not allow to customise lightning for regions!");
						return true;
				}
				
				//MODIFY
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.modify") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if( args.length == 2 && (args[0].equalsIgnoreCase("modify")) && !args[1].equalsIgnoreCase("confirm")){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								modificationIndex.put(player, RegiosFileManager.regionIndex);
								//player.sendMessage("" + modificationIndex.get(player));
								RegiosPlayerListener.playerModifyingOriginalRegion.put(player, true);
								player.sendMessage(ChatColor.GREEN + "You are now modifying the points for region : " + ChatColor.LIGHT_PURPLE + args[1]);
								if(!player.getInventory().contains(RegiosFileManager.selectionToolID)){
									player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(RegiosFileManager.selectionToolID, 1));
									//player.getInventory().setItemInHand(new ItemStack(RegiosFileHandler.selectionToolID, 1));
								}
							    return true;
							} else if( args.length == 2 && (args[0].equalsIgnoreCase("modify"))) {
								player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
								return true;
							}
						} else if( args.length == 2 && (args[0].equalsIgnoreCase("modify"))){
							player.sendMessage(ChatColor.RED + "That region does not exist");
							return true;
						}
					}
					}  else  if( args.length == 2 && (args[0].equalsIgnoreCase("modify"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to modify region points.");
						return true;
					}
				
				
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.modify") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if( args.length == 2 && (args[0].equalsIgnoreCase("modify")) && args[1].equalsIgnoreCase("confirm") && !args[1].equalsIgnoreCase("cancel")){
							
							player.sendMessage(ChatColor.GREEN + "Modification for region : " + ChatColor.LIGHT_PURPLE + RegiosFileManager.regionNames[modificationIndex.get(player)] + ChatColor.GREEN + " confirmed");
							
							if(RegiosPlayerListener.regionFirstPointLocation.containsKey(player) && RegiosPlayerListener.regionSecondPointLocation.containsKey(player)){
							
								double x1 = RegiosPlayerListener.regionFirstPointLocation.get(player).getX(), x2 = RegiosPlayerListener.regionSecondPointLocation.get(player).getX();
								double y1 = RegiosPlayerListener.regionFirstPointLocation.get(player).getY(), y2 = RegiosPlayerListener.regionSecondPointLocation.get(player).getY();
								double z1 = RegiosPlayerListener.regionFirstPointLocation.get(player).getZ(), z2 = RegiosPlayerListener.regionSecondPointLocation.get(player).getZ();						
								String updateQuery = "UPDATE regions SET x1='" + x1 + "', x2='" + x2 + "', y1='" + y1 + "', y2='" + y2 + "', z1='" + z1 + "', z2='" + z2 + "' WHERE regionname='" + RegiosFileManager.regionNames[modificationIndex.get(player)] + "'";
								RegiosFileManager.dbManage.updateQuery(updateQuery);
								RegiosPlayerListener.playerModifyingOriginalRegion.put(player, false);
								
								sender.sendMessage(ChatColor.GREEN + "Region " + ChatColor.LIGHT_PURPLE + RegiosFileManager.regionNames[RegiosFileManager.regionIndex] + ChatColor.GREEN + " modified successfully!");
								RegiosFileManager.rereadDatabaseAddition(modificationIndex.get(player) + 1, player, RegiosFileManager.regionNames[modificationIndex.get(player)]);
						    return true;
							} else {
								sender.sendMessage(ChatColor.RED + "You must select two points before modifying a region.");
								return true;
							}
								

					}
					}  else if( args.length == 2 && (args[0].equalsIgnoreCase("modify"))) {
						player.sendMessage(ChatColor.RED + "You are not allowed to modify region points.");
						return true;
					}
				
				//MODIFY
				
				//DELETE
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.createdelete") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if( args.length == 2 && (args[0].equalsIgnoreCase("delete"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								RegiosFileManager.deleteRegion(args[1].toLowerCase(), player);
								return true;
							} else if( args.length == 2 && (args[0].equalsIgnoreCase("delete"))){
								player.sendMessage(ChatColor.RED + "You can't delete someone elses region!");
								return true;
							}
						} else if( args.length == 2 && (args[0].equalsIgnoreCase("delete"))){
							player.sendMessage(ChatColor.RED + "That region does not exist!");
							return true;
						}
					}
				} else if( args.length == 2 && (args[0].equalsIgnoreCase("delete"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to delete Regions.");
						return true;
				}
						
				//DELETE
				
				
				//CUSTOMISE WELCOME & Leave
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.customise-messages") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length >= 3 && (args[0].equalsIgnoreCase("setwelcome"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								//set welcome procedure
								String welcomemsg = "";
								int i;
								for(i = 3; i <= args.length; i++){
									welcomemsg += args[i - 1] + " ";
								}
								if(welcomemsg.length() >= 5){
									RegiosFileManager.pushWelcomeMsg(welcomemsg, player, args[1]);
									return true;
								} else {
									player.sendMessage(ChatColor.RED + "Your welcome message must be at least 5 characters long.");
									return true;
								}
								
							} else if(args.length >= 3 && (args[0].equalsIgnoreCase("setwelcome"))){
								player.sendMessage(ChatColor.RED + "You can't customise someone elses region!");
								return true;
							}
						} else if(args.length >= 3 && (args[0].equalsIgnoreCase("setwelcome"))){
							player.sendMessage(ChatColor.RED + "That region does not exist!");
							return true;
						}
					}
				} else {
					if(args.length >= 3 && (args[0].equalsIgnoreCase("setwelcome"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to customise region messages.");
						return true;
					}
				}
				
				
				
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.customise-messages") || Regios.permissionHandler.has(player, "region.*"))) || player.isOp()){
					if( args.length == 3 && (args[0].equalsIgnoreCase("showwelcome"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								
								if(args[2].equalsIgnoreCase("true")){
									RegiosFileManager.pushEnabledWelcome(true, player, args[1]);
									return true;
								} 
								
								if(args[2].equalsIgnoreCase("false")){
									RegiosFileManager.pushEnabledWelcome(false, player, args[1]);
									return true;
								} 
								

						} else if( args.length == 3 && (args[0].equalsIgnoreCase("showwelcome"))){
							player.sendMessage(ChatColor.RED + "That region does not exist!");
							return true;
						}
					}
				} 
				} else {
					if( args.length == 3 && (args[0].equalsIgnoreCase("showwelcome"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to customise region messages.");
						return true;
					}
				}
				
				
				
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.customise-messages") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 3 && (args[0].equalsIgnoreCase("showleave"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								
								if(args[2].equalsIgnoreCase("true")){
									RegiosFileManager.pushEnabledLeave(true, player, args[1]);
									return true;
								} 
								
								if(args[2].equalsIgnoreCase("false")){
									RegiosFileManager.pushEnabledLeave(false, player, args[1]);
									return true;
								} 
							} else if(args.length == 3 && (args[0].equalsIgnoreCase("showleave"))){
								player.sendMessage(ChatColor.RED + "You cannot customise someone elses region!");
								return true;
							}

						} else if(args.length == 3 && (args[0].equalsIgnoreCase("showleave"))){
							player.sendMessage(ChatColor.RED + "That region does not exist!");
							return true;
						}
					}
				} else if(args.length == 3 && (args[0].equalsIgnoreCase("showleave"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to customise region messages.");
						return true;
					}
				
				
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.pvp") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 3 && (args[0].equalsIgnoreCase("setpvp"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								
								if(args[2].equalsIgnoreCase("true")){
									RegiosFileManager.pushPVPSetting(true, player, args[1]);
									return true;
								} 
								
								if(args[2].equalsIgnoreCase("false")){
									RegiosFileManager.pushPVPSetting(false, player, args[1]);
									return true;
								} 
							} else if(args.length == 3 && (args[0].equalsIgnoreCase("setpvp"))){
								player.sendMessage(ChatColor.RED + "You cannot customise someone elses region!");
								return true;
							}

						} else if(args.length == 3 && (args[0].equalsIgnoreCase("setpvp"))){
							player.sendMessage(ChatColor.RED + "That region does not exist!");
							return true;
						}
					}
				} else if(args.length == 3 && (args[0].equalsIgnoreCase("setpvp"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to customise region PVP settings.");
						return true;
				}
				
				
				
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.customise-messages") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length >= 3 && (args[0].equalsIgnoreCase("setleave"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								//set welcome procedure
								String leavemsg = ChatColor.RED + "";
								int i;
								for(i = 3; i <= args.length; i++){
									leavemsg += args[i - 1] + " ";
								}
								if(leavemsg.length() >= 5){
									RegiosFileManager.pushLeaveMsg(leavemsg, player, args[1]);
									return true;
								} else {
									player.sendMessage(ChatColor.RED + "Your leaving message must be at least 5 characters long.");
									return true;
								}
								
							} else if(args.length >= 3 && (args[0].equalsIgnoreCase("setleave"))){
								player.sendMessage(ChatColor.RED + "You can't customise someone elses region!");
								return true;
							}
						} else if(args.length >= 3 && (args[0].equalsIgnoreCase("setleave"))){
							player.sendMessage(ChatColor.RED + "That region does not exist!");
							return true;
						}
					}
				}  else if(args.length >= 3 && (args[0].equalsIgnoreCase("setleave"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to customise region messages.");
						return true;
					}	
			//CUSTOMISE WELCOME & Leave
				
				
				//PREVENT ENTRY
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.customise-entry") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 2 && (args[0].equalsIgnoreCase("prevententry"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								
								RegiosFileManager.pushRegionPreventEntry(true, player, args[1]);

							return true;
						} else if(args.length == 2 && (args[0].equalsIgnoreCase("prevententry"))){
							player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
							return true;
						}
					} else if(args.length == 2 && (args[0].equalsIgnoreCase("prevententry"))){
						player.sendMessage(ChatColor.RED + "That region does not exist!");
						return true;
					}
						
					}
						
				} else {
					if(args.length == 2 && (args[0].equalsIgnoreCase("prevententry"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to prevent/allow access to Regios.");
						return true;
					}
				}
				
				/*if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.customise-exit") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 2 && (args[0].equalsIgnoreCase("preventexit"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								
								RegiosFileManager.pushRegionPreventExit(true, player, args[1]);

							return true;
						} else if(args.length == 2 && (args[0].equalsIgnoreCase("preventexit"))){
							player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
							return true;
						}
					} else if(args.length == 2 && (args[0].equalsIgnoreCase("preventexit"))){
						player.sendMessage(ChatColor.RED + "That region does not exist!");
						return true;
					}
						
					}
						
				} else {
					if(args.length == 2 && (args[0].equalsIgnoreCase("preventexit"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to prevent/allow access to Regios.");
						return true;
					}
				}
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.customise-exit") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 2 && (args[0].equalsIgnoreCase("allowexit"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								
								RegiosFileManager.pushRegionPreventExit(false, player, args[1]);

							return true;
						} else if(args.length == 2 && (args[0].equalsIgnoreCase("allowexit"))){
							player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
							return true;
						}
					} else if(args.length == 2 && (args[0].equalsIgnoreCase("allowexit"))){
						player.sendMessage(ChatColor.RED + "That region does not exist!");
						return true;
					}
						
					}
						
				} else {
					if(args.length == 2 && (args[0].equalsIgnoreCase("preventexit"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to prevent/allow access to Regios.");
						return true;
					}
				}*/
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.customise-entry") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 2 && (args[0].equalsIgnoreCase("allowentry"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								
								RegiosFileManager.pushRegionPreventEntry(false, player, args[1]);

							return true;
						} else if(args.length == 2 && (args[0].equalsIgnoreCase("allowentry"))){
							player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
							return true;
						}
					}  else if(args.length == 2 && (args[0].equalsIgnoreCase("allowentry"))){
						player.sendMessage(ChatColor.RED + "That region does not exist!");
						return true;
					}
				} 
				} else if(args.length == 2 && (args[0].equalsIgnoreCase("allowentry"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to prevent/allow access to Regions.");
						return true;
					}
				//PREVENT ENTRY
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.restore") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 2 && (args[0].equalsIgnoreCase("restore"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								if(RegiosBackups.restoreOccupied){
									player.sendMessage(ChatColor.RED + "A backup is currently being performed on this thread. Please wait a minute and try again.");
									return true;
								}
								if(new File(RegiosBackups.mainDir + File.separator + args[1] + ".MATRIX").exists()){
									RegiosBackups.scheduleRestore(args[1], player);
									return true;
								} else {
									player.sendMessage(ChatColor.RED + "No backup has been created for this region!");
									return true;
								}
								
								
							} else if(args.length == 2 && (args[0].equalsIgnoreCase("restore"))){
								player.sendMessage(ChatColor.RED + "You cannot restore someone elses region!");
								return true;
							}
						}  else if(args.length == 2 && (args[0].equalsIgnoreCase("restore"))){
							player.sendMessage(ChatColor.RED + "That region does not exist!");
							return true;
						}
					}
				} else if(args.length == 2 && (args[0].equalsIgnoreCase("restore"))){
					player.sendMessage(ChatColor.RED + "You are not allowed to restore Regions.");
					return true;
				}
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.backup") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 2 && (args[0].equalsIgnoreCase("backup"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								if(RegiosBackups.threadOccupied){
									player.sendMessage(ChatColor.RED + "A backup is currently being performed on this thread. Please wait a minute and try again.");
									return true;
								}
								Location p1 = new Location(Regios.server.getWorld(RegiosFileManager.regionWorldName[RegiosFileManager.regionIndex]), RegiosFileManager.regionx1[RegiosFileManager.regionIndex], RegiosFileManager.regiony1[RegiosFileManager.regionIndex], RegiosFileManager.regionz1[RegiosFileManager.regionIndex]);
								Location p2 = new Location(Regios.server.getWorld(RegiosFileManager.regionWorldName[RegiosFileManager.regionIndex]), RegiosFileManager.regionx2[RegiosFileManager.regionIndex], RegiosFileManager.regiony2[RegiosFileManager.regionIndex], RegiosFileManager.regionz2[RegiosFileManager.regionIndex]);
								player.sendMessage(ChatColor.BLUE + "[Regios] " + ChatColor.YELLOW + " Saving region to matrix backup file...");
								//RegiosBackups.backupRegion(p1, p2, args[1], player);
								RegiosBackups.scheduleBackup(p1, p2, args[1], player);
								return true;
								
							} else if(args.length == 2 && (args[0].equalsIgnoreCase("backup"))){
								player.sendMessage(ChatColor.RED + "You cannot backup someone elses region!");
								return true;
							}
						}  else if(args.length == 2 && (args[0].equalsIgnoreCase("backup"))){
							player.sendMessage(ChatColor.RED + "That region does not exist!");
							return true;
						}
					}
				} else if(args.length == 2 && (args[0].equalsIgnoreCase("backup"))){
					player.sendMessage(ChatColor.RED + "You are not allowed to backup Regions.");
					return true;
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.list") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 1  &&(args[0].equalsIgnoreCase("list"))){
						
							String regions = "";
							int pages = 1, pageCount = 0;
							for(int a = 1; a <= RegiosFileManager.regionCount; a++){
								if(!RegiosFileManager.regionWorldName[a - 1].equalsIgnoreCase("gjdkgdmh")){
									regions += ChatColor.GREEN + RegiosFileManager.regionNames[a - 1].toLowerCase() + ChatColor.RED + " | ";
									pageCount++;
									if(pageCount > 17){
										pageCount = 0;
										pages++;
									}
								}
								}
								
							player.sendMessage(ChatColor.GOLD + "---------------------" + ChatColor.AQUA + "Regions List" + ChatColor.GOLD + "---------------------");
							player.sendMessage(regions);
							player.sendMessage(ChatColor.GOLD + "---------------------" + ChatColor.AQUA + "Regions List" + ChatColor.GOLD + "---------------------");
							return true;
							
							} else if(args.length == 1  &&(args[0].equalsIgnoreCase("list"))){
						player.sendMessage(ChatColor.RED + "That region does not exist!");
						return true;
					        }
				}else if(args.length == 1  &&(args[0].equalsIgnoreCase("list"))) {
					player.sendMessage(ChatColor.RED + "You are not allowed to view the list of regions!");
					return true;
				}
				
				//LIST
				
				//BACKUP
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.backup-db") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length >= 2  &&(args[0].equalsIgnoreCase("backup-db"))){
						
						String backupFile = "";
						int i;
						for(i = 2; i <= args.length; i++){
							backupFile += args[i - 1];
						}
						
						if(backupFile.length() < 5){
							player.sendMessage(ChatColor.RED + "The backup name must be at least 5 characters long!");
							return true;
						}
						
						if(backupFile.length() > 20){
							player.sendMessage(ChatColor.RED + "The backup name can be no longer than 20 characters!");
							return true;
						}
						
						String backupDir = "plugins" + File.separator + "Regios" + File.separator + "RegionsBackup" + File.separator + backupFile;
						File targetDir = new File(backupDir); 
						
						if(targetDir.exists()){
							player.sendMessage(ChatColor.RED + "There is already a backup created with the name :" + ChatColor.LIGHT_PURPLE + backupFile);
							return true;
						}
						
						
						RegiosFileManager.backupDatabase(new File("plugins/Regios/regions"), backupFile, player);
			
						if(!RegiosFileManager.errorThrown){
						player.sendMessage(ChatColor.GREEN + "Copying SQL tables and configuration to backup : " + ChatColor.LIGHT_PURPLE + backupFile);
						player.sendMessage(ChatColor.GREEN + "Saving backup directory : " + ChatColor.LIGHT_PURPLE + backupFile);
						player.sendMessage(ChatColor.GREEN + "Backup completed successfully!");
						Logger.getLogger("Minecraft").info("[Regios] - Backup completed successfully!");
						Logger.getLogger("Minecraft").info("[Regios] - Backup path : " + backupDir);
						} else {
							RegiosFileManager.errorThrown = false;
						}

						return true;
							
						}
				}else if(args.length >= 2  &&(args[0].equalsIgnoreCase("backup"))) {
					player.sendMessage(ChatColor.RED + "You are not allowed to backup the database!");
					return true;
				}
				//BACKUP
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.movement-factor") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 3 && args[0].equalsIgnoreCase("mf")){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								int fac = 0;
								if(args[2].equalsIgnoreCase("slow")){
										RegiosFileManager.pushMovementFactor(args[1], "slow", player);
										return true;
								}
								if(args[2].equalsIgnoreCase("normal")){
									RegiosFileManager.pushMovementFactor(args[1], "normal", player);
									return true;
								}
							} else if(args.length == 3 && args[0].equalsIgnoreCase("mf")) {
								player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
								return true;
							}

						} else if(args.length == 3 && args[0].equalsIgnoreCase("mf")){
							player.sendMessage(ChatColor.RED + "That region does not exist.");
							return true;
						}
					}
				} else if(args.length == 3 && args[0].equalsIgnoreCase("mf")){
					player.sendMessage(ChatColor.RED + "You are not allowed to customise movement factor settings!");
					return true;
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.rename") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 3 && args[0].equalsIgnoreCase("rename")){
						if(RegiosFileManager.doesRegionExist(args[1], player) && !RegiosFileManager.doesRegionExist(args[2], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								//RegiosFileManager.inheritProperties(args[1], args[2], player);
								RegiosFileManager.renameRegion(args[1], args[2], player);
								return true;
							} else if(args.length == 3 && args[0].equalsIgnoreCase("rename")){
								player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
								return true;
							}
						} else if(args.length == 3 && args[0].equalsIgnoreCase("rename")){
							if(!RegiosFileManager.doesRegionExist(args[1], player)){
								player.sendMessage(ChatColor.RED + "The specified region does not exist.");
								return true;
							}
							if(RegiosFileManager.doesRegionExist(args[2], player)){
								player.sendMessage(ChatColor.RED + "A region with that name already exists.");
								return true;
							}
						}
						return true;
					}
				} else if( args.length == 3  && (args[0].equalsIgnoreCase("rename") )){
					player.sendMessage(ChatColor.RED + "You are not allowed to inherit region properties!");
					return true;
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.inherit") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 3 && args[0].equalsIgnoreCase("inherit")){
						if(RegiosFileManager.doesRegionExist(args[1], player) && RegiosFileManager.doesRegionExist(args[2], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								RegiosFileManager.inheritProperties(args[1], args[2], player);
								return true;
							} else if(args.length == 3 && args[0].equalsIgnoreCase("inherit")){
								player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
								return true;
							}
						} else if(args.length == 3 && args[0].equalsIgnoreCase("inherit")){
							player.sendMessage(ChatColor.RED + "One or both of the regions do not exist.");
							return true;
						}
						return true;
					}
				} else if( args.length == 3  && (args[0].equalsIgnoreCase("inherit") )){
					player.sendMessage(ChatColor.RED + "You are not allowed to inherit region properties!");
					return true;
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.health") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 4 && args[0].equalsIgnoreCase("healthregen")){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								int fac = 0;
								try{
									fac = Integer.parseInt(args[3]);
								} catch (NumberFormatException ex){
									player.sendMessage(ChatColor.RED + "The regen factor must be an integer!");
									return true;
								}
								String type = args[2];
								boolean cmdExecuted = false;
								if(type.equalsIgnoreCase("+")){
									RegiosFileManager.pushRegenFac(args[1], fac, player, true);
									cmdExecuted = true;
									return true;
								} else if(type.equalsIgnoreCase("-")){
									RegiosFileManager.pushRegenFac(args[1], fac, player, false);
									cmdExecuted = true;
									return true;
								}
								if(!cmdExecuted){
									player.sendMessage(ChatColor.RED + "The factor marker must either be " + ChatColor.GREEN + "+" + ChatColor.RED + " or " + ChatColor.GREEN + "-");
									return true;
								}
							} else if(args.length == 4 && args[0].equalsIgnoreCase("healthregen")){
								player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
								return true;
							}
						} else if(args.length == 4 && args[0].equalsIgnoreCase("healthregen")){
							player.sendMessage(ChatColor.RED + "That region does not exist.");
							return true;
						}
					}
				} else if( args.length == 4  && (args[0].equalsIgnoreCase("healthregen") )){
					player.sendMessage(ChatColor.RED + "You are not allowed to customise region health settings!");
					return true;
				}
					
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.health") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 3 && args[0].equalsIgnoreCase("usehealth")){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								if(args[2].equalsIgnoreCase("true")){
									RegiosFileManager.pushHealthSetting(player, args[1], true);
									return true;
								} else if(args[2].equalsIgnoreCase("false")){
									RegiosFileManager.pushHealthSetting(player, args[1], false);
									return true;
								}
							} else if(args.length == 3 && args[0].equalsIgnoreCase("health")){
								player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
								return true;
							}
						} else if(args.length == 3 && args[0].equalsIgnoreCase("health")){
							player.sendMessage(ChatColor.RED + "That region does not exist.");
							return true;
						}
					}
				}  else if( args.length == 3  && (args[0].equalsIgnoreCase("health") )){
					player.sendMessage(ChatColor.RED + "You are not allowed to customise region health settings!");
					return true;
				}
				
				
				
				//USE HEALTH
				
				//VERSION CHECK
				
				//VERSION CHECK
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.mobs") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 3 && (args[0].equalsIgnoreCase("mobspawn"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								
								if(args[2].equalsIgnoreCase("true")){
									RegiosFileManager.prohibitMobSpawns(args[1], player, true);
									return true;
								} else if(args[2].equalsIgnoreCase("false")){
									RegiosFileManager.prohibitMobSpawns(args[1], player, false);
									return true;
								}
								
								player.sendMessage(ChatColor.RED + "The value must either be true or false!");

							return true;
						} else if(args.length == 3 && (args[0].equalsIgnoreCase("mobspawn"))){
							player.sendMessage(ChatColor.RED + "You cant modify someone elses region!");
							return true;
						}
					}  else if(args.length == 3 && (args[0].equalsIgnoreCase("mobspawn"))){
						player.sendMessage(ChatColor.RED + "That region does not exist!");
						return true;
					}
					}
				} else if(args.length == 3 && (args[0].equalsIgnoreCase("mobspawn"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to customise mob-spawns.");
						return true;
					}
				
				//PROTECTION
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.protect") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 2 && (args[0].equalsIgnoreCase("protect"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								
								RegiosFileManager.pushRegionProtected(true, player, args[1]);

							return true;
						} else if(args.length == 2 && (args[0].equalsIgnoreCase("protect"))){
							player.sendMessage(ChatColor.RED + "You cant modify someone elses region!");
							return true;
						}
					}  else if(args.length == 2 && (args[0].equalsIgnoreCase("protect"))){
						player.sendMessage(ChatColor.RED + "That region does not exist!");
						return true;
					}
					}
				} else if(args.length == 2 && (args[0].equalsIgnoreCase("protect"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to (un)protect Regios.");
						return true;
					}
				
				
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.protect") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 2 && (args[0].equalsIgnoreCase("unprotect"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							if(RegiosFileManager.isOwner(args[1], player)){
								
								RegiosFileManager.pushRegionProtected(false, player, args[1]);

							return true;
						} else if(args.length == 2 && (args[0].equalsIgnoreCase("unprotect"))){
							player.sendMessage(ChatColor.RED + "You cannot modify someone elses region!");
							return true;
						}
					} else if(args.length == 2 && (args[0].equalsIgnoreCase("unprotect"))){
						player.sendMessage(ChatColor.RED + "That region does not exist!");
						return true;
					}
				} 
				} else {
					if(args.length == 2 && (args[0].equalsIgnoreCase("unprotect"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to (un)protect Regions.");
						return true;
					}
				}
				//PROTECTION
				
				//HELP
				if( args.length >= 1  && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?") )){
					if(args.length == 2){
						int pageNum = 0;
						try{
							pageNum = Integer.parseInt(args[1]);
						} catch (NumberFormatException ex){
							player.sendMessage(ChatColor.RED + "The page number must be an integer!");
							return true;
						}
						if(pageNum == 1){
							displayHelp(player, 1);
							return true;
						}
						if(pageNum == 2){
							displayHelp(player, 2);
							return true;
						}
						if(pageNum == 3){
							displayHelp(player, 3);
							return true;
						}
					}
					displayHelp(player, 1);
					return true;
				}
				//HELP
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.modifyowner") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 3 && args[0].equalsIgnoreCase("setowner")){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
							RegiosFileManager.pushModifyRegionOwner(args[2], args[1], player);
							return true;
						} else if(args.length == 3 && args[0].equalsIgnoreCase("setowner")){
							player.sendMessage(ChatColor.RED + "That region does not exist!");
							return true;
						}
					}
				} else if(args.length == 3 && args[0].equalsIgnoreCase("setowner")){
					player.sendMessage(ChatColor.RED + "You are not allowed to modify the owner of regions!");
					return true;
				}
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.info") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if( args.length == 2 && (args[0].equalsIgnoreCase("info"))){
						if(RegiosFileManager.doesRegionExist(args[1], player)){
								player.sendMessage(ChatColor.GOLD + "---------------------" + ChatColor.AQUA + "Regions Info" + ChatColor.GOLD + "---------------------");
								player.sendMessage(ChatColor.GOLD + "Region Name | " + ChatColor.LIGHT_PURPLE + args[1] + ChatColor.GOLD + "");
								player.sendMessage(ChatColor.GOLD + "Location | " + ChatColor.GREEN + "X : " + RegiosFileManager.regionx1[RegiosFileManager.regionIndex] + " Z : " + RegiosFileManager.regionz1[RegiosFileManager.regionIndex]  + " | Y1 : " + RegiosFileManager.regiony1[RegiosFileManager.regionIndex]  + " | Y2 : " + RegiosFileManager.regiony2[RegiosFileManager.regionIndex] );
								player.sendMessage(ChatColor.GOLD + "Region in World | " + ChatColor.LIGHT_PURPLE + RegiosFileManager.regionWorldName[RegiosFileManager.regionIndex] + ChatColor.GOLD + "");
								player.sendMessage(ChatColor.GOLD + "Region Creator | " + ChatColor.LIGHT_PURPLE + RegiosFileManager.regionOwner[RegiosFileManager.regionIndex] + ChatColor.GOLD + "");
								player.sendMessage(ChatColor.GOLD + "Region Protected | " + ChatColor.LIGHT_PURPLE + RegiosFileManager.regionProtected[RegiosFileManager.regionIndex] + ChatColor.GOLD + "");
								player.sendMessage(ChatColor.GOLD + "Region Preventing Entry | " + ChatColor.LIGHT_PURPLE + RegiosFileManager.regionPreventEntry[RegiosFileManager.regionIndex] + ChatColor.GOLD + "");
								player.sendMessage(ChatColor.GOLD + "Region Health Enabled | " + ChatColor.LIGHT_PURPLE + RegiosFileManager.regionHealthEnabled[RegiosFileManager.regionIndex] + ChatColor.GOLD + "");
								player.sendMessage(ChatColor.GOLD + "Region Mobs Spawn | " + ChatColor.LIGHT_PURPLE + RegiosFileManager.regionCreaturesSpawn[RegiosFileManager.regionIndex] + ChatColor.GOLD + "");
								player.sendMessage(ChatColor.GOLD + "Exception Count | " + ChatColor.LIGHT_PURPLE + RegiosFileManager.exceptionCount[RegiosFileManager.regionIndex] + ChatColor.GOLD + "");
								if(RegiosFileManager.healthgenFactor[RegiosFileManager.regionIndex] >= 0){
									player.sendMessage(ChatColor.GOLD + "H-HPS | " + ChatColor.GREEN + "+" + ChatColor.LIGHT_PURPLE + RegiosFileManager.healthgenFactor[RegiosFileManager.regionIndex] + ChatColor.GOLD + "");
								} else {
									player.sendMessage(ChatColor.GOLD + "H-HPS | " + ChatColor.LIGHT_PURPLE + RegiosFileManager.healthgenFactor[RegiosFileManager.regionIndex] + ChatColor.GOLD + "");
								}
								player.sendMessage(ChatColor.GOLD + "LSPS | " + ChatColor.LIGHT_PURPLE + RegiosFileManager.lsps[RegiosFileManager.regionIndex] + ChatColor.GOLD + "");
								player.sendMessage(ChatColor.GOLD + "PVP | " + ChatColor.LIGHT_PURPLE + RegiosFileManager.regionPVPEnabled[RegiosFileManager.regionIndex] + ChatColor.GOLD + "");
								
								player.sendMessage(ChatColor.GOLD + "---------------------" + ChatColor.AQUA + "Regions Info" + ChatColor.GOLD + "---------------------");
								return true;
						}
					} else {
						if( args.length == 2 && (args[0].equalsIgnoreCase("info"))){
							player.sendMessage(ChatColor.RED + "That region does not exist!");
							return true;
						}
					}
				} else if( args.length == 2 && (args[0].equalsIgnoreCase("info"))){
					player.sendMessage(ChatColor.RED + "You are not allowed to view the info of regions!");
					return true;
				}
				
				
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.createdelete") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 1 && args[0].equalsIgnoreCase("set")){
						RegiosPlayerListener.settingRegion.put(player, true);
						sender.sendMessage(ChatColor.GREEN + "Region setting enabled. Left and right click to select points.");
						if(!player.getInventory().contains(RegiosFileManager.selectionToolID)){
							player.getInventory().setItem(player.getInventory().firstEmpty(), new ItemStack(RegiosFileManager.selectionToolID, 1));
							//player.getInventory().setItemInHand(new ItemStack(RegiosFileHandler.selectionToolID, 1));
						}
						return true;
				}
				}  else if(args.length == 1 && args[0].equalsIgnoreCase("set")){
						player.sendMessage(ChatColor.RED + "You are not allowed to create Regios.");
						return true;
					}
					
				if((Regios.hasPermissions && (Regios.permissionHandler.has(player, "region.createdelete") || Regios.permissionHandler.has(player, "region.*"))) || player.isOp()){
					if(args.length == 1 && args[0].equalsIgnoreCase("cancel")){
						RegiosPlayerListener.settingRegion.put(player, false);
						sender.sendMessage(ChatColor.RED + "Region setting cancelled.");
						return true;
					}
				} else {
					if(args.length == 1 && args[0].equalsIgnoreCase("cancel")){
						player.sendMessage(ChatColor.RED + "You are not allowed to create Regios.");
						return true;
					}
				}
				
				
				if(args.length == 1 && (args[0].equalsIgnoreCase("kill"))){
					Location l = player.getLocation();
					return true;
				}
				
				if( (Regios.hasPermissions && (Regios.permissionHandler.has(player, "regios.createdelete") || Regios.permissionHandler.has(player, "regios.*"))) || player.isOp()){
					if(args.length == 2 && (args[0].equalsIgnoreCase("create"))){
						if((RegiosPlayerListener.regionFirstPointLocation.containsKey(player) && RegiosPlayerListener.regionSecondPointLocation.containsKey(player)) && (RegiosPlayerListener.regionSetFirstPoint.get(player) && RegiosPlayerListener.regionSetFirstPoint.get(player) && args[1].length() >= 3 && args[1].length() <= 20)){
							if(!RegiosFileManager.doesRegionExist(args[1], player)){
								if(args[1].contains("'")){
									player.sendMessage(ChatColor.RED + "Illegal Character " + ChatColor.AQUA +  "' " + ChatColor.RED + " in Region Name.");
									return true;
								}
								RegiosFileManager.updateDatabase(args[1].toLowerCase(), player);
								RegiosPlayerListener.regionSetSecondPoint.put(player, false);
								RegiosPlayerListener.regionSecondPointLocation.remove(player);
								RegiosPlayerListener.regionSetFirstPoint.put(player, false);
								RegiosPlayerListener.regionFirstPointLocation.remove(player);
								RegiosPlayerListener.settingRegion.put(player, false);
								RegiosPlayerListener.settingRegion.remove(player);
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "A region with that name already exists!");
								return true;
							}
						} else {
							if((RegiosPlayerListener.regionFirstPointLocation.containsKey(player) && !RegiosPlayerListener.regionSetFirstPoint.get(player))){
								player.sendMessage(ChatColor.RED + "You must set two points before creating a Region.");
								return true;
							}
							if((RegiosPlayerListener.regionSecondPointLocation.containsKey(player) && !RegiosPlayerListener.regionSetSecondPoint.get(player))){
								player.sendMessage(ChatColor.RED + "You must set two points before creating a Region.");
								return true;
							}
							if((!RegiosPlayerListener.regionFirstPointLocation.containsKey(player) || !RegiosPlayerListener.regionSecondPointLocation.containsKey(player))){
								player.sendMessage(ChatColor.RED + "You must set two points before creating a Region.");
								return true;
							}
							if(!(args[1].length() >= 3)){
								player.sendMessage(ChatColor.RED + "Region names must be at least 3 characters long.");
								return true;
							}
							if(!(args[1].length() <= 20)){
								player.sendMessage(ChatColor.RED + "Region names can only be a maximum of 20 characters long.");
								return true;
							}
							
						}
					}
				} else if(args.length == 2 && (args[0].equalsIgnoreCase("create"))){
						player.sendMessage(ChatColor.RED + "You are not allowed to create Regions.");
						return true;
					}
				
				
			}//end of instance check
			sender.sendMessage(ChatColor.RED + "Invalid Regios command. Use /regios help");
			return true;
		}//end of label check
		return false;
	}//end of function
	
	
	public void displayHelp(Player player, int page){ //Minecraft chat display length == 54
		if(page == 1){
			player.sendMessage(ChatColor.GOLD + "---------------------" + ChatColor.AQUA + "Regios Help" + ChatColor.GOLD + "---------------------");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios set" + ChatColor.GOLD + "                                                         ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios create <Regionname>  " + ChatColor.RED + "|" + ChatColor.GREEN + " /Regios cancel" + ChatColor.GOLD + "           ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios delete <Regionname>" + ChatColor.GOLD + "                                     ");
			//player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios teleto <Regionname>   "  + ChatColor.RED + "|" + ChatColor.GREEN + " /Regios teleback" + ChatColor.GOLD + "            ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios cancel  ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios modify <Regionname>  ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios modify confirm  ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios list" + ChatColor.GOLD + "                                 ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios info <Regionname>" + ChatColor.GOLD + "                                 ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios addex <Regionname> <playername>" + ChatColor.GOLD + "             ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios remex <Regionname> <playername>" + ChatColor.GOLD + "        ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios setwelcome <Regionname> <welcome msg>" + ChatColor.GOLD + "            ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios showwelcome <Regionname> <true/false>" + ChatColor.GOLD + "            ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios setleave <Regionname> <leave msg>" + ChatColor.GOLD + "                   ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios showleave <Regionname> <true/false>" + ChatColor.GOLD + "               ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios protect <Regionname>" + ChatColor.GOLD + "                                  ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios unprotect <Regionname>" + ChatColor.GOLD + "                               ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios backup <Backup FileName>" + ChatColor.GOLD + "                               ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.RED + "Page[1/3]. Type " + ChatColor.GREEN + "/Regios help <1/2>" + ChatColor.RED + " to switch pages.");
			player.sendMessage(ChatColor.GOLD + "---------------------" + ChatColor.AQUA + "Regios Help" + ChatColor.GOLD + "---------------------");
		}
		
		if(page == 2){
			player.sendMessage(ChatColor.GOLD + "---------------------" + ChatColor.AQUA + "Regios Help" + ChatColor.GOLD + "---------------------");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios expandmax <Regionname>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios allowentry <Regionname>" + ChatColor.GOLD + "                               ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios prevententry <Regionname>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios reload-config" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios reload-database" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios usehealth <Regionname> <true/false>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios setowner <Regionname> <Player>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios mobspawn <Regionname> <true/false>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios healthregen <Regionname> <+/-> <Integer_Value>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios lsps <Regionname> <Integer_Value>" + ChatColor.GOLD + " ");
			//player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios setpermlvl <Regionname> <guest/player/mod/admin>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios version" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios setwarp" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios warpto <Regionname>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios setpvp <Regionname> <True/False>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios backup <Regionname>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios restore <Regionname>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios rename <Regionname> <New name>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.RED + "Page[2/3]. Type " + ChatColor.GREEN + "/Regios help <1/2>" + ChatColor.RED + " to switch pages.");
			player.sendMessage(ChatColor.GOLD + "---------------------" + ChatColor.AQUA + "Regios Help" + ChatColor.GOLD + "---------------------");
		}
		
		if(page == 3){
			player.sendMessage(ChatColor.GOLD + "---------------------" + ChatColor.AQUA + "Regios Help" + ChatColor.GOLD + "---------------------");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios inherit <RegionToInherit> <InheritFrom>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios expandup <Regionname> <Integer_Value>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.GREEN + "/Regios expanddown <Regionname> <Integer_Value>" + ChatColor.GOLD + " ");
			player.sendMessage(ChatColor.GOLD + "|" + ChatColor.RED + "Page[3/3]. Type " + ChatColor.GREEN + "/Regios help <1/2>" + ChatColor.RED + " to switch pages.");
			player.sendMessage(ChatColor.GOLD + "---------------------" + ChatColor.AQUA + "Regios Help" + ChatColor.GOLD + "---------------------");
		}
	}

}
