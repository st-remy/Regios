package couk.Adamki11s.Regios.Versions;

import java.io.File;
import java.io.IOException;

import couk.Adamki11s.Regios.Main.Regios;

public class VersionTracker {

	static final File vtCore = new File("plugins" + File.separator + "Regios" + File.separator + "Versions" + File.separator + "Version Tracker");

	public static void createCurrentTracker() {
		File version = new File(vtCore + File.separator + Regios.version + ".rv");
		if(!version.exists()){
			try {
				version.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			VersionPatcher.runPatch(Regios.version);
		}
	}

}
