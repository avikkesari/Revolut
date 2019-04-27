package revolutMoneyTransfer.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;

import revolutMoneyTransfer.db.HibernateSessionUtil;
import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.Account;
import revolutMoneyTransfer.model.MoneyTransaction;

import revolutMoneyTransfer.service.AccountService;
import revolutMoneyTransfer.service.TransactionService;

public class TransactionDaoTest {

	TransactionService transactionService = new TransactionService();
	HibernateSessionUtil hibernateUtil = mock(HibernateSessionUtil.class);
	Session session = mock(Session.class);
	SessionFactory sessionFactory = mock(SessionFactory.class);
	AccountDao accountDao = new AccountDao();
	TransactionDao transactionDao = new TransactionDao();

	@Test
	public void executeTransaction() throws CustomApplicationException {
		Account bankAccount1 = new Account(1L, "Avik", "kesari", 3000.0, 0.0, "USD");
		Account bankAccount2 = new Account(2L, "Alan", "Harper", 1000.0, 0.0, "USD");
		AccountService accountService = new AccountService();
		accountService.createBankAccount(bankAccount1);
		accountService.createBankAccount(bankAccount2);
		MoneyTransaction moneyTransaction = new MoneyTransaction(1L, 1L, 2L, 1000.0, "USD", null, null, null, null);
		Double blockedAmountInTransaction = moneyTransaction.getAmountToBeTransferred();
		transactionDao.createTransaction(moneyTransaction);
		Long fromAccountId = moneyTransaction.getFromBankAccountId();
		Long toAccountId = moneyTransaction.getToBankAccountId();
		accountService.getBankAccountById(fromAccountId).setBlockedAmount(blockedAmountInTransaction);
		assertEquals(blockedAmountInTransaction, (Double) 1000.0);

		Session session = hibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Account fromAccount = accountDao.findById(fromAccountId);
		Account toAccount = accountDao.findById(moneyTransaction.getToBankAccountId());
		Double newBlockedAmount = fromAccount.getBlockedAmount() - moneyTransaction.getAmountToBeTransferred();
		Double newBalance = fromAccount.getBalance() - moneyTransaction.getAmountToBeTransferred();
		fromAccount.setBlockedAmount(newBlockedAmount);
		fromAccount.setBalance(newBalance);
		toAccount.setBalance(toAccount.getBalance() + moneyTransaction.getAmountToBeTransferred());
		session.update(accountService.getBankAccountById(fromAccountId));
		session.update(accountService.getBankAccountById(toAccountId));
		tx.commit();

		assertEquals((Double) 2000.0, newBalance);

	}

}
