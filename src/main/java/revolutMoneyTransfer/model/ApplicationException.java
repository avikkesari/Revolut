package revolutMoneyTransfer.model;

public class ApplicationException {

	 private String message;
	 private String type;
	public ApplicationException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ApplicationException(String message, String type) {
		super();
		this.message = message;
		this.type = type;
	}



	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	 
	 
	 
}
