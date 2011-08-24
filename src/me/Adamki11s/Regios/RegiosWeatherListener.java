package me.Adamki11s.Regios;

import org.bukkit.World;
import org.bukkit.entity.Weather;
import org.bukkit.event.Event.Type;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherEvent;
import org.bukkit.event.weather.WeatherListener;

import couk.Adamki11s.WorldConfiguration.ConfigurationSettings;

public class RegiosWeatherListener extends WeatherListener {
	
	public void onLightningStrike(LightningStrikeEvent event){
		World w = event.getWorld();
		int index = getIndex(w);
		
		if(!ConfigurationSettings.lightningEnabled[index - 1]){
			event.setCancelled(true);
		}
	}
	
	public static int getIndex(World w){
		String wN = w.getName();
		for(int a = 1; a <= ConfigurationSettings.size; a++){
			if(ConfigurationSettings.worldName[a - 1].equalsIgnoreCase(wN)){
				return a;
			}
		}
		return 1;
	}

}
