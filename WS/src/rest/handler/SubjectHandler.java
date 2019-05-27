package rest.handler;


import java.time.LocalDate;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import business_model.Name;

import javax.ws.rs.core.MediaType;


import entity.controller.SubjectController;

import model.Demonstrator;
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
			return Response.status(201).entity(e.getMessage()).build();
		}


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

	@POST

	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/create-lecture")
	public Response lecture(String data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
//			Demonstrator d;


			//Date date ;
			Subject s = mapper.readValue(data, Subject.class);
//			String subjectName = mapper.readValue(data, String.class);
//			Subject s = new Subject(subjectName, d);
			//LocalDate date;
			//date = mapper.readValue(data, Date.class);
			Lecture l = new Lecture(LocalDate.now(), s);
			int a = subjectRepository.createLecture(l);
			String url = "";

			// have to insert record to database;
			return Response.status(200).entity("Sikeres letrehozas!" + a).build();
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/demonstrator-subjectList")
	public ArrayList<String> getSubjects(String data){
		try {
			ObjectMapper mapper = new ObjectMapper();
			 Map<String, Integer> map =
				        mapper.readValue(data, TypeFactory.defaultInstance()
				                         .constructMapType(HashMap.class, String.class, Integer.class));
			Integer id = map.get("id");
			return subjectRepository.allSubjectByDeId(id);

		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return null;
	}



	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/attend")
	public Response attendLecture(String data) {


		return Response.status(200).entity("").build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/student-subjectList")
	public Response getStudentSubject(@FormParam("id") Integer id){
		return Response.status(200).entity(subjectRepository.allSubjectByStId(id)).build();
	}
}
