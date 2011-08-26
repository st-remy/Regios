package couk.Adamki11s.Regios.Economy;

import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Regions.Region;

public class BoseEconomyManager {
	
	private double getBalance(String p){
		return EconomyCore.boseconomy.getBankMoneyDouble(p);
	}
	
	public boolean canAffordRegion(String p, int price){
		return getBalance(p) >= price;
	}
	
	public void buyRegion(Region r, String buyer, String seller, int price){
		EconomyCore.boseconomy.addPlayerMoney(seller, (double)price, false);
		EconomyCore.boseconomy.setBankMoney(buyer, (double)(getBalance(seller) + (double) price), false);
	}

}
