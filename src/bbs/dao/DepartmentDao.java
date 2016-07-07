package bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bbs.utils.DBUtil;

public class DepartmentDao {
	public static int getDepartmentId(String departmentName) {
		PreparedStatement ps = null;
		Connection connection = DBUtil.getConnection();
		int result = 0;
		try {
			String sql = "select id from departments where name = ?;";
			ps = connection.prepareStatement(sql);
			ps.setString(1, departmentName);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = rs.getInt("id");
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
		}

		return result;
	}

	public static List<String> getDepartments() {
		List<String> departmentList = new ArrayList<String>();
		PreparedStatement ps = null;
		Connection connection = DBUtil.getConnection();
		StringBuilder sql = new StringBuilder();
		sql.append("select name from departments;");
		try {

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				departmentList.add(rs.getString("name"));
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return departmentList;
	}

}
