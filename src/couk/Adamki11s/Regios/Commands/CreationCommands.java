package couk.Adamki11s.Regios.Commands;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import couk.Adamki11s.Regios.Data.ConfigurationData;
import couk.Adamki11s.Regios.Regions.GlobalRegionManager;
import couk.Adamki11s.Regios.Regions.Region;

public class CreationCommands extends CommandLink{
	
	public static HashMap<Player, Location> point1 = new HashMap<Player, Location>();
	public static HashMap<Player, Location> point2 = new HashMap<Player, Location>();
	public static HashMap<Player, Boolean> setting = new HashMap<Player, Boolean>();
	
	static GlobalRegionManager grm = new GlobalRegionManager();
	
	public boolean isSetting(Player p){
		return (setting.containsKey(p) ? setting.get(p) : false);
	}
	
	public void giveTool(Player p){
		if(isSetting(p)){
			p.sendMessage(ChatColor.RED + "[Regios] You are already setting a region!");
			return;
		} else {
			setting.put(p, true);
		}
		if(!p.getInventory().contains(new ItemStack(ConfigurationData.defaultSelectionTool, 1))){
			ItemStack is = new ItemStack(ConfigurationData.defaultSelectionTool, 1);
			p.getInventory().addItem(is);
			p.setItemInHand(is);
		} else {
			p.getInventory().remove(new ItemStack(ConfigurationData.defaultSelectionTool, 1));
			p.getInventory().setItemInHand(new ItemStack(ConfigurationData.defaultSelectionTool, 1));
		}
		p.sendMessage(ChatColor.GREEN + "[Regios] Left and right click to select points.");
	}
	
	public void createRegion(Player p, String name){
		if(grm.doesExist(name)){
			p.sendMessage(ChatColor.RED + "[Regios] A region with name : " + ChatColor.BLUE + name + ChatColor.RED + " already exists!");
			super.setTag();
			return;
		}
		System.out.println("INSIDE CREATION CORE! ");
		System.out.println(point1.get(p));
		System.out.println(point2.get(p));
		Region r = new Region(p.getName(), name, point1.get(p), point2.get(p), p.getWorld(), p);
		p.sendMessage(ChatColor.GREEN + "[Regios] Region " + ChatColor.BLUE + name + ChatColor.GREEN + " created successfully!");
		clearPoints(p);
	}
	
	public boolean arePointsSet(Player p){
		return point1.containsKey(p) && point2.containsKey(p);
	}
	
	public void setFirst(Player p, Location l){
		if(p.getItemInHand().getType() == ConfigurationData.defaultSelectionTool){
			point1.put(p, l);
			p.sendMessage(ChatColor.GREEN + "[Regios]" + ChatColor.DARK_RED + "[1] " + ChatColor.LIGHT_PURPLE + String.format("X : %d, Y : %d, Z : %d", l.getBlockX(), l.getBlockY(), l.getBlockZ()));
		}
	}
	
	public void setSecond(Player p, Location l){
		if(p.getItemInHand().getType() == ConfigurationData.defaultSelectionTool){
			point2.put(p, l);
			p.sendMessage(ChatColor.GREEN + "[Regios]" + ChatColor.DARK_RED + "[2] " + ChatColor.LIGHT_PURPLE + String.format("X : %d, Y : %d, Z : %d", l.getBlockX(), l.getBlockY(), l.getBlockZ()));
		}
	}
	
	public static void clearPoints(Player p){
		point1.remove(p);
		point2.remove(p);
	}

}
