package couk.Adamki11s.EconomyHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

import com.alta189.sqlLibrary.SQLite.sqlCore;
import com.iConomy.iConomy;
import com.iConomy.system.Account;
import com.iConomy.system.Holdings;

import me.Adamki11s.Regios.RegiosFileManager;

public class EconomyDatabase {

	static sqlCore db = RegiosFileManager.dbManage;
	
	public static void changeOwner(String region, Player player){
		String changeOwner = "UPDATE regions SET owner='" + player.getName() + "' WHERE regionname='" + region.toLowerCase() + "';";
		db.updateQuery(changeOwner);
	}
	
	public static void addSign(String region, int price, String player){
		String insertNewRegion = "INSERT INTO economy (regionname, owner, price) values ('" + region.toLowerCase() + "', '" + player + "', '" + price + "');";
		db.insertQuery(insertNewRegion);
	}
	
	public static void buyRegion(String region, Player buyer, double price) throws SQLException{
		String ownerQuery = "SELECT owner FROM economy WHERE regionname='" + region.toLowerCase() + "';";
		ResultSet owner = db.sqlQuery(ownerQuery);
		String toSend = null;
		while(owner.next()){
			toSend = owner.getString("owner");
		}
		Holdings balance = iConomy.getAccount(toSend).getHoldings();
		balance.add(price);
		String deleteFromEconomy = "DELETE FROM economy WHERE regionname='" + region.toLowerCase() + "';";
		db.deleteQuery(deleteFromEconomy);
		String updateRegionsDB = "UPDATE regions SET owner='" + buyer.getName() + "' WHERE regionname='" + region.toLowerCase() + "';";
		RegiosFileManager.doesRegionExist(region, buyer);
		RegiosFileManager.regionOwner[RegiosFileManager.regionIndex] = buyer.getName();
		db.updateQuery(updateRegionsDB);
	}
	
}
