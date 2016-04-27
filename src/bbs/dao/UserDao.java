package bbs.dao;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.User;


public class UserDao {



	public void insert(Connection connection, User user) {
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

			sql.append("insert into users ( ");
			sql.append("login_id, password, name, branch_id, department_id, status");
			sql.append(" ) values ( ");
			sql.append("?, ?, ?, ?, ?, ");
			sql.append("false);");
			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getDepartmentId());
			System.out.println(ps.toString());
			ps.executeUpdate();

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
	}

	private List<User> toUserList(ResultSet rs) throws Exception {
		List<User> ret = new ArrayList<User>();
		try{
			while(rs.next()){
				int id = rs.getInt("id");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				String name = rs.getString("name");
				String loginId = rs.getString("login_id");
				String password = rs.getString("password");


				User user = new User();
				user.setId(id);
				user.setBranchId(branchId);
				user.setDepartmentId(departmentId);
				user.setLoginId(loginId);
				user.setName(name);
				user.setPassword(password);


				ret.add(user);
			}
			return ret;

		}finally{
			rs.close();
		}
	}

	public User getUser(Connection connection, String loginId, String encPassword) {
		PreparedStatement ps = null;
		List<User> userList = null;
		try{
			String sql = "select * from users where login_id = ? and password = ? and status = true;";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			ps.setString(2, encPassword);
			System.out.println(ps.toString());

			ResultSet rs = ps.executeQuery();
			System.out.println("SQL実行");
			userList = toUserList(rs);
			System.out.println(userList.size());
			if(userList.isEmpty()){
				return null;
			}else if(2 <= userList.size()){
				throw new IllegalStateException("2 <= userList.size()");
			}else{

			}
			if(ps != null){
				ps.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{

		}
		return userList.get(0);
	}

	public static boolean isExist(String loginId) {
		PreparedStatement ps = null;
		Connection connection = getConnection();
		int result = 0;
		try{
			String sql = "select count(login_id) as result from users where login_id = ?;";
			ps = connection.prepareStatement(sql);
			ps.setString(1, loginId);
			System.out.println(ps.toString());

			ResultSet rs = ps.executeQuery();
			rs.next();
			result = rs.getInt("result");
			if(ps != null){
				ps.close();
			}
		}catch(Exception e){
		}
		if(result == 1){
			return true;
		}
		return false;
	}


	public User getUser(Connection connection, int id) {
		PreparedStatement ps = null;
		List<User> userList = null;
		try{
			String sql = "select * from user where id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			userList = toUserList(rs);
			if(userList.isEmpty()){
				return null;
			}else if(2 <= userList.size()){
				throw new IllegalStateException("2 <= userList.size()");
			}
		}catch(Exception e){

		}finally{
			close(ps);
		}
		return userList.get(0);
	}

//	public void update(Connection connection, User user) {
//		PreparedStatement ps = null;
//		try{
//			StringBuilder sql = new StringBuilder();
//			sql.append("update user set account = ?, name = ?, email = ?, password = ?, description = ?, update_date = CURRENT_TIMESTAMP ");
//			sql.append("where id = ? and update_date = ?;");
//
//			ps = connection.prepareStatement(sql.toString());
//			ps.setString(1, user.getAccount());
//			ps.setString(2, user.getName());
//			ps.setString(3, user.getEmail());
//			ps.setString(4, user.getPassword());
//			ps.setString(5, user.getDescription());
//			ps.setInt(6, user.getId());
//			ps.setTimestamp(7, new Timestamp(user.getUpdateDate().getTime()));
//			System.out.println(ps.toString());
//			if((ps.executeUpdate()) == 0){
//				throw new NoRowsUpdatedRuntimeException();
//			}
//		}catch(SQLException e){
//			throw new SQLRuntimeException(e);
//		}finally{
//			close(ps);
//		}
//
//	}

}
