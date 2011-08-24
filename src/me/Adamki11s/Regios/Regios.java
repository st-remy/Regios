package me.Adamki11s.Regios;

import java.io.IOException;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.iConomy.*;

import couk.Adamki11s.AutoUpdater.AUCore;
import couk.Adamki11s.WorldConfiguration.ConfigurationLoader;
import couk.Adamki11s.WorldConfiguration.ConfigurationSettings;

public class Regios extends JavaPlugin {	
	
	public static double currentVersion = 3.6, currentSubVersion = 1;

	public static PermissionHandler permissionHandler;
    public static boolean hasPermissions = false, iConomyEnabled = false;
    //ClassListeners
    private final RegiosPlayerListener playerListener = new RegiosPlayerListener(this);
	private final RegiosBlockListener blockListener = new RegiosBlockListener(this);
	//private final RegionsFileHandler fileHandler = new RegionsFileHandler(this);
	private final RegiosCreatureSpawnEvent spawnListener = new RegiosCreatureSpawnEvent(this);
	private final RegiosEntityListener entityListener = new RegiosEntityListener(this);
	
	public static Server server;
	public static String logPrefix = "[Regios] ";
    //ClassListeners
	
	private RegiosScheduler schedule = null;
	private int taskId = 0;
	
	Logger log = Logger.getLogger("Minecraft");

	public static AUCore core;

	public void onDisable() {
		log.info(logPrefix + "v" + currentVersion + "_" + currentSubVersion + " - Closing SQL connections...");
		if (RegiosFileManager.dbManage != null) {
			RegiosFileManager.dbManage.close();
		}
		log.info(logPrefix + "v" + currentVersion + "_" + currentSubVersion + " - Regios disabled.");
		server.getScheduler().cancelAllTasks();
	}
	
	public static Plugin plugin;
	
	 public PluginDescriptionFile info = null;
	 public PluginManager pluginManager = null;
	 public static iConomy iConomy = null;

	public void onEnable() {
		
		if (RegiosFileManager.dbManage != null) {
			RegiosFileManager.dbManage.close();
		}
		
        PluginManager pm = this.getServer().getPluginManager();
        server = this.getServer();
        plugin = this;
        
        //Permissions Setup
        setupPermissions();
        //Permissions Setup
        
        RegiosFileManager.startupProcedure();
        
        log.info("[Regios] - v" + currentVersion + "_" + currentSubVersion + " - [" + RegiosFileManager.regionCount + "] Regions loaded from database");
        
        core = new AUCore("http://regiosplugin.zxq.net/regiosupdates.html", log, "[Regios]");
        
        if(!core.checkVersion(currentVersion, currentSubVersion, "Regios")){
        	core.forceDownload("http://dl.dropbox.com/u/27260323/Regios/Latest%20Release/Regios.jar", "Regios");
        }
        
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.CREATURE_SPAWN, spawnListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.ENTITY_EXPLODE, entityListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.BLOCK_BURN, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.BLOCK_IGNITE, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.SIGN_CHANGE, blockListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLUGIN_ENABLE, new RegiosServerListener(this), Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.PLUGIN_DISABLE, new RegiosServerListener(this), Event.Priority.Monitor, this);
        pm.registerEvent(Event.Type.LIGHTNING_STRIKE, new RegiosWeatherListener(), Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.EXPLOSION_PRIME, entityListener, Event.Priority.Highest, this);
        pm.registerEvent(Event.Type.PLAYER_BUCKET_EMPTY, playerListener, Event.Priority.Highest, this);
        
        try {
			ConfigurationLoader.loadWorldConfiguration(server);
			log.info("[Regios] Loaded world configuration and protection for " + ConfigurationSettings.size + " worlds successfully!");
		} catch (IOException e) {
			e.printStackTrace();
			log.severe("[Regios] Error while reading configuration file. Check your spaces, tabs and make sure you have only modified the true/false values!");
		}
        
        getCommand("regios").setExecutor(new RegiosCommandManager(this));
        this.schedule = new RegiosScheduler(this);
        taskId = this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, this.schedule, 1L, 21L * 1);
        
        Player[] onlinePs;
        onlinePs = server.getOnlinePlayers();
        for(int a = 1; a <= onlinePs.length; a++){
        	RegiosPlayerListener.reloadPlugin(onlinePs[a - 1]);
        	RegiosPlayerListener.initValues(onlinePs[a - 1]);
        	//log.info("Reload procedure completed for = " + onlinePs[a - 1]);
        }
       
	}
	
	
	
	
    
    //Permissions Setup
    @SuppressWarnings("static-access")
	private void setupPermissions() {
	      Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
	      if (this.permissionHandler == null) {
	          if (permissionsPlugin != null) {
	              this.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
	              hasPermissions = true;
	              log.info("[Regios] - v" + currentVersion + "_" + currentSubVersion + " - Permissions support enabled");
	          } else {
	        	  log.info("[Regios] - v" + currentVersion + "_" + currentSubVersion + " - Permissions not detected, defaulting to OP");
	          }
	      }
	  }
    //Permissions Setup

	public static void broadcast(Player gg) {
		// TODO Auto-generated method stub
		server.broadcastMessage("" + gg);
	}
	
}
