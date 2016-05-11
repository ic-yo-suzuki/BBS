package bbs.service;

import static bbs.utils.DBUtil.*;

import java.sql.Connection;

import bbs.dao.UserDao;
import bbs.utils.CipherUtil;


public class LoginService {
	public int login(String loginId, String password) throws Exception{
		Connection connection = null;
		int id = 0;
		try{
			connection = getConnection();
			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			id = userDao.getUser(connection, loginId, encPassword);
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
		return id;
	}
}
