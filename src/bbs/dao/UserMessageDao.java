package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Comment;
import bbs.beans.Message;

public class UserMessageDao {

	private static final String FIXED_STRING = "select posts.id as post_id, users.id as user_id, branch_id, department_id, users.name as name, title, text, category, insert_date, timestampdiff(SECOND, posts.insert_date, CURRENT_TIMESTAMP) as elapsed_time from posts inner join users on posts.user_id = users.id ";

	public List<Message> getUserMessages(Connection connection) throws Exception {
		PreparedStatement ps = null;
		List<Message> ret = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(FIXED_STRING);
			sql.append("where status = true order by insert_date DESC;");

			ps = connection.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			ret = toUserMessageList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
		return ret;
	}

	public List<Message> toUserMessageList(ResultSet rs) throws SQLException {
		List<Message> ret = new ArrayList<Message>();
		try {

			while (rs.next()) {
				int id = rs.getInt("post_id");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");
				int userId = rs.getInt("user_id");
				String name = rs.getString("name");
				String title = rs.getString("title");
				String category = rs.getString("category");

				String text = rs.getString("text");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				Message message = new Message();
				long elapsedTime = rs.getLong("elapsed_time");

				message.setId(id);
				message.setName(name);
				message.setText(text);
				message.setTitle(title);
				message.setCategory(category);
				message.setInsertDate(insertDate);
				message.setBranchId(branchId);
				message.setDepartmentId(departmentId);
				message.setUserId(userId);
				message.setElapsedTime(elapsedTime);
				message.setElapsedTimeText(elapsedTime);

				ret.add(message);

			}
			return ret;
		} finally {
			rs.close();
		}

	}

	public List<Message> getUserMessages(Connection connection, String category) throws SQLException {
		PreparedStatement ps = null;
		List<Message> ret = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(FIXED_STRING);
			sql.append("where category = ? and status = true order by insert_date DESC;");

			ps = connection.prepareStatement(sql.toString());
			ps.setString(1, category);
			ResultSet rs = ps.executeQuery();
			ret = toUserMessageList(rs);

		} finally {
			close(ps);
		}
		return ret;
	}

	public List<Message> getUserMessages(Connection connection, String[] selectedDates) throws SQLException {
		PreparedStatement ps = null;
		List<Message> ret = null;
		StringBuilder sql = new StringBuilder();
		sql.append(FIXED_STRING);

		String[] time = { " 00:00:00", " 23:59:59" };

		try {

			if (selectedDates[0].isEmpty()) {
				sql.append("where insert_date <= ? and status = true order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				StringBuilder selectedDate = new StringBuilder();

				selectedDate.append(selectedDates[1]);
				selectedDate.append(time[1]);
				ps.setString(1, selectedDate.toString());

			} else if (selectedDates[1].isEmpty()) {
				sql.append("where insert_date >= ? and status = true order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				StringBuilder selectedDate = new StringBuilder();

				selectedDate.append(selectedDates[0]);
				selectedDate.append(time[0]);
				ps.setString(1, selectedDate.toString());

			} else {
				sql.append("where insert_date between ? and ? and status = true order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				for (int i = 0; i < 2; i++) {
					int index = i + 1;
					StringBuilder selectedDate = new StringBuilder();

					selectedDate.append(selectedDates[i]);
					selectedDate.append(time[i]);
					ps.setString(index, selectedDate.toString());
				}
			}
			ResultSet rs = ps.executeQuery();
			ret = toUserMessageList(rs);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			close(ps);
		}
		return ret;
	}

	public List<Message> getUserMessages(Connection connection, String category, String[] selectedDates) {
		PreparedStatement ps = null;
		List<Message> ret = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(FIXED_STRING);

			String[] time = { " 00:00:00", " 23:59:59" };

			if (selectedDates[0].isEmpty()) {
				sql.append("where category = ? and insert_date <= ? and status = true order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				ps.setString(1, category);
				StringBuilder selectedDate = new StringBuilder();

				selectedDate.append(selectedDates[1]);
				selectedDate.append(time[1]);
				ps.setString(2, selectedDate.toString());

			} else if (selectedDates[1].isEmpty()) {
				sql.append("where category = ? and insert_date >= ? and status = true order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				ps.setString(1, category);
				StringBuilder selectedDate = new StringBuilder();

				selectedDate.append(selectedDates[0]);
				selectedDate.append(time[0]);
				ps.setString(2, selectedDate.toString());
			} else {
				sql.append(
						"where category = ? and insert_date between ? and ? and status = true order by insert_date DESC;");
				ps = connection.prepareStatement(sql.toString());
				ps.setString(1, category);
				for (int i = 0; i < 2; i++) {
					StringBuilder selectedDate = new StringBuilder();
					selectedDate.append(selectedDates[i]);
					selectedDate.append(time[i]);
					ps.setString(i + 2, selectedDate.toString());
				}
			}
			ResultSet rs = ps.executeQuery();
			ret = toUserMessageList(rs);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			close(ps);
		}
		return ret;
	}

	public List<Comment> getComments(Connection connection) throws Exception {
		PreparedStatement ps = null;
		List<Comment> ret = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(
					"select comments.id, user_id, post_id, users.name as name, users.branch_id as branch_id, users.department_id as department_id, text, insert_date, timestampdiff(SECOND, comments.insert_date, CURRENT_TIMESTAMP) as elapsed_time  from comments inner join users on users.id = comments.user_id ");
			sql.append("where status = true order by id;");

			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			ret = toCommentList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
		return ret;
	}

	public List<Comment> toCommentList(ResultSet rs) throws SQLException {
		List<Comment> ret = new ArrayList<Comment>();
		try {

			while (rs.next()) {
				int id = rs.getInt("id");
				int postId = rs.getInt("post_id");
				int userId = rs.getInt("user_id");
				int branchId = rs.getInt("branch_id");
				int departmentId = rs.getInt("department_id");

				String text = rs.getString("text");
				String name = rs.getString("name");
				Timestamp insertDate = rs.getTimestamp("insert_date");
				long elapsedTime = rs.getLong("elapsed_time");

				Comment comment = new Comment();

				comment.setId(id);
				comment.setUserId(userId);
				comment.setPostId(postId);
				comment.setText(text);
				comment.setName(name);
				comment.setInsertDate(insertDate);
				comment.setBranchId(branchId);
				comment.setDepartmentId(departmentId);
				comment.setElapsedTime(elapsedTime);
				comment.setElapsedTimeText(elapsedTime);

				ret.add(comment);
			}
			return ret;
		} finally {
			rs.close();
		}

	}

}
