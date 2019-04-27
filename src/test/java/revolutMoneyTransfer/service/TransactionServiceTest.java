package revolutMoneyTransfer.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;


import java.util.*;
import revolutMoneyTransfer.dao.TransactionDao;
import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.Account;
import revolutMoneyTransfer.model.MoneyTransaction;
import revolutMoneyTransfer.model.TransactionStatus;

public class TransactionServiceTest {
	TransactionDao transactionDao = mock(TransactionDao.class);
	TransactionService transactionService = new TransactionService(transactionDao);

	@Test
	public void getAllTransactions()
	{
		
		MoneyTransaction moneyTransaction1 = new MoneyTransaction(3L,1L, 2L, 40.0,"USD", null, null, TransactionStatus.SUCCESS, null) ;
		MoneyTransaction moneyTransaction2 = new MoneyTransaction(2L,1L, 2L, 30.0,"USD", null, null, TransactionStatus.SUCCESS, null) ;
		MoneyTransaction moneyTransaction3 = new MoneyTransaction(1L,1L, 2L, 70.0,"USD", null, null, TransactionStatus.SUCCESS, null) ;
		List<MoneyTransaction> listOfTransactions= new ArrayList<MoneyTransaction>();
		listOfTransactions.add(moneyTransaction1);
		listOfTransactions.add(moneyTransaction2);
		listOfTransactions.add(moneyTransaction3);
		when(transactionDao.getAllTransaction()).thenReturn(listOfTransactions);
		List<MoneyTransaction> list = transactionService.getAllTransactions();
		assertNotNull(list);
		assertArrayEquals(listOfTransactions.toArray(), list.toArray());

	}
	@Test
	public void getTransactionById() throws CustomApplicationException
	{
		

		MoneyTransaction moneyTransaction = new MoneyTransaction(2L,1L, 2L, 30.0,"USD", null, null, TransactionStatus.SUCCESS, null) ;

		when(transactionDao.getTransactionById(2L)).thenReturn(moneyTransaction);
		MoneyTransaction transaction = transactionService.getTransactionById(2L);
		assertNotNull(transaction);
		assertEquals(moneyTransaction,transaction);
	}
	@Test(expected=CustomApplicationException.class)
	public void getTransactionByIdDoesnotExist() throws CustomApplicationException
	{
		
		when(transactionDao.getTransactionById(1L)).thenReturn(null);
		
		MoneyTransaction transaction1 = transactionService.getTransactionById(1L);
		
	
	assertNull(transaction1);
	}
	@Test
	public void getAllTransactionIdsByStatus()
	{
		


		MoneyTransaction moneyTransaction1 = new MoneyTransaction(3L,1L, 2L, 40.0,"USD", null, null, TransactionStatus.SUCCESS, null) ;
		MoneyTransaction moneyTransaction2 = new MoneyTransaction(2L,1L, 2L, 30.0,"USD", null, null, TransactionStatus.SUCCESS, null) ;
		MoneyTransaction moneyTransaction3 = new MoneyTransaction(1L,1L, 2L, 70.0,"USD", null, null, TransactionStatus.SUCCESS, null) ;
		List<Long> listOfTransactions= new ArrayList<Long>();
		listOfTransactions.add(moneyTransaction1.getId());
		listOfTransactions.add(moneyTransaction2.getId());
		listOfTransactions.add(moneyTransaction3.getId());

		when(transactionDao.getAllTransactionIdsByStatus(TransactionStatus.SUCCESS)).thenReturn(listOfTransactions);
		List<Long> transactionList = transactionService.getAllTransactionIdsByStatus(TransactionStatus.SUCCESS);
		assertNotNull(transactionList);
		assertArrayEquals(listOfTransactions.toArray(),transactionList.toArray());
	}

	@Test
	public void createTransaction() throws CustomApplicationException
	{
		Account bankAccount = new Account(1L,"Avik","kesari",3000.0,0.0,"USD");
		
		//AccountService accountService = new AccountService();
		//accountService.createBankAccount(bankAccount);
		MoneyTransaction moneyTransaction = new MoneyTransaction(2L,1L, 2L, 30.0,"USD", null, null,null, null) ;
		MoneyTransaction updatedMoneyTransaction = new MoneyTransaction(2L,1L, 2L, 30.0,"USD", null, null,TransactionStatus.PROCESSING, null) ;
		transactionService.createTransaction(moneyTransaction);
		bankAccount.setBlockedAmount(moneyTransaction.getAmountToBeTransferred());
		when(transactionDao.createTransaction(moneyTransaction)).thenReturn(updatedMoneyTransaction);
		MoneyTransaction transaction = transactionService.createTransaction(moneyTransaction);
		assertEquals(updatedMoneyTransaction,transaction);
		
		
	}
	
	@Test(expected=CustomApplicationException.class) 
	public void createTransactionFromAccountIdNull() throws CustomApplicationException
	{
	
		MoneyTransaction moneyTransaction = new MoneyTransaction(2L,null, 2L, 30.0,"USD", null, null,null, null) ;
		transactionService.createTransaction(moneyTransaction);
		
		
	}
	@Test(expected=CustomApplicationException.class)
	public void createTransactionToAccountIdNull() throws CustomApplicationException
	{
	
		MoneyTransaction moneyTransaction = new MoneyTransaction(2L,1L, null, 30.0,"USD", null, null,null, null) ;
		transactionService.createTransaction(moneyTransaction);
		
		
	}
	@Test(expected=CustomApplicationException.class)
	public void createTransactionBoundaryCaseForAmountTransferred() throws CustomApplicationException
	{
	
		MoneyTransaction moneyTransaction = new MoneyTransaction(2L,1L, null,0.0,"USD", null, null,null, null) ;
		transactionService.createTransaction(moneyTransaction);
		
		
	}
	@Test(expected=CustomApplicationException.class)
	public void createTransactionNegativeAmountTransferred() throws CustomApplicationException
	{
	
		MoneyTransaction moneyTransaction = new MoneyTransaction(2L,1L, null,-30.0,"USD", null, null,null, null) ;
		transactionService.createTransaction(moneyTransaction);
		
		
	}
	
	@Test
	public void executeTransaction() throws CustomApplicationException
	{
		MoneyTransaction moneyTransaction1 = new MoneyTransaction(2L,1L, 2L, 30.0,"USD", null, null,TransactionStatus.PROCESSING, null) ;
		MoneyTransaction moneyTransaction2 = new MoneyTransaction(2L,1L, 2L, 30.0,"USD", null, null,TransactionStatus.PROCESSING, null) ;
		List<MoneyTransaction> list = new ArrayList<MoneyTransaction>();
		list.add(moneyTransaction1);
		list.add(moneyTransaction2);
		transactionService.createTransaction(moneyTransaction1);
		transactionService.createTransaction(moneyTransaction2);
		transactionService.executeTransactions();
		List<Long> listOfIds =Arrays.asList(new Long[]{1L,2l});
		when(transactionDao.getAllTransactionIdsByStatus(TransactionStatus.SUCCESS)).thenReturn(listOfIds);
		List<Long> result = transactionService.getAllTransactionIdsByStatus(TransactionStatus.SUCCESS);
	
	assertArrayEquals(listOfIds.toArray(), result.toArray());
	}

	

	
	
}
