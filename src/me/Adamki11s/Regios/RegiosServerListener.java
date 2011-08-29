package me.Adamki11s.Regios;

import org.bukkit.event.server.ServerListener;
import com.iConomy.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

public class RegiosServerListener extends ServerListener {
	
	 // Change "MyPlugin" to the name of your MAIN class file.
    // Let's say my plugins MAIN class is: Register.java
    // I would change "MyPlugin" to "Register"
    private Regios plugin;

    public RegiosServerListener(Regios plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginDisable(PluginDisableEvent event) {
    	if (plugin.iConomy != null) {
            if (event.getPlugin().getDescription().getName().equals("iConomy")) {
                plugin.iConomy = null;
                System.out.println("[MyPlugin] un-hooked from iConomy.");
            }
        }
    }

    @Override
    public void onPluginEnable(PluginEnableEvent event) {
    	if (plugin.iConomy == null) {
            Plugin iConomy = plugin.getServer().getPluginManager().getPlugin("iConomy");

            if (iConomy != null) {
                if (iConomy.isEnabled() && iConomy.getClass().getName().equals("com.iConomy.iConomy")) {
                    plugin.iConomy = (iConomy) iConomy;
                    System.out.println("[Regios] iConomy support enabled!");
                    Regios.iConomyEnabled = true;
                } else {
                	System.out.println("[Regios] iConomy support disabled!");
                    Regios.iConomyEnabled = false;
                }
            }
        }
    }

}
