package application.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;

import application.Views.AlertBox;
import application.Views.ShipGrid;

public class Player extends Observable {

	//
	public static int userScore;
	public List<String> deployedShips = new ArrayList<>();

	public HashMap<String, Integer> convert = new HashMap<>();
	public static Map<String, ArrayList<String>> shipsMap = new HashMap<>();
	public static ArrayList<String> sunkenShips = new ArrayList<String>();
	public static ArrayList<String> coordinatesHit = new ArrayList<String>();
	
	public int numOfShipsDep = 0;

	public String shipType = "";
	
	int[] coords = {};

	String axis = "";

	public String reply = "";

	public boolean Hit = false;
	public boolean Miss = false;

	final static int rows = 9;
	final static int cols = 11;

	// original Grid that remains unchanged throughout the game
	public static Integer[][] userGrid = new Integer[rows][cols];

	public Player() {

		initialize();
	}

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public String getReply() {
		return reply;
	}

	public void setReply(String reply) {
		this.reply = reply;
	}

	public boolean isHit() {
		return Hit;
	}

	public void setHit(boolean hit) {
		Hit = hit;
	}

	public boolean isMiss() {
		return Miss;
	}

	public void setMiss(boolean miss) {
		Miss = miss;
	}

	public int getNumOfShipsDep() {
		return numOfShipsDep;
	}

	public Integer[][] getUserGrid() {
		return userGrid;
	}

	public List<String> getDeployedShips() {
		return deployedShips;
	}

	public int[] getCoords() {
		return coords;
	}

	public void setCoords(int[] coords) {
		this.coords = coords;
	}

	public String getAxis() {
		return axis;
	}

	public void setAxis(String axis) {
		this.axis = axis;
	}
	public static void setSunkenShips(String shipType) {
		sunkenShips.add(shipType);
	}
	public ArrayList<String> getSunkenShips() {
		
		return sunkenShips;
		
	}

	/**
	 * Takes the input based on the event listener Provides the hit or miss while
	 * hitting on the computer grid
	 * 
	 * @param x coordinate
	 * @param y coordinates
	 * @return String defining if the user hit or miss the computer grid
	 */
	public void userTurn(int x, int y, Integer[][] computerGrid) {

		// get the X and y coordinate from the input
		System.out.println("reached here" + x + "; " + y);
		if (computerGrid[x][y] == 1) {
			// change the grid value from 1 to 2 to signify hit

			computerGrid[x][y] = 2;

			setReply("It's a Hit!!!!!");
			userScore += 10;
			// incrementing user score for a successful hit

			// return "It's a Hit!!!!!";
		} else if (computerGrid[x][y] == 0) {

			setReply("It's a miss!!!!!");
			// return "It's a miss!!!!!";

			userScore -= 2;
			// loosing points for a miss

		} else if (computerGrid[x][y] == 2) {

			setReply("The location has been hit earlier");
			// return "The location has been hit earlier";

		}

		setReply("Some other error");
		// some other case or error
		// return "Some other error";

	}

	/*
	 * Returning user score
	 */
	public int GetScore() {
		return userScore;
	}

	/**
	 * Takes the input based on the event listener Provides the hit or miss while
	 * hitting on the computer grid
	 * 
	 * @param x coordinate
	 * @param y coordinates
	 * @return String defining if the user hit or miss the computer grid
	 */

