package application;



import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {
	
	static Boolean result;
	
	public static Boolean display(String title, String msg ) {
	Stage stage = new Stage();
	
	stage.initModality(Modality.APPLICATION_MODAL);
	stage.setTitle(title);
	stage.setMinWidth(550);
	
	Label label1 = new Label(msg);
	
	Button btn1 = new Button("Yes");
	Button btn2 = new Button("No");
	
	btn1.setOnAction(e -> {
		result = true;
		stage.close();
	});
	
	btn2.setOnAction(e -> {
		result = false;
		stage.close();
	});
	
	VBox v_box = new VBox();
	v_box.getChildren().addAll(label1, btn1, btn2 );
	v_box.setAlignment(Pos.CENTER);
	
	Scene scene = new Scene(v_box);
	stage.setScene(scene);
	stage.showAndWait();
	
	return result;
	
	}
}
