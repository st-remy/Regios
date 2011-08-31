package couk.Adamki11s.Regios.Scheduler;

import java.util.HashSet;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import couk.Adamki11s.Regios.Commands.HelpCommands;
import couk.Adamki11s.Regios.SpoutGUI.RegionScreenManager;

public class LockHandler {
	
	public static boolean editorLocked, helpLocked;
	
	public static SpoutPlayer editorOccupant, helpOccupant;
	
	public static HashSet<SpoutPlayer> editorQueue = new HashSet<SpoutPlayer>();
	public static HashSet<SpoutPlayer> helpQueue = new HashSet<SpoutPlayer>();
	
	public static void editorClosed(){
		sendEditorQueue();
		editorLocked = false;
		editorOccupant = null;
	}
	
	public static void helpClosed(){
		sendHelpQueue();
		helpLocked = false;
		helpOccupant = null;
	}
	
	public static boolean isEditorLocked(SpoutPlayer sp){
		if(!editorLocked){
			return false;
		} else {
			return (editorOccupant != null ? editorOccupant.getName().equals(sp.getName()) : false);	
		}
	}
	
	public static boolean isHelpLocked(SpoutPlayer sp){
		if(!helpLocked){
			return false;
		} else {
			return (helpOccupant != null ? helpOccupant.getName().equals(sp.getName()) : false);	
		}
	}
	
	public static void addToEditorQueue(Player p) {
		((SpoutPlayer) p).sendNotification("Editing GUI Locked!", "GUI in use, please wait.", Material.BOOK);
		p.sendMessage(ChatColor.GREEN + "[Regios] You will be notified when the editing GUI is available.");
		editorQueue.add(((SpoutPlayer) p));
	editorOccupant.sendNotification(p.getName(), "Wants to use the GUI!", Material.MAP);
	}

	public static void addToHelpQueue(Player p) {
		((SpoutPlayer) p).sendNotification("Editing GUI Locked!", "GUI in use, please wait.", Material.BOOK);
		p.sendMessage(ChatColor.GREEN + "[Regios] You will be notified when the editing GUI is available.");
		helpQueue.add(((SpoutPlayer) p));
		helpOccupant.sendNotification(p.getName(), "Wants to use the GUI!", Material.MAP);
	}

	private static void sendEditorQueue() {
		if(editorQueue.isEmpty()){ return; }
		for(SpoutPlayer sp : editorQueue){
			sp.sendNotification("Editing GUI Free!", "Editing GUI available!", Material.BOOK);
		}
		editorQueue.clear();
	}

	private static void sendHelpQueue() {
		if(helpQueue.isEmpty()){ return; }
		for(SpoutPlayer sp : helpQueue){
			sp.sendNotification("Help GUI Free!", "Help GUI available!", Material.BOOK);
		}
		helpQueue.clear();
	}

}
