package application.test.Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	 * Check comp has won or not
	 */

	@Test
	public void checkIfUserWonTest() {

		Player user = new Player();
		for (int i = 0; i < 9; i++) {

			for (int j = 0; j < 11; j++) {

				if (user.userGrid[i][j] == 1) {
					// set the flag as true if there is still one present somewhere

					user.userGrid[i][j] = 2;
				}

			}
		}

		user.checkIfCompWon();

		assertEquals("Won", user.compWon);

	}



}
