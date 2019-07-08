package application;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Prateek
 * 
 *
 */
public class GridUser {
	Battleship_Grid_Pane shipObject;

	public static int rows = 9;
	public static int cols = 11;

	public static boolean Userwon = false;
	public static boolean Compwon = false;

	// original Grid that remains unchanged throughout the game
	public static Integer[][] userGrid = new Integer[rows][cols];

	public static Integer[][] computerGrid = new Integer[rows][cols];

	// below are the grids that will be changed during the game
	public static Integer[][] ChangedUserGrid = new Integer[rows][cols];

	public static Integer[][] ChangedComputerGrid = new Integer[rows][cols];

	public static HashMap<String, Integer> convert = new HashMap<>();

	GridUser() {
		
		//creating the object to deploy colored ships
		shipObject = new Battleship_Grid_Pane();
		// assign the values to the grids
		Initialize();
		
		// deploy all the computer ships
		DeployComputerShips();

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

	}

	public String DeployUserGrid(String coordinates, String shipType) {

		String str[] = coordinates.split("\\s");

		int x1 = convert.get(str[0]);
		System.out.println(x1);
		// decrease the value of Y since the coordinated start from 0 in grid
		int y1 = Integer.parseInt(str[1]) - 1;
		int x2 = convert.get(str[2]);

		// decrease the value of Y since the coordinated start from 0 in grid
		int y2 = Integer.parseInt(str[3]) - 1;

		System.out.println("values x1 y1 x2 y2  " + x1 + y1 + x2 + y2);

		if (x1 == x2) {
			// if two X are the same then the line is vertical
			// increment the Y values to set the ship location

			// count to check if all coordinated were placed successfully
			int count = 0;

			for (int i = y1; i <= y2; i++) {

				if ((x1 >= 0 && x1 < rows) && (y1 >= 0 && y1 < cols) && (y2 >= 0 && y2 < cols)
						&& (userGrid[x1][i] == 0)) {
					userGrid[x1][i] = 1;
					count++;

				} else if ((x1 >= 0 && x1 < rows) && (y1 >= 0 && y1 < cols) && (y2 >= 0 && y2 < cols)
						&& userGrid[x1][i] == 1) {
					return "ships cannot be placed on the same location";
					//sagar change
				} else if ((y1 < 0 || y1 >= rows) || (y2 < 0 || y2 >= rows) || (x1 < 0 || x1 >= cols)) {
					return "You can't place ships outside the " + rows + " by " + cols + " grid";
				}

			}

			if (((y2 + 1) - (y1 + 1))+1 == count) {
				int[] coords = {x1, y1, x2, y2};
				//calling the function on front end to color the
				//coordinates of the ship as required
				shipObject.deployShipsWithColors(coords, shipType, "Y");
				
				//result done signifies everything went right
				
				return "Done";
			}

		}

		else if (y1 == y2) {
			// if two Y are the same then the line is Horizontal

			// count to check if all coordinated were placed successfully
			int count = 0;

			// increment the X values to set the ship location
			for (int i = x1; i <= x2; i++) {
				if ((y1 >= 0 && y1 < cols) && (x1 >= 0 && x1 < rows) && (x2 >= 0 && x2 < rows)
						&& (userGrid[i][y1] == 0)) {
					userGrid[i][y1] = 1;

				} else if ((y1 >= 0 && y1 < cols) && (x1 >= 0 && x1 < rows) && (x2 >= 0 && x2 < rows)
						&& userGrid[i][y1] == 1) {
					return "ships cannot be placed on the same location";
					//sagar change
				} else if ((x1 < 0 || x1 >= cols) || (x2 < 0 || x2 >= cols) || (y1 < 0 || y1 >= rows))
					return "You can't place ships outside the " + rows + " by " + cols + " grid";

			}

			if ((x2 + 1) - (x1 + 1) == count) {
				int[] coords = {x1, y1, x2, y2};
				//calling the function on front end to color the
				//coordinates of the ship as required
				shipObject.deployShipsWithColors(coords, shipType, "X");

				return "Done";
			}

		}

		else {// case when anything Diagonal
			return "Can not place ship Diagonal";

		}

		//  signifies some other error
		return "Some other error";

	}

