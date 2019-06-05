package rest.handler;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import business_model.Message;
import business_model.QrCode;
import entity.controller.SubjectController;
import model.Lecture;
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
			e.printStackTrace();
			return Response.status(500).entity(new Message("A regisztráció nem sikerült").toString()).build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create-lecture")
	public Response lecture(@Context UriInfo uri, String data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Subject s = mapper.readValue(data, Subject.class);
			Lecture l = new Lecture(LocalDate.now(), s);
			int a = subjectRepository.createLecture(l);
			String url = uri.getBaseUri().toString() + "subject/presence-list?le_id=" + a;
			return Response.status(200).entity("\"" + url + "\"").build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return Response.status(500).entity(new Message("A létrehozás nem sikerült").toString()).build();
		}

	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XHTML_XML })
	@Path("/demonstrator-subjectList") //
	public Response getSubjects(@QueryParam("id") Integer id) {
		try {

			return Response.status(200).entity(subjectRepository.allSubjectByDeId(id)).build();

		} catch (Exception e) {
			return Response.status(500).entity(new Message("A szerver nem elérhetõ")).build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/attend")
	public Response attendLecture(String data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Integer> map1 = mapper.readValue(data,
					TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Integer.class));
			Integer st_id = map1.get("st_id");
			Map<String, Integer> map2 = mapper.readValue(data,
					TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Integer.class));
			Integer sj_id = map2.get("sj_id");
			int a = subjectRepository.attendLecture(st_id, sj_id);

			return Response.status(200).entity(new Message("A jelentkezés sikerült").toString()).build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.status(500).entity(new Message("A jelentkezés nem sikerült " + e.getMessage()).toString())
					.build();
		}

	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XHTML_XML })
	@Path("/student-subjectList")
	public Response getStudentSubject(@QueryParam("id") Integer id) {
		try {
			return Response.status(200).entity(subjectRepository.allSubjectByStId(id)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity(new Message(e.getMessage().toString())).build();

		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/student-attendedClasses")
	public Response getAttendedSubjects(@QueryParam("id") Integer id) {
		try {
			return Response.status(200).entity(subjectRepository.allLectures(id)).build(); // query in progress
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			return Response.status(500).entity(new Message(e.getMessage().toString())).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/presence-list")
	public Response attendOnClass(@QueryParam("le_id") Integer le_id, @QueryParam("st_id") Integer st_id) {
		try {
			/*
			 * ObjectMapper mapper = new ObjectMapper(); Map<String, Integer> map1 =
			 * mapper.readValue(data, TypeFactory.defaultInstance()
			 * .constructMapType(HashMap.class, String.class, Integer.class)); Integer st_id
			 * = map1.get("st_id"); Map<String, Integer> map2 = mapper.readValue(data,
			 * TypeFactory.defaultInstance() .constructMapType(HashMap.class, String.class,
			 * Integer.class)); Integer le_id = map2.get("le_id");
			 */
			int a = subjectRepository.attendOnClass(st_id, le_id);
			return Response.status(200).entity(new Message("Jelentkezes sikeres!").toString()).build();

		} catch (Exception e) {
			// TODO: handle exception
			return Response.status(500).entity(new Message("A jelentkezés nem sikerült " + e.getMessage()).toString())
					.build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/classes")
	public Response pastClasses(@QueryParam("id") Integer id) {
		try {

			return Response.status(200).entity(subjectRepository.pastLectures(id)).build();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return Response.status(500).entity(new Message("A szerver nem elérhetõ").toString()).build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/absence-list")
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
