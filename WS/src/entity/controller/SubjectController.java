package entity.controller;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	private Connection conn = null;

	@Override
	public int addSubject(Subject s)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ResultSet rs;
		int subjectId = 0;

		String sql = "INSERT INTO subject_sj(sj_d_id, sj_name) " + "VALUES(?,?,?)";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//			String id = dm.demonstratorByName(s.getDemonstrator().getName()).toString();
		String id = s.getDemonstrator().getId().toString();
		System.out.println(id);
		pstmt.setString(1, id); // need to get id from demonstrator table;
		pstmt.setString(2, s.getSubjectName());
		pstmt.setString(3, s.getSubjectMajor());
		int rowAffected = pstmt.executeUpdate();
		if (rowAffected == 1) {
			// get candidate id
			rs = pstmt.getGeneratedKeys();
			if (rs.next())
				subjectId = rs.getInt(1);

		}

		return subjectId;
	}

	public boolean subjectExists(String subjectName) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		String sql = "SELECT * FROM subject_sj where sj_name like ?";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, subjectName);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return false;
		}

		return true;
	}

	public Integer subjectIdByName(String subjectName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		String sql = "SELECT sj_id FROM subject_sj where sj_name = ?";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, subjectName);
		ResultSet rs = pstmt.executeQuery();
		rs.next();
		return rs.getInt("sj_id");

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
	public ArrayList<HashMap<String, String>> allSubjectByDeId(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		ArrayList<HashMap<String, String>> resultList = new ArrayList<>();
		String sql = "SELECT * FROM subject_sj where sj_d_id = ?";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			HashMap<String, String> hs = new HashMap<>();
			hs.put("value", rs.getString("sj_name"));
			hs.put("label", rs.getString("sj_name"));
			resultList.add(hs);
		}
		return resultList;

	}


	@Override
	public int createLecture(Lecture lecture)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ResultSet rs;
		int subjectId = 0;

		String sql = "INSERT INTO lecture_le(le_sj_id, le_date) " + "VALUES(?,STR_TO_DATE(?,'%Y-%m-%d'))";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//			String id = dm.demonstratorByName(s.getDemonstrator().getName()).toString();
		// pstmt.setInt(2, lecture.getSubject().getDemonstrator().getId()); // need to
		// get id from demonstrator table;
		pstmt.setInt(1, subjectIdByName(lecture.getSubject().getSubjectName()));
		pstmt.setString(2, lecture.getDay().toString());
//			System.out.println(pstmt.toString());
		int rowAffected = pstmt.executeUpdate();
		if (rowAffected == 1) {
			// get candidate id
			rs = pstmt.getGeneratedKeys();
			if (rs.next())
				subjectId = rs.getInt(1);

		}

		return subjectId;

	}
	@Override
	public int attendLecture(Integer st_id, Integer sj_id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ResultSet rs;
		int subjectId = 0;

		String sql = "INSERT INTO sj_st_attend_at(at_st_id,at_sj_id)" + "VALUES(?,?)";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setInt(1, st_id);
		pstmt.setInt(2, sj_id);
		int rowAffected = pstmt.executeUpdate();
		if (rowAffected == 1) {
			// get candidate id
			rs = pstmt.getGeneratedKeys();
			if (rs.next())
				subjectId = rs.getInt(1);

		}

		return subjectId;
	}
	@Override
	public ArrayList<HashMap<String, String>> allSubjectByStId(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		ArrayList<HashMap<String, String>> resultList = new ArrayList<>();

		String sql = "select sj_id, CONCAT(de_fn, \" \", de_mn, \" \", de_ln) as name, sj_name from demonstrator_de, subject_sj where sj_d_id = de_id and sj_id in(\r\n" +
				"select sj_id from subject_sj where sj_id not in (\r\n" +
				"select at_sj_id from sj_st_attend_at where at_st_id=?\r\n" +
				")\r\n" +
				");";

		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			HashMap<String, String> hm = new HashMap<>();
			Integer sjId = rs.getInt("sj_id");
			String deName = rs.getString("name");
			String sjName = rs.getString("sj_name");
			hm.put("id", sjId.toString());
			hm.put("name", deName);
			hm.put("subjectName", sjName);
			// resultList.add();
			// Subject s = new Subject(subjectName, demonstrator);
			resultList.add(hm);
		}
		return resultList;

	}

	@Override
	public void addSubjects(Subject[] ss) {
		// TODO Auto-generated method stub

	}
	@Override
	public Integer attendOnClass(Integer st_id, Integer le_id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ResultSet rs;
		int subjectId = 0;

			String sql = "INSERT INTO presence_pe(pe_st_id,pe_le_id)" + "VALUES(?,?)";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setInt(1, st_id);
			pstmt.setInt(2, le_id);
			// pstmt.setString(3, LocalDateTime.now().toString());
			int rowAffected = pstmt.executeUpdate();
			if (rowAffected == 1) {
				// get candidate id
				rs = pstmt.getGeneratedKeys();
				if (rs.next())
					subjectId = rs.getInt(1);

			}

		return subjectId;
	}

	public ArrayList<HashMap<String,String>> allLectures(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		ArrayList<HashMap<String,String>> resultList = new ArrayList<>();
		String sql = "select "
				+ "sj_name,  count(le_id) as osszesen, count(pe_id) as resztvett"
				+ " from ((sj_st_attend_at a Join subject_sj s on a.at_sj_id = s.sj_id)"
				+ "  left join lecture_le l on l.le_sj_id = sj_id)"
				+ " left join presence_pe on pe_le_id = le_id"
				+ " where at_st_id = ?"
				+ " group by sj_id;";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		//pstmt.setInt(2, id);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {

			HashMap<String, String> hs1 = new HashMap<>();
			hs1.put("subject_name", rs.getString("sj_name"));
			hs1.put("all", rs.getString("osszesen"));
			hs1.put("attended", rs.getString("resztvett"));

			resultList.add(hs1);
		}
		return resultList;

	}

	public ArrayList<HashMap<String,String>> pastLectures(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		ArrayList<HashMap<String, String>> resultList = new ArrayList<>();
		String sql = "select * from subject_sj s , lecture_le l  where le_sj_id = sj_id and sj_d_id = ?;";

		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			HashMap<String, String> hs = new HashMap<>();
			hs.put("class", rs.getString("sj_name"));
			hs.put("date", rs.getDate("le_date").toString());
			hs.put("id", rs.getString("le_id"));
			resultList.add(hs);
		}
		return resultList;
	}


	public ArrayList<HashMap<String, String>> absences(Integer id) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		ArrayList<HashMap<String, String>> resultList = new ArrayList<>();
		String sql = "select st_neptun, CONCAT(st_fn, \" \" , st_ln) as nev, \"RESZTVETT\" as attended from student_st join presence_pe on st_id = pe_st_id where pe_le_id = ?\r\n" +
				"UNION\r\n" +
				"select st_neptun, CONCAT(st_fn, \" \" , st_ln) as nev, \"HIANYZOTT\" as attended from student_st join presence_pe on st_id != pe_st_id where pe_le_id = ?;\r\n";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		pstmt.setInt(2, id);
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			HashMap<String,String> hs = new HashMap<>();
			hs.put("name", rs.getString("nev"));
			hs.put("neptun", rs.getString("st_neptun"));
			hs.put("attended", rs.getString("attended"));
			resultList.add(hs);
		}
		return resultList;
	}
}
