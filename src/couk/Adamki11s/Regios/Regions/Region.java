package couk.Adamki11s.Regios.Regions;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import couk.Adamki11s.Checks.Checks;
import couk.Adamki11s.Checks.ChunkGrid;
import couk.Adamki11s.Checks.PermChecks;
import couk.Adamki11s.Regios.Data.ConfigurationData;
import couk.Adamki11s.Regios.Data.MODE;
import couk.Adamki11s.Regios.Data.Saveable;
import couk.Adamki11s.Regios.Data.WeatherSetting;
import couk.Adamki11s.Regios.Scheduler.LightningRunner;
import couk.Adamki11s.Regios.SpoutInterface.SpoutInterface;

public class Region extends PermChecks implements Checks {

	private final RegionLocation l1, l2;

	public final Region region = this;

	public static final GlobalRegionManager grm = new GlobalRegionManager();
	public static final Saveable saveable = new Saveable();

	public ChunkGrid chunkGrid;

	public String world;

	public Location warp = null;

	public String[] customSoundUrl, commandSet;

	public ArrayList<String> exceptions = new ArrayList<String>();
	public ArrayList<String> nodes = new ArrayList<String>();
	public ArrayList<Integer> items = new ArrayList<Integer>();

	public String welcomeMessage = "", leaveMessage = "", protectionMessage = "", preventEntryMessage = "", preventExitMessage = "", authenticationRequiredMessage = "",
			authenticationSuccessMessage = "", password = "", name = "", owner = "", spoutEntryMessage = "", spoutExitMessage = "";

	public Material spoutEntryMaterial = Material.GRASS, spoutExitMaterial = Material.DIRT;

	public WeatherSetting weatherSetting = WeatherSetting.NONE;

	public boolean _protection = false, preventEntry = false, preventExit = false, mobSpawns = true, monsterSpawns = true, healthEnabled = true, pvp = true, doorsLocked = false,
			chestsLocked = false, preventInteraction = false, showPvpWarning = true, passwordEnabled = false, showWelcomeMessage = true, showLeaveMessage = true,
			showProtectionMessage = true, showPreventEntryMessage = true, showPreventExitMessage = true, fireProtection = false, tntProtection = false, creeperProtection = false,
			playCustomSoundUrl = false, permWipeOnEnter = false, permWipeOnExit = false, wipeAndCacheOnEnter = false, wipeAndCacheOnExit = false, forceCommand = false;

	public int LSPS = 0, healthRegen = 0;
	public double velocityWarp = 0;

	public MODE protectionMode = MODE.Whitelist, preventEntryMode = MODE.Whitelist, preventExitMode = MODE.Whitelist, itemMode = MODE.Whitelist;

	public HashMap<Player, Boolean> authentication = new HashMap<Player, Boolean>();
	public HashMap<Player, Long> timeStamps = new HashMap<Player, Long>();
	public ArrayList<Player> playersInRegion = new ArrayList<Player>();
	public HashMap<Player, Boolean> welcomeMessageSent = new HashMap<Player, Boolean>();
	public HashMap<Player, Boolean> leaveMessageSent = new HashMap<Player, Boolean>();
	public HashMap<Player, PlayerInventory> inventoryCache = new HashMap<Player, PlayerInventory>();

