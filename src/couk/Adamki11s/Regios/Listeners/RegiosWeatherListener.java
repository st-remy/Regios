package couk.Adamki11s.Regios.Listeners;

import org.bukkit.World;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherListener;
import org.bukkit.event.world.WorldListener;

import couk.Adamki11s.Extras.Events.ExtrasEvents;
import couk.Adamki11s.Extras.Regions.ExtrasRegions;
import couk.Adamki11s.Regios.Checks.PermChecks;
import couk.Adamki11s.Regios.Commands.CreationCommands;
import couk.Adamki11s.Regios.Regions.GlobalRegionManager;
import couk.Adamki11s.Regios.Regions.SubRegionManager;

public class RegiosWeatherListener extends WeatherListener {
	
	private static final GlobalRegionManager grm = new GlobalRegionManager();
	private static final ExtrasEvents extEvt = new ExtrasEvents();
	private static final ExtrasRegions extReg = new ExtrasRegions();
	private static final SubRegionManager srm = new SubRegionManager();
	private static final PermChecks permChecks = new PermChecks();
	private static final CreationCommands creationCommands = new CreationCommands();
	
	public void onLightningStrike(LightningStrikeEvent evt){
		World w = evt.getWorld();
		if(GlobalRegionManager.getGlobalWorldSetting(w) == null){
			return;
		}
		if(!GlobalRegionManager.getGlobalWorldSetting(w).lightning_enabled){
			evt.setCancelled(true);
		}
	}

}
