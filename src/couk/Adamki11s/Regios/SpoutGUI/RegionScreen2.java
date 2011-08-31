package couk.Adamki11s.Regios.SpoutGUI;

import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTextField;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.Widget;
import org.getspout.spoutapi.player.SpoutPlayer;

import couk.Adamki11s.Regios.Main.Regios;
import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.Regios.SpoutGUI.RegionScreenManager.RGB;

public class RegionScreen2 {

	public static Widget[] page2Widgets = { new GenericTextField(), new GenericTextField(), new GenericTextField(), new GenericTextField(), new GenericTextField(),
			new GenericLabel("Welcome Message"), new GenericLabel("Leave Message"), new GenericLabel("Prevent Entry Message"), new GenericLabel("Prevent Exit Message"),
			new GenericLabel("Protection Message") };

	public static void loadScreen(SpoutPlayer sp, Region r, Object[] oldWidgets) {
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

		((GenericTextField) page2Widgets[0]).setX(15);
		((GenericTextField) page2Widgets[0]).setY(55);
		((GenericTextField) page2Widgets[0]).setFieldColor(RGB.BLACK.getColour());
		((GenericTextField) page2Widgets[0]).setBorderColor(RGB.SPRING_GREEN.getColour());
		((GenericTextField) page2Widgets[0]).setWidth(350);
		((GenericTextField) page2Widgets[0]).setHeight(15);
		((GenericTextField) page2Widgets[0]).setText(r.getWelcomeMessage());

		if (((GenericPopup) RegionScreenManager.popup.get(sp)).containsWidget(page2Widgets[0])) {
			((GenericPopup) RegionScreenManager.popup.get(sp)).getWidget(page2Widgets[0].getId()).setVisible(true);
			((GenericPopup) RegionScreenManager.popup.get(sp)).getWidget(page2Widgets[0].getId()).setDirty(true);
		} else {
			((GenericPopup) RegionScreenManager.popup.get(sp)).attachWidget(Regios.regios, page2Widgets[0]);
		}

	}

}
