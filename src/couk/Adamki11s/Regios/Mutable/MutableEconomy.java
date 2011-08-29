package couk.Adamki11s.Regios.Mutable;

import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.util.config.Configuration;

import couk.Adamki11s.Regios.Regions.Region;

public class MutableEconomy {

	public void editForSale(Region r, boolean val) {
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Economy.ForSale");
		for (Entry<String, Object> entry : all.entrySet()) {
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Economy.ForSale", val);
		r.setForSale(val);
		c.save();
	}

	public void editSalePrice(Region r, int val) {
		if (val > 0) {
			editForSale(r, true);// Forces the region to be sellable if a price
									// has been set.
		} else {
			editForSale(r, false);
		}
		Configuration c = r.getConfigFile();
		c.load();
		Map<String, Object> all = c.getAll();
		all.remove("Region.Economy.Price");
		for (Entry<String, Object> entry : all.entrySet()) {
			c.setProperty(entry.getKey(), entry.getValue());
		}
		c.setProperty("Region.Economy.Price", val);
		r.setSalePrice(val);
		c.save();
	}

}