	public Region(String owner, String name, Location l1, Location l2, World world, Player p) {
		this.owner = owner;
		this.name = name;
		this.l1 = new RegionLocation(l1.getWorld(), l1.getX(), l1.getY(), l1.getZ());
		this.l2 = new RegionLocation(l2.getWorld(), l2.getX(), l2.getY(), l2.getZ());
		RegionLocation rl1 = new RegionLocation(l1.getWorld(), l1.getX(), l1.getY(), l1.getZ()), rl2 = new RegionLocation(l2.getWorld(), l2.getX(), l2.getY(), l2.getZ());
		this.world = world.getName();
		exceptions.add(owner);
		this.welcomeMessage = ConfigurationData.defaultWelcomeMessage.toString();

		this.leaveMessage = ConfigurationData.defaultLeaveMessage.toString();
		this.protectionMessage = (ConfigurationData.defaultProtectionMessage.toString());
		this.preventEntryMessage = (ConfigurationData.defaultPreventEntryMessage.toString());
		this.preventExitMessage = (ConfigurationData.defaultPreventExitMessage.toString());
		if (ConfigurationData.passwordEnabled) {
			this.passwordEnabled = true;
			this.password = ConfigurationData.password;
		} else {
			this.passwordEnabled = false;
			this.password = "";
		}
		this._protection = ConfigurationData.regionProtected;
		this.preventEntry = ConfigurationData.regionPreventEntry;
		this.mobSpawns = ConfigurationData.mobSpawns;
		this.monsterSpawns = ConfigurationData.monsterSpawns;
		this.healthEnabled = ConfigurationData.healthEnabled;
		this.pvp = ConfigurationData.pvp;
		this.doorsLocked = ConfigurationData.doorsLocked;
		this.chestsLocked = ConfigurationData.chestsLocked;
		this.preventInteraction = ConfigurationData.preventInteraction;
		this.showPvpWarning = ConfigurationData.showPvpWarning;
		this.LSPS = ConfigurationData.LSPS;
		this.healthRegen = ConfigurationData.healthRegen;
		this.velocityWarp = ConfigurationData.velocityWarp;
		this.protectionMode = ConfigurationData.protectionMode;
		this.preventEntryMode = ConfigurationData.preventEntryMode;
		this.preventExitMode = ConfigurationData.preventExitMode;
		this.preventExit = ConfigurationData.regionPreventExit;
		this.authenticationRequiredMessage = colourFormat(ConfigurationData.defaultAuthenticationMessage);
		this.authenticationSuccessMessage = colourFormat(ConfigurationData.defaultAuthenticationSuccessMessage);
		this.spoutEntryMaterial = ConfigurationData.defaultSpoutWelcomeMaterial;
		this.spoutExitMaterial = ConfigurationData.defaultSpoutLeaveMaterial;
		this.spoutEntryMessage = "Welcome to [NAME]";
		this.spoutExitMessage = "You left [NAME]";
		this.showWelcomeMessage = ConfigurationData.showWelcomeMessage;
		this.showLeaveMessage = ConfigurationData.showLeaveMessage;
		this.showProtectionMessage = ConfigurationData.showProtectionMessage;
		this.showPreventEntryMessage = ConfigurationData.showPreventEntryMessage;
		this.showPreventExitMessage = ConfigurationData.showPreventExitMessage;
		this.fireProtection = ConfigurationData.fireProtection;
		this.tntProtection = ConfigurationData.tntProtection;
		this.creeperProtection = ConfigurationData.creeperProtection;
		this.permWipeOnEnter = ConfigurationData.permWipeOnEnter;
		this.permWipeOnExit = ConfigurationData.permWipeOnExit;
		this.wipeAndCacheOnEnter = ConfigurationData.wipeAndCacheOnEnter;
		this.wipeAndCacheOnExit = ConfigurationData.wipeAndCacheOnExit;
		this.forceCommand = ConfigurationData.forceCommand;
		this.commandSet = ConfigurationData.commandSet;
		chunkGrid = new ChunkGrid(l1, l2, this);
		GlobalRegionManager.addRegion(this);
		if (p != null) {
			saveable.saveRegion(this, rl1, rl2);
		}
		this.welcomeMessage = colourFormat(welcomeMessage);
		this.leaveMessage = colourFormat(leaveMessage);
		this.protectionMessage = colourFormat(protectionMessage);
		this.preventEntryMessage = colourFormat(preventEntryMessage);
		this.preventExitMessage = colourFormat(preventExitMessage);

		if (this.LSPS > 0 && !LightningRunner.doesStikesContain(this)) {
			LightningRunner.addRegion(this);
		} else if (this.LSPS == 0 && LightningRunner.doesStikesContain(this)) {
			LightningRunner.removeRegion(this);
		}
	}

	public String getName() {
		return this.name;
	}

	public String getOwner() {
		return this.owner;
	}

	public ChunkGrid getChunkGrid() {
		return this.chunkGrid;
	}

	private boolean isWelcomeMessageSent(Player p) {
		if (!welcomeMessageSent.containsKey(p)) {
			return false;
		} else {
			return welcomeMessageSent.get(p);
		}
	}

	private boolean isLeaveMessageSent(Player p) {
		if (!leaveMessageSent.containsKey(p)) {
			return false;
		} else {
			return leaveMessageSent.get(p);
		}
	}

