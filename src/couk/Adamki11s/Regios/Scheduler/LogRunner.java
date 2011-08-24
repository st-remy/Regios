package couk.Adamki11s.Regios.Scheduler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;

import couk.Adamki11s.Regios.Regions.Region;

public class LogRunner {
	
	public static HashMap<Region, ArrayList<String>> log = new HashMap<Region, ArrayList<String>>();
	
	public static int timer = 0;
	
	public static void pollLogMessages(){
		timer++;
		if(timer >= 600){
			pushLogMessages();
			timer = 0;
		}
	}
	
	private synchronized static void pushLogMessages(){
		for(Entry<Region, ArrayList<String>> entry : log.entrySet()){
			File logFile = entry.getKey().getLogFile();
			ArrayList<String> messages = log.get(entry.getKey());
			for(String msg : messages){
				/*
				 * Append log file here
				 */
			}
		}
	}
	
	public static String getPrefix(Region r) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "[" + sdf.format(cal.getTime()) + "]" + "[" + r.getName() + "]";
     }
	
	public static void addLogMessage(Region r, String message){
		if(log.containsKey(r)){
			ArrayList<String> tempLog = log.get(r);
			tempLog.add(message);
			log.put(r, tempLog);
		} else {
			ArrayList<String> tempLog = new ArrayList<String>();
			tempLog.add(message);
			log.put(r, tempLog);
		}
		
	}

}
