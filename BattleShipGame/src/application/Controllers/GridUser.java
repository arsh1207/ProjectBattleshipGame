package application.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import application.Views.AlertBox;
import application.Views.Battleship_Grid_Pane;

/**
 * 
 * Class provides the AI functionality of the Computer and the User
 * 
 * @author Prateek
 * 
 *
 */
public class GridUser {
	Battleship_Grid_Pane shipObject;

	public static int numOfShipsDep = 0;

	public static int rows = 9;
	public static int cols = 11;

	// original Grid that remains unchanged throughout the game
	public static Integer[][] userGrid = new Integer[rows][cols];

	public static Integer[][] computerGrid = new Integer[rows][cols];

	// below are the grids that will be changed during the game
	public static Integer[][] changedUserGrid = new Integer[rows][cols];

	public static Integer[][] changedComputerGrid = new Integer[rows][cols];

	static List<String> deployedShips = new ArrayList<>();

	public static HashMap<String, Integer> convert = new HashMap<>();

	/**
	 * Constructor used for initializing the required objects
	 */
	public GridUser() {

		// creating the object to deploy colored ships
		shipObject = new Battleship_Grid_Pane();
		// assign the values to the grids
		initialize();

		// deploy all the computer ships
		deployComputerShips();

		// set the hashMap for conversion
		convert.put("A", 0);
		convert.put("B", 1);
		convert.put("C", 2);
		convert.put("D", 3);
		convert.put("E", 4);
		convert.put("F", 5);
		convert.put("G", 6);
		convert.put("H", 7);
		convert.put("I", 8);
		convert.put("J", 9);
		convert.put("K", 10);

		convert.put("a", 0);
		convert.put("b", 1);
		convert.put("c", 2);
		convert.put("d", 3);
		convert.put("e", 4);
		convert.put("f", 5);
		convert.put("g", 6);
		convert.put("h", 7);
		convert.put("i", 8);
		convert.put("j", 9);
		convert.put("k", 10);

	}

