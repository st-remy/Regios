package couk.Adamki11s.Regios.Data;

public enum WeatherSetting {
	
	NONE,
	SUN,
	RAIN,
	SNOW;
	
	public static WeatherSetting toWeatherSetting(String setting){
		if(setting.equalsIgnoreCase("SUN")){
			return SUN;
		} else if(setting.equalsIgnoreCase("RAIN")){
			return RAIN;
		} else if(setting.equalsIgnoreCase("SNOW")){
			return SNOW;
		} else {
			return NONE;
		}
	}

}
