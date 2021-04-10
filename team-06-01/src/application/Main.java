package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/* Before running the code, make sure you have JavaFX and PDFBox.
 * PDFBox is a library to open up pdfs. */
public class Main extends Application {

	Stage window;
	Scene scene1, scene2;

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		Text text = new Text("Welcome to Track the Words");
		Label label = new Label("Drag PDF Here");
		Label dropped = new Label("");
		Label pdfText = new Label("");
		VBox dragTarget = new VBox();
		dragTarget.getChildren().addAll(label, dropped, pdfText);
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
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				boolean success = false;
				if (db.hasFiles()) {
					dropped.setText(db.getFiles().toString());
					success = true;
					if (success = true) {

						File file = new File(db.getFiles().toString().replaceAll("\\[", "").replaceAll("\\]", ""));
						PDDocument document;
						try {
							document = PDDocument.load(file);
							PDFTextStripper pdfTextStripper = new PDFTextStripper();
							pdfTextStripper.setStartPage(1);
							pdfTextStripper.setEndPage(5);
							String text = pdfTextStripper.getText(document);
							System.out.println(text);
							pdfText.setText(text);
							document.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				/*
				 * let the source know whether the string was successfully transferred and used
				 */
				event.setDropCompleted(success);

				event.consume();
			}
		});

		// Layout 2
		StackPane layout2 = new StackPane();
		layout2.getChildren().addAll(dragTarget);
		Scene scene2 = new Scene(layout2, 800, 600);
		
		// Button 1
		Button button1 = new Button("Start");
		button1.setOnAction(e -> window.setScene(scene2));
		
		// Layout 1
		GridPane layout1 = new GridPane();
		text.setFont(Font.font(null, FontWeight.BOLD, 16));
		layout1.setAlignment(Pos.CENTER);
		layout1.setPadding(new Insets(30,30,30,30));
		layout1.setVgap(10);
		layout1.setHgap(10);
		
		GridPane.setConstraints(button1, 0, 1);
		GridPane.setHalignment(button1, HPos.CENTER);
		GridPane.setValignment(button1, VPos.CENTER);
		
		layout1.getChildren().addAll(text,button1);
		
		scene1 = new Scene(layout1, 800, 600);
		
		
		primaryStage.setTitle("Track the Words"); 
		primaryStage.setScene(scene1);
		primaryStage.show();
		
	}

	public static void main(String[] args) throws IOException {
		launch(args); // Set up program as javaFX application

	}

}
