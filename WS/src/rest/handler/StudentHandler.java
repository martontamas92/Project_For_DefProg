package rest.handler;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

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
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registrate")
	public Response proba(String data) {

		//studentRepository.testConnection();


		try {
			System.out.println(data);
			JSONObject json = new JSONObject(data);
			String firstName = json.getJSONObject("Name").getString("firstName");
			String middleName = json.getJSONObject("Name").getString("middleName");
			String lastName = json.getJSONObject("Name").getString("lastName");
			String neptun = json.getJSONObject("Neptun").getString("neptun");
			String passwd = json.getJSONObject("Auth").getString("passwd");
			String uname = json.getJSONObject("Auth").getString("uname");

			/*
			System.out.println("fn: " + firstName);
			System.out.println("mn: " + middleName);
			System.out.println("ln: " + lastName);
			System.out.println("nept: " + neptun);
			*/
			Name name = Name.NameBuilder(firstName, middleName, lastName);
			Neptun_Code nept = Neptun_Code.buildNeptun_Code(neptun);
			UserName userName = UserName.userNameBuilder(uname);
			Password password = Password.passwordBuilder(passwd);

			ArrayList<Student> students = studentRepository.allStudent();
			Student st = Student.studentBuilder(name, nept);
			String student_err = "error: student already exists";
			String username_err = "error: username already exist";
			//System.out.println(students.contains(st));
			if(students.contains(st)) {return Response.status(403).type(MediaType.APPLICATION_JSON).entity(student_err).build();}
			if(authRepository.userNameExists(uname)) {return Response.status(403).type(MediaType.APPLICATION_JSON).entity(username_err).build();}
			int a = studentRepository.addStudent(st);
			Auth auth = new Auth(a, userName, password);
			if(!authRepository.add(auth)) {return Response.status(500).entity("Registration failed").build();}
			return Response.status(200).entity("Succes").build();



		}catch (Exception e) {
			System.out.println(e.getMessage());
			return Response.status(500).entity(e.getMessage()).build();
		}

	/*	Student st = Student.studentBuilder(name, nept);
	//	System.out.println(nept.toString());
	//	System.out.println(name.toString());
		return Response.status(200).entity(st.toString()).build();
	 */
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test")
	public Student getStudentRecord(){
        String fn = "veztek";
        String mn = "kozep";
        String ln = "uto";
        String nept = "ITXDJ8";

        Name n = Name.NameBuilder(fn, mn, ln);
        Neptun_Code neptun = Neptun_Code.buildNeptun_Code(nept);

        Student st = Student.studentBuilder(n, neptun);
        return st;

    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/list")
	public ArrayList<Student> getAllStudents() {
		return studentRepository.allStudent();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/take-class")
	public Response takeClass(String data) {
		try {
			SubjectController sc = new SubjectController();
			ObjectMapper mapper = new ObjectMapper();
			 Map<String, Integer> map =
				        mapper.readValue(data, TypeFactory.defaultInstance()
				                         .constructMapType(HashMap.class, String.class, Integer.class));
			 Map<String, String> map2 =
				        mapper.readValue(data, TypeFactory.defaultInstance()
				                         .constructMapType(HashMap.class, String.class, String.class));
			Integer stid = map.get("st_id");
			Integer sjid = sc.subjectIdByName(map2.get("sj_name"));


			//add taken class to database
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return null; // will dissapear
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/classes-taken")
	public Response possibleClasses(String data) {

		try {
			ArrayList<Map<String, String>> subjects = new ArrayList<>();
			ObjectMapper mapper = new ObjectMapper();
			 Map<String, Integer> map =
				        mapper.readValue(data, TypeFactory.defaultInstance()
				                         .constructMapType(HashMap.class, String.class, Integer.class));
			Integer stid = map.get("st_id");
			//Integer sjid = sc.subjectIdByName(subjectName)
			//Integer sjid = map.get("sj_id");

			//
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return Response.status(200).entity("Under contruction").build(); // will dissapear
	}
}
