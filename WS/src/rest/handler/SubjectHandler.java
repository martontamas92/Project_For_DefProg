package rest.handler;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.hibernate.validator.internal.util.privilegedactions.GetDeclaredConstructor;

import com.fasterxml.jackson.databind.ObjectMapper;

import business_model.Name;

import javax.ws.rs.core.MediaType;


import entity.controller.SubjectController;
import model.Demonstrator;
import model.Subject;

@Path("/subject")
public class SubjectHandler {
	private SubjectController subjectRepository = new SubjectController();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/registrate")

	public Response registrate(String data) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			Subject s;
			s = mapper.readValue(data, Subject.class);



			subjectRepository.addSubject(s); // don't call its not finished yet

			return Response.status(201).entity(s.toString()).build();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(201).entity(e.getMessage()).build();
		}
		//Subject s = new Subject(subjectName, demonstrator);

	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
	public Subject test() {
		Name n = Name.NameBuilder("tanito", "benedek", "ignac");
		Demonstrator d = new Demonstrator(n);
		Subject s = new Subject("proba", d);
		return s;
	}
}
