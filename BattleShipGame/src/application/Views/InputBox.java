/**
 * This class takes 4 inputs from user.
 * 
 * @author arsalaan
 */

package application.Views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
		
		final ToggleGroup headOrTails = new ToggleGroup();

		RadioButton head = new RadioButton("Head");
		head.setToggleGroup(headOrTails);
		head.setSelected(true);

		RadioButton tail = new RadioButton("Tail");
		tail.setToggleGroup(headOrTails);
		grid.getChildren().addAll(head,tail);
		
		Button submit = new Button("Submit");
		
		submit.setOnAction(e -> {
			
				RadioButton rb = (RadioButton)headOrTails.getSelectedToggle(); 
			  
            if (rb != null) { 
            	result = rb.getText(); 
               
            }
		stage.close();
		});
		GridPane.setConstraints(head, 0, 1);
		GridPane.setConstraints(tail, 0, 2);
		GridPane.setConstraints(submit, 0, 3);
		grid.getChildren().add(submit);
		
		Scene scene = new Scene(grid);
		stage.setScene(scene);
		stage.showAndWait();
		
		return result;
		
		}
	

}
