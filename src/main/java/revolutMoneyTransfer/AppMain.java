package revolutMoneyTransfer;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import com.sun.net.httpserver.HttpServer;
import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class AppMain {
	
	public static void main(String[] args) {
		URI baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build();
		
		ResourceConfig config = new ResourceConfig().packages("revolutMoneyTransfer.resource");
		JdkHttpServerFactory.createHttpServer(baseUri, config);
		System.out.println("Application started at http://localhost:8080/");
	}
	
}
