package revolutMoneyTransfer.db;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
public class HibernateSessionUtil {

	private static SessionFactory sessionFactory;

	static
	{
		try
		{   
			
			
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  
			Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build(); 
			sessionFactory = meta.getSessionFactoryBuilder().build();  
			
//			sessionFactory = new Configuration().configure()
//					.buildSessionFactory();
			
		}
		catch (HibernateException he)
		{
			System.err.println("Error creating Session: " + he);
			he.printStackTrace();
			throw new ExceptionInInitializerError(he);
		}
	}


	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}

}

