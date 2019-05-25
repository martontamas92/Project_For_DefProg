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

	public Integer subjectIdByName(String subjectName) {
		try {
			String sql = "SELECT sj_id FROM subject_sj where sj_name = ?";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subjectName);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("sj_id");

		}catch (Exception e) {
			// TODO: handle exception
		}
		return 0;
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

	public ArrayList<String> allSubjectByDeId(Integer id){
		try {
			ArrayList<String>resultList = new ArrayList<>();
			String sql = "SELECT * FROM subject_sj where sj_d_id = ?";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				//Subject s = new Subject(subjectName, demonstrator);
				resultList.add(rs.getString("sj_name"));
			}
			return resultList;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}


	}

	@Override
	public void addSubjects(Subject[] ss) {
		// TODO Auto-generated method stub

	}

}
