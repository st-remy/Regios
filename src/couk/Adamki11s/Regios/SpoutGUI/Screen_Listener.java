package couk.Adamki11s.Regios.SpoutGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenCloseEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.ScreenType;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.getspout.spoutapi.sound.SoundEffect;

import couk.Adamki11s.Regios.Commands.HelpCommands;
import couk.Adamki11s.Regios.Data.MODE;
import couk.Adamki11s.Regios.Mutable.MutableFun;
import couk.Adamki11s.Regios.Mutable.MutableMessages;
import couk.Adamki11s.Regios.Mutable.MutableMisc;
import couk.Adamki11s.Regios.Mutable.MutableMobs;
import couk.Adamki11s.Regios.Mutable.MutableModes;
import couk.Adamki11s.Regios.Mutable.MutableProtection;
import couk.Adamki11s.Regios.Mutable.MutableProtectionMisc;
import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.Regios.Scheduler.LockHandler;

public class Screen_Listener extends ScreenListener {

	public static HashMap<SpoutPlayer, GenericLabel[]> oldWidgets = new HashMap<SpoutPlayer, GenericLabel[]>();

	public void onButtonClick(ButtonClickEvent evt) {
		if (evt.getScreenType() == ScreenType.CUSTOM_SCREEN) {
			if (LockHandler.helpLocked) {// &&
										// HelpCommands.occupant.getName().equals(evt.getPlayer().getName())
				helpListener(evt);
				regionControlListener(evt);
			}
			if (LockHandler.editorLocked) {
				if (RegionScreenManager.page.get(evt.getPlayer()) == 1) {
					regionScreen1Listener(evt);
				}
				regionControlListener(evt);
			}
		}
	}

