package bbs.beans;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
	private static final long serialVersionUID = 1L;

	private int id;
	private String title, text, category;
	private Date insertDate;

	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
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
