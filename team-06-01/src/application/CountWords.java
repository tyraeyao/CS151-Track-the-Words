package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

public class CountWords{
	static List<String> unsortedList = new ArrayList<String>();
	static Words words = new Words();
	static UI ui = new UI();
	public static void storeWords(String[] text) {
		if(text == null)
			return;
		// Gets the text from the dragged file and removes all special characters and lowercase it
		for(int i = 0; i <= text.length-1; i++) {
			unsortedList.add(text[i].replaceAll("[^a-zA-Z0-9\\s]", "").toLowerCase());
			//Words.numberWords.add(i, i++);
		}
		sameWord();
	}
	
	public static void sameWord() { 
		Set<String> unique = new HashSet<String>(unsortedList);
		
		for(String key : unique) {
			System.out.println(key + ": " + Collections.frequency(unsortedList, key));
			words.setNum(Collections.frequency(unsortedList, key));
			words.addToNumberList();
			// If there is a common word, it won't be sent to the new list
			words.setEnglish(key);
			words.addToEnglishList();
			
		}
	}
	
	public static void clearList() {
		// Clears all lists when we want to import a new file
		unsortedList.clear();
		words.clearLists();
	}
	
	public static void storeTranslatedWord() throws Exception {
		String temp = "";
		words.wordsTranslatedList.clear();
		// Gets the english words and translates and store in new list
		for(int i = 0; i <= words.wordsEnglishList.size()-1; i++) {
			// cbValue from the choice box from UI class
			temp = Translator.translate("en", UI.cbValue, words.wordsEnglishList.get(i));
			words.setTranslated(temp);
			words.addToTranslatedList();
		}
	}
	
//	public static void printTranslatedWord() {
//		//System.out.println(newLanguageList);
//		for(int i = 0; i < words.wordsEnglishList.size(); i++) {
//			//System.out.println(newList.get(i) + " = " + newLanguageList.get(i));
//			System.out.println(words.wordsTranslatedList.get(i));
//		}
//	}
	
}
