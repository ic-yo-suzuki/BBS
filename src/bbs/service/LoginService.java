package bbs.service;

import static bbs.utils.DBUtil.*;

import java.sql.Connection;

import bbs.beans.User;
import bbs.dao.UserDao;
import bbs.utils.CipherUtil;


public class LoginService {
	public User login(String loginId, String password) throws Exception{
		Connection connection = null;
		User user = null;
		try{
			connection = getConnection();
			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			user = userDao.getUser(connection, loginId, encPassword);
			commit(connection);

		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			connection.close();
		}
		return user;
	}
}
