package entity.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import entity.interfaces.DBconnection;
import entity.interfaces.IAuth;
import model.Auth;

public class DemonstratorAuthController implements IAuth {

	private Connection conn;
	@Override
	public boolean add(Auth a) {
		try {
			String sql = "INSERT into de_auth_a (a_de_id, a_un, a_pwd) " + "VALUES(?,?,?)";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a.getId().toString());
			pstmt.setString(2, a.getUname().getUname());
			pstmt.setString(3, a.getPasswd().getPasswd());
			int rowAffected = pstmt.executeUpdate();
			if(rowAffected != 1) {return false;}
			return true;

		}catch (Exception e) {

			System.out.println(e.getMessage());
			return false;
		}


	}

	@Override
	public boolean userNameExists(String username) {
		try {
		String sql = "SELECT * FROM de_auth_a " + "WHERE a_un = ?" ;
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, username);
		ResultSet rs  = pstmt.executeQuery();
		if(rs.next()) {return true;}
		return false;
		}catch (Exception e) {
			System.out.println("uname exists: " + e.getMessage());
			return false;
		}


	}

	@Override
	public boolean valid(String username, String passwd) {
		try {
			if (userNameExists(username)) {
				String sql = "SELECT * FROM de_auth_a " + "WHERE a_un like '?'";
				conn = DBconnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				if(rs.getString("a_pwd").equals(passwd)) {return true;				}
				return false;
			}
			return false;
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return false;

		}

	}

}