	private void setTimestamp(Player p) {
		timeStamps.put(p, System.currentTimeMillis());
	}

	public boolean isSendable(Player p) {
		boolean outcome = (timeStamps.containsKey(p) ? (System.currentTimeMillis() > timeStamps.get(p) + 5000) : true);
		if (outcome) {
			setTimestamp(p);
		}
		return outcome;
	}

	public void sendLeaveMessage(Player p) {
		if (!isLeaveMessageSent(p) && isSendable(p)) {
			p.sendMessage(this.liveFormat(leaveMessage, p));
			if (SpoutInterface.doesPlayerHaveSpout(p)) {
				SpoutInterface.sendLeaveMessage(p, this);
				if (this.playCustomSoundUrl) {
					SpoutInterface.stopMusicPlaying(p, region);
				}
			}
			leaveMessageSent.put(p, true);
			welcomeMessageSent.remove(p);
			playersInRegion.remove(p);
			removePlayer(p);
		}
	}

	public void sendWelcomeMessage(Player p) {
		if (!isWelcomeMessageSent(p) && isSendable(p)) {
			p.sendMessage(this.liveFormat(welcomeMessage, p));
			if (SpoutInterface.doesPlayerHaveSpout(p)) {
				SpoutInterface.sendWelcomeMessage(p, this);
				if (this.playCustomSoundUrl) {
					SpoutInterface.playToPlayerMusicFromUrl(p, this);
				}
			}
			welcomeMessageSent.put(p, true);
			leaveMessageSent.remove(p);
			addPlayer(p);
		}
	}

	public String liveFormat(String original, Player p) {
		original = original.replaceAll("\\[", "");
		original = original.replaceAll("\\]", "");
		if (original.contains("PLAYER-COUNT")) {
			original = original.replaceAll("PLAYER-COUNT", "" + this.getPlayersInRegion().size());
		}
		if (original.contains("BUILD-RIGHTS")) {
			original = original.replaceAll("BUILD-RIGHTS", "" + this.canBuild(p));
		}
		return original;
	}

	public void addException(String exception) {
		this.exceptions.add(exception);
	}

	public void addExceptionNode(String node) {
		nodes.add(node);
	}

	public ArrayList<String> getExceptionNodes() {
		return this.nodes;
	}

	public ArrayList<Player> getPlayersInRegion() {
		return this.playersInRegion;
	}

	public void sendBuildMessage(Player p) {
		if (this.showProtectionMessage && isSendable(p)) {
			p.sendMessage(protectionMessage);
		}
	}

	public void sendPreventEntryMessage(Player p) {
		if (this.showPreventEntryMessage && isSendable(p)) {
			p.sendMessage(preventEntryMessage);
		}
	}

	public void sendPreventExitMessage(Player p) {
		if (this.showPreventExitMessage && isSendable(p)) {
			p.sendMessage(preventExitMessage);
		}
	}

	public void addPlayer(Player p) {
		playersInRegion.add(p);
	}

	public void removePlayer(Player p) {
		if (playersInRegion.contains(p)) {
			playersInRegion.remove(p);
		}
	}

