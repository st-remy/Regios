package couk.Adamki11s.Regios.RBF;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import couk.Adamki11s.Regios.Regions.Region;

public class RBF_Save {
	
	public void saveRegion(Region r){
		Location l1 = r.getL1().toBukkitLocation(), l2 = r.getL2().toBukkitLocation();
		World w = l1.getWorld();
		
		int smallx, bigx, smally, bigy, smallz, bigz;
		
		if(l1.getBlockX() > l2.getBlockX()){
			smallx = l2.getBlockX();
			bigx = l1.getBlockX();
		} else {
			smallx = l1.getBlockX();
			bigx = l2.getBlockX();
		}
		
		if(l1.getBlockY() > l2.getBlockY()){
			smally = l2.getBlockY();
			bigy = l1.getBlockY();
		} else {
			smally = l1.getBlockY();
			bigy = l2.getBlockY();
		}
		
		if(l1.getBlockZ() > l2.getBlockZ()){
			smallz = l2.getBlockZ();
			bigz = l1.getBlockZ();
		} else {
			smallz = l1.getBlockZ();
			bigz = l2.getBlockZ();
		}
		
		Location loc1 = new Location(w, smallx, smally, smallz),
		loc2 = new Location(w, bigx, bigy, bigz);
		
		try {
			writeToFile(w, loc1, loc2, r.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeToFile(World w, Location l1, Location l2, String name) throws IOException{
		File backup = new File("plugins" + File.separator + "Regios" + File.separator +
				"Database" + File.separator + name + File.separator + name + ".rbf");
		BufferedWriter out = new BufferedWriter(new FileWriter(backup));
		out.write(w.getName());
		out.newLine();
		for(int x = l1.getBlockX(); x <= l2.getBlockX(); x++){
			for(int y = l1.getBlockY(); y <= l2.getBlockY(); y++){
				for(int z = l1.getBlockZ(); z <= l2.getBlockZ(); z++){
					Block b = w.getBlockAt(new Location(w, x, y, z));
					String construct = x + "|" + y + "|" + z + "|" + b.getTypeId() + "|" + b.getData();
					out.write(construct);
					out.newLine();
				}
			}
		}
		out.flush();
		out.close();
	}

}
