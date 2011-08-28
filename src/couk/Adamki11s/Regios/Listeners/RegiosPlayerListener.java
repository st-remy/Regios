package couk.Adamki11s.Regios.Listeners;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.material.Door;

import couk.Adamki11s.Extras.Events.ExtrasEvents;
import couk.Adamki11s.Extras.Regions.ExtrasRegions;
import couk.Adamki11s.Regios.Checks.PermChecks;
import couk.Adamki11s.Regios.Commands.CreationCommands;
import couk.Adamki11s.Regios.Economy.Economy;
import couk.Adamki11s.Regios.Economy.EconomyCore;
import couk.Adamki11s.Regios.Economy.EconomyPending;
import couk.Adamki11s.Regios.Regions.GlobalRegionManager;
import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.Regios.Regions.RegionLocation;
import couk.Adamki11s.Regios.Regions.SubRegionManager;
import couk.Adamki11s.Regios.Scheduler.HealthRegeneration;
import couk.Adamki11s.Regios.Scheduler.LogRunner;
import couk.Adamki11s.Regios.SpoutInterface.SpoutInterface;

public class RegiosPlayerListener extends PlayerListener {

	private enum MSG {
		PROTECTION, AUTHENTICATION, PREVENT_ENTRY, PREVENT_EXIT, ECONOMY;
	}

	private static final GlobalRegionManager grm = new GlobalRegionManager();
	private static final ExtrasEvents extEvt = new ExtrasEvents();
	private static final ExtrasRegions extReg = new ExtrasRegions();
	private static final SubRegionManager srm = new SubRegionManager();
	private static final PermChecks permChecks = new PermChecks();
	private static final CreationCommands creationCommands = new CreationCommands();

	public static HashMap<Player, Region> regionBinding = new HashMap<Player, Region>();
	public static HashMap<Player, Region> currentRegion = new HashMap<Player, Region>();

	private static HashMap<Player, Location> outsideRegionLocation = new HashMap<Player, Location>();
	private static HashMap<Player, Location> insideRegionLocation = new HashMap<Player, Location>();

	public HashMap<Player, Long> timeStampsProtection = new HashMap<Player, Long>();
	public HashMap<Player, Long> timeStampsAuth = new HashMap<Player, Long>();
	public HashMap<Player, Long> timeStampsPreventEntry = new HashMap<Player, Long>();
	public HashMap<Player, Long> timeStampsPreventExit = new HashMap<Player, Long>();
	public HashMap<Player, Long> timeStampsEconomy = new HashMap<Player, Long>();

	private void setTimestamp(Player p, MSG msg) {
		switch (msg) {
		case PROTECTION:
			timeStampsProtection.put(p, System.currentTimeMillis());
			break;
		case AUTHENTICATION:
			timeStampsAuth.put(p, System.currentTimeMillis());
			break;
		case PREVENT_ENTRY:
			timeStampsPreventEntry.put(p, System.currentTimeMillis());
			break;
		case PREVENT_EXIT:
			timeStampsPreventExit.put(p, System.currentTimeMillis());
			break;
		case ECONOMY:
			timeStampsEconomy.put(p, System.currentTimeMillis());
			break;
		}
	}

	private boolean isSendable(Player p, MSG msg) {
		boolean outcome = false;
		switch (msg) {
		case PROTECTION:
			outcome = (timeStampsProtection.containsKey(p) ? (System.currentTimeMillis() > timeStampsProtection.get(p) + 5000) : true);
			break;
		case AUTHENTICATION:
			outcome = (timeStampsAuth.containsKey(p) ? (System.currentTimeMillis() > timeStampsAuth.get(p) + 5000) : true);
			break;
		case PREVENT_ENTRY:
			outcome = (timeStampsPreventEntry.containsKey(p) ? (System.currentTimeMillis() > timeStampsPreventEntry.get(p) + 5000) : true);
			break;
		case PREVENT_EXIT:
			outcome = (timeStampsPreventExit.containsKey(p) ? (System.currentTimeMillis() > timeStampsPreventExit.get(p) + 5000) : true);
			break;
		case ECONOMY:
			outcome = (timeStampsEconomy.containsKey(p) ? (System.currentTimeMillis() > timeStampsEconomy.get(p) + 5000) : true);
			break;
		}
		if (outcome) {
			setTimestamp(p, msg);
		}
		return outcome;
	}
	
