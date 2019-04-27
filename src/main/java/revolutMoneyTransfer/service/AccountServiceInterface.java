package revolutMoneyTransfer.service;

import java.util.List;

import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.Account;

public interface AccountServiceInterface {

	public Account getBankAccountById(Long id) throws  CustomApplicationException;
	public Account updateBankAccount(Long accountId, String name) throws CustomApplicationException;
	public Account removeAccount(Long id) throws CustomApplicationException;
	public Account createBankAccount(Account bankAccount) throws CustomApplicationException;
	public List<Account> getAllBankAccounts();
}
