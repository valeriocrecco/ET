package logic.exceptions;

public class EmailException extends Exception{
	private static final long serialVersionUID = 1L;

	public EmailException (String message){
		super(message);
	}
}
