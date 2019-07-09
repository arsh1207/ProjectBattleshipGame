package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	
	public static void displayError(String title, String msg ) {
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(750);
		stage.setMinHeight(500);
		
		Label label1 = new Label(msg);
		
		Button btn1 = new Button("Ok");
		
		btn1.setOnAction(e -> {
			stage.close();
		});
		
		
		VBox v_box = new VBox();
		v_box.getChildren().addAll(label1, btn1);
		v_box.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(v_box);
		stage.setScene(scene);
		stage.showAndWait();
		
	}
	
	
	public static void displayResult(String title, String msg ) {
		Stage stage = new Stage();
		
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle(title);
		stage.setMinWidth(750);
		stage.setMinHeight(500);
		
		Label label1 = new Label(msg);
		
		Button btn1 = new Button("Restart Game");
		Button btn2 = new Button("Quit Game");
		
		btn1.setOnAction(e -> {
			//put the reinitialize
		});
		
		btn2.setOnAction(e -> {
			stage.close();
		});
		
		VBox v_box = new VBox();
		v_box.getChildren().addAll(label1, btn1, btn2 );
		v_box.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(v_box);
		stage.setScene(scene);
		stage.showAndWait();
		
	}
	
}
