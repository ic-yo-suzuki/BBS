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
import bbs.exception.NoRowsUpdatedRuntimeException;
import bbs.exception.SQLRuntimeException;


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
				boolean status = rs.getBoolean("status");
				String branchName = getBranchName(id, branchId);
				String departmentName = getDepartmentName(id, departmentId);

				User user = new User();
				user.setId(id);
				user.setBranchId(branchId);
				user.setBranchName(branchName);
				user.setDepartmentId(departmentId);
				user.setDepartmentName(departmentName);
				user.setLoginId(loginId);
				user.setName(name);
				user.setPassword(password);
				user.setStatus(status);

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

			ResultSet rs = ps.executeQuery();
			userList = toUserList(rs);
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
			String sql = "select * from users where id = ?";
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

	public List<User> getUserList(Connection connection) {
		PreparedStatement ps = null;
		List<User> userList = null;
		try{
			String sql = "select * from users order by branch_id, department_id, id;";
			ps = connection.prepareStatement(sql);

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
		return userList;
	}

	public String getBranchName(int userId, int branchId){
		PreparedStatement ps = null;
		String branchName = null;
		Connection connection = getConnection();

		try{
			String sql = "select branches.name as branch_name from branches inner join users on branches.id = users.branch_id where users.id = ? and users.branch_id = ?;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, branchId);

			ResultSet rs = ps.executeQuery();
			rs.next();
			branchName = rs.getString("branch_name");

		}catch(Exception e){

		}finally{
			close(ps);
		}
		return branchName;
	}

	public String getDepartmentName(int userId, int departmentId){
		PreparedStatement ps = null;
		String departmentName = null;
		Connection connection = getConnection();

		try{
			String sql = "select departments.name as department_name from departments inner join users on departments.id = users.department_id where users.id = ? and users.department_id = ?;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, departmentId);

			ResultSet rs = ps.executeQuery();
			rs.next();
			departmentName = rs.getString("department_name");

		}catch(Exception e){

		}finally{
			close(ps);
		}
		return departmentName;
	}

	public boolean getStatus(Connection connection, int id){
		PreparedStatement ps = null;
		connection = getConnection();
		boolean status = false;

		try{
			String sql = "select status from users where id = ?;";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			rs.next();
			status = rs.getBoolean("status");

		}catch(Exception e){

		}finally{
			close(ps);
		}
		return status;
	}

	public void changeStatus(Connection connection, int id, boolean status){
		PreparedStatement ps = null;

		try{
			StringBuilder sql = new StringBuilder();
			sql.append("update users set status = ? where id = ?");
			ps = connection.prepareStatement(sql.toString());
			ps.setBoolean(1, !status);
			ps.setInt(2, id);
			if((ps.executeUpdate()) == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}
	}

	public void update(Connection connection, User user, boolean passwordModifyFlg) {
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			if(passwordModifyFlg){
				sql.append("update users set login_id = ?, name = ?, password = ?, branch_id = ?, department_id = ? ");
				sql.append("where id = ?");

				ps = connection.prepareStatement(sql.toString());

				ps.setString(1, user.getLoginId());
				ps.setString(3, user.getPassword());
				ps.setString(2, user.getName());
				ps.setInt(4, user.getBranchId());
				ps.setInt(5, user.getDepartmentId());
				ps.setInt(6, user.getId());
			}else{
				sql.append("update users set login_id = ?, name = ?, branch_id = ?, department_id = ? ");
				sql.append("where id = ?");

				ps = connection.prepareStatement(sql.toString());

				ps.setString(1, user.getLoginId());
				ps.setString(2, user.getName());
				ps.setInt(3, user.getBranchId());
				ps.setInt(4, user.getDepartmentId());
				ps.setInt(5, user.getId());
			}

			if((ps.executeUpdate()) == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch(SQLException e){
			throw new SQLRuntimeException(e);
		}finally{
			close(ps);
		}

	}

	public void delete(Connection connection, int id){
		String[] sql = new String[3];
		sql[0] = "delete posts from posts inner join users on users.id = posts.user_id where users.id = ?;";
		sql[1] = "delete comments from comments inner join users on users.id = comments.user_id where users.id = ?;";
		sql[2] = "delete from users where id = ?";

		PreparedStatement ps = null;
		for(int i = 0; i < 3; i++){
			try{
				ps = connection.prepareStatement(sql[i].toString());
				ps.setInt(1, id);
				ps.executeUpdate();
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				close(ps);
			}
		}
	}
	public boolean isExistUser(Connection connection, int id){
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) as count from users where id = ?;");
		PreparedStatement ps = null;
		try{
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if(rs.getInt("count") == 1){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}

	}
}
