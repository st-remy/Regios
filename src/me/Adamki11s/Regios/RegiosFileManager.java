package me.Adamki11s.Regios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.config.Configuration;

import com.alta189.sqlLibrary.SQLite.sqlCore;

public class RegiosFileManager {

	static String mainDirectory = "plugins/Regios";

	static Logger log = Logger.getLogger("Minecraft");

	static File maindir = new File(mainDirectory);

	public static File configFile = new File(
			"plugins/Regios/RegiosGlobalConfig.yml");
	static Configuration config = new Configuration(configFile);
	public static Configuration configReload = new Configuration(configFile);

	static ArrayList<Material> IllegalBlocks = new ArrayList<Material>();
	public static boolean useBedrock, useWater, useWaterBucket, useLava,
			useLavaBucket, useTNT, useFire, useObsidian, useFlintAndSteel,
			spawnCreepers, creepersExplode, updateCheck;
	public static int selectionToolID = 0, regionCount = 0, regionIndex = 0;

	public static double[] regionx1 = new double[5000];
	public static double[] regionx2 = new double[5000];
	public static double[] regiony1 = new double[5000];
	public static double[] regiony2 = new double[5000];
	public static double[] regionz1 = new double[5000];
	public static double[] regionz2 = new double[5000];

	public static String[] regionNames = new String[5000];
	public static String[] regionWelcomeMsg = new String[5000];
	public static String[] regionLeaveMsg = new String[5000];
	public static String[] regionOwner = new String[5000];
	public static String[] regionWorldName = new String[5000];

	public static String[][] regionExceptions = new String[5000][5000];
	public static int[] exceptionCount = new int[5000];
	public static double[] movementFactor = new double[5000];
	public static int[] healthgenFactor = new int[5000];
	public static int[] lsps = new int[5000];

	public static boolean[] regionWelcome = new boolean[5000];
	public static boolean[] regionLeave = new boolean[5000];
	public static boolean[] regionProtected = new boolean[5000];
	public static boolean[] regionPreventEntry = new boolean[5000];
	public static boolean[] regionPreventExit = new boolean[5000];
	public static boolean[] regionEnabled = new boolean[5000];
	public static boolean[] regionHealthEnabled = new boolean[5000];
	public static boolean[] regionPVPEnabled = new boolean[5000];
	public static boolean[] regionCreaturesSpawn = new boolean[5000];
        
        public static int[][] bannedItems = new int[1000][1000];
	
	public static Location[] warpLocations = new Location[5000];

	public static boolean check4Updates = false;

	public static int count = 0;

	public static sqlCore dbManage;
	
	public static void patchWarps(){
		
	}
	
	static boolean patched = false;

