package couk.Adamki11s.Regios.SpoutInterface;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class SpoutInterface extends SpoutRegion {
	
	public static boolean global_spoutEnabled;
	
	public static HashMap<Player, Boolean> spoutEnabled = new HashMap<Player, Boolean>();
	
	public static boolean doesPlayerHaveSpout(Player p){
		if(spoutEnabled.containsKey(p)){
			return spoutEnabled.get(p);
		} else {
			return false;
		}
	}

	public static void setup(PluginManager pm, Logger log){
		Plugin p = pm.getPlugin("Spout");
		if(p == null){
			global_spoutEnabled = false;
			log.info("[Regios] Spout was not found. Spout support disabled!");
		} else {
			global_spoutEnabled = true;
			log.info("[Regios] Spout detected! Spout support enabled!");
		}
	}
	
}
