package bbs.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.User;

public class UserDao {



//	public void insert(Connection connection, User user) {
//		PreparedStatement ps = null;
//		try{
//			StringBuilder sql = new StringBuilder();
//
//			sql.append("insert into user ( ");
//			sql.append("account, name, email, password, description, insert_date, update_date");
//			sql.append(" ) values ( ");
//			sql.append("?, ?, ?, ?, ?, ");
//			sql.append("CURRENT_TIMESTAMP, ");
//			sql.append("CURRENT_TIMESTAMP);");
//			ps = connection.prepareStatement(sql.toString());
//
//			ps.setString(1, user.getAccount());
//			ps.setString(2, user.getName());
//			ps.setString(3, user.getEmail());
//			ps.setString(4, user.getPassword());
//			ps.setString(5, user.getDescription());
//			System.out.println(sql.toString());
//			ps.executeUpdate();
//
//		}catch(SQLException e){
////			throw new SQLRuntimeException(e);
//		}finally{
//			close(ps);
//		}
//	}

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
				user.setBranch_Id(branchId);
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

//	public User getUser(Connection connection, int id) {
//		PreparedStatement ps = null;
//		List<User> userList = null;
//		try{
//			String sql = "select * from user where id = ?";
//			ps = connection.prepareStatement(sql);
//			ps.setInt(1, id);
//
//			ResultSet rs = ps.executeQuery();
//			userList = toUserList(rs);
//			if(userList.isEmpty()){
//				return null;
//			}else if(2 <= userList.size()){
//				throw new IllegalStateException("2 <= userList.size()");
//			}
//		}catch(Exception e){
//
//		}finally{
//			close(ps);
//		}
//		return userList.get(0);
//	}

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
