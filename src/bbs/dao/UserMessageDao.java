package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.UserMessage;

public class UserMessageDao {
	public List<UserMessage> getUserMessages(Connection connection) throws Exception{
		PreparedStatement ps  = null;
		List<UserMessage> ret = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select posts.id, branch_id, department_id, users.name as name, title, text, category, insert_date from posts inner join users on posts.user_id = users.id order by insert_date DESC;");

			ps = connection.prepareStatement(sql.toString());
			System.out.println(ps.toString());

			ResultSet rs = ps.executeQuery();
			ret = toUserMessageList(rs);

		}finally{
			close(ps);
		}
		return ret;
	}

	public List<UserMessage> toUserMessageList(ResultSet rs) throws SQLException {
		List<UserMessage> ret = new ArrayList<UserMessage>();
		try{

			while(rs.next()){
				int id = rs.getInt("id");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				String name = rs.getString("name");
				String title = rs.getString("title");
				String category = rs.getString("category");

				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserMessage message = new UserMessage();

				message.setId(id);
				message.setName(name);
				message.setText(text);
				message.setTitle(title);
				message.setCategory(category);
				message.setInsertDate(insertDate);
				message.setBranchId(branchId);
				message.setDepartmentId(departmentId);

				ret.add(message);

			}
			return ret;
		}finally{
			rs.close();
		}

	}

	public List<UserMessage> getUserMessages(Connection connection, String category) throws SQLException {
		PreparedStatement ps  = null;
		List<UserMessage> ret = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select posts.id, users.name as name, branch_id, department_id, title, text, category, insert_date from posts inner join users on posts.user_id = users.id ");
			sql.append("where category = ? order by insert_date DESC;");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, category);

			System.out.println(ps.toString());

			ResultSet rs = ps.executeQuery();
			ret = toUserMessageList(rs);

		}finally{
			close(ps);
		}
		return ret;
	}

	public List<UserMessage> getUserMessages(Connection connection, String[] selectedDates) throws SQLException {
		PreparedStatement ps  = null;
		List<UserMessage> ret = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select posts.id, users.name as name, branch_id, department_id, title, text, category, insert_date from posts inner join users on posts.user_id = users.id ");

		String[] time = {" 00:00:00", " 23:59:59"};

		try{

			if(selectedDates[0].isEmpty()){
				sql.append("where insert_date <= ? order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				StringBuilder selectedDate = new StringBuilder();

				selectedDate.append(selectedDates[1]);
				selectedDate.append(time[1]);
				ps.setString(1, selectedDate.toString());

				System.out.println(ps.toString());

			}else if(selectedDates[1].isEmpty()){
				sql.append("where insert_date >= ? order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				StringBuilder selectedDate = new StringBuilder();

				selectedDate.append(selectedDates[0]);
				selectedDate.append(time[0]);
				ps.setString(1, selectedDate.toString());

				System.out.println(ps.toString());

			} else{
				sql.append("where insert_date between ? and ? order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				for(int i = 0; i < 2; i++){
					int index = i + 1;
					StringBuilder selectedDate = new StringBuilder();

					selectedDate.append(selectedDates[i]);
					selectedDate.append(time[i]);
					ps.setString(index, selectedDate.toString());
				}
				System.out.println(ps.toString());
			}
			ResultSet rs = ps.executeQuery();
			ret = toUserMessageList(rs);
		}catch(Exception e){
			e.printStackTrace();

		}finally{
			close(ps);
		}
		return ret;
	}

	public List<UserMessage> getUserMessages(Connection connection, String category, String[] selectedDates) {
		PreparedStatement ps  = null;
		List<UserMessage> ret = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select posts.id, users.name as name, branch_id, department_id, title, text, category, insert_date from posts inner join users on posts.user_id = users.id ");

			String[] time = {" 00:00:00", " 23:59:59"};

			if(selectedDates[0].isEmpty()){
				sql.append("where category = ? and insert_date <= ? order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				ps.setString(1, category);
				StringBuilder selectedDate = new StringBuilder();

				selectedDate.append(selectedDates[1]);
				selectedDate.append(time[1]);
				ps.setString(2, selectedDate.toString());

				System.out.println(ps.toString());

			}else if(selectedDates[1].isEmpty()){
				sql.append("where category = ? and insert_date >= ? order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				ps.setString(1, category);
				StringBuilder selectedDate = new StringBuilder();

				selectedDate.append(selectedDates[0]);
				selectedDate.append(time[0]);
				ps.setString(2, selectedDate.toString());

				System.out.println(ps.toString());
			}else{
				sql.append("where category = ? and insert_date between ? and ? order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				ps.setString(1, category);
				for(int i = 0; i < 2; i++){
					StringBuilder selectedDate = new StringBuilder();
					selectedDate.append(selectedDates[i]);
					selectedDate.append(time[i]);
					ps.setString(i + 2, selectedDate.toString());
				}

				System.out.println(ps.toString());
			}

			ResultSet rs = ps.executeQuery();
			ret = toUserMessageList(rs);
		}catch(Exception e){
			e.printStackTrace();

		}finally{
			close(ps);
		}
		return ret;
	}
}
