package couk.Adamki11s.Regios.Checks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Regions.Region;

public class ChunkGrid {
	
	public Set<Chunk> chunkGrid = new HashSet<Chunk>();
	public HashMap<Player, ArrayList<Chunk>> playerChunks = new HashMap<Player, ArrayList<Chunk>>();
	
	public Region region;
	
	public Set<Chunk> getChunks(){
		return this.chunkGrid;
	}
	
	public Region getRegion(){
		return this.region;
	}
	
	public boolean isInGrid(Chunk c){
		return chunkGrid.contains(c);
	}
	
	public ChunkGrid(Location l1, Location l2, Region r){ //Initialises the chunk grid for the given region
		World w = l1.getWorld();
		region = r;
		chunkGrid.add(w.getChunkAt(l1));
		chunkGrid.add(w.getChunkAt(l2));
		Location clone1 = l1, clone2 = l2;
		if(l1.getX() < l2.getX()){
			clone1.setX(l2.getX());
			clone2.setX(l1.getX());
		}
		if(l1.getZ() < l2.getZ()){
			clone1.setZ(l2.getZ());
			clone2.setZ(l1.getZ());
		}
		double yperm = l1.getY();
		clone2.subtract(16, 0, 16);
		clone1.add(16, 0, 16);
		for(double z = clone2.getZ(); z <= clone1.getZ(); z += 16){
			for(double x = clone2.getX(); x <= clone1.getX(); x += 16){
				chunkGrid.add(w.getChunkAt(new Location(w, x, yperm, z)));
			}
		}		
		
	}

}
