package entity.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import javax.validation.constraints.AssertFalse.List;

import com.mysql.cj.protocol.ResultListener;

import business_model.Name;
import entity.interfaces.DBconnection;
import entity.interfaces.ISubject;
import model.Demonstrator;
import model.Lecture;
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

	public ArrayList<HashMap<String,String>> allSubjectByDeId(Integer id){
		try {
			ArrayList<HashMap<String,String>>resultList = new ArrayList<>();
			String sql = "SELECT * FROM subject_sj where sj_d_id = ?";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				HashMap<String,String> hs = new HashMap<>();
				hs.put("value", rs.getString("sj_name"));
				hs.put("label", rs.getString("sj_name"));
				resultList.add(hs);
			}
			return resultList;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}


	}

	public int createLecture(Lecture lecture){
		ResultSet rs;
		int subjectId = 0;
		try {
			String sql = "INSERT INTO lecture_le(le_sj_id, le_date) " + "VALUES(?,STR_TO_DATE(?,'%Y-%m-%d'))";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
//			String id = dm.demonstratorByName(s.getDemonstrator().getName()).toString();
			//pstmt.setInt(2, lecture.getSubject().getDemonstrator().getId()); // need to get id from demonstrator table;
			pstmt.setInt(1, subjectIdByName(lecture.getSubject().getSubjectName()));
			pstmt.setString(2, lecture.getDay().toString());
//			System.out.println(pstmt.toString());
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

	public int attendLecture(Integer st_id, Integer sj_id) {
		ResultSet rs;
		int subjectId = 0;
		try {
			String sql = "INSERT INTO sj_st_attend_at(at_st_id,at_sj_id)" + "VALUES(?,?)";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, st_id);
			pstmt.setInt(2, sj_id);
			int rowAffected = pstmt.executeUpdate();
            if(rowAffected == 1)
            {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    subjectId = rs.getInt(1);

            }
		}catch (Exception e) {
			// TODO: handle exception
		}
		return subjectId;
	}

	public ArrayList<HashMap<String,String>> allSubjectByStId(Integer id){

		ArrayList<HashMap<String,String>> resultList = new ArrayList<>();
		try {

			String sql = "select sj_id, CONCAT(de_fn, \" \", de_mn, \" \", de_ln) as name, sj_name from demonstrator_de, subject_sj where sj_d_id = de_id and de_id in(\r\n" +
					"	select sj_d_id from subject_sj where sj_id not in (\r\n" +
					"		select at_sj_id from sj_st_attend_at where at_st_id=?\r\n" +
					"	)\r\n" +
					");";


			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				HashMap<String,String> hm = new HashMap<>();
				Integer sjId = rs.getInt("sj_id");
				String deName = rs.getString("name");
				String sjName = rs.getString("sj_name");
				hm.put("id", sjId.toString());
				hm.put("name", deName);
				hm.put("subjectName", sjName);
				//resultList.add();
				//Subject s = new Subject(subjectName, demonstrator);
				resultList.add(hm);
			}
			return resultList;
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return resultList;
		}


	}

	@Override
	public void addSubjects(Subject[] ss) {
		// TODO Auto-generated method stub

	}

	public Integer attendOnClass(Integer st_id, Integer le_id) {
		ResultSet rs;
		int subjectId = 0;
		try {
			String sql = "INSERT INTO presence_pe(pe_st_id,pe_le_id)"+ "VALUES(?,?)";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, st_id);
			pstmt.setInt(2, le_id);
			//pstmt.setString(3, LocalDateTime.now().toString());
			int rowAffected = pstmt.executeUpdate();
            if(rowAffected == 1)
            {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    subjectId = rs.getInt(1);

            }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return subjectId;
	}

}
