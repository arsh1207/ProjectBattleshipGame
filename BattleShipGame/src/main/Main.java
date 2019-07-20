package main;

import java.io.FileInputStream;
import application.Controllers.GridUser;
import application.Models.Computer;
import application.Models.HitStrategy;
import application.Models.Player;
import application.Views.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application{

	
	static GridUser ob;
	Scene scene1;
	Scene scene2;
	GridPane g_pane1, g_pane2;
	VBox v_box1, v_box2, v_box3;
	//static Button[][] radarButton;
	//static Button[][] userButton;
	int rowButtonCount;
	int columnButtonCount;
	int buttonRowIndex;
	Label resulttext1, resulttext2;
	public static String shipType = "";
	public static String gameMode = "Medium"; 
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Stage stage = primaryStage;
			stage.setTitle(" Battle Ship Game");
			launchStartupWindow(stage);
			
			Player player = new Player();
			Computer computer = new Computer();
			HitStrategy strategy = new HitStrategy();
			ob = new GridUser(player, computer, strategy);
			ShipGrid sg = new ShipGrid(player, ob, strategy);
			
			
			 ScrollPane sp = new ScrollPane();
	
			SplitPane split_pane = new SplitPane();
			g_pane1 = new GridPane();
			g_pane2 = new GridPane();

			v_box3 = new VBox();
			v_box1 = new VBox();
			v_box2 = new VBox();
			v_box1.setId("glass-grey");
			Image image = new Image(new FileInputStream("images/bombs.png"));
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(500);
			imageView.setFitWidth(800);
			imageView.setPreserveRatio(true);
			imageView.fitWidthProperty().bind(v_box2.widthProperty());
		//	Battleship_Grid_Pane obj = new Battleship_Grid_Pane();

			//MenuBar menuBar = obj.battleMenu(v_box1, stage);
			MenuBar menuBar = battleMenu(v_box1, stage);

			 g_pane1.setVgap(10);
			 g_pane1.setHgap(10);

			 g_pane2.setVgap(10);
			 g_pane2.setHgap(10);
			// g_pane.setStyle("-fx-color: black;");
			//g_pane1.setGridLinesVisible(true);
			//g_pane2.setGridLinesVisible(true);

			// g_pane.alignmentProperty().addListener(listener );
			 Label l1 = new Label();
			 seeResultUser("User ");
				v_box3.getChildren().add(l1);
				seeResultComp("Computer ");

				RadarGrid radarGridObserver = new RadarGrid(computer, resulttext2, resulttext1, ob, strategy);
			 radarGridObserver.setUserRadarGrid(g_pane1, resulttext2);
			
			sg.setUserShipGrid(g_pane2);
			Button userRandomShips = new Button("Feelin' Lazy?");
			VBox v_box4 = new VBox();

			v_box1.getChildren().addAll(g_pane1, g_pane2, userRandomShips);
			v_box1.setSpacing(20.0);
			
			userRandomShips.setOnAction((ActionEvent event) -> {
				if(player.numOfShipsDep==0)
					player.deployUserRandomShips();
			});
			

			v_box2.setStyle("-fx-background-color: #000000;");
			v_box2.getChildren().addAll(imageView, v_box3);

			split_pane.setDividerPositions(0.7);
			//sp.setBackground(Color.WHITE);
			sp.setContent(v_box1);
			split_pane.getItems().add(sp);
			split_pane.getItems().add(v_box2);

			
			Button startBtn = new Button("Start Playing");
			startBtn.setDisable(false);
			startBtn.setOnAction((ActionEvent event) -> {
				if (player.numOfShipsDep == 5) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Battleship Game");
					alert.setHeaderText("The battle ships are placed correctly.");
					alert.setContentText("You can start playing!");
					alert.showAndWait();
					for (int i = 0; i < 9; i++) {
						for (int j = 0; j < 11; j++) {
							radarGridObserver.radarButton[i][j].setDisable(false);
						}
					}
					computer.deployComputerShips();
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Battleship Game");
					alert.setHeaderText("The battle ships are not placed correctly.");
					alert.setContentText("Please place them before starting.");
					alert.showAndWait();

				}
			});

			for (Node node : g_pane2.getChildren()) {
				node.setOnMouseEntered((MouseEvent t) -> {
					node.setStyle("-fx-background-color: blue;");
				});

				node.setOnMouseExited((MouseEvent t) -> {
					node.setStyle("-fx-background-color: black;");
				});

			}

			v_box3.getChildren().addAll(startBtn);
			v_box4.getChildren().addAll(menuBar, split_pane);
			v_box1.fillWidthProperty();
			scene1 = new Scene(v_box4, 800, 750);
			sp.getStylesheets().add("application/Views/application.css");
			sp.fitToWidthProperty();
			v_box1.prefWidthProperty().bind(sp.widthProperty());
			//v_box1.prefHeightProperty().bind(sp.heightProperty());
			split_pane.prefHeightProperty().bind(stage.heightProperty());
			stage.show();

			// deployment has been done start the game turn by turn now
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public MenuBar battleMenu(VBox v_box1, Stage stage) {
		Menu menu1 = new Menu("Game");
		Menu menu2 = new Menu("BattleShip");
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(menu1);
		menuBar.getMenus().add(menu2);

		MenuItem menu1Item1 = new MenuItem("Start new game");
		MenuItem menu1Item2 = new MenuItem("Exit");

		menu1Item1.setOnAction(e -> {
			stage.close();
			try {
				start(new Stage());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		//	ob.reInitialize();
		});

		menu1Item2.setOnAction(e -> {
			Boolean res = ConfirmBox.display("Confirmation box", "Are you sure?");
			if (res)
				stage.close();
		});

		menu1.getItems().add(menu1Item1);
		menu1.getItems().add(menu1Item2);

		Menu place_ship = new Menu("Place");
		MenuItem Carrier = new MenuItem("Carrier (5)");
		Carrier.setGraphic(new ImageView("file:images/blue1.png"));
		MenuItem Battleship = new MenuItem("Battleship (4)");
		Battleship.setGraphic(new ImageView("file:images/brown.png"));
		MenuItem Cruiser = new MenuItem("Cruiser (3)");
		Cruiser.setGraphic(new ImageView("file:images/green.png"));
		MenuItem Submarine = new MenuItem("Submarine (3)");
		Submarine.setGraphic(new ImageView("file:images/orange.png"));
		MenuItem Destroyer = new MenuItem("Destroyer (2)");
		Destroyer.setGraphic(new ImageView("file:images/yellow.png"));
		place_ship.getItems().add(Carrier);
		place_ship.getItems().add(Battleship);
		place_ship.getItems().add(Cruiser);
		place_ship.getItems().add(Submarine);
		place_ship.getItems().add(Destroyer);

		menu2.getItems().add(place_ship);

		Carrier.setOnAction(e -> {
			shipType = "Carrier";

		});
		Battleship.setOnAction(e -> {

			shipType = "Battleship";
			
		});
		Cruiser.setOnAction(e -> {

			shipType = "Cruiser";
		});
		Submarine.setOnAction(e -> {
			shipType = "Submarine";
			

		});
		Destroyer.setOnAction(e -> {
			shipType = "Destroyer";
			
		});

		return menuBar;
	}
	
	/**
	 * The startup window gets launched with this method
	 * 
	 * @param stg It is a temorary stage used a start up screen
	 * @throws Exception may throw file not found error
	 */
	public void launchStartupWindow(Stage stg) throws Exception {

		GridPane root1 = new GridPane();
		scene2 = new Scene(root1, 800, 600);
		root1.setVgap(10);
		root1.setHgap(10);

		Image image = new Image(new FileInputStream("images/battleship.png"));

		BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

		Background background = new Background(backgroundimage);

		root1.setBackground(background);

		MenuItem mI1 = new MenuItem("Easy");
		MenuItem mI2 = new MenuItem("Medium");
		MenuItem mI3 = new MenuItem("Hard");
		MenuButton menuButton = new MenuButton("Please select a mode.");
		menuButton.getItems().addAll(mI1, mI2, mI3);
		
		mI1.setOnAction(event -> {
		   gameMode = "Easy";
		});
		mI2.setOnAction(event -> {
		    gameMode = "Medium";
		});
		mI3.setOnAction(event -> {
		    gameMode = "Hard";
		});
		
		Button btn1 = new Button("Start New Game");
		btn1.setStyle("-fx-background-color: #a3a0a0; ");
		Button btn2 = new Button("Exit Game");
		btn2.setStyle("-fx-background-color: #a3a0a0; ");
		btn1.setOnAction((ActionEvent event) -> {
			stg.setScene(scene1);
		});
		btn2.setOnAction((ActionEvent event) -> {
			Boolean res = ConfirmBox.display("Confirmation box", "Are you sure?");
			if (res)
				stg.close();
		});
		root1.setAlignment(Pos.CENTER);
		root1.add(menuButton, 0, 0);
		root1.add(btn1, 0, 1);
		root1.add(btn2, 0, 2);
		stg.setScene(scene2);
	}
	
	/**
	 * Places the result of the user on the screen
	 * 
	 * @param title To display name of caller (User or CPU).
	 */
	public void seeResultUser(String title) {
		Label resultLabel = new Label(title + "Turn: ");
		resultLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		resultLabel.setTextFill(Color.web("#c40831"));
		resulttext1 = new Label();
		resulttext1.setStyle("-fx-background-color: white;");
		v_box3.getChildren().addAll(resultLabel, resulttext1);

	}

	/**
	 * Places the result of the computer on the screen
	 * 
	 * @param title To display name of caller (User or CPU).
	 */
	public void seeResultComp(String title) {
		Label resultLabel = new Label(title + "Turn: ");
		resultLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		resultLabel.setTextFill(Color.web("#c40831"));
		resulttext2 = new Label();
		resulttext2.setStyle("-fx-background-color: white;");
		v_box3.getChildren().addAll(resultLabel, resulttext2);
	}


}
