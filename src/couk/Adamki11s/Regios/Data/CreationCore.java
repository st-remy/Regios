package couk.Adamki11s.Regios.Data;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import org.bukkit.Material;
import org.bukkit.util.config.Configuration;

public class CreationCore {

	private final File root = new File("plugins" + File.separator + "Regios"), db_root = new File(root + File.separator + "Database"), config_root = new File(root + File.separator + "Configuration");

	private final Logger log = Logger.getLogger("Minecraft.Regios");
	private final String prefix = "[Regios]";

	public void setup() throws IOException {
		directories();
		configuration();
		new LoaderCore().setup();
	}

	private void directories() {
		log.info(prefix + " Checking directories.");
		boolean flawless = true;

		if (!root.exists()) {
			flawless = false;
			root.mkdir();
			log.info(prefix + " Creating directory @_root/plugins/Regios");
		}

		if (!db_root.exists()) {
			flawless = false;
			db_root.mkdir();
			log.info(prefix + " Creating directory @_root/plugins/Regios/Database");
		}

		if (!config_root.exists()) {
			flawless = false;
			config_root.mkdir();
			log.info(prefix + " Creating directory @_root/plugins/Regios/Configuration");
		}

		if (!flawless) {
			log.info(prefix + " Required directories created successfully!");
		}
		log.info(prefix + " Directory check completed.");
	}

	private void configuration() throws IOException {
		log.info(prefix + " Checking configuration files.");
		boolean flawless = true;
		File updateconfig = new File(config_root + File.separator + "Updates.config"), defaultregions = new File(config_root + File.separator + "DefaultRegion.config"), generalconfig = new File(config_root + File.separator + "GeneralSettings.config");
		
		if (!updateconfig.exists()) {
			log.info(prefix + " Creating update configuration.");
			updateconfig.createNewFile();
			Configuration c = new Configuration(updateconfig);
			c.setProperty("CheckForUpdates", true);
			c.setProperty("DownloadUpdatesAutomatically", true);
			c.setProperty("CacheOldVersions", true);
			c.setProperty("ForceReload", true);
			c.save();
		}
		if (!generalconfig.exists()) {
			log.info(prefix + " Creating general configuration.");
			generalconfig.createNewFile();
			Configuration c = new Configuration(generalconfig);
			c.setProperty("Regios.Economy", "NONE");
			c.setProperty("Region.Tools.Setting.ID", Material.WOOD_AXE.getId());
			c.save();
		}
		if (!defaultregions.exists()) {
			log.info(prefix + " Creating default region settings configuration.");
			defaultregions.createNewFile();
			Configuration c = new Configuration(defaultregions);
			c.setProperty("DefaultSettings.General.Protected", false);
			c.setProperty("DefaultSettings.General.PreventEntry", false);
			c.setProperty("DefaultSettings.General.PreventExit", false);
			c.setProperty("DefaultSettings.General.MobSpawns", true);
			c.setProperty("DefaultSettings.General.MonsterSpawns", true);
			c.setProperty("DefaultSettings.General.PvP", false);
			c.setProperty("DefaultSettings.General.DoorsLocked", false);
			c.setProperty("DefaultSettings.General.ChestsLocked", false);
			c.setProperty("DefaultSettings.General.PreventInteraction", false);

			c.setProperty("DefaultSettings.Protection.FireProtection", true);

			c.setProperty("DefaultSettings.Messages.WelcomeMessage", "<BGREEN>Welcome to <BLUE>[NAME] <BGREEN>owned by <YELLOW>[OWNER]");
			c.setProperty("DefaultSettings.Messages.LeaveMessage", "<RED>You left <BLUE>[NAME] <RED>owned by <YELLOW>[OWNER]");
			c.setProperty("DefaultSettings.Messages.ProtectionMessage", "<RED>This region is protected by owner <YELLOW>[OWNER]!");
			c.setProperty("DefaultSettings.Messages.PreventEntryMessage", "<RED>You cannot enter this region : <BLUE>[NAME]");
			c.setProperty("DefaultSettings.Messages.PreventExitMessage", "<RED>You cannot exit this region : <BLUE>[NAME]");
			c.setProperty("DefaultSettings.Messages.ShowWelcomeMessage", true);
			c.setProperty("DefaultSettings.Messages.ShowLeaveMessage", true);
			c.setProperty("DefaultSettings.Messages.ShowProtectionMessage", true);
			c.setProperty("DefaultSettings.Messages.ShowPreventEntryMessage", true);
			c.setProperty("DefaultSettings.Messages.ShowPreventExitMessage", true);
			c.setProperty("DefaultSettings.Messages.ShowPvPWarning", true);

			c.setProperty("DefaultSettings.Permissions.TemporaryCache.AddNodes", "");
			c.setProperty("DefaultSettings.Permissions.PermanentCache.AddNodes", "");
			c.setProperty("DefaultSettings.Permissions.PermanentCache.RemoveNodes", "");

			c.setProperty("DefaultSettings.Other.LSPS", 0);
			c.setProperty("DefaultSettings.Other.HealthEnabled", true);
			c.setProperty("DefaultSettings.Other.HealthRegenRate", 0);
			c.setProperty("DefaultSettings.Other.VelocityWarp", 0);

			c.setProperty("DefaultSettings.Modes.ProtectionMode", "WHITELIST");
			c.setProperty("DefaultSettings.Modes.PreventEntryMode", "WHITELIST");
			c.setProperty("DefaultSettings.Modes.PreventExitMode", "WHITELIST");
			c.setProperty("DefaultSettings.Modes.ItemControlMode", "WHITELIST");

			c.setProperty("DefaultSettings.Inventory.PermWipeOnEnter", false);
			c.setProperty("DefaultSettings.Inventory.PermWipeOnExit", false);
			c.setProperty("DefaultSettings.Inventory.WipeAndCacheOnEnter", false);
			c.setProperty("DefaultSettings.Inventory.WipeAndCacheOnExit", false);

			c.setProperty("DefaultSettings.Command.ForceCommand", false);
			c.setProperty("DefaultSettings.Command.CommandSet", "");

			c.setProperty("DefaultSettings.Password.PasswordProtection", false);
			c.setProperty("DefaultSettings.Password.Password", "NA");
			c.setProperty("DefaultSettings.Password.PasswordMessage", "<RED>Authentication required! Do /regios auth <password>");
			c.setProperty("DefaultSettings.Password.PasswordSuccessMessage", "Authentication successful!");

			c.setProperty("DefaultSettings.Spout.SpoutWelcomeIconID", Material.GRASS.getId());
			c.setProperty("DefaultSettings.Spout.SpoutLeaveIconID", Material.DIRT.getId());
			c.setProperty("DefaultSettings.Spout.Sound.PlayCustomMusic", false);
			c.setProperty("DefaultSettings.Spout.Sound.CustomMusicURL", "");

			c.setProperty("DefaultSettings.General.PlayerCap.Cap", 0);

			c.setProperty("DefaultSettings.Block.BlockForm.Enabled", true);

			c.save();
		}
		if (!flawless) {
			log.info(prefix + " Required configurations created successfully!");
		}
		log.info(prefix + " Configuration check completed.");
	}

}