	/**
	 * 
	 * @param coordinates defines the coordinated to be deployed
	 * @param shipType    type of the ship
	 * @return strings defining the placement of the ship in grid
	 */
	public void deployUserGrid(String coordinates, String shipType) {

		try {

			/*
			 * int x1 = convert.get(str[0]);
			 * 
			 * // decrease the value of Y since the coordinated start from 0 in grid int y1
			 * = Integer.parseInt(str[1]) - 1; int x2 = convert.get(str[2]);
			 * 
			 * // decrease the value of Y since the coordinated start from 0 in grid int y2
			 * = Integer.parseInt(str[3]) - 1;
			 */

			String str[] = coordinates.split("\\s");
			List<String> templist = new ArrayList<>();

			// int x1 = convert.get(str[0]);
			int y1 = Integer.parseInt(str[0]);

			// decrease the value of Y since the coordinated start from 0 in grid
			int x1 = Integer.parseInt(str[1]);
			// int x2 = convert.get(str[2]);
			int y2 = Integer.parseInt(str[2]);

			// decrease the value of Y since the coordinated start from 0 in grid
			int x2 = Integer.parseInt(str[3]);

			System.out.println("values x1 y1 x2 y2  " + x1 + y1 + x2 + y2);

			// ships cannot be placed outside the grid
			if (x1 >= cols || y1 >= rows || x2 >= cols || y2 >= rows) {

				deployedShips.remove(shipType);

				setReply("You can't place ships outside the " + rows + " by " + cols + " grid");
				// return "You can't place ships outside the " + rows + " by " + cols + " grid";

			}

			// coordinates cannot be negative
			if (x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0) {

				deployedShips.remove(shipType);

				setReply("You can't place ships outside the " + rows + " by " + cols + " grid");
				// return "You can't place ships outside the " + rows + " by " + cols + " grid";

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
						// displayUserShips();
						deployedShips.remove(shipType);
						setReply("ships cannot be placed on the same location");
						// return "ships cannot be placed on the same location";
					}

				}
				String res = areHolesValid(y2 - y1 + 1, shipType);
				if (res.equals("YES") && deployedShips.contains(shipType)) {
					for (int i = y1; i <= y2; i++) {
						if ((x1 >= 0 && x1 < cols) && (y1 >= 0 && y1 < rows) && (y2 >= 0 && y2 < rows)
								&& (userGrid[i][x1] == 0)) {
							userGrid[i][x1] = 1;
							templist.add(Integer.toString(i) + ","+ Integer.toString(x1));
							count++;
						}
					}
					
					if (((y2 + 1) - (y1 + 1)) + 1 == count) {
						shipsMap.put(shipType, (ArrayList)templist);
						int[] coords = { x1, y1, x2, y2 };
						// calling the function on front end to color the
						// coordinates of the ship as required
						setCoords(coords);
						setShipType(shipType);
						setAxis("Y");
						// shipObject.deployShipsWithColors(coords, shipType, "Y");
						// result done signifies everything went right
						// changedUserGrid = userGrid;
						numOfShipsDep++;
						setReply("Done");
						// return "Done";
					}
				} /*
					 * else { deployedShips.remove(shipType); setReply(res); // return res; }
					 */

			}

