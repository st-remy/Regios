package couk.Adamki11s.Regios.Commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericScreen;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import couk.Adamki11s.Regios.Main.Regios;
import couk.Adamki11s.Regios.SpoutGUI.RegionScreenManager.RGB;

public class HelpCommands {

	static Plugin plugin;

	public void getStandardHelp(Player p, String[] args) {
		String pre = ChatColor.GREEN + "[Regios] ";
		if (args.length == 1) {
			p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] For more help use the commands below.");
			p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help general");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help protection");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help fun");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help data");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help messages");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help inventory");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help modes");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help modify");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help exceptions");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help spout");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help permissions");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help other");
			p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
			return;
		} else if (args.length == 2) {
			p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
			if (args[1].equalsIgnoreCase("general")) {
				p.sendMessage(ChatColor.DARK_RED + "[General]");
				for (GenericLabel gl : generalDataText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[General]");
				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("protection")) {
				p.sendMessage(ChatColor.DARK_RED + "[Protection]");
				for (GenericLabel gl : protectionText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Protection]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("fun")) {
				p.sendMessage(ChatColor.DARK_RED + "[Fun]");
				for (GenericLabel gl : funText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Fun]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("data")) {
				p.sendMessage(ChatColor.DARK_RED + "[Data]");
				for (GenericLabel gl : dataText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Data]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("messages")) {
				p.sendMessage(ChatColor.DARK_RED + "[Messages]");
				for (GenericLabel gl : messagesText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Messages]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("inventory")) {
				p.sendMessage(ChatColor.DARK_RED + "[Inventory]");
				for (GenericLabel gl : inventText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Inventory]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("modes")) {
				p.sendMessage(ChatColor.DARK_RED + "[Modes]");
				for (GenericLabel gl : modeText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Modes]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("modify")) {
				p.sendMessage(ChatColor.DARK_RED + "[Modify]");
				for (GenericLabel gl : modifyText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Modify]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("exceptions")) {
				p.sendMessage(ChatColor.DARK_RED + "[Exceptions]");
				for (GenericLabel gl : exceptionText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Exceptions]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("spout")) {
				p.sendMessage(ChatColor.DARK_RED + "[Spout]");
				for (GenericLabel gl : spoutText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Spout]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("permissions")) {
				p.sendMessage(ChatColor.DARK_RED + "[Permissions]");
				for (GenericLabel gl : permissionsText) {
					p.sendMessage(pre + gl.getText());
				}
				p.sendMessage(ChatColor.DARK_RED + "[Permissions]");

				p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
				return;
			} else if (args[1].equalsIgnoreCase("other")) {
				p.sendMessage(ChatColor.DARK_RED + "[Other]");
				for (GenericLabel gl : otherText) {
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
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help inventory");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help modes");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help modify");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help exceptions");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help spout");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help permissions");
			p.sendMessage(ChatColor.GREEN + "[Regios] /r help other");
			p.sendMessage(ChatColor.LIGHT_PURPLE + "[Regios] -----------------------------------------");
		}
	}

	public static HashMap<SpoutPlayer, GenericPopup> helps = new HashMap<SpoutPlayer, GenericPopup>();

	public static GenericButton escButton, generalData, modes, protection, messages, fun, other, inventory, perms, exceptions, modify, spout, data;

	public static GenericLabel[] generalDataText = { new GenericLabel("/r create <region>"), new GenericLabel("/r delete <region>"),
			new GenericLabel("/r rename <oldname> <newname>"), new GenericLabel("/r info <region>"), new GenericLabel("/r list"), new GenericLabel("/r list-for-sale"),
			new GenericLabel("/r delete <region>"), new GenericLabel("/r reload"), new GenericLabel("/r reload-regions"), new GenericLabel("/r reload-config"),
			new GenericLabel("/r cancel"), new GenericLabel("/r set-owner <region> <owner>") };

	public static GenericLabel[] modeText = { new GenericLabel("/r protection-mode <region> <bl/wl>"), new GenericLabel("/r prevent-entry-mode <region> <bl/wl>"),
			new GenericLabel("/r prevent-exit-mode <region> <bl/wl>"), new GenericLabel("/r item-mode <region> <bl/wl>") };

	public static GenericLabel[] protectionText = { new GenericLabel("/r send-auth <password>"), new GenericLabel("/r protect <region>"),
			new GenericLabel("/r unprotect <region>"), new GenericLabel("/r prevent-entry <region>"), new GenericLabel("/r allow-entry <region>"),
			new GenericLabel("/r prevent-exit <region>"), new GenericLabel("/r allow-exit <region>"), new GenericLabel("/r prevent-interaction <region> <T/F>"),
			new GenericLabel("/r doors-locked <region> <T/F>"), new GenericLabel("/r chests-locked <region> <T/F>"), new GenericLabel("/r set-password <region> <password>"),
			new GenericLabel("/r use-password <region> <T/F>"), new GenericLabel("/r fire-protection <region> <T/F>"), new GenericLabel("/r player-cap <region> <cap>"),
			new GenericLabel("/r block-form <region> <T/F>") };

	public static GenericLabel[] messagesText = { new GenericLabel("/r set-welcome <region> <message>"), new GenericLabel("/r show-welcome <region> <T/F>"),
			new GenericLabel("/r set-leave <region> <message>"), new GenericLabel("/r show-leave <region> <T/F>"), new GenericLabel("/r set-prevent-exit <region> <message>"),
			new GenericLabel("/r show-prevent-exit <region> <T/F>"), new GenericLabel("/r set-prevent-entry <region> <message>"),
			new GenericLabel("/r show-prevent-entry <region> <T/F>"), new GenericLabel("/r set-protection <region> <message>"),
			new GenericLabel("/r show-protection <region> <T/F>"), new GenericLabel("/r show-pvp <region> <T/F>") };

	public static GenericLabel[] funText = { new GenericLabel("/r setwarp"), new GenericLabel("/r warp-to <region>"), new GenericLabel("/r reset-warp <region>"),
			new GenericLabel("/r lsps <region> <rate>"), new GenericLabel("/r pvp <region> <T/F>"), new GenericLabel("/r health-regen <region> <rate>"),
			new GenericLabel("/r health-enabled <region> <T/F>"), new GenericLabel("/r vel-warp <region> <rate>"), new GenericLabel("/r sell <region> <price>"),
			new GenericLabel("/r buy <region>") };

	public static GenericLabel[] otherText = { new GenericLabel("/r mob-spawns <region> <T/F>"), new GenericLabel("/r monster-spawns <region> <T/F>"),
			new GenericLabel("/r check"), new GenericLabel("/r add-cmd-set <region> <cmd>"), new GenericLabel("/r rem-cmd-set <region> <cmd>"),
			new GenericLabel("/r list-cmd-set <region> <cmd>"), new GenericLabel("/r reset-cmd-set <region>"), new GenericLabel("/r use-cmd-set <region> <T/F>") };

	public static GenericLabel[] inventText = { new GenericLabel("/r perm-wipe-entry <region> <T/F>"), new GenericLabel("/r perm-wipe-exit <region> <T/F>"),
			new GenericLabel("/r cache-wipe-entry <region> <T/F>"), new GenericLabel("/r cache-wipe-exit <region> <T/F>") };

	public static GenericLabel[] exceptionText = { new GenericLabel("/r add-ex <region> <player>"), new GenericLabel("/r rem-ex <region> <player>"),
			new GenericLabel("/r list-player-ex <region>"), new GenericLabel("/r erase-player-ex <region>"), new GenericLabel("/r add-item-ex <region> <itemid>"),
			new GenericLabel("/r rem-item-ex <region> <itemid>"), new GenericLabel("/r list-item-ex <region>"), new GenericLabel("/r erase-item-ex <region>"),
			new GenericLabel("/r add-node-ex <region> <node>"), new GenericLabel("/r rem-node-ex <region> <node>"), new GenericLabel("/r list-node-ex <region>"),
			new GenericLabel("/r erase-node-ex <region>"), new GenericLabel("/r add-sub-ex <region> <node>"), new GenericLabel("/r rem-sub-ex <region> <node>"),
			new GenericLabel("/r list-sub-ex <region>"), new GenericLabel("/r reset-sub-ex <region>") };

	public static GenericLabel[] modifyText = { new GenericLabel("/r expand-up <region> <value>"), new GenericLabel("/r expand-down <region> <value>"),
			new GenericLabel("/r expand-out <region> <value>"), new GenericLabel("/r expand-max <region>"), new GenericLabel("/r shrink-up <region> <value>"),
			new GenericLabel("/r shrink-down <region> <value>"), new GenericLabel("/r shrink-in <region> <value>"), new GenericLabel("/r expand-up <region> <value>"),
			new GenericLabel("/r modify <region>"), new GenericLabel("/r modify confirm"), new GenericLabel("/r inherit <toinherit> <inheritfrom>") };

	public static GenericLabel[] spoutText = { new GenericLabel("/r set-spout-welcome <region> <message>"), new GenericLabel("/r set-spout-leave <region> <message>"),
			new GenericLabel("/r set-wlecome-id <region> <itemid>"), new GenericLabel("/r set-leave-id <region> <itemid>"),
			new GenericLabel("/r spout-texture-url <region> <url>"), new GenericLabel("/r use-texture-url <region> <T/F>"),
			new GenericLabel("/r use-music-url <region> <T/F>"), new GenericLabel("/r add-music-url <region> <url>"), new GenericLabel("/r rem-music-url <region> <url>"),
			new GenericLabel("/r reset-music-url <region>") };

	public static GenericLabel[] permissionsText = { new GenericLabel("/r perm-cache-add <region> <node>"), new GenericLabel("/r perm-cache-rem <region> <node>"),
			new GenericLabel("/r perm-cache-list <region>"), new GenericLabel("/r perm-cache-reset <region>"), new GenericLabel("/r perm-add-add <region> <node>"),
			new GenericLabel("/r perm-add-rem <region> <node>"), new GenericLabel("/r perm-add-list <region>"), new GenericLabel("/r perm-add-reset <region>"),
			new GenericLabel("/r perm-rem-add <region> <node>"), new GenericLabel("/r perm-rem-rem <region> <node>"), new GenericLabel("/r perm-rem-list <region>"),
			new GenericLabel("/r perm-rem-reset <region>") };

	public static GenericLabel[] dataText = { new GenericLabel("/r save-region <region> <name>"), new GenericLabel("/r load-region <region> <name>"),
			new GenericLabel("/r list-backups <region>"), new GenericLabel("/r backup-database <region> <name>"), new GenericLabel("/r version"),
			new GenericLabel("/r check"), new GenericLabel("/r info <region>"), new GenericLabel("/r patch") };

	public static void pinLabels(SpoutPlayer sp, GenericLabel[] labels, GenericLabel[] oldLabels) {
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

	public void getSpoutHelp(SpoutPlayer p) {
		
		this.plugin = Regios.regios;
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

		escButton = new GenericButton("Close");
		escButton.setColor(RGB.RED.getColour());
		escButton.setHoverColor(RGB.GREEN.getColour());
		escButton.setX(4);
		escButton.setY(5);
		escButton.setWidth(100);
		escButton.setHeight(20);
		escButton.setTooltip("  Close the help menu");

		((GenericPopup) helps.get(p)).attachWidget(plugin, escButton);

		generalData = new GenericButton("General");
		generalData.setColor(RGB.RED.getColour());
		generalData.setHoverColor(RGB.GREEN.getColour());
		generalData.setX(4);
		generalData.setY(50);
		generalData.setWidth(100);
		generalData.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, generalData);

		protection = new GenericButton("Protection");
		protection.setColor(RGB.RED.getColour());
		protection.setHoverColor(RGB.GREEN.getColour());
		protection.setX(4);
		protection.setY(75);
		protection.setWidth(100);
		protection.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, protection);

		fun = new GenericButton("Fun");
		fun.setColor(RGB.RED.getColour());
		fun.setHoverColor(RGB.GREEN.getColour());
		fun.setX(4);
		fun.setY(100);
		fun.setWidth(100);
		fun.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, fun);

		data = new GenericButton("Data");
		data.setColor(RGB.RED.getColour());
		data.setHoverColor(RGB.GREEN.getColour());
		data.setX(4);
		data.setY(125);
		data.setWidth(100);
		data.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, data);

		messages = new GenericButton("Messages");
		messages.setColor(RGB.RED.getColour());
		messages.setHoverColor(RGB.GREEN.getColour());
		messages.setX(4);
		messages.setY(150);
		messages.setWidth(100);
		messages.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, messages);

		inventory = new GenericButton("Inventory");
		inventory.setColor(RGB.RED.getColour());
		inventory.setHoverColor(RGB.GREEN.getColour());
		inventory.setX(4);
		inventory.setY(175);
		inventory.setWidth(100);
		inventory.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, inventory);

		modes = new GenericButton("Modes");
		modes.setColor(RGB.RED.getColour());
		modes.setHoverColor(RGB.GREEN.getColour());
		modes.setX(322);
		modes.setY(50);
		modes.setWidth(100);
		modes.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, modes);

		modify = new GenericButton("Modify");
		modify.setColor(RGB.RED.getColour());
		modify.setHoverColor(RGB.GREEN.getColour());
		modify.setX(322);
		modify.setY(75);
		modify.setWidth(100);
		modify.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, modify);

		exceptions = new GenericButton("Exceptions");
		exceptions.setColor(RGB.RED.getColour());
		exceptions.setHoverColor(RGB.GREEN.getColour());
		exceptions.setX(322);
		exceptions.setY(100);
		exceptions.setWidth(100);
		exceptions.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, exceptions);

		spout = new GenericButton("Spout");
		spout.setColor(RGB.RED.getColour());
		spout.setHoverColor(RGB.GREEN.getColour());
		spout.setX(322);
		spout.setY(125);
		spout.setWidth(100);
		spout.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, spout);

		perms = new GenericButton("Permissions");
		perms.setColor(RGB.RED.getColour());
		perms.setHoverColor(RGB.GREEN.getColour());
		perms.setX(322);
		perms.setY(150);
		perms.setWidth(100);
		perms.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, perms);

		other = new GenericButton("Other");
		other.setColor(RGB.RED.getColour());
		other.setHoverColor(RGB.GREEN.getColour());
		other.setX(322);
		other.setY(175);
		other.setWidth(100);
		other.setHeight(20);

		((GenericPopup) helps.get(p)).attachWidget(plugin, other);
		
		hud.attachPopupScreen((PopupScreen) helps.get(p));
	}

}
