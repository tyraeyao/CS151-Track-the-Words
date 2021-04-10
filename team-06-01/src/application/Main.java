package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class Main extends Application{
	
    @Override
    public void start(Stage primaryStage){
        Label label = new Label("Insert File");
        Label dropped = new Label("");
        Label pdfText = new Label("");
        VBox dragTarget = new VBox();
        dragTarget.getChildren().addAll(label,dropped,pdfText);
        dragTarget.setOnDragOver(new EventHandler<DragEvent>() {

            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != dragTarget
                        && event.getDragboard().hasFiles()) {
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
                    if(success = true) {
               
                        File file = new File(db.getFiles().toString().replaceAll("\\[", "").replaceAll("\\]", ""));
                        PDDocument document;
						try {
							document = PDDocument.load(file);
	                        PDFTextStripper pdfTextStripper = new PDFTextStripper();
	                        pdfTextStripper.setStartPage(1);
	                        pdfTextStripper.setEndPage(5);
	                        String text  = pdfTextStripper.getText(document);
	                        System.out.println(text);
	                        pdfText.setText(text);
	                        document.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(dragTarget);

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Track the Words");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

	public static void main(String[] args) throws IOException{
		launch(args); // Set up program as javaFX application
		
	}
	
}