	public static void startupProcedure() {
		createDirs();

		dbManage = new sqlCore(log, Regios.logPrefix, "regions",
				maindir.getAbsolutePath());
		dbManage.initialize();
		
		if(!dbManage.checkTable("economy")){
			String query = "CREATE TABLE economy ( 'id' INTEGER PRIMARY KEY AUTOINCREMENT, 'regionname' VARCHAR(20), 'owner' VARCHAR(40), 'price' Double);";
			dbManage.createTable(query);
			String initialise = "INSERT INTO economy (regionname, owner, price) values ('gfhfsdvmbv', 'null', '0');";
		}

		if (!dbManage.checkTable("regions")) {
			Location loc = new Location(Regios.server.getWorld("world"), 0, 0, 0, 0, 0);
			String query = ("CREATE TABLE regions ( 'id' INTEGER PRIMARY KEY AUTOINCREMENT, 'regionname' VARCHAR(20), 'x1' Double , 'x2' Double , 'y1' Double , 'y2' Double , 'z1' Double , 'z2' Double , 'showwelcome' Byte DEFAULT 1, 'showleave' Byte DEFAULT 1, 'protected' Byte DEFAULT 0, 'prevententry' Byte DEFAULT 0, 'preventexit' Byte DEFAULT 0, 'welcomemsg' VARCHAR(50) ,'leavemsg' VARCHAR(50) , 'owner' VARCHAR(30) , 'healthenabled' Byte DEFAULT 1 , 'exceptions' Byte DEFAULT 0 , 'movementfactor' DOUBLE DEFAULT 1 , 'pvp'  Byte DEFAULT 0 , 'healthregen' Byte DEFAULT 0 , 'prohibitedmobs' Byte DEFAULT 1 , 'world' VARCHAR(50) , 'lsps' Byte DEFAULT 0, 'warps' VARCHAR(200) DEFAULT 'world@0@0@0@0@0');");
			dbManage.createTable(query); // owner, healthenabled, exceptions,
											// movementfactor, pvp, healthregen,
											// prohibitedmobs, world)
			String initialiseDB = ("INSERT INTO regions (regionname, x1, x2, y1, y2, z1, z2, showwelcome, showleave, protected, prevententry, preventexit, welcomemsg, leavemsg, owner, healthenabled, exceptions, movementfactor, pvp, healthregen, prohibitedmobs, world, lsps, warps) values ('default', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', 'default', 'default', 'null', '1', '0', '0', '1', '0', '1', 'world', '0', 'world@0@0@0@0@0');");
			dbManage.insertQuery(initialiseDB);
			log.info("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion
					+ " - SQL Tables created on first run.");
			try {
				new File(mainDirectory + File.separator + "Patches" + File.separator + "Version3-4.PATCH").createNewFile();
				new File(mainDirectory + File.separator + "Patches" + File.separator + "Version3-4-01.PATCH").createNewFile();
				new File(mainDirectory + File.separator + "Patches" + File.separator + "Version3-4-02.PATCH").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//String query = "IF NOT EXISTS (SELECT warps FROM INFORMATION_SCHEMA.columns WHERE table_name = 'regions' AND column_name = 'warps') ALTER TABLE regions ADD warps VARCHAR(20);";
		if(!(new File(mainDirectory + File.separator + "Patches" + File.separator + "Version3-4.PATCH").exists())){
			backupDatabase(new File("plugins/Regios/regions"), "3-4DEFAULT BACKUP", null);
			log.info("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion + " - Patching SQL Tables for Version 3.4");
			String loc = new Location(Regios.server.getWorld("world"), 0, 0, 0, 0, 0).toString();
			String query = "ALTER TABLE regions ADD warps VARCHAR(200) DEFAULT '" + loc + "';";
			dbManage.updateQuery(query);
			patched = true;
			try {
				new File(mainDirectory + File.separator + "Patches" + File.separator + "Version3-4.PATCH").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion + " - Patch completed successfully!");
		}	
		
		if(!(new File(mainDirectory + File.separator + "Patches" + File.separator + "Version3-4-01.PATCH").exists())){
			backupDatabase(new File("plugins/Regios/regions"), "3-4-01DEFAULT BACKUP");
			log.info("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion + " - Patching MobSpawn Tables for Version 3.4.01");
			String fixMobs = "UPDATE regions SET prohibitedmobs='0' WHERE prohibitedmobs='';";
			dbManage.updateQuery(fixMobs);
			try {
				new File(mainDirectory + File.separator + "Patches" + File.separator + "Version3-4-01.PATCH").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion + " - Patch completed successfully!");
		}
		
		
		if(!(new File(mainDirectory + File.separator + "Patches" + File.separator + "Version3-4-02.PATCH").exists())){
			backupDatabase(new File("plugins/Regios/regions"), "3-4-02DEFAULT BACKUP");
			log.info("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion + " - Patching Warp Tables for Version 3.4.02");
			String formatTables = "UPDATE regions SET warps='world@0@0@0@0@0';";
			dbManage.updateQuery(formatTables);
			try {
				new File(mainDirectory + File.separator + "Patches" + File.separator + "Version3-4-02.PATCH").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion + " - Patch completed successfully!");
		}
                
                if(!(new File(mainDirectory + File.separator + "Patches" + File.separator + "Version3-6-00.PATCH").exists())){
			backupDatabase(new File("plugins/Regios/regions"), "3-6-00DEFAULT BACKUP");
			log.info("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion + " - Patching SQL Tables for Version 3.6.00");
			String query = ("CREATE TABLE itemflags ( 'id' INTEGER PRIMARY KEY AUTOINCREMENT, 'banneditems' VARCHAR(1000), 'alloweditems' VARCHAR(1000), 'regionname' VARCHAR(30) ;");
			dbManage.createTable(query);
                        dbManage.insertQuery("INSERT INTO itemflags (banneditems, alloweditems, regionname) values ('', '', 'jdbhfhkbkgabfj54fdl');");
			try {
				new File(mainDirectory + File.separator + "Patches" + File.separator + "Version3-6-00.PATCH").createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion + " - Patch completed successfully!");
		}
		

		if (!checkConfigurationExistance()) {
			createConfiguration();
		}
		log.info("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion
				+ " - Initialising SQL tables...");
		
		loadDatabase();
		loadConfiguration();

	}

	public static void createDirs() {
		new File(mainDirectory).mkdir();
		new File(mainDirectory + File.separator + "Patches").mkdir();
		new File(mainDirectory + File.separator + "RegionBackups").mkdir();
	}

	public static void createConfiguration() {
		try {
			configFile.createNewFile();
			// config.load();
			config.setProperty("RegioGeneralConfiguration.SelectionToolID", 286);
			config.save();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		log.info("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion
				+ " - Configuration created on first run.");
	}

	public static boolean checkConfigurationExistance() {
		if (!configFile.exists()) {
			return false;
		} else {
			return true;
		}
	}

	public static String formatPlayer(Player player) {
		String stf = player.toString();
		String[] fm1, fm2;
		fm1 = stf.split("=");
		fm2 = fm1[1].split("}");
		stf = fm2[0];
		return stf;
	}

	public static int getRegionCount() {
		try {
			String query = "SELECT COUNT(*) as count FROM regions;";
			ResultSet result = dbManage.sqlQuery(query);
			count = result.getInt("count") - 1;
			regionCount = count;
			return count;
		} catch (SQLException ex) {
			// ex.printStackTrace();
		}
		return count;
	}

	public static Location parseLocation(String location, String wn){
		//if(location.contains("\\@") && location != constructLocation(new Location(Regios.server.getWorld(wn), 0, 0, 0, 0, 0))){
		String[] parts = location.split("\\@");
		World w = Regios.server.getWorld(parts[0]);
		double x = Double.parseDouble(parts[1]);
		double y = Double.parseDouble(parts[2]);
		double z = Double.parseDouble(parts[3]);
		float yaw = Float.parseFloat(parts[4]);
		float pitch = Float.parseFloat(parts[5]);
		return new Location(w, x, y, z, yaw, pitch);
	}

	public static void loadDatabase() {
		try {
			String query = "SELECT COUNT(*) as count FROM regions;";
			ResultSet result = dbManage.sqlQuery(query);
			count = result.getInt("count");
			regionCount = count - 1;

			String regQuery = "SELECT * FROM regions;";
			ResultSet regres = dbManage.sqlQuery(regQuery);

			int arrCount = -1;
			while (regres.next()) {
				arrCount++;
				if (arrCount > 0) {

					regionx1[arrCount - 1] = regres.getDouble("x1");
					regionx2[arrCount - 1] = regres.getDouble("x2");
					regiony1[arrCount - 1] = regres.getDouble("y1");
					regiony2[arrCount - 1] = regres.getDouble("y2");
					regionz1[arrCount - 1] = regres.getDouble("z1");
					regionz2[arrCount - 1] = regres.getDouble("z2");
					exceptionCount[arrCount - 1] = regres.getByte("exceptions");
					regionWelcomeMsg[arrCount - 1] = regres
							.getString("welcomemsg");
					regionLeaveMsg[arrCount - 1] = regres.getString("leavemsg");
					regionOwner[arrCount - 1] = regres.getString("owner");
					// log.info("Owner = " + regionOwner[arrCount - 1]);
					movementFactor[arrCount - 1] = regres
							.getInt("movementfactor");
					healthgenFactor[arrCount - 1] = regres
							.getInt("healthregen");
					regionWorldName[arrCount - 1] = regres.getString("world");
					regionNames[arrCount - 1] = regres.getString("regionname")
							.toLowerCase();
					lsps[arrCount - 1] = regres.getInt("lsps");
					
					
		
					
					RegiosScheduler.lspsCounter.put(arrCount, 0);

					if (regionWorldName[arrCount - 1]
							.equalsIgnoreCase("gjdkgdmh")
							&& (regionOwner[arrCount - 1]
									.equalsIgnoreCase("fgvdfh") || regionOwner[arrCount - 1]
									.equalsIgnoreCase(""))) {
						regionCount -= 1;
					}
					
					
					warpLocations[arrCount - 1] = parseLocation(regres.getString("warps"), regionWorldName[arrCount - 1]);
					

					if (regres.getByte("showwelcome") == 1) {
						regionWelcome[arrCount - 1] = true;
					} else {
						regionWelcome[arrCount - 1] = false;
					}
					if (regres.getByte("showleave") == 1) {
						regionLeave[arrCount - 1] = true;
					} else {
						regionLeave[arrCount - 1] = false;
					}
					if (regres.getByte("protected") == 1) {
						regionProtected[arrCount - 1] = true;
					} else {
						regionProtected[arrCount - 1] = false;
					}
					if (regres.getByte("prevententry") == 1) {
						regionPreventEntry[arrCount - 1] = true;
					} else {
						regionPreventEntry[arrCount - 1] = false;
					}
					if (regres.getByte("preventexit") == 1) {
						regionPreventExit[arrCount - 1] = true;
					} else {
						regionPreventExit[arrCount - 1] = false;
					}
					if (regres.getByte("healthenabled") == 1) {
						regionHealthEnabled[arrCount - 1] = true;
					} else {
						regionHealthEnabled[arrCount - 1] = false;
					}
					if (regres.getByte("pvp") == 1) {
						regionPVPEnabled[arrCount - 1] = true;
					} else {
						regionPVPEnabled[arrCount - 1] = false;
					}
					if (regres.getByte("prohibitedmobs") == 1) {
						regionCreaturesSpawn[arrCount - 1] = true;
					} else {
						regionCreaturesSpawn[arrCount - 1] = false;
					}
					if (regres.getByte("pvp") == 1) {
						regionPVPEnabled[arrCount - 1] = true;
					} else {
						regionPVPEnabled[arrCount - 1] = false;
					}

					if (dbManage.checkTable(regionNames[arrCount - 1])) {
						// log.info("Region has exceptions = " +
						// regionNames[arrCount - 1]);
						String internalQuery = "SELECT * FROM "
								+ regionNames[arrCount - 1] + ";";
						ResultSet internalRes = dbManage
								.sqlQuery(internalQuery);
						int exC = 0;
						while (internalRes.next()) {
							exC++;
							exceptionCount[arrCount - 1]++;
							regionExceptions[arrCount - 1][exC - 1] = internalRes
									.getString("exception");
							// log.info("Exception " + exC + " at index " +
							// (arrCount - 1) + "= " + regionExceptions[arrCount
							// - 1][exC - 1]);
						}
					}

					// log.info("Exceptions = " + exceptionCount[arrCount - 1]);

					/*
					 * log.info("______________________________________________")
					 * ; log.info("X1 = " + regionx1[arrCount -1]);
					 * log.info("X2 = " + regionx2[arrCount -1]);
					 * log.info("Y1 = " + regiony1[arrCount -1]);
					 * log.info("Y2 = " + regiony2[arrCount -1]);
					 * log.info("Z1 = " + regionz1[arrCount -1]);
					 * log.info("Z2 = " + regionz2[arrCount -1]);
					 * log.info("Owner = " + regionOwner[arrCount - 1]);
					 * 
					 * log.info("World Name = " + regionWorldName[arrCount -
					 * 1]); log.info("Preventing Entry = " +
					 * regionPreventEntry[arrCount -1]); log.info("Protected = "
					 * + regionProtected[arrCount -1]);
					 * log.info("Region Welcome = " + regionWelcome[arrCount
					 * -1]); log.info("regionName = " + regionNames[arrCount
					 * -1]); log.info("Welcome Message = " +
					 * regionWelcomeMsg[arrCount -1]);
					 * log.info("______________________________________________"
					 * );
					 */
				}

			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			log.severe("[Regios] - v" + Regios.currentVersion + "_" + Regios.currentSubVersion
					+ " - Unknown error whilst loading SQL tables!");
		}

	}

	public static void loadConfiguration() {
		configReload.load();
		selectionToolID = configReload.getInt(
				"RegioGeneralConfiguration.SelectionToolID", 286);
		configReload.save();
	}

	public static void reloadConfiguration(Player player) {
		configReload.load();
		selectionToolID = configReload.getInt(
				"RegioGeneralConfiguration.SelectionToolID", 286);
		configReload.save();
		player.sendMessage(ChatColor.GREEN + "Configuration reloaded.");
	}

	public static boolean doesRegionExist(String regionName, Player player) {

		for (int p = 1; p <= regionCount; p++) {
			if (regionName.equalsIgnoreCase(regionNames[p - 1])) {
				regionIndex = p - 1;
				return true;
			}
		}

		return false;
	}

	public static void updateDatabase(String regionName, Player player) {
		double x1 = RegiosPlayerListener.regionFirstPointLocation.get(player)
				.getX(), x2 = RegiosPlayerListener.regionSecondPointLocation
				.get(player).getX(), y1 = RegiosPlayerListener.regionFirstPointLocation
				.get(player).getY(), y2 = RegiosPlayerListener.regionSecondPointLocation
				.get(player).getY(), z1 = RegiosPlayerListener.regionFirstPointLocation
				.get(player).getZ(), z2 = RegiosPlayerListener.regionSecondPointLocation
				.get(player).getZ();
		String wMSG = ChatColor.GREEN + "Welcome to region : " + regionName, lMSG = ChatColor.RED
				+ "You have left region : " + regionName;

		String world = player.getWorld().getName();

		String updateDB = ("INSERT INTO regions (regionname, x1, x2, y1, y2, z1, z2, showwelcome, showleave, protected, prevententry, preventexit, welcomemsg, leavemsg, owner, healthenabled, exceptions, movementfactor, pvp, healthregen, prohibitedmobs, world, lsps, warps) values ('"
				+ regionName.toLowerCase()
				+ "', '"
				+ x1
				+ "', '"
				+ x2
				+ "', '"
				+ y1
				+ "', '"
				+ y2
				+ "', '"
				+ z1
				+ "', '"
				+ z2
				+ "', '1', '1', '0', '0', '0', '"
				+ wMSG
				+ "', '"
				+ lMSG
				+ "', '"
				+ formatPlayer(player)
				+ "', '1', '0', '1', '0', '0', '0', '" + world + "', '0', 'world@0@0@0@0@0');");
		dbManage.insertQuery(updateDB);

		player.sendMessage(ChatColor.GREEN + "New Region "
				+ ChatColor.LIGHT_PURPLE + regionName + ChatColor.GREEN
				+ " created successfully!");
		getRegionCount();
		// log.info("" + regionCount);
		rereadDatabaseAddition(regionCount, player, regionName);

	}

	public static void prohibitMobSpawns(String region, Player player,
			boolean mobVal) {
		doesRegionExist(region, player);
		if (regionCreaturesSpawn[regionIndex] == false && mobVal == false) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ region + ChatColor.RED
					+ " already has mob spawns set to false.");
			return;
		}
		if (regionCreaturesSpawn[regionIndex] == true && mobVal == true) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ region + ChatColor.RED
					+ " already has mob spawns set to true.");
			return;
		}
		if (regionCreaturesSpawn[regionIndex] != mobVal) {
			if (mobVal) {
				String updateQuery = "UPDATE regions SET prohibitedmobs='1' WHERE prohibitedmobs='0' AND regionname='"
						+ region.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionCreaturesSpawn[regionIndex] = true;
				player.sendMessage(ChatColor.GREEN
						+ "Mobs & Creatures will now spawn in "
						+ ChatColor.LIGHT_PURPLE + region.toLowerCase());
			} else {
				String updateQuery = "UPDATE regions SET prohibitedmobs='0' WHERE prohibitedmobs='1' AND regionname='"
						+ region.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionCreaturesSpawn[regionIndex] = false;
				player.sendMessage(ChatColor.GREEN
						+ "Mobs & Creatures will no longer spawn in "
						+ ChatColor.LIGHT_PURPLE + region.toLowerCase());
			}
		}
	}

	public static void inheritProperties(String targetRegion,
			String regionToInherit, Player player) {
		doesRegionExist(targetRegion, player);
		int inheritanceIndex = regionIndex;
		doesRegionExist(regionToInherit, player);
		int copyIndex = regionIndex;

		boolean sw = regionWelcome[copyIndex], sl = regionLeave[copyIndex], pr = regionProtected[copyIndex], pen = regionPreventEntry[copyIndex], pex = regionPreventExit[copyIndex], healthEnabled = regionHealthEnabled[copyIndex], pvpSet = regionPVPEnabled[copyIndex], prohibMob = regionCreaturesSpawn[copyIndex];

		int exceptions = exceptionCount[copyIndex], LS = lsps[copyIndex];
		double mf = movementFactor[copyIndex];
		String world = regionWorldName[copyIndex], wMSG = regionWelcomeMsg[copyIndex], lMSG = regionLeaveMsg[copyIndex];

		Byte v1, v2, v3, v4, v5, v6, v7, v8;

		if (sw) {
			v1 = 1;
		} else {
			v1 = 0;
		}
		if (sl) {
			v2 = 1;
		} else {
			v2 = 0;
		}
		if (pr) {
			v3 = 1;
		} else {
			v3 = 0;
		}
		if (pen) {
			v4 = 1;
		} else {
			v4 = 0;
		}
		if (pex) {
			v5 = 1;
		} else {
			v5 = 0;
		}
		if (healthEnabled) {
			v6 = 1;
		} else {
			v6 = 0;
		}
		if (pvpSet) {
			v7 = 1;
		} else {
			v7 = 0;
		}
		if (prohibMob) {
			v8 = 1;
		} else {
			v8 = 0;
		}
		// String initialiseDB = ("INSERT INTO regions (regionname, x1, x2, y1,
		// y2, z1, z2, showwelcome, showleave, protected, prevententry,
		// preventexit,
		// welcomemsg, leavemsg, owner, healthenabled, exceptions,
		// movementfactor, pvp, healthregen, prohibitedmobs, world, lsps) values

		String cloneValues = "UPDATE regions SET showwelcome='" + v1
				+ "', showleave='" + v2 + "', protected='" + v3 + "',"
				+ " prevententry='" + v4 + "', preventexit='" + v5
				+ "', healthenabled='" + v6 + "'," + " pvp='" + v7
				+ "', prohibitedmobs='" + v8 + "', exceptions='" + exceptions
				+ "', " + " movementfactor='" + mf + "', lsps='" + LS
				+ "', world='" + world + "', welcomemsg='" + wMSG + "',"
				+ " leavemsg='" + lMSG + "', warps='" + warpLocations[copyIndex] + "' WHERE regionname='" + targetRegion.toLowerCase()
				+ "';";

		dbManage.updateQuery(cloneValues);
		player.sendMessage(ChatColor.GREEN + "Region " + ChatColor.BLUE
				+ targetRegion + ChatColor.GREEN
				+ " has inherited the properties of region " + ChatColor.BLUE
				+ regionToInherit);
		
		rereadDatabaseAddition(copyIndex + 1, player, targetRegion.toLowerCase());
	}
	
	public static void renameRegion(String oldName, String newName, Player player){
		String rename = "UPDATE regions SET regionname='" + newName.toLowerCase() + "' WHERE regionname='" + oldName.toLowerCase() + "';";
		dbManage.updateQuery(rename);
		player.sendMessage(ChatColor.GREEN + "Region " + ChatColor.BLUE + oldName.toLowerCase() + ChatColor.GREEN + " renamed successfully to " + ChatColor.BLUE + newName.toLowerCase());
		regionNames[regionIndex] = newName.toLowerCase();
		if(regionWelcomeMsg[regionIndex].equalsIgnoreCase("Welcome to region : " + oldName.toLowerCase())){
			regionWelcomeMsg[regionIndex] = "Welcome to region : " + newName.toLowerCase();
		}
		if(regionLeaveMsg[regionIndex].equalsIgnoreCase("You have left region : " + oldName.toLowerCase())){
			regionLeaveMsg[regionIndex] = "You have left region : " + newName.toLowerCase();
		}
		
	}

	public static void rereadDatabaseAddition(int i, Player player,
			String regionName) {

		try {
			String query = "SELECT COUNT(*) as count FROM regions;";
			ResultSet result = dbManage.sqlQuery(query);
			count = result.getInt("count");

			String regQuery = "SELECT * FROM regions WHERE regionname='"
					+ regionName.toLowerCase() + "';";
			ResultSet regres = dbManage.sqlQuery(regQuery);

			regionx1[i - 1] = regres.getDouble("x1");
			regionx2[i - 1] = regres.getDouble("x2");
			regiony1[i - 1] = regres.getDouble("y1");
			regiony2[i - 1] = regres.getDouble("y2");
			regionz1[i - 1] = regres.getDouble("z1");
			regionz2[i - 1] = regres.getDouble("z2");
			exceptionCount[i - 1] = regres.getByte("exceptions");
			regionWelcomeMsg[i - 1] = regres.getString("welcomemsg");
			regionLeaveMsg[i - 1] = regres.getString("leavemsg");
			regionOwner[i - 1] = regres.getString("owner");
			movementFactor[i - 1] = regres.getInt("movementfactor");
			healthgenFactor[i - 1] = regres.getInt("healthregen");
			regionWorldName[i - 1] = regres.getString("world");
			regionNames[i - 1] = regres.getString("regionname");
			lsps[i - 1] = regres.getInt("lsps");
			RegiosScheduler.lspsCounter.put(i, 0);
			
			warpLocations[i - 1] = new Location(Regios.server.getWorld(regionWorldName[i - 1]), 0, 0, 0, 0, 0);

			if (regres.getByte("showwelcome") == 1) {
				regionWelcome[i - 1] = true;
			} else {
				regionWelcome[i - 1] = false;
			}
			if (regres.getByte("showleave") == 1) {
				regionLeave[i - 1] = true;
			} else {
				regionLeave[i - 1] = false;
			}
			if (regres.getByte("protected") == 1) {
				regionProtected[i - 1] = true;
			} else {
				regionProtected[i - 1] = false;
			}
			if (regres.getByte("prevententry") == 1) {
				regionPreventEntry[i - 1] = true;
			} else {
				regionPreventEntry[i - 1] = false;
			}
			if (regres.getByte("preventexit") == 1) {
				regionPreventExit[i - 1] = true;
			} else {
				regionPreventExit[i - 1] = false;
			}
			if (regres.getByte("healthenabled") == 1) {
				regionHealthEnabled[i - 1] = true;
			} else {
				regionHealthEnabled[i - 1] = false;
			}
			if (regres.getByte("pvp") == 1) {
				regionPVPEnabled[i - 1] = true;
			} else {
				regionPVPEnabled[i - 1] = false;
			}
			if (regres.getByte("prohibitedmobs") == 1) {
				regionCreaturesSpawn[i - 1] = true;
			} else {
				regionCreaturesSpawn[i - 1] = false;
			}
			if (regres.getByte("pvp") == 1) {
				regionPVPEnabled[i - 1] = true;
			} else {
				regionPVPEnabled[i - 1] = false;
			}

			/*
			 * log.info("Reload___________________________________");
			 * log.info("X1 = " + regionx1[i -1]); log.info("X2 = " + regionx2[i
			 * -1]); log.info("Y1 = " + regiony1[i -1]); log.info("Y2 = " +
			 * regiony2[i -1]); log.info("Z1 = " + regionz1[i -1]);
			 * log.info("Z2 = " + regionz2[i -1]); log.info("Owner = " +
			 * regionOwner[i - 1]); log.info("Exceptions = " + exceptionCount[i
			 * - 1]); log.info("World Name = " + regionWorldName[i - 1]);
			 * log.info("Preventing Entry = " + regionPreventEntry[i -1]);
			 * log.info("Protected = " + regionProtected[i -1]);
			 * log.info("Region Welcome = " + regionWelcome[i -1]);
			 * log.info("regionName = " + regionNames[i -1]);
			 * log.info("Welcome Message = " + regionWelcomeMsg[i -1]);
			 * log.info("______________________________________________");
			 */

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
                
                if(player != null){

		player.sendMessage(ChatColor.GREEN + "Database reloaded region "
				+ ChatColor.LIGHT_PURPLE + regionName.toLowerCase()
				+ ChatColor.GREEN + " successfully!");
                } else {
                    log.info("[Regios][GUI] Update Successful!");
                }
	}

	public static boolean isOwner(String region, Player player) {
		if (doesRegionExist(region, player)) {
			if ((regionOwner[regionIndex].equalsIgnoreCase(formatPlayer(player)))
					|| player.isOp()
					|| (Regios.hasPermissions && (Regios.permissionHandler.has(
							player, "regios.override")))) {
				return true;
			}
		}
		return false;
	}

	public static void pushHealthSetting(Player player, String region,
			boolean mobVal) {
		doesRegionExist(region, player);
		if (regionHealthEnabled[regionIndex] == false && mobVal == false) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ region.toLowerCase() + ChatColor.RED
					+ " already has health set to false.");
			return;
		}
		if (regionHealthEnabled[regionIndex] == true && mobVal == true) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ region.toLowerCase() + ChatColor.RED
					+ " already has health set to true.");
			return;
		}
		if (regionHealthEnabled[regionIndex] != mobVal) {
			if (mobVal) {
				String updateQuery = "UPDATE regions SET healthenabled='1' WHERE healthenabled='0' AND regionname='"
						+ region.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionHealthEnabled[regionIndex] = true;
				player.sendMessage(ChatColor.GREEN + "Any players in region "
						+ ChatColor.LIGHT_PURPLE + region + ChatColor.GREEN
						+ " will have normal health.");
			} else {
				String updateQuery = "UPDATE regions SET healthenabled='0' WHERE healthenabled='1' AND regionname='"
						+ region.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionHealthEnabled[regionIndex] = false;
				player.sendMessage(ChatColor.GREEN + "Any players in region "
						+ ChatColor.LIGHT_PURPLE + region + ChatColor.GREEN
						+ " will have unlimited health.");
			}
		}

	}
	
	public static String constructLocation(Location loc){
		return loc.getWorld() + "@" + loc.getX() + "@" + loc.getY() + "@" + loc.getZ() + "@" + loc.getYaw() + "@" + loc.getPitch();
	}
	
	public static void pushWarpLocation(String region, Player player, Location warp){
		doesRegionExist(region, player);
		String warpString = player.getWorld().getName() + "@" + warp.getX() + "@" + warp.getY() + "@" + warp.getZ() + "@" + warp.getYaw() + "@" + warp.getPitch();
		String updateWarp = "UPDATE regions SET warps='" + warpString + "' WHERE regionname='" + regionNames[regionIndex].toLowerCase() + "';";
		dbManage.updateQuery(updateWarp);
		warpLocations[regionIndex] = warp;
		player.sendMessage(ChatColor.BLUE + "[Regios][Warp] " + ChatColor.GREEN + "Warp created successfully!");
		//player.sendMessage(ChatColor.GREEN + "Warp for region " + ChatColor.LIGHT_PURPLE + region.toLowerCase() + ChatColor.GREEN + " updated successfully to your current coordinates!");
	}

	public static void pushMovementFactor(String region, String type,
			Player player) {
		doesRegionExist(region, player);
		double factor = 1;
		if (type.equalsIgnoreCase("slow")) {
			factor = 1.5;
		}
		if (type.equalsIgnoreCase("normal")) {
			factor = 1;
		}
		String updateQuery = "UPDATE regions SET movementfactor='" + factor
				+ "' WHERE movementfactor='" + movementFactor[regionIndex]
				+ "' AND regionname='" + region.toLowerCase() + "';";
		dbManage.updateQuery(updateQuery);
		movementFactor[regionIndex] = factor;
		player.sendMessage(ChatColor.GREEN
				+ "Movement Factor updated for region : "
				+ ChatColor.LIGHT_PURPLE + region.toLowerCase());
	}

	public static void pushLSPS(String region, int lspsFAC, Player player) {
		doesRegionExist(region, player);
		String updateQuery = "UPDATE regions SET lsps='" + lspsFAC
				+ "' WHERE lsps='" + lsps[regionIndex] + "' AND regionname='"
				+ region.toLowerCase() + "';";
		dbManage.updateQuery(updateQuery);
		lsps[regionIndex] = lspsFAC;
		player.sendMessage(ChatColor.GREEN + "Region " + ChatColor.LIGHT_PURPLE
				+ region.toLowerCase() + ChatColor.GREEN
				+ " will now cast down lightning every " + ChatColor.YELLOW
				+ lspsFAC + " seconds!");
	}

	public static void pushWelcomeMsg(String welcomemsg, Player player,
			String zonename) {
		if (welcomemsg.contains("'")) {
			player.sendMessage(ChatColor.RED + "Illegal Character "
					+ ChatColor.AQUA + "' " + ChatColor.RED
					+ " in Region message.");
			return;
		}
		doesRegionExist(zonename, player);
		String updateQuery = "UPDATE regions SET welcomemsg='"
				+ replaceString(welcomemsg) + "' WHERE welcomemsg='"
				+ regionWelcomeMsg[regionIndex] + "' AND regionname='"
				+ zonename.toLowerCase() + "';";
		dbManage.updateQuery(updateQuery);
		regionWelcomeMsg[regionIndex] = replaceString(welcomemsg);
                if(player != null){
		player.sendMessage(ChatColor.GREEN
				+ "Welcome message successfully updated for region : "
				+ ChatColor.LIGHT_PURPLE + zonename.toLowerCase());
                }
	}

	public static void pushModifyRegionOwner(String newOwner,
			String regionName, Player player) {
		doesRegionExist(regionName, player);
		String updateQuery = "UPDATE regions SET owner='" + newOwner
				+ "' WHERE owner='" + regionOwner[regionIndex]
				+ "' AND regionname='" + regionName.toLowerCase() + "';";
                if(player != null){
		player.sendMessage(ChatColor.GREEN + "Owner for region "
				+ ChatColor.LIGHT_PURPLE + regionName.toLowerCase()
				+ ChatColor.GREEN + " changed from " + ChatColor.RED
				+ regionOwner[regionIndex] + ChatColor.GREEN + " to "
				+ ChatColor.YELLOW + newOwner);
                }
		dbManage.updateQuery(updateQuery);
		regionOwner[regionIndex] = newOwner;
	}

	public static void pushRegenFac(String regionName, int fac, Player player,
			boolean add) {// true equals +, false = -
		doesRegionExist(regionName, player);
		if (!add) {
			fac *= -1;
		}
		String updateQuery = "UPDATE regions SET healthregen='" + fac
				+ "' WHERE healthregen='" + healthgenFactor[regionIndex]
				+ "' AND regionname='" + regionName.toLowerCase() + "';";
		dbManage.updateQuery(updateQuery);
		healthgenFactor[regionIndex] = fac;
		if (add) {
			player.sendMessage(ChatColor.GREEN
					+ "Region H-HPS setting modified to " + ChatColor.GREEN
					+ "+" + ChatColor.LIGHT_PURPLE + fac);
		} else {
			player.sendMessage(ChatColor.GREEN
					+ "Region H-HPS setting modified to "
					+ ChatColor.LIGHT_PURPLE + fac);
		}

		// moreee
	}

	public static void pushLeaveMsg(String Leavemsg, Player player,
			String zonename) {
		if (Leavemsg.contains("'")) {
                    if(player != null){
			player.sendMessage(ChatColor.RED + "Illegal Character "
					+ ChatColor.AQUA + "' " + ChatColor.RED
					+ " in Region message.");
			return;
                    }
		}
		doesRegionExist(zonename, player);
		String updateQuery = "UPDATE regions SET leavemsg='"
				+ replaceString(Leavemsg) + "' WHERE leavemsg='"
				+ regionLeaveMsg[regionIndex] + "' AND regionname='"
				+ zonename.toLowerCase() + "';";
		dbManage.updateQuery(updateQuery);
		regionLeaveMsg[regionIndex] = replaceString(Leavemsg);
                if(player != null){
		player.sendMessage(ChatColor.GREEN
				+ "Leave message successfully updated for region : "
				+ ChatColor.LIGHT_PURPLE + zonename.toLowerCase());
                }
	}

	public static void pushEnabledWelcome(boolean enabledValue, Player player,
			String zonename) {
		doesRegionExist(zonename, player);
		if (regionWelcome[regionIndex] == false && enabledValue == false) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " already has welcome msg set to false.");
			return;
		}
		if (regionWelcome[regionIndex] == true && enabledValue == true) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " already has welcome msg set to true.");
			return;
		}
		if (regionWelcome[regionIndex] != enabledValue) {
			if (enabledValue) {
				String updateQuery = "UPDATE regions SET showwelcome='1' WHERE showwelcome='0' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionWelcome[regionIndex] = true;
				player.sendMessage(ChatColor.GREEN
						+ "Welcome message for region " + ChatColor.YELLOW
						+ zonename + ChatColor.GREEN + " enabled");
			} else {
				String updateQuery = "UPDATE regions SET showwelcome='0' WHERE showwelcome='1' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionWelcome[regionIndex] = false;
				player.sendMessage(ChatColor.RED
						+ "Welcome message for region "
						+ ChatColor.LIGHT_PURPLE + zonename + ChatColor.RED
						+ " disabled");
			}
		}

	}

	public static void pushPVPSetting(boolean enabledValue, Player player,
			String zonename) {
		doesRegionExist(zonename, player);
		if (regionPVPEnabled[regionIndex] == false && enabledValue == false) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " already has PVP set to false.");
			return;
		}
		if (regionPVPEnabled[regionIndex] == true && enabledValue == true) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " already has PVP set to true.");
			return;
		}
		if (regionPVPEnabled[regionIndex] != enabledValue) {
			if (enabledValue) {
				String updateQuery = "UPDATE regions SET pvp='1' WHERE pvp='0' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionPVPEnabled[regionIndex] = true;
				player.sendMessage(ChatColor.GREEN + "PVP for region "
						+ ChatColor.YELLOW + zonename.toLowerCase()
						+ ChatColor.GREEN + " enabled");
			} else {
				String updateQuery = "UPDATE regions SET pvp='0' WHERE pvp='1' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionPVPEnabled[regionIndex] = false;
				player.sendMessage(ChatColor.RED + "PVP for region "
						+ ChatColor.LIGHT_PURPLE + zonename.toLowerCase()
						+ ChatColor.RED + " disabled");
			}
		}

	}

	public static void pushEnabledLeave(boolean enabledValue, Player player,
			String zonename) {
		doesRegionExist(zonename, player);
		if (regionLeave[regionIndex] == false && enabledValue == false) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " already has leave msg set to false.");
			return;
		}
		if (regionLeave[regionIndex] == true && enabledValue == true) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " already has leave msg set to true.");
			return;
		}
		if (regionLeave[regionIndex] != enabledValue) {
			if (enabledValue) {
				String updateQuery = "UPDATE regions SET showleave='1' WHERE showleave='0' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionLeave[regionIndex] = true;
				player.sendMessage(ChatColor.GREEN
						+ "Leave message for region " + ChatColor.YELLOW
						+ zonename.toLowerCase() + ChatColor.GREEN + " enabled");
			} else {
				String updateQuery = "UPDATE regions SET showleave='0' WHERE showleave='1' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionLeave[regionIndex] = false;
				player.sendMessage(ChatColor.RED + "Leave message for region "
						+ ChatColor.LIGHT_PURPLE + zonename.toLowerCase()
						+ ChatColor.RED + " disabled");
			}
		}
	}

	public static void pushRegionProtected(boolean protectedValue,
			Player player, String zonename) {
		doesRegionExist(zonename, player);
		if (regionProtected[regionIndex] == false && protectedValue == false) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " is not protected.");
			return;
		}
		if (regionProtected[regionIndex] == true && protectedValue == true) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " is already protected.");
			return;
		}
		if (regionProtected[regionIndex] != protectedValue) {
			if (protectedValue) {
				String updateQuery = "UPDATE regions SET protected='1' WHERE protected='0' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionProtected[regionIndex] = true;
				player.sendMessage(ChatColor.GREEN + "Protection for region "
						+ ChatColor.YELLOW + zonename.toLowerCase()
						+ ChatColor.GREEN + " enabled.");
			} else {
				String updateQuery = "UPDATE regions SET protected='0' WHERE protected='1' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionProtected[regionIndex] = false;
				player.sendMessage(ChatColor.RED + "Protection for region "
						+ ChatColor.LIGHT_PURPLE + zonename.toLowerCase()
						+ ChatColor.RED + " disabled.");
			}
		}
	}

	public static void pushRegionPreventEntry(boolean protectedValue,
			Player player, String zonename) {
		doesRegionExist(zonename, player);
		if (regionPreventEntry[regionIndex] == false && protectedValue == false) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " is not preventing entry.");
			return;
		}
		if (regionPreventEntry[regionIndex] == true && protectedValue == true) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " is already preventing entry.");
			return;
		}
		if (regionPreventEntry[regionIndex] != protectedValue) {
			if (!protectedValue) {
				String updateQuery = "UPDATE regions SET prevententry='0' WHERE prevententry='1' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionPreventEntry[regionIndex] = false;
				player.sendMessage(ChatColor.RED
						+ "Entry protection for region " + ChatColor.YELLOW
						+ zonename.toLowerCase() + ChatColor.RED + " disabled.");
			} else {
				String updateQuery = "UPDATE regions SET prevententry='1' WHERE prevententry='0' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionPreventEntry[regionIndex] = true;
				player.sendMessage(ChatColor.GREEN
						+ "Entry protection for region "
						+ ChatColor.LIGHT_PURPLE + zonename.toLowerCase()
						+ ChatColor.GREEN + " enabled.");
			}
		}
	}

	public static void pushRegionPreventExit(boolean protectedValue,
			Player player, String zonename) {
		doesRegionExist(zonename, player);
		if (regionPreventExit[regionIndex] == false && protectedValue == false) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " is not preventing exit.");
			return;
		}
		if (regionPreventExit[regionIndex] == true && protectedValue == true) {
			player.sendMessage(ChatColor.RED + "The region " + ChatColor.YELLOW
					+ zonename.toLowerCase() + ChatColor.RED
					+ " is already preventing exit.");
			return;
		}
		if (regionPreventExit[regionIndex] != protectedValue) {
			if (!protectedValue) {
				String updateQuery = "UPDATE regions SET preventexit='0' WHERE preventexit='1' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionPreventExit[regionIndex] = false;
				player.sendMessage(ChatColor.RED
						+ "Exit protection for region " + ChatColor.YELLOW
						+ zonename.toLowerCase() + ChatColor.RED + " disabled.");
			} else {
				String updateQuery = "UPDATE regions SET preventexit='1' WHERE preventexit='0' AND regionname='"
						+ zonename.toLowerCase() + "';";
				dbManage.updateQuery(updateQuery);
				regionPreventExit[regionIndex] = true;
				player.sendMessage(ChatColor.GREEN
						+ "Exit protection for region "
						+ ChatColor.LIGHT_PURPLE + zonename.toLowerCase()
						+ ChatColor.GREEN + " enabled.");
			}
		}
	}

	public static void pushExpandRegionMAX(String regionName,
			double expandHeight, Player player) {
		doesRegionExist(regionName, player);
		String updateQuery = "UPDATE regions SET y1='0', y2='128' WHERE y1='"
				+ regiony1[regionIndex] + "' AND y2 = '"
				+ regiony2[regionIndex] + "' AND regionname='"
				+ regionName.toLowerCase() + "';";
		dbManage.updateQuery(updateQuery);
		regiony1[regionIndex] = 0;
		regiony2[regionIndex] = 128;
		player.sendMessage(ChatColor.GREEN + "Region " + ChatColor.YELLOW
				+ regionName.toLowerCase() + ChatColor.GREEN
				+ " was expanded from bedrock to sky limit.");
	}

	public static void pushAddToExceptions(String regionName, Player player,
			String toAdd) {
		doesRegionExist(regionName, player);
		boolean matchFound = false;
		for (int a = 1; a <= exceptionCount[regionIndex]; a++) {
			if (regionExceptions[regionIndex][a - 1].equalsIgnoreCase(toAdd)) {
				matchFound = true;
			}
		}

		if (matchFound) {
			player.sendMessage(ChatColor.RED + "Player " + ChatColor.YELLOW
					+ toAdd + ChatColor.RED
					+ " is already an exception for the region "
					+ ChatColor.YELLOW + regionName.toLowerCase());
			return;
		}

		if (!dbManage.checkTable(regionName)) {
			String query = ("CREATE TABLE " + regionName.toLowerCase() + " ( 'id' INTEGER PRIMARY KEY AUTOINCREMENT, 'exception' VARCHAR(70) );");
			dbManage.createTable(query); // owner, healthenabled, exceptions,
											// movementfactor, pvp, healthregen,
											// prohibitedmobs, world)
			String insertQuery = "INSERT INTO " + regionName.toLowerCase()
					+ " (exception) values ('" + toAdd + "');";
			dbManage.insertQuery(insertQuery);
		} else {
			String insertQuery = "INSERT INTO " + regionName.toLowerCase()
					+ " (exception) values ('" + toAdd + "');";
			dbManage.insertQuery(insertQuery);
		}
		exceptionCount[regionIndex] += 1;
		regionExceptions[regionIndex][exceptionCount[regionIndex] - 1] = toAdd;
		// log.info("Player added to exceptions = " +
		// regionExceptions[regionIndex][exceptionCount[regionIndex] - 1]);
		player.sendMessage(ChatColor.GREEN + "Player " + ChatColor.LIGHT_PURPLE
				+ toAdd + ChatColor.GREEN
				+ " is now an exception for the region "
				+ ChatColor.LIGHT_PURPLE + regionName.toLowerCase());
	}

	public static void pushRemoveFromExceptions(String regionName,
			Player player, String toRemove) {
		doesRegionExist(regionName, player);
		int matchIndex = 0;
		boolean matchFound = false;
		for (int a = 1; a <= exceptionCount[regionIndex]; a++) {
			if (regionExceptions[regionIndex][a - 1].equalsIgnoreCase(toRemove)) {
				matchIndex = a - 1;
				matchFound = true;
			}
		}

		if (!matchFound) {
			player.sendMessage(ChatColor.RED + "Player " + ChatColor.YELLOW
					+ toRemove + ChatColor.RED
					+ " is not an exception for the region " + ChatColor.YELLOW
					+ regionName.toLowerCase());
			return;
		}

		String query = "DELETE FROM " + regionName.toLowerCase()
				+ " WHERE exception='" + toRemove + "';";
		dbManage.deleteQuery(query);

		player.sendMessage(ChatColor.GREEN + "Player " + ChatColor.RED
				+ toRemove + ChatColor.GREEN
				+ " is no longer an exception for the region "
				+ ChatColor.LIGHT_PURPLE + regionName.toLowerCase());
		/*for (int e = 1; e <= exceptionCount[regionIndex]; e++) {
			String formatQuery = "UPDATE " + regionName.toLowerCase()
					+ " SET id ='" + e + "' WHERE id='" + (e + 1) + "';";
			dbManage.updateQuery(formatQuery);
		}*/
		player.sendMessage(ChatColor.GREEN
				+ "SQL tables reformatted successfully.");

		exceptionCount[regionIndex] -= 1;
		regionExceptions[regionIndex][matchIndex] = "null";
	}

	private static String replaceString(String message) {
		message= message.replaceAll("%BLACK%", "\u00A70");
		message= message.replaceAll("\\&0", "\u00A70");
		message= message.replaceAll("\\$0", "\u00A70");

		message= message.replaceAll("%DBLUE%", "\u00A71");
		message= message.replaceAll("\\&1", "\u00A71");
		message= message.replaceAll("\\$1", "\u00A71");

		message= message.replaceAll("%DGREEN%", "\u00A72");
		message= message.replaceAll("\\&2", "\u00A72");
		message= message.replaceAll("\\$2", "\u00A72");

		message= message.replaceAll("%DTEAL%", "\u00A73");
		message= message.replaceAll("\\&3", "\u00A73");
		message= message.replaceAll("\\$3", "\u00A73");

		message= message.replaceAll("%DRED%", "\u00A74");
		message= message.replaceAll("\\&4", "\u00A74");
		message= message.replaceAll("\\$4", "\u00A74");

		message= message.replaceAll("%PURPLE%", "\u00A75");
		message= message.replaceAll("\\&5", "\u00A75");
		message= message.replaceAll("\\$5", "\u00A75");

		message= message.replaceAll("%GOLD%", "\u00A76");
		message= message.replaceAll("\\&6", "\u00A76");
		message= message.replaceAll("\\$6", "\u00A76");

		message= message.replaceAll("%GREY%", "\u00A77");
		message= message.replaceAll("\\&7", "\u00A77");
		message= message.replaceAll("\\$7", "\u00A77");

		message= message.replaceAll("%DGREY%", "\u00A78");
		message= message.replaceAll("\\&8", "\u00A78");
		message= message.replaceAll("\\$8", "\u00A78");

		message= message.replaceAll("%BLUE%", "\u00A79");
		message= message.replaceAll("\\&9", "\u00A79");
		message= message.replaceAll("\\$9", "\u00A79");

		message= message.replaceAll("%BGREEN%", "\u00A7a");
		message= message.replaceAll("\\&A", "\u00A7a");
		message= message.replaceAll("\\$A", "\u00A7a");

		message= message.replaceAll("%TEAL%", "\u00A7b");
		message= message.replaceAll("\\&B", "\u00A7b");
		message= message.replaceAll("\\$B", "\u00A7b");

		message= message.replaceAll("%RED%", "\u00A7c");
		message= message.replaceAll("\\&C", "\u00A7c");
		message= message.replaceAll("\\$C", "\u00A7c");

		message= message.replaceAll("%PINK%", "\u00A7d");
		message= message.replaceAll("\\&D", "\u00A7d");
		message= message.replaceAll("\\$D", "\u00A7d");

		message= message.replaceAll("%YELLOW%", "\u00A7e");
		message= message.replaceAll("\\&E", "\u00A7e");
		message= message.replaceAll("\\$E", "\u00A7e");

		message= message.replaceAll("%WHITE%", "\u00A7f");
		message= message.replaceAll("\\&F", "\u00A7f");
		message= message.replaceAll("\\$F", "\u00A7f");

		return message;
	}
	
	public static void expandRegionDown(String region, int value, Player player){
		doesRegionExist(region, player);
		boolean y1Smaller = true;
		
		if(regiony1[regionIndex] > regiony2[regionIndex] || regiony1[regionIndex] == regiony2[regionIndex]){
			y1Smaller = false;
		}
		
		if(y1Smaller){
			if(value - regiony1[regionIndex] < 0){
				player.sendMessage(ChatColor.RED + "The height of your region cannot be smaller than 0!");
				return;
			}	
		} else {
			if(value - regiony2[regionIndex] < 0){
				player.sendMessage(ChatColor.RED + "The height of your region cannot be smaller than 0!");
				return;
			}
		}
		
		String updateQuery;
		if(y1Smaller){
			updateQuery = "UPDATE regions SET y1='" + (regiony1[regionIndex] - value) + "' WHERE y1='" + regiony1[regionIndex] + "' AND regionname='" + region.toLowerCase() + "';";
			regiony1[regionIndex] = (regiony1[regionIndex] - value);
			dbManage.updateQuery(updateQuery);
			player.sendMessage(ChatColor.GREEN + "Region " + ChatColor.LIGHT_PURPLE + region.toLowerCase() + ChatColor.GREEN + " height expanded from " + ChatColor.BLUE + regiony1[regionIndex] + ChatColor.GREEN + " to " + ChatColor.BLUE + (regiony1[regionIndex] - value));
		} else {
			updateQuery = "UPDATE regions SET y2='" + (regiony2[regionIndex] - value) + "' WHERE y2='" + regiony2[regionIndex] + "' AND regionname='" + region.toLowerCase() + "';";
			dbManage.updateQuery(updateQuery);
			regiony2[regionIndex] = (regiony2[regionIndex] - value);
			player.sendMessage(ChatColor.GREEN + "Region " + ChatColor.LIGHT_PURPLE + region.toLowerCase() + ChatColor.GREEN + " height expanded from " + ChatColor.BLUE + regiony2[regionIndex] + ChatColor.GREEN + " to " + ChatColor.BLUE + (regiony2[regionIndex] - value));
		}
		
		
		
	}
	
	public static void expandRegionUp(String region, int value, Player player){
		doesRegionExist(region, player);
		boolean y1Bigger = false;
		
		if(regiony1[regionIndex] > regiony2[regionIndex] || regiony1[regionIndex] == regiony2[regionIndex]){
			y1Bigger = true;
		}
		
		if(y1Bigger){
			if(value + regiony1[regionIndex] > 128){
				player.sendMessage(ChatColor.RED + "The height of your region cannot be larger than 128!");
				return;
			}	
		} else {
			if(value + regiony2[regionIndex] > 128){
				player.sendMessage(ChatColor.RED + "The height of your region cannot be larger than 128!");
				return;
			}
		}
		
		String updateQuery;
		if(y1Bigger){
			updateQuery = "UPDATE regions SET y1='" + (regiony1[regionIndex] + value) + "' WHERE y1='" + regiony1[regionIndex] + "' AND regionname='" + region.toLowerCase() + "';";
			regiony1[regionIndex] = (regiony1[regionIndex] + value);
			dbManage.updateQuery(updateQuery);
			player.sendMessage(ChatColor.GREEN + "Region " + ChatColor.LIGHT_PURPLE + region.toLowerCase() + ChatColor.GREEN + " height expanded from " + ChatColor.BLUE + regiony1[regionIndex] + ChatColor.GREEN + " to " + ChatColor.BLUE + (regiony1[regionIndex] + value));
		} else {
			updateQuery = "UPDATE regions SET y2='" + (regiony2[regionIndex] + value) + "' WHERE y2='" + regiony2[regionIndex] + "' AND regionname='" + region.toLowerCase() + "';";
			regiony2[regionIndex] = (regiony2[regionIndex] + value);
			dbManage.updateQuery(updateQuery);
			player.sendMessage(ChatColor.GREEN + "Region " + ChatColor.LIGHT_PURPLE + region.toLowerCase() + ChatColor.GREEN + " height expanded from " + ChatColor.BLUE + regiony2[regionIndex] + ChatColor.GREEN + " to " + ChatColor.BLUE + (regiony2[regionIndex] + value));
		}
		
		
		
	}

	public static void deleteRegion(String regionName, Player player) {
		doesRegionExist(regionName, player);

		// String initialiseDB =
		// ("UPDATE regions SET regionname='gjdkgdmh', x1='0', x2='0', y1='0', y2='0', z1='0', z2='0', showwelcome='0', showleave='0', protected='0', prevententry='0', preventexit='0', welcomemsg='', leavemsg='', owner='fgvdfh', healthenabled='0', exceptions='0', movementfactor='1', pvp='0', healthregen='0', prohibitedmobs='0', world='gjdkgdmh', lsps='0' WHERE regionname='"
		// + regionName + "' AND owner='" + regionOwner[regionIndex] + "';");
		String delQuery = "DELETE FROM regions WHERE regionname='"
				+ regionName.toLowerCase() + "' AND owner='"
				+ regionOwner[regionIndex] + "';";
		dbManage.deleteQuery(delQuery);

		player.sendMessage(ChatColor.GREEN + "Region " + ChatColor.LIGHT_PURPLE
				+ regionName.toLowerCase() + ChatColor.GREEN
				+ " deleted successfully.");

		/*
		 * for(int e = 1; e <= regionCount - 1; e++){ String formatQuery =
		 * "UPDATE regions SET id ='" + e + "' WHERE id='" + (e + 1) + "';";
		 * dbManage.updateQuery(formatQuery); }
		 */

		if (dbManage.checkTable(regionName)) {
			String dellQuery = "DROP TABLE " + regionName.toLowerCase() + ";";
			dbManage.deleteQuery(dellQuery);
		}
		for (int i = 1; i <= exceptionCount[regionIndex]; i++) {
			regionExceptions[regionIndex][i - 1] = "null";
		}
		exceptionCount[regionIndex] = 0;
		getRegionCount();
		loadDatabase();

		player.sendMessage(ChatColor.GREEN
				+ "Database re-loaded and re-formatted successfully.");

	}

	public static boolean backupDatabase(File db, String targetDirectory,
			Player player) {
		try {
			File td = new File("plugins/Regios/" + targetDirectory + "/");
			td.mkdir();
			copy(new File("plugins/Regios/regions"), new File("plugins/Regios/"
					+ targetDirectory + "/regions"));
			copy(new File("plugins/Regios/RegiosGlobalConfig.yml"), new File(
					"plugins/Regios/" + targetDirectory
							+ "/RegiosGlobalConfig.yml"));
			return true;
		} catch (IOException ex) {
			player.sendMessage(ChatColor.RED
					+ "Error whilst trying to create backup!");
			player.sendMessage(ChatColor.RED
					+ "Has the database been modified?");
			player.sendMessage(ChatColor.RED
					+ "Have you entered an invalid file name?");
			errorThrown = true;
			return false;
		}
	}
	
	public static boolean backupDatabase(File db, String targetDirectory) {
		try {
			File td = new File("plugins/Regios/" + targetDirectory + "/");
			td.mkdir();
			copy(new File("plugins/Regios/regions"), new File("plugins/Regios/"
					+ targetDirectory + "/regions"));
			copy(new File("plugins/Regios/RegiosGlobalConfig.yml"), new File(
					"plugins/Regios/" + targetDirectory
							+ "/RegiosGlobalConfig.yml"));
			return true;
		} catch (IOException ex) {
			errorThrown = true;
			return false;
		}
	}


	public static boolean errorThrown = false;

	public static void copy(File src, File dst) throws IOException {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

}
