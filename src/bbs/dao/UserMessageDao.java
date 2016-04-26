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
			sql.append("select users.name as name, title, text, category, insert_date from posts inner join users on posts.user_id = users.id order by insert_date DESC;");

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

				String name = rs.getString("name");
				String title = rs.getString("title");
				String category = rs.getString("category");

				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserMessage message = new UserMessage();

				message.setName(name);
				message.setText(text);
				message.setTitle(title);
				message.setCategory(category);
				message.setInsertDate(insertDate);

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
			sql.append("select users.name as name, title, text, category, insert_date from posts inner join users on posts.user_id = users.id ");
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
}
