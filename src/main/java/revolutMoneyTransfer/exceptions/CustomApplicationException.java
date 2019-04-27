package revolutMoneyTransfer.exceptions;

public class CustomApplicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomApplicationException(Throwable e, String message) {
		super(message,e);
	}
	
	
	public CustomApplicationException(String message) {
		super(message);
	}
	
	

}
