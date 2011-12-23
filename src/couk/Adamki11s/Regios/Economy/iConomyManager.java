package couk.Adamki11s.Regios.Economy;

import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Regions.Region;

import com.iCo6.system.Accounts;

public class iConomyManager extends Buying {
	private static final Accounts accounts = new Accounts();
	
	@SuppressWarnings("static-access")
	private double getBalance(Player p){
		return accounts.get(p.getName()).getHoldings().getBalance();
	}
	
	public boolean canAffordRegion(Player p, int price){
		return getBalance(p) >= price;
	}
	
	@SuppressWarnings("static-access")
	public void buyRegion(Region r, String buyer, String seller, int price){
		accounts.get(seller).getHoldings().add(price);
		accounts.get(buyer).getHoldings().subtract(price);
		super.buy(seller, buyer, r, price);
	}


}
