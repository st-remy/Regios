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
		Map<String, Object> all = c.getAll();
		String value = (String) all.get("Regios.Economy");
		all.remove("Regios.Economy");
		for (Entry<String, Object> entry : all.entrySet()) {
			if (!entry.getKey().contains("Regios")) {
				c.setProperty(entry.getKey(), entry.getValue());
			}
		}
		c.setProperty("Region.LogsEnabled", true);
		c.setProperty("Region.Economy", value);
		c.save();
		ConfigurationData.logs = true;
		System.out.println("[Regios][Patch] Region.LogsEnabled property added.");
		System.out.println("[Regios][Patch] Region.Economy property modified from Regios.Economy.");
		System.out.println("[Regios][Patch] Patch completed!");
	}

}
