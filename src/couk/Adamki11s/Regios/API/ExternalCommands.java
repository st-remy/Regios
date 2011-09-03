package couk.Adamki11s.Regios.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ExternalCommands {

	public static HashMap<Plugin, List<ExternalCommandData>> ecd = new HashMap<Plugin, List<ExternalCommandData>>();

	public static void addToExternalCommandData(Plugin p, ExternalCommandData excd) {
		if (ecd.containsKey(p)) {
			List<ExternalCommandData> externalData = ecd.get(p);
			externalData.add(excd);
			ecd.put(p, externalData);
		} else {
			List<ExternalCommandData> externalData = new ArrayList<ExternalCommandData>();
			externalData.add(excd);
			ecd.put(p, externalData);
		}
	}

	public static List<ExternalCommandData> getExternalCommandData(Plugin p) {
		return ecd.get(p);
	}

	public static void processExternalCommands(Player p, String[] args) {
		for (Entry<Plugin, List<ExternalCommandData>> entry : ecd.entrySet()) {
			for (ExternalCommandData internal : entry.getValue()) {
				if (args.length == internal.getArgsLength()) {
					boolean completeMatch = true;
					for (int index = 0; index < args.length; index++) {
						if(!(args[index].equalsIgnoreCase(internal.getArgs()[index]))){
							completeMatch = false;
						}
					}
					if(completeMatch){
						internal.forceInvoke();
						return; //Command was found so invoke it and end the function.
					}
				}
			}
		}
	}

}
