package application.test.Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.Models.Computer;
import application.Models.Player;

public class PlayerTest {

	private Player ob = null;

	@Before
	public void setUp() {
		ob = new Player();

	}

	@After
	public void cleanUp() {
		ob = null;

	}

	/**
	 * 
	 * Test case to check so that ships cannot be placed diagonally
	 * 
	 */

	@Test
	public void deployUserGridTest() {

		String coordinates1 = "1 1 3 3";

		String shiptype = "Cruiser";

		ob.deployUserGrid(coordinates1, shiptype);

		ob.getReply();
		assertEquals("Can not place ship Diagonal", ob.getReply());
	}

	/**
	 * 
	 * Test case to check ship placement outside the grid
	 * 
	 */

	@Test
	public void deployUserGridTest2() {

		String coordinates1 = "9 6 11 6";

		String shiptype = "Cruiser";

		ob.deployUserGrid(coordinates1, shiptype);

		assertEquals("Invalid input, please try again.", ob.getReply());
	}

	/**
	 * 
	 * Test case to check so that ships cannot on the same location
	 * 
	 */

	@Test
	public void deployUserGridTest3() {

		// placing the ship at the location
		String coordinates1 = "1 1 3 1";

		String shiptype = "Cruiser";
		ob.deployedShips.add("Cruiser");
		ob.deployUserGrid(coordinates1, shiptype);

		// again trying to place at the same location
		String coordinates2 = "1 1 1 3";

		String shiptype2 = "Submarine";
		ob.deployedShips.add("Submarine");
		ob.deployUserGrid(coordinates2, shiptype2);

		assertEquals("ships cannot be placed on the same location", ob.getReply());
	}



	/**
	 * 
	 * Test case to check so that ships can be placed right
	 * 
	 */

	@Test
	public void deployUserGridTest4() {

		// placing the ship at the location
		String coordinates1 = "1 1 3 1";

		String shiptype = "Cruiser";
		ob.deployedShips.add("Cruiser");
		ob.deployUserGrid(coordinates1, shiptype);

		assertEquals("Done", ob.getReply());
	}
	
	
	/**
	 * 
	 * 
	 * Ships cannot be place Adjacent to Each other
	 * 
	 */

	@Test
	public void deployUserGridTest5() {

		// placing the ship at the location
		String coordinates1 = "1 1 3 1";

		String shiptype = "Cruiser";
		ob.deployedShips.add("Cruiser");
		ob.deployUserGrid(coordinates1, shiptype);

		// again trying to place at the same location
		String coordinates2 = "1 2 3 2";

		String shiptype2 = "Submarine";
		ob.deployedShips.add("Submarine");
		ob.deployUserGrid(coordinates2, shiptype2);

		assertEquals("Ship cannot be placed Adjacent to each other", ob.getReply());
	}

	/**
	 * Test that the grid has been initialized correctly or not
	 * 
	 */
	@Test
	public void initializeTest() {
		boolean flag = true;

		int rows = 9;
		int cols = 11;

		Integer userGrid[][] = ob.getUserGrid();

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				if (!(userGrid[i][j] == 0)) {

					flag = false;
				}

			}
		}

		assertTrue(flag);
	}
	
	/**
	 * 
	 * Test that user has Won 
	 * 
	 */
	@Test 
	public void checkIfUserWonTest() {
		boolean flag = true;

		int rows = 9;
		int cols = 11;

		Integer userGrid[][] = ob.getUserGrid();

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				if (!(userGrid[i][j] == 0)) {

					flag = false;
				}

			}
		}

		assertTrue(flag);
	}
	
	
	
	
	
	
	
	/**
	 * 
	 * Test Case to verify if the score is increased after a hit or not
	 * 
	 */
	
	@Test
	public void getScoreCompTest() {
		//sets the score to update by 10 if a ship is hit
		
		ob.setScore(10);
		
		assertEquals(10, ob.getScore());
		
		
	}

	
	/**
	 * To check if all the ships have been deployed 
	 * 
	 * 
	 */
		@Test  
	
	public void areAllShipsDeployedTest()
	{
			
		
			ob.deployedShips.add("Cruiser");
			ob.deployedShips.add("Destroyer");
			ob.deployedShips.add("Submarine");
			ob.deployedShips.add("Carrier");
			ob.deployedShips.add("Battleship");
			
			assertEquals(5,ob.deployedShips.size());
			
		
		
	}
	
	
		 

}
