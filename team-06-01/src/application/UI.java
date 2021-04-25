package application;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UI extends Application{
	
	private Scene scene1;
	private Stage window;
	static Words words = new Words();
	static String cbValue = "en";
	
	public void start(Stage primaryStage) throws Exception{
		window = primaryStage;
		scene1 = new Scene(layoutOne(), 800, 600); // Home page scene
		primaryStage.setResizable(false);
		primaryStage.setTitle("Track the Words"); 
		primaryStage.setScene(scene1);
		primaryStage.show();
	}
	
	public VBox dragTarget() {
		// Drag and drop listener that takes in a file
		// Once a file is stored we check if it is a txt, if not we do not accept
		// If it is accepted we store the words in our list
		Label label = new Label("Drag txt file Here");
		Text dropped = new Text("");
		Label fileText = new Label("");
		
		VBox dragTarget = new VBox();
		dragTarget.setStyle("-fx-border-color: black; -fx-font-size: 16; -fx-background-color: white");
		dragTarget.setMinSize(518, 5);
		dragTarget.getChildren().addAll(label, dropped, fileText);
		dragTarget.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				if (event.getGestureSource() != dragTarget && event.getDragboard().hasFiles()) {
					/* allow for both copying and moving, whatever user chooses */
					event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				}
				event.consume();
			}
		});

		dragTarget.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			// Checking what the user dragged into and printing if the file is a txt file
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				// Split the strings
				String[] splitStr = null;
				if (db.hasFiles()) {
					String fileName = db.getFiles().toString().replaceAll("(^\\[|\\]$)", "");
					if(fileName.substring(fileName.length()-3).matches("txt")) // If the file is not a txt file it will not work. It has to have the .txt extension at the end
					{
						File file = new File(fileName);
						dropped.setText(db.getFiles().toString());
						success = true;
						if(success = true) {
							CountWords.clearList();
							words.clearLists();
							try {
								FileReader file1 = new FileReader(fileName);
								@SuppressWarnings("resource")
								BufferedReader reader = new BufferedReader(file1);
								String text1 = "";
								String line = reader.readLine();
								while(line != null) {
									text1 += line;
									splitStr = text1.split("\\s+");
									line = reader.readLine();
								}
								CountWords.storeWords(splitStr);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								System.out.println("Not Txt file");
							}
						}
					}
					else {
						System.out.println(fileName.substring(fileName.length()-3));
						System.out.println("Not a txt file");
						dropped.setText("Not a txt file");
						fileText.setText("");
					}
						
				}
				/*
				 * let the source know whether the string was successfully transferred and used
				 */
				event.setDropCompleted(success);

				event.consume();
			}
		});
		return dragTarget;
	}
	
	public BorderPane layoutTwo() {
		// Creates Scene 2 once the user presses start button in Scene 1
		// User chooses words and when to translate and drag file in
		// TableView is shown once the user has translated the word
		ChoiceBox<String> choiceBox = new ChoiceBox<>();
		GridPane leftUI = new GridPane();
		leftUI.add(dragTarget(), 0, 0, 1, 1);
		ProgressBar progressBar = new ProgressBar();
		Button buttonLy2 = new Button("Translate");
		buttonLy2.setStyle("-fx-border-color: black; -fx-font-size: 16;");
			buttonLy2.setOnAction(new EventHandler<ActionEvent>() {
				TableView<Words> tableView = new TableView();
			    @Override public void handle(ActionEvent e) {
			    	try {
			    		if(choiceBox.getValue() == "Choose A Language") {
			    			popupErrorMsg();
			    			return;
			    		}
						CountWords.storeTranslatedWord();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			    	//CountWords.printTranslatedWord();
			        
			    	
			    	// Creating TableView with two columns
			        TableColumn<Words, Words> column1 = new TableColumn<>("English");
			        column1.setCellValueFactory(new PropertyValueFactory<>("english"));
			        TableColumn<Words, Words> column2 = new TableColumn<>("Translated");
			        column2.setCellValueFactory(new PropertyValueFactory<>("translated"));
			        
			        tableView.getColumns().clear();
			    	
			        tableView.getColumns().add(column1);
			        tableView.getColumns().add(column2);
			        
			        // Fill the tableView with our lists
			        for(int i = 0; i <= words.getEnglishWords().size()-1; i++) {
			        	tableView.getItems().add(new Words(words.getEnglishWords().get(i), words.getTransWords().get(i)));
			        }
			        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	
					//ObservableList<String> obvList = FXCollections.observableArrayList(words.getTransWords());
					//ListView<String> listView = new ListView<String>(obvList);
					//listView.setMaxSize(200, 160);
					//Creating layout
					VBox vBoxLayout = new VBox(10);
					vBoxLayout.setStyle("-fx-border-color: black; -fx-font-size: 16;");
					vBoxLayout.getChildren().add(tableView);
					vBoxLayout.setStyle("-fx-background-color: BEIGE");
					leftUI.add(vBoxLayout, 0, 1, 1, 1);
			    }
			});
		// ChoiceBox 1 for Language choosing
		String[] languageArray = { "Afrikaans", "Albanian", "Amharic", "Arabic", "Armenian", "Azerbaijani", "Basque",
				"Belarusian", "Bengali", "Bosnian", "Bulgarian", "Catalan", "Cebuano", "Chinese (Simplified)",
				"Chinese (Traditional)", "Corsican", "Croatian", "Czech", "Danish", "Dutch", "English", "Esperanto",
				"Estonian", "Finnish", "French", "Frisian", "Galician", "Georgian", "German", "Greek", "Gujarati",
				"Haitian Creole", "Hausa", "Hawaiian", "Hebrew", "Hindi", "Hmong", "Hungarian", "Icelandic", "Igbo",
				"Indonesian", "Irish", "Italian", "Japanese", "Javanese", "Kannada", "Kazakh", "Khmer", "Kinyarwanda",
				"Korean", "Kurdish", "Kyrgyz", "Lao", "Latin", "Latvian", "Lithuanian", "Luxembourgish", "Macedonian",
				"Malagasy", "Malay", "Malayalam", "Maltese", "Maori", "Marathi", "Mongolian", "Myanmar (Burmese)",
				"Nepali", "Norwegian", "Nyanja (Chichewa)", "Odia (Oriya)", "Pashto", "Persian", "Polish",
				"Portuguese (Portugal, Brazil)", "Punjabi", "Romanian", "Russian", "Samoan", "Scots Gaelic", "Serbian",
				"Sesotho", "Shona", "Sindhi", "Sinhala (Sinhalese)", "Slovak", "Slovenian", "Somali", "Spanish",
				"Sundanese", "Swahili", "Swedish", "Tagalog (Filipino)", "Tajik", "Tamil", "Tatar", "Telugu", "Thai",
				"Turkish", "Turkmen", "Ukrainian", "Urdu", "Uyghur", "Uzbek", "Vietnamese", "Welsh", "Xhosa", "Yiddish",
				"Yoruba", "Zulu" };
		String[] choiceLanguage = { "af", "sq", "am", "ar", "hy", "az", "eu", "be", "bn", "bs", "bg", "ca", "ceb",
				"zh-CN", "zh-TW", "co", "hr", "cs", "da", "nl", "en", "eo", "et", "fi", "fr", "fy", "gl", "ka", "de",
				"el", "gu", "ht", "ha", "haw", "iw", "hi", "hmn", "hu", "is", "ig", "id", "ga", "it", "ja", "jv", "kn",
				"kk", "km", "rw", "ko", "ku", "ky", "lo", "la", "lv", "lt", "lb", "mk", "mg", "ms", "ml", "mt", "mi",
				"mr", "mn", "my", "ne", "no", "ny", "or", "ps", "fa", "pl", "pt", "pa", "ro", "ru", "sm", "gd", "sr",
				"st", "sn", "sd", "si", "sk", "sl", "so", "es", "su", "sw", "sv", "tl", "tg", "ta", "tt", "te", "th",
				"tr", "tk", "uk", "ur", "ug", "uz", "vi", "cy", "xh", "yi", "yo", "zu" };
		// Adding in Languages in drop down
		for(int i = 0; i <= languageArray.length-1; i++)
		{
			choiceBox.getItems().add(languageArray[i]);
		}
		choiceBox.setValue("Choose A Language");
		choiceBox.setStyle("-fx-border-color: black; -fx-font-size: 16; -fx-background-insets: 0 0 0 0;");
		
		BorderPane layout2 = new BorderPane();
		layout2.setPadding(new Insets(5));
		
		GridPane rightUI = new GridPane();
		rightUI.add(choiceBox, 0, 0, 1, 1);
		choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			  
			// A listener that tracks the users click and gets the Index of the language
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
            	// Using the index of language and send to CountWords
                System.out.println(languageArray[new_value.intValue()] + " selected");
                cbValue = choiceLanguage[new_value.intValue()];
            }
        });
		rightUI.add(buttonLy2, 0, 2, 1, 1);
		rightUI.setAlignment(Pos.TOP_RIGHT);
		
		layout2.setRight(rightUI);
		layout2.setLeft(leftUI);
		System.out.println(choiceBox.getValue());
		return layout2;
	}
	
	public GridPane layoutOne() {
		// Creates the first Scene which is the home page
		// It consists of a start button and welcome text
		Text text = new Text("Welcome to Track the Words");
		Button button1 = new Button("Start");
		text.setFont(Font.font(null, FontWeight.BOLD, 16));
		GridPane layout1 = new GridPane();
		layout1.setAlignment(Pos.CENTER);
		layout1.setPadding(new Insets(30,30,30,30));
		layout1.setVgap(10);
		layout1.setHgap(10);
		//Align button1 to center under text
		layout1.getChildren().addAll(text,button1);
		GridPane.setConstraints(button1, 0, 1);
		GridPane.setHalignment(button1, HPos.CENTER);
		GridPane.setValignment(button1, VPos.CENTER);
		Scene scene2 = new Scene(layoutTwo(), 800, 600);
		button1.setOnAction(e -> window.setScene(scene2));
		return layout1;
	}
	
    public void popupErrorMsg() {
        final Stage myDialog = new Stage();
        myDialog.initModality(Modality.APPLICATION_MODAL);
        Label popUpText = new Label();
        popUpText.setText("Choose a Language");
        Button okButton = new Button("Ok");
        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                myDialog.close();
            }
        });
        VBox popUp = new VBox();
        popUp.setAlignment(Pos.CENTER);
        popUp.getChildren().addAll(popUpText,okButton);
        Scene myDialogScene = new Scene(popUp,100,100);
        
        myDialog.setScene(myDialogScene);
        myDialog.show();
    }
	
}
