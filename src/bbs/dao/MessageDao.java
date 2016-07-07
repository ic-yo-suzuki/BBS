package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bbs.beans.Comment;
import bbs.beans.Message;
import bbs.beans.NgWord;

public class MessageDao {

	public List<String> getCategories(Connection connection) {
		PreparedStatement ps = null;
		List<String> categories = new ArrayList<String>();
		categories.add("カテゴリを選択してください");
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct category from posts order by category;");
			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				categories.add(rs.getString("category"));
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {

		}

		return categories;
	}

	public void insert(Connection connection, Message message) throws Exception {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("insert into posts(user_id, title, text, category, insert_date) ");
			sql.append("values(?, ?, ?, ?, CURRENT_TIMESTAMP);");
			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, message.getId());
			ps.setString(2, message.getTitle());
			ps.setString(3, message.getText());
			ps.setString(4, message.getCategory());
			ps.toString();
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	public void delete(Connection connection, int id, String operation) throws Exception {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append(operation);
			sql.append("where id = ?;");
			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, id);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	public void insertComment(Connection connection, Comment comment) throws SQLException {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("insert into comments(user_id, post_id, text, insert_date) ");
			sql.append("values(?, ?, ?, CURRENT_TIMESTAMP);");
			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, comment.getUserId());
			ps.setInt(2, comment.getPostId());
			ps.setString(3, comment.getText());
			ps.toString();
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	public void postDelete(Connection connection, int id) {
		PreparedStatement ps_post = null;
		PreparedStatement ps_comment = null;

		try {
			StringBuilder sql_post = new StringBuilder();
			StringBuilder sql_comment = new StringBuilder();

			sql_post.append("delete from posts ");
			sql_post.append("where id = ?;");
			sql_comment.append("delete from comments ");
			sql_comment.append("where post_id = ?;");
			ps_post = connection.prepareStatement(sql_post.toString());
			ps_comment = connection.prepareStatement(sql_comment.toString());

			ps_post.setInt(1, id);
			ps_comment.setInt(1, id);

			ps_post.executeUpdate();
			ps_comment.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps_post);
			close(ps_comment);
		}
	}

	public List<NgWord> getNgWord(Connection connection) {
		PreparedStatement ps = null;
		List<NgWord> ngWord = new ArrayList<NgWord>();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("select * from ngwords order by id;");
			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String word = rs.getString("word");
				NgWord ng = new NgWord();
				ng.setId(id);
				ng.setWord(word);
				ngWord.add(ng);
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ngWord;
	}

	public void deleteComment(Connection connection, int id) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("delete from comments ");
			sql.append("where id = ?;");
			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, id);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	public int[] getUserPostCount(Connection connection, int id) {
		PreparedStatement ps_post = null;
		PreparedStatement ps_comment = null;

		int[] count = new int[2];
		try {
			StringBuilder sql_post = new StringBuilder();
			StringBuilder sql_comment = new StringBuilder();

			sql_post.append("select count(*) as count from posts inner join users on users.id = posts.user_id ");
			sql_post.append("where posts.user_id = ? and users.status = true;");

			ps_post = connection.prepareStatement(sql_post.toString());
			ps_post.setInt(1, id);

			sql_comment
					.append("select count(*) as count from comments inner join users on users.id = comments.user_id ");
			sql_comment.append("where comments.user_id = ? and users.status = true;");

			ps_comment = connection.prepareStatement(sql_comment.toString());
			ps_comment.setInt(1, id);

			ResultSet rs = ps_post.executeQuery();
			rs.next();
			count[0] = rs.getInt("count");

			rs = ps_comment.executeQuery();
			rs.next();
			count[1] = rs.getInt("count");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps_post);
			close(ps_comment);
		}
		return count;
	}

	public int[] getBranchPostCount(Connection connection, int id) {
		PreparedStatement ps_post = null;
		PreparedStatement ps_comment = null;

		int[] count = new int[2];
		try {
			StringBuilder sql_post = new StringBuilder();
			StringBuilder sql_comment = new StringBuilder();

			sql_post.append(
					"select count(posts.text) as count from users inner join posts on users.id = posts.user_id ");
			sql_post.append("where branch_id = (select branch_id from users where id = ?) and status = true;");

			ps_post = connection.prepareStatement(sql_post.toString());
			ps_post.setInt(1, id);

			sql_comment.append(
					"select count(comments.text) as count from users inner join comments on users.id = comments.user_id ");
			sql_comment.append("where branch_id = (select branch_id from users where id = ?) and status = true;");

			ps_comment = connection.prepareStatement(sql_comment.toString());
			ps_comment.setInt(1, id);

			ResultSet rs = ps_post.executeQuery();
			rs.next();
			count[0] = rs.getInt("count");

			rs = ps_comment.executeQuery();
			rs.next();
			count[1] = rs.getInt("count");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps_post);
			close(ps_comment);
		}
		return count;
	}

	public void deleteNgWord(Connection connection, int id) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("delete from ngwords ");
			sql.append("where id = ?;");
			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, id);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
	}

	public void addNgWord(Connection connection, String word) {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append("insert into ngwords(word) ");
			sql.append("values(?);");
			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, word);

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}

	}

	public boolean isExistPost(Connection connection, int postId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) as count from posts where id = ?;");
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, postId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if (rs.getInt("count") == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isExistComment(Connection connection, int postId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select count(*) as count from comments where id = ?;");
		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql.toString());
			ps.setInt(1, postId);
			ResultSet rs = ps.executeQuery();
			rs.next();
			if (rs.getInt("count") == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

}
