package couk.Adamki11s.Regios.Main;

import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import couk.Adamki11s.Regios.Commands.CommandCore;
import couk.Adamki11s.Regios.Data.CreationCore;
import couk.Adamki11s.Regios.Listeners.RegiosBlockListener;
import couk.Adamki11s.Regios.Listeners.RegiosPlayerListener;
import couk.Adamki11s.Regios.SpoutInterface.SpoutCraftListener;
import couk.Adamki11s.Regios.SpoutInterface.SpoutInterface;
import couk.Adamki11s.Regios.SpoutInterface.SpoutInventoryListener;

public class Regios extends JavaPlugin{
	
	Logger log = Logger.getLogger("Minecraft.Regios");
	String prefix = "[Regios]", version;
	
	public final RegiosBlockListener blockListener = new RegiosBlockListener();
	public final RegiosPlayerListener playerListener = new RegiosPlayerListener();
	
	public static Plugin regios;

	@Override
	public void onDisable() {
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
		
		SpoutInterface.setup(pm, log);
		
		getCommand("regios").setExecutor(new CommandCore());
		
		if(SpoutInterface.global_spoutEnabled){
			pm.registerEvent(Type.CUSTOM_EVENT, new SpoutCraftListener(), Priority.Highest, this);
			pm.registerEvent(Type.CUSTOM_EVENT, new SpoutInventoryListener(), Priority.Highest, this);
		}
		
		
		log.info(prefix + " Regios version " + version + " enabled successfully!");
	}

}
