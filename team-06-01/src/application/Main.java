package application;

import javafx.application.Application;

/* Originally, we were going to use PDFBox to read PDFs but we are unsure if we allowed to use PDF.
 * We are now only using txt files until we are sure that we can use other libraries.
 * PDFBox is a library to open up pdfs. 
 * This program takes in a TXT file and translates it to the users wanted language
 * Although with non English letters the translation prints out what seems to be random characters. These characters cannot be understood because we cannot print languages
 * such as Chinese, Japanese, Hindi etc.
 * */

public class Main{
	
	public static void main(String[] args) throws Exception {
		Application.launch(UI.class, args); // Set up program as javaFX application
	}

}
