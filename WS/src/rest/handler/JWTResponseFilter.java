package rest.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

public class JWTResponseFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		 System.out.println("response filter invoked...");
	        if (requestContext.getProperty("auth-failed") != null) {
	            Boolean failed = (Boolean) requestContext.getProperty("auth-failed");
	            if (failed) {
	                System.out.println("JWT auth failed. No need to return JWT token");
	                return;
	            }
	        }
	        List<Object> jwt = new ArrayList<Object>();
	        jwt.add(requestContext.getHeaderString("Authorization").split(" ")[1]);
	        responseContext.getHeaders().put("jwt", jwt);
	        System.out.println("Added JWT to response header 'jwt'");
	    }

	}


