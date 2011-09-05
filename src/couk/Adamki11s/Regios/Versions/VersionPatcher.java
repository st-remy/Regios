package couk.Adamki11s.Regios.Versions;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.util.config.Configuration;

import couk.Adamki11s.Regios.Data.ConfigurationData;

public class VersionPatcher {

	private static final File root = new File("plugins" + File.separator + "Regios"), config_root = new File(root + File.separator + "Configuration");

	public static void runPatch(String version) {
		if (version.equalsIgnoreCase("4.0.57")) {
			patch4057(version);
		}
	}

	private static void patch4057(String v) {
		System.out.println("[Regios][Patch] Patching files for version : " + v);
		System.out.println("[Regios][Patch] Modifying general configuration file...");
		File generalconfig = new File(config_root + File.separator + "GeneralSettings.config");
		Configuration c = new Configuration(generalconfig);
		c.load();
		String value = (String) c.getString("Regios.Economy");
		int oldID = c.getInt("Region.Tools.Setting.ID", 271);
		c = new Configuration(generalconfig);
		c.setProperty("Region.LogsEnabled", true);
		c.setProperty("Region.Tools.Setting.ID", oldID);
		c.setProperty("Region.Economy", value);
		c.save();
		ConfigurationData.logs = true;
		System.out.println("[Regios][Patch] Region.LogsEnabled property added.");
		System.out.println("[Regios][Patch] Region.Economy property modified from Regios.Economy.");
		System.out.println("[Regios][Patch] Patch completed!");
	}

}
