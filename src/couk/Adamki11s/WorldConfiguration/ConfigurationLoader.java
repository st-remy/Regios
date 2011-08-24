package couk.Adamki11s.WorldConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.util.config.Configuration;

public class ConfigurationLoader {
	
	static String mainDirectory = "plugins/Regios/GlobalConfiguration";
	private static File configFile;
	static Configuration config = new Configuration(configFile);
	private static String wn;
	private static int worldIndex;

	public static void loadWorldConfiguration(Server s) throws IOException{
		worldIndex = -1;
		new File(mainDirectory).mkdir();
		
		for(World world : s.getWorlds()){
			wn = world.getName();
			worldIndex++;
			Configuration config  = new Configuration(new File(mainDirectory + File.separator + wn + ".yml"));
			
			if(!(new File(mainDirectory + File.separator + wn + ".yml").exists())){
				System.out.println("[Regios] Creating configuration file for world '" + wn + "'.");
				new File(mainDirectory + File.separator + wn + ".yml").createNewFile();
				config.setProperty(wn + "Configuration.Weather.LightningEnabled", Boolean.toString(true));
				config.setProperty(wn + "Configuration.Events.FireEnabled", Boolean.toString(true));
				config.setProperty(wn + "Configuration.Events.LavaEnabled", Boolean.toString(true));
				config.setProperty(wn + "Configuration.Events.WaterEnabled", Boolean.toString(true));
				config.setProperty(wn + "Configuration.Events.TNTEnabled", Boolean.toString(true));
				config.setProperty(wn + "Configuration.Mobs.MonstersSpawn", Boolean.toString(true));
				config.setProperty(wn + "Configuration.Mobs.MobsSpawn", Boolean.toString(true));
				config.setProperty(wn + "Configuration.Mobs.CreepersExplode", Boolean.toString(true));
			}
			
			config.load();
			
			boolean lE = config.getBoolean(wn + "Configuration.Weather.LightningEnabled", true);
			boolean fE = config.getBoolean(wn + "Configuration.Events.FireEnabled", true);
			boolean lavaE = config.getBoolean(wn + "Configuration.Events.LavaEnabled", true);
			boolean waterE = config.getBoolean(wn + "Configuration.Events.WaterEnabled", true);
			boolean tE = config.getBoolean(wn + "Configuration.Events.TNTEnabled", true);
			boolean mS = config.getBoolean(wn + "Configuration.Mobs.MonstersSpawn", true);
			boolean mobS = config.getBoolean(wn + "Configuration.Mobs.MobsSpawn", true);
			boolean cE = config.getBoolean(wn + "Configuration.Mobs.CreepersExplode", true);
			
			ConfigurationSettings.setVariables(worldIndex, lE, fE, lavaE, tE, waterE, mS, mobS, cE, wn);
				
			config.save();
			
		}
		

	}
	
}
