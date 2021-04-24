package application;

import javafx.application.Application;

/* Originally, we were going to use PDFBox to read PDFs but we are unsure if we allowed to use PDF.
 * We are now only using txt files until we are sure that we can use other libraries.
 * PDFBox is a library to open up pdfs. */

public class Main{
	
	public static void main(String[] args) throws Exception {
		Application.launch(UI.class, args); // Set up program as javaFX application
	}

}
