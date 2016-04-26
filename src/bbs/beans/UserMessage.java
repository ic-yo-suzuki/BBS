package bbs.beans;

import java.io.Serializable;
import java.util.Date;


public class UserMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	private String category, name, text, title;
	private int userId;
	private Date insertDate;

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
}
