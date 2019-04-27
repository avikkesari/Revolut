//package revolutMoneyTransfer;
//
//import org.glassfish.jersey.internal.inject.AbstractBinder;
//
//import com.google.inject.AbstractModule;
//import com.google.inject.Guice;
//import com.google.inject.Injector;
//
//import revolutMoneyTransfer.dao.AccountDao;
//import revolutMoneyTransfer.dao.AccountDaoInterface;
//import revolutMoneyTransfer.service.AccountService;
//import revolutMoneyTransfer.service.AccountServiceInterface;
//
//
//public class InjectorUtil {
//
//	 private static Injector injector;
//
//	    static {
//	    	
//	    	
//	    	 
//	        injector = Guice.createInjector(
//	        		new AbstractModule(){
//
//						@Override
//						protected void configure() {
//							
//							bind(AccountServiceInterface.class).to(AccountService.class);
//							bind(AccountDaoInterface.class).to(AccountDao.class);
//							
//						}
//	        			
//	        		}
//	        		);
//	    }
//
//	    public static Injector provide() {
//	        return injector;
//	    }
//	    
//	    public void ApplicationConfig() {
//	        register(new AbstractBinder(){
//	        	protected void configure() {
//				
//				bind(AccountServiceInterface.class).to(AccountService.class);
//				bind(AccountDaoInterface.class).to(AccountDao.class);
//				
//			}});
//	        packages(true, "api");
//	    }
//
//		
//}
