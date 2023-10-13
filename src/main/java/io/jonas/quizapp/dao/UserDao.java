package io.jonas.quizapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import io.jonas.quizapp.beans.UserInfo;

public class UserDao {
	private Connection connect = null;
	private Statement stm = null;
	private PreparedStatement pstm = null;
	private int result;
	private ResultSet rs;

	// save the user into database
	public void createUserTable() {
		String sql = "create table `user_tbl`( `id` int(20) not null AUTO_INCREMENT,"
				+ " `username` varchar(200) not null," + "`email` varchar(200) not null,"
				+ "`password` varchar(200) not null," + "`role` varchar(200) not null,"
				+ " Primary key(`id`)) engine= InnoDB default char set=utf8mb4 collate=utf8mb4_0900_as_ci;";
		try {
			connect = JDBCUtil.getDbConnection();
			Statement stm = connect.createStatement();
			stm.execute(sql);
			System.out.println("table created");
		} catch (SQLSyntaxErrorException e) {
			// throw new SQLSyntaxErrorException("Table 'user_tbl' already exists");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JDBCUtil.closeResources(stm, connect);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public int register(UserInfo user) {

		String sql = "INSERT INTO `user_tbl` (username,email,password,role)  VALUES (?,?,?,?)";

		try {
			connect = JDBCUtil.getDbConnection();

			pstm = connect.prepareStatement(sql);
			pstm.setString(1, user.getUsername());
			pstm.setString(2, user.getEmail());
			pstm.setString(3, user.getPassword());
			pstm.setString(4, user.getRole());

			result = pstm.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				JDBCUtil.closeResources(pstm, connect);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;

	}

	public UserInfo getUserByName(String username) {
		UserInfo user = new UserInfo();
		try {
			String sql = "Select * FROM `user_tbl` where username=?";
			connect = JDBCUtil.getDbConnection();
			// stm = connect.createStatement();
			pstm = connect.prepareStatement(sql);
			if (pstm != null) {
				pstm.setString(1, username);
				rs = pstm.executeQuery();

			}
			if (rs != null) {
				if (rs.next()) {
					System.out.println("result not null");
					user = new UserInfo();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setEmail(rs.getString("email"));
					user.setRole(rs.getString("role"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				JDBCUtil.closeResources(rs, pstm, connect);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println(user);
		return user;

	}

}
