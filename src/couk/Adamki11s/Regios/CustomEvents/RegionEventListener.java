package couk.Adamki11s.Regios.CustomEvents;

import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;

public abstract class RegionEventListener extends CustomEventListener {
	
	@Override
    public void onCustomEvent(Event event) {
        if (event instanceof RegionEnterEvent) {
            this.onRegionEnter((RegionEnterEvent)event);
        } else if(event instanceof RegionExitEvent){
        	this.onRegionExit((RegionExitEvent)event);
        } else if(event instanceof RegionLightningStrikeEvent){
        	this.onRegionLightningStrike((RegionLightningStrikeEvent)event);
        }
    }

    public abstract void onRegionEnter(RegionEnterEvent event);
    
    public abstract void onRegionExit(RegionExitEvent event);
    
    public abstract void onRegionLightningStrike(RegionLightningStrikeEvent event);

}
