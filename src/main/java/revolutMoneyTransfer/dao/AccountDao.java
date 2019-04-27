package revolutMoneyTransfer.dao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import revolutMoneyTransfer.db.HibernateSessionUtil;
import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.Account;

/**
 * @author Avik Kesari
 *
 */
public class AccountDao implements AccountDaoInterface{

	/**
	 * @return
	 */
	@Override
	public List<Account> findAll() {

		List<Account> accounts=null;

		try(
				Session session = HibernateSessionUtil.getSessionFactory().openSession();

				)
		{
			Transaction tx = session.beginTransaction();
			accounts = session.createQuery("FROM Account").list();
			tx.commit();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return accounts;
	}


	/**
	 * @param id
	 * @return
	 */
	@Override
	public Account findById(Long id) {

		Account account = null;
		try(Session session = HibernateSessionUtil.getSessionFactory().openSession();)
		{
			Transaction tx = session.beginTransaction();
			account = session.get(Account.class, id);
			tx.commit();
		}
		return account;
	}


	/**
	 * @param bankAccount
	 * @throws CustomApplicationException
	 */
	@Override
	public Account update(Long bankAccount, String name) throws CustomApplicationException {


		Account account = findById(bankAccount);
		account.setNickName(name);
		try(Session session = HibernateSessionUtil.getSessionFactory().openSession())
		{
			Transaction tx = session.beginTransaction();
			session.update(account);
			tx.commit();
		}
		catch(Exception e)
		{
			throw new CustomApplicationException(e,"CANNOT UPDATE OBJECT");
		}

		return account;

	}

	@Override
	public Account update(Account account) throws CustomApplicationException {


		findById(account.getId());
		try(Session session = HibernateSessionUtil.getSessionFactory().openSession())
		{
			Transaction tx = session.beginTransaction();
			session.update(account);
			tx.commit();
		}
		catch(Exception e)
		{
			throw new CustomApplicationException(e,"CANNOT UPDATE OBJECT");
		}

		return account;

	}


	/**
	 * @param bankAccount
	 * @return
	 * @throws CustomApplicationException
	 */
	@Override
	public Account save(Account bankAccount) throws CustomApplicationException {


		try(Session session = HibernateSessionUtil.getSessionFactory().openSession()){
			Transaction tx = session.beginTransaction();
			Long id = (Long) session.save(bankAccount);
			tx.commit();
			bankAccount.setId(id);
		}
		catch(Exception e)
		{
			throw new CustomApplicationException(e,"CANNOT SAVE OBJECT");
		}


		return bankAccount;


	}


	/**
	 * @param id

	 * @return
	 * @throws CustomApplicationException 
	 */
	@Override
	public Account delete(Long id) throws CustomApplicationException {

		Account account = null;
		try(Session session = HibernateSessionUtil.getSessionFactory().openSession();)
		{
			Transaction tx = session.beginTransaction();
			account = session.get(Account.class, id);
			session.delete(account);
			tx.commit();
		}
		catch(Exception e)
		{
			throw new CustomApplicationException(e,"CANNOT DELETE OBJECT");
		}
		return account;
	}






}
