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
	public void deployUserGridTest() {
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
		// checks that all 5 ships are deployed or not
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
}
