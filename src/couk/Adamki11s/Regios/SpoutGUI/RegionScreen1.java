package couk.Adamki11s.Regios.SpoutGUI;

import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

import couk.Adamki11s.Regios.Data.MODE;
import couk.Adamki11s.Regios.Main.Regios;
import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.Regios.SpoutGUI.RegionScreenManager.RGB;

public class RegionScreen1 {

	/*public static GenericButton[] page1Buttons = { new GenericButton("Protection"), new GenericButton("Prevent Entry"), new GenericButton("Prevent Exit"),
			new GenericButton("Prevent Interaction"), new GenericButton("Doors Locked"), new GenericButton("Chests Locked"), new GenericButton("Fire Protection"),
			new GenericButton("Block Form"), new GenericButton("Mobs Spawn"), new GenericButton("Monsters Spawn"), new GenericButton("Show Welcome"),
			new GenericButton("Show Leave"), new GenericButton("Show Prevent Entry"), new GenericButton("Show Prevent Exit"), new GenericButton("Show Protection"),
			new GenericButton("Show Pvp"), new GenericButton("PvP"), new GenericButton("Health Enabled"), new GenericButton("Protection Mode"),
			new GenericButton("Prevent Entry Mode"), new GenericButton("Prevent Exit Mode"), new GenericButton("Item Mode")};*/

