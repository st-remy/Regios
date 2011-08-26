package couk.Adamki11s.Regios.Main;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijikokun.bukkit.Permissions.Permissions;
import couk.Adamki11s.Regios.Commands.CommandCore;
import couk.Adamki11s.Regios.Data.CreationCore;
import couk.Adamki11s.Regios.Economy.Economy;
import couk.Adamki11s.Regios.Economy.EconomyCore;
import couk.Adamki11s.Regios.Listeners.RegiosBlockListener;
import couk.Adamki11s.Regios.Listeners.RegiosEntityListener;
import couk.Adamki11s.Regios.Listeners.RegiosPlayerListener;
import couk.Adamki11s.Regios.Listeners.RegiosServerListener;
import couk.Adamki11s.Regios.Listeners.RegiosWeatherListener;
import couk.Adamki11s.Regios.Permissions.PermissionsCore;
import couk.Adamki11s.Regios.Regions.GlobalRegionManager;
import couk.Adamki11s.Regios.Regions.GlobalWorldSetting;
import couk.Adamki11s.Regios.Scheduler.MainRunner;
import couk.Adamki11s.Regios.SpoutInterface.SpoutCraftListener;
import couk.Adamki11s.Regios.SpoutInterface.SpoutInterface;
import couk.Adamki11s.Regios.SpoutInterface.SpoutInventoryListener;

public class Regios extends JavaPlugin {

	Logger log = Logger.getLogger("Minecraft.Regios");
	String prefix = "[Regios]", version;

	public final RegiosBlockListener blockListener = new RegiosBlockListener();
	public final RegiosPlayerListener playerListener = new RegiosPlayerListener();
	public final RegiosEntityListener entityListener = new RegiosEntityListener();
	public final RegiosWeatherListener weatherListener = new RegiosWeatherListener();
	public final RegiosServerListener serverListener = new RegiosServerListener();

	public static Plugin regios;

	@Override
	public void onDisable() {
		log.info(prefix + " Shutting down scheduler task...");
		MainRunner.stopMainRunner();
		log.info(prefix + " Scheduler task stopped successfully!");
		log.info(prefix + " Regios version " + version + " disabled successfully!");
	}

	@Override
	public void onEnable() {
		version = this.getDescription().getVersion();
		PluginManager pm = this.getServer().getPluginManager();

		try {
			new CreationCore().setup();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		regios = this;
		
		pm.registerEvent(Type.PLAYER_MOVE, playerListener, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_JOIN, playerListener, Priority.Highest, this);
		pm.registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Highest, this);
		pm.registerEvent(Type.BLOCK_PLACE, blockListener, Priority.Highest, this);
		pm.registerEvent(Type.BLOCK_BREAK, blockListener, Priority.Highest, this);
		pm.registerEvent(Type.BLOCK_IGNITE, blockListener, Priority.Highest, this);
		pm.registerEvent(Type.BLOCK_FORM, blockListener, Priority.Highest, this);
		pm.registerEvent(Type.SIGN_CHANGE, blockListener, Priority.Highest, this);
		pm.registerEvent(Type.EXPLOSION_PRIME, entityListener, Priority.Highest, this);
		pm.registerEvent(Type.ENTITY_DAMAGE, entityListener, Priority.Highest, this);
		pm.registerEvent(Type.CREATURE_SPAWN, entityListener, Priority.Highest, this);
		pm.registerEvent(Type.LIGHTNING_STRIKE, weatherListener, Priority.Highest, this);
		pm.registerEvent(Type.ENTITY_DEATH, entityListener, Priority.Highest, this);
		
		if(EconomyCore.economy == Economy.ICONOMY){
			pm.registerEvent(Type.PLUGIN_ENABLE, serverListener, Priority.Monitor, this);
	        pm.registerEvent(Type.PLUGIN_DISABLE, serverListener, Priority.Monitor, this);
		} else if(EconomyCore.economy == Economy.BOSECONOMY){
			EconomyCore.boseConomySetup();
		}

		SpoutInterface.setup(pm, log);

		setupPermissions();

		getCommand("regios").setExecutor(new CommandCore());
		
		GlobalWorldSetting.writeWorldsToConfiguration();
		GlobalWorldSetting.loadWorldsFromConfiguration();
		
		for(World w : Bukkit.getServer().getWorlds()){
			 GlobalWorldSetting gws = GlobalRegionManager.getGlobalWorldSetting(w);
			 if(gws.overridingPvp && !w.getPVP()){
				 w.setPVP(true);
				 log.info("[Regios] PvP Setting for world : " + w.getName() + ", overridden!");
			 }
		}

		if (SpoutInterface.global_spoutEnabled) {
			pm.registerEvent(Type.CUSTOM_EVENT, new SpoutCraftListener(), Priority.Highest, this);
			pm.registerEvent(Type.CUSTOM_EVENT, new SpoutInventoryListener(), Priority.Highest, this);
		}
		
		log.info(prefix + " Starting scheduler task...");
		MainRunner.startMainRunner();
		log.info(prefix + " Scheduler task initiated!");
		
		log.info(prefix + " Regios version " + version + " enabled successfully!");
	}

	private void setupPermissions() {
		Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
		if (PermissionsCore.permissionHandler == null) {
			if (permissionsPlugin != null) {
				PermissionsCore.permissionHandler = ((Permissions) permissionsPlugin).getHandler();
				PermissionsCore.hasPermissions = true;
				log.info("[Regios] Permissions support enabled");
			} else {
				log.info("[Regios] Permissions not detected, defaulting to OP");
			}
		}
	}

}
