package couk.Adamki11s.Regios.DependancyControl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.logging.Logger;

public class SpoutDependancy {

	public static final File dependCore = new File("plugins" + File.separator + "Regios" + File.separator + "Dependancies");
	static final Logger log = Logger.getLogger("Minecraft.Regios");
	static final String spoutURL = "http://dev.bukkit.org/media/files/538/725/Spout.jar";

	public static boolean doesDependancyExist() {
		File spout = new File(dependCore + File.separator + "Spout.jar");
		return spout.exists();
	}

	public static void downloadDependancy() {
		File spout = new File(dependCore + File.separator + "Spout.jar");
		log.info("[Regios] Spout not found!");
		log.info("[Regios] Downloading Spout dependancy...");
		try {
			BufferedInputStream in = new BufferedInputStream(new

			URL(spoutURL).openStream());
			FileOutputStream fos = new FileOutputStream(spout);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 2048);
			byte[] data = new byte[2048];
			int x = 0;
			while ((x = in.read(data, 0, 2048)) >= 0) {
				bout.write(data, 0, x);
			}
			bout.flush();
			bout.close();
			in.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			log.severe("[Regios] Error occurred whilst download Spout dependancy!");
		}
		log.info("[Regios] Spout dependancy downloaded successfully!");
		log.info("[Regios] Saved to : plugins/Regios/Dependancies/Spout.jar");
		log.info("[Regios] Go and add this to your plugins folder and restart the server.");
	}

}
