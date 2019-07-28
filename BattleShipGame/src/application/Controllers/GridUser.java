package application.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import application.Models.*;
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

	/**
	 * Constructor used for initializing the required objects
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
		if(Main.gameType.equals("Salvo")) {
			if (gameMode.equals("Easy"))
				strategySalvo.randomHit();
			else if (gameMode.equals("Medium"))
				strategySalvo.mediumMode(hitResult);
			else
				strategySalvo.hardMode(hitResult);
		}
		else {
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
	
	public void deployCompShips() {
		computer.deployComputerShips();
	}

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
	 * @param computer
	 * @param coordX
	 * @param coordY
	 */
	public void callUserTurn(int coordX, int coordY) {
		computer.userTurn(coordX, coordY);

	}

	/**
	 * This method will tell the computer model to deploy its ships in the backend
	 * 
	 * @param computer
	 */
	public void callDeployComputerShips(Computer computer) {
		computer.deployComputerShips();
	}
	
	public void callDeployUserGrid(String coordinates, String shipType) {
		player.deployUserGrid(coordinates, shipType);
	}
	
	public void callSunkenShips(Computer computer) {
		computer.checkSunkenShips();
	}

}
