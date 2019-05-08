package entity.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import entity.interfaces.DBconnection;
import entity.interfaces.IStudent;
import model.Student;

public class StudentController implements IStudent {

	private Connection conn = null;

	public void testConnection() {
		try {
			conn = DBconnection.getConnection();
			System.out.println(String.format("Connected to database %s "
                    + "successfully.", conn.getCatalog()));
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void addStudent(Student s) {
		ResultSet rs = null;
        int candidateId = 0;

		try {
			String sql = "INSERT INTO student_st(st_fn,st_mn,st_ln,st_neptun) "
		            + "VALUES(?,?,?,?)";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, s.getName().getFirstName());
			pstmt.setString(2, s.getName().getMiddleName());
			pstmt.setString(3, s.getName().getLastName());
			pstmt.setString(4, s.getNeptun().getNeptun());
			 int rowAffected = pstmt.executeUpdate();
	            if(rowAffected == 1)
	            {
	                // get candidate id
	                rs = pstmt.getGeneratedKeys();
	                if(rs.next())
	                    candidateId = rs.getInt(1);

	            }

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}



	}

	@Override
	public Student findStudent(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Student[] allStudent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addStudents(Student[] ss) {
		// TODO Auto-generated method stub

	}

}
