package revolutMoneyTransfer.resource;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import revolutMoneyTransfer.AppConstants;
import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.Account;
import revolutMoneyTransfer.model.dto.AccountOwnerName;
import revolutMoneyTransfer.service.AccountService;


@Path(AppConstants.ACCOUNTS_URL)
@Produces(MediaType.APPLICATION_JSON)
public class AccountsController {


	AccountService accountService = new AccountService(); 
	
	
	
	@GET
	public Response getAllBankAccounts() {

		List<Account> bankAccounts;
		bankAccounts = accountService.getAllBankAccounts();
		if (bankAccounts == null) {
			Response.noContent().build();
		}

		return Response.ok(bankAccounts).build();


	}
	
	
	
	
	
	@GET
    @Path("/{id: [0-9]+}")
    public Response getBankAccountById(@PathParam("id") Long id) throws CustomApplicationException {
        Account bankAccount;


        bankAccount = accountService.getBankAccountById(id);

        if (bankAccount == null) {
            throw new WebApplicationException("The bank account is not exists", Response.Status.NOT_FOUND);
        }

        return Response.ok(bankAccount).build();
    } 
	
	
	
	
	@PUT
	@Path("{id: [0-9]+}")
    public Response updateBankAccount(@PathParam("id") Long accountId,AccountOwnerName ownerName) throws CustomApplicationException {
        Account account = accountService.updateBankAccount(accountId,ownerName.getNickName());

        return Response.ok(account).build();
    }

    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createBankAccount(Account bankAccount) throws CustomApplicationException {
        Account createdBankAccount;

        System.out.println("Check");
        createdBankAccount = accountService.createBankAccount(bankAccount);

        return Response.ok(createdBankAccount).build();
    }
	
    
    
	
	
    @DELETE
    @Path("{id: [0-9]+}")
    public Response deleteBankAccount(@PathParam("id") String accountId) throws NumberFormatException, CustomApplicationException  {
    	Account bankAccount = accountService.removeAccount(Long.valueOf(accountId));

        if (bankAccount == null) {
            throw new WebApplicationException("The bank account is not exists", Response.Status.NOT_FOUND);
        }

        return Response.noContent().build();
    }
    
    
	

}
