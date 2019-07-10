/**
 * This class takes 4 inputs from user.
 * 
 * @author arsalaan
 */

package application.Views;


import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class InputBox {
	

		
		static String result;
		
		public static String display(String title) {
		Stage stage = new Stage();
		
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(550);
		stage.setMinHeight(300);
		
		final TextField x1 = new TextField();
		x1.setPromptText("Initial X co-ordinate ");
		x1.setPrefColumnCount(10);
		x1.getText();
		GridPane.setConstraints(x1, 0, 0);
		grid.getChildren().add(x1);
		
		final TextField y1 = new TextField();
		y1.setPromptText("Initial Y co-ordinate");
		GridPane.setConstraints(y1, 1, 0);
		grid.getChildren().add(y1);
		
		final TextField x2 = new TextField();
		x2.setPrefColumnCount(15);
		x2.setPromptText("Final X co-ordinate");
		GridPane.setConstraints(x2, 0, 1);
		grid.getChildren().add(x2);
	
		final TextField y2 = new TextField();
		y2.setPrefColumnCount(15);
		y2.setPromptText("Final Y co-ordinate");
		GridPane.setConstraints(y2, 1, 1);
		grid.getChildren().add(y2);
		
		Button submit = new Button("Submit");
		
		submit.setOnAction(e -> {
		result = x1.getText() + " " + y1.getText()+" " + x2.getText() + " " + y2.getText();	
		stage.close();
		});
		
		GridPane.setConstraints(submit, 0, 2);
		grid.getChildren().add(submit);
		
		Scene scene = new Scene(grid);
		stage.setScene(scene);
		stage.showAndWait();
		
		return result;
		
		}
	

}
