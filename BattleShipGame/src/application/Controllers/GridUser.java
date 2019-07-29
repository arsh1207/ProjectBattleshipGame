package application.Controllers;

import application.Models.Computer;
import application.Models.HitStrategy;
import application.Models.HitStrategySalvo;
import application.Models.Player;
import main.Main;

/**
 * 
 * Class provides the AI functionality of the Computer and the User
 * 
 * @author Prateek
 * 
 *
 */
public class GridUser {

	Computer computer;
	Player player;
	HitStrategy strategy;
	HitStrategySalvo strategySalvo;
	String callType = "";

	public String getCallType() {
		return callType;
	}

	public void setCallType(String callType) {
		this.callType = callType;
	}

	/**
	 * Constructor to initialize the required object
	 * @param player Player model object
	 * @param computer Computer model object
	 * @param strategy HitStrategy model object
	 * @param strategySalvo HitStrategySalvo object 
	 */
	public GridUser(Player player, Computer computer, HitStrategy strategy, HitStrategySalvo strategySalvo) {
		this.computer = computer;
		this.player = player;
		this.strategy = strategy;
		this.strategySalvo = strategySalvo;

	}

	/**
	 * Provides if its a hit or miss while hitting on the computer grid
	 * 
	 * @return String defining the computer turn results
	 */
	public void computerTurn(Boolean hitResult, String gameMode) {
		if (Main.gameType.equals("Salvo")) {
			
			setCallType("Salvo");
			strategySalvo.mediumMode(hitResult);

		} else {

			// set call type for junit
			setCallType("Normal");

			if (gameMode.equals("Easy"))
				strategy.randomHit();
			else if (gameMode.equals("Medium"))
				strategy.mediumMode(hitResult);
			else
				strategy.hardMode(hitResult);
		}

	}

	/**
	 * 
	 * checks the grid of the computer to verify if they won or not sets the static
	 * flag true if someone wins
	 */
	public void callCheckIfUserWon() {
		computer.checkIfUserWon();
	}
	
	/**
	 * to call the method in the model to deploy the computer ships
	 */
	public void deployCompShips() {
		computer.deployComputerShips();
	}
	
	/**
	 * to call the feeling lazy functionality in the model
	 */
	public void deployUserShips() {
		player.deployUserRandomShips();
	}

	/**
	 * 
	 * checks the grid of the User to verify if user has won or not displays the Win
	 * case if User won
	 * 
	 */
	public void callCheckIfCompWon() {
		player.checkIfCompWon();

	}

	/**
	 * This method will user turn in the model to check whether the coordinates are
	 * hit or not
	 * 
	 * @param computer Computer model object
	 * @param coordX x-coordinate
	 * @param coordY y-coordinate
	 */
	public void callUserTurn(int coordX, int coordY) {
		computer.userTurn(coordX, coordY);

	}

	/**
	 * This method will tell the computer model to deploy its ships in the backend
	 * 
	 * @param computer Computer model object
	 */
	public void callDeployComputerShips(Computer computer) {
		computer.deployComputerShips();
	}
	
	/**
	 * method to set the user grid in the model as per the coordinates from the view
	 * @param coordinates coordinates on the grid
	 * @param shipType type of the ship
	 */
	public void callDeployUserGrid(String coordinates, String shipType) {
		player.deployUserGrid(coordinates, shipType);
	}
	
	/**
	 * method to call the method to check the sunken ships
	 * @param computer computer object
	 */
	public void callSunkenShips(Computer computer) {
		computer.checkSunkenShips();
	}

}
