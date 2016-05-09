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


public class MessageDao {

	public List<String> getCategories(Connection connection){
		PreparedStatement ps = null;
		List<String> categories = new ArrayList<String>();
		categories.add("カテゴリを選択してください");
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct category from posts order by category;");
			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				categories.add(rs.getString("category"));
			}
			if(ps != null){
				ps.close();
			}
		}catch(Exception e){

		}

		return categories;
	}


	public void insert(Connection connection, Message message) throws Exception{
		PreparedStatement ps = null;
		try{
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
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
	}

	public void delete(Connection connection, int id, String operation) throws Exception{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

			sql.append(operation);
			sql.append("where id = ?;");
			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, id);

			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
	}

	public void insertComment(Connection connection, Comment comment) throws SQLException{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("insert into comments(user_id, post_id, text, insert_date) ");
			sql.append("values(?, ?, ?, CURRENT_TIMESTAMP);");
			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, comment.getUserId());
			ps.setInt(2, comment.getPostId());
			ps.setString(3, comment.getText());
			ps.toString();
			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
	}


	public void delete(Connection connection, int id, String operation, String query) {
		PreparedStatement ps_post = null;
		PreparedStatement ps_comment = null;

		try{
			StringBuilder sql_post = new StringBuilder();
			StringBuilder sql_comment = new StringBuilder();

			sql_post.append(operation);
			sql_post.append("where id = ?;");
			sql_comment.append(query);
			sql_comment.append("where post_id = ?;");
			ps_post = connection.prepareStatement(sql_post.toString());
			ps_comment = connection.prepareStatement(sql_comment.toString());

			ps_post.setInt(1, id);
			ps_comment.setInt(1, id);

			ps_post.executeUpdate();
			ps_comment.executeUpdate();

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps_post);
			close(ps_comment);
		}
	}

	public List<String> getNgWord(Connection connection){
		PreparedStatement ps = null;
		List<String> ngWord = new ArrayList<String>();
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("select word from ngwords;");
			ps = connection.prepareStatement(sql.toString());
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				ngWord.add(rs.getString("word"));
			}
			if(ps != null){
				ps.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return ngWord;
	}

}