	public boolean isPlayerInRegion(Player p) {
		if (playersInRegion.contains(p)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean canBuild(Player p) {
		return super.canBypass(p, this);
	}

	public boolean canBypass(Player p) {
		return super.canBypass(p, this);
	}

	@Override
	public boolean canEnter(Player p) {
		return super.canBypass(p, this);
	}

	@Override
	public boolean canExit(Player p) {
		return super.canBypass(p, this);
	}

	@Override
	public boolean canModify(Player p) {
		return super.canOverride(p, this);
	}

	@Override
	public boolean isProtected() {
		return this._protection;
	}

	@Override
	public boolean isPreventingEntry() {
		return this.preventEntry;
	}

	@Override
	public boolean isPreventingExit() {
		return this.preventExit;
	}

	public boolean canMobsSpawn() {
		return this.mobSpawns;
	}

	public boolean canMonstersSpawn() {
		return this.monsterSpawns;
	}

	public boolean isPasswordEnabled() {
		return this.passwordEnabled;
	}

	public boolean areChestsLocked() {
		return this.chestsLocked;
	}

	public boolean areDoorsLocked() {
		return this.doorsLocked;
	}

	public boolean isHealthEnabled() {
		return this.healthEnabled;
	}

	public int getHealthRegen() {
		return this.healthRegen;
	}

	public double getVelocityWarp() {
		return this.velocityWarp;
	}

	public int getLSPS() {
		return this.LSPS;
	}

	public boolean isPreventingInteraction() {
		return this.preventInteraction;
	}

	public boolean isPvpEnabled() {
		return this.pvp;
	}

	public String getPassword() {
		return this.password;
	}

	public boolean isForcingCommand() {
		return this.forceCommand;
	}

	public String[] getCommandSets() {
		return this.commandSet;
	}

	public String[] getMusicUrls() {
		return this.customSoundUrl;
	}

	public boolean permWipeOnEnter() {
		return this.permWipeOnEnter;
	}

	public boolean permWipeOnExit() {
		return this.permWipeOnExit;
	}

	public boolean wipeAndCacheOnEnter() {
		return this.wipeAndCacheOnEnter;
	}

	public boolean wipeAndCacheOnExit() {
		return this.wipeAndCacheOnExit;
	}

	public void cacheInventory(Player p) {
		inventoryCache.put(p, p.getInventory());
	}

	public PlayerInventory getInventoryCache(Player p) {
		return inventoryCache.containsKey(p) ? inventoryCache.get(p) : null;
	}

	public boolean getAuthentication(String password, Player p) {
		if (password.equals(this.password)) {
			authentication.put(p, true);
			return true;
		} else {
			authentication.put(p, true);
			return false;
		}
	}

	public boolean isAuthenticated(Player p) {
		if (authentication.containsKey(p)) {
			return authentication.get(p);
		} else {
			return false;
		}
	}

	public void resetAuthentication(Player p) {
		authentication.put(p, false);
	}

	public void sendAuthenticationMessage(Player p) {
		p.sendMessage(this.authenticationRequiredMessage);
	}

	public void sendAuthenticationGrantedMessage(Player p) {
		p.sendMessage(this.authenticationSuccessMessage);
	}

	public RegionLocation getL1() {
		return this.l1;
	}

	public RegionLocation getL2() {
		return this.l2;
	}

	public String colourFormat(String message) {
		message = message.replaceAll("<BLACK>", ChatColor.BLACK + "");
		message = message.replaceAll("<0>", "\u00A70");

		message = message.replaceAll("<DBLUE>", "\u00A71");
		message = message.replaceAll("<1>", "\u00A71");

		message = message.replaceAll("<DGREEN>", "\u00A72");
		message = message.replaceAll("<2>", "\u00A72");

		message = message.replaceAll("<DTEAL>", "\u00A73");
		message = message.replaceAll("<3>", "\u00A73");

		message = message.replaceAll("<DRED>", "\u00A74");
		message = message.replaceAll("<4>", "\u00A74");

		message = message.replaceAll("<PURPLE>", "\u00A75");
		message = message.replaceAll("<5>", "\u00A75");

		message = message.replaceAll("<GOLD>", "\u00A76");
		message = message.replaceAll("<6>", "\u00A76");

		message = message.replaceAll("<GREY>", "\u00A77");
		message = message.replaceAll("<7>", "\u00A77");

		message = message.replaceAll("<DGREY>", "\u00A78");
		message = message.replaceAll("<8>", "\u00A78");

		message = message.replaceAll("<BLUE>", "\u00A79");
		message = message.replaceAll("<9>", "\u00A79");

		message = message.replaceAll("<BGREEN>", "\u00A7a");
		message = message.replaceAll("<A>", "\u00A7a");

		message = message.replaceAll("<TEAL>", "\u00A7b");
		message = message.replaceAll("<B>", "\u00A7b");

		message = message.replaceAll("<RED>", "\u00A7c");
		message = message.replaceAll("<C>", "\u00A7c");

		message = message.replaceAll("<PINK>", "\u00A7d");
		message = message.replaceAll("<D>", "\u00A7d");

		message = message.replaceAll("<YELLOW>", "\u00A7e");
		message = message.replaceAll("<E>", "\u00A7e");

		message = message.replaceAll("<WHITE>", "\u00A7f");
		message = message.replaceAll("<F>", "\u00A7f");

		message = message.replaceAll("\\[", "");
		message = message.replaceAll("\\]", "");
		message = message.replaceAll("OWNER", this.getOwner());
		message = message.replaceAll("NAME", this.name);

		return message;
	}

}
