package revolutMoneyTransfer.resource;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import revolutMoneyTransfer.exceptions.CustomApplicationException;
import revolutMoneyTransfer.model.ApplicationException;


@Provider
public class ExcepptionController implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		

        ApplicationException applicationException;
        Response.ResponseBuilder serverError = Response.serverError().type(MediaType.APPLICATION_JSON_TYPE);

        if (exception instanceof WebApplicationException) {
            applicationException = new ApplicationException(exception.getMessage(), "UNEXPECTED EXCEPTION");
            serverError = serverError.status(((WebApplicationException) exception).getResponse().getStatus());
        } else if (exception instanceof CustomApplicationException) {
            applicationException = new ApplicationException(exception.getMessage(), "ACCOUNT MODIFICATION ERROR");
        } else {
        	applicationException = new ApplicationException(exception.getMessage(), "UNEXPECTED EXCEPTION");
        }

      
        return serverError.entity(applicationException).build();
    
		
		
	}

}
