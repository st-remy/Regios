package couk.Adamki11s.Regios.SpoutGUI;

import java.util.HashMap;
import java.util.UUID;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.screen.ButtonClickEvent;
import org.getspout.spoutapi.event.screen.ScreenListener;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.getspout.spoutapi.sound.SoundEffect;

import couk.Adamki11s.Regios.Commands.HelpCommands;

public class Screen_Listener extends ScreenListener {

	public static HashMap<SpoutPlayer, GenericLabel[]> oldWidgets = new HashMap<SpoutPlayer, GenericLabel[]>();

	public void onButtonClick(ButtonClickEvent evt) {
		helpListener(evt);
	}
	
	private void helpListener(ButtonClickEvent evt){
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

}
