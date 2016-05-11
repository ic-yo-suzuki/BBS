package bbs.beans;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id, branchId, departmentId;
	private String loginId, name, password, branchName, departmentName, elapsedTimeText;
	private boolean status;
	private long elapsedTime;
	private Date lastLoginDate;


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

	public String getBranchName(){
		return branchName;
	}

	public void setBranchName(String branchName){
		this.branchName = branchName;
	}

	public int getDepartmentId(){
		return departmentId;
	}

	public void setDepartmentId(int departmentId){
		this.departmentId = departmentId;
	}

	public String getDepartmentName(){
		return departmentName;
	}

	public void setDepartmentName(String departmentName){
		this.departmentName = departmentName;
	}

	public String getLoginId(){
		return loginId;
	}
	public void setLoginId(String loginId){
		this.loginId = loginId;
	}

	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}

	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password = password;
	}

	public boolean getStatus(){
		return status;
	}

	public void setStatus(boolean status){
		this.status = status;
	}

	public Date getLastLoginDate(){
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate){
		this.lastLoginDate = lastLoginDate;
	}
	public long getElapsedTime(){
		return elapsedTime;
	}
	public void setElapsedTime(long elapsedTime){
		this.elapsedTime = elapsedTime;
	}

	public String getElapsedTimeText(){
		return elapsedTimeText;
	}

	public void setElapsedTimeText(long elapsedTime){

		if(elapsedTime / 60 < 1){
			this.elapsedTimeText = String.valueOf(elapsedTime) + "秒前";

		}
		if(elapsedTime / 60 >= 1){
			this.elapsedTimeText = String.valueOf(elapsedTime / 60) + "分前";

		}
		if(elapsedTime / 3600 >= 1){
			this.elapsedTimeText = String.valueOf(elapsedTime / 3600) + "時間前";

		}
		if(elapsedTime / 86400 >= 1){
			this.elapsedTimeText = String.valueOf(elapsedTime / 86400) + "日前";

		}
		if(elapsedTime / 604800 >= 1){
			this.elapsedTimeText = String.valueOf(elapsedTime / 604800) + "週間前";

		}
		if(elapsedTime / 2592000 >= 1){
			this.elapsedTimeText = String.valueOf(elapsedTime / 2592000) + "ヶ月前";

		}
		if(elapsedTime / 31536000 >= 1){
			this.elapsedTimeText = String.valueOf(elapsedTime / 31536000) + "年前";

		}
	}


}
