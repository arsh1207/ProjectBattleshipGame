package application;

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Battleship_Grid_Pane extends Application {

	Scene scene1;
	Scene scene2;
	GridPane g_pane, g_pane2;
	VBox v_box1, v_box2;

	// Stage stage;
	@Override
	public void start(Stage primarystage) {  
		 try {
			Stage  stage = primarystage;
	            stage.setTitle(" Battle Ship Game"); 
	            
	            launchStartupWindow(stage);
	  
	            SplitPane split_pane = new SplitPane();
	            g_pane=new GridPane();
	            g_pane2=new GridPane();
	            v_box1 = new VBox();
	            v_box2 = new VBox();
	            
	            Image image = new Image(new FileInputStream("bombs.png"));
	            ImageView imageView = new ImageView(image); 
	            imageView.setFitHeight(500); 
	            imageView.setFitWidth(800); 
	            imageView.setPreserveRatio(true);  
	            Battleship_Grid_Pane obj = new Battleship_Grid_Pane();
	            
	            MenuBar menuBar = obj.battleMenu(v_box1, stage);
	       
	            g_pane.setStyle("-fx-background-color: #35E7DB;");
	            g_pane.setVgap(10); 
	            g_pane.setHgap(10); 
	            	          
	            setUserRadarGrid(0, 0);
	           
	            setUserShipGrid(19, 0);
	           
	            
	            v_box1.getChildren().addAll(menuBar, g_pane);
	            
	            v_box2.setStyle("-fx-background-color: #000000;");
	            v_box2.getChildren().addAll(imageView, g_pane2);  
	            
	            split_pane.setDividerPositions(0.7);
	            
	            split_pane.getItems().add(v_box1);
	            split_pane.getItems().add(v_box2);
	            seeResult();
	            scene1 = new Scene(split_pane, 800, 700); 
	            //stage.setScene(scene1); 
	            stage.show(); 
	        } 
	  
	        catch (Exception e) { 
	  
	            System.out.println(e.getMessage()); 
	        } 
	   } 
	

	public void setUserRadarGrid(int rowButtonCount, int columnButtonCount) {

		double r = 7.5;

		Text t = new Text("Radar Grid");
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		g_pane.add(t, columnButtonCount, rowButtonCount);

		for(rowButtonCount = 10;rowButtonCount >= 1; rowButtonCount--) {
	    	int ch = 10;
			Text text1 = new Text(Integer.toString(ch - rowButtonCount)); 
			g_pane.add(text1, columnButtonCount, rowButtonCount);
		}
	    rowButtonCount = 1;
	    
	    //placing the buttons or holes on the grid
	    for(;rowButtonCount < 10; rowButtonCount += 1) {
	    	for(columnButtonCount = 1;columnButtonCount < 12; columnButtonCount += 1) { 
	        	Button btn = new Button();
	        	btn.setStyle("-fx-background-color: #000000; ");
	        	btn.setShape(new Circle(r));
	            btn.setMinSize(2*r, 2*r);
	            btn.setMaxSize(2*r, 2*r);
	            btn.setOnAction((ActionEvent event) -> {
	        		btn.setStyle("-fx-background-color: #FFFFFF; "); 
	        	});
	            g_pane.add(btn, columnButtonCount, rowButtonCount);
	    	}
	    }
	    
	    //placing the letters on the grid
	    for(columnButtonCount = 1;columnButtonCount < 12; columnButtonCount += 1) {
	    	char ch = (char) ('A'+columnButtonCount-1);        	  	
			Text text1 = new Text(Character.toString(ch)); 
			g_pane.add(text1, columnButtonCount, rowButtonCount);
		}
	}
	
	
public void setUserShipGrid(int rowButtonCount, int columnButtonCount) {
		
		StackPane s_pane = new StackPane();
		Rectangle rectangle = new Rectangle(0, 0, 20, 20);
		rectangle.setFill(Color.AQUA);
		s_pane.getChildren().add(rectangle);
		
	    double r = 7.5;
	    Text t = new Text("Ship Grid");
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		g_pane.add(t, columnButtonCount, 11);
		
		//placing the numbers for the grid
	    for(rowButtonCount = 21;rowButtonCount >= 12; rowButtonCount--) {
	    	 int ch = 21;
			Text text1 = new Text(Integer.toString(ch - rowButtonCount));
			g_pane.add(text1, columnButtonCount, rowButtonCount);
		}

	//    String prefix1,prefix2, btn_name;
	    rowButtonCount = 12;
	    //placing the buttons or holes on the grid
	    for(;rowButtonCount < 21; rowButtonCount += 1) {
	    	for(columnButtonCount = 1;columnButtonCount < 12; columnButtonCount += 1) {
	    		//prefix1 = Integer.toString(rowButtonCount);
	    		//prefix2 = Integer.toString(columnButtonCount);
	    		//btn_name = "btn"+prefix1+prefix2;
	        	Button btn = new Button();
	        	btn.setStyle("-fx-background-color: #000000; ");
	        	btn.setShape(new Circle(r));
	            btn.setMinSize(2*r, 2*r);
	            btn.setMaxSize(2*r, 2*r);
	            btn.setOnAction((ActionEvent event) -> {
	        		btn.setStyle("-fx-background-color: #FFFFFF; "); 
	        	});
	           // s_pane.getChildren().addAll(btn);
	            g_pane.add(btn, columnButtonCount, rowButtonCount);
	    	}
	    }

		// placing the buttons or holes on the grid
		for (; rowButtonCount < 10; rowButtonCount += 1) {
			for (columnButtonCount = 1; columnButtonCount < 12; columnButtonCount += 1) {
				Button btn = new Button();
				btn.setStyle("-fx-background-color: #000000; ");
				btn.setShape(new Circle(r));
				btn.setMinSize(2 * r, 2 * r);
				btn.setMaxSize(2 * r, 2 * r);
				btn.setOnAction((ActionEvent event) -> {
					btn.setStyle("-fx-background-color: #FFFFFF; ");
				});
				g_pane.add(btn, columnButtonCount, rowButtonCount);
			}
		}

		// placing the letters on the grid
		for (columnButtonCount = 1; columnButtonCount < 12; columnButtonCount += 1) {
			char ch = (char) ('A' + columnButtonCount - 1);
			Text text1 = new Text(Character.toString(ch));
			g_pane.add(text1, columnButtonCount, rowButtonCount);
		}
	}

//places the Result for the hit or miss in the bottom of the window
	public void seeResult() {
		Label resultLabel = new Label("Result: ");
		resultLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		resultLabel.setTextFill(Color.web("#c40831"));
		TextField resultTextField = new TextField();
		g_pane2.add(resultLabel, 0, 9);
		g_pane2.add(resultTextField, 1, 25);
	}

	public MenuBar battleMenu(VBox v_box1, Stage stage) {
		Menu menu1 = new Menu("Game");
		Menu menu2 = new Menu("BattleShip");
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(menu1);
		menuBar.getMenus().add(menu2);

		MenuItem menu1Item1 = new MenuItem("Start new game");
		MenuItem menu1Item2 = new MenuItem("Exit");

		menu1Item2.setOnAction(e -> {
			Boolean res = ConfirmBox.display("Confirmation box", "Are you sure?");
			if (res)
				stage.close();
		});

		menu1.getItems().add(menu1Item1);
		menu1.getItems().add(menu1Item2);

		Menu place_ship = new Menu("Place");
		MenuItem Carrier = new MenuItem("Carrier");
		MenuItem Battleship = new MenuItem("Battleship");
		MenuItem Cruiser = new MenuItem("Cruiser");
		MenuItem Submarine = new MenuItem("Submarine");
		MenuItem Destroyer = new MenuItem("Destroyer");
		place_ship.getItems().add(Carrier);
		place_ship.getItems().add(Battleship);
		place_ship.getItems().add(Cruiser);
		place_ship.getItems().add(Submarine);
		place_ship.getItems().add(Destroyer);

		menu2.getItems().add(place_ship);

		GridUser ob = new GridUser();

		Carrier.setOnAction(e -> {

			final TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Add cordinates");
			dialog.setHeaderText("Enter the coordinates:");
			dialog.setContentText("coordinate:");
			dialog.showAndWait();
			// use this value to display on the result box
			System.out.println((String) dialog.getEditor().getText());

		});
		Battleship.setOnAction(e -> {

			final TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Add cordinates");
			dialog.setHeaderText("Enter the coordinates:");
			dialog.setContentText("coordinate:");
			dialog.showAndWait();
			// use this value to display on the result box
			String value = ob.DeployUserGrid((String) dialog.getEditor().getText());

		});
		Cruiser.setOnAction(e -> {
			final TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Add cordinates");
			dialog.setHeaderText("Enter the coordinates:");
			dialog.setContentText("coordinate:");
			dialog.showAndWait();

			String value = ob.DeployUserGrid((String) dialog.getEditor().getText());

		});
		Submarine.setOnAction(e -> {
			final TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Add cordinates");
			dialog.setHeaderText("Enter the coordinates:");
			dialog.setContentText("coordinate:");
			dialog.showAndWait();
			// use this value to display on the result box
			String value = ob.DeployUserGrid((String) dialog.getEditor().getText());

		});
		Destroyer.setOnAction(e -> {
			final TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Add cordinates");
			dialog.setHeaderText("Enter the coordinates:");
			dialog.setContentText("coordinate:");
			dialog.showAndWait();
			// use this value to display on the result box
			String value = ob.DeployUserGrid((String) dialog.getEditor().getText());

		});

		// deployment has been done start the

		return menuBar;

	}

	public void launchStartupWindow(Stage stg) throws Exception {

		GridPane root1 = new GridPane();
		scene2 = new Scene(root1, 800, 600);
		root1.setVgap(10);
		root1.setHgap(10);

		Image image = new Image(new FileInputStream("battleship.png"));

		BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

		Background background = new Background(backgroundimage);

		root1.setBackground(background);

		// Text text = new Text("The Battleship Game!");
		// text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 20));
		Button btn1 = new Button("Start New Game! (Vs. CPU)");
		btn1.setStyle("-fx-background-color: #a3a0a0; ");
		Button btn2 = new Button("Exit Game");
		btn2.setStyle("-fx-background-color: #a3a0a0; ");
		// root1.setStyle("-fx-background-color: #0000FF;");
		btn1.setOnAction((ActionEvent event) -> {
			stg.setScene(scene1);
		});
		btn2.setOnAction((ActionEvent event) -> {
			Boolean res = ConfirmBox.display("Confirmation box", "Are you sure?");
			if (res)
				stg.close();
		});
		root1.setAlignment(Pos.CENTER);
		root1.add(btn1, 0, 1);
		root1.add(btn2, 0, 2);
		// root1.add(text, 0, 0);
		stg.setScene(scene2);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
