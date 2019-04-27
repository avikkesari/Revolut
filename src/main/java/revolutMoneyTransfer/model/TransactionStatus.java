package revolutMoneyTransfer.model;


public enum TransactionStatus {
	PROCESSING(1), FAILED(2), SUCCESS(3);
	
	
	 private int id;

	    TransactionStatus(int id) {
	        this.id = id;
	    }
	    
	    public static TransactionStatus valueOf(int id) {
	        for(TransactionStatus e : values()) {
	            if(e.id == id) return e;
	        }

	        return null;
	    }

	    public int getId() {
	        return id;
	    }
}
