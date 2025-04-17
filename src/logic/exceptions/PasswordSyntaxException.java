package logic.exceptions;

public class PasswordSyntaxException extends Exception {

	private static final long serialVersionUID = 1L;

	public PasswordSyntaxException (String message){
		super(message);
	}
	
}
