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

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/registrate")
	public Response addStudent(String data) {

		try {
			JSONObject json = new JSONObject(data);

			String firstName = json.getJSONObject("Name").getString("firstName");
			String lastName = json.getJSONObject("Name").getString("lastName");
			String neptun = json.getJSONObject("Neptun").getString("neptun");
			String passwd = json.getJSONObject("Auth").getString("passwd");
			String uname = json.getJSONObject("Auth").getString("uname");

			Name name = Name.NameBuilder(firstName, lastName);
			Neptun_Code nept = Neptun_Code.buildNeptun_Code(neptun);
			UserName userName = UserName.userNameBuilder(uname);
			Password password = Password.passwordBuilder(passwd);

			ArrayList<Student> students = studentRepository.allStudent();
			Student st = Student.studentBuilder(name, nept);

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
			System.out.println(e.getMessage());
			return Response.status(500).entity(new Message(e.getMessage().toString())).build();
		}

	}

}
