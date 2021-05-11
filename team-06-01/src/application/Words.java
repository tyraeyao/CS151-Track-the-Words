package application;

import java.util.ArrayList;
import java.util.List;

public class Words {
	private String english;
	private String translated;
	private String number;
	static List<String> wordsEnglishList = new ArrayList<String>();
	static List<String> wordsTranslatedList = new ArrayList<String>();
	static List<String> numberWords = new ArrayList<String>();
	
	public Words(String english, String translated, String string) {
		this.english = english;
		this.translated = translated;
		this.number = string;
	}
	
	public Words() {
		// TODO Auto-generated constructor stub
	}
	
	public void addToNumberList() {
		numberWords.add(number);
	}
	
	public void setNum(int integer) {
		String s = String.valueOf(integer);
		this.number = s;
	}
	
	public List<String> getNumberList(){
		return numberWords;
	}
	
	public List<String> getTransWords(){
		return wordsTranslatedList;
	}
	
	public List<String> getEnglishWords(){
		return wordsEnglishList;
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getEnglish() {
		return english;
	}
	
	public void setEnglish(String english) {
		this.english = english;
	}
	
	public String getTranslated() {
		return translated;
	}
	
	public void setTranslated(String translated) {
		this.translated = translated;
	}
	
	public void addToEnglishList() {
		wordsEnglishList.add(english);
	}
	
	public void addToTranslatedList() {
		wordsTranslatedList.add(translated);
	}
	
	public void clearLists() {
		wordsEnglishList.clear();
		wordsTranslatedList.clear();
		numberWords.clear();
	}
}
