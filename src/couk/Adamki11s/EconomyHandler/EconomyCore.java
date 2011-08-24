package couk.Adamki11s.EconomyHandler;

import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import com.iConomy.*;
import com.iConomy.system.Account;
import com.iConomy.system.Holdings;

import me.Adamki11s.Regios.Regios;
import me.Adamki11s.Regios.RegiosServerListener;

public class EconomyCore {
	
	static iConomy icon = Regios.iConomy;
	
	public static void buyRegion(String region, double price, Player player, Block block) throws SQLException{
		String playerName = player.getName();
		if(icon.hasAccount(playerName)){
			 Account account = iConomy.getAccount(playerName);
			 Holdings balance = iConomy.getAccount(playerName).getHoldings();
			 if(account != null){					
					if(balance.hasEnough(price)){
						balance.subtract(price);
						EconomyDatabase.buyRegion(region, player, price);
						player.sendMessage(ChatColor.GREEN + "[Regios] Region purchased!");
						block.setTypeId(0);
						return;
					} else {
						player.sendMessage(ChatColor.RED + "[Regios] You do not have enough money to buy this region!");
						return;
					}
			 }
		} else {
			player.sendMessage(ChatColor.RED + "[Regios] You do not have an account!");
			return;
		}
		
	}

}
