package logic.exceptions;

public class EmailSyntaxException extends Exception {
	private static final long serialVersionUID = 1L;

	public EmailSyntaxException (String message){
		super(message);
	}
}
