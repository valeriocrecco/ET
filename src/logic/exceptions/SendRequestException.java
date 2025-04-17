package logic.exceptions;

public class SendRequestException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public SendRequestException (String message){
		super(message);
	}
}