	/**
	 * 
	 * @param coordinates defines the coordinated to be deployed
	 * @param shipType    type of the ship
	 * @return strings defining the placement of the ship in grid
	 */
	public String deployUserGrid(String coordinates, String shipType) {

		try {
			String str[] = coordinates.split("\\s");

			int x1 = convert.get(str[0]);
			// System.out.println(x1);
			// decrease the value of Y since the coordinated start from 0 in grid
			int y1 = Integer.parseInt(str[1]) - 1;
			int x2 = convert.get(str[2]);

			// decrease the value of Y since the coordinated start from 0 in grid
			int y2 = Integer.parseInt(str[3]) - 1;

			System.out.println("values x1 y1 x2 y2  " + x1 + y1 + x2 + y2);
			
			//ships cannot be placed outside the grid
			if(x1 >= cols || y1 >= rows || x2 >= cols || y2 >= rows) {
				
				deployedShips.remove(shipType);
				return "You can't place ships outside the " + rows + " by " + cols + " grid";
				
			}
			
			//coordinates cannot be negative
			if(x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {
				
				deployedShips.remove(shipType);
				return "You can't place ships outside the " + rows + " by " + cols + " grid";
				
			}
			

			if (x1 == x2) {
				
				System.out.println("X coordinates are same");
				// if two X are the same then the line is vertical
				// increment the Y values to set the ship location
				// count to check if all coordinated were placed successfully
				int count = 0;
				if (y1 > y2) {
					int temp = y2;
					y2 = y1;
					y1 = temp;
				}
				for (int i = y1; i <= y2; i++) {
					
					if (userGrid[i][x1] == 1) {
						displayUserShips();
						deployedShips.remove(shipType);
						return "ships cannot be placed on the same location";
					}
					
				}
				String res = areHolesValid(y2 - y1 + 1, shipType);
				if (res.equals("YES")) {

					for (int i = y1; i <= y2; i++) {

						if ((x1 >= 0 && x1 < cols) && (y1 >= 0 && y1 < rows) && (y2 >= 0 && y2 < rows)
								&& (userGrid[i][x1] == 0)) {
							userGrid[i][x1] = 1;
							count++;

						}
					}

					if (((y2 + 1) - (y1 + 1)) + 1 == count) {
						int[] coords = { x1, y1, x2, y2 };
						// calling the function on front end to color the
						// coordinates of the ship as required
						shipObject.deployShipsWithColors(coords, shipType, "Y");

						// result done signifies everything went right
						changedUserGrid = userGrid;
						numOfShipsDep++;
						return "Done";
					}
				} else {
					deployedShips.remove(shipType);
					return res;
				}

			}

			else if (y1 == y2) {
				// System.out.println("Y coordinates are same");
				// if two Y are the same then the line is Horizontal

				// count to check if all coordinated were placed successfully
				int count = 0;
				if (x1 > x2) {
					int temp = x2;
					x2 = x1;
					x1 = temp;
				}
				for (int i = x1; i <= x2; i++) {
					
					if (userGrid[y1][i] == 1) {
						displayUserShips();
						deployedShips.remove(shipType);
						return "ships cannot be placed on the same location";
					}
				}
				String res = areHolesValid(x2 - x1 + 1, shipType);
				if (res.equals("YES")) {
					// increment the X values to set the ship location
					for (int i = x1; i <= x2; i++) {

						if ((y1 >= 0 && y1 < rows) && (x1 >= 0 && x1 < cols) && (x2 >= 0 && x2 < cols)
								&& (userGrid[y1][i] == 0)) {
							System.out.println("For coordinates " + y1 + " and " + i);
							userGrid[y1][i] = 1;
							count++;

						}

					}

					if (((x2 + 1) - (x1 + 1)) + 1 == count) {
						int[] coords = { x1, y1, x2, y2 };
						// calling the function on front end to color the
						// coordinates of the ship as required
						shipObject.deployShipsWithColors(coords, shipType, "X");
						changedUserGrid = userGrid;
						numOfShipsDep++;
						return "Done";
					}

				} else {
					deployedShips.remove(shipType);
					return res;
				}
			}

			else {// case when anything Diagonal
				deployedShips.remove(shipType);
				return "Can not place ship Diagonal";

			}

			deployedShips.remove(shipType);
			// signifies some other error
			return "Invalid input, please try again.";
		} catch (Exception e) {
			deployedShips.remove(shipType);
			// e.printStackTrace();
			return "Invalid input, please try again.";
		}
	}

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
			changedComputerGrid = computerGrid;

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
	 * Takes the input based on the event listener Provides the hit or miss while
	 * hitting on the computer grid
	 * 
	 * @param x coordinate
	 * @param y coordinates
	 * @return String defining if the user hit or miss the computer grid
	 */

	public String userTurn(int x, int y) {

		// get the X and y coordinate from the input
		System.out.println("reached here" + x + "; " + y);
		if (changedComputerGrid[x][y] == 1) {
			// change the grid value from 1 to 2 to signify hit

			changedComputerGrid[x][y] = 2;

			return "It's a Hit!!!!!";
		} else if (changedComputerGrid[x][y] == 0) {

			return "It's a miss!!!!!";

		} else if (changedComputerGrid[x][y] == 2) {

			return "The location has been hit earlier";

		}

		// some other case or error
		return "Some other error";

	}

	/**
	 * Provides if its a hit or miss while hitting on the computer grid
	 * 
	 * @return String defining the computer turn results
	 */
	public String computerTurn() {
		// get the X and y coordinate from the input
		Random ran = new Random();

		int x = ran.nextInt(9);

		int y = ran.nextInt(11);

		if (changedUserGrid[x][y] == 1) {
			// change the grid value from 1 to 2 to signify hit

			changedUserGrid[x][y] = 2;
			Battleship_Grid_Pane.setUserShipCoordinates(x, y, "Hit");

			return "It's a Hit!!!!!";

		} else if (changedUserGrid[x][y] == 0) {

			Battleship_Grid_Pane.setUserShipCoordinates(x, y, "Miss");
			return "It's a miss!!!!!";

		} else if (changedUserGrid[x][y] == 2) {

			return "The location has been hit earlier";

		}

		// some other case or error
		return " ";

	}

	/**
	 * 
	 * checks the grid of the computer to verify if they won or not sets the static
	 * flag true if someone wins
	 */

	public void checkIfUserWon() {

		boolean flaguser = false;

		// check the computer grid if all the 1 are converted to 2
		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				if (changedComputerGrid[i][j] == 1) {
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
	 * 
	 * checks the grid of the User to verify if user has won or not displays the Win
	 * case if User won
	 * 
	 */
	public void checkIfCompWon() {

		boolean flagcomp = false;

		// check the computer grid if all the 1 are converted to 2
		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				if (changedUserGrid[i][j] == 1) {
					// set the flag as true
					flagcomp = true;

				}

			}
		}

		if (!flagcomp) {// set that user has won

			AlertBox.displayResult("Hurray!!", "AI has Won ");
		} else {
			// do nothing

		}

	}

	/**
	 * Checking that the ships are of the exact size
	 * 
	 * 
	 * @param diff     size of the ship
	 * @param shipType type of the ship
	 * @return returns String defining if the ships have the correct size
	 */
	public String areHolesValid(int diff, String shipType) {
		if (shipType.equals("Carrier")) {
			if (diff == 5)
				return "YES";
			else
				return "Carriers can only have 5 holes";
		}
		if (shipType.equals("Battleship")) {
			if (diff == 4)
				return "YES";
			else
				return "Battleships can only have 4 holes";
		}
		if (shipType.equals("Cruiser")) {
			if (diff == 3)
				return "YES";
			else
				return "Cruisers can only have 3 holes";
		}
		if (shipType.equals("Submarine")) {
			if (diff == 3)
				return "YES";
			else
				return "Submarines can only have 3 holes";
		}
		if (shipType.equals("Destroyer")) {
			if (diff == 2)
				return "YES";
			else
				return "Destroyers can only have 2 holes";
		}
		return null;
	}

	/**
	 * function to see whether a particular ship is deployed or not
	 * 
	 * @param shipType gives the type of the ship
	 * @return boolean to check the ship deployed
	 */
	public boolean isShipDeployed(String shipType) {

		if (deployedShips.contains(shipType))
			return true;
		else if (deployedShips.isEmpty()) {
			deployedShips.add(shipType);
			return false;
		} else
			deployedShips.add(shipType);
		return false;
	}

	/**
	 * check if all the ships have been deployed or not
	 * 
	 * @return boolean true if all the ships deployed
	 */
	public boolean areAllShipsDeployed() {
		if (deployedShips.size() == 5)
			return true;
		else
			return false;
	}

	/**
	 * Initialize all the grids to zero
	 * 
	 */
	public void initialize() {

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				userGrid[i][j] = 0;
				computerGrid[i][j] = 0;
				changedUserGrid[i][j] = 0;
				changedComputerGrid[i][j] = 0;

			}
		}

	}

	/**
	 * 
	 * Reinitialize the required variable in case of starting a new Game
	 * 
	 */
	public void reInitialize() {
		initialize();

		deployComputerShips();

		numOfShipsDep = 0;
		deployedShips = new ArrayList<>();

		// set the hashMap for conversion
		convert.put("A", 0);
		convert.put("B", 1);
		convert.put("C", 2);
		convert.put("D", 3);
		convert.put("E", 4);
		convert.put("F", 5);
		convert.put("G", 6);
		convert.put("H", 7);
		convert.put("I", 8);
		convert.put("J", 9);
		convert.put("K", 10);

		convert.put("a", 0);
		convert.put("b", 1);
		convert.put("c", 2);
		convert.put("d", 3);
		convert.put("e", 4);
		convert.put("f", 5);
		convert.put("g", 6);
		convert.put("h", 7);
		convert.put("i", 8);
		convert.put("j", 9);
		convert.put("k", 10);

	}

	/**
	 * method to display user ship
	 */

	public static void displayUserShips() {

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 11; y++)
				System.out.print(userGrid[x][y] + " ");
			System.out.println("");
		}

	}

	/**
	 * Method to display the computer grid
	 */

	public void printGrid() {

		System.out.println("inside the printGrid");

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				System.out.print(changedComputerGrid[i][j] + " ");

			}
			System.out.println();
		}

	}

}
