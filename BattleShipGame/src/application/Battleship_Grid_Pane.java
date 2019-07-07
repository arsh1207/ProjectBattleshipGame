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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
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
	GridPane g_pane;
	VBox v_box1, v_box2, v_box3;
	Button[][] radarButton;
	Button[][] userButton;
	int rowButtonCount;
	int columnButtonCount;
	int buttonRowIndex;
	
	// Stage stage;
	@Override
	public void start(Stage primarystage) {
		try {
			Stage stage = primarystage;
			stage.setTitle(" Battle Ship Game");
			
			launchStartupWindow(stage);
			
			radarButton = new Button[9][11];
			userButton = new Button[9][11];
			SplitPane split_pane = new SplitPane();
			g_pane = new GridPane();
			g_pane.setId("glass-grey");
			v_box3 = new VBox();
			v_box1 = new VBox();
			v_box2 = new VBox();
			
			Image image = new Image(new FileInputStream("bombs.png"));
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(500);
			imageView.setFitWidth(800);
			imageView.setPreserveRatio(true);
			Battleship_Grid_Pane obj = new Battleship_Grid_Pane();
			
			MenuBar menuBar = obj.battleMenu(v_box1, stage);
			
			//g_pane.setStyle("-fx-background-color: #35E7DB;");
			g_pane.setVgap(10);
			g_pane.setHgap(10);
			
			setUserRadarGrid();
			
			setUserShipGrid();
			
			//changes
			
			VBox v_box4 = new VBox();
			
			v_box1.getChildren().addAll(g_pane);
			
			v_box2.setStyle("-fx-background-color: #000000;");
			v_box2.getChildren().addAll(imageView, v_box3);
			
			split_pane.setDividerPositions(0.7);
			
			split_pane.getItems().add(v_box1);
			split_pane.getItems().add(v_box2);
			seeResult("Computer ");
			seeResult("User ");
			
			v_box4.getChildren().addAll(menuBar, split_pane);
			
			scene1 = new Scene(v_box4, 800, 700);
			g_pane.getStylesheets().add("application/application.css");
			// stage.setScene(scene1);
			stage.show();
			
			// deployment has been done start the game turn by turn now
		}
		
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setUserRadarGrid() {
		//System.out.println("here");
		double r = 7.5;
		Text t = new Text("Radar Grid");
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		g_pane.add(t, columnButtonCount, rowButtonCount);
		rowButtonCount = 0;
		columnButtonCount = 0;
		
		for (rowButtonCount = 10; rowButtonCount >= 1; rowButtonCount--) {
			int ch = 10;
			Text text1 = new Text(Integer.toString(ch - rowButtonCount));
			g_pane.add(text1, columnButtonCount, rowButtonCount);
		}
		
		//initializing the radar grid buttons of 9*11 size
		//so that they can be accessed via ID
		for (rowButtonCount = 0; rowButtonCount < 9; rowButtonCount++) {
			for (columnButtonCount = 0; columnButtonCount < 11; columnButtonCount++) {
				radarButton[rowButtonCount][columnButtonCount] = new Button();
		}
	}
	
		buttonRowIndex = 8;
	
		// placing the buttons or holes on the grid
		for (rowButtonCount = 1; rowButtonCount < 10; rowButtonCount += 1) {
			columnButtonCount = 1;
			//columnButtonCount = 0; columnButtonCount < 11; columnButtonCount += 1
			for (Button b : radarButton[buttonRowIndex]) {
				b.setStyle("-fx-background-color: #000000; ");
				b.setShape(new Circle(r));
				b.setMinSize(2 * r, 2 * r);
				b.setMaxSize(2 * r, 2 * r);
				b.setOnAction((ActionEvent event) -> {
				b.setStyle("-fx-background-color: #FFFFFF; ");
				});
				g_pane.add(b, columnButtonCount, rowButtonCount);
				columnButtonCount++;
			}
			if(buttonRowIndex > 0) {
			buttonRowIndex--;
			}
		}
		
		// placing the letters on the grid
		for (columnButtonCount = 1; columnButtonCount < 12; columnButtonCount += 1) {
			char ch = (char) ('A' + columnButtonCount - 1);
			Text text1 = new Text(Character.toString(ch));
			g_pane.add(text1, columnButtonCount, rowButtonCount);
		}
	}
	
	public void setUserShipGrid() {
		//System.out.println("here");
		
		Rectangle carrier1 = new Rectangle(20, 30, Color.BLUE);
		Rectangle carrier2 = new Rectangle(20, 30, Color.BLUE);
		Rectangle carrier3 = new Rectangle(20, 30, Color.BLUE);
		Rectangle carrier4 = new Rectangle(20, 30, Color.BLUE);
		Rectangle carrier5 = new Rectangle(20, 30, Color.BLUE);
		
		Rectangle battleship1 = new Rectangle(20, 30, Color.BROWN);
		Rectangle battleship2 = new Rectangle(20, 30, Color.BROWN);
		
		
		double r = 7.5;
		Text t = new Text("Ship Grid");
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		g_pane.add(t, 0, 11);
		rowButtonCount = 19;
		columnButtonCount = 0;
		// placing the numbers for the grid
		for (rowButtonCount = 21; rowButtonCount >= 12; rowButtonCount--) {
			int ch = 21;
			Text text1 = new Text(Integer.toString(ch - rowButtonCount));
			g_pane.add(text1, columnButtonCount, rowButtonCount);
		}
		
		g_pane.add(carrier1, 2, 15);
		g_pane.add(carrier2, 3, 15);
		g_pane.add(carrier3, 4, 15);
		g_pane.add(carrier4, 5, 15);
		g_pane.add(carrier5, 6, 15);
		
		g_pane.add(battleship1, 5, 13);
		g_pane.add(battleship2, 5, 12);
		
		//initializing the radar grid buttons of 9*11 size
		//so that they can be accessed via ID
		for (rowButtonCount = 0; rowButtonCount < 9; rowButtonCount++) {
			for (columnButtonCount = 0; columnButtonCount < 11; columnButtonCount++) {
				userButton[rowButtonCount][columnButtonCount] = new Button();
			}
		}
		
		rowButtonCount = 12;
		buttonRowIndex = 8;
		// placing the buttons or holes on the grid
		for (; rowButtonCount < 21; rowButtonCount += 1) {
		columnButtonCount = 1;
		//columnButtonCount = 0; columnButtonCount < 11; columnButtonCount += 1
			for (Button b : userButton[buttonRowIndex]) {
				b.setStyle("-fx-background-color: #000000; ");
				b.setShape(new Circle(r));
				b.setMinSize(2 * r, 2 * r);
				b.setMaxSize(2 * r, 2 * r);
				b.setOnAction((ActionEvent event) -> {
				b.setStyle("-fx-background-color: #FFFFFF; ");
				});
				g_pane.add(b, columnButtonCount, rowButtonCount);
				columnButtonCount++;
			}
			if(buttonRowIndex > 0) {
				buttonRowIndex--;
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
	public void seeResult(String title) {
		Label resultLabel = new Label(title+ "Result: ");
		resultLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		resultLabel.setTextFill(Color.web("#c40831"));
		TextField resultTextField = new TextField();
		//Label resulttext = new Label();
		//resulttext.setStyle("-fx-background-color: white;");
		v_box3.getChildren().addAll(resultLabel, resultTextField);
		
		//g_pane2.add(resultLabel, 0, 9);
		//g_pane2.add(resultTextField, 1, 25);
	}
	
	/**
	 * Menubar displaying the menu for the game including placment of battleships
	 * 
	 * @param v_box1
	 * @param stage
	 * @return
	 */
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
		Carrier.setGraphic(new ImageView("file:images/blue1.png"));
		MenuItem Battleship = new MenuItem("Battleship");
		Battleship.setGraphic(new ImageView("file:images/brown.png"));
		MenuItem Cruiser = new MenuItem("Cruiser");
		Cruiser.setGraphic(new ImageView("file:images/green.png"));
		MenuItem Submarine = new MenuItem("Submarine");
		Submarine.setGraphic(new ImageView("file:images/orange.png"));
		MenuItem Destroyer = new MenuItem("Destroyer");
		Destroyer.setGraphic(new ImageView("file:images/yellow.png"));
		place_ship.getItems().add(Carrier);
		place_ship.getItems().add(Battleship);
		place_ship.getItems().add(Cruiser);
		place_ship.getItems().add(Submarine);
		place_ship.getItems().add(Destroyer);
		
		menu2.getItems().add(place_ship);
		
		GridUser ob = new GridUser();
		
		Carrier.setOnAction(e -> {
			
			// the format of result I have kept same so no need to change anything in grid user related to 
			//input format
			
			String res = InputBox.display("Carrier ship");
			//System.out.println(res);
			String value = ob.DeployUserGrid(res);
		
		});
		Battleship.setOnAction(e -> {
			/*
			 * final TextInputDialog dialog = new TextInputDialog();
			 * dialog.setTitle("Add cordinates");
			 * dialog.setHeaderText("Enter the coordinates:");
			 * dialog.setContentText("coordinate:"); dialog.showAndWait(); // use this value
			 * to display on the result box String value = ob.DeployUserGrid((String)
			 * dialog.getEditor().getText());
			 */
			
			String res = InputBox.display("Battleship");
			String value = ob.DeployUserGrid(res);
		});
		Cruiser.setOnAction(e -> {
			String res = InputBox.display(" Cruiser ");
			String value = ob.DeployUserGrid(res);
		});
		Submarine.setOnAction(e -> {
			String res = InputBox.display("Submarine");
			String value = ob.DeployUserGrid(res);
		});
		Destroyer.setOnAction(e -> {
			String res = InputBox.display("Destroyer");
			String value = ob.DeployUserGrid(res);
		});
		
		
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
	
	//trying to commit and push
	public static void main(String[] args) {
	launch(args);
	}
}