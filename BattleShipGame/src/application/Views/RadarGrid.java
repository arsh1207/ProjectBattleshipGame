package application.Views;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.Main;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import application.Controllers.GridUser;
import application.Models.Computer;
import application.Models.HitStrategy;
import application.Models.HitStrategySalvo;
import application.Models.Player;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;

/**
 * This class is a view for computer grid and renders Computer.java model class 
 *
 */
public class RadarGrid implements Observer {

	int rowButtonCount;
	int columnButtonCount;
	public static int buttonCount = 0;
	int tempButtonCount;
	public Button[][] radarButton;
	public String[][] selectedButtons;
	int coordX = 0;
	int coordY = 0;
	Label resulttext2, resulttext1, resulttext3, resulttext4;
	GridPane g_pane1;
	GridUser ob;
	String userWon = "";

	public String getUserWon() {
		return userWon;
	}

	public void setUserWon(String userWon) {
		this.userWon = userWon;
	}

	public static Boolean lastCompResult = false;

	/**
	 * It is the parameterized class constructor
	 * 
	 * @param resulttext2 It is a label for setting computer result on scene
	 * @param resulttext1 It is a label for setting User result on scene
	 * @param resulttext3 It is a label for setting User Score on scene
	 * @param resulttext4 It is a label for setting computer score on scene
	 * @param ob          It is controller object
	 */
	public RadarGrid(Label resulttext2, Label resulttext1, Label resulttext3, Label resulttext4, GridUser ob) {

		this.resulttext2 = resulttext2;
		this.resulttext1 = resulttext1;
		this.resulttext3 = resulttext3;
		this.resulttext4 = resulttext4;
		this.ob = ob;
		this.selectedButtons = new String[5][2];

	}

	/**
	 * This method adds action listener to the button toss. It calculates the winner
	 * of the toss as per user choice and allows the user to play.
	 * 
	 */
	public void addTossAction() {
		Main.tossBtn.setOnAction((ActionEvent event) -> {
			if (Player.numOfShipsDep == 5) {

				for (int i = 0; i < 9; i++) {
					for (int j = 0; j < 11; j++) {
						radarButton[i][j].setDisable(false);
					}
				}
				ob.deployCompShips();
				String tossResult = InputBox.display("Please choose Head or Tail");
				int r = (int) Math.round(Math.random());
				if ((r == 1 && tossResult.equalsIgnoreCase("Head"))
						|| (r == 0 && tossResult.equalsIgnoreCase("Tail"))) {
					if (Main.gameType.equals("Salvo")) {
						Main.salvoAlertCall();
					}
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Battleship - Toss Result");
					alert.setHeaderText("It's computer turn first.");
					alert.showAndWait();
					ob.computerTurn(lastCompResult, Main.gameMode);
				} else {
					if (Main.gameType.equals("Salvo")) {
						Main.salvoAlertCall();
					}
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Battleship Game");
					alert.setHeaderText("It's a " + tossResult);
					alert.setContentText("It's your turn first!");
					alert.showAndWait();
				}
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Battleship Game");
				alert.setHeaderText("The battle ships are not placed correctly.");
				alert.setContentText("Please place them before starting.");
				alert.showAndWait();

			}
		});
	}

