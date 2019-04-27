package revolutMoneyTransfer.dao;

import java.util.List;

import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.Account;

public interface AccountDaoInterface {

	public List<Account> findAll();
	public Account findById(Long id);
	public Account update(Long bankAccount, String name) throws CustomApplicationException;
	public Account update(Account account) throws CustomApplicationException;
	public Account save(Account bankAccount) throws CustomApplicationException;
	public Account delete(Long id) throws CustomApplicationException;
	
}
