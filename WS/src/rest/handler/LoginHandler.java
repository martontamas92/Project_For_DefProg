package rest.handler;

import java.security.Key;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import business_model.KeyGenerator;
import business_model.Message;
import business_model.Password;
import business_model.SimpleKeyGenerator;
import business_model.UserName;
import business_model.WebResponse;
import entity.controller.DemonstratorAuthController;
import entity.controller.DemonstratorController;
import entity.controller.StudentAuthController;
import entity.controller.StudentController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import model.Demonstrator;
import model.Student;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Path("/authentication")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)


public class LoginHandler {
	private StudentAuthController studentAuthRepository = new StudentAuthController();
	private DemonstratorAuthController demonstratorAuthRepository = new DemonstratorAuthController();
	private StudentController studentRepository = new StudentController();
	private DemonstratorController demonstratorRepository = new DemonstratorController();
	//@Inject
	private KeyGenerator keyGenerator = new SimpleKeyGenerator();
	@Context
	private UriInfo uriInfo;

	// private Logger logger;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	// @PermitAll
	@Path("/login")

	public Response authenticateUser(@FormParam("uname") String username, @FormParam("passwd") String password,
			@FormParam("status") String status) {

		try {
//			System.out.print(data + "\n");
//			System.out.println("status: " + status);
//			System.out.println("username: " + username);
//			System.out.println("passwd: " + password);
//			System.out.println("is student: " + status.equals("student"));
//			System.out.println("is demonstrator: " + status.equals("demonstrator"));
//			System.out.println("demauth:" + authenticateDemonstrator(uname,passwd));
//			System.out.println("studauth:" + authenticateStudent(uname,passwd));
			// System.out.println(issueToken(uname));
			UserName uname = UserName.userNameBuilder(username);
			Password passwd = Password.passwordBuilder(password);
			String token = issueToken(uname);
//			System.out.println("token:" + token);

			if (status.equals("student") && authenticateStudent(uname, passwd)) {
//				System.out.println("stud auth ok");
				// System.out.println(studentAuthRepository.getStudentId(uname.getUname()));
				Integer id = studentAuthRepository.getStudentId(uname.getUname());
				Student st = studentRepository.findStudent(id);
				return Response.ok().header(AUTHORIZATION, "Bearer " + token).entity(st).build();
			}
			if (status.equals("demonstrator") && authenticateDemonstrator(uname, passwd)) {
//				System.out.println("dem auth ok");
				Integer id = demonstratorAuthRepository.getDemonstratorId(uname.getUname());
				Demonstrator demonstrator = demonstratorRepository.findDemonstrator(id);
				WebResponse wr = new WebResponse(token, demonstrator);
				return Response.ok().header(AUTHORIZATION, "Bearer " + token).entity(wr).build();
			}
			return Response.status(UNAUTHORIZED).entity(new Message("Hibás felhasználónév vagy jelszó!").toString())
					.build();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return Response.status(UNAUTHORIZED).entity(new Message("Autentikációs hiba történt!").toString()).build();
		}

	}

	public LoginHandler() {
	}

	private boolean authenticateStudent(UserName username, Password password) {
		try {
			StudentAuthController studentRepository = new StudentAuthController();
			if (studentRepository.valid(username.getUname(), password.getPasswd())) {
				return true;
			}
			return false;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	private boolean authenticateDemonstrator(UserName username, Password password)
	{

		DemonstratorAuthController demonstratorRepository = new DemonstratorAuthController();
		try {
			if (demonstratorRepository.valid(username.getUname(), password.getPasswd())) {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

	private String issueToken(UserName usname) {

		 Key key = keyGenerator.generateKey();
	        String jwtToken = Jwts.builder()
	                .setSubject(usname.getUname())
	                .setIssuer(uriInfo.getAbsolutePath().toString())
	                .setIssuedAt(new Date())
	                .setExpiration(toDate(LocalDateTime.now().plusMinutes(30L)))
	                .signWith(SignatureAlgorithm.HS512, key)
	                .compact();
	       // System.out.println("#### generating token for a key : " + jwtToken + " - " + key);
	        return jwtToken;



	}

	private Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}
