package application.Models;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import application.Views.AlertBox;


public class Computer extends Observable {

	final int rows = 9;
	final int cols = 11;

	public Integer[][] computerGrid = new Integer[rows][cols];
private int scoringComp=5;
	private String reply = "";

	// public Integer[][] changedComputerGrid = new Integer[rows][cols];

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
		setChanged();
		notifyObservers("HITORMISS");
	}
	
	public void setScoreComp(int s) {
		scoringComp=scoringComp+s;
	}
	public int getScoreComp() {
		return scoringComp;
	}

	
	

	public Computer() {

		initialize();

	}
	
	/**
	 * Takes the input based on the event listener Provides the hit or miss while
	 * hitting on the user grid
	 * 
	 * @param x coordinate
	 * @param y coordinates
	 * @return String defining if the user hit or miss the computer grid
	 */

	public void userTurn(int x, int y) {

		// get the X and y coordinate from the input
		System.out.println("reached here" + x + "; " + y);
		if (computerGrid[x][y] == 1) {
			// change the grid value from 1 to 2 to signify hit

			computerGrid[x][y] = 2;
			System.out.println("Hit");
			setScoreComp(5);
			//incrementing computer score for a hit
			setReply("It's a Hit!!!!!");
			
			//return "It's a Hit!!!!!";
			
			
		} else if (computerGrid[x][y] == 0) {
			setScoreComp(-2);
			//decrementing score for a miss
			setReply("It's a miss!!!!!");
			//return "It's a miss!!!!!";


		} else if (computerGrid[x][y] == 2) {

			
			setReply("The location has been hit earlier");
			//return "The location has been hit earlier";

		}
		
		else {
		
			setReply("Some other error");
		}
		// some other case or error
		//return "Some other error";

	}


	/**
	 * Provides if its a hit or miss while hitting on the computer grid
	 * 
	 * @return String defining the computer turn results
	 */
	

	/**
	 * This method places computer ships randomly
	 * 
	 * @author arsalaan
	 */
	public void deployComputerShips() {

		try {
			Random rand = new Random();
			int carrierX = rand.nextInt(9);
			int carrierY = rand.nextInt(11);

			HashMap<Integer, Integer> Carrier = new HashMap<>();
			Boolean placed = false;

			while (!placed) {
				if (check(carrierX, carrierY, "horizontal", 5)) {
					if ((carrierY + 5) <= 11) {

						for (int i = 0; i < 5; i++) {
							Carrier.put((carrierY + i), carrierX);
						}
					} else {
						for (int i = 0; i < 5; i++) {
							Carrier.put((carrierY - i), carrierX);
						}
					}
					placed = true;
				} else {
					carrierX = rand.nextInt(9);
					carrierY = rand.nextInt(11);
				}
			}

			for (Map.Entry<Integer, Integer> entry : Carrier.entrySet()) {
				computerGrid[entry.getValue()][entry.getKey()] = 1;
				// computerGrid[entry.getKey()][entry.getValue()] = 1;

			}

			int battleShipX = rand.nextInt(9);
			int battleShipY = rand.nextInt(11);

			HashMap<Integer, Integer> BattleShip = new HashMap<>();

			placed = false;
			while (!placed) {
				if (check(battleShipX, battleShipY, "vertical", 4)) {
					if ((battleShipX + 4) < 9) {

						for (int i = 0; i < 4; i++) {
							BattleShip.put((battleShipX + i), battleShipY);
						}
					} else {
						for (int i = 0; i < 4; i++) {
							BattleShip.put((battleShipX - i), battleShipY);
						}
					}
					placed = true;
				} else {
					battleShipX = rand.nextInt(9);
					battleShipY = rand.nextInt(11);
				}
			}

			for (Map.Entry<Integer, Integer> entry : BattleShip.entrySet()) {

				computerGrid[entry.getKey()][entry.getValue()] = 1;
				// computerGrid[entry.getValue()][entry.getKey()] = 1;

			}

			int cruiserX = rand.nextInt(9);
			int cruiserY = rand.nextInt(11);

			HashMap<Integer, Integer> Cruiser = new HashMap<>();
			placed = false;
			while (!placed) {
				if (check(cruiserX, cruiserY, "vertical", 3)) {
					if ((cruiserX + 3) < 9) {

						for (int i = 0; i < 3; i++) {
							Cruiser.put((cruiserX + i), cruiserY);
						}
					} else {
						for (int i = 0; i < 3; i++) {
							Cruiser.put((cruiserX - i), cruiserY);
						}
					}
					placed = true;
				} else {
					cruiserX = rand.nextInt(9);
					cruiserY = rand.nextInt(11);
				}
			}

			for (Map.Entry<Integer, Integer> entry : Cruiser.entrySet()) {

				computerGrid[entry.getKey()][entry.getValue()] = 1;
				// computerGrid[entry.getValue()][entry.getKey()] = 1;

			}

			int subX = rand.nextInt(9);
			int subY = rand.nextInt(11);
			HashMap<Integer, Integer> Submarine = new HashMap<>();

			placed = false;
			while (!placed) {
				if (check(subX, subY, "vertical", 3)) {
					if ((subX + 3) < 9) {

						for (int i = 0; i < 3; i++) {
							Submarine.put((subX + i), subY);
						}
					} else {
						for (int i = 0; i < 3; i++) {
							Submarine.put((subX - i), subY);
						}
					}
					placed = true;
				} else {
					subX = rand.nextInt(9);
					subY = rand.nextInt(11);
				}
			}
			for (Map.Entry<Integer, Integer> entry : Submarine.entrySet()) {

				computerGrid[entry.getKey()][entry.getValue()] = 1;
				// computerGrid[entry.getValue()][entry.getKey()] = 1;

			}

			int destroyerX = rand.nextInt(9);
			int destroyerY = rand.nextInt(11);

			HashMap<Integer, Integer> Destroyer = new HashMap<>();

			placed = false;
			while (!placed) {
				if (check(destroyerX, destroyerY, "horizontal", 2)) {
					if ((destroyerY + 2) < 11) {

						for (int i = 0; i < 2; i++) {
							Destroyer.put((destroyerY + i), destroyerX);
						}
					} else {
						for (int i = 0; i < 2; i++) {
							Destroyer.put((destroyerY - i), destroyerX);
						}
					}
					placed = true;
				} else {
					destroyerX = rand.nextInt(9);
					destroyerY = rand.nextInt(11);
				}
			}

			for (Map.Entry<Integer, Integer> entry : Destroyer.entrySet()) {

				computerGrid[entry.getValue()][entry.getKey()] = 1;

			}

			// set the computerGrid to be changed grid
			// changedComputerGrid = computerGrid;

			printGrid();
		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println(print);
		}

	}

	/**
	 * This method checks if a ship can be placed over the computer grid.
	 * 
	 * @author arsalaan
	 * 
	 * @param x         coordinate
	 * @param y         coordinate
	 * @param direction describes horizontal or vertical
	 * @param points    limit of movement
	 * @return canPlace tells if ship can be placed or not
	 */
	public Boolean check(int x, int y, String direction, int points) {
		Boolean canPlace = true;
		if (direction.equals("horizontal")) {
			if ((y + points) < 11) {
				for (int j = 0; j < points; j++) {
					if (computerGrid[x][y + j] != 0)
						canPlace = false;
				}
			} else {
				for (int j = 0; j < points; j++) {
					if (computerGrid[x][y - j] != 0)
						canPlace = false;
				}
			}

		} else {

			if ((x + points) < 9) {
				for (int j = 0; j < points; j++) {
					if (computerGrid[x + j][y] != 0)
						canPlace = false;
				}
			} else {
				for (int j = 0; j < points; j++) {
					if (computerGrid[x - j][y] != 0)
						canPlace = false;
				}
			}

		}

		return canPlace;

	}

	/**
	 * Initialize all the grids to zero
	 * 
	 */
	public void initialize() {

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				computerGrid[i][j] = 0;

			}
		}

	}
	
	public void checkIfUserWon() {

		boolean flaguser = false;

		// check the computer grid if all the 1 are converted to 2
		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				if (computerGrid[i][j] == 1) {
					// set the flag as true if there is still one present somewhere
					flaguser = true;

				}

			}
		}

		if (!flaguser) {// set that user has won

			AlertBox.displayResult("Hurray!!", "User has Won ");
		} else {
			// do nothing

		}

	}
	
	/**
	 * Method to display the computer grid
	 */

	public void printGrid() {

		System.out.println("inside the printGrid");

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				System.out.print(computerGrid[i][j] + " ");

			}
			System.out.println();
		}

	}

}
