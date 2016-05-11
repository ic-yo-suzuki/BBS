package bbs.service;

import static bbs.utils.CloseableUtil.*;
import static bbs.utils.DBUtil.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import bbs.beans.Comment;
import bbs.beans.Message;
import bbs.beans.NgWord;
import bbs.dao.MessageDao;
import bbs.dao.UserMessageDao;


public class MessageService {
	public void register(Message message) throws Exception{
		Connection connection = null;
		try{
			connection = getConnection();
			MessageDao messageDao = new MessageDao();

			messageDao.insert(connection, message);
			commit(connection);
		}catch(RuntimeException e){
			e.printStackTrace();
			rollback(connection);
			throw e;
		}catch(Error e){
			e.printStackTrace();
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}

	public List<Message> getMessage() {
		Connection connection = null;
		List<Message> ret = null;
		try{
			connection = getConnection();
			UserMessageDao messageDao = new UserMessageDao();
			ret = messageDao.getUserMessages(connection);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			close(connection);
		}
		return ret;
	}

	public List<String> getCategories(){
		Connection connection = null;
		List<String> ret = null;
		try{
			connection = getConnection();
			MessageDao messageDao = new MessageDao();
			ret = messageDao.getCategories(connection);
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
	return ret;
	}

	public List<Message> getMessage(String category) {
		Connection connection = null;
		List<Message> ret = null;
		try{
			connection = getConnection();
			UserMessageDao messageDao = new UserMessageDao();
			ret = messageDao.getUserMessages(connection, category);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			close(connection);
		}
		return ret;
	}

	public List<Message> getMessage(String[] selectedDates) {
		Connection connection = null;
		List<Message> ret = null;
		try{
			connection = getConnection();
			UserMessageDao messageDao = new UserMessageDao();
			ret = messageDao.getUserMessages(connection, selectedDates);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			close(connection);
		}
		return ret;
	}

	public List<Message> getMessage(String category, String[] selectedDates) {
		Connection connection = null;
		List<Message> ret = null;
		try{
			connection = getConnection();
			UserMessageDao messageDao = new UserMessageDao();
			ret = messageDao.getUserMessages(connection, category, selectedDates);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			close(connection);
		}
		return ret;
	}

	public void delete(int id){
		Connection connection = null;

		try{
			connection = getConnection();
			new MessageDao().postDelete(connection, id);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			close(connection);
		}
	}
	public void insertComment(Comment comment) throws SQLException{
		Connection connection = null;
		try{
			connection = getConnection();
			MessageDao messageDao = new MessageDao();
			messageDao.insertComment(connection, comment);
			commit(connection);
		}catch(RuntimeException e){
			e.printStackTrace();
			rollback(connection);
			throw e;
		}catch(Error e){
			e.printStackTrace();
			rollback(connection);
			throw e;
		}finally{
			close(connection);
		}
	}

	public List<Comment> getComment() {
		Connection connection = null;
		List<Comment> ret = null;
		try{
			connection = getConnection();
			UserMessageDao messageDao = new UserMessageDao();
			ret = messageDao.getComments(connection);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			close(connection);
		}
		return ret;
	}

	public List<NgWord> getNgWord(){
		Connection connection = null;
		List<NgWord> ngWord = null;
		try{
			connection = getConnection();
			MessageDao messageDao = new MessageDao();
			ngWord = messageDao.getNgWord(connection);
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
	return ngWord;
	}

	public void deleteComment(int id) {
		Connection connection = null;

		try{
			connection = getConnection();
			new MessageDao().deleteComment(connection, id);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			close(connection);
		}
	}

	public int[] getUserPostCount(int id){
		Connection connection = null;
		int[] count = new int[2];
		try{
			connection = getConnection();

			count = new MessageDao().getUserPostCount(connection , id);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			close(connection);
		}
		return count;
	}

	public int[] getBranchPostCount(int id){
		Connection connection = null;
		int[] count = new int[2];
		try{
			connection = getConnection();
			count = new MessageDao().getBranchPostCount(connection , id);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			close(connection);
		}
		return count;
	}

	public void deleteNgWord(int id) {
		Connection connection = null;

		try{
			connection = getConnection();
			new MessageDao().deleteNgWord(connection, id);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			close(connection);
		}

	}

	public void addNgWord(String word) {
		Connection connection = null;
		try{
			connection = getConnection();
			new MessageDao().addNgWord(connection , word);
			commit(connection);
		}catch(RuntimeException e){
			rollback(connection);
			throw e;
		}catch(Error e){
			rollback(connection);
			throw e;
		} catch (Exception e) {

		}finally{
			close(connection);
		}

	}


}
