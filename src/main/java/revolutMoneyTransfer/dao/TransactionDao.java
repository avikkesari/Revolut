package revolutMoneyTransfer.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.google.inject.Singleton;

import revolutMoneyTransfer.db.HibernateSessionUtil;
import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.Account;
import revolutMoneyTransfer.model.MoneyTransaction;
import revolutMoneyTransfer.model.TransactionStatus;

@Singleton
public class TransactionDao {



	AccountDao accountDao = new AccountDao();
	HashMap<Long, ReentrantLock> lockMap = new HashMap<Long,ReentrantLock>();

 	public List<MoneyTransaction> getAllTransaction() {
		List<MoneyTransaction> transactions=null;
		try(
				Session session = HibernateSessionUtil.getSessionFactory().openSession();

				)
		{
			Transaction tx = session.beginTransaction();
			transactions = session.createQuery("FROM revolutMoneyTransfer.model.MoneyTransaction").list();
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return transactions;

	}
	
	
	public void addLock(Long account)
	{
		lockMap.putIfAbsent(account, new ReentrantLock());
	}
	
	public void removeLock(Long account)
	{
		lockMap.remove(account);
	}
	

	public MoneyTransaction getTransactionById(Long id) {

		MoneyTransaction transaction = null;
		try(Session session = HibernateSessionUtil.getSessionFactory().openSession();)
		{
			Transaction tx = session.beginTransaction();
			transaction = session.get(MoneyTransaction.class, id);
			tx.commit();
		}
		return transaction;
	}

	public MoneyTransaction createTransaction(MoneyTransaction transaction) throws CustomApplicationException {
		
		addLock(transaction.getFromBankAccountId());
		addLock(transaction.getToBankAccountId());
		lockMap.get(transaction.getFromBankAccountId()).lock();
		lockMap.get(transaction.getToBankAccountId()).lock();
		long fromAccountId = transaction.getFromBankAccountId();
		Account fromAccount = accountDao.findById(fromAccountId);

		System.out.println("from Account : " + fromAccount);
		if((fromAccount.getBalance() - fromAccount.getBlockedAmount() - transaction.getAmountToBeTransferred()) < 0)
		{
			System.out.println("throwing exception");
			throw new CustomApplicationException("INSUFFICIENT BALANCE IN ACCOUNT");
		}
		
		
		fromAccount.setBlockedAmount(fromAccount.getBlockedAmount() + transaction.getAmountToBeTransferred());

		transaction.setStatus(TransactionStatus.PROCESSING);
		Transaction tx=null;
		try(Session session = HibernateSessionUtil.getSessionFactory().openSession()){
			System.out.println("Beginning transaction");
			tx = session.beginTransaction();
			System.out.println(transaction);
			Long id =  (Long) session.save(transaction);
			System.out.println("transaction saved with id : " + id);


			session.update(fromAccount);
			System.out.println("account updated");


			tx.commit();
			transaction.setId(id);
			lockMap.get(transaction.getFromBankAccountId()).unlock();
			lockMap.get(transaction.getToBankAccountId()).unlock();
			System.out.println("ended transaction");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			if(tx!=null) tx.rollback();
			throw new CustomApplicationException(e,"CANNOT SAVE OBJECT");
		}


		System.out.println("Returning transaction");

		return transaction;
	}

	public void executeTransaction(Long transactionId) throws CustomApplicationException {
		
		
		MoneyTransaction transaction = getTransactionById(transactionId);
		if(transaction.getStatus()!=TransactionStatus.PROCESSING)
			throw new CustomApplicationException("Invalid transaction for execution");
		Account fromAccount = accountDao.findById(transaction.getFromBankAccountId());
		Account toAccount   = accountDao.findById(transaction.getToBankAccountId());
		double newBlockedAmount = fromAccount.getBlockedAmount()-transaction.getAmountToBeTransferred();
		double newBalance = fromAccount.getBalance()-transaction.getAmountToBeTransferred();
		fromAccount.setBlockedAmount(newBlockedAmount);
		fromAccount.setBalance(newBalance);
		toAccount.setBalance(toAccount.getBalance()+transaction.getAmountToBeTransferred());			
		transaction.setStatus(TransactionStatus.SUCCESS);
		transaction.setUpdateDate(new Date());
		transaction.setMessage("Transaction Successful");
		Transaction tx=null;
		try(Session session = HibernateSessionUtil.getSessionFactory().openSession()){
			tx = session.beginTransaction();
			session.update(fromAccount);
			session.update(toAccount);

			System.out.println(transaction);
			session.update(transaction);
			tx.commit();
		}
		catch(Exception e)
		{
			if(tx!=null)tx.rollback();
			throw new CustomApplicationException(e,"CANNOT FINISH TRANSACTION");
		}

	}

	public List<Long> getAllTransactionIdsByStatus(TransactionStatus transactionStatus)  {

		List<Long> transactions = null;

		try(Session session = HibernateSessionUtil.getSessionFactory().openSession()){
			Transaction tx = session.beginTransaction();
			int id = transactionStatus.getId();
			System.out.println("going to run query");
			Query<Long> query = session.createQuery("Select id from revolutMoneyTransfer.model.MoneyTransaction where status =:status");
			transactions = query.setParameter("status",transactionStatus).list();
			System.out.println("query successful "+ query.getFirstResult());
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


		return transactions;
	}

}
