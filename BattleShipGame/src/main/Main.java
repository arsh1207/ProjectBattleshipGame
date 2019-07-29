package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;

import application.Controllers.GridUser;
import application.Models.Computer;
import application.Models.HitStrategy;
import application.Models.HitStrategySalvo;
import application.Models.Player;
import application.Views.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application implements Observer {

	Player player;
	ShipGrid sg;
	static GridUser ob;
	Scene scene1;
	Scene scene2;
	GridPane g_pane1, g_pane2;
	VBox v_box1, v_box2, v_box3;
	HBox h_box1, h_box2;
	public static final int TOTAL_SHIPS = 5;
	int rowButtonCount;
	int columnButtonCount;
	int buttonRowIndex;
	Label resulttext1, resulttext2, resulttext3, resulttext4;
	// public static String gameType = "Salvo";
	public static String gameType = "None";
	public static String shipType = "";
	public static String gameMode = "Medium";

	public static Button tossBtn;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Stage stage = primaryStage;
			stage.setTitle(" Battle Ship Game");
			launchStartupWindow(stage);
			Main main = new Main();
			player = new Player();
			Computer computer = new Computer();
			HitStrategy strategy = new HitStrategy();
			HitStrategySalvo strategySalvo = new HitStrategySalvo();
			ob = new GridUser(player, computer, strategy, strategySalvo);
			sg = new ShipGrid(player, ob, resulttext1, resulttext4);
			player.addObserver(sg);
			strategy.addObserver(sg);
			strategySalvo.addObserver(sg);
			player.addObserver(main);
			//strategy.addObserver(main);

			SplitPane split_pane = new SplitPane();
			SplitPane split_pane2 = new SplitPane();
			// HBox h_box = new HBox();

			g_pane1 = new GridPane();
			g_pane2 = new GridPane();

			v_box3 = new VBox();
			v_box1 = new VBox();
			v_box2 = new VBox();
			h_box1 = new HBox();
			h_box2 = new HBox();
			v_box1.setId("glass-grey");
			v_box2.setId("glass-grey");

			String[] shipNames = { "Carrier", "Battleship", "Cruiser", "Submarine", "Destroyer" };
			for (String name : shipNames) {
				setShipImages(v_box2, name);
			}

			// MenuBar menuBar = obj.battleMenu(v_box1, stage);
			MenuBar menuBar = battleMenu(v_box1, stage);

			g_pane1.setVgap(10);
			g_pane1.setHgap(10);

			g_pane2.setVgap(10);
			g_pane2.setHgap(10);

			Label l1 = new Label();
			seeResultUser("User ");
			v_box3.getChildren().add(l1);
			seeResultComp("Computer ");
			Score("Player SCORE");
			ScoreComp("Computer SCORE");

			RadarGrid radarGridObserver = new RadarGrid(resulttext2, resulttext1, resulttext3, resulttext4, ob);
			computer.addObserver(radarGridObserver);
			//strategy.addObserver(radarGridObserver);
			radarGridObserver.setUserRadarGrid(g_pane1, resulttext2);
			sg.setUserShipGrid(g_pane2);
			setShipPlacementActions();
			Button userRandomShips = new Button("Feelin' Lazy?");
			VBox v_box4 = new VBox();
			v_box1.getChildren().addAll(g_pane1, userRandomShips, g_pane2);
			v_box1.setSpacing(20.0);
			// v_box2.setSpacing(10.0);
			userRandomShips.setOnAction((ActionEvent event) -> {
				if (Player.numOfShipsDep == 0)
			
					ob.deployUserShips();
			});

			// v_box2.setStyle("-fx-background-color: #000000;");
			v_box2.getChildren().addAll(v_box3);

			split_pane.setDividerPositions(0.7);

			split_pane.getItems().add(v_box1);
			split_pane.getItems().add(v_box2);

			Button startBtn = new Button("Start Playing");
			startBtn.setDisable(false);
			startBtn.setOnAction((ActionEvent event) -> {
				ob.deployUserShips();
				if (Player.numOfShipsDep == 5) {
					/*
					 * Alert alert = new Alert(AlertType.INFORMATION);
					 * alert.setTitle("Battleship Game");
					 * alert.setHeaderText("The battle ships are placed correctly.");
					 * alert.setContentText("You can start playing!"); alert.showAndWait();
					 */
					// Salve mode alert box
					
					  if (gameType.equals("Salvo")) { salvoAlertCall(); }
					 
					for (int i = 0; i < 9; i++) {
						for (int j = 0; j < 11; j++) {
							radarGridObserver.radarButton[i][j].setDisable(false);
						}
					}
					ob.deployCompShips();
					
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Battleship Game");
					alert.setHeaderText("The battle ships are not placed correctly.");
					alert.setContentText("Please place them before starting.");
					alert.showAndWait();

				}
			});

			tossBtn = new Button("Toss");
			radarGridObserver.addTossAction();

			for (Node node : g_pane2.getChildren()) {
				node.setOnMouseEntered((MouseEvent t) -> {
					node.setStyle("-fx-background-color: blue;");
				});

				node.setOnMouseExited((MouseEvent t) -> {
					node.setStyle("-fx-background-color: black;");
				});

			}
			split_pane2.getItems().addAll(h_box1, h_box2);
			/*
			 * h_box.getChildren().addAll(h_box1, h_box2); h_box.setSpacing(100);
			 */

			v_box3.getChildren().addAll(startBtn, tossBtn);
			v_box4.getChildren().addAll(menuBar, split_pane2, split_pane);
			v_box1.fillWidthProperty();
			scene1 = new Scene(v_box4, 850, 850);

			v_box1.getStylesheets().add("application/Views/application.css");
			v_box2.getStylesheets().add("application/Views/application.css");

			// v_box1.prefWidthProperty().bind(sp.widthProperty());
			split_pane.prefHeightProperty().bind(stage.heightProperty());
			stage.show();

		}

		catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Player) {
			// TODO Auto-generated method stub
			System.out.println("observer called");
			String value = ((Player) o).getReply();
			System.out.println(value);

			int coord[] = ((Player) o).getCoords();
			String shipType = ((Player) o).getShipType();
			String axis = ((Player) o).getAxis();
			ShipGrid.deployShipsWithColors(coord, shipType, axis);

			if (!(value.equals("Done")) && !value.isEmpty()) {
				// ShipGrid.userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color:
				// black;");
				AlertBox.displayError(shipType, value);
			}
		}

	}

	public void setShipPlacementActions() {
		for (Node node : g_pane2.getChildren()) {

			node.setOnDragEntered(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					// Dragboard db = event.getDragboard();
					// node.setStyle("-fx-background-color: blue;");
					int iniX = Integer.parseInt(node.getId().split(" ")[0]);
					int iniY = Integer.parseInt(node.getId().split(" ")[1]);
					if (event.getDragboard().getString().split(";")[0].equals("Primary")) {
						if (event.getDragboard().getString().split(";")[1].equals("Carrier")) {
							if (iniY - 2 >= 0 && iniY + 2 < 11) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY - 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY - 1].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY - 2].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY - 2].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY + 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 1].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY + 2].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 2].setStyle("-fx-background-color: blue;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Battleship")) {
							if (iniY - 1 >= 0 && iniY + 2 < 11) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY - 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY - 1].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY + 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 1].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY + 2].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 2].setStyle("-fx-background-color: blue;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Cruiser")) {
							if (iniY - 1 >= 0 && iniY + 1 < 11) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY - 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY - 1].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY + 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 1].setStyle("-fx-background-color: blue;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Submarine")) {
							if (iniY - 1 >= 0 && iniY + 1 < 11) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY - 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY - 1].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY + 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 1].setStyle("-fx-background-color: blue;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Destroyer")) {
							if (iniY >= 0 && iniY + 1 < 11) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX][iniY + 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 1].setStyle("-fx-background-color: blue;");
							}
						}
					} else {
						if (event.getDragboard().getString().split(";")[1].equals("Carrier")) {
							if (iniX - 2 >= 0 && iniX + 2 < 9) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX - 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX - 1][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX - 2][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX - 2][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX + 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 1][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX + 2][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 2][iniY].setStyle("-fx-background-color: blue;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Battleship")) {
							if (iniX - 1 >= 0 && iniX + 2 < 9) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX - 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX - 1][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX + 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 1][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX + 2][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 2][iniY].setStyle("-fx-background-color: blue;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Cruiser")) {
							if (iniX - 1 >= 0 && iniX + 1 < 9) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX - 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX - 1][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX + 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 1][iniY].setStyle("-fx-background-color: blue;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Submarine")) {
							if (iniX - 1 >= 0 && iniX + 1 < 9) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX - 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX - 1][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX + 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 1][iniY].setStyle("-fx-background-color: blue;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Destroyer")) {
							if (iniX >= 0 && iniX + 1 < 9) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: blue;");
								if (!ShipGrid.userButton[iniX + 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 1][iniY].setStyle("-fx-background-color: blue;");
							}
						}
					}
					event.consume();
				}
			});

			node.setOnDragExited(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					int iniX = Integer.parseInt(node.getId().split(" ")[0]);
					int iniY = Integer.parseInt(node.getId().split(" ")[1]);
					if (event.getDragboard().getString().split(";")[0].equals("Primary")) {
						if (event.getDragboard().getString().split(";")[1].equals("Carrier")) {
							if (iniY - 2 >= 0 && iniY + 2 < 11) {

								// System.out.println("here :"+ShipGrid.userButton[iniX][iniY].getText());
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY - 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY - 1].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY - 2].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY - 2].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY + 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 1].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY + 2].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 2].setStyle("-fx-background-color: black;");

							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Battleship")) {
							if (iniY - 1 >= 0 && iniY + 2 < 11) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY - 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY - 1].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY + 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 1].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY + 2].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 2].setStyle("-fx-background-color: black;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Cruiser")) {
							if (iniY - 1 >= 0 && iniY + 1 < 11) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY - 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY - 1].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY + 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 1].setStyle("-fx-background-color: black;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Submarine")) {
							if (iniY - 1 >= 0 && iniY + 1 < 11) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY - 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY - 1].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY + 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 1].setStyle("-fx-background-color: black;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Destroyer")) {
							if (iniY >= 0 && iniY + 1 < 11) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX][iniY + 1].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY + 1].setStyle("-fx-background-color: black;");
							}
						}
					} else {
						if (event.getDragboard().getString().split(";")[1].equals("Carrier")) {
							if (iniX - 2 >= 0 && iniX + 2 < 9) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX - 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX - 1][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX - 2][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX - 2][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX + 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 1][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX + 2][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 2][iniY].setStyle("-fx-background-color: black;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Battleship")) {
							if (iniX - 1 >= 0 && iniX + 2 < 9) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX - 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX - 1][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX + 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 1][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX + 2][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 2][iniY].setStyle("-fx-background-color: black;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Cruiser")) {
							if (iniX - 1 >= 0 && iniX + 1 < 9) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX - 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX - 1][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX + 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 1][iniY].setStyle("-fx-background-color: black;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Submarine")) {
							if (iniX - 1 >= 0 && iniX + 1 < 9) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX - 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX - 1][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX + 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 1][iniY].setStyle("-fx-background-color: black;");
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Destroyer")) {
							if (iniX >= 0 && iniX + 1 < 9) {
								if (!ShipGrid.userButton[iniX][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX][iniY].setStyle("-fx-background-color: black;");
								if (!ShipGrid.userButton[iniX + 1][iniY].getText().contains("placed"))
									ShipGrid.userButton[iniX + 1][iniY].setStyle("-fx-background-color: black;");
							}
						}
					}
					event.consume();
				}
			});
			node.setOnDragOver(new EventHandler<DragEvent>() {
				public void handle(DragEvent event) {
					Dragboard db = event.getDragboard();
					if (db.hasImage()) {
						event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
					}
					event.consume();
				}
			});
			node.setOnDragDropped(new EventHandler<DragEvent>() {
				@Override
				public void handle(DragEvent event) {
					String initialCoordinates, finalCoordinates;
					int iniX = Integer.parseInt(node.getId().split(" ")[0]);
					int iniY = Integer.parseInt(node.getId().split(" ")[1]);
					if (event.getDragboard().getString().split(";")[0].equals("Primary")) {
						if (event.getDragboard().getString().split(";")[1].equals("Carrier")) {
							if (iniY - 2 >= 0 && iniY + 2 < 11) {
								initialCoordinates = Integer.toString(iniX) + " " + Integer.toString(iniY - 2);
								finalCoordinates = Integer.toString(iniX) + " " + Integer.toString(iniY + 2);
								if (!player.areAllShipsDeployed()) {
									if (!player.isShipDeployed("Carrier")) {
										String res = initialCoordinates + " " + finalCoordinates;
										ob.callDeployUserGrid(res, "Carrier");
									} else {
										// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
										AlertBox.displayError("Carrier", "Already Deployed!");
									}
								} else {
									// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
									AlertBox.displayError("Carrier", "All Ships Deployed!");
								}
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Battleship")) {
							if (iniY - 1 >= 0 && iniY + 2 < 11) {
								initialCoordinates = Integer.toString(iniX) + " " + Integer.toString(iniY - 1);
								finalCoordinates = Integer.toString(iniX) + " " + Integer.toString(iniY + 2);
								if (!player.areAllShipsDeployed()) {
									if (!player.isShipDeployed("Battleship")) {
										String res = initialCoordinates + " " + finalCoordinates;
										ob.callDeployUserGrid(res, "Battleship");
									} else {
										// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
										AlertBox.displayError("Battleship", "Already Deployed!");
									}
								} else {
									// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
									AlertBox.displayError("Battleship", "All Ships Deployed!");
								}
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Cruiser")) {
							if (iniY - 1 >= 0 && iniY + 1 < 11) {
								initialCoordinates = Integer.toString(iniX) + " " + Integer.toString(iniY - 1);
								finalCoordinates = Integer.toString(iniX) + " " + Integer.toString(iniY + 1);
								if (!player.areAllShipsDeployed()) {
									if (!player.isShipDeployed("Cruiser")) {
										String res = initialCoordinates + " " + finalCoordinates;
										ob.callDeployUserGrid(res, "Cruiser");
									} else {
										// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
										AlertBox.displayError("Cruiser", "Already Deployed!");
									}
								} else {
									// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
									AlertBox.displayError("Cruiser", "All Ships Deployed!");
								}
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Submarine")) {
							if (iniY - 1 >= 0 && iniY + 1 < 11) {
								initialCoordinates = Integer.toString(iniX) + " " + Integer.toString(iniY - 1);
								finalCoordinates = Integer.toString(iniX) + " " + Integer.toString(iniY + 1);
								if (!player.areAllShipsDeployed()) {
									if (!player.isShipDeployed("Submarine")) {
										String res = initialCoordinates + " " + finalCoordinates;
										ob.callDeployUserGrid(res, "Submarine");
									} else {
										// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
										AlertBox.displayError("Submarine", "Already Deployed!");
									}
								} else {
									// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
									AlertBox.displayError("Submarine", "All Ships Deployed!");
								}
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Destroyer")) {
							if (iniY >= 0 && iniY + 1 < 11) {
								initialCoordinates = Integer.toString(iniX) + " " + Integer.toString(iniY);
								finalCoordinates = Integer.toString(iniX) + " " + Integer.toString(iniY + 1);
								if (!player.areAllShipsDeployed()) {
									if (!player.isShipDeployed("Destroyer")) {
										String res = initialCoordinates + " " + finalCoordinates;
										ob.callDeployUserGrid(res, "Destroyer");
									} else {
										// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
										AlertBox.displayError("Destroyer", "Already Deployed!");
									}
								} else {
									// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
									AlertBox.displayError("Destroyer", "All Ships Deployed!");
								}
							}
						}
					} else {
						if (event.getDragboard().getString().split(";")[1].equals("Carrier")) {
							if (iniX - 2 >= 0 && iniX + 2 < 9) {
								initialCoordinates = Integer.toString(iniX - 2) + " " + Integer.toString(iniY);
								finalCoordinates = Integer.toString(iniX + 2) + " " + Integer.toString(iniY);
								if (!player.areAllShipsDeployed()) {
									if (!player.isShipDeployed("Carrier")) {
										String res = initialCoordinates + " " + finalCoordinates;
										ob.callDeployUserGrid(res, "Carrier");
									} else {
										// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
										AlertBox.displayError("Carrier", "Already Deployed!");
									}
								} else {
									// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
									AlertBox.displayError("Carrier", "All Ships Deployed!");
								}
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Battleship")) {
							if (iniX - 1 >= 0 && iniX + 2 < 9) {
								initialCoordinates = Integer.toString(iniX - 1) + " " + Integer.toString(iniY);
								finalCoordinates = Integer.toString(iniX + 2) + " " + Integer.toString(iniY);
								if (!player.areAllShipsDeployed()) {
									if (!player.isShipDeployed("Battleship")) {
										String res = initialCoordinates + " " + finalCoordinates;
										ob.callDeployUserGrid(res, "Battleship");
									} else {
										// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
										AlertBox.displayError("Battleship", "Already Deployed!");
									}
								} else {
									// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
									AlertBox.displayError("Battleship", "All Ships Deployed!");
								}
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Cruiser")) {
							if (iniX - 1 >= 0 && iniX + 1 < 9) {
								initialCoordinates = Integer.toString(iniX - 1) + " " + Integer.toString(iniY);
								finalCoordinates = Integer.toString(iniX + 1) + " " + Integer.toString(iniY);
								if (!player.areAllShipsDeployed()) {
									if (!player.isShipDeployed("Cruiser")) {
										String res = initialCoordinates + " " + finalCoordinates;
										ob.callDeployUserGrid(res, "Cruiser");
									} else {
										// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
										AlertBox.displayError("Cruiser", "Already Deployed!");
									}
								} else {
									// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
									AlertBox.displayError("Cruiser", "All Ships Deployed!");
								}
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Submarine")) {
							if (iniX - 1 >= 0 && iniX + 1 < 9) {
								initialCoordinates = Integer.toString(iniX - 1) + " " + Integer.toString(iniY);
								finalCoordinates = Integer.toString(iniX + 1) + " " + Integer.toString(iniY);
								if (!player.areAllShipsDeployed()) {
									if (!player.isShipDeployed("Submarine")) {
										String res = initialCoordinates + " " + finalCoordinates;
										ob.callDeployUserGrid(res, "Submarine");
									} else {
										// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
										AlertBox.displayError("Submarine", "Already Deployed!");
									}
								} else {
									// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
									AlertBox.displayError("Submarine", "All Ships Deployed!");
								}
							}
						} else if (event.getDragboard().getString().split(";")[1].equals("Destroyer")) {
							if (iniX >= 0 && iniX + 1 < 9) {
								initialCoordinates = Integer.toString(iniX) + " " + Integer.toString(iniY);
								finalCoordinates = Integer.toString(iniX + 1) + " " + Integer.toString(iniY);
								if (!player.areAllShipsDeployed()) {
									if (!player.isShipDeployed("Destroyer")) {
										String res = initialCoordinates + " " + finalCoordinates;
										ob.callDeployUserGrid(res, "Destroyer");
									} else {
										// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
										AlertBox.displayError("Destroyer", "Already Deployed!");
									}
								} else {
									// userButton[xInitialCo][yInitialCo].setStyle("-fx-background-color: black;");
									AlertBox.displayError("Destroyer", "All Ships Deployed!");
								}
							}
						}
					}
				}
			});
		}
	}

	public void setShipImages(VBox v_box2, String shipName) {
		try {
			Image image = new Image(new FileInputStream("images/ships/" + shipName + ".png"));
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(70);
			imageView.setFitWidth(100);
			imageView.setPreserveRatio(true);
			imageView.fitWidthProperty().bind(v_box2.widthProperty());

			Image image2 = new Image(new FileInputStream("images/ships/" + shipName + "90.png"));
			Label shipNameLabel = new Label(shipName);
			shipNameLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 18));
			shipNameLabel.setTextFill(Color.web("#c40831"));
			v_box2.getChildren().addAll(shipNameLabel, imageView);

			imageView.setOnDragDetected(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
					System.out.println("drag detected");
					ClipboardContent content = new ClipboardContent();

					if (event.getButton() == MouseButton.PRIMARY) {
						content.putString("Primary;" + shipName);
						content.putImage(imageView.getImage());
						// content.putImage(image);
					} else if (event.getButton() == MouseButton.SECONDARY) {
						content.putString("Secondary;" + shipName);
						content.putImage(image2);
					}

					db.setContent(content);
					event.consume();

				}
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
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
			// ob.reInitialize();
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
		root1.setVgap(10);
		root1.setHgap(10);

		Image image = new Image(new FileInputStream("images/battleship.png"));

		BackgroundImage backgroundimage = new BackgroundImage(image, BackgroundRepeat.ROUND, BackgroundRepeat.ROUND,
				BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

		Background background = new Background(backgroundimage);

		root1.setBackground(background);

		MenuItem mI1 = new MenuItem("Salvo");
		MenuItem mI2 = new MenuItem("Classic");
		MenuButton menuButton = new MenuButton("Please select game type.");
		menuButton.getItems().addAll(mI1, mI2);

		mI1.setOnAction(event -> {
			gameType = "Salvo";
			menuButton.setText(gameType);
		});
		mI2.setOnAction(event -> {
			gameType = "Classic";
			menuButton.setText(gameType);
		});

		Button btn1 = new Button("Start New Game");
		btn1.setStyle("-fx-background-color: #a3a0a0; ");
		Button btn2 = new Button("Exit Game");
		btn2.setStyle("-fx-background-color: #a3a0a0; ");
		btn1.setOnAction((ActionEvent event) -> {
			if (gameType.equals("None")) {
				AlertBox.displayError("Start up error", "Please select game type(salvo or classic).");
			} else {
				if (gameType.equals("Classic"))
					gameMode = AlertBox.displayDifficulty();
				stg.setScene(scene1);
			}
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
		resultLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 18));
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
		resultLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 18));
		resultLabel.setTextFill(Color.web("#c40831"));
		resulttext2.setStyle("-fx-background-color: white;");
		v_box3.getChildren().addAll(resultLabel, resulttext2);
	}

	/**
	 * Places the Score of the user on the screen
	 * 
	 */
	public void Score(String title) {
		Label resultLabel = new Label(title + ": ");
		resultLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 18));
		resultLabel.setTextFill(Color.web("#c40831"));
		resulttext3 = new Label();

		resulttext3.setStyle("-fx-background-color: white;");
		// v_box3.getChildren().addAll(resultLabel, resulttext3);
		h_box1.getChildren().addAll(resultLabel, resulttext3);

	}

	/**
	 * Places the Score of the Computer on the screen
	 * 
	 */
	public void ScoreComp(String title) {
		Label resultLabel = new Label(title + ": ");
		resultLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 18));
		resultLabel.setTextFill(Color.web("#c40831"));
		resulttext4 = new Label();
		resulttext4.setStyle("-fx-background-color: white;");
		h_box2.getChildren().addAll(resultLabel, resulttext4);

	}

	/**
	 * Method to call the alert box for salvo variation in the starting
	 * 
	 * @param alert
	 */
	public static void salvoAlertCall() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Salvo Mode");
		alert.setHeaderText("You are in Salvo mode.");
		alert.setContentText("The number of shots you take depends on the number of ships you have left.");
		alert.showAndWait();
	}

}
