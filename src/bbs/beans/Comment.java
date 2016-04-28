package bbs.beans;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id, postId, userId, branchId, departmentId;
	private String text, name;
	private Date insertDate;

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}

	public int getBranchId(){
		return branchId;
	}
	public void setBranchId(int branchId){
		this.branchId = branchId;
	}

	public int getDepartmentId(){
		return departmentId;
	}
	public void setDepartmentId(int departmentId){
		this.departmentId = departmentId;
	}

	public Date getInsertDate(){
		return insertDate;
	}

	public void setInsertDate(Date insertDate){
		this.insertDate = insertDate;
	}

	public String getText(){
		return text;
	}
	public void setText(String text){
		this.text = text;
	}

	public int getUserId(){
		return userId;
	}
	public void setUserId(int userId){
		this.userId = userId;
	}

	public int getPostId(){
		return postId;
	}
	public void setPostId(int postId){
		this.postId = postId;
	}

	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}

}