			else if (y1 == y2) {

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
						// displayUserShips();
						deployedShips.remove(shipType);
						setReply("ships cannot be placed on the same location");
						// return "ships cannot be placed on the same location";
					}
				}
				String res = areHolesValid(x2 - x1 + 1, shipType);
				if (res.equals("YES") && deployedShips.contains(shipType)) {
					// increment the X values to set the ship location
					for (int i = x1; i <= x2; i++) {

						if ((y1 >= 0 && y1 < rows) && (x1 >= 0 && x1 < cols) && (x2 >= 0 && x2 < cols)
								&& (userGrid[y1][i] == 0)) {
							System.out.println("For coordinates " + y1 + " and " + i);
							userGrid[y1][i] = 1;
							templist.add(Integer.toString(y1) + ","+ Integer.toString(i));
							count++;

						}

					}

					if (((x2 + 1) - (x1 + 1)) + 1 == count) {
						shipsMap.put(shipType, (ArrayList)templist);
						int[] coords = { x1, y1, x2, y2 };
						// calling the function on front end to color the
						// coordinates of the ship as required

						setCoords(coords);
						setShipType(shipType);
						setAxis("X");

						// shipObject.deployShipsWithColors(coords, shipType, "X");
						// changedUserGrid = userGrid;
						numOfShipsDep++;

						setReply("Done");
						// return "Done";
					}

				} /*
					 * else { deployedShips.remove(shipType);
					 * 
					 * setReply(res); // return res; }
					 */
			}

			else {// case when anything Diagonal
				deployedShips.remove(shipType);

				setReply("Can not place ship Diagonal");
				// return "Can not place ship Diagonal";

			}

			// deployedShips.remove(shipType);
			// signifies some other error

			// setReply("Invalid input, please try again.");
			// return "Invalid input, please try again.";
		} catch (Exception e) {
			deployedShips.remove(shipType);
			e.printStackTrace();
			setReply("Invalid input, please try again.");
			// return "Invalid input, please try again.";
		} finally {
			setChanged();
			notifyObservers();
		}
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

				if (userGrid[i][j] == 1) {
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

	public void deployUserRandomShips() {
		try {
			Random rand = new Random();
			int carrierX = rand.nextInt(9);
			int carrierY = rand.nextInt(11);

			HashMap<Integer, Integer> Carrier = new HashMap<>();
			Boolean placed = false;

			Boolean shipPlacementFlag = false;
			while (!placed) {
				if (checkUserShip(carrierX, carrierY, "horizontal", 5)) {
					if ((carrierY + 5) <= 11) {
						shipPlacementFlag = true;
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
				userGrid[entry.getValue()][entry.getKey()] = 1;
				// computerGrid[entry.getKey()][entry.getValue()] = 1;

			}

			if (shipPlacementFlag) {
				int[] coords = { carrierY, carrierX, carrierY + 4, (carrierX) };
				setCoords(coords);
				// shipObject.deployShipsWithColors(coords, "Carrier", "X");
			} else {
				int[] coords = { carrierY - 4, carrierX, carrierY, (carrierX) };
				setCoords(coords);
				// shipObject.deployShipsWithColors(coords, "Carrier", "X");
			}

			setShipType("Carrier");
			setAxis("X");

			setChanged();
			notifyObservers();

			int battleShipX = rand.nextInt(9);
			int battleShipY = rand.nextInt(11);

			HashMap<Integer, Integer> BattleShip = new HashMap<>();
			shipPlacementFlag = false;
			placed = false;
			while (!placed) {
				if (checkUserShip(battleShipX, battleShipY, "vertical", 4)) {
					if ((battleShipX + 4) < 9) {
						shipPlacementFlag = true;
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

				userGrid[entry.getKey()][entry.getValue()] = 1;
				// computerGrid[entry.getValue()][entry.getKey()] = 1;

			}
			if (shipPlacementFlag) {
				int[] coords = { battleShipY, battleShipX, (battleShipY), battleShipX + 3 };
				setCoords(coords);
			} else {

				int[] coords = { battleShipY, battleShipX - 3, (battleShipY), battleShipX };
				setCoords(coords);
			}

			setShipType("Battleship");
			setAxis("Y");

			setChanged();
			notifyObservers();

			int cruiserX = rand.nextInt(9);
			int cruiserY = rand.nextInt(11);

			HashMap<Integer, Integer> Cruiser = new HashMap<>();
			shipPlacementFlag = false;
			placed = false;
			while (!placed) {
				if (checkUserShip(cruiserX, cruiserY, "vertical", 3)) {
					if ((cruiserX + 3) < 9) {
						shipPlacementFlag = true;
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

				userGrid[entry.getKey()][entry.getValue()] = 1;
				// computerGrid[entry.getValue()][entry.getKey()] = 1;

			}

			if (shipPlacementFlag) {
				int[] coords = { cruiserY, cruiserX, (cruiserY), cruiserX + 2 };
				setCoords(coords);
			} else {
				// int[] coords = { cruiserX, cruiserY, (cruiserX - 3), cruiserY };
				int[] coords = { cruiserY, cruiserX - 2, (cruiserY), cruiserX };
				setCoords(coords);
			}

			setShipType("Cruiser");
			setAxis("Y");

			setChanged();
			notifyObservers();

			int subX = rand.nextInt(9);
			int subY = rand.nextInt(11);
			HashMap<Integer, Integer> Submarine = new HashMap<>();
			shipPlacementFlag = false;
			placed = false;
			while (!placed) {
				if (checkUserShip(subX, subY, "vertical", 3)) {
					if ((subX + 3) < 9) {
						shipPlacementFlag = true;
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

				userGrid[entry.getKey()][entry.getValue()] = 1;
				// computerGrid[entry.getValue()][entry.getKey()] = 1;

			}
			if (shipPlacementFlag) {
				int[] coords = { subY, subX, (subY), subX + 2 };
				setCoords(coords);

			} else {

				int[] coords = { subY, subX - 2, (subY), subX };
				setCoords(coords);

			}

			setShipType("Submarine");
			setAxis("Y");

			setChanged();
			notifyObservers();

			int destroyerX = rand.nextInt(9);
			int destroyerY = rand.nextInt(11);

			HashMap<Integer, Integer> Destroyer = new HashMap<>();
			shipPlacementFlag = false;
			placed = false;
			while (!placed) {
				if (checkUserShip(destroyerX, destroyerY, "horizontal", 2)) {
					if ((destroyerY + 2) < 11) {
						shipPlacementFlag = true;
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

				userGrid[entry.getValue()][entry.getKey()] = 1;

			}
			if (shipPlacementFlag) {
				int[] coords = { destroyerY, destroyerX, destroyerY + 1, (destroyerX) };
				setCoords(coords);
			} else {

				int[] coords = { destroyerY - 1, destroyerX, destroyerY, (destroyerX) };
				setCoords(coords);
			}

			setShipType("Destroyer");
			setAxis("X");

			setChanged();
			notifyObservers();

			numOfShipsDep = 5;

			/*
			 * System.out.println("user grid"); for (int i = 0; i < rows; i++) {
			 * 
			 * for (int j = 0; j < cols; j++) {
			 * 
			 * System.out.print(userGrid[i][j] + " ");
			 * 
			 * } System.out.println(); }
			 */

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public Boolean checkUserShip(int x, int y, String direction, int points) {
		Boolean canPlace = true;
		if (direction.equals("horizontal")) {
			if ((y + points) < 11) {
				for (int j = 0; j < points; j++) {
					if (userGrid[x][y + j] != 0)
						canPlace = false;
				}
			} else {
				for (int j = 0; j < points; j++) {
					if (userGrid[x][y - j] != 0)
						canPlace = false;
				}
			}

		} else {

			if ((x + points) < 9) {
				for (int j = 0; j < points; j++) {
					if (userGrid[x + j][y] != 0)
						canPlace = false;
				}
			} else {
				for (int j = 0; j < points; j++) {
					if (userGrid[x - j][y] != 0)
						canPlace = false;
				}
			}

		}

		return canPlace;

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

				if (userGrid[i][j] == 1) {
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
	 * Method to display the computer grid
	 */

	public void printGrid(Integer[][] Grid) {

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				System.out.print(Grid[i][j] + " ");

			}
			System.out.println();
		}

	}

	/**
	 * Initialize all the grids to zero
	 * 
	 */
	public void initialize() {

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				userGrid[i][j] = 0;

			}
		}

	}
	
	/**
	 * This function checks whether any ships have sunk or not
	 * @param coordX X-coordinate
	 * @param coordY Y-coordinate
	 */
	public static void checkSunkenShips() {
		
		System.out.print("checkSunkenShips called\n");
		Map<String, ArrayList<String>> tempMap; 
		for (String coords : coordinatesHit) {
			System.out.println("Checking coordinates "+coords);
			tempMap = new HashMap<>();
			tempMap.putAll(shipsMap);
			for (Map.Entry<String, ArrayList<String>> entry : shipsMap.entrySet()) {
				System.out.println("Checking "+entry.getKey());
				System.out.println(entry);
				if(!shipsMap.get(entry.getKey()).isEmpty()) {
					//if any ship has been placed on the assigned coordinate
					if(shipsMap.get(entry.getKey()).contains(coords)) {
						tempMap.get(entry.getKey()).remove(coords);
						
						//if no coordinates are remaining to be hit then add the ship to sunken ships
						//and remove the ships from the shipsMap
						if(shipsMap.get(entry.getKey()).isEmpty()) {
							setSunkenShips(entry.getKey());
							System.out.println(entry.getKey()+" destroyed");
							tempMap.remove(entry.getKey());
							System.out.println(entry.getKey()+" removed");
						}
					}
					
				}
	
			}
			shipsMap = new HashMap<>();
			shipsMap.putAll(tempMap);
		}
		ShipGrid.salvaAlertCall(sunkenShips);
		
	}


}
