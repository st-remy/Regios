package couk.Adamki11s.Regios.Data;

import java.io.File;
import java.io.IOException;

import org.bukkit.util.config.Configuration;

import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.Regios.Regions.RegionLocation;

public class Saveable {
	
	private final File root = new File("plugins" + File.separator + "Regios"),
	 db_root = new File(root + File.separator + "Database");
	
	public synchronized void saveRegion(Region r, RegionLocation rl1, RegionLocation rl2){
		
		File region_root = new File(db_root + File.separator + r.getName());
		File region_core = new File(region_root + File.separator + r.getName() + ".rz");
		
		region_root.mkdir();
		
		try {
			region_core.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Saving Region '" + r.getName() + "' @ " + region_core.getAbsoluteFile().toString());
		
		Configuration c = new Configuration(region_core);
		
		c.setProperty("Region.Messages.WelcomeMessage", r.welcomeMessage.toString());
		c.setProperty("Region.Messages.LeaveMessage", r.leaveMessage.toString());
		c.setProperty("Region.Messages.ProtectionMessage", r.protectionMessage.toString());
		c.setProperty("Region.Messages.PreventEntryMessage", r.preventEntryMessage.toString());
		c.setProperty("Region.Messages.PreventExitMessage", r.preventExitMessage.toString());
		c.setProperty("Region.Messages.ShowPvpWarning", Boolean.valueOf(r.showPvpWarning));
		
		c.setProperty("Region.Messages.ShowWelcomeMessage", Boolean.valueOf(r.showWelcomeMessage));
		c.setProperty("Region.Messages.ShowLeaveMessage", Boolean.valueOf(r.showLeaveMessage));
		c.setProperty("Region.Messages.ShowProtectionMessage", Boolean.valueOf(r.showProtectionMessage));
		c.setProperty("Region.Messages.ShowPreventEntryMessage", Boolean.valueOf(r.showPreventEntryMessage));
		c.setProperty("Region.Messages.ShowPreventExitMessage", Boolean.valueOf(r.showPreventExitMessage));
		
		c.setProperty("Region.Modes.ItemControlMode", r.itemMode.toString());
		c.setProperty("Region.Modes.ProtectionMode", r.protectionMode.toString());
		c.setProperty("Region.Modes.PreventEntryMode", r.preventEntryMode.toString());
		c.setProperty("Region.Modes.PreventExitMode", r.preventExitMode.toString());
		
		c.setProperty("Region.Inventory.PermWipeOnEnter", r.permWipeOnEnter);
		c.setProperty("Region.Inventory.PermWipeOnExit", r.permWipeOnExit);
		c.setProperty("Region.Inventory.WipeAndCacheOnEnter", r.wipeAndCacheOnEnter);
		c.setProperty("Region.Inventory.WipeAndCacheOnExit", r.wipeAndCacheOnExit);
		
		c.setProperty("Region.Command.ForceCommand", r.forceCommand);
		c.setProperty("Region.Command.CommandSet", "");
		
		c.setProperty("Region.Permissions.TemporaryCache.AddNodes", "");
		c.setProperty("Region.Permissions.PermanentCache.AddNodes", "");
		c.setProperty("Region.Permissions.PermanentCache.RemoveNodes", "");
		
		c.setProperty("Region.General.Protected", Boolean.valueOf(r._protection));
		c.setProperty("Region.General.PreventEntry", Boolean.valueOf(r.preventEntry));
		c.setProperty("Region.General.PreventExit", Boolean.valueOf(r.preventExit));
		c.setProperty("Region.General.PreventInteraction", Boolean.valueOf(r.preventInteraction));
		c.setProperty("Region.General.DoorsLocked", Boolean.valueOf(r.doorsLocked));
		c.setProperty("Region.General.ChestsLocked", Boolean.valueOf(r.chestsLocked));
		c.setProperty("Region.General.Password.Enabled", Boolean.valueOf(r.passwordEnabled));
		c.setProperty("Region.General.Password.Password", r.exCrypt.computeSHA2_384BitHash(r.password.toString()));
		
		c.setProperty("Region.Other.MobSpawns", Boolean.valueOf(r.mobSpawns));
		c.setProperty("Region.Other.MonsterSpawns", Boolean.valueOf(r.monsterSpawns));
		c.setProperty("Region.Other.PvP", Boolean.valueOf(r.pvp));
		c.setProperty("Region.Other.HealthEnabled", Boolean.valueOf(r.healthEnabled));
		c.setProperty("Region.Other.HealthRegen", r.healthRegen);
		c.setProperty("Region.Other.LSPS", r.LSPS);
		c.setProperty("Region.Other.VelocityWarp", r.velocityWarp);
		
		c.setProperty("Region.Essentials.Owner", r.owner.toString());
		c.setProperty("Region.Essentials.SubOwners", "");
		c.setProperty("Region.Essentials.Name", r.name.toString());
		c.setProperty("Region.Essentials.World", r.world.toString());
		c.setProperty("Region.Essentials.Points.Point1", convertLocation(rl1));
		c.setProperty("Region.Essentials.Points.Point2", convertLocation(rl2));
		
		c.setProperty("Region.Spout.Welcome.Message", r.spoutEntryMessage);
		c.setProperty("Region.Spout.Welcome.IconID", r.spoutEntryMaterial.getId());	
		c.setProperty("Region.Spout.Leave.Message", r.spoutExitMessage);
		c.setProperty("Region.Spout.Leave.IconID", r.spoutExitMaterial.getId());
		c.setProperty("Region.Spout.Sound.PlayCustomMusic", r.playCustomSoundUrl);
		c.setProperty("Region.Spout.Sound.CustomMusicURL", r.customSoundUrl);
		
		c.setProperty("Region.Economy.ForSale", r.forSale);
		c.setProperty("Region.Economy.Price", r.salePrice);
		
		c.setProperty("Region.Teleportation.Warp.Location", r.world + ",0,0,0");
		
		c.setProperty("Region.Block.BlockForm.Enabled", r.blockForm);
		c.setProperty("Region.General.PlayerCap.Cap", r.playerCap);
		
		c.save();
		
		new File(region_root + File.separator + "Exceptions").mkdir();
		new File(region_root + File.separator + "Exceptions" + File.separator + "Players").mkdir();
		new File(region_root + File.separator + "Exceptions" + File.separator + "Nodes").mkdir();
		new File(region_root + File.separator + "Items").mkdir();
		new File(region_root + File.separator + "Backups").mkdir();
		new File(region_root + File.separator + "Logs").mkdir();
		try {
			new File(region_root + File.separator + "Logs" + File.separator + r.getName() + ".log").createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			new File(region_root + File.separator + "Exceptions" + File.separator + "Players" + File.separator + r.getOwner() + ".excep").createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Region '" + r.getName() + "' saved successfully!");
	}
	
	public String convertLocation(RegionLocation l){
		return l.getWorld().getName() + "," + l.getX() + "," + l.getY() + "," + l.getZ();
	}
	
	public void deleteRegion(String s){
		if(doesRegionExist(s)){
			File f = new File(db_root + File.separator + s);
			f.delete();
		}
	}
	
	public boolean doesRegionExist(String s){
		File f = new File(db_root + File.separator + s);
		if(f.exists()){
			return true;
		} else {
			return false;
		}
	}

}