	private void regionControlListener(ButtonClickEvent evt) {
		if (!RegionScreenManager.popup.containsKey(evt.getPlayer())) {
			return;
		}

		SpoutPlayer sp = evt.getPlayer();

		UUID esc = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreenManager.escButton.getId()).getId();
		UUID next = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreenManager.pageForward.getId()).getId();
		UUID prev = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreenManager.pageBackwards.getId()).getId();

		UUID buttonID = evt.getButton().getId();

		if (buttonID == esc) {
			sp.getMainScreen().closePopup();
			return;
		}

		if (buttonID == next) {
			RegionScreenManager.nextPage(sp);
			return;
		}

		if (buttonID == prev) {
			RegionScreenManager.previousPage(sp);
			return;
		}
	}

	private void helpListener(ButtonClickEvent evt) {

		if (!HelpCommands.helps.containsKey(evt.getPlayer())) {
			return;
		}

		UUID esc = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.escButton.getId()).getId();
		UUID general = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.generalData.getId()).getId();
		UUID protection = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.protection.getId()).getId();
		UUID data = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.data.getId()).getId();
		UUID exceptions = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.exceptions.getId()).getId();
		UUID fun = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.fun.getId()).getId();
		UUID invent = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.inventory.getId()).getId();
		UUID message = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.messages.getId()).getId();
		UUID modes = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.modes.getId()).getId();
		UUID modify = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.modify.getId()).getId();
		UUID other = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.other.getId()).getId();
		UUID perms = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.perms.getId()).getId();
		UUID spout = ((GenericPopup) HelpCommands.helps.get(evt.getPlayer())).getWidget(HelpCommands.spout.getId()).getId();

		SpoutPlayer sp = evt.getPlayer();

		UUID buttonID = evt.getButton().getId();
		if (buttonID == esc) {
			evt.getPlayer().getMainScreen().closePopup();
		}

		if (buttonID == general) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.generalDataText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.generalDataText, null);
			}
			oldWidgets.put(sp, HelpCommands.generalDataText);
		}

		if (buttonID == protection) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.protectionText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.protectionText, null);
			}
			oldWidgets.put(sp, HelpCommands.protectionText);
		}

		if (buttonID == data) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.dataText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.dataText, null);
			}
			oldWidgets.put(sp, HelpCommands.dataText);
		}

		if (buttonID == exceptions) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.exceptionText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.exceptionText, null);
			}
			oldWidgets.put(sp, HelpCommands.exceptionText);
		}

		if (buttonID == fun) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.funText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.funText, null);
			}
			oldWidgets.put(sp, HelpCommands.funText);
		}

		if (buttonID == invent) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.inventText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.inventText, null);
			}
			oldWidgets.put(sp, HelpCommands.inventText);
		}

		if (buttonID == message) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.messagesText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.messagesText, null);
			}
			oldWidgets.put(sp, HelpCommands.messagesText);
		}

		if (buttonID == modes) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.modeText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.modeText, null);
			}
			oldWidgets.put(sp, HelpCommands.modeText);
		}

		if (buttonID == modify) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.modifyText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.modifyText, null);
			}
			oldWidgets.put(sp, HelpCommands.modifyText);
		}

		if (buttonID == other) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.otherText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.otherText, null);
			}
			oldWidgets.put(sp, HelpCommands.otherText);
		}

		if (buttonID == perms) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.permissionsText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.permissionsText, null);
			}
			oldWidgets.put(sp, HelpCommands.permissionsText);
		}

		if (buttonID == spout) {
			if (oldWidgets.containsKey(sp)) {
				HelpCommands.pinLabels(sp, HelpCommands.spoutText, oldWidgets.get(sp));
			} else {
				HelpCommands.pinLabels(sp, HelpCommands.spoutText, null);
			}
			oldWidgets.put(sp, HelpCommands.spoutText);
		}
	}

	static final MutableProtection protection = new MutableProtection();
	static final MutableProtectionMisc miscProtection = new MutableProtectionMisc();
	static final MutableMessages msg = new MutableMessages();
	static final MutableFun fun = new MutableFun();
	static final MutableMobs mob = new MutableMobs();
	static final MutableMisc misc = new MutableMisc();
	static final MutableModes modes = new MutableModes();

	private void regionScreen1Listener(ButtonClickEvent evt) {
		if (!RegionScreenManager.popup.containsKey(evt.getPlayer())) {
			return;
		}

		Region r = RegionScreenManager.editing.get(evt.getPlayer());

		UUID protect = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[0].getId()).getId();
		UUID prevententry = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[1].getId()).getId();
		UUID preventexit = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[2].getId()).getId();
		UUID preventinteraction = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[3].getId()).getId();
		UUID doorslocked = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[4].getId()).getId();
		UUID chestslocked = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[5].getId()).getId();
		UUID fireprotection = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[6].getId()).getId();
		UUID blockform = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[7].getId()).getId();
		UUID mobspawn = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[8].getId()).getId();
		UUID monsterspawn = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[9].getId()).getId();
		UUID showwelcome = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[10].getId()).getId();
		UUID showleave = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[11].getId()).getId();
		UUID showprevententry = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[12].getId()).getId();
		UUID showpreventexit = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[13].getId()).getId();
		UUID showprotection = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[14].getId()).getId();
		UUID showpvp = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[15].getId()).getId();
		UUID pvp = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[16].getId()).getId();
		UUID healthenabled = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[17].getId()).getId();
		UUID protectmode = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[18].getId()).getId();
		UUID prevententrymode = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[19].getId()).getId();
		UUID preventexitmode = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[20].getId()).getId();
		UUID itemmode = ((GenericPopup) RegionScreenManager.popup.get(evt.getPlayer())).getWidget(RegionScreen1.page1Buttons[21].getId()).getId();

		SpoutPlayer sp = evt.getPlayer();

		UUID buttonID = evt.getButton().getId();

		if (buttonID == protect) {
			if (r.is_protection()) {
				protection.editUnprotect(r);
				sp.sendNotification("Protection", "Protection Disabled", Material.DIAMOND_BLOCK);
			} else {
				protection.editProtect(r);
				sp.sendNotification("Protection", "Protection Enabled", Material.DIAMOND_BLOCK);
			}
			RegionScreen1.page1Buttons[0].setTooltip(RegionScreenManager.getStatus(r.is_protection()));
			RegionScreen1.page1Buttons[0].setTextColor(RegionScreenManager.getColourToken(r.is_protection()));
			RegionScreen1.page1Buttons[0].setDirty(true);
			return;
		}

		if (buttonID == prevententry) {
			if (r.isPreventEntry()) {
				protection.editAllowEntry(r);
				sp.sendNotification("Prevent Entry", "Prevent Entry Disabled", Material.CHAINMAIL_CHESTPLATE);
			} else {

				protection.editPreventEntry(r);
				sp.sendNotification("Prevent Entry", "Prevent Entry Enabled", Material.CHAINMAIL_CHESTPLATE);
			}
			RegionScreen1.page1Buttons[1].setTooltip(RegionScreenManager.getStatus(r.isPreventEntry()));
			RegionScreen1.page1Buttons[1].setTextColor(RegionScreenManager.getColourToken(r.isPreventEntry()));
			RegionScreen1.page1Buttons[1].setDirty(true);
			return;
		}

		if (buttonID == preventexit) {
			if (r.isPreventExit()) {
				protection.editAllowExit(r);
				sp.sendNotification("Prevent Exit", "Prevent Exit Disabled", Material.CHAINMAIL_CHESTPLATE);
			} else {

				protection.editPreventExit(r);
				sp.sendNotification("Prevent Exit", "Prevent Exit Enabled", Material.CHAINMAIL_CHESTPLATE);
			}
			RegionScreen1.page1Buttons[2].setTooltip(RegionScreenManager.getStatus(r.isPreventExit()));
			RegionScreen1.page1Buttons[2].setTextColor(RegionScreenManager.getColourToken(r.isPreventExit()));
			RegionScreen1.page1Buttons[2].setDirty(true);
			return;
		}

		if (buttonID == preventinteraction) {
			if (r.isPreventingInteraction()) {
				miscProtection.editInteraction(r, false);
				sp.sendNotification("Prevent Interaction", "Interaction Disabled", Material.SHEARS);
			} else {
				miscProtection.editInteraction(r, true);
				sp.sendNotification("Prevent Interaction", "Interaction Enabled", Material.SHEARS);
			}
			RegionScreen1.page1Buttons[3].setTooltip(RegionScreenManager.getStatus(r.isPreventingInteraction()));
			RegionScreen1.page1Buttons[3].setTextColor(RegionScreenManager.getColourToken(r.isPreventingInteraction()));
			RegionScreen1.page1Buttons[3].setDirty(true);
			return;
		}

		if (buttonID == doorslocked) {
			if (r.areDoorsLocked()) {
				miscProtection.editDoorsLocked(r, false);
				sp.sendNotification("Doors Locked", "Door Locking Disabled", Material.IRON_DOOR);
			} else {
				miscProtection.editDoorsLocked(r, true);
				sp.sendNotification("Doors Locked", "Door Locking Enabled", Material.IRON_DOOR);
			}
			RegionScreen1.page1Buttons[4].setTooltip(RegionScreenManager.getStatus(r.areDoorsLocked()));
			RegionScreen1.page1Buttons[4].setTextColor(RegionScreenManager.getColourToken(r.areDoorsLocked()));
			RegionScreen1.page1Buttons[4].setDirty(true);
			return;
		}

		if (buttonID == chestslocked) {
			if (r.isChestsLocked()) {
				miscProtection.editChestsLocked(r, false);
				sp.sendNotification("Chests Locked", "Chest Locking Disabled", Material.CHEST);
			} else {
				miscProtection.editChestsLocked(r, true);
				sp.sendNotification("Chests Locked", "Chest Locking Enabled", Material.CHEST);
			}
			RegionScreen1.page1Buttons[5].setTooltip(RegionScreenManager.getStatus(r.isChestsLocked()));
			RegionScreen1.page1Buttons[5].setTextColor(RegionScreenManager.getColourToken(r.isChestsLocked()));
			RegionScreen1.page1Buttons[5].setDirty(true);
			return;
		}

		if (buttonID == fireprotection) {
			if (r.isFireProtection()) {
				miscProtection.editFireProtection(r, false);
				sp.sendNotification("Fire Protection", "Fire Protection Disabled", Material.FIRE);
			} else {
				miscProtection.editFireProtection(r, true);
				sp.sendNotification("Fire Protection", "Fire Protection Enabled", Material.FIRE);
			}
			RegionScreen1.page1Buttons[6].setTooltip(RegionScreenManager.getStatus(r.isFireProtection()));
			RegionScreen1.page1Buttons[6].setTextColor(RegionScreenManager.getColourToken(r.isFireProtection()));
			RegionScreen1.page1Buttons[6].setDirty(true);
			return;
		}

		if (buttonID == blockform) {
			if (r.isBlockForm()) {
				miscProtection.editBlockForm(r, false);
				sp.sendNotification("Block Form", "Block Form Disabled", Material.SNOW);
			} else {
				miscProtection.editBlockForm(r, true);
				sp.sendNotification("Block Form", "Block Form Enabled", Material.SNOW);
			}
			RegionScreen1.page1Buttons[7].setTooltip(RegionScreenManager.getStatus(r.isBlockForm()));
			RegionScreen1.page1Buttons[7].setTextColor(RegionScreenManager.getColourToken(r.isBlockForm()));
			RegionScreen1.page1Buttons[7].setDirty(true);
			return;
		}

		if (buttonID == mobspawn) {
			if (r.isMobSpawns()) {
				mob.editMobSpawn(r, false);
				sp.sendNotification("Mob Spawns", "Mob Spawns Disabled", Material.RAW_FISH);
			} else {
				mob.editMobSpawn(r, true);
				sp.sendNotification("Mob Spawns", "Mob Spawns Enabled", Material.RAW_FISH);
			}
			RegionScreen1.page1Buttons[8].setTooltip(RegionScreenManager.getStatus(r.isMobSpawns()));
			RegionScreen1.page1Buttons[8].setTextColor(RegionScreenManager.getColourToken(r.isMobSpawns()));
			RegionScreen1.page1Buttons[8].setDirty(true);
			return;
		}

		if (buttonID == monsterspawn) {
			if (r.isMonsterSpawns()) {
				mob.editMonsterSpawn(r, false);
				sp.sendNotification("Monster Spawns", "Monster Spawns Disabled", Material.RAW_FISH);
			} else {
				mob.editMonsterSpawn(r, true);
				sp.sendNotification("Monster Spawns", "Monster Spawns Enabled", Material.RAW_FISH);
			}
			RegionScreen1.page1Buttons[9].setTooltip(RegionScreenManager.getStatus(r.isMonsterSpawns()));
			RegionScreen1.page1Buttons[9].setTextColor(RegionScreenManager.getColourToken(r.isMonsterSpawns()));
			RegionScreen1.page1Buttons[9].setDirty(true);
			return;
		}

		if (buttonID == showwelcome) {
			if (r.isShowWelcomeMessage()) {
				msg.editShowWelcomeMessage(r, false);
				sp.sendNotification("Welcome Message", "Welcome Msg Disabled", Material.BOOK);
			} else {
				msg.editShowWelcomeMessage(r, true);
				sp.sendNotification("Welcome Message", "Welcome Msg Enabled", Material.BOOK);
			}
			RegionScreen1.page1Buttons[10].setTooltip(RegionScreenManager.getStatus(r.isShowWelcomeMessage()));
			RegionScreen1.page1Buttons[10].setTextColor(RegionScreenManager.getColourToken(r.isShowWelcomeMessage()));
			RegionScreen1.page1Buttons[10].setDirty(true);
			return;
		}

		if (buttonID == showleave) {
			if (r.isShowLeaveMessage()) {
				msg.editShowLeaveMessage(r, false);
				sp.sendNotification("Leave Message", "Leave Msg Disabled", Material.BOOK);
			} else {
				msg.editShowLeaveMessage(r, true);
				sp.sendNotification("Leave Message", "Leave Msg Enabled", Material.BOOK);
			}
			RegionScreen1.page1Buttons[11].setTooltip(RegionScreenManager.getStatus(r.isShowLeaveMessage()));
			RegionScreen1.page1Buttons[11].setTextColor(RegionScreenManager.getColourToken(r.isShowLeaveMessage()));
			RegionScreen1.page1Buttons[11].setDirty(true);
			return;
		}

		if (buttonID == showprevententry) {
			if (r.isShowPreventEntryMessage()) {
				msg.editShowPreventEntryMessage(r, false);
				sp.sendNotification("Prev Entry Message", "Prev Entry Msg Disabled", Material.BOOK);
			} else {
				msg.editShowPreventEntryMessage(r, true);
				sp.sendNotification("Prev Entry Message", "Prev Entry Msg Enabled", Material.BOOK);
			}
			RegionScreen1.page1Buttons[12].setTooltip(RegionScreenManager.getStatus(r.isShowPreventEntryMessage()));
			RegionScreen1.page1Buttons[12].setTextColor(RegionScreenManager.getColourToken(r.isShowPreventEntryMessage()));
			RegionScreen1.page1Buttons[12].setDirty(true);
			return;
		}

		if (buttonID == showpreventexit) {
			if (r.isShowPreventExitMessage()) {
				msg.editShowPreventExitMessage(r, false);
				sp.sendNotification("Prev Exit Message", "Prev Exit Msg Disabled", Material.BOOK);
			} else {
				msg.editShowPreventExitMessage(r, true);
				sp.sendNotification("Prev Exit Message", "Prev Exit Msg Enabled", Material.BOOK);
			}
			RegionScreen1.page1Buttons[13].setTooltip(RegionScreenManager.getStatus(r.isShowPreventExitMessage()));
			RegionScreen1.page1Buttons[13].setTextColor(RegionScreenManager.getColourToken(r.isShowPreventExitMessage()));
			RegionScreen1.page1Buttons[13].setDirty(true);
			return;
		}

		if (buttonID == showprotection) {
			if (r.isShowProtectionMessage()) {
				msg.editShowProtectionMessage(r, false);
				sp.sendNotification("Protection Message", "Protection Msg Disabled", Material.BOOK);
			} else {
				msg.editShowProtectionMessage(r, true);
				sp.sendNotification("Protection Message", "Protection Msg Enabled", Material.BOOK);
			}
			RegionScreen1.page1Buttons[14].setTooltip(RegionScreenManager.getStatus(r.isShowProtectionMessage()));
			RegionScreen1.page1Buttons[14].setTextColor(RegionScreenManager.getColourToken(r.isShowProtectionMessage()));
			RegionScreen1.page1Buttons[14].setDirty(true);
			return;
		}

		if (buttonID == showpvp) {
			if (r.isShowPvpWarning()) {
				msg.editShowPvpWarningMessage(r, false);
				sp.sendNotification("PvP Message", "PvP Msg Disabled", Material.BOOK);
			} else {
				msg.editShowPvpWarningMessage(r, true);
				sp.sendNotification("PvP Message", "PvP Msg Enabled", Material.BOOK);
			}
			RegionScreen1.page1Buttons[15].setTooltip(RegionScreenManager.getStatus(r.isShowPvpWarning()));
			RegionScreen1.page1Buttons[15].setTextColor(RegionScreenManager.getColourToken(r.isShowPvpWarning()));
			RegionScreen1.page1Buttons[15].setDirty(true);
			return;
		}

		if (buttonID == pvp) {
			if (r.isPvp()) {
				fun.editPvPEnabled(r, false);
				sp.sendNotification("PvP", "PvP Disabled", Material.DIAMOND_SWORD);
			} else {
				fun.editPvPEnabled(r, true);
				sp.sendNotification("PvP", "PvP Enabled", Material.DIAMOND_SWORD);
			}
			RegionScreen1.page1Buttons[16].setTooltip(RegionScreenManager.getStatus(r.isPvp()));
			RegionScreen1.page1Buttons[16].setTextColor(RegionScreenManager.getColourToken(r.isPvp()));
			RegionScreen1.page1Buttons[16].setDirty(true);
			return;
		}

		if (buttonID == healthenabled) {
			if (r.isHealthEnabled()) {
				fun.editHealthEnabled(r, false);
				sp.sendNotification("Health", "Health Disabled", Material.GOLDEN_APPLE);
			} else {
				fun.editHealthEnabled(r, true);
				sp.sendNotification("Health", "Health Enabled", Material.GOLDEN_APPLE);
			}
			RegionScreen1.page1Buttons[17].setTooltip(RegionScreenManager.getStatus(r.isHealthEnabled()));
			RegionScreen1.page1Buttons[17].setTextColor(RegionScreenManager.getColourToken(r.isHealthEnabled()));
			RegionScreen1.page1Buttons[17].setDirty(true);
			return;
		}

		if (buttonID == protectmode) {
			if (r.getProtectionMode() == MODE.Whitelist) {
				modes.editProtectionMode(r, MODE.Blacklist);
				sp.sendNotification("Protection Mode", "Mode : Blacklist", Material.OBSIDIAN);
			} else if (r.getProtectionMode() == MODE.Blacklist) {
				modes.editProtectionMode(r, MODE.Whitelist);
				sp.sendNotification("Protection Mode", "Mode : Whitelist", Material.OBSIDIAN);
			}
			RegionScreen1.page1Buttons[18].setTooltip(RegionScreenManager.getStatus(r.getProtectionMode()));
			RegionScreen1.page1Buttons[18].setTextColor(RegionScreenManager.getColourToken(r.getProtectionMode()));
			RegionScreen1.page1Buttons[18].setDirty(true);
			return;
		}

		if (buttonID == prevententrymode) {
			if (r.getPreventEntryMode() == MODE.Whitelist) {
				modes.editPreventEntryMode(r, MODE.Blacklist);
				sp.sendNotification("Prevent Entry Mode", "Mode : Blacklist", Material.OBSIDIAN);
			} else if (r.getPreventEntryMode() == MODE.Blacklist) {
				modes.editPreventEntryMode(r, MODE.Whitelist);
				sp.sendNotification("Prevent Entry Mode", "Mode : Whitelist", Material.OBSIDIAN);
			}
			RegionScreen1.page1Buttons[19].setTooltip(RegionScreenManager.getStatus(r.getPreventEntryMode()));
			RegionScreen1.page1Buttons[19].setTextColor(RegionScreenManager.getColourToken(r.getPreventEntryMode()));
			RegionScreen1.page1Buttons[19].setDirty(true);
			return;
		}

		if (buttonID == preventexitmode) {
			if (r.getPreventExitMode() == MODE.Whitelist) {
				modes.editPreventExitMode(r, MODE.Blacklist);
				sp.sendNotification("Prevent Exit Mode", "Mode : Blacklist", Material.OBSIDIAN);
			} else if (r.getPreventExitMode() == MODE.Blacklist) {
				modes.editPreventExitMode(r, MODE.Whitelist);
				sp.sendNotification("Prevent Exit Mode", "Mode : Whitelist", Material.OBSIDIAN);
			}
			RegionScreen1.page1Buttons[20].setTooltip(RegionScreenManager.getStatus(r.getPreventExitMode()));
			RegionScreen1.page1Buttons[20].setTextColor(RegionScreenManager.getColourToken(r.getPreventExitMode()));
			RegionScreen1.page1Buttons[20].setDirty(true);
			return;
		}

		if (buttonID == itemmode) {
			if (r.getItemMode() == MODE.Whitelist) {
				modes.editItemControlMode(r, MODE.Blacklist);
				sp.sendNotification("Item Control Mode", "Mode : Blacklist", Material.OBSIDIAN);
			} else if (r.getItemMode() == MODE.Blacklist) {
				modes.editItemControlMode(r, MODE.Whitelist);
				sp.sendNotification("Item Control Mode", "Mode : Whitelist", Material.OBSIDIAN);
			}
			RegionScreen1.page1Buttons[21].setTooltip(RegionScreenManager.getStatus(r.getItemMode()));
			RegionScreen1.page1Buttons[21].setTextColor(RegionScreenManager.getColourToken(r.getItemMode()));
			RegionScreen1.page1Buttons[21].setDirty(true);
			return;
		}

	}

	public void onScreenClose(ScreenCloseEvent evt) {
		if (evt.getScreenType() == ScreenType.CUSTOM_SCREEN) {
			if(LockHandler.isEditorLocked(evt.getPlayer())){
				LockHandler.editorClosed();
			}
			if(LockHandler.isHelpLocked(evt.getPlayer())){
				LockHandler.helpClosed();
			}
		}
	}

}
