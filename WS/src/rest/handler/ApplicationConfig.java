package rest.handler;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.server.ResourceConfig;



@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {
	public ApplicationConfig() {
	       register( new CORSFilter() );
	   }
}
