package rest.handler;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import business_model.Message;
import business_model.Name;
import business_model.Password;
import business_model.UserName;
import entity.controller.DemonstratorAuthController;
import entity.controller.DemonstratorController;
import entity.controller.SubjectController;
import model.Auth;
import model.Demonstrator;
import model.Lecture;
import model.Subject;

@Path("/demonstrator")
public class DemonstratorHandler {

	private DemonstratorAuthController authRepository = new DemonstratorAuthController();
	private DemonstratorController demRepository = new DemonstratorController();
	private SubjectController subjectRepository = new SubjectController();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registration")

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
			if (ds.contains(d)) {
				return Response.status(403).entity(new Message("Ez a tanár már regisztrált").toString()).build();
			}
			if (authRepository.userNameExists(uname)) {
				return Response.status(403).entity(new Message("Ezt a felhasználónevet már regisztrálták").toString())
						.build();
			}
			int a = demRepository.addDemonstrator(d);
			Auth auth = new Auth(a, userName, password);
			if (!authRepository.add(auth)) {
				return Response.status(500).entity(new Message("Registration failed")).build();
			}
			// String response = "response:" + d.toString();
			return Response.status(201).entity(new Message("a regisztracio sikeres volt").toString()).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.status(500).entity(new Message("A regisztráció nem sikerült: " + e.getMessage()).toString()).build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/create-subject")
	@JWTTokenNeeded
	// goes to demonstrator
	public Response subjectRegistrate(String data) {
		System.out.println(data);
		try {
			ObjectMapper mapper = new ObjectMapper();
			Subject s;
			s = mapper.readValue(data, Subject.class);
			if (!subjectRepository.subjectExists(s.getSubjectName())) {
				subjectRepository.addSubject(s); // don't call its not finished yet
				return Response.status(201).entity(new Message("A regisztráció sikeres volt!").toString()).build();
			}
			return Response.status(403).entity(new Message("A tantárgyat már regisztrálták").toString()).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(new Message("A regisztráció nem sikerült").toString()).build();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XHTML_XML })
	@Path("/subjectList") //
	@JWTTokenNeeded
	// goes to demonstrator
	public Response getSubjects(@QueryParam("id") Integer id) {
		try {

			return Response.status(200).entity(subjectRepository.allSubjectByDeId(id)).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(new Message("A szerver nem elérhetõ").toString()).build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/classes")
	//@JWTTokenNeeded
	// goes to demonstrator
	public Response pastClasses(@QueryParam("id") Integer id) {
		try {

			return Response.status(200).entity(subjectRepository.pastLectures(id)).build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.status(500).entity(new Message("A szerver nem elérhetõ").toString()).build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create-lecture")
	//@JWTTokenNeeded
	// goes to demonstrator
	public Response lecture(@Context UriInfo uri, String data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Subject s = mapper.readValue(data, Subject.class);
			Lecture l = new Lecture(LocalDate.now(), s);
			int a = subjectRepository.createLecture(l);
			String url = uri.getBaseUri().toString() + "student/presence-list?le_id=" + a;
			return Response.status(200).entity("\"" + url + "\"").build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return Response.status(500).entity(new Message("A létrehozás nem sikerült").toString()).build();
		}

	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/absence-list")
	//@JWTTokenNeeded
	// goes to demonstrator
	public Response absences(@QueryParam("id") Integer id) {
		try {

			return Response.status(200).entity(subjectRepository.absences(id)).build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.status(500).entity(new Message("A szerver nem elérhetõ").toString() + e.getMessage())
					.build();
		}

	}
}
