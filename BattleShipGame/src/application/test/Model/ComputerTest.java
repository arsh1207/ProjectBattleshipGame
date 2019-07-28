package application.test.Model;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import application.Models.Computer;
import application.Models.HitStrategy;

public class ComputerTest {
	
	//private Computer ob = null;

	@Before
	public void setUp() {
		//ob = new Computer();

	}

	@After
	public void cleanUp() {
		//ob = null;

	}
	
	
	/**
	 * 
	 * Test case to check so that ships cannot be placed diagonally
	 * 
	 */

	@Test
	public void deployUserGridTest() {
		

		Computer ob = Mockito.spy(new Computer());

		Mockito.when(ob.randomX()).thenReturn(7);
		Mockito.when(ob.randomY()).thenReturn(10);

		ob.deployComputerShips();

	//	assertEquals(ran.getReply(), "It's a miss!!!!!");


		String coordinates1 = "1 1 3 3";

		String shiptype = "Cruiser";

		ob.deployComputerShips();

		ob.getReply();
		assertEquals("Can not place ship Diagonal", ob.getReply());
	}


	
	
}
