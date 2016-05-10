package bbs.beans;

import java.io.Serializable;
import java.util.Date;


public class Message implements Serializable {
	private static final long serialVersionUID = 1L;

	private String category, name, text, title;
	private String elapsedTimeText;
	private long elapsedTime;
	private int id, branchId, departmentId, userId;
	private Date insertDate;

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}

	public int getUserId(){
		return userId;
	}
	public void setUserId(int userId){
		this.userId = userId;
	}

	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}

	public String getTitle(){
		return title;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getText(){
		return text;
	}

	public void setText(String text){
		this.text = text;
	}


	public String getCategory(){
		return category;
	}
	public void setCategory(String category){
		this.category = category;
	}


	public Date getInsertDate(){
		return insertDate;
	}
	public void setInsertDate(Date insertDate){
		this.insertDate = insertDate;
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

	public long getElapsedTime(){
		return elapsedTime;
	}
	public void setElapsedTime(long elapsedTime){
		this.elapsedTime = elapsedTime;
	}
	public void setElapsedTimeText(long elapsedTime){

		if(elapsedTime / 60 < 1){
			this.elapsedTimeText = String.valueOf(elapsedTime) + "秒前";
		}else if(elapsedTime / 60 >= 1){
			this.elapsedTimeText = String.valueOf(elapsedTime) + "分前";
		}else if(elapsedTime / 3600 >= 1){
			this.elapsedTimeText = String.valueOf(elapsedTime) + "時間前";
		}else if(elapsedTime / 86400 >= 1){
			this.elapsedTimeText = String.valueOf(elapsedTime) + "日前";
		}else if(elapsedTime / 604800 >= 1){
			this.elapsedTimeText = String.valueOf(elapsedTime) + "週間前";
		}
	}
}