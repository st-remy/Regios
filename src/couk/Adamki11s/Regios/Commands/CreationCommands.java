package couk.Adamki11s.Regios.Commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import couk.Adamki11s.Regios.CustomEvents.RegionCreateEvent;
import couk.Adamki11s.Regios.Data.ConfigurationData;
import couk.Adamki11s.Regios.Net.PingManager;
import couk.Adamki11s.Regios.Regions.GlobalRegionManager;
import couk.Adamki11s.Regios.Regions.Region;

public class CreationCommands {

	public static HashMap<Player, Location> point1 = new HashMap<Player, Location>();
	public static HashMap<Player, Location> point2 = new HashMap<Player, Location>();
	public static HashMap<Player, Boolean> setting = new HashMap<Player, Boolean>();

	public static HashMap<Player, Boolean> modding = new HashMap<Player, Boolean>();
	public static HashMap<Player, Location> mod1 = new HashMap<Player, Location>();
	public static HashMap<Player, Location> mod2 = new HashMap<Player, Location>();

	public static HashMap<Player, Region> modRegion = new HashMap<Player, Region>();

	static GlobalRegionManager grm = new GlobalRegionManager();

	public boolean isSetting(Player p) {
		return (setting.containsKey(p) ? setting.get(p) : false);
	}

	public boolean isModding(Player p) {
		return (modding.containsKey(p) ? modding.get(p) : false);
	}

	public void giveTool(Player p) {
		if (isSetting(p)) {
			if (!p.getInventory().contains(new ItemStack(ConfigurationData.defaultSelectionTool, 1))) {
			ItemStack is = new ItemStack(ConfigurationData.defaultSelectionTool, 1);

			p.getInventory().addItem(is);

			if (p.getItemInHand() == new ItemStack(Material.AIR, 0)) {
				p.setItemInHand(is);
			}

		}
			p.sendMessage(ChatColor.RED + "[Regios] You are already setting a region!");
			return;
		} else {
			setting.put(p, true);
			modding.put(p, false);
		}
		if (!p.getInventory().contains(new ItemStack(ConfigurationData.defaultSelectionTool, 1))) {
			ItemStack is = new ItemStack(ConfigurationData.defaultSelectionTool, 1);

			p.getInventory().addItem(is);

			if (p.getItemInHand() == new ItemStack(Material.AIR, 0)) {
				p.setItemInHand(is);
			}

		}
		p.sendMessage(ChatColor.GREEN + "[Regios] Left and right click to select points.");
	}

	public void createRegion(Player p, String name) {
		if (GlobalRegionManager.doesExist(name)) {
			p.sendMessage(ChatColor.RED + "[Regios] A region with name : " + ChatColor.BLUE + name + ChatColor.RED + " already exists!");
			return;
		}
		if(!point1.containsKey(p) || !point2.containsKey(p)){
			p.sendMessage(ChatColor.RED + "[Regios] You must set 2 points!");
			return;
		}
		Region r = new Region(p.getName(), name, point1.get(p), point2.get(p), p.getWorld(), null, true);
		p.sendMessage(ChatColor.GREEN + "[Regios] Region " + ChatColor.BLUE + name + ChatColor.GREEN + " created successfully!");
		clearPoints(p);
		modding.put(p, false);
		setting.put(p, false);
		PingManager.created();
		RegionCreateEvent event = new RegionCreateEvent("RegionCreateEvent");
		event.setProperties(p, r);
        Bukkit.getServer().getPluginManager().callEvent(event);
	}

	public boolean arePointsSet(Player p) {
		return point1.containsKey(p) && point2.containsKey(p);
	}

	public boolean areModPointsSet(Player p) {
		return mod1.containsKey(p) && mod2.containsKey(p);
	}

	public void setFirst(Player p, Location l) {
		if (p.getItemInHand().getType() == ConfigurationData.defaultSelectionTool) {
			point1.put(p, l);
			p.sendMessage(ChatColor.GREEN + "[Regios]" + ChatColor.BLUE + "[1] " + ChatColor.LIGHT_PURPLE + String.format("X : %d, Y : %d, Z : %d", l.getBlockX(), l.getBlockY(), l.getBlockZ()));
		}
	}

	public void setSecond(Player p, Location l) {
		if (p.getItemInHand().getType() == ConfigurationData.defaultSelectionTool) {
			point2.put(p, l);
			p.sendMessage(ChatColor.GREEN + "[Regios]" + ChatColor.BLUE + "[2] " + ChatColor.LIGHT_PURPLE + String.format("X : %d, Y : %d, Z : %d", l.getBlockX(), l.getBlockY(), l.getBlockZ()));
		}
	}

	public void setFirstMod(Player p, Location l) {
		if (p.getItemInHand().getType() == ConfigurationData.defaultSelectionTool) {
			mod1.put(p, l);
			p.sendMessage(ChatColor.GREEN + "[Regios]" + ChatColor.BLUE + "[1] " + ChatColor.LIGHT_PURPLE + String.format("X : %d, Y : %d, Z : %d", l.getBlockX(), l.getBlockY(), l.getBlockZ()));
		}
	}

	public void setSecondMod(Player p, Location l) {
		if (p.getItemInHand().getType() == ConfigurationData.defaultSelectionTool) {
			mod2.put(p, l);
			p.sendMessage(ChatColor.GREEN + "[Regios]" + ChatColor.BLUE + "[2] " + ChatColor.LIGHT_PURPLE + String.format("X : %d, Y : %d, Z : %d", l.getBlockX(), l.getBlockY(), l.getBlockZ()));
		}
	}

	public static void clearAll(Player p) {
		clearPoints(p);
		if (mod1.containsKey(p)) {
			mod1.remove(p);
		}
		if (mod2.containsKey(p)) {
			mod2.remove(p);
		}
		if (setting.containsKey(p)) {
			setting.remove(p);
		}
		if (modding.containsKey(p)) {
			modding.remove(p);
		}
		p.sendMessage(ChatColor.RED + "[Regios] Region setting cancelled.");
	}

	public static void clearPoints(Player p) {
		if (point1.containsKey(p)) {
			point1.remove(p);
		}
		if (point2.containsKey(p)) {
			point2.remove(p);
		}
	}

}