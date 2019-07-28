package application.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import application.Views.AlertBox;

public class Computer extends Observable {

	final int rows = 9;
	final int cols = 11;
	boolean time1=false,time2=false;
	double  timea=0;
	double timeb=0;
	Random rand = new Random();
	//to check if all ships have been placed or not 
	int counter = 0;
	public Integer[][] computerGrid = new Integer[rows][cols];
	

	public static Map<String, ArrayList<String>> shipsMap = new HashMap<>();
	static ArrayList<String> tempList = new ArrayList<String>();
	static ArrayList<String> sunkenShips = new ArrayList<String>();
	static ArrayList<String> coordinatesHit = new ArrayList<String>();
	private int scoringComp=0;
	private String reply = "";

	// public Integer[][] changedComputerGrid = new Integer[rows][cols];
	
	public Integer[][] getComputerGrid() {
		return computerGrid;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
		setChanged();
		notifyObservers("HITORMISS");
	}

	public void setScoreComp(int s) {
		scoringComp = scoringComp + s;
	}

	public int getScoreComp() {
		return scoringComp;
	}

	public void setSunkenShips(String shipType) {
		sunkenShips.add(shipType);
	}

	public void setCounter(int counter) {
		
		this.counter=counter;

	}


	public int  getCounter() {
		return this.counter;

	}
	public ArrayList<String> getSunkenShips() {

		return sunkenShips;

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
		String coordx = Integer.toString(x);
		String coordy = Integer.toString(y);
		if (computerGrid[x][y] == 1) {
			// change the grid value from 1 to 2 to signify hit

			computerGrid[x][y] = 2;

			setScoreComp(5);
			System.out.println("Hit");
			coordinatesHit.add(coordx + "," + coordy);
			setReply("It's a Hit!!!!!");

			// return "It's a Hit!!!!!";

			// incrementing computer score for a successful hit

		} else if (computerGrid[x][y] == 0) {
			setScoreComp(-2);
			setReply("It's a miss!!!!!");
			// return "It's a miss!!!!!";

			// loosing points for a miss

		} else if (computerGrid[x][y] == 2) {

			setReply("The location has been hit earlier");
			// return "The location has been hit earlier";

		}

		else {

			setReply("Some other error");
		}
		// some other case or error
		// return "Some other error";

	}

