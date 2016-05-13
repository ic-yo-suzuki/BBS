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
		Connection connection = getConnection();
		try{

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
		Connection connection = getConnection();
		List<Message> ret = null;
		try{

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
		Connection connection = getConnection();
		List<String> ret = null;
		try{

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
		Connection connection = getConnection();
		List<Message> ret = null;
		try{

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
		Connection connection = getConnection();
		List<Message> ret = null;
		try{

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
		Connection connection = getConnection();
		List<Message> ret = null;
		try{

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
		Connection connection = getConnection();

		try{

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
		Connection connection = getConnection();
		try{

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
		Connection connection = getConnection();
		List<Comment> ret = null;
		try{

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
		Connection connection = getConnection();
		List<NgWord> ngWord = null;
		try{

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
		Connection connection = getConnection();

		try{

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
		Connection connection = getConnection();
		int[] count = new int[2];
		try{


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
		Connection connection = getConnection();
		int[] count = new int[2];
		try{

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
		Connection connection = getConnection();

		try{
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
		Connection connection = getConnection();
		try{

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


	public boolean isExistPost(int postId) {
		Connection connection = getConnection();
		boolean flg = false;
		try{

			flg = new MessageDao().isExistPost(connection, postId);
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

	public boolean isExistComment(int postId) {
		Connection connection = getConnection();
		boolean flg = false;
		try{

			flg = new MessageDao().isExistComment(connection, postId);
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
}

