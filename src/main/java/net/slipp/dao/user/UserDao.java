package net.slipp.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.slipp.domain.user.User;
import net.slipp.support.jdbc.ConnectionManager;

public class UserDao {
	public void save(User user) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getName());
			pstmt.setString(4, user.getEmail());

			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}		
	}

	public User findByUserId(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();

			User user = null;
			if (rs.next()) {
				user = new User();
				user.setUserId(rs.getString("userId"));
				user.setPassword(rs.getString("password"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
			}

			return user;
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (con != null) {
				con.close();
			}
		}
	}
}