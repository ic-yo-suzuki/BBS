package bbs.beans;

import java.io.Serializable;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id, branchId, departmentId;
	private String loginId, name, password;
	private boolean status;
//	private Date insertDate, updateDate;

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}

	public int getBranch_Id(){
		return branchId;
	}
	public void setBranch_Id(int branchId){
		this.branchId = branchId;
	}

	public int getDepartmentId(){
		return departmentId;
	}
	public void setDepartmentId(int departmentId){
		this.departmentId = departmentId;
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
		this.status = !status;
	}


//	public Date getInsertDate(){
//		return insertDate;
//	}
//
//	public void setInsertDate(Date insertDate){
//		this.insertDate = insertDate;
//	}
//
//	public Date getUpdateDate(){
//		return updateDate;
//	}
//
//	public void setUpdateDate(Date updateDate){
//		this.updateDate = updateDate;
//	}

}
