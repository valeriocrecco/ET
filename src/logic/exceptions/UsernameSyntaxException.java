package logic.exceptions;

public class UsernameSyntaxException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UsernameSyntaxException (String message){
		super(message);
	}
}
