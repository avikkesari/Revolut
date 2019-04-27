package revolutMoneyTransfer.service;

import java.util.List;
import javax.inject.Singleton;
import revolutMoneyTransfer.dao.AccountDao;
import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.Account;

@Singleton
public class AccountService implements AccountServiceInterface{

	AccountDao accountDao = new AccountDao(); 

	
	
	
	public AccountService() {
	}
	
	public AccountService(AccountDao accDao) {
		this.accountDao = accDao;
	}

	
	
	
	public List<Account> getAllBankAccounts() {

		List<Account> list = accountDao.findAll();

		return list;
	}

	public Account getBankAccountById(Long id) throws  CustomApplicationException{


		Account account = accountDao.findById(id);
		if(account==null)
		{
			throw new CustomApplicationException ("account doesnot exist for this id");
		}
		return account;
	}

	public Account updateBankAccount(Long accountId, String name) throws CustomApplicationException {
		if(getBankAccountById(accountId)!=null && getBankAccountById(accountId).getNickName()==name){
			throw new CustomApplicationException("Account cannot be updated with the same");
		}

		return accountDao.update(accountId, name);
	}

	public Account createBankAccount(Account bankAccount) throws CustomApplicationException {		
		System.out.println(bankAccount);
		verify(bankAccount);
		System.out.println("Account Verified");
		Account account = accountDao.save(bankAccount);
		return account;
	}

	public Account removeAccount(Long id) throws CustomApplicationException {
		Account account = accountDao.delete(id);
		return account;
	}




	private void verify(Account bankAccount) throws CustomApplicationException {
		if (bankAccount.getId() == null) {
			throw new CustomApplicationException("ID value is invalid");
		}

		if (bankAccount.getOwnerName() == null || bankAccount.getBalance() == null || bankAccount.getCurrency() == null) {
			throw new CustomApplicationException("Fields could not be NULL");
		}
	}

}
