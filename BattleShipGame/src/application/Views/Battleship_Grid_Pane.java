package application.Views;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import application.Controllers.GridUser;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
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
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Battleship_Grid_Pane extends Application {

	static GridUser ob;
	Scene scene1;
	Scene scene2;
	GridPane g_pane1, g_pane2;
	VBox v_box1, v_box2, v_box3;
	static Button[][] radarButton;
	static Button[][] userButton;
	int rowButtonCount;
	int columnButtonCount;
	int buttonRowIndex;
	Label resulttext1, resulttext2;
	String initialCoordinates, finalCoordinates, shipType = "";
	int noButtonsClicked = 0;
	static String gameMode = "Medium"; 
	
	Boolean lastCompResult = false;

	@Override
	public void start(Stage primarystage) {
		try {
			Stage stage = primarystage;
			stage.setTitle(" Battle Ship Game");
			launchStartupWindow(stage);
			ob = new GridUser();
			 ScrollPane sp = new ScrollPane();
			radarButton = new Button[9][11];
			userButton = new Button[9][11];
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

			setUserRadarGrid();

			setUserShipGrid();
			Button userRandomShips = new Button("Feelin' Lazy?");
			VBox v_box4 = new VBox();

			v_box1.getChildren().addAll(g_pane1, g_pane2, userRandomShips);
			v_box1.setSpacing(20.0);
			userRandomShips.setOnAction((ActionEvent event) -> {
				if(GridUser.numOfShipsDep==0)
					ob.deployUserRandomShips();
			});
			Label l1 = new Label();

			v_box2.setStyle("-fx-background-color: #000000;");
			v_box2.getChildren().addAll(imageView, v_box3);

			split_pane.setDividerPositions(0.7);
			//sp.setBackground(Color.WHITE);
			sp.setContent(v_box1);
			split_pane.getItems().add(sp);
			split_pane.getItems().add(v_box2);

			seeResultUser("User ");
			v_box3.getChildren().add(l1);
			seeResultComp("Computer ");

			Button startBtn = new Button("Start Playing");
			startBtn.setDisable(false);
			startBtn.setOnAction((ActionEvent event) -> {
				if (GridUser.numOfShipsDep == 5) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Battleship Game");
					alert.setHeaderText("The battle ships are placed correctly.");
					alert.setContentText("You can start playing!");
					alert.showAndWait();
					for (int i = 0; i < 9; i++) {
						for (int j = 0; j < 11; j++) {
							radarButton[i][j].setDisable(false);
						}
					}
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
		}
	}

	/**
	 * Deploys the radar grid on the screen
	 * @throws FileNotFoundException 
	 */
	public void setUserRadarGrid() throws FileNotFoundException {
		double r = 10;
		Text t = new Text("Radar Grid");
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		rowButtonCount = 0;
		columnButtonCount = 0;
		g_pane1.add(t, columnButtonCount, rowButtonCount);
		for (rowButtonCount = 10; rowButtonCount >= 1; rowButtonCount--) {
			int ch = 10;
			Text text1 = new Text(Integer.toString(ch - rowButtonCount));
			g_pane1.add(text1, columnButtonCount, rowButtonCount);
		}

		// initializing the radar grid buttons of 9*11 size
		// so that they can be accessed via ID
		for (rowButtonCount = 0; rowButtonCount < 9; rowButtonCount++) {
			for (columnButtonCount = 0; columnButtonCount < 11; columnButtonCount++) {
				radarButton[rowButtonCount][columnButtonCount] = new Button();
			}
		}

		buttonRowIndex = 0;

		
		
		// placing the buttons or holes on the grid
		for (rowButtonCount = 9; rowButtonCount >= 1; rowButtonCount -= 1) {
			columnButtonCount = 1;
			for (Button b : radarButton[buttonRowIndex]) {
				b.setStyle("-fx-background-color: #000000; ");
				b.setId((buttonRowIndex) + ":" + (columnButtonCount - 1));
				b.setDisable(true);
				b.setShape(new Circle(r));
				b.setMinSize(2 * r, 2 * r);
				b.setMaxSize(2 * r, 2 * r);
				b.setOnAction((ActionEvent event) -> {
					
					Image image;
					try {
						image = new Image(new FileInputStream("images/blast.png"));
						ImageView imageView = new ImageView(image);
						imageView.setFitHeight(25);
						imageView.setFitWidth(25);
						
					
						FadeTransition ft = new FadeTransition(Duration.millis(3000), imageView);
						ft.setFromValue(1.0);
						ft.setToValue(0.0);
						ft.play();
						AudioClip audioClip = new AudioClip(Paths.get("Sounds/blast.wav").toUri().toString());
					

					String xy[] = b.getId().split(":");
					String res = ob.userTurn(Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));

					resulttext2.setText(res);
					if (res.contains("miss"))
						b.setStyle("-fx-background-color: #FFFFFF; ");
					else if (res.contains("Hit")) {
						b.setStyle("-fx-background-color: #ff1100; ");
						g_pane1.add(imageView, (Integer.parseInt(xy[1])+1), (9 - Integer.parseInt(xy[0])));
						audioClip.play(100);
					//	System.out.println(Integer.parseInt(xy[1])+";"+ Integer.parseInt(xy[0]));
						//System.out.println((Integer.parseInt(xy[1])+1)+";"+  (9 - Integer.parseInt(xy[0])));
					}

					// checks if User has Won
					ob.checkIfUserWon();

					//String compres = ob.computerTurn();
					String compres = ob.computerTurn(lastCompResult, gameMode);
					if(compres.contains("Hit")) 
						lastCompResult = true;
					else 
						lastCompResult = false;
					
					resulttext1.setText(compres);
					// Checks if AI has won
					ob.checkIfCompWon();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				
				g_pane1.add(b, columnButtonCount, rowButtonCount);
				columnButtonCount++;
			}
			if (buttonRowIndex < 9) {
				buttonRowIndex++;
			}
		}
		rowButtonCount = 10;
		// placing the letters on the grid
		for (columnButtonCount = 1; columnButtonCount < 12; columnButtonCount += 1) {
			char ch = (char) ('A' + columnButtonCount - 1);
			Text text1 = new Text(Character.toString(ch));
			g_pane1.add(text1, columnButtonCount, rowButtonCount);
		}
	}

	/**
	 * Deploys user grid on the screen
	 */
	public void setUserShipGrid() {

		double r = 10;
		Text t = new Text("User Grid");
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		rowButtonCount = 0;
		columnButtonCount = 0;
		g_pane2.add(t, columnButtonCount, rowButtonCount);
		for (rowButtonCount = 10; rowButtonCount >= 1; rowButtonCount--) {
			int ch = 10;
			Text text1 = new Text(Integer.toString(ch - rowButtonCount));
			g_pane2.add(text1, columnButtonCount, rowButtonCount);
		}

		// initializing the radar grid buttons of 9*11 size
		// so that they can be accessed via ID
		for (rowButtonCount = 0; rowButtonCount < 9; rowButtonCount++) {
			for (columnButtonCount = 0; columnButtonCount < 11; columnButtonCount++) {
				userButton[rowButtonCount][columnButtonCount] = new Button();
			}
		}

		buttonRowIndex = 0;

		// placing the buttons or holes on the grid
		for (rowButtonCount = 9; rowButtonCount >= 1; rowButtonCount -= 1) {
			columnButtonCount = 1;
			for (Button b : userButton[buttonRowIndex]) {
				b.setStyle("-fx-background-color: #000000; ");
				b.setId((buttonRowIndex) + " " + (columnButtonCount - 1));

				b.setShape(new Circle(r));
				b.setMinSize(2 * r, 2 * r);
				b.setMaxSize(2 * r, 2 * r);
				b.setOnMouseClicked(event -> {

					noButtonsClicked++;
					if (noButtonsClicked == 1) {
						initialCoordinates = b.getId();
						b.setStyle("-fx-background-color: red;");
						// b.setOnMouseEntered(null);
						b.setOnMouseExited(null);
					} else if (noButtonsClicked == 2) {
						finalCoordinates = b.getId();
					//	System.out.println(initialCoordinates + " " + finalCoordinates);
						noButtonsClicked = 0;
						String xy[] =initialCoordinates.split(" ");
						int xInitialCo = Integer.parseInt(xy[0]);
						int yInitialCo = Integer.parseInt(xy[1]);
						if (shipType.isEmpty()) {
							userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
							AlertBox.displayError("Error", "Please first select a ship type from menu options.");
						} else {
							
							if (!ob.areAllShipsDeployed()) {
								if (!ob.isShipDeployed(shipType)) {
									String res = initialCoordinates + " " + finalCoordinates;// InputBox.display("Carrier
																								// ship");
									//System.out.println(res);
									String value = ob.deployUserGrid(res, shipType);
									if (!(value.equals("Done"))) {
										userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
										AlertBox.displayError(shipType, value);
									}
								//	System.out.println(value);
								} else {
									userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
									AlertBox.displayError(shipType, "Already Deployed!");
								}
							} else {
								userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
								AlertBox.displayError(shipType, "All Ships Deployed!");
							}
							shipType = "";
						}
					}
					/*
					 * if (event.getButton() == MouseButton.PRIMARY) {
					 * b.setStyle("-fx-background-color: red;"); } else if (event.getButton() ==
					 * MouseButton.SECONDARY) { b.setStyle("-fx-background-color: yellow;"); }
					 */

				});

				g_pane2.add(b, columnButtonCount, rowButtonCount);
				columnButtonCount++;
			}
			if (buttonRowIndex < 9) {
				buttonRowIndex++;
			}
		}
		rowButtonCount = 10;
		// placing the letters on the grid
		for (columnButtonCount = 1; columnButtonCount < 12; columnButtonCount += 1) {
			char ch = (char) ('A' + columnButtonCount - 1);
			Text text1 = new Text(Character.toString(ch));
			g_pane2.add(text1, columnButtonCount, rowButtonCount);
		}

	
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

	/**
	 * Menu bar displaying the menu for the game including placement of battleships
	 * 
	 * @param v_box1 layout used to add the menu bar
	 * @param stage  The stage variable holding all the layouts.
	 * @return Menu bar obeject after adding all the required options on it
	 */
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
			start(new Stage());
			ob.reInitialize();
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
	 * This method deploys the ships with different colors
	 * 
	 * @param coords   holds the co-ordinates that will changed as per the ship type
	 * @param shipType The type of ship being placed
	 * @param axis     tell either horizontal of vertical
	 */
	public void deployShipsWithColors(int[] coords, String shipType, String axis) {

		// if the ship is to be placed along Y-axis
		if (axis.equals("Y")) {
			if (shipType.equals("Carrier"))
				colorShipYCoords(coords, "#000080");
			if (shipType.equals("Battleship"))
				colorShipYCoords(coords, "#654321");
			if (shipType.equals("Cruiser"))
				colorShipYCoords(coords, "#008000");
			if (shipType.equals("Submarine"))
				colorShipYCoords(coords, "#FFC0CB");
			if (shipType.equals("Destroyer"))
				colorShipYCoords(coords, "#FFFF00");
		}

		// if it is to be placed along X-axis
		else {
			if (shipType.equals("Carrier"))
				colorShipXCoords(coords, "#000080");
			if (shipType.equals("Battleship"))
				colorShipXCoords(coords, "#D2691E");
			if (shipType.equals("Cruiser"))
				colorShipXCoords(coords, "#008000");
			if (shipType.equals("Submarine"))
				colorShipXCoords(coords, "#FFA500");
			if (shipType.equals("Destroyer"))
				colorShipXCoords(coords, "#FFFF00");
		}
	}

	/**
	 * This function will color the user coordinates based on the hit or miss
	 * 
	 * @param x   x-coordinate of gird being hit
	 * @param y   y-coordinate of gird being hit
	 * @param res contains hit or miss
	 */
	public static void setUserShipCoordinates(int x, int y, String res) {

	//	GridUser.displayUserShips();

		if (res.equals("Miss"))
			userButton[x][y].setStyle("-fx-background-color: #FFFFFF; ");
		else if (res.equals("Hit"))
			userButton[x][y].setStyle("-fx-background-color: #ff1100; ");

	}

	/**
	 * The function will color the Y coordinates based on the type of ship
	 * 
	 * @param coords coordinates that need to be updated for ship placement
	 * @param color  Which color needs to be placed
	 */
	public void colorShipYCoords(int[] coords, String color) {

		for (int i = coords[1]; i <= coords[3]; i++) {
			userButton[i][coords[0]].setStyle("-fx-background-color: " + color + "; ");
			userButton[i][coords[0]].setOnMouseEntered(null);
			userButton[i][coords[0]].setOnMouseExited(null);
			userButton[i][coords[0]].setOnMouseClicked(null);

		}
	}

	/**
	 * The function will color the X coordinates based on the type of ship
	 * 
	 * @param coords coordinates that need to be updated for ship placement
	 * @param color  Which color needs to be placed
	 */
	public void colorShipXCoords(int[] coords, String color) {

		for (int i = coords[0]; i <= coords[2]; i++) {
			userButton[coords[1]][i].setStyle("-fx-background-color: " + color + "; ");
			userButton[coords[1]][i].setOnMouseEntered(null);
			userButton[coords[1]][i].setOnMouseExited(null);
			userButton[coords[1]][i].setOnMouseClicked(null);
		}
	}

	/**
	 * main function
	 * 
	 * @param args default arguments array
	 */
	public static void main(String[] args) {
		launch(args);
	}
}