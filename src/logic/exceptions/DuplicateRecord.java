package logic.exceptions;

public class DuplicateRecord extends Exception{

	private static final long serialVersionUID = 1L;

	public DuplicateRecord (String message){
		super("An error has occured :" + message);
	}
	
	
}
