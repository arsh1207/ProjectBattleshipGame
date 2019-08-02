package application.Views;

import java.util.Observable;
import java.util.Observer;

import application.Controllers.GridUser;
import application.Models.SaveClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Class to enter the details for the new as well as the existing users
 * @author Sagar Bhatia
 *
 */
public class UserDetailsWindow implements Observer{
	
	String userName;
	boolean verifyUserName;
	String password;
	String userOptions;
	GridUser ob;
	SaveClass saveClass;
	
	public UserDetailsWindow(GridUser ob, SaveClass saveClass){
		this.ob = ob;
		this.saveClass = saveClass;
	}
	
	public boolean getVerifyUserName() {
		return this.verifyUserName;
	}

	public void setVerifyUserName(boolean verifyUserName) {
		this.verifyUserName = verifyUserName;
	}
	
	/**
	 * method to add the details for the new user
	 */
	public void newUser() {
		
		Stage stage = new Stage();
		stage.setTitle("New User");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 300, 275);
		stage.setScene(scene);
		Text scenetitle = new Text("Welcome to the Battleship game");
		scenetitle.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label username = new Label("User Name:");
		grid.add(username, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		
		Button btn = new Button("Star new game");
		btn.setOnAction(e -> {
			try {
				
				this.userName = userTextField.getText();
				this.password = pwBox.getText();
				System.out.println("User name is: "+userName);
				System.out.println("User psswd is: "+password);
				ob.checkUserName(saveClass, userName);
				//if(this.getVerifyUserName())
					//AlertBox.displayError("Username Error", "Name Already Taken");
				if(!this.getVerifyUserName()) {
					AlertBox.displayResult("Success", "Have a good game.");
					stage.close();
				}
				else {
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Username Error");
					alert.setHeaderText("Username taken. Please select a different username.");
					alert.showAndWait();
					
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		stage.showAndWait();
		
	}
	
	/**
	 * method to add the details for the new user
	 */
	public String existingUser() {
		
		Stage stage = new Stage();
		stage.setTitle("Existing User");
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Scene scene = new Scene(grid, 450, 350);
		stage.setScene(scene);
		Text scenetitle = new Text("Welcome Back to the Battleship game");
		scenetitle.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label username = new Label("User Name:");
		grid.add(username, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);
		
		Button btn1 = new Button("Start new game");
		btn1.setOnAction(e -> {
			try {
				
				this.userName = userTextField.getText();
				this.password = pwBox.getText();
				System.out.println("User name is: "+userName);
				System.out.println("User psswd is: "+password);
				ob.checkUserName(saveClass, userName);
				//if(this.getVerifyUserName())
					//AlertBox.displayError("Username Error", "Name Already Taken");
				if(this.getVerifyUserName()) {
					AlertBox.displayResult("Success", "Have a good game.");
					stage.close();
					this.userOptions = "newgame";
				}
				else {
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Username Error");
					alert.setHeaderText("No such username is found.");
					alert.showAndWait();
					
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		Button btn2 = new Button("Load existing game");
		btn2.setOnAction(e -> {
			try {
				
				this.userName = userTextField.getText();
				this.password = pwBox.getText();
				System.out.println("User name is: "+userName);
				System.out.println("User psswd is: "+password);
				if(this.getVerifyUserName()) {
					AlertBox.displayResult("Success", "Have a good game.");
					stage.close();
					this.userOptions = "loadgame";
				}
				else {
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Username Error");
					alert.setHeaderText("No such username is found.");
					alert.showAndWait();
					
				}

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		HBox hbBtn = new HBox(20);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().addAll(btn1, btn2);
		grid.add(hbBtn, 1, 4);
		stage.showAndWait();
		return userOptions;
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if(o instanceof SaveClass) {
			setVerifyUserName((boolean)arg);
		}
		
	}

}
