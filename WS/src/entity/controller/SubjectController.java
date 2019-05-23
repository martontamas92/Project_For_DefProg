package entity.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import entity.interfaces.DBconnection;
import entity.interfaces.ISubject;
import model.Subject;

public class SubjectController implements ISubject {
	private DemonstratorController dm = new DemonstratorController();

	private Connection conn = null;
	@Override
	public int addSubject(Subject s) {
		ResultSet rs;
		int subjectId = 0;
		try {
			String sql = "INSERT INTO subject_sj(sj_d_id, sj_name) " + "VALUES(?,?)";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			String id = dm.demonstratorByName(s.getDemonstrator().getName()).toString();
			pstmt.setString(1, id); // need to get id from demonstrator table;
			pstmt.setString(2, s.getSubjectName());
			int rowAffected = pstmt.executeUpdate();
            if(rowAffected == 1)
            {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    subjectId = rs.getInt(1);

            }

		}catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return subjectId;
	}

	@Override
	public Subject findSubject(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Subject> allSubject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSubjects(Subject[] ss) {
		// TODO Auto-generated method stub

	}

}
