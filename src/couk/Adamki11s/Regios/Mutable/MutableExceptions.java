package couk.Adamki11s.Regios.Mutable;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;

import couk.Adamki11s.Regios.Regions.Region;

public class MutableExceptions {

	/*
	 * Players
	 */

	public void addPlayerException(Region r, String ex) {
		File playerDir = new File(r.getExceptionDirectory() + File.separator + "Players" + File.separator + ex + ".excep");
		try {
			playerDir.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		r.addException(ex);
	}

	public void removePlayerException(Region r, String ex) {
		File playerDir = new File(r.getExceptionDirectory() + File.separator + "Players" + File.separator + ex + ".excep");
		playerDir.delete();
		r.removeException(ex);
	}

	public boolean checkPlayerException(Region r, String ex) {
		for (File f : new File(r.getExceptionDirectory() + File.separator + "Nodes").listFiles()) {
			if (f.getName().substring(0, f.getName().lastIndexOf(".")).equals(ex)) {
				return true;
			}
		}
		return false;
	}

	public String listPlayerExceptions(Region r) {
		StringBuilder sb = new StringBuilder();
		for (File f : new File(r.getExceptionDirectory() + File.separator + "Players").listFiles()) {
			sb.append(ChatColor.BLUE + ", ").append(ChatColor.WHITE + f.getName().substring(0, f.getName().lastIndexOf(".")));
		}
		return sb.toString();
	}
	
	public void eraseAllPlayerExceptions(Region r){
		for (File f : new File(r.getExceptionDirectory() + File.separator + "Nodes").listFiles()) {
			f.delete();
		}
	}

	/*
	 * Nodes
	 */

	public void addNodeException(Region r, String ex) {
		File playerDir = new File(r.getExceptionDirectory() + File.separator + "Nodes" + File.separator + ex + ".excep");
		try {
			playerDir.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		r.addExceptionNode(ex);
	}

	public void removeNodeException(Region r, String ex) {
		File playerDir = new File(r.getExceptionDirectory() + File.separator + "Nodes" + File.separator + ex + ".excep");
		playerDir.delete();
		r.removeExceptionNode(ex);
	}

	public boolean checkNodeException(Region r, String ex) {
		for (File f : new File(r.getExceptionDirectory() + File.separator + "Nodes").listFiles()) {
			if (f.getName().substring(0, f.getName().lastIndexOf(".")).equals(ex)) {
				return true;
			}
		}
		return false;
	}

	public String listNodeExceptions(Region r) {
		StringBuilder sb = new StringBuilder();
		for (File f : new File(r.getExceptionDirectory() + File.separator + "Nodes").listFiles()) {
			sb.append(ChatColor.BLUE + ", ").append( ChatColor.WHITE + f.getName().substring(0, f.getName().lastIndexOf(".")));
		}
		return sb.toString();
	}
	
	public void eraseAllNodeExceptions(Region r){
		for (File f : new File(r.getExceptionDirectory() + File.separator + "Nodes").listFiles()) {
			f.delete();
		}
	}

}
