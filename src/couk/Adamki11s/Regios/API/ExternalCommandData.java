package couk.Adamki11s.Regios.API;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.plugin.Plugin;

public class ExternalCommandData {
	
	public int argsLength;
	
	public String[] args;
	
	public Class<?> invokeClass;
	
	public Method invokeMethod;
	
	public Plugin p;
	
	public ExternalCommandData(Plugin p, int argsLength, String[] args, Class<?> invokeClass, String invokeMethodName){
		
		boolean methodFound = false;
		
		Method invoke = null;
		
		for(Method invokee : invokeClass.getDeclaredMethods()){
			if(invokee.getName().equals(invokeMethodName)){
				methodFound = true;
				invoke = invokee;
				break;
			}
		}
		
		if(!methodFound){
			System.out.println("[Regios][API] " + p.getDescription().getName() + " : Method (" + invokeMethodName + ") was not found in Class : (" + invokeClass.getName() + ")!");
			return;
		}
		
		if(!invoke.isAccessible()){
			System.out.println("[Regios][API] " + p.getDescription().getName() + " : Method (" + invokeMethodName + ") is unnaccessible in Class : (" + invokeClass.getName() + ")!");
			return;
		}
		
		this.argsLength = argsLength;
		this.args = args;
		this.invokeClass = invokeClass;
		this.p = p;
		this.invokeMethod = invoke;
	}
	
	public int getArgsLength(){
		return this.argsLength;
	}
	
	public String[] getArgs(){
		return this.args;
	}
	
	public Class<?> getClassObject(){
		return this.invokeClass;
	}
	
	public Method getMethod(){
		return this.invokeMethod;
	}
	
	public Plugin getPlugin(){
		return this.p;
	}
	
	public void forceInvoke(){
		try {
			this.invokeMethod.invoke(invokeClass, (Object[])args);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
