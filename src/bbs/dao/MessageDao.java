package bbs.dao;

import static bbs.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
			System.out.println(ps.toString());
			System.out.println(ps.executeUpdate());
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
	}

	public void delete(Connection connection, int id) throws Exception{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();

			sql.append("delete from posts where id = ?;");
			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, id);

			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
	}
}
