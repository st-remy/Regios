package couk.Adamki11s.Regios.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.config.Configuration;

import couk.Adamki11s.Regios.Economy.Economy;
import couk.Adamki11s.Regios.Economy.EconomyCore;
import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.Regios.Scheduler.LightningRunner;

public class LoaderCore {

	private final File root = new File("plugins" + File.separator + "Regios"), db_root = new File(root + File.separator + "Database"), config_root = new File(root + File.separator
			+ "Configuration");
	File updateconfig = new File(config_root + File.separator + "Updates.config"), defaultregions = new File(config_root + File.separator + "DefaultRegion.config"),
			generalconfig = new File(config_root + File.separator + "GeneralSettings.config");

	private final Logger log = Logger.getLogger("Minecraft.Regios");
	private final String prefix = "[Regios]";

	public void setup() {
		loadConfiguration();
		loadRegions();
	}

	public void loadConfiguration() {
		log.info(prefix + " Loading configuration files.");
		Configuration c = new Configuration(defaultregions);
		c.load();

		String a = c.getString("DefaultSettings.Messages.WelcomeMessage"), b = c.getString("DefaultSettings.Messages.LeaveMessage"), cc = c
				.getString("DefaultSettings.Messages.ProtectionMessage"), d = c.getString("DefaultSettings.Messages.PreventEntryMessage"), e = c
				.getString("DefaultSettings.Messages.PreventExitMessage"), pass = c.getString("DefaultSettings.Password.Password");

		boolean aa = c.getBoolean("DefaultSettings.Messages.ShowWelcomeMessage", true), bb = c.getBoolean("DefaultSettings.Messages.ShowLeaveMessage", true), ccc = c.getBoolean(
				"DefaultSettings.Messages.ShowProtectionMessage", true), dd = c.getBoolean("DefaultSettings.Messages.ShowPreventEntryMessage", true), ee = c.getBoolean(
				"DefaultSettings.Messages.ShowPreventExitMessage", true);

		boolean f = c.getBoolean("DefaultSettings.General.Protected", false), g = c.getBoolean("DefaultSettings.General.PreventEntry", false), exit = c.getBoolean(
				"DefaultSettings.General.PreventExit", false), h = c.getBoolean("DefaultSettings.General.MobSpawns", true), i = c.getBoolean(
				"DefaultSettings.General.MonsterSpawns", true), j = c.getBoolean("DefaultSettings.Other.HealthEnabled", true), k = c.getBoolean("DefaultSettings.General.PvP",
				false), m = c.getBoolean("DefaultSettings.General.DoorsLocked", false), n = c.getBoolean("DefaultSettings.General.ChestsLocked", false), o = c.getBoolean(
				"DefaultSettings.General.PreventInteraction", false), v = c.getBoolean("DefaultSettings.Messages.ShowPvPWarning", true), pe = c.getBoolean(
				"DefaultSettings.Password.PasswordProtection", false), tntProtection = c.getBoolean("DefaultSettings.Protection.TNTProtection", false), fireProtection = c
				.getBoolean("DefaultSettings.Protection.FireProtection", false), creeperProtection = c.getBoolean("DefaultSettings.Protection.CreeperProtection", false);

		int p = c.getInt("DefaultSettings.Other.LSPS", 0), q = c.getInt("DefaultSettings.Other.HealthRegen", 0), r = c.getInt("DefaultSettings.Other.VelocityWarp", 0);

		String dam = c.getString("DefaultSettings.Password.PasswordMessage", "Authentication required! Do /regios auth <password>"), dasm = c.getString(
				"DefaultSettings.Password.PasswordSuccessMessage", "Authentication successful!");

		MODE s = MODE.toMode(c.getString("DefaultSettings.Modes.ProtectionMode")), t = MODE.toMode(c.getString("DefaultSettings.Modes.PreventEntryMode")), u = MODE.toMode(c
				.getString("DefaultSettings.Modes.PreventExitMode")), item = MODE.toMode(c.getString("DefaultSettings.Modes.ItemControlMode"));

		Material welcomeIcon = Material.getMaterial(c.getInt("DefaultSettings.Spout.SpoutWelcomeIconID", Material.GRASS.getId())), leaveIcon = Material.getMaterial(c.getInt(
				"DefaultSettings.Spout.SpoutLeaveIconID", Material.DIRT.getId()));

		String[] musicUrl = c.getString("DefaultSettings.Spout.Sound.CustomMusicURL", "").trim().split(",");
		boolean playmusic = c.getBoolean("DefaultSettings.Spout.Sound.PlayCustomMusic", false);

		boolean permWipeOnEnter = c.getBoolean("DefaultSettings.Inventory.PermWipeOnEnter", false), permWipeOnExit = c
				.getBoolean("DefaultSettings.Inventory.PermWipeOnExit", false), wipeAndCacheOnEnter = c.getBoolean("DefaultSettings.Inventory.WipeAndCacheOnEnter", false), wipeAndCacheOnExit = c
				.getBoolean("DefaultSettings.Inventory.WipeAndCacheOnExit", false), forceCommand = c.getBoolean("DefaultSettings.Command.ForceCommand", false);

		String[] commandSet = c.getString("DefaultSettings.Command.CommandSet", "").trim().split(",");
		
		String[] tempAddCache = c.getString("Region.Permissions.TemporaryCache.AddNodes", "").trim().split(",");
		
		String[] permAddCache = c.getString("Region.Permissions.PermanentCache.AddNodes", "").trim().split(",");
		
		String[] permRemCache = c.getString("Region.Permissions.PermanentCache.RemoveNodes", "").trim().split(",");
		
		boolean form = c.getBoolean("DefaultSettings.Block.BlockForm.Enabled", true);
				
		// ___________________________________

		c = new Configuration(updateconfig);

		c.load();

		boolean cfu = c.getBoolean("CheckForUpdates", true), dua = c.getBoolean("DownloadUpdatesAutomatically", true), cov = c.getBoolean("CacheOldVersions", true), fr = c
				.getBoolean("ForceReload", true);
		
		int playerCap = c.getInt("DefaultSettings.General.PlayerCap.Cap", 0);

		new ConfigurationData(a, b, cc, d, e, pass, f, g, h, i, j, k, m, n, o, v, pe, p, q, r, s, t, u, item, cfu, dua, cov, fr, exit, dam, dasm, welcomeIcon, leaveIcon, aa, bb,
				ccc, dd, ee, tntProtection, fireProtection, creeperProtection, musicUrl, playmusic, permWipeOnEnter, permWipeOnExit, wipeAndCacheOnEnter, wipeAndCacheOnExit,
				forceCommand, commandSet, tempAddCache, permAddCache, permRemCache, form, playerCap);
		// Initialises variables in configuration data.

		c = new Configuration(generalconfig);

		c.load();

		int id = c.getInt("Region.Tools.Setting.ID", Material.WOOD_AXE.getId());
		ConfigurationData.defaultSelectionTool = Material.getMaterial(id);
		
		Economy econ = Economy.toEconomy(c.getString("Regios.Economy", "NONE"));
		
		if(econ == Economy.NONE){
			EconomyCore.economySupport = false;
			EconomyCore.economy = econ;
			log.info(prefix + " No economy preset specified in config. Not using economy.");
		} else if(econ == Economy.BOSECONOMY || econ == Economy.ICONOMY){
			EconomyCore.economySupport = true;
			EconomyCore.economy = econ;
			log.info(prefix + " Economy preset found : " + econ.toString().toUpperCase());
		}

		log.info(prefix + " Configuration files loaded successfully!");
	}

