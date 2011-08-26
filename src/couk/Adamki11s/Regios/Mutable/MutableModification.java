package couk.Adamki11s.Regios.Mutable;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import org.bukkit.Location;

import org.bukkit.util.config.Configuration;

import couk.Adamki11s.Regios.Data.LoaderCore;
import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.Regios.Regions.RegionLocation;

public class MutableModification {

	private String convertLocation(Location l) {
		return l.getWorld().getName() + "," + l.getX() + "," + l.getY() + "," + l.getZ();
	}

	private Location getBiggerY(Location l1, Location l2) {
		return l1.getBlockY() >= l2.getBlockY() ? l1 : l2;
	}

	private Location getSmallerY(Location l1, Location l2) {
		return l1.getBlockY() <= l2.getBlockY() ? l1 : l2;
	}

	public void editExpandUp(Region r, int value) {
		// need to check which point to modify
		Configuration c = r.getConfigFile();
		c.load();
		Location l = getBiggerY(r.getL1().toBukkitLocation(), r.getL2().toBukkitLocation());
		boolean p1m = false;
		if (r.getL1().equals(new RegionLocation(l.getWorld(), l.getX(), l.getY(), l.getZ()))) {
			p1m = true;
		} else {
			p1m = false;
		}
		l.add(0, value, 0);
		Map<String, Object> all = c.getAll();
		if (p1m) {
			all.remove("Region.Essentials.Points.Point1");
		} else {
			all.remove("Region.Essentials.Points.Point2");
		}
		for (Entry<String, Object> entry : all.entrySet()) {
			c.setProperty(entry.getKey(), entry.getValue());
		}
		if (p1m) {
			c.setProperty("Region.Essentials.Points.Point1", convertLocation(l));
			r.setL1(l.getWorld(), l.getX(), l.getY(), l.getZ());
		} else {
			c.setProperty("Region.Essentials.Points.Point2", convertLocation(l));
			r.setL2(l.getWorld(), l.getX(), l.getY(), l.getZ());
		}
		c.save();
	}

	public void editExpandDown(Region r, int value) {
		Configuration c = r.getConfigFile();
		c.load();
		Location l = getSmallerY(r.getL1().toBukkitLocation(), r.getL2().toBukkitLocation());
		boolean p1m = false;
		if (r.getL1().equals(new RegionLocation(l.getWorld(), l.getX(), l.getY(), l.getZ()))) {
			p1m = true;
		} else {
			p1m = false;
		}
		l.subtract(0, value, 0);
		Map<String, Object> all = c.getAll();
		if (p1m) {
			all.remove("Region.Essentials.Points.Point1");
		} else {
			all.remove("Region.Essentials.Points.Point2");
		}
		for (Entry<String, Object> entry : all.entrySet()) {
			c.setProperty(entry.getKey(), entry.getValue());
		}
		if (p1m) {
			c.setProperty("Region.Essentials.Points.Point1", convertLocation(l));
			r.setL1(l.getWorld(), l.getX(), l.getY(), l.getZ());
		} else {
			c.setProperty("Region.Essentials.Points.Point2", convertLocation(l));
			r.setL2(l.getWorld(), l.getX(), l.getY(), l.getZ());
		}
		c.save();
	}

	public void editShrinkDown(Region r, int value) {
		Configuration c = r.getConfigFile();
		c.load();
		Location l = getBiggerY(r.getL1().toBukkitLocation(), r.getL2().toBukkitLocation());
		boolean p1m = false;
		if (r.getL1().equals(new RegionLocation(l.getWorld(), l.getX(), l.getY(), l.getZ()))) {
			p1m = true;
		} else {
			p1m = false;
		}
		l.subtract(0, value, 0);
		Map<String, Object> all = c.getAll();
		if (p1m) {
			all.remove("Region.Essentials.Points.Point1");
		} else {
			all.remove("Region.Essentials.Points.Point2");
		}
		for (Entry<String, Object> entry : all.entrySet()) {
			c.setProperty(entry.getKey(), entry.getValue());
		}
		if (p1m) {
			c.setProperty("Region.Essentials.Points.Point1", convertLocation(l));
			r.setL1(l.getWorld(), l.getX(), l.getY(), l.getZ());
		} else {
			c.setProperty("Region.Essentials.Points.Point2", convertLocation(l));
			r.setL2(l.getWorld(), l.getX(), l.getY(), l.getZ());
		}
		c.save();
	}
	
