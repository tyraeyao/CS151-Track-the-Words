package application;

import java.util.ArrayList;
import java.util.List;

public class Words {
	private String english;
	private String translated;
	static List<String> wordsEnglishList = new ArrayList<String>();
	static List<String> wordsTranslatedList = new ArrayList<String>();
	
	public Words(String english, String translated) {
		this.english = english;
		this.translated = translated;
	}
	
	public Words() {
		
	}
	
	public List<String> getTransWords(){
		return wordsTranslatedList;
	}
	
	public List<String> getEnglishWords(){
		return wordsEnglishList;
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
	}
}
