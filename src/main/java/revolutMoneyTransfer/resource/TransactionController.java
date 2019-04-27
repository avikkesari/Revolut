package revolutMoneyTransfer.resource;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import revolutMoneyTransfer.AppConstants;
import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.MoneyTransaction;
import revolutMoneyTransfer.service.TransactionService;


@Path(AppConstants.TRANSACTION_URL)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionController {


	TransactionService transactionService = new TransactionService();



	@GET
	public Response getAllTransactions() {


		List<MoneyTransaction> transactionList = transactionService.getAllTransactions();
		return Response.ok().entity(transactionList).build();
	}


	@GET
	@Path("/{id: [0-9]+}")
	public Response getTransactionById(@PathParam("id") Long id) throws CustomApplicationException {
		return Response.ok().entity(transactionService.getTransactionById(id)).build();
	}


	@POST
	public Response createTransaction(MoneyTransaction transaction) throws CustomApplicationException  {
		transaction.setCreationDate(new Date());
		transaction.setMessage("Transaction has been initiated");
		transaction = transactionService.createTransaction(transaction);

		return Response.ok().entity(transaction).build();
	}

}
