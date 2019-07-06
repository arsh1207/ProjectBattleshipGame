package application;

import java.util.HashMap;
import java.util.Map;

public class GridUser {

	public static int rows = 9;
	public static int cols = 11;
	
	public static boolean Userwon=false;
	public static boolean Compwon=false;
	
	
	public static Integer[][] userGrid = new Integer[rows][cols];

	public static Integer[][] computerGrid = new Integer[rows][cols];
	public static HashMap<String, Integer> convert = new HashMap<>();

	GridUser() {

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

	public String DeployUserGrid(String coordinates) {

		String str[] = coordinates.split("\\s");

		int x1 = convert.get(str[0]);

		// decrease the value of Y since the coordinated start from 0 in grid
		int y1 = Integer.parseInt(str[1]) - 1;
		int x2 = convert.get(str[2]);

		// decrease the value of Y since the coordinated start from 0 in grid
		int y2 = Integer.parseInt(str[3]) - 1;

		if (x1 == x2) {
			// if two X are the same then the line is horizontal

			// increment the Y values to set the ship location

			for (int i = y1; i <= y2; i++) {

				if ((x1 >= 0 && x1 < rows) && (y1 >= 0 && y1 < cols) && (y2 >= 0 && y2 < cols)
						&& (userGrid[x1][i] == 0)) {
					userGrid[x1][i] = 1;

				} else if ((x1 >= 0 && x1 < rows) && (y1 >= 0 && y1 < cols) && (y2 >= 0 && y2 < cols)
						&& userGrid[x1][i] == 1) {
					return "ships cannot be placed on the same location";
				} else if ((y1 < 0 || y1 >= rows) || (y2 < 0 || y2 >= rows) || (x1 < 0 || x1 >= cols)) {
					return "You can't place ships outside the " + rows + " by " + cols + " grid";
				}
				

			}

		}

		else if (y1 == y2) {

			// if two Y are the same then the line is vertical

			// increment the X values to set the ship location
			for (int i = x1; i <= x2; i++) {
				if ((y1 >= 0 && y1 < cols) && (x1 >= 0 && x1 < rows) && (x2 >= 0 && x2 < rows)
						&& (userGrid[i][y1] == 0)) {
					userGrid[i][y1] = 1;

				} else if ((y1 >= 0 && y1 < cols) && (x1 >= 0 && x1 < rows) && (x2 >= 0 && x2 < rows)
						&& userGrid[i][y1] == 1)
					return "ships cannot be placed on the same location";
				else if ((x1 < 0 || x1 >= rows) || (x2 < 0 || x2 >= rows) || (y1 < 0 || y1 >= cols))
					return "You can't place ships outside the " + rows + " by " + cols + " grid";

			}

		}
		
		// blank space signifies some other error
	return " ";
	

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

	}

	
	
	public void startTurns()
	{
		
		
		
		Userturn();
		
		CheckIfWon();
		Computerturn();
		
		
		
		
		
	}
	
	
	/**
	 * Provides the hit or miss while hitting on the computer grid
	 * 
	 * @return
	 */
	
	
	public String Userturn()
	{
		
		
		
		return " ";
		
	}
	
	/**
	 * Provides the  hit or miss while hitting on the computer grid
	 * @return
	 */
	
	public String Computerturn()
	{
		
		
		
		return " ";
		
	}
	
	
	/**
	 * checks both the grid of the User and the computer to verify if they won or not 
	 * sets the static flag true if someone wins
	 * @return
	 */
	
	public String CheckIfWon()
	{
		
		
		
		return " ";
		
	}
	
	
	
	
	/**
	 * Function to check the grid print of user and computer on the console
	 * 
	 */
	
	public void PrintGrid() {
		
		
		

	}

}