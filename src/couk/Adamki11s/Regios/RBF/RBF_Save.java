package couk.Adamki11s.Regios.RBF;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Permissions.PermissionsCore;
import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.jnbt.ByteArrayTag;
import couk.Adamki11s.jnbt.CompoundTag;
import couk.Adamki11s.jnbt.IntTag;
import couk.Adamki11s.jnbt.NBTInputStream;
import couk.Adamki11s.jnbt.NBTOutputStream;
import couk.Adamki11s.jnbt.Tag;

public class RBF_Save extends PermissionsCore{

	public void saveRegion(Region r, String backupname, Player p) {
		try {

			if(!super.canModifyBasic(r, p)){
				p.sendMessage(ChatColor.RED + "[Regios] You are not permitted to modify this region!");
				return;
			}
			
			if (r == null) {
				p.sendMessage(ChatColor.RED + "[Regios] That Region does not exist!");
				return;
			}

			File f = new File("plugins" + File.separator + "Regios" + File.separator + "Database" + File.separator + r.getName() + File.separator + "Backups" + File.separator
					+ backupname + ".rbf");
			if (!f.exists()) {
				f.createNewFile();
			} else {
				p.sendMessage(ChatColor.RED + "[Regios] A backup with the name " + ChatColor.BLUE + backupname + ChatColor.RED + " already exists!");
				return;
			}

			World w = r.getL1().getWorld();
			Location max = new Location(w, Math.max(r.getL1().getX(), r.getL2().getX()), Math.max(r.getL1().getY(), r.getL2().getY()), Math.max(r.getL1().getZ(), r.getL2()
					.getZ())), min = new Location(w, Math.min(r.getL1().getX(), r.getL2().getX()), Math.min(r.getL1().getY(), r.getL2().getY()), Math.min(r.getL1().getZ(), r
					.getL2().getZ()));

			int width = max.getBlockX() - min.getBlockX();
			int height = max.getBlockY() - min.getBlockY();
			int length = max.getBlockZ() - min.getBlockZ();

			width += 1;
			height += 1;
			length += 1;

			if (width > 65535) {
				p.sendMessage(ChatColor.RED + "[Regios] The width is too large for a .rbf file!");
				p.sendMessage(ChatColor.RED + "[Regios] Max width : 65535. Your size : " + ChatColor.BLUE + width);
				return;
			}
			if (height > 65535) {
				p.sendMessage(ChatColor.RED + "[Regios] The height is too large for a .rbf file!");
				p.sendMessage(ChatColor.RED + "[Regios] Max height : 65535. Your size : " + ChatColor.BLUE + width);
				return;
			}
			if (length > 65535) {
				p.sendMessage(ChatColor.RED + "[Regios] The length is too large for a .rbf file!");
				p.sendMessage(ChatColor.RED + "[Regios] Max length : 65535. Your size : " + ChatColor.BLUE + width);
				return;
			}

			HashMap<String, Tag> backuptag = new HashMap<String, Tag>();

			// Copy
			byte[] blockID = new byte[width * height * length];
			byte[] blockData = new byte[width * height * length];

			int index = 0;

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					for (int z = 0; z < length; z++) {
						blockID[index] = (byte) w.getBlockAt(min.getBlockX() + x, min.getBlockY() + y, min.getBlockZ() + z).getTypeId();
						blockData[index] = (byte) w.getBlockAt(min.getBlockX() + x, min.getBlockY() + y, min.getBlockZ() + z).getData();
						index++;
					}
				}
			}

			backuptag.put("BlockID", new ByteArrayTag("BlockID", blockID));
			backuptag.put("Data", new ByteArrayTag("Data", blockData));
			backuptag.put("StartX", new IntTag("StartX", min.getBlockX()));
			backuptag.put("StartY", new IntTag("StartY", min.getBlockY()));
			backuptag.put("StartZ", new IntTag("StartZ", min.getBlockZ()));
			backuptag.put("XSize", new IntTag("XSize", width));
			backuptag.put("YSize", new IntTag("YSize", height));
			backuptag.put("ZSize", new IntTag("ZSize", length));

			CompoundTag compoundTag = new CompoundTag("RBF", backuptag);

			NBTOutputStream nbt = new NBTOutputStream(new FileOutputStream(f));
			nbt.writeTag(compoundTag);
			nbt.close();
			p.sendMessage(ChatColor.GREEN + "[Regios] Region saved successfully!");
		} catch (Exception ex) {
			p.sendMessage(ChatColor.RED + "[Regios] Error saving region! Stack trace printed in console.");
			ex.printStackTrace();
		}
	}

}
