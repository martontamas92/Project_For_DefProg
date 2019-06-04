package entity.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import business_model.Name;
import business_model.Neptun_Code;
import entity.interfaces.DBconnection;
import entity.interfaces.IStudent;
import model.Demonstrator;
import model.Student;

public class StudentController implements IStudent {

	private Connection conn = null;

	/*
	 * public void testConnection() { try { conn = DBconnection.getConnection();
	 * System.out.println(String.format("Connected to database %s " +
	 * "successfully.", conn.getCatalog())); } catch (Exception e) {
	 * System.out.println(e.getMessage()); } }
	 */
	@Override
	public int addStudent(Student s)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ResultSet rs = null;
		int candidateId = 0;

		String sql = "INSERT INTO student_st(st_fn,st_ln,st_neptun) " + "VALUES(?,?,?)";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, s.getName().getFirstName());
		// pstmt.setString(2, s.getName().getMiddleName());
		pstmt.setString(2, s.getName().getLastName());
		pstmt.setString(3, s.getNeptun().getNeptun());
		int rowAffected = pstmt.executeUpdate();
		if (rowAffected == 1) {
			// get candidate id
			rs = pstmt.getGeneratedKeys();
			if (rs.next())
				candidateId = rs.getInt(1);

		}

		return candidateId;

	}

	@Override
	public Student findStudent(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		String sql = "Select * from student_st where st_id = ?";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		Name name = Name.NameBuilder(rs.getString("st_fn"), rs.getString("st_ln"));
		Neptun_Code nept = Neptun_Code.buildNeptun_Code(rs.getString("st_neptun"));
		Student st = Student.studentBuilder(name, nept);
		st.setId(id);

		return st;

	}

	@Override
	public ArrayList<Student> allStudent()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ArrayList<Student> students = new ArrayList<>();

		String sql = "Select * from student_st";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Name name = Name.NameBuilder(rs.getString("st_fn"), rs.getString("st_ln"));
			Neptun_Code neptun = Neptun_Code.buildNeptun_Code(rs.getString("st_neptun"));
			Student s = Student.studentBuilder(name, neptun);
			students.add(s);
		}
		return students;

	}

	@Override
	public void addStudents(Student[] ss) {
		// TODO Auto-generated method stub

	}

}
