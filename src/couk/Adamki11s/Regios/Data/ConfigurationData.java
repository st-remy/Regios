package couk.Adamki11s.Regios.Data;

import org.bukkit.Material;

public class ConfigurationData {
	
	public static String defaultWelcomeMessage, defaultLeaveMessage, defaultProtectionMessage, 
	defaultPreventEntryMessage, defaultPreventExitMessage, password, defaultAuthenticationMessage, defaultAuthenticationSuccessMessage,
	defaulSpoutEntryMessage, defaulSpoutExitMessage, defaultCustomMusicUrl;
	
	public static Material defaultSpoutWelcomeMaterial, defaultSpoutLeaveMaterial, defaultSelectionTool;
	
	public static boolean regionProtected, regionPreventEntry, regionPreventExit, mobSpawns, monsterSpawns, healthEnabled, pvp,
	doorsLocked, chestsLocked, preventInteraction, showPvpWarning, checkForUpdates, downloadUpdatesAuto,
	cacheOldVersions, forceReload, passwordEnabled, showWelcomeMessage, showLeaveMessage, showProtectionMessage, showPreventEntryMessage, showPreventExitMessage,
	fireProtection, tntProtection, creeperProtection, playCustomMusic;
	
	public static int LSPS, healthRegen, velocityWarp;
	
	public static MODE protectionMode, preventEntryMode, preventExitMode, itemMode;
	
	public ConfigurationData(String a, String b, String c, String d, String e, String pass, boolean f, boolean g, boolean h, boolean i, boolean j,
			boolean k, boolean m, boolean n, boolean o, boolean v, boolean passEnabled, int p, int q, int r, MODE s, MODE t, MODE u,
			MODE item, boolean w, boolean x, boolean y, boolean z, boolean exit, String dam, String dasm, Material welcome, Material leave, boolean welcomeMsg,
			boolean leaveMsg, boolean protectMsg, boolean preventEntryMsg, boolean preventExitMsg, boolean tntProt, boolean fireProt, boolean creeperProt,
			String music, boolean playmusic){
		defaultWelcomeMessage = a;
		defaultLeaveMessage = b;
		defaultProtectionMessage = c;
		defaultPreventEntryMessage = d;
		defaultPreventExitMessage = e;
		regionProtected = f;
		regionPreventEntry = g;
		mobSpawns = h;
		monsterSpawns = i;
		healthEnabled = j;
		pvp = k;
		doorsLocked = m;
		chestsLocked = n;
		preventInteraction = o;
		LSPS = p;
		healthRegen = q;
		velocityWarp = r;
		protectionMode = s;
		preventEntryMode = t;
		preventExitMode = u;
		showPvpWarning = v;
		checkForUpdates = w;
		downloadUpdatesAuto = x;
		cacheOldVersions = y;
		forceReload = z;
		passwordEnabled = passEnabled;
		password = pass;
		itemMode = item;
		regionPreventExit = exit;
		defaultAuthenticationMessage = dam;
		defaultAuthenticationSuccessMessage = dasm;
		defaultSpoutWelcomeMaterial = welcome;
		defaultSpoutLeaveMaterial = leave;
		showWelcomeMessage = welcomeMsg;
		showLeaveMessage = leaveMsg;
		showProtectionMessage = protectMsg;
		showPreventEntryMessage = preventEntryMsg;
		showPreventExitMessage = preventExitMsg;
		fireProtection = fireProt;
		tntProtection = tntProt;
		creeperProtection = creeperProt;
		defaultCustomMusicUrl = music;
		playCustomMusic = playmusic;
	}

}
