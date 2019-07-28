package application.test.Controllers;

import org.junit.After;
import org.junit.Before;

import application.Models.Player;

public class GridUserTest {

	private Player ob = null;

	@Before
	public void setUp() {
		ob = new Player();

	}

	@After
	public void cleanUp() {
		
		ob = null;

	}
	
	
	

}
