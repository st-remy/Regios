package couk.Adamki11s.Regios.Economy;

import couk.Adamki11s.Regios.Mutable.MutableAdministration;
import couk.Adamki11s.Regios.Regions.Region;

public class Buying {
	
	MutableAdministration admin = new MutableAdministration();
	
	public void buy(String seller, String buyer, Region r, int value){
		admin.setOwner(r, buyer);
		EconomyPending.sendAppropriatePending(seller, buyer, r.getName(), value);
	}

}