	public void onPlayerQuit(PlayerQuitEvent evt){
		Player p = evt.getPlayer();
		if(HealthRegeneration.isRegenerator(p)){
			HealthRegeneration.removeRegenerator(p);
		}
		if(SpoutInterface.doesPlayerHaveSpout(p)){
			SpoutInterface.stopMusicPlaying(p, null);
		}
		for(Region r : GlobalRegionManager.getRegions()){
			if(r.isAuthenticated(p)){
				r.getAuthentication().put(p, false);
			}
		}
	}

	public void onPlayerJoin(PlayerJoinEvent evt) {
		SpoutInterface.spoutEnabled.put(evt.getPlayer(), false);
		if(EconomyPending.isPending(evt.getPlayer())){
			EconomyPending.loadAndSendPending(evt.getPlayer());
		}
	}

	public void onPlayerInteract(PlayerInteractEvent evt) {

		if (evt.getClickedBlock() == null) {
			return;
		}

		Location l = evt.getClickedBlock().getLocation();
		Player p = evt.getPlayer();
		World w = p.getWorld();
		Chunk c = w.getChunkAt(l);
		Region r;
		ArrayList<Region> regionSet = new ArrayList<Region>();

		Block b = evt.getClickedBlock();

		if (creationCommands.isSetting(p)) {
			Action act = evt.getAction();
			if (act == Action.LEFT_CLICK_BLOCK) {
				creationCommands.setFirst(p, evt.getClickedBlock().getLocation());
				return;
			} else if (act == Action.RIGHT_CLICK_BLOCK) {
				creationCommands.setSecond(p, evt.getClickedBlock().getLocation());
				return;
			}
		}

		if (creationCommands.isModding(p)) {
			Action act = evt.getAction();
			if (act == Action.LEFT_CLICK_BLOCK) {
				creationCommands.setFirstMod(p, evt.getClickedBlock().getLocation());
				return;
			} else if (act == Action.RIGHT_CLICK_BLOCK) {
				creationCommands.setSecondMod(p, evt.getClickedBlock().getLocation());
				return;
			}
		}

		if (EconomyCore.isEconomySupportEnabled() && evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if ((b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST || b.getTypeId() == 68)) {
				Sign sign = (Sign) b.getState();
				if (sign.getLine(0).equalsIgnoreCase("[Regios]")) {
					Region region = GlobalRegionManager.getRegion(sign.getLine(1));
					if (region.isForSale()) {
						int price = region.getSalePrice();
						if (EconomyCore.getEconomy() == Economy.ICONOMY) {
							if (!EconomyCore.getiConomyManager().canAffordRegion(p, price)) {
								if (isSendable(p, MSG.ECONOMY)) {
									p.sendMessage(ChatColor.RED + "[Regios] You cannot afford this region!");
								}
								return;
							} else {
								EconomyCore.getiConomyManager().buyRegion(region, p.getName(), region.getOwner(), price);
								p.sendMessage(ChatColor.GREEN + "[Regios] Region " + ChatColor.BLUE + region.getName() + ChatColor.GREEN + " purchased for " + ChatColor.GOLD + price + ChatColor.GREEN + "!");
								EconomyCore.getEconomySigns().removeSign(b.getLocation());
								b.setTypeId(0);
								return;
							}
						} else if (EconomyCore.getEconomy() == Economy.BOSECONOMY) {
							if (!EconomyCore.getBoseEconomyManager().canAffordRegion(p.getName(), price)) {
								if (isSendable(p, MSG.ECONOMY)) {
									p.sendMessage(ChatColor.RED + "[Regios] You cannot afford this region!");
								}
								return;
							} else {
								EconomyCore.getBoseEconomyManager().buyRegion(region, p.getName(), region.getOwner(), price);
								p.sendMessage(ChatColor.GREEN + "[Regios] Region " + ChatColor.BLUE + region.getName() + ChatColor.GREEN + " purchased for " + ChatColor.GOLD + price + ChatColor.GREEN + "!");
								EconomyCore.getEconomySigns().removeSign(b.getLocation());
								b.setTypeId(0);
								return;
							}
						}
					} else {
						p.sendMessage(ChatColor.RED + "[Regios] This region is not for sale, sorry!");
						b.setTypeId(0);
						return;
					}
				}
			}
		}

		for (Region region : GlobalRegionManager.getRegions()) {
			for (Chunk chunk : region.getChunkGrid().getChunks()) {
				if (chunk.getWorld() == w) {
					if (areChunksEqual(chunk, c)) {
						if (!regionSet.contains(region)) {
							regionSet.add(region);
						}
					}
				}
			}
		}

		if (regionSet.isEmpty()) {
			return;
		}

		ArrayList<Region> currentRegionSet = new ArrayList<Region>();

		for (Region reg : regionSet) {
			if (extReg.isInsideCuboid(l, reg.getL1().toBukkitLocation(), reg.getL2().toBukkitLocation())) {
				currentRegionSet.add(reg);
			}
		}

		if (currentRegionSet.isEmpty()) { // If player is in chunk range but not
											// inside region then cancel the
											// check.
			return;
		}

		if (currentRegionSet.size() > 1) {
			r = srm.getCurrentRegion(p, currentRegionSet);
		} else {
			r = currentRegionSet.get(0);
		}

		if (r.isPreventingInteraction()) {
			if (!r.canBuild(p, r)) {
				if (isSendable(p, MSG.PROTECTION)) {
					p.sendMessage(ChatColor.RED + "[Regios] You cannot interact within this region!");
				}
				LogRunner.addLogMessage(r, LogRunner.getPrefix(r) + (" Player '" + p.getName() + "' tried to interact but did not have permissions."));
				return;
			}
		}

		if (b.getTypeId() == 71 || b.getTypeId() == 64) {
			if (r.areDoorsLocked()) {
				if (!r.canBuild(p, r)) {
					if (isSendable(p, MSG.PROTECTION)) {
						p.sendMessage(ChatColor.RED + "[Regios] Doors are locked for this region!");
					}
					LogRunner.addLogMessage(r, LogRunner.getPrefix(r) + (" Player '" + p.getName() + "' tried to open a locked door but did not have permissions."));
					Door d = new Door(b.getType());
					d.setOpen(false);
					evt.setCancelled(true);
				}
			}
		}

	}

