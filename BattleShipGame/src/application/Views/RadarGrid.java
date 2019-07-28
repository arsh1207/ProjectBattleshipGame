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
import application.Models.Player;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;

public class RadarGrid implements Observer {

	int rowButtonCount;
	int columnButtonCount;
	public static int buttonCount = 0;
	int tempButtonCount;
	public Button[][] radarButton;
	public String[][] selectedButtons;
	//Computer computer;
	int coordX = 0;
	int coordY = 0;
	Label resulttext2, resulttext1, resulttext3, resulttext4;
	GridPane g_pane1;
	GridUser ob;

	Boolean lastCompResult = false;
	//private HitStrategy strategy;

	public RadarGrid(Label resulttext2, Label resulttext1, Label resulttext3, Label resulttext4,
			GridUser ob) {

		this.resulttext2 = resulttext2;
		this.resulttext1 = resulttext1;
		this.resulttext3 = resulttext3;
		this.resulttext4 = resulttext4;
		this.ob = ob;
		this.selectedButtons = new String[5][2];
	}

	/**
	 * Deploys the radar grid on the screen
	 */
	public void setUserRadarGrid(GridPane g_pane, Label resulttext2) {
		g_pane1 = g_pane;
		radarButton = new Button[9][11];
		this.resulttext2 = resulttext2;
		System.out.println("Setting radarGrid");
		double r = 12;
		int buttonRowIndex;
		Text t = new Text("Radar Grid");
		t.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		rowButtonCount = 0;
		columnButtonCount = 0;
		g_pane.add(t, columnButtonCount, rowButtonCount);
		for (rowButtonCount = 10; rowButtonCount >= 1; rowButtonCount--) {
			System.out.println(rowButtonCount + " " + columnButtonCount);
			int ch = 10;
			Text text1 = new Text(Integer.toString(ch - rowButtonCount));
			g_pane.add(text1, columnButtonCount, rowButtonCount);
		}
		// initializing the radar grid buttons of 9*11 size
		// so that they can be accessed via ID
		for (rowButtonCount = 0; rowButtonCount < 9; rowButtonCount++) {
			for (columnButtonCount = 0; columnButtonCount < 11; columnButtonCount++) {
				// System.out.println(rowButtonCount+" "+columnButtonCount);
				radarButton[rowButtonCount][columnButtonCount] = new Button();
			}
		}

		buttonRowIndex = 0;

		// placing the buttons or holes on the grid
		for (rowButtonCount = 9; rowButtonCount >= 1; rowButtonCount -= 1) {
			columnButtonCount = 1;
			// columnButtonCount = 0; columnButtonCount < 11; columnButtonCount += 1
			for (Button b : radarButton[buttonRowIndex]) {
				System.out.println(rowButtonCount + " " + columnButtonCount);
				System.out.println(rowButtonCount + " " + columnButtonCount);
				b.setStyle("-fx-background-color: #000000; ");
				b.setId((buttonRowIndex) + ":" + (columnButtonCount - 1));
				b.setDisable(true);
				b.setShape(new Circle(r));
				b.setMinSize(2 * r, 2 * r);
				b.setMaxSize(2 * r, 2 * r);
				b.setOnAction((ActionEvent event) -> {
					//to initially change the targets to white color
					b.setStyle("-fx-background-color: #FFFFFF; ");
					if(Main.gameType.equals("Salvo")) {
						String xy[] = b.getId().split(":");
						salvaFunc(xy);	
					}
					else {
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
		// placing the letters on the grid
		for (columnButtonCount = 1; columnButtonCount < 12; columnButtonCount += 1) {
			char ch = (char) ('A' + columnButtonCount - 1);
			Text text1 = new Text(Character.toString(ch));
			g_pane.add(text1, columnButtonCount, rowButtonCount);
		}
	}

	public void disableButtons(int i, int j) {

		radarButton[i][j].setDisable(false);

	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Computer) {
			System.out.println("update called");
			// TODO Auto-generated method stub

			if (arg.equals("HITORMISS")) {
				
				String res = ((Computer) o).getReply();
				int score1 = ((Computer) o).getScoreComp();
				resulttext3.setText("" + score1);
				afterCompReply(res, (Computer) o);
				
			}
		} else {
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

		}

	}

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
			
			if(Main.gameType.equals("Salvo")) {
				if(buttonCount == 0) {
					//call the method for getting sunken ships here through controller
					ob.callSunkenShips(o);
					//call the method for getting sunken ships from computer model here
					sunkenShips = o.getSunkenShips();
					salvaAlertCall(sunkenShips);
					ob.callCheckIfUserWon();
					ob.computerTurn(lastCompResult, Main.gameMode);
				}
			}
			else {
				ob.callCheckIfUserWon();
				ob.computerTurn(lastCompResult, Main.gameMode);
			}

		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to keep storing the salvos of the user in every round
	 * @param xy coordinates
	 */
	public void salvaFunc(String[] xy) {
		System.out.println("Number of buttons"+buttonCount);
		if(buttonCount >= Player.shipsMap.size() - 1) {
			for(buttonCount = Player.shipsMap.size() - 1;buttonCount>=0; buttonCount--) {
				coordX = Integer.parseInt(selectedButtons[buttonCount][0]);
				coordY = Integer.parseInt(selectedButtons[buttonCount][1]);
				ob.callUserTurn(coordX, coordY);
			}
			buttonCount = 0;
		}
		else {
			selectedButtons[buttonCount][0] = xy[0];
			selectedButtons[buttonCount][1] = xy[1];
			System.out.println("Selected buttons are "+selectedButtons[buttonCount][0]+" and "+selectedButtons[buttonCount][1]);
			buttonCount++;
			if(buttonCount == 5) {
				salvaFunc(null);
			}
		}
	}
	
	/**
	 * Method to display the enemy ships that have sunk in the latest round
	 * @param sunkenShips list of sunken ships
	 */
	public static void salvaAlertCall(ArrayList<String> sunkenShips) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Salva Mode");
		if(sunkenShips.isEmpty()) {
			alert.setHeaderText("Number of Comp ships Hit: 0");
			alert.setContentText("No ships sunk this time");
		}
		else {
			String ships = new String();
			alert.setHeaderText("Number of Comp ships Hit: "+sunkenShips.size());
			ships = "Ships destroyed:\n";
			for(int i = 0; i < sunkenShips.size(); i++) {
				ships = ships + sunkenShips.get(i)+"\n";
			}
			alert.setContentText(ships);
		}
		alert.showAndWait();
	}
}
