package me.Adamki11s.Regios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class RegiosBackups {
	
	static File mainDir = new File("plugins/Regios/RegionBackups/");

	
	private static Location p1, p2; private static String regStr; private static Player plyr;
	
	public static int backupTaskID, restoreTaskID;
	
	public static boolean threadOccupied = false, restoreOccupied = false;
	
	public static void scheduleBackup(Location pos1, Location pos2, String region, Player player){
		//Plugin Regioss;
		p1 = pos1;;
		p2 = pos2;
		regStr = region;
		plyr = player;
		
		backupTaskID = Regios.server.getScheduler().scheduleAsyncDelayedTask(Regios.plugin, new Runnable() {
			
			
	        public void run() {
	           //do stuff
	        	
	        	try {
	        		threadOccupied = true;
					backupRegion(p1, p2, regStr, plyr);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }, 20L);
		
	}
	
	
	public static void scheduleRestore(String region, Player player){
		//Plugin Regioss;
		regStr = region;
		plyr = player;
		
		restoreTaskID = Regios.server.getScheduler().scheduleAsyncDelayedTask(Regios.plugin, new Runnable() {
			
			
	        public void run() {
	           //do stuff
	        	
	        	try {
	        		restoreOccupied = true;
					restoreRegion(regStr, plyr);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }, 20L);
		Regios.server.getScheduler().cancelTask(backupTaskID);
		
	}
		
        public void run() {
            
        }
	
	
	public static String constructMatrixID(Location loc){
		int ID = loc.getWorld().getBlockTypeIdAt(loc);
		Byte data = loc.getWorld().getBlockAt(loc).getData();
		return loc.getWorld().getName() + "@" + loc.getX() + "@" + loc.getY() + "@" + loc.getZ() + "@" + ID + "@" + data + "|";
	}
	
	public static int blockCount;
	
	public static void backupRegion(Location pos1, Location pos2, String region, Player player) throws IOException{
		double x1 = pos1.getX(), x2 = pos2.getX(),
		       y1 = pos1.getY(), y2 = pos2.getY(),
		       z1 = pos1.getZ(), z2 = pos2.getZ();
		double tmpVal;
		
		World world = pos1.getWorld();
		
		if(x1 < x2){
			tmpVal = x1;
			x1 = x2;
			x2 = tmpVal;
		} 
		
		if(y1 < y2){
			tmpVal = y1;
			y1 = y2;
			y2 = tmpVal;
		} 
		
		if(z1 < z2){
			tmpVal = z1;
			z1 = z2;
			z2 = tmpVal;
		} 
		
		double volD1, volD2, volD3;
		volD1 = x1 - x2;
		volD2 = y1 - y2;
		volD3 = z1 - z2;
		
		blockCount = (int) (volD1 * volD2 * volD3);
		
		String backupMatrix = blockCount + "|";
		String matrixArray[] = new String [1000];
		boolean pcentSent[] = new boolean [10];
		int matrixCount = 1;
		double pcent;
		int cntt = 0;
		for(int x = 0; x2 + x <= x1; x++){
			for(int y = 0; y2 + y <= y1; y++){
				for(int z = 0; z2 + z <= z1; z++){
					cntt++;
					//player.sendMessage(ChatColor.GREEN + "" + cntt + " : " + blockCount);
					//pcent = (cntt / blockCount) * 100;
					double cnt = cntt;
					double bc = blockCount;
					pcent = Math.ceil((cnt / bc) * 100);
					//player.sendMessage(pcent + " " + cntt + " " + blockCount);
					if(pcent > 20 && pcent < 30){
						if(!pcentSent[0]){
							player.sendMessage(ChatColor.BLUE + "[Regios] " + ChatColor.GREEN + " 10% Complete.");
							pcentSent[0] = true;
						}
					}
					if(pcent > 40 && pcent < 50){
						if(!pcentSent[1]){
							player.sendMessage(ChatColor.BLUE + "[Regios] " + ChatColor.GREEN + " 30% Complete.");
							pcentSent[1] = true;
						}
					}
					if(pcent > 60 && pcent < 70){
						if(!pcentSent[2]){
							player.sendMessage(ChatColor.BLUE + "[Regios] " + ChatColor.GREEN + " 50% Complete.");
							pcentSent[2] = true;
						}
					}
					if(pcent > 90 && pcent < 100){
						if(!pcentSent[3]){
							player.sendMessage(ChatColor.BLUE + "[Regios] " + ChatColor.GREEN + " 70% Complete.");
							pcentSent[3] = true;
						}
					}
					if(pcent > 115 && pcent < 130){
						if(!pcentSent[4]){
							player.sendMessage(ChatColor.BLUE + "[Regios] " + ChatColor.GREEN + " 90% Complete.");
							pcentSent[4] = true;
						}
					}
					
					
					if(backupMatrix.length() > 30000){
						matrixArray[matrixCount - 1] = backupMatrix;
						matrixCount++;
						backupMatrix = "";
					}
					backupMatrix += constructMatrixID(new Location(world, x2 + x, y2 + y, z2 + z));
				}	
			}
		}
		String matBuild = "";
		for(int e = 1; e <= matrixCount; e++){
			matBuild += matrixArray[e - 1];
		}
		writeToFile(region, matBuild, player);
		threadOccupied = false;
		matBuild = "";
		backupMatrix = "";
	}
	
	public static void writeToFile(String region, String backupMatrix, Player player) throws IOException{
		boolean override = false;
		File bckFile = new File(mainDir + File.separator + region + ".MATRIX");
		if(bckFile.exists()){
			bckFile.delete();
			bckFile.createNewFile();
			override = true;
		} else {
			bckFile.createNewFile();
		}
		BufferedWriter out = new BufferedWriter(new FileWriter(bckFile));
	    out.write(backupMatrix);
	    out.flush();
	    out.close();
	    if(override){
	    	player.sendMessage(ChatColor.BLUE + "[Regios][Backup] " + ChatColor.GREEN + "Region backup overriden successfully!");
	    	player.sendMessage(ChatColor.BLUE + "[Regios] " + ChatColor.YELLOW + blockCount + " blocks saved.");
	    } else {
	    	player.sendMessage(ChatColor.BLUE + "[Regios][Backup] " + ChatColor.GREEN + "Region backed up successfully!");
	    	player.sendMessage(ChatColor.BLUE + "[Regios] " + ChatColor.YELLOW + blockCount + " blocks saved.");
	    }
	}
	
	static Location[] locations = new Location[10000000];
	static int[] itemID = new int[10000000];
	static byte[] blockData = new byte[10000000];
	static int bCount;
	static int blocksrestored = 0;
	
	public static void restoreRegion(String region, Player player) throws IOException{
		region = region.toLowerCase();
		File bckFile = new File(mainDir + File.separator + region + ".MATRIX");
		
		if(!bckFile.exists()){
			player.sendMessage(ChatColor.RED + "[ERROR] No backup file exists for this region!");
		}
		player.sendMessage(ChatColor.BLUE + "[Regios] " + ChatColor.YELLOW + " Restoring region from matrix backup file...");
		String rawMatrix = "";
		
		
		/*FileReader input = new FileReader(bckFile);

		BufferedReader bufRead = new BufferedReader(input);

		String line;
		int count = 0;

		line = bufRead.readLine();

		while (line != null) {
			line = bufRead.readLine();
			rawMatrix += line;
		}

		bufRead.close();
		*/
		FileInputStream in = new FileInputStream(bckFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
	    String strLine;
	  //Read File Line By Line
	    while ((strLine = br.readLine()) != null)   {
	      // Print the content on the console
	      //System.out.println(strLine);
	      //banList[banCount] = strLine;
	      rawMatrix += strLine;
	      //NoSpamPlayerListener.banmap.put(banList[banCount], true);
	      //banCount++;
	    }
		in.close();
		
		String parts[] = rawMatrix.split("\\|");
		//RegiosFileManager.log.info(rawMatrix);
		//RegiosFileManager.log.info("PARTS 0 = " + parts[0]);
		bCount = Integer.parseInt(parts[0]);
		String[] location;
		
		for(int i = 1; i <= bCount; i++){
			location = parts[i].split("\\@");
			locations[i - 1] = new Location(Regios.server.getWorld(location[0]), Double.parseDouble(location[1]), Double.parseDouble(location[2]), Double.parseDouble(location[3]));
			itemID[i - 1] = Integer.parseInt(location[4]);
			blockData[i - 1] = Byte.parseByte(location[5]);
		}
		 
		reconstructRegion(locations[0].getWorld(), bCount);
		player.sendMessage(ChatColor.GREEN + "Region restored to previous backup successfully.");
		player.sendMessage(ChatColor.BLUE + "[Regios] " + ChatColor.YELLOW + blocksrestored + " blocks restored.");
		player.sendMessage(ChatColor.BLUE + "[Regios] " + ChatColor.YELLOW + (bCount - blocksrestored) + " blocks were not modified.");
		restoreOccupied = false;
		Regios.server.getScheduler().cancelTask(restoreTaskID);
	}
	
	
	public static void reconstructRegion(World world, int count){
		try{
			for(int mx = 1; 1 <= count; mx++){
				if(world.getBlockTypeIdAt(locations[mx - 1]) != itemID[mx - 1]){
					Block b = world.getBlockAt(locations[mx - 1]);
					b.setTypeId(itemID[mx - 1]);
					b.setData(blockData[mx - 1]);
					blocksrestored++;
				}
			}
		} catch (Exception ex){
			
		}
		
	}
	
}
