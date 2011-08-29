package couk.Adamki11s.WorldConfiguration;

public class ConfigurationSettings {
	
	public static boolean fireEnabled[] = new boolean[100];
	public static boolean lavaEnabled[] = new boolean[100];
	public static boolean waterEnabled[] = new boolean[100];
	public static boolean TNTEnabled[] = new boolean[100];
	public static boolean lightningEnabled[] = new boolean[100];
	public static boolean monstersSpawn[] = new boolean[100];
	public static boolean mobsSpawn[] = new boolean[100];
	public static boolean creepersExplode[] = new boolean[100];
	public static String worldName[] = new String[100];
	public static int size;
	
	public static void setVariables(int index, boolean lE, boolean fE, boolean lavaE, boolean tE, boolean waterE, boolean mS, boolean mobS, boolean cE, String wN){
		fireEnabled[index] = fE;
		lavaEnabled[index] = lavaE;
		waterEnabled[index] = waterE;
		TNTEnabled[index] = tE;
		lightningEnabled[index] = lE;
		monstersSpawn[index] = mS;
		mobsSpawn[index] = mobS;
		creepersExplode[index] = cE;
		worldName[index] = wN;
		size++;
	}

}