	public static void loadScreen(SpoutPlayer sp, Region r, Object[] oldWidgets, ScreenHolder sh) {
		InGameHUD hud = sp.getMainScreen();

		if (oldWidgets != null) {
			for (Object w : oldWidgets) {
				if (w instanceof Widget) {
					((Widget) w).setVisible(false);
					((Widget) w).setDirty(true);
					//((GenericPopup) RegionScreenManager.popup.get(sp)).getWidget(((Widget) w).getId()).setVisible(false);
				}
			}
		}
		
		for(Widget widg : sh.page1Buttons){
			widg.setPriority(RenderPriority.Lowest);
		}

		int pinX = 18, pinY = 10, index = 0;

		for (GenericButton b : sh.page1Buttons) {
			pinY += 23;
			if (pinY > (hud.getHeight() - 35)) {
				pinY = 33;
				pinX += 130;
			}
			b.setX(pinX);
			b.setY(pinY);
			b.setWidth(125);
			b.setHeight(20);

			switch (index) {
			case 0:
				b.setTextColor(getColourToken(r.is_protection()));
				b.setTooltip(getStatus(r.is_protection()));
				b.setDirty(true);
				break;
			case 1:
				b.setTextColor(getColourToken(r.isPreventEntry()));
				b.setTooltip(getStatus(r.isPreventEntry()));
				b.setDirty(true);
				break;
			case 2:
				b.setTextColor(getColourToken(r.isPreventExit()));
				b.setTooltip(getStatus(r.isPreventExit()));
				b.setDirty(true);
				break;
			case 3:
				b.setTextColor(getColourToken(r.isPreventingInteraction()));
				b.setTooltip(getStatus(r.isPreventingInteraction()));
				b.setDirty(true);
				break;
			case 4:
				b.setTextColor(getColourToken(r.isDoorsLocked()));
				b.setTooltip(getStatus(r.isDoorsLocked()));
				b.setDirty(true);
				break;
			case 5:
				b.setTextColor(getColourToken(r.isChestsLocked()));
				b.setTooltip(getStatus(r.isChestsLocked()));
				b.setDirty(true);
				break;
			case 6:
				b.setTextColor(getColourToken(r.isFireProtection()));
				b.setTooltip(getStatus(r.isFireProtection()));
				b.setDirty(true);
				break;
			case 7:
				b.setTextColor(getColourToken(r.isBlockForm()));
				b.setTooltip(getStatus(r.isBlockForm()));
				b.setDirty(true);
				break;
			case 8:
				b.setTextColor(getColourToken(r.isMobSpawns()));
				b.setTooltip(getStatus(r.isMobSpawns()));
				b.setDirty(true);
				break;
			case 9:
				b.setTextColor(getColourToken(r.isMonsterSpawns()));
				b.setTooltip(getStatus(r.isMonsterSpawns()));
				b.setDirty(true);
				break;
			case 10:
				b.setTextColor(getColourToken(r.isShowWelcomeMessage()));
				b.setTooltip(getStatus(r.isShowWelcomeMessage()));
				b.setDirty(true);
				break;
			case 11:
				b.setTextColor(getColourToken(r.isShowLeaveMessage()));
				b.setTooltip(getStatus(r.isShowLeaveMessage()));
				b.setDirty(true);
				break;
			case 12:
				b.setTextColor(getColourToken(r.isShowPreventEntryMessage()));
				b.setTooltip(getStatus(r.isShowPreventEntryMessage()));
				b.setDirty(true);
				break;
			case 13:
				b.setTextColor(getColourToken(r.isShowPreventExitMessage()));
				b.setTooltip(getStatus(r.isShowPreventExitMessage()));
				b.setDirty(true);
				break;
			case 14:
				b.setTextColor(getColourToken(r.isShowProtectionMessage()));
				b.setTooltip(getStatus(r.isShowProtectionMessage()));
				b.setDirty(true);
				break;
			case 15:
				b.setTextColor(getColourToken(r.isShowPvpWarning()));
				b.setTooltip(getStatus(r.isShowPvpWarning()));
				b.setDirty(true);
				break;
			case 16:
				b.setTextColor(getColourToken(r.isPvp()));
				b.setTooltip(getStatus(r.isPvp()));
				b.setDirty(true);
				break;
			case 17:
				b.setTextColor(getColourToken(r.isHealthEnabled()));
				b.setTooltip(getStatus(r.isHealthEnabled()));
				b.setDirty(true);
				break;
			case 18:
				b.setTextColor(getColourToken(r.getProtectionMode()));
				b.setTooltip(getStatus(r.getProtectionMode()));
				b.setDirty(true);
				break;
			case 19:
				b.setTextColor(getColourToken(r.getPreventEntryMode()));
				b.setTooltip(getStatus(r.getPreventEntryMode()));
				b.setDirty(true);
				break;
			case 20:
				b.setTextColor(getColourToken(r.getPreventExitMode()));
				b.setTooltip(getStatus(r.getPreventExitMode()));
				b.setDirty(true);
				break;
			case 21:
				b.setTextColor(getColourToken(r.getItemMode()));
				b.setTooltip(getStatus(r.getItemMode()));
				b.setDirty(true);
				break;
			}

			
			if (((GenericPopup) RegionScreenManager.popup.get(sp)).containsWidget((Widget) b)) {
				((GenericPopup) RegionScreenManager.popup.get(sp)).getWidget(((Widget) b).getId()).setVisible(true);
				((GenericPopup) RegionScreenManager.popup.get(sp)).getWidget(((Widget) b).getId()).setDirty(true);
			} else {
				((GenericPopup) RegionScreenManager.popup.get(sp)).attachWidget(Regios.regios, (Widget) b);
			}

			index++;
		}
	}

	public static String getStatus(boolean b) {
		if (b) {
			return "  True";
		} else {
			return "  False";
		}
	}

	public static String getStatus(MODE m) {
		if (m == MODE.Whitelist) {
			return "  Whitelist";
		} else {
			return "  Blacklist";
		}
	}
	
	public static Color getColourToken(boolean b) {
		if (b) {
			return RGB.GREEN.getColour();
		} else {
			return RGB.FIREBRICK.getColour();
		}
	}
	
	public static Color getColourToken(MODE m) {
		if (m == MODE.Whitelist) {
			return RGB.WHITE.getColour();
		} else {
			return RGB.DARK_GREY.getColour();
		}
	}

}
