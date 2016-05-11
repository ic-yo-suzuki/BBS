package bbs.beans;
import java.io.Serializable;
public class NgWord implements Serializable {
	private static final long serialVersionUID = 1L;

	int id;
	String word;

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id = id;
	}

	public String getWord(){
		return word;
	}

	public void setWord(String word){
		this.word = word;
	}

}