	/**
	 * Deploys the radar grid on the screen
	 * 
	 * @param g_pane      This store the reference for radar grid pane.
	 * @param resulttext2 It is a label for setting computer result on scene
	 */
	public void setUserRadarGrid(GridPane g_pane, Label resulttext2) {
		g_pane1 = g_pane;
		radarButton = new Button[9][11];
		this.resulttext2 = resulttext2;
		double r = 12;
		int buttonRowIndex;
		Text t = new Text("Radar Grid");
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		rowButtonCount = 0;
		columnButtonCount = 0;
		g_pane.add(t, columnButtonCount, rowButtonCount);
		for (rowButtonCount = 10; rowButtonCount >= 1; rowButtonCount--) {
			int ch = 10;
			Text text1 = new Text(Integer.toString(ch - rowButtonCount));
			g_pane.add(text1, columnButtonCount, rowButtonCount);
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
					b.setStyle("-fx-background-color: #FFFFFF; ");
					if (Main.gameType.equals("Salvo")) {
						String xy[] = b.getId().split(":");
						salvaFunc(xy);
					} else {
						String xy[] = b.getId().split(":");
						coordX = Integer.parseInt(xy[0]);
						coordY = Integer.parseInt(xy[1]);
						ob.callUserTurn(coordX, coordY);
					}
				});
				g_pane.add(b, columnButtonCount, rowButtonCount);
				columnButtonCount++;
			}
			if (buttonRowIndex < 9) {
				buttonRowIndex++;
			}
		}
		rowButtonCount = 10;
		for (columnButtonCount = 1; columnButtonCount < 12; columnButtonCount += 1) {
			char ch = (char) ('A' + columnButtonCount - 1);
			Text text1 = new Text(Character.toString(ch));
			g_pane.add(text1, columnButtonCount, rowButtonCount);
		}
	}

	/**
	 * This class makes the radar grid visible on appropriate calling.
	 * 
	 * @param i Contains the x axis for the radar button
	 * @param j Contains the y axis for the radar button
	 */
	public void disableButtons(int i, int j) {
		radarButton[i][j].setDisable(false);
	}

	/**
	 * This method is overridden from base class and implements the logic when a
	 * notify is called at the observable classes
	 * 
	 * @param o   contains object reference from observable class
	 * @param arg contains any argument that is sent from the observable class
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Computer) {
			if (arg.equals("HITORMISS")) {

				String res = ((Computer) o).getReply();
				int score1 = ((Computer) o).getScoreComp();
				resulttext2.setText("");
				resulttext3.setText("" + score1);
				afterCompReply(res, (Computer) o);
			}
		} else if (o instanceof HitStrategy) {
			if (arg.equals("Won")) {
				setUserWon("Won");
			}
			String reply = ((HitStrategy) o).getReply();
			int score = ((HitStrategy) o).getScore();
			int coord[] = ((HitStrategy) o).getCoords();
			if (reply.contains("Hit")) {
				lastCompResult = true;
			} else {
				lastCompResult = false;
			}
			resulttext1.setText(reply);
			resulttext4.setText("" + score);
			ob.callCheckIfCompWon();

			if (getUserWon().equals("Won")) {
				AlertBox.displayResult("OOPS:( :(", "Computer Won ");
			}
		} else if (o instanceof HitStrategySalvo) {
			String reply = ((HitStrategySalvo) o).getReply();
			int score = ((HitStrategySalvo) o).getScore();
			int coord[] = ((HitStrategy) o).getCoords();
			if (reply.contains("Hit")) {
				lastCompResult = true;
			} else {
				lastCompResult = false;
			}
			resulttext1.setText(reply);
			resulttext4.setText("" + score);
		}
	}
	/**
	 * This class make the changes on the grid as per the response from the model class and calls controller to 
	 * check if user won and tell computer to take its turn.
	 * 
	 * @param res This parameter contains the response received after state change
	 * @param o This param contains object reference of the notifying class 
	 */
	public void afterCompReply(String res, Computer o) {
		try {
			ArrayList<String> sunkenShips = new ArrayList<>();
			Image image = new Image(new FileInputStream("images/blast.png"));
			ImageView imageView = new ImageView(image);
			imageView.setFitHeight(25);
			imageView.setFitWidth(25);
			FadeTransition ft = new FadeTransition(Duration.millis(3000), imageView);
			ft.setFromValue(1.0);
			ft.setToValue(0.0);
			ft.play();
			AudioClip audioClip = new AudioClip(Paths.get("Sounds/blast.wav").toUri().toString());
			if (res.equals("It's a Hit!!!!!")) {
				radarButton[coordX][coordY].setStyle("-fx-background-color: #FF0000; ");
				g_pane1.add(imageView, (coordY + 1), (9 - coordX));
				audioClip.play(100);
			} else if (res.equals("It's a miss!!!!!")) {
				radarButton[coordX][coordY].setStyle("-fx-background-color: #FFFFFF; ");
			}
			resulttext2.setText(res);
			if (Main.gameType.equals("Salvo")) {
				if (buttonCount == 0) {
					// call the method for getting sunken ships here through controller
					ob.callSunkenShips(o);
					// call the method for getting sunken ships from computer model here
					sunkenShips = o.getSunkenShips();
					salvaAlertCall(sunkenShips);
					ob.callCheckIfUserWon();
					if (getUserWon().equals("Won")) {
						AlertBox.displayResult("Hurray!!", "User has Won ");
					}
					ob.computerTurn(lastCompResult, Main.gameMode);
				}
			} else {
				ob.callCheckIfUserWon();
				if(((Computer) o).getUserWon().equals("Won")) {
					AlertBox.displayResult("Hurray!", "User has won ");
				}
				ob.computerTurn(lastCompResult, Main.gameMode);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Method to keep storing the salvos of the user in every round
	 * 
	 * @param xy coordinates
	 */
	public void salvaFunc(String[] xy) {
		int shipsremaining = Main.TOTAL_SHIPS - Player.sunkenShips.size();
		if (buttonCount < shipsremaining) {
			selectedButtons[buttonCount][0] = xy[0];
			selectedButtons[buttonCount][1] = xy[1];
			buttonCount++;
			if (buttonCount == Main.TOTAL_SHIPS - Player.sunkenShips.size())
				salvaFunc(null);
		} else {
			for (buttonCount = (Main.TOTAL_SHIPS - Player.sunkenShips.size()) - 1; buttonCount >= 0; buttonCount--) {
				coordX = Integer.parseInt(selectedButtons[buttonCount][0]);
				coordY = Integer.parseInt(selectedButtons[buttonCount][1]);
				ob.callUserTurn(coordX, coordY);
			}
			buttonCount = 0;
		}
	}

	/**
	 * Method to display the enemy ships that have sunk in the latest round
	 * 
	 * @param sunkenShips list of sunken ships
	 */
	public static void salvaAlertCall(ArrayList<String> sunkenShips) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Salva Mode");
		if (sunkenShips.isEmpty()) {
			alert.setHeaderText("Number of Comp ships Hit: 0");
			alert.setContentText("No ships sunk this time");
		} else {
			String ships = new String();
			alert.setHeaderText("Number of Comp ships Hit: " + sunkenShips.size());
			ships = "Ships destroyed:\n";
			for (int i = 0; i < sunkenShips.size(); i++) {
				ships = ships + sunkenShips.get(i) + "\n";
			}
			alert.setContentText(ships);
		}
		alert.showAndWait();
	}
}
