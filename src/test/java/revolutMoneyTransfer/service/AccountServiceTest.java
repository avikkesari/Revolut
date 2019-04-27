package revolutMoneyTransfer.service;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import revolutMoneyTransfer.dao.AccountDao;
import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.Account;
import revolutMoneyTransfer.service.AccountService;

public class AccountServiceTest {
	
	AccountDao accDao= mock(AccountDao.class);
	AccountService accountService = new AccountService(accDao);

	@Test
	public void getAllBankAccountsTest() 
	{
		Account bankAccount1= new Account(1L,"avik","avi",2000.0,0.0,"USD");
		Account bankAccount2= new Account(1L,"alan","harper",1000.0,0.0,"USD");
		List<Account> result = new ArrayList<Account>();
		result.add(bankAccount1);
		result.add(bankAccount2);
		
		Mockito.when(accDao.findAll()).thenReturn(result);
	
		
				List<Account>allAccounts=accountService.getAllBankAccounts();
		System.out.println(allAccounts);
		
		assertEquals(result, allAccounts);
	}
	
	

		@Test
		public void getBankAccountById() throws CustomApplicationException
		{
			
			Account mockedBankAccount= new Account(1L,"avik","avi",2000.0,0.0,"USD");
			Mockito.when(accDao.findById(1L)).thenReturn(mockedBankAccount);
			Account account=accountService.getBankAccountById(1L);
			assertEquals(mockedBankAccount, account);
		}
		
		@Test(expected=CustomApplicationException.class) //no account exists with this id
		public void getBankAccountByIdWithException() throws CustomApplicationException 
		{
			Account mockedBankAccount= new Account(1L,"avik","avi",2000.0,0.0,"USD");
			accountService.createBankAccount(mockedBankAccount);
			
			accountService.getBankAccountById(5L);
			
		
		}
		
		@Test
		public void createAccountTest() throws CustomApplicationException
		{
			 
				
			Account mockedBankAccount= new Account(1L,"avik","avi",2000.0,0.0,"USD");
			Mockito.when(accDao.save(mockedBankAccount)).thenReturn(mockedBankAccount);
			Account account = accountService.createBankAccount(mockedBankAccount);
			assertEquals(mockedBankAccount, account);
			
		}
	
		@Test(expected=CustomApplicationException.class) //if no account exists with particular Id
		public void updateAccountWithException() throws CustomApplicationException 
		{
			Account mockedBankAccount= new Account(1L,"avik","avi",2000.0,0.0,"USD");
			accountService.createBankAccount(mockedBankAccount);
			
			
			accountService.updateBankAccount(1L,"avi");
			
		
		}
		
		@Test
		public void updateAccountTest() throws CustomApplicationException
		{
			Account mockedBankAccount= new Account(1L,"avik","avi",2000.0,0.0,"USD");
			accountService.createBankAccount(mockedBankAccount);
			Account mockedUpdatedBankAccount= new Account(1L,"avik","avi",2000.0,0.0,"USD");
			Mockito.when(accDao.findById(1l)).thenReturn(mockedBankAccount);
			Mockito.when(accDao.update(1L, "kesari")).thenReturn(mockedUpdatedBankAccount);
			Account account = accountService.updateBankAccount(1L,"kesari");
			assertEquals(mockedUpdatedBankAccount, account);
			
		}

		@Test(expected=CustomApplicationException.class)
		public void deleteAccountTest() throws CustomApplicationException
		{
			Account mockedBankAccount= new Account(1L,"avik","avi",2000.0,0.0,"USD");
			accountService.createBankAccount(mockedBankAccount);
			
			Mockito.when(accDao.delete(1L)).thenReturn(mockedBankAccount);
			Mockito.when(accDao.findById(1l)).thenReturn(null);
			assertEquals(null,accountService.getBankAccountById(1L));
			
		}
		@Test(expected=CustomApplicationException.class) //test if mandatory parameters are  null in request--Bad Request
		public void accountVerifyTest() throws CustomApplicationException
		{
			Account mockedBankAccount= new Account(1L,null,"avi",2000.0,0.0,"USD");
			accountService.createBankAccount(mockedBankAccount);
		}
	

}
