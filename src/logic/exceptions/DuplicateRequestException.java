package logic.exceptions;

public class DuplicateRequestException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public DuplicateRequestException (String message){
		super(message);
	}
}
