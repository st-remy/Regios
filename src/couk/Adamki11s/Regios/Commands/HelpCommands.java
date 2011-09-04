package couk.Adamki11s.Regios.Commands;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import couk.Adamki11s.Regios.Main.Regios;
import couk.Adamki11s.Regios.SpoutGUI.RegionScreenManager.RGB;
import couk.Adamki11s.Regios.SpoutGUI.ScreenHolder;

public class HelpCommands {

	static Plugin plugin;

	public void getStandardHelp(Player p, String[] args, ScreenHolder sh) {
		String pre = ChatColor.GREEN + "[Regios] ";
		if (args.length == 1) {
			p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] For more help use the commands below.");
			p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help general");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help protection");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help fun");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help data");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help messages");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help sh.inventory");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help sh.modes");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help sh.modify");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help exceptions");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help spout");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help permissions");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help sh.other");
			p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
			return;
		} else if (args.length == 2) {
			p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
			if (args[1].equalsIgnoreCase("general")) {
				p.sendMessage(ChatColor.DARK_RED + "[General]");
				for (GenericLabel gl : sh.generalDataText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[General]");
				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("protection")) {
				p.sendMessage(ChatColor.DARK_RED + "[Protection]");
				for (GenericLabel gl : sh.protectionText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Protection]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("fun")) {
				p.sendMessage(ChatColor.DARK_RED + "[Fun]");
				for (GenericLabel gl : sh.funText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Fun]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("data")) {
				p.sendMessage(ChatColor.DARK_RED + "[Data]");
				for (GenericLabel gl : sh.dataText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Data]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("messages")) {
				p.sendMessage(ChatColor.DARK_RED + "[Messages]");
				for (GenericLabel gl : sh.messagesText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Messages]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("sh.inventory")) {
				p.sendMessage(ChatColor.DARK_RED + "[sh.inventory]");
				for (GenericLabel gl : sh.inventText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[sh.inventory]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("sh.modes")) {
				p.sendMessage(ChatColor.DARK_RED + "[Modes]");
				for (GenericLabel gl : sh.modeText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Modes]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("sh.modify")) {
				p.sendMessage(ChatColor.DARK_RED + "[Modify]");
				for (GenericLabel gl : sh.modifyText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Modify]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("exceptions")) {
				p.sendMessage(ChatColor.DARK_RED + "[Exceptions]");
				for (GenericLabel gl : sh.exceptionText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Exceptions]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("spout")) {
				p.sendMessage(ChatColor.DARK_RED + "[Spout]");
				for (GenericLabel gl : sh.spoutText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Spout]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("permissions")) {
				p.sendMessage(ChatColor.DARK_RED + "[Permissions]");
				for (GenericLabel gl : sh.permissionsText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Permissions]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("sh.other")) {
				p.sendMessage(ChatColor.DARK_RED + "[Other]");
				for (GenericLabel gl : sh.otherText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Other]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			}
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help general");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help protection");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help fun");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help data");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help messages");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help sh.inventory");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help sh.modes");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help sh.modify");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help exceptions");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help spout");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help permissions");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help sh.other");
			p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
		}
	}

	public static HashMap<SpoutPlayer, GenericPopup> helps = new HashMap<SpoutPlayer, GenericPopup>();

	public static void pinLabels(SpoutPlayer sp, GenericLabel[] labels, GenericLabel[] oldLabels, ScreenHolder sh) {
		if (oldLabels != null) {
			for (GenericLabel gl : oldLabels) {
				((GenericPopup) helps.get(sp)).removeWidget(gl);
				gl.setDirty(true);
			}
		}
		final int pinX = 107;
		int pinY = 53;
		for (GenericLabel l : labels) {
			l.setX(pinX);
			l.setY(pinY);
			l.setWidth(150);
			l.setHeight(150);
			((GenericPopup) helps.get(sp)).attachWidget(plugin, l);
			pinY += 10;
		}
	}
	
	public static HashMap<SpoutPlayer, GenericButton> escButton = new HashMap<SpoutPlayer, GenericButton>();

	public void getSpoutHelp(SpoutPlayer p, ScreenHolder sh) {
		
		HelpCommands.plugin = Regios.regios;
		InGameHUD hud = p.getMainScreen();

		if (helps.containsKey(p)) {
			((GenericPopup) helps.get(p)).removeWidgets(plugin);
		}
		
		helps.put((SpoutPlayer) p, new GenericPopup());
		
		GenericTexture texture = new GenericTexture("http://dl.dropbox.com/u/27260323/Regios/GUI/Help%20GUI%20Texture.png");

		texture.setAnchor(WidgetAnchor.SCALE);
		texture.setWidth(hud.getWidth());
		texture.setHeight(hud.getHeight());
		texture.setPriority(RenderPriority.Highest);

		((GenericPopup) helps.get(p)).attachWidget(plugin, texture);

		GenericButton tmp1 = new GenericButton("Close");
		tmp1.setColor(RGB.RED.getColour());
		tmp1.setHoverColor(RGB.FIREBRICK.getColour());
		tmp1.setX(4);
		tmp1.setY(6);
		tmp1.setWidth(60);
		tmp1.setHeight(20);
		tmp1.setTooltip("  Close the Editor");
		
		sh.escButton = tmp1;
		
		escButton.put(p, tmp1);

		((GenericPopup) helps.get(p)).attachWidget(plugin, escButton.get(p));

		sh.generalData = new GenericButton("General");
		sh.generalData.setColor(RGB.RED.getColour());
		sh.generalData.setHoverColor(RGB.GREEN.getColour());
		sh.generalData.setX(4);
		sh.generalData.setY(50);
		sh.generalData.setWidth(100);
		sh.generalData.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.generalData);

		sh.protection = new GenericButton("Protection");
		sh.protection.setColor(RGB.RED.getColour());
		sh.protection.setHoverColor(RGB.GREEN.getColour());
		sh.protection.setX(4);
		sh.protection.setY(75);
		sh.protection.setWidth(100);
		sh.protection.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.protection);

		sh.fun = new GenericButton("Fun");
		sh.fun.setColor(RGB.RED.getColour());
		sh.fun.setHoverColor(RGB.GREEN.getColour());
		sh.fun.setX(4);
		sh.fun.setY(100);
		sh.fun.setWidth(100);
		sh.fun.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.fun);

		sh.data = new GenericButton("Data");
		sh.data.setColor(RGB.RED.getColour());
		sh.data.setHoverColor(RGB.GREEN.getColour());
		sh.data.setX(4);
		sh.data.setY(125);
		sh.data.setWidth(100);
		sh.data.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.data);

		sh.messages = new GenericButton("Messages");
		sh.messages.setColor(RGB.RED.getColour());
		sh.messages.setHoverColor(RGB.GREEN.getColour());
		sh.messages.setX(4);
		sh.messages.setY(150);
		sh.messages.setWidth(100);
		sh.messages.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.messages);

		sh.inventory = new GenericButton("Inventory");
		sh.inventory.setColor(RGB.RED.getColour());
		sh.inventory.setHoverColor(RGB.GREEN.getColour());
		sh.inventory.setX(4);
		sh.inventory.setY(175);
		sh.inventory.setWidth(100);
		sh.inventory.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.inventory);

		sh.modes = new GenericButton("Modes");
		sh.modes.setColor(RGB.RED.getColour());
		sh.modes.setHoverColor(RGB.GREEN.getColour());
		sh.modes.setX(322);
		sh.modes.setY(50);
		sh.modes.setWidth(100);
		sh.modes.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.modes);

		sh.modify = new GenericButton("Modify");
		sh.modify.setColor(RGB.RED.getColour());
		sh.modify.setHoverColor(RGB.GREEN.getColour());
		sh.modify.setX(322);
		sh.modify.setY(75);
		sh.modify.setWidth(100);
		sh.modify.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.modify);

		sh.exceptions = new GenericButton("Exceptions");
		sh.exceptions.setColor(RGB.RED.getColour());
		sh.exceptions.setHoverColor(RGB.GREEN.getColour());
		sh.exceptions.setX(322);
		sh.exceptions.setY(100);
		sh.exceptions.setWidth(100);
		sh.exceptions.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.exceptions);

		sh.spout = new GenericButton("Spout");
		sh.spout.setColor(RGB.RED.getColour());
		sh.spout.setHoverColor(RGB.GREEN.getColour());
		sh.spout.setX(322);
		sh.spout.setY(125);
		sh.spout.setWidth(100);
		sh.spout.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.spout);

		sh.perms = new GenericButton("Permissions");
		sh.perms.setColor(RGB.RED.getColour());
		sh.perms.setHoverColor(RGB.GREEN.getColour());
		sh.perms.setX(322);
		sh.perms.setY(150);
		sh.perms.setWidth(100);
		sh.perms.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.perms);

		sh.other = new GenericButton("Other");
		sh.other.setColor(RGB.RED.getColour());
		sh.other.setHoverColor(RGB.GREEN.getColour());
		sh.other.setX(322);
		sh.other.setY(175);
		sh.other.setWidth(100);
		sh.other.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, sh.other);
		
		hud.attachPopupScreen((PopupScreen) helps.get(p));
	}

}
