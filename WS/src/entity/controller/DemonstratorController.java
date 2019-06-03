package entity.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import business_model.Name;
import business_model.Neptun_Code;
import entity.interfaces.DBconnection;
import entity.interfaces.IDemonstrator;
import model.Demonstrator;


public class DemonstratorController implements IDemonstrator {
	private Connection conn;
	@Override
	public int addDemonstrator(Demonstrator d) {
		ResultSet rs = null;
        int candidateId = 0;

		try {
			String sql = "INSERT INTO demonstrator_de(de_fn,de_ln) "
		            + "VALUES(?,?)";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, d.getName().getFirstName());
			//pstmt.setString(2, d.getName().getMiddleName());
			pstmt.setString(2, d.getName().getLastName());

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

		return candidateId;

	}

	@Override
	public Demonstrator findDemonstrator(Integer id) {

		try {
			String sql = "Select * from demonstrator_de where de_id = ?";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
				Name name = Name.NameBuilder(rs.getString("de_fn"), rs.getString("de_ln"));
				Demonstrator d =new Demonstrator(name);
				d.setId(id);

			return d;
		}catch(Exception e ) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public ArrayList<Demonstrator> allDemonstrator() {
		ArrayList<Demonstrator> demonstrators = new ArrayList<>();
		try {
			String sql = "Select * from demonstrator_de";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				Name name = Name.NameBuilder(rs.getString("de_fn"), rs.getString("de_ln"));
				Demonstrator d =new Demonstrator(name);
				demonstrators.add(d);
			}
			return demonstrators;
		}catch(Exception e ) {
			System.out.println(e.getMessage());
			return demonstrators;
		}

	}
	public Integer demonstratorByName(Name n) {
		try {
			System.out.println(n.toString());
			String sql = "Select de_id From demonstrator_de where de_fn like ? and de_ln like ?";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, n.getFirstName());
//			pstmt.setString(2, n.getMiddleName());
			pstmt.setString(2, n.getLastName());

			System.out.println(pstmt.toString());
			ResultSet rs  = pstmt.executeQuery();
			rs.next();
			System.out.println(rs.getInt("de_id"));
			return rs.getInt("de_id");
		}catch (Exception e) {
			System.out.println(e.getMessage());
			return 0;
		}


	}
	@Override
	public void addDemonstrators(Demonstrator[] ds) {
		// TODO Auto-generated method stub

	}

}
