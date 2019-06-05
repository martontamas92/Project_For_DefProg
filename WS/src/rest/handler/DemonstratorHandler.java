package rest.handler;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import business_model.Message;
import business_model.Name;
import business_model.Password;
import business_model.UserName;
import entity.controller.DemonstratorAuthController;
import entity.controller.DemonstratorController;
import model.Auth;
import model.Demonstrator;

@Path("/demonstrator")
public class DemonstratorHandler {

	private DemonstratorAuthController authRepository = new DemonstratorAuthController();
	private DemonstratorController demRepository = new DemonstratorController();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registrate")
	public Response registrate(String data) {
		try {

			 JSONObject json = new JSONObject(data);
			 String firstName = json.getJSONObject("Name").getString("firstName");
			 String lastName = json.getJSONObject("Name").getString("lastName");

			 String passwd = json.getJSONObject("Auth").getString("passwd");
			 String uname = json.getJSONObject("Auth").getString("uname");

			 Name n = Name.NameBuilder(firstName, lastName);
			 Demonstrator d = new Demonstrator(n);
			 UserName userName = UserName.userNameBuilder(uname);
			 Password password = Password.passwordBuilder(passwd);

			 ArrayList<Demonstrator> ds = demRepository.allDemonstrator();
			 if(ds.contains(d)) {return Response.status(403).entity(new Message("Ez a tanár már regisztrált").toString()).build();}
			 if(authRepository.userNameExists(uname)) {return Response.status(403).entity(new Message("Ezt a felhasználónevet már regisztrálták").toString()).build();}
			 int a = demRepository.addDemonstrator(d);
			 Auth auth = new Auth(a, userName, password);
			 if(!authRepository.add(auth)) {return Response.status(500).entity("Registration failed").build();}
			 //String response = "response:" + d.toString();
			 return Response.status(201).entity(d).build();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.status(500).entity(new Message("A regisztráció nem sikerült").toString()).build();
		}


	}

}