	public void editShrinkUp(Region r, int value) {
		Configuration c = r.getConfigFile();
		c.load();
		Location l = getSmallerY(r.getL1().toBukkitLocation(), r.getL2().toBukkitLocation());
		boolean p1m = false;
		if (r.getL1().equals(new RegionLocation(l.getWorld(), l.getX(), l.getY(), l.getZ()))) {
			p1m = true;
		} else {
			p1m = false;
		}
		l.add(0, value, 0);
		Map<String, Object> all = c.getAll();
		if (p1m) {
			all.remove("Region.Essentials.Points.Point1");
		} else {
			all.remove("Region.Essentials.Points.Point2");
		}
		for (Entry<String, Object> entry : all.entrySet()) {
			c.setProperty(entry.getKey(), entry.getValue());
		}
		if (p1m) {
			c.setProperty("Region.Essentials.Points.Point1", convertLocation(l));
			r.setL1(l.getWorld(), l.getX(), l.getY(), l.getZ());
		} else {
			c.setProperty("Region.Essentials.Points.Point2", convertLocation(l));
			r.setL2(l.getWorld(), l.getX(), l.getY(), l.getZ());
		}
		c.save();
	}

	public void editExpandMax(Region r) {
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Essentials.Points.Point1");
		all.remove("Region.Essentials.Points.Point2");
		for (Entry<String, Object> entry : all.entrySet()) {
			c.setProperty(entry.getKey(), entry.getValue());
		}
		r.getL1().setY(0);
		r.getL2().setY(128);
		c.setProperty("Region.Essentials.Points.Point1", convertLocation(r.getL1().toBukkitLocation()));
		c.setProperty("Region.Essentials.Points.Point2", convertLocation(r.getL2().toBukkitLocation()));
		c.save();
	}

	public void editModifyPoints(Region r, Location l1, Location l2) {
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Essentials.Points.Point1");
		all.remove("Region.Essentials.Points.Point2");
		for (Entry<String, Object> entry : all.entrySet()) {
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Essentials.Points.Point1", convertLocation(l1));
		c.setProperty("Region.Essentials.Points.Point2", convertLocation(l2));
		r.setL1(l1.getWorld(), l1.getX(), l1.getY(), l1.getY());
		r.setL1(l2.getWorld(), l2.getX(), l2.getY(), l2.getY());
		c.save();
	}
	
	public void editExpandOut(Region r, int expand){
		boolean l1Bigger = false;
		if(r.getL1().getX() >= r.getL2().getX() && r.getL1().getZ() >= r.getL2().getZ()){
			l1Bigger = true;
		}
		if(l1Bigger){
			r.getL1().add(expand, 0, expand);
			r.getL2().subtract(expand, 0, expand);
		} else {
			r.getL1().subtract(expand, 0, expand);
			r.getL2().add(expand, 0, expand);
		}
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Essentials.Points.Point1");
		all.remove("Region.Essentials.Points.Point2");
		for (Entry<String, Object> entry : all.entrySet()) {
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Essentials.Points.Point1", convertLocation(r.getL1().toBukkitLocation()));
		c.setProperty("Region.Essentials.Points.Point2", convertLocation(r.getL2().toBukkitLocation()));
		c.save();
	}
	
	public void editShrinkIn(Region r, int shrink){
		boolean l1Bigger = false;
		if(r.getL1().getX() >= r.getL2().getX() && r.getL1().getZ() >= r.getL2().getZ()){
			l1Bigger = true;
		}
		if(!l1Bigger){
			r.getL1().add(shrink, 0, shrink);
			r.getL2().subtract(shrink, 0, shrink);
		} else {
			r.getL1().subtract(shrink, 0, shrink);
			r.getL2().add(shrink, 0, shrink);
		}
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Essentials.Points.Point1");
		all.remove("Region.Essentials.Points.Point2");
		for (Entry<String, Object> entry : all.entrySet()) {
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Essentials.Points.Point1", convertLocation(r.getL1().toBukkitLocation()));
		c.setProperty("Region.Essentials.Points.Point2", convertLocation(r.getL2().toBukkitLocation()));
		c.save();
	}

	public void editRename(Region r, String new_name) {
		File f = new File(r.getLogFile().getParentFile() + r.getName() + ".rz");

		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Essentials.Name");
		for (Entry<String, Object> entry : all.entrySet()) {
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Essentials.Name", new_name);
		c.save();

		f.renameTo(new File(r.getLogFile().getParentFile() + new_name + ".rz"));
	}

	public void editDeleteRegion(Region r) {
		File f = r.getLogFile().getParentFile().getParentFile();
		f.delete();
		new LoaderCore().loadRegions(true);
	}

}
