package couk.Adamki11s.Regios.SpoutGUI;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericButton;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.gui.PopupScreen;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import couk.Adamki11s.Regios.Data.MODE;
import couk.Adamki11s.Regios.Main.Regios;
import couk.Adamki11s.Regios.Mutable.MutableProtection;
import couk.Adamki11s.Regios.Regions.Region;

public class RegionScreenManager {

	public static HashMap<SpoutPlayer, GenericPopup> popup = new HashMap<SpoutPlayer, GenericPopup>();
	public static HashMap<SpoutPlayer, Integer> page = new HashMap<SpoutPlayer, Integer>();
	public static HashMap<SpoutPlayer, Region> editing = new HashMap<SpoutPlayer, Region>();

	public static GenericButton escButton, pageForward, pageBackwards;

	public static GenericLabel pageTracker;

	public static Plugin plugin;

	private static final int pages = 2;

	public static void drawPanelFramework(SpoutPlayer sp, Region r) {
		
		plugin = Regios.regios;

		editing.put(sp, r);

		if (popup.containsKey(sp)) {
			((GenericPopup) popup.get(sp)).removeWidgets(plugin);
		}

		popup.put((SpoutPlayer) sp, new GenericPopup());

		page.put(sp, 1);

		drawPanelBase(sp);
		RegionScreen1.loadScreen(sp, r, null);
	}

	private static void drawPanelBase(SpoutPlayer sp) {
		InGameHUD hud = sp.getMainScreen();

		GenericTexture texture = new GenericTexture("http://dl.dropbox.com/u/27260323/Regios/GUI/Editor%20GUI%20Texture.png");

		texture.setAnchor(WidgetAnchor.SCALE);
		texture.setWidth(hud.getWidth());
		texture.setHeight(hud.getHeight());
		texture.setPriority(RenderPriority.Highest);

		((GenericPopup) popup.get(sp)).attachWidget(plugin, texture);

		escButton = new GenericButton("Close");
		escButton.setColor(RGB.RED.getColour());
		escButton.setHoverColor(RGB.FIREBRICK.getColour());
		escButton.setX(4);
		escButton.setY(6);
		escButton.setWidth(60);
		escButton.setHeight(20);
		escButton.setTooltip("  Close the Editor");

		((GenericPopup) popup.get(sp)).attachWidget(plugin, escButton);

		pageTracker = new GenericLabel("Page : " + page.get(sp) + " / " + pages);
		pageTracker.setX((hud.getWidth() / 2) - 37);
		pageTracker.setY(hud.getHeight() - 15);
		pageTracker.setWidth(60);
		pageTracker.setTextColor(RGB.YELLOW.getColour());

		((GenericPopup) popup.get(sp)).attachWidget(plugin, pageTracker);

		pageForward = new GenericButton(">");
		pageForward.setWidth(35);
		pageForward.setHeight(20);
		pageForward.setX(390);
		pageForward.setY(hud.getHeight() - 24);
		pageForward.setColor(RGB.RED.getColour());
		pageForward.setHoverColor(RGB.GREEN.getColour());

		((GenericPopup) popup.get(sp)).attachWidget(plugin, pageForward);

		pageBackwards = new GenericButton("<");
		pageBackwards.setWidth(35);
		pageBackwards.setHeight(20);
		pageBackwards.setX(2);
		pageBackwards.setY(hud.getHeight() - 22);
		pageBackwards.setColor(RGB.RED.getColour());
		pageBackwards.setHoverColor(RGB.GREEN.getColour());

		((GenericPopup) popup.get(sp)).attachWidget(plugin, pageBackwards);

		hud.attachPopupScreen((PopupScreen) popup.get(sp));

	}

	public static void nextPage(SpoutPlayer sp) {
		int pageNum = page.get(sp);
		if (pageNum == pages) {
			sp.sendNotification("Error!", "No next page.", Material.FIRE);
			return;
		}
		page.put(sp, pageNum + 1);
		pageTracker.setText("Page : " + page.get(sp) + " / " + pages);
		pageTracker.setDirty(true);
		switch(page.get(sp)){
			case 2 : RegionScreen2.loadScreen(sp, editing.get(sp), RegionScreen1.page1Buttons);
		}
	}

	public static void previousPage(SpoutPlayer sp) {
		int pageNum = page.get(sp);
		if (pageNum == 1) {
			sp.sendNotification("Error!", "No previous page.", Material.FIRE);
			return;
		}
		page.put(sp, pageNum - 1);
		pageTracker.setText("Page : " + page.get(sp) + " / " + pages);
		pageTracker.setDirty(true);
		switch(page.get(sp)){
		case 1 : RegionScreen1.loadScreen(sp, editing.get(sp), RegionScreen2.page2Widgets);
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

	public static enum RGB {
		SPRING_GREEN(new Color((float) 0, (float) 0.803, (float) 0.4)), GREEN(new Color(0 / 255, 255 / 255, 0 / 255)), RED(new Color(255 / 255, 0 / 255, 0 / 255)), MIDNIGHT_BLUE(
				new Color((float) 0.098, (float) 0.098, (float) 0.439)), YELLOW(new Color((float) 255 / 255, (float) 255 / 255, (float) 0 / 255)), WHITE(new Color(255 / 255,
				255 / 255, 255 / 255)), FIREBRICK((new Color((float) 0.698, (float) 0.13, (float) 0.13))), BLACK(new Color(0 / 255, 0 / 255, 0 / 255)),
				DARK_GREY(new Color(128 / 255, 128 / 255, 128 / 255));

		RGB(Color c) {
			this.colour = c;
		}

		public Color getColour() {
			return this.colour;
		}

		private final Color colour;

		@Override
		public String toString() {
			return super.toString().substring(0, 1).toUpperCase() + super.toString().toLowerCase().substring(1, super.toString().length());
		}
	}

}