	/**
	 * This method places computer ships randomly
	 * 
	 * @author arsalaan
	 */
	public void deployComputerShips() {

		try {

			int carrierX = randomX();
			int carrierY = randomY();

			HashMap<Integer, Integer> Carrier = new HashMap<>();
			Boolean placed = false;

			while (!placed) {

				if (check(carrierX, carrierY, "horizontal", 5)) {

					if ((carrierY + 5) <= 11) {

						for (int i = 0; i < 5; i++) {
							Carrier.put((carrierY + i), carrierX);
							putIntoShipsMap("Carrier", carrierY + i, carrierX, "horizontal");
						}
					} else {
						for (int i = 0; i < 5; i++) {
							Carrier.put((carrierY - i), carrierX);
							putIntoShipsMap("Carrier", carrierY - i, carrierX, "horizontal");
						}
					}
					placed = true;
					counter++;
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

			tempList = new ArrayList<String>();

			placed = false;
			while (!placed) {
				if (check(battleShipX, battleShipY, "vertical", 4)) {
					if ((battleShipX + 4) < 9) {

						for (int i = 0; i < 4; i++) {
							BattleShip.put((battleShipX + i), battleShipY);
							putIntoShipsMap("Battleship", battleShipX + i, battleShipY, "vertical");
						}
					} else {
						for (int i = 0; i < 4; i++) {
							BattleShip.put((battleShipX - i), battleShipY);
							putIntoShipsMap("Battleship", battleShipX - i, battleShipY, "vertical");
						}
					}
					placed = true;
					counter++;
				} else {
					battleShipX = rand.nextInt(9);
					battleShipY = rand.nextInt(11);
				}
			}

			for (Map.Entry<Integer, Integer> entry : BattleShip.entrySet()) {

				computerGrid[entry.getKey()][entry.getValue()] = 1;

			}

			int cruiserX = rand.nextInt(9);
			int cruiserY = rand.nextInt(11);

			HashMap<Integer, Integer> Cruiser = new HashMap<>();
			tempList = new ArrayList<String>();
			placed = false;
			while (!placed) {
				if (check(cruiserX, cruiserY, "vertical", 3)) {
					if ((cruiserX + 3) < 9) {

						for (int i = 0; i < 3; i++) {
							Cruiser.put((cruiserX + i), cruiserY);
							putIntoShipsMap("Cruiser", cruiserX + i, cruiserY, "vertical");
						}
					} else {
						for (int i = 0; i < 3; i++) {
							Cruiser.put((cruiserX - i), cruiserY);
							putIntoShipsMap("Cruiser", cruiserX - i, cruiserY, "vertical");
						}
					}
					placed = true;
					counter++;
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
			tempList = new ArrayList<String>();

			placed = false;
			while (!placed) {
				if (check(subX, subY, "vertical", 3)) {
					if ((subX + 3) < 9) {

						for (int i = 0; i < 3; i++) {
							Submarine.put((subX + i), subY);
							putIntoShipsMap("Submarine", subX + i, subY, "vertical");
						}
					} else {
						for (int i = 0; i < 3; i++) {
							Submarine.put((subX - i), subY);
							putIntoShipsMap("Submarine", subX - i, subY, "vertical");
						}
					}
					placed = true;
					counter++;
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
			tempList = new ArrayList<String>();
			placed = false;
			while (!placed) {
				if (check(destroyerX, destroyerY, "horizontal", 2)) {
					if ((destroyerY + 2) < 11) {

						for (int i = 0; i < 2; i++) {
							Destroyer.put((destroyerY + i), destroyerX);
							putIntoShipsMap("Destroyer", destroyerY + i, destroyerX, "horizontal");
						}
					} else {
						for (int i = 0; i < 2; i++) {
							Destroyer.put((destroyerY - i), destroyerX);
							putIntoShipsMap("Destroyer", destroyerY - i, destroyerX, "horizontal");
						}
					}
					placed = true;
					counter++;
					
					setCounter(counter);
				} else {
					destroyerX = rand.nextInt(9);
					destroyerY = rand.nextInt(11);
				}
			}

			for (Map.Entry<Integer, Integer> entry : Destroyer.entrySet()) {

				// ask about this
				computerGrid[entry.getKey()][entry.getValue()] = 1;
				// computerGrid[entry.getValue()][entry.getKey()] = 1;

			}

			// set the computerGrid to be changed grid
			// changedComputerGrid = computerGrid;

			printGrid();
		} catch (Exception e) {
			e.printStackTrace();

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

	/**
	 * The method puts the values in ships and coordinates hashmap
	 * 
	 * @param shipType
	 * @param cY       coordinate X
	 * @param cX       coordinate Y
	 */
	public void putIntoShipsMap(String shipType, int cY, int cX, String direction) {
		String coordy = new String();
		String coordx = new String();
		if (direction.equals("horizontal")) {
			coordy = Integer.toString(cX);
			coordx = Integer.toString(cY);
		} else {
			coordy = Integer.toString(cY);
			coordx = Integer.toString(cX);
		}
		tempList.add(coordy + "," + coordx);
		shipsMap.put(shipType, tempList);

	}

	/**
	 * This function checks whether any ships have sunk or not
	 * 
	 * @param coordX X-coordinate
	 * @param coordY Y-coordinate
	 */
	public void checkSunkenShips() {

		System.out.print("checkSunkenShips called\n");
		Map<String, ArrayList<String>> tempMap;
		for (String coords : coordinatesHit) {
			System.out.println("Checking coordinates " + coords);
			tempMap = new HashMap<>();
			tempMap.putAll(shipsMap);
			for (Map.Entry<String, ArrayList<String>> entry : shipsMap.entrySet()) {
				System.out.println("Checking " + entry.getKey());
				System.out.println(entry);
				if (!shipsMap.get(entry.getKey()).isEmpty()) {
					// if any ship has been placed on the assigned coordinate
					if (shipsMap.get(entry.getKey()).contains(coords)) {
						tempMap.get(entry.getKey()).remove(coords);

						// if no coordinates are remaining to be hit then add the ship to sunken ships
						// and remove the ships from the shipsMap
						if (shipsMap.get(entry.getKey()).isEmpty()) {
							setSunkenShips(entry.getKey());
							System.out.println(entry.getKey() + " destroyed");
							tempMap.remove(entry.getKey());
							System.out.println(entry.getKey() + " removed");
						}
					}

				}

			}
			shipsMap = new HashMap<>();
			shipsMap.putAll(tempMap);
		}

	}

	public int randomX() {

		return rand.nextInt(9);
	}

	public int randomY() {

		return rand.nextInt(11);

	}

}