	public void loadRegions() {
		File[] children = db_root.listFiles();
		if (children.length > 0) {
			log.info(prefix + " Loading [" + children.length + "] Regions.");
		} else {
			log.info(prefix + " No Regions to load.");
		}
		for (File root : children) {
			if (root.isDirectory()) {
				ArrayList<String> exceptionsPlayers = new ArrayList<String>();
				ArrayList<String> exceptionsNodes = new ArrayList<String>();
				ArrayList<Integer> items = new ArrayList<Integer>();

				File toload = new File(db_root + File.separator + root.getName() + File.separator + root.getName() + ".rz");
				File excep = new File(db_root + File.separator + root.getName() + File.separator + "Exceptions" + File.separator + "Players"), nodes = new File(db_root
						+ File.separator + root.getName() + File.separator + "Exceptions" + File.separator + "Nodes"), it = new File(db_root + File.separator + root.getName()
						+ File.separator + "Items");

				for (File ex : excep.listFiles()) {
					exceptionsPlayers.add(ex.getName().substring(0, ex.getName().lastIndexOf(".")));
				}
				for (File nd : nodes.listFiles()) {
					exceptionsNodes.add(nd.getName().substring(0, nd.getName().lastIndexOf(".")));
				}
				if (it.listFiles().length > 0) {
					for (File f : it.listFiles()) {
						try {
							items.add(Integer.parseInt(f.getName().substring(0, f.getName().lastIndexOf("."))));
						} catch (NumberFormatException nfe) {
							log.severe(prefix + " Error parsing integer in item file!");
						}
					}
				}

				Configuration c = new Configuration(toload);

				c.load();

				String welcomeMessage = c.getString("Region.Messages.WelcomeMessage"), leaveMessage = c.getString("Region.Messages.LeaveMessage"), protectionMessage = c
						.getString("Region.Messages.ProtectionMessage"), preventEntryMessage = c.getString("Region.Messages.PreventEntryMessage"), preventExitMessage = c
						.getString("Region.Messages.PreventExitMessage");

				boolean showPvpWarning = c.getBoolean("Region.Messages.ShowPvpWarning", true), showWelcomeMessage = c.getBoolean("Region.Messages.ShowWelcomeMessage", true), showLeaveMessage = c
						.getBoolean("Region.Messages.ShowLeaveMessage", true), showProtectionMessage = c.getBoolean("Region.Messages.ShowProtectionMessage", true), showPreventEntryMessage = c
						.getBoolean("Region.Messages.ShowPreventEntryMessage", true), showPreventExitMessage = c.getBoolean("Region.Messages.ShowPreventExitMessage", true);

				MODE itemMode = MODE.toMode(c.getString("Region.Modes.ItemControlMode", "Whitelist")), protectionMode = MODE.toMode(c.getString("Region.Modes.ProtectionMode",
						"Whitelist")), preventEntryMode = MODE.toMode(c.getString("Region.Modes.PreventEntryMode", "Whitelist")), preventExitMode = MODE.toMode(c.getString(
						"Region.Modes.PreventExitMode", "Whitelist"));

				boolean _protected = c.getBoolean("Region.General.Protected", false), preventEntry = c.getBoolean("Region.General.PreventEntry", false), preventExit = c
						.getBoolean("Region.General.PreventExit", false), preventInteraction = c.getBoolean("Region.General.PreventInteraction", false), doorsLocked = c
						.getBoolean("Region.General.DoorsLocked", false), chestsLocked = c.getBoolean("Region.General.ChestsLocked", false), passwordEnabled = c.getBoolean(
						"Region.General.Password.Enabled", false), tntProtection = c.getBoolean("DefaultSettings.Protection.TNTProtection", false), fireProtection = c.getBoolean(
						"DefaultSettings.Protection.FireProtection", false), creeperProtection = c.getBoolean("DefaultSettings.Protection.CreeperProtection", false);

				String password = c.getString("Region.General.Password.Password");

				boolean mobSpawns = c.getBoolean("Region.Other.MobSpawns", true), monsterSpawns = c.getBoolean("Region.Other.MonsterSpawns", true), pvp = c.getBoolean(
						"Region.Other.PvP", true), healthEnabled = c.getBoolean("Region.Other.HealthEnabled", true);

				int healthRegen = c.getInt("Region.Other.HealthRegen", 0), lsps = c.getInt("Region.Other.LSPS", 0);

				double velocityWarp = c.getDouble("Region.Other.VelocityWarp", 0);

				String owner = c.getString("Region.Essentials.Owner"), name = c.getString("Region.Essentials.Name");

				String ww = c.getString("Region.Essentials.World");
				World world = Bukkit.getServer().getWorld(ww);

				String l11 = c.getString("Region.Essentials.Points.Point1"), l22 = c.getString("Region.Essentials.Points.Point2");

				Location l1 = toLocation(l11), l2 = toLocation(l22);

				String spoutWelcomeMessage = c.getString("Region.Spout.Welcome.Message"), spoutLeaveMessage = c.getString("Region.Spout.Leave.Message");

				Material spoutWelcomeMaterial = Material.getMaterial(c.getInt("Region.Spout.Welcome.IconID", Material.GRASS.getId())), spoutLeaveMaterial = Material.getMaterial(c
						.getInt("Region.Spout.Leave.IconID", Material.DIRT.getId()));

				String[] musicUrl = c.getString("Region.Spout.Sound.CustomMusicURL", "").trim().split(",");
				boolean playmusic = c.getBoolean("Region.Spout.Sound.PlayCustomMusic", false);

				boolean permWipeOnEnter = c.getBoolean("Region.Inventory.PermWipeOnEnter", false), permWipeOnExit = c.getBoolean("Region.Inventory.PermWipeOnExit", false), wipeAndCacheOnEnter = c
						.getBoolean("Region.Inventory.WipeAndCacheOnEnter", false), wipeAndCacheOnExit = c.getBoolean("Region.Inventory.WipeAndCacheOnExit", false), forceCommand = c
						.getBoolean("Region.Command.ForceCommand", false);

				String[] commandSet = c.getString("Region.Command.CommandSet", "").trim().split(",");
				
				String[] tempAddCache = c.getString("Region.Permissions.TemporaryCache.AddNodes", "").trim().split(",");
				
				String[] permAddCache = c.getString("Region.Permissions.PermanentCache.AddNodes", "").trim().split(",");
				
				String[] permRemCache = c.getString("Region.Permissions.PermanentCache.RemoveNodes", "").trim().split(",");
				
				String[] subOwners = c.getString("Region.Essentials.SubOwners", "").trim().split(",");
				
				Location warp = toLocation(c.getString("Region.Teleportation.Warp.Location", ww + ",0,0,0"));
				
				int playerCap = c.getInt("Region.General.PlayerCap.Cap", 0);
				
				boolean form = c.getBoolean("Region.Block.BlockForm.Enabled", true);
				
				int price = c.getInt("Region.Economy.Price", 0);
				
				boolean forSale = c.getBoolean("Region.Economy.ForSale", false);

				Region r = new Region(owner, name, l1, l2, world, null);

				for (String s : exceptionsPlayers) {
					r.addException(s);
				}
				for (String s : exceptionsNodes) {
					r.addExceptionNode(s);
				}

				r.spoutExitMaterial = spoutLeaveMaterial;
				r.spoutEntryMaterial = spoutWelcomeMaterial;
				r.spoutEntryMessage = spoutWelcomeMessage;
				r.spoutExitMessage = spoutLeaveMessage;

				r.velocityWarp = velocityWarp;
				r.LSPS = lsps;
				r.healthRegen = healthRegen;

				r.healthEnabled = healthEnabled;
				r.pvp = pvp;
				r.monsterSpawns = monsterSpawns;
				r.mobSpawns = mobSpawns;

				r.password = password;

				r.passwordEnabled = passwordEnabled;
				r.chestsLocked = chestsLocked;
				r.doorsLocked = doorsLocked;
				r.preventInteraction = preventInteraction;
				r.preventExit = preventExit;
				r.preventEntry = preventEntry;
				r._protection = _protected;

				r.preventExitMode = preventExitMode;
				r.preventEntryMode = preventEntryMode;
				r.protectionMode = protectionMode;
				r.itemMode = itemMode;

				r.showPreventExitMessage = showPreventExitMessage;
				r.showPreventEntryMessage = showPreventEntryMessage;
				r.showProtectionMessage = showProtectionMessage;
				r.showLeaveMessage = showLeaveMessage;
				r.showWelcomeMessage = showWelcomeMessage;
				r.showPvpWarning = showPvpWarning;

				r.preventExitMessage = r.colourFormat(preventExitMessage);
				r.preventEntryMessage = r.colourFormat(preventEntryMessage);
				r.protectionMessage = r.colourFormat(protectionMessage);
				r.leaveMessage = r.colourFormat(leaveMessage);
				r.welcomeMessage = r.colourFormat(welcomeMessage);

				r.tntProtection = tntProtection;
				r.fireProtection = fireProtection;
				r.creeperProtection = creeperProtection;

				r.playCustomSoundUrl = playmusic;
				r.customSoundUrl = musicUrl;

				r.permWipeOnEnter = permWipeOnEnter;
				r.permWipeOnExit = permWipeOnExit;
				r.wipeAndCacheOnEnter = wipeAndCacheOnEnter;
				r.wipeAndCacheOnExit = wipeAndCacheOnExit;
				r.forceCommand = forceCommand;

				r.commandSet = commandSet;
				r.items = items;						
				
				r.temporaryNodesCacheAdd = tempAddCache;
				r.permanentNodesCacheAdd = permAddCache;
				r.permanentNodesCacheRemove = permRemCache;
				
				r.subOwners = subOwners;
				
				r.warp = warp;
				
				r.blockForm = form;
				
				r.playerCap = playerCap;
				
				r.salePrice = price;
				r.forSale = forSale;

				if (r.LSPS > 0 && !LightningRunner.doesStikesContain(r)) {
					LightningRunner.addRegion(r);
				} else if (r.LSPS == 0 && LightningRunner.doesStikesContain(r)) {
					LightningRunner.removeRegion(r);
				}

			}
		}
	}

	public Location toLocation(String loc) {
		Location l = null;
		try{
		String[] locations = loc.split(",");
		l = new Location(Bukkit.getServer().getWorld(locations[0].trim()), Double.parseDouble(locations[1].trim()), Double.parseDouble(locations[2].trim()),
				Double.parseDouble(locations[3].trim()));
		} catch (Exception ex){
			ex.printStackTrace();
		}
		return l;
	}

}
