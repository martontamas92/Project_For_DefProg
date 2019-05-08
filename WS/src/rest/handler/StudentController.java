package rest.handler;



import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.web.bind.annotation.RequestParam;

import business_model.Name;
import business_model.Neptun_Code;
import entity.interfaces.IStudent;
import model.Student;



@Path("/student")
public class StudentController {
	@Autowired
	private IStudent studentRepository;
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/registrate")

	public Response proba(String data) {


		JSONObject json = new JSONObject(data);
		String firstName = json.getJSONObject("Name").getString("firstName");
		String middleName = json.getJSONObject("Name").getString("middleName");
		String lastName = json.getJSONObject("Name").getString("lastName");
		String neptun = json.getJSONObject("Neptun").getString("neptun");

		/*
		System.out.println("fn: " + firstName);
		System.out.println("mn: " + middleName);
		System.out.println("ln: " + lastName);
		System.out.println("nept: " + neptun);
		*/
		Name name = Name.NameBuilder(firstName, middleName, lastName);
		Neptun_Code nept = Neptun_Code.buildNeptun_Code(neptun);

		try {
			Student st = Student.studentBuilder(name, nept);

			return Response.status(201).entity(st.toString()).build();
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

}
