package couk.Adamki11s.Regios.SpoutInterface;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.event.spout.SpoutCraftEnableEvent;
import org.getspout.spoutapi.event.spout.SpoutListener;

import couk.Adamki11s.Regios.SpoutGUI.CacheHandler;

public class SpoutCraftListener extends SpoutListener {
	
	@Override
    public void onSpoutCraftEnable(SpoutCraftEnableEvent event) {
        SpoutInterface.spoutEnabled.put(((Player)event.getPlayer()), true);
    }

}
