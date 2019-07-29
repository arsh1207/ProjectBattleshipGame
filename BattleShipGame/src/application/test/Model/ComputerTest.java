package application.test.Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.Models.Computer;

public class ComputerTest {

	private Computer ob = null;

	@Before
	public void setUp() {
		ob = new Computer();

	}

	@After
	public void cleanUp() {
		ob = null;

	}

	/**
	 * 
	 * Test case to check so that all 5 ships have been deployed or not
	 * 
	 */

	@Test
	public void deployComputerShipsTest() {
		// checks that all 5 ships are deployed or not
		ob.deployComputerShips();

		assertEquals(5, ob.getCounter());
	}

	/**
	 * 
	 * Test case to verify the Check method for ship placement is working
	 * 
	 */

	@Test
	public void checkTest() {
		
		String direction = "horizontal";

		// ship type is Cruiser hence points three
		int points = 3;
		int x = 5;
		int y = 5;

		boolean res = ob.check(x, y, direction, points);

		assertTrue(res);
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

		Integer compGrid[][] = ob.getComputerGrid();

		for (int i = 0; i < rows; i++) {

			for (int j = 0; j < cols; j++) {

				if (!(compGrid[i][j] == 0)) {

					flag = false;
				}

			}
		}

		assertTrue(flag);
	}

	/**
	 * 
	 * To test if player missed the the computer grid
	 * 
	 */

	@Test
	public void userTurnTest() {

		Computer comp = new Computer();

		int x = 3;
		int y = 4;

		int x1 = 5;
		int y1 = 6;

		comp.computerGrid[x][y] = 1;

		comp.userTurn(x, y);

		assertEquals("It's a Hit!!!!!", comp.getReply());

	}

	/**
	 * 
	 * To test if player hit or missed the the computer grid
	 * 
	 */

	@Test
	public void userTurnTest2() {

		Computer comp = new Computer();

		int x = 3;
		int y = 4;

		int x1 = 5;
		int y1 = 6;

		comp.computerGrid[x][y] = 1;

		comp.userTurn(x, y);

		assertEquals("It's a Hit!!!!!", comp.getReply());

		comp.userTurn(x1, y1);

		assertEquals("It's a miss!!!!!", comp.getReply());

	}

	/**
	 * Player cannot hit on the same location if once HIt
	 */

	@Test
	public void UserTurnTest3() {

		Computer comp = new Computer();

		int x = 3;
		int y = 4;

		int x1 = 5;
		int y1 = 6;

		comp.computerGrid[x][y] = 2;

		comp.userTurn(x, y);

		comp.userTurn(x, y);

		assertEquals("The location has been hit earlier", comp.getReply());

	}

	/**
	 * Check user has Won or not
	 * 
	 */

	@Test
	public void checkIfUserWonTest() {

		Computer comp = new Computer();
		
		comp.deployComputerShips();
		for (int i = 0; i < 9; i++) {

			for (int j = 0; j < 11; j++) {

				if (comp.computerGrid[i][j] == 1) {
					// set the flag as true if there is still one present somewhere

					comp.computerGrid[i][j] = 2;
				}

			}
		}

		comp.checkIfUserWon();

		assertEquals("Won", comp.getUserWon());

	}

}
