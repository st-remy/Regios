package couk.Adamki11s.Regios.SpoutGUI;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;

public class Input_Listener extends InputListener {

	public void onKeyPressedEvent(KeyPressedEvent event) {
		Player player = event.getPlayer();
		if (event.getKey() == Keyboard.KEY_R) {
			// TODO - Launch GUI Mainframe.
		}
	}

}
