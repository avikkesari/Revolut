package revolutMoneyTransfer.service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import revolutMoneyTransfer.dao.TransactionDao;
import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.MoneyTransaction;
import revolutMoneyTransfer.model.TransactionStatus;

public class TransactionService {

	TransactionDao dao = new TransactionDao(); 

	



	private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
	public TransactionService() {		
		
		executorService.scheduleAtFixedRate(() -> {
			try {
				executeTransactions();
			} catch (CustomApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		},0, 5, TimeUnit.SECONDS);

	}

	public TransactionService(TransactionDao transactionDao) {
		this.dao=transactionDao;
	}





	public void executeTransactions() throws CustomApplicationException {

		System.out.println("Checking and completeing transactions");

		List<Long> transactionIds = getAllTransactionIdsByStatus(TransactionStatus.PROCESSING);

		System.out.println("Transaction ID's"+transactionIds);
		for (Long transactionId : transactionIds) {
			try {

				dao.executeTransaction(transactionId);
			} catch (CustomApplicationException e) {
				throw new CustomApplicationException("Could not execute transaction with id %d" +  transactionId + "because of -> " +  e);
				
			}
		}

	}




	public List<Long> getAllTransactionIdsByStatus(revolutMoneyTransfer.model.TransactionStatus transactionStatus) {
		return dao.getAllTransactionIdsByStatus(transactionStatus);
	}

	public List<MoneyTransaction> getAllTransactions() {

		List<MoneyTransaction> transactions = dao.getAllTransaction();
		return transactions;
	}

	public MoneyTransaction getTransactionById(Long id)  throws CustomApplicationException{

		MoneyTransaction transaction = dao.getTransactionById(id);
		if(transaction==null)
		{
			throw new CustomApplicationException("no Transaction exist with this Id");
		}
		return transaction; 
	}





	public MoneyTransaction createTransaction(MoneyTransaction transaction) throws CustomApplicationException {
		if (transaction.getFromBankAccountId() == null || transaction.getToBankAccountId() == null) {
			throw new CustomApplicationException("The transaction has not provided from Bank Account or to Bank Account values");
		}
		if (transaction.getFromBankAccountId().equals(transaction.getToBankAccountId())) {
			throw new CustomApplicationException("The sender and recipient should not be same");
		}
		if (transaction.getAmountToBeTransferred() <= 0) {
			throw new CustomApplicationException("The amount should be more than 0");
		}


		System.out.println("creating transactions");
		
		return dao.createTransaction(transaction);
	}
}
