package couk.Adamki11s.Regios.CustomExceptions;

public class InvalidDataSetException extends Exception {
	
	private static final long serialVersionUID = -439035065502578900L;
	
	String set;
	
	public InvalidDataSetException(String set){
		this.set = set;
	}
	
	public void printException(){
		System.out.println("[Regios][Exception] The following properties in the dataset were null : \n '" + set);
	}

}
