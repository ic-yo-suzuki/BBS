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
		return userList;
	}

	public void changeStatis(int id){
		Connection connection = null;
		boolean status = false;
		try{
			connection = getConnection();
			UserDao userDao = new UserDao();
			status = userDao.getStatus(connection, id);
			userDao.changeStatus(connection, id, status);
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



	public void update(User user, boolean passwordModifyFlg) {
		Connection connection = null;
		try{
			connection = getConnection();

			if(passwordModifyFlg){
				String encPassword = CipherUtil.encrypt(user.getPassword());
				user.setPassword(encPassword);
			}
			UserDao userDao = new UserDao();
			userDao.update(connection, user, passwordModifyFlg);

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

	}

	public void deleteUser(int id){
		Connection connection = null;
		try{
			connection = getConnection();

			new UserDao().delete(connection, id);
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

	}
	public boolean isExistUser(int id){
		Connection connection = null;
		boolean flg = false;
		try{
			connection = getConnection();
			flg = new UserDao().isExistUser(connection, id);
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
		return flg;
	}

	public void updateLastLoginDate(int id){
		Connection connection = null;
		try{
			connection = getConnection();
			new UserDao().updateLastLoginDate(connection, id);
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

	}

	public long getElapsedTime(int id){
		Connection connection = null;
		long elapsedTime = 0;
		try{
			connection = getConnection();
			elapsedTime = new UserDao().getElapsedTime(connection, id);
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
		return elapsedTime;

	}

	public String getLoginId(int id){
		Connection connection = null;
		String loginId;
		try{
			connection = getConnection();
			loginId = new UserDao().getLoginId(connection, id);
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
		return loginId;
	}


}