	public void DeployComputerShips() {

		HashMap<Integer, Integer> Carrier = new HashMap<>();

		Carrier.put(0, 7);
		Carrier.put(1, 7);
		Carrier.put(2, 7);
		Carrier.put(3, 7);
		Carrier.put(4, 7);

		HashMap<Integer, Integer> BattleShip = new HashMap<>();
		BattleShip.put(0, 0);
		BattleShip.put(0, 1);
		BattleShip.put(0, 2);
		BattleShip.put(0, 3);
		HashMap<Integer, Integer> Cruiser = new HashMap<>();
		Cruiser.put(1, 2);
		Cruiser.put(2, 2);
		Cruiser.put(3, 2);

		HashMap<Integer, Integer> Submarine = new HashMap<>();
		Submarine.put(5, 4);
		Submarine.put(5, 5);
		Submarine.put(5, 6);
		HashMap<Integer, Integer> Destroyer = new HashMap<>();
		Destroyer.put(8, 5);
		Destroyer.put(8, 6);

		for (Map.Entry<Integer, Integer> entry : Carrier.entrySet()) {

			computerGrid[entry.getKey()][entry.getValue()] = 1;

		}

		for (Map.Entry<Integer, Integer> entry : BattleShip.entrySet()) {

			computerGrid[entry.getKey()][entry.getValue()] = 1;

		}

		for (Map.Entry<Integer, Integer> entry : Cruiser.entrySet()) {

			computerGrid[entry.getKey()][entry.getValue()] = 1;

		}

		for (Map.Entry<Integer, Integer> entry : Submarine.entrySet()) {

			computerGrid[entry.getKey()][entry.getValue()] = 1;

		}

		for (Map.Entry<Integer, Integer> entry : Destroyer.entrySet()) {

			computerGrid[entry.getKey()][entry.getValue()] = 1;

		}

		// set the computerGrid to be changed grid
		ChangedUserGrid = computerGrid;

	}

	/**
	 * Takes the input based on the event listener Provides the hit or miss while
	 * hitting on the computer grid
	 * 
	 * @return
	 */

	public String Userturn(int x, int y) {
		// get the X and y coordinate from the input
		//int x = 0;

		//int y = 0;

		if (ChangedComputerGrid[x][y] == 1) {
			// change the grid value from 1 to 2 to signify hit

			ChangedComputerGrid[x][y] = 2;

			return "It's a Hit!!!!!";

		} else if (ChangedComputerGrid[x][y] == 0) {

			return "It's a miss!!!!!";

		} else if (ChangedComputerGrid[x][y] == 2) {

			return "The location has been hit earlier";

		}

		// check if User won or computer won
		CheckIfUserWon();

		// some other case or error
		return " ";

	}

	/**
	 * Provides the hit or miss while hitting on the computer grid
	 * 
	 * @return
	 */

	public String Computerturn() {
		// get the X and y coordinate from the input
		int x = 0;

		int y = 0;

		if (ChangedUserGrid[x][y] == 1) {
			// change the grid value from 1 to 2 to signify hit

			ChangedUserGrid[x][y] = 2;

			return "It's a Hit!!!!!";

		} else if (ChangedUserGrid[x][y] == 0) {

			return "It's a miss!!!!!";

		} else if (ChangedUserGrid[x][y] == 2) {

			return "The location has been hit earlier";

		}

		// check if User won or computer won
		CheckIfCompWon();

		// some other case or error
		return " ";

	}

	/**
	 * checks both the grid of the User and the computer to verify if they won or
	 * not sets the static flag true if someone wins
	 * 
	 * @return
	 */

	public void CheckIfUserWon() {

		boolean flaguser = false;

		// check the computer grid if all the 1 are converted to 2
		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				if (!(computerGrid[i][j] == 1 && ChangedComputerGrid[i][j] == 2)) {
					// set the flag as true
					flaguser = true;

				}

			}
		}

		if (!flaguser) {// set that user has won

			Userwon = true;
		} else {
			// do nothing

		}

	}

	public void CheckIfCompWon() {

		boolean flagcomp = false;

		// check the computer grid if all the 1 are converted to 2
		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				if (!(userGrid[i][j] == 1 && ChangedUserGrid[i][j] == 2)) {
					// set the flag as true
					flagcomp = true;

				}

			}
		}

		if (!flagcomp) {// set that user has won

			Compwon = true;
		} else {
			// do nothing

		}

	}

	public void Initialize() {
		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				userGrid[i][j] = 0;
				computerGrid[i][j] = 0;
				ChangedUserGrid[i][j] = 0;
				ChangedComputerGrid[i][j] = 0;

			}
		}

	}

}
