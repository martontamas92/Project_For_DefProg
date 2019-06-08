package entity.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.interfaces.DBconnection;
import entity.interfaces.IAuth;
import model.Auth;

public class DemonstratorAuthController implements IAuth {

	private Connection conn;

	@Override
	public boolean add(Auth a)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		String sql = "INSERT into de_auth_a (a_de_id, a_un, a_pwd) " + "VALUES(?,?,?)";
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, a.getId().toString());
		pstmt.setString(2, a.getUname().getUname());
		pstmt.setString(3, a.getPasswd().getPasswd());
		int rowAffected = pstmt.executeUpdate();
		if (rowAffected != 1) {
			return false;
		}
		return true;

	}

	@Override
	public boolean userNameExists(String username)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		String sql = "SELECT * FROM de_auth_a " + "WHERE a_un = ?";
		conn = DBconnection.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, username);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return true;
		}
		return false;

	}

	@Override
	public boolean valid(String username, String passwd)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		if (userNameExists(username)) {
			String sql = "SELECT * FROM de_auth_a WHERE a_un like \"" + username + "\"";
			conn = DBconnection.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// pstmt.setString(1, username);

			ResultSet rs = pstmt.executeQuery();
			rs.next();
			System.out.println(rs.getString("a_pwd"));
			if (rs.getString("a_pwd").equals(passwd)) {
				return true;
			}
			return false;
		}
		return false;

	}

	public int getDemonstratorId(String username)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		String sql = "SELECT a_de_id FROM de_auth_a WHERE a_un like \"" + username + "\""; // rain check
		conn = DBconnection.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		// pstmt.setString(1, username);

		ResultSet rs = pstmt.executeQuery();
		rs.next();

		return rs.getInt("a_de_id");

	}
}