package application.Models;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Scanner;


import main.Main;

public class LoadClass extends Observable{
	
	int rowNum, colNum;
	int coordState;
	int score;
	String rowCoord, colCoord, shipType;
	
	public void setRadarGridCoords(int j, int i, int coordstate) {
		this.rowNum = j;
		this.colNum = i;
		this.coordState = coordstate;
		setChanged();
		notifyObservers("setcoordscomp");
	}
	
	public int[] getRadarGridCoords() {
		int coords[] = {this.rowNum, this.colNum, this.coordState};
		return coords;
	}
	
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		setChanged();
		notifyObservers("setscorecomp");
	}
	
	public void setShipGridCoords(int j, int i, int coordstate) {
		this.rowNum = j;
		this.colNum = i;
		this.coordState = coordstate;
		setChanged();
		notifyObservers("setcoordsuser");
	}
	
	public int[] getShipGridCoords() {
		int coords[] = {this.rowNum, this.colNum, this.coordState};
		return coords;
	}
	
	
	public int getUserScore() {
		return score;
	}

	public void setUserScore(int score) {
		this.score = score;
		setChanged();
		notifyObservers("setscoreuser");
	}
	
	public void setColoredShipsCoord(String rowCoord, String colCoord, String shipType) {
		
		this.rowCoord = rowCoord;
		this.colCoord = colCoord;
		this.shipType = shipType;
		setChanged();
		notifyObservers("setcolorcoords");
		
	}
	
	public String[] getColoredShipsCoord() {
		String[] coords = {this.rowCoord, this.colCoord, this.shipType}; 
		return coords;
	}

	public void loadGame(Computer computer, Player player, SaveClass saveClass) {
		
		String folderPath = "User-Data\\"; 
		String userName = saveClass.getuName();
		String line = "";
		try {
			Scanner in  = new Scanner(new File(folderPath+userName+".txt"));
			while(in.hasNextLine()) {
				line = in.nextLine();
				if(line.contains("Sat Aug 03 00:16:01")) {
					break;
				}
			}
			Main.gameMode = in.nextLine().split(" ")[2];
			Main.gameType = in.nextLine().split(" ")[2];
			System.out.println("game mode and type are "+Main.gameMode+" "+Main.gameType);
			in.nextLine();
			storeComputerGrid(in, computer);
			//store sunken ships
			line = in.nextLine();
			//System.out.println(line);
			storeComputerSunkenShips(line);
			line = in.nextLine();
			//store shipsmap
			storeComputerShipsMap(in);
			//perform operations for ship grid
			while(in.hasNextLine()) {
				line = in.nextLine();
				if(line.contains("Player ships status")) {
					break;
				}
			}
			//load the ships with colors
			loadColoredUserShips(in);
			//load the ship grid with hits and misses
			in  = new Scanner(new File(folderPath+userName+".txt"));
			while(in.hasNextLine()) {
				line = in.nextLine();
				if(line.contains("Sat Aug 03 00:16:01")) {
					break;
				}
			}
			while(in.hasNextLine()) {
				line = in.nextLine();
				if(line.contains("ShipGrid")) {
					break;
				}
			}
			storeUserGrid(in, player);
			line = in.nextLine();
			storeUserSunkenShips(line);
			in.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void storeComputerGrid(Scanner in, Computer computer) {
		
		String line;
		int j = 0;
		while(in.hasNextLine()) {
			line = in.nextLine();
			if(line.contains("Computer Score")) {
				String[] line2 = line.split(" ");
				setScore(Integer.parseInt(line2[2]));
				break;
			}
			for(int i = 0; i < line.length(); i++) {
				computer.computerGrid[j][i] = Integer.parseInt(Character.toString(line.charAt(i)));
				System.out.print(computer.computerGrid[j][i]);
				setRadarGridCoords(j, i, computer.computerGrid[j][i]);
			}
			System.out.println("");
			j++;
		}
	}
	
	public void storeComputerSunkenShips(String line) {
		
		List<String> sunkenShips = new ArrayList<>();
		int i;
		for(i = 0; line.charAt(i) != ':'; i++);
		i+=3;
		while(i < line.length()) {
			int j = i;
			while(line.charAt(j) != ',') {
				if(line.charAt(j) == ']')
					break;
				j++;
			}
			String subline = line.substring(i, j);
			i = j+2;
			sunkenShips.add(subline);
		}
		Computer.sunkenShips = (ArrayList<String>) sunkenShips;
		System.out.println(Computer.sunkenShips.size());
	}
	
	public void storeComputerShipsMap(Scanner in) {
		String line;
		ArrayList<String> tempList = new ArrayList<>();
		Map<String, ArrayList<String>> shipsMap = new HashMap<>();
		while(in.hasNextLine()) {
			line = in.nextLine();
			if(line.contains("ShipGrid"))
				break;
			else {
				tempList = new ArrayList<>();
				String[] sublines = line.split(" ");
				System.out.println(sublines[0]);
				for(int i = 1; i < sublines.length; i++) {
					String sublines2 = sublines[i];
					if(sublines2.contains("[")) {
						int x = sublines2.indexOf("[");
						sublines2 = sublines2.substring(x+1, x+4);
						//System.out.println(sublines2);
					}
					else if(sublines2.contains("]")) {
						int x = sublines2.indexOf("]");
						sublines2 = sublines2.substring(x-3, x);
					}
					else {
						sublines2 = sublines2.substring(0, 3);
					}
					System.out.println(sublines2);
					tempList.add(sublines2);
				}
				shipsMap.put(sublines[0], tempList);
			}
		}
		Computer.shipsMap.putAll(shipsMap);
		System.out.println(Computer.shipsMap.size());
	}
	
	public void storeUserGrid(Scanner in, Player player) {
		
		String line;
		int j = 0;
		while(in.hasNextLine()) {
			line = in.nextLine();
			if(line.contains("Player Score")) {
				String[] line2 = line.split(" ");
				setUserScore(Integer.parseInt(line2[2]));
				break;
			}
			for(int i = 0; i < line.length(); i++) {
				player.userGrid[j][i] = Integer.parseInt(Character.toString(line.charAt(i)));
				System.out.print(player.userGrid[j][i]);
				setShipGridCoords(j, i, player.userGrid[j][i]);
			}
			System.out.println("");
			j++;
		}
	}
	
	public void loadColoredUserShips(Scanner in) {
		String line;
		while(in.hasNextLine()) {
			line = in.nextLine();
			if(line.contains("Created on"))
				break;
			else {
				String[] sublines = line.split(" ");
				System.out.println(sublines[0]);
				for(int i = 1; i < sublines.length; i++) {
					String sublines2 = sublines[i];
					if(sublines2.contains("[")) {
						int x = sublines2.indexOf("[");
						sublines2 = sublines2.substring(x+1, x+4);
						//System.out.println(sublines2);
					}
					if(sublines2.contains("]")) {
						int x = sublines2.indexOf("]");
						sublines2 = sublines2.substring(x-3, x);
					}
					else {
						sublines2 = sublines2.substring(0, 3);
					}
					System.out.println(sublines2);
					String[] sublines3 = sublines2.split(",");
					setColoredShipsCoord(sublines3[0], sublines3[1], sublines[0]);
				}
			}
		}
		
	}
	public void storeUserSunkenShips(String line) {
			
		List<String> sunkenShips = new ArrayList<>();
		int i;
		for(i = 0; line.charAt(i) != ':'; i++);
		i+=3;
		while(i < line.length()) {
			int j = i;
			while(line.charAt(j) != ',') {
				if(line.charAt(j) == ']')
					break;
				j++;
			}
			if(j > i+1) {
				String subline = line.substring(i, j);
				i = j+2;
				sunkenShips.add(subline);
			}
			else {
				i++;
			}
		}
		if(!sunkenShips.isEmpty()) {
			Player.sunkenShips = (ArrayList<String>) sunkenShips;
		}else {
			Player.sunkenShips = new ArrayList<>();
		}
		System.out.println(sunkenShips.size());
	}
	
}
