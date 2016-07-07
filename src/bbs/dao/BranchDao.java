package bbs.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bbs.utils.DBUtil;

public class BranchDao {

	public static int getBranchId(String branchName) {
		PreparedStatement ps = null;
		Connection connection = DBUtil.getConnection();
		int result = 0;
		try {
			String sql = "select id from branches where name = ?;";
			ps = connection.prepareStatement(sql);
			ps.setString(1, branchName);
			ResultSet rs = ps.executeQuery();
			rs.next();
			result = rs.getInt("id");
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public static List<String> getBranches() {
		List<String> branchList = new ArrayList<String>();
		PreparedStatement ps = null;
		Connection connection = DBUtil.getConnection();

		try {
			String sql = "select name from branches;";
			ps = connection.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				branchList.add(rs.getString("name"));
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return branchList;
	}

}
