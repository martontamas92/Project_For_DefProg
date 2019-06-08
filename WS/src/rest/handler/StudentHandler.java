package rest.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import business_model.Message;
import business_model.Name;
import business_model.Neptun_Code;
import business_model.Password;
import business_model.UserName;
import model.Auth;
import model.Student;
import entity.controller.StudentAuthController;
import entity.controller.StudentController;
import entity.controller.SubjectController;

@Path("/student")
public class StudentHandler {

	private StudentController studentRepository = new StudentController();
	private StudentAuthController authRepository = new StudentAuthController();
	private SubjectController subjectRepository = new SubjectController();

	@POST
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registrate")
	public Response addStudent(@FormParam("uname") String uname, @FormParam("passwd") String passwd,
			@FormParam("data") String data) {

		try {
//			System.out.println(uname);
//			System.out.println(passwd);
//			System.out.println(data);
			ObjectMapper mapper = new ObjectMapper();
			Student st = mapper.readValue(data, Student.class);
//			JSONObject json = new JSONObject(data);
//
//			String firstName = json.getJSONObject("Name").getString("firstName");
//			String lastName = json.getJSONObject("Name").getString("lastName");
//			String neptun = json.getJSONObject("Neptun").getString("neptun");
//			String passwd = json.getJSONObject("Auth").getString("passwd");
//			String uname = json.getJSONObject("Auth").getString("uname");
//
//			Name name = Name.NameBuilder(firstName, lastName);
//			Neptun_Code nept = Neptun_Code.buildNeptun_Code(neptun);
			UserName userName = UserName.userNameBuilder(uname);
			Password password = Password.passwordBuilder(passwd);

			ArrayList<Student> students = studentRepository.allStudent();
//			Student st = Student.studentBuilder(name, nept);

			if (students.contains(st)) {
				return Response.status(403).type(MediaType.APPLICATION_JSON)
						.entity(new Message("A tanuló már regisztrált").toString()).build();
			}
			if (authRepository.userNameExists(uname)) {
				return Response.status(403).type(MediaType.APPLICATION_JSON)
						.entity(new Message("Ez a felhasználó név már létezik").toString()).build();
			}
			int a = studentRepository.addStudent(st);
			Auth auth = new Auth(a, userName, password);
			if (!authRepository.add(auth)) {
				return Response.status(500).entity("A regisztráció nem sikerült").build();
			}
			return Response.status(200).entity(new Message("A Regisztráció siekres volt")).build();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return Response.status(500).entity(new Message(e.getMessage()).toString()).build();
		}

	}

	/*
	 * @POST
	 *
	 * @Consumes(MediaType.APPLICATION_JSON)
	 *
	 * @Produces(MediaType.APPLICATION_JSON)
	 *
	 * @Path("/attend") //@JWTTokenNeeded //goes to student public Response
	 * attendLecture(String data) { try { ObjectMapper mapper = new ObjectMapper();
	 * Map<String, Integer> map1 = mapper.readValue(data,
	 * TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class,
	 * Integer.class)); Integer st_id = map1.get("st_id"); Map<String, Integer> map2
	 * = mapper.readValue(data,
	 * TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class,
	 * Integer.class)); Integer sj_id = map2.get("sj_id"); int a =
	 * subjectRepository.attendLecture(st_id, sj_id);
	 *
	 * return Response.status(200).entity(new
	 * Message("A jelentkezés sikerült").toString()).build(); } catch (Exception e)
	 * { System.out.println(e.getMessage()); return Response.status(500).entity(new
	 * Message("A jelentkezés nem sikerült " + e.getMessage()).toString()) .build();
	 * }
	 *
	 * }
	 *
	 * @GET
	 *
	 * @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XHTML_XML })
	 *
	 * @Path("/student-subjectList") //@JWTTokenNeeded //goes to student public
	 * Response getStudentSubject(@QueryParam("id") Integer id) { try { return
	 * Response.status(200).entity(subjectRepository.allSubjectByStId(id)).build();
	 * } catch (Exception e) { e.printStackTrace(); return
	 * Response.status(500).entity(new Message(e.getMessage().toString())).build();
	 *
	 * } }
	 *
	 * @GET
	 *
	 * @Produces(MediaType.APPLICATION_JSON)
	 *
	 * @Path("/student-attendedClasses") //@JWTTokenNeeded //goes to student public
	 * Response getAttendedSubjects(@QueryParam("id") Integer id) { try { return
	 * Response.status(200).entity(subjectRepository.allLectures(id)).build(); //
	 * query in progress } catch (Exception e) { e.getMessage();
	 * e.printStackTrace(); return Response.status(500).entity(new
	 * Message(e.getMessage().toString())).build(); } }
	 *
	 * @POST
	 *
	 * @Consumes(MediaType.APPLICATION_JSON)
	 *
	 * @Produces(MediaType.APPLICATION_JSON)
	 *
	 * @Path("/presence-list") //@JWTTokenNeeded //goes to student public Response
	 * attendOnClass(@QueryParam("le_id") Integer le_id, @QueryParam("st_id")
	 * Integer st_id) { try { /* ObjectMapper mapper = new ObjectMapper();
	 * Map<String, Integer> map1 = mapper.readValue(data,
	 * TypeFactory.defaultInstance() .constructMapType(HashMap.class, String.class,
	 * Integer.class)); Integer st_id = map1.get("st_id"); Map<String, Integer> map2
	 * = mapper.readValue(data, TypeFactory.defaultInstance()
	 * .constructMapType(HashMap.class, String.class, Integer.class)); Integer le_id
	 * = map2.get("le_id");
	 *
	 *
	 * if(!subjectRepository.canAttend(le_id,st_id)) { return
	 * Response.status(403).entity(new
	 * Message("A jelentkezés nem lehetséges, mert a diák nem jelentkezett a kurzusra"
	 * ).toString()) .build(); } if(subjectRepository.isAttend(le_id, st_id)) {
	 * return Response.status(403).entity(new
	 * Message("A jelentkezés nem lehetséges, mert a diák már jelentkezett").
	 * toString()) .build(); } int a = subjectRepository.attendOnClass(st_id,
	 * le_id); return Response.status(200).entity(new
	 * Message("Jelentkezes sikeres!").toString()).build();
	 *
	 *
	 * } catch (Exception e) { // TODO: handle exception return
	 * Response.status(500).entity(new Message("A jelentkezés nem sikerült " +
	 * e.getMessage()).toString()) .build(); }
	 *
	 * }
	 */
}
