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
			String sql = "INSERT INTO demonstrator_de(de_fn,de_mn,de_ln) "
		            + "VALUES(?,?,?)";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, d.getName().getFirstName());
			pstmt.setString(2, d.getName().getMiddleName());
			pstmt.setString(3, d.getName().getLastName());

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
		// TODO Auto-generated method stub
		return null;
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
				Name name = Name.NameBuilder(rs.getString("de_fn"), rs.getString("de_mn"), rs.getString("de_ln"));
				Demonstrator d =new Demonstrator(name);
				demonstrators.add(d);
			}
			return demonstrators;
		}catch(Exception e ) {
			System.out.println(e.getMessage());
			return demonstrators;
		}

	}

	@Override
	public void addDemonstrators(Demonstrator[] ds) {
		// TODO Auto-generated method stub

	}

}
