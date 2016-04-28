package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import bbs.beans.User;
import bbs.dao.UserDao;
import bbs.utils.CipherUtil;

public class UserService {
	public void register(User user) {
		Connection connection = null;
		try{
			connection = getConnection();
			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			UserDao userDao = new UserDao();
			userDao.insert(connection, user);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		}
		finally{
			close(connection);
		}
	}

	public User getUser(int userId) {
		Connection connection = null;
		User user = null;
		try{
			connection = getConnection();
			UserDao userDao = new UserDao();

			user = userDao.getUser(connection, userId);
			commit(connection);

		}catch(Error e){
			rollback(connection);
		}finally{
			close(connection);
		}
		return user;
	}

	public List<User> getUserList() {
		Connection connection = null;
		List<User> userList = null;
		try{
			connection = getConnection();
			UserDao userDao = new UserDao();
			userList = userDao.getUserList(connection);
			commit(connection);
		}catch(Error e){
			rollback(connection);
		}finally{
			close(connection);
		}
		return userList;
	}


/*
	public void update(User user) {
		Connection connection = null;
		try{
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);
			System.out.println("パスワード暗号化完了");

			UserDao userDao = new UserDao();
			userDao.update(connection, user);

			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}

	}*/
}
