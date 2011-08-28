package couk.Adamki11s.Regios.RBF;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import couk.Adamki11s.Regios.Permissions.PermissionsCore;
import couk.Adamki11s.Regios.Regions.Region;
import couk.Adamki11s.jnbt.ByteArrayTag;
import couk.Adamki11s.jnbt.CompoundTag;
import couk.Adamki11s.jnbt.IntTag;
import couk.Adamki11s.jnbt.NBTInputStream;
import couk.Adamki11s.jnbt.Tag;

public class RBF_Load extends PermissionsCore {
	
	private Tag getChildTag(Map<String, Tag> items, String key, Class<? extends Tag> expected) {
		Tag tag = items.get(key);
		return tag;
	}
	
	public void loadRegion(Region r, String backupname, Player p) throws IOException {


		if(!super.canModifyMain(r, p)){
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
			p.sendMessage(ChatColor.RED + "[Regios] A backup with the name " + ChatColor.BLUE + backupname + ChatColor.RED + " does not exist!");
			return;
		}

		p.sendMessage(ChatColor.GREEN + "[Regios] Restoring region...");

		World w = p.getWorld();

		FileInputStream fis = new FileInputStream(f);
		NBTInputStream nbt = new NBTInputStream(new GZIPInputStream(fis));

		CompoundTag backuptag = (CompoundTag) nbt.readTag();
		Map<String, Tag> tagCollection = backuptag.getValue();

		if (!backuptag.getName().equals("RBF")) {
			p.sendMessage(ChatColor.RED + "[Regios] Backup file in unexpected format! Tag does not match 'RBF'.");
		}

		int StartX = (Integer) getChildTag(tagCollection, "StartX", IntTag.class).getValue();
		int StartY = (Integer) getChildTag(tagCollection, "StartY", IntTag.class).getValue();
		int StartZ = (Integer) getChildTag(tagCollection, "StartZ", IntTag.class).getValue();

		int width = (Integer) getChildTag(tagCollection, "XSize", IntTag.class).getValue();
		int height = (Integer) getChildTag(tagCollection, "YSize", IntTag.class).getValue();
		int length = (Integer) getChildTag(tagCollection, "ZSize", IntTag.class).getValue();

		byte[] blocks = (byte[]) getChildTag(tagCollection, "BlockID", ByteArrayTag.class).getValue();
		byte[] blockData = (byte[]) getChildTag(tagCollection, "Data", ByteArrayTag.class).getValue();

		int index = 0;

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < length; z++) {
					Block b = w.getBlockAt(StartX + x, StartY + y, StartZ + z);
					b.setTypeId((int) blocks[index]);
					b.setData(blockData[index]);
					index++;
				}
			}
		}

		fis.close();
		nbt.close();

		p.sendMessage(ChatColor.GREEN + "[Regios] Region restored successfully!");
	}

}