	public boolean areChunksEqual(Chunk c1, Chunk c2) {
		return (c1.getX() == c2.getX() && c1.getZ() == c2.getZ());
	}

	public void onPlayerMove(PlayerMoveEvent evt) {

		if (!extEvt.didMove(evt)) {
			return; // Cancel the check if the player did not move
		}

		Player p = evt.getPlayer();
		World w = p.getWorld();
		Chunk c = w.getChunkAt(evt.getPlayer().getLocation());
		Region r;

		ArrayList<Region> regionSet = new ArrayList<Region>();

		for (Region region : GlobalRegionManager.getRegions()) {
			for (Chunk chunk : region.getChunkGrid().getChunks()) {
				if (chunk.getWorld() == w) {
					if (areChunksEqual(chunk, c)) {
						if (!regionSet.contains(region)) {
							regionSet.add(region);
						}
					}
				}
			}
		}

		if (regionSet.isEmpty()) {
			if (evt.getFrom().getBlockY() == evt.getTo().getBlockY()) { // To
																		// prevent
																		// people
																		// getting
																		// stuck
																		// if
																		// jumping
																		// into
																		// a
																		// region
				outsideRegionLocation.put(p, p.getLocation());
			}
			return;
		}

		if (regionBinding.containsKey(p)) {
			Region binding = regionBinding.get(p);
			if (binding == null) {
				return;
			}
			if (binding.isPreventingEntry() && extReg.isInsideCuboid(p, binding.getL1().toBukkitLocation(), binding.getL2().toBukkitLocation())) {
				if (!binding.canEnter(p, binding)) {
					if (!binding.isPasswordEnabled()) {
						p.teleport(outsideRegionLocation.get(p));
						if (isSendable(p, MSG.PREVENT_ENTRY)) {
							binding.sendPreventEntryMessage(p);
						}
						return;
					} else {
						if (!binding.isAuthenticated(p)) {
							if (isSendable(p, MSG.AUTHENTICATION)) {
								binding.sendAuthenticationMessage(p);
							}
							p.teleport(outsideRegionLocation.get(p));
							return;
						}
					}
				}
			}

			if (binding.isPreventingExit() && !extReg.isInsideCuboid(p, binding.getL1().toBukkitLocation(), binding.getL2().toBukkitLocation())) {
				if (!binding.canExit(p, binding)) {
					if (!binding.isPasswordEnabled()) {
						p.teleport(insideRegionLocation.get(p));
						if (isSendable(p, MSG.PREVENT_EXIT)) {
							binding.sendPreventExitMessage(p);
						}
						return;
					} else {
						if (!binding.isAuthenticated(p)) {
							if (isSendable(p, MSG.AUTHENTICATION)) {
								binding.sendAuthenticationMessage(p);
							}
							p.teleport(insideRegionLocation.get(p));
							return;
						}
					}
				}
			}

			if (!extReg.isInsideCuboid(p, binding.getL1().toBukkitLocation(), binding.getL2().toBukkitLocation())) {
				binding.sendLeaveMessage(p);
			}

		}

		ArrayList<Region> currentRegionSet = new ArrayList<Region>();

		for (Region reg : regionSet) {
			if (extReg.isInsideCuboid(p, reg.getL1().toBukkitLocation(), reg.getL2().toBukkitLocation())) {
				currentRegionSet.add(reg);
				insideRegionLocation.put(p, p.getLocation());
			}
		}

		if (currentRegionSet.isEmpty()) { // If player is in chunk range but not
											// inside region then cancel the
											// check.
			if (evt.getFrom().getBlockY() == evt.getTo().getBlockY()) { // To
																		// prevent
																		// people
																		// getting
																		// stuck
																		// if
																		// jumping
																		// into
																		// a
																		// region
				outsideRegionLocation.put(p, p.getLocation());
			}
			return;
		}

		if (currentRegionSet.size() > 1) {
			r = srm.getCurrentRegion(p, currentRegionSet);
			regionBinding.put(p, r);
		} else {
			r = currentRegionSet.get(0);
			regionBinding.put(p, r);
		}

		if (r.isRegionFull(p)) {
			p.teleport(outsideRegionLocation.get(p));
			LogRunner.addLogMessage(r, LogRunner.getPrefix(r) + (" Player '" + p.getName() + "' tried to enter region but it was full."));
			if (isSendable(p, MSG.PREVENT_ENTRY)) {
				p.sendMessage(ChatColor.RED + "[Regios] This region is full! Only " + r.getPlayerCap() + " players are allowed inside at a time.");
			}
			return;
		}

		if (r.isPreventingEntry()) {
			if (!r.canEnter(p)) {
				LogRunner.addLogMessage(r, LogRunner.getPrefix(r) + (" Player '" + p.getName() + "' tried to enter but did not have permissions."));
				p.teleport(outsideRegionLocation.get(p));
				if (isSendable(p, MSG.PREVENT_ENTRY)) {
					r.sendPreventEntryMessage(p);
				}
				return;
			}
		}

		r.sendWelcomeMessage(p);

		// __________________________________
		// ^^^^ Messages & Entry control ^^^^
		// __________________________________

		if (r.getVelocityWarp() != 0) {
			p.setVelocity(p.getLocation().getDirection().multiply(((r.getVelocityWarp()) * (0.3)) / 2).setY(0.1));
		}

	}

	public String getLocation(Location l) {
		return l.getX() + " | " + l.getY() + " | " + l.getZ();
	}

}
