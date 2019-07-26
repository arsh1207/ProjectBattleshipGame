package application.test;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import application.Models.HitStrategy;
import application.Models.Player;

public class HitStratergyTest {


	public Player ob;

	public HitStrategy hitstat;

	@Before
	public void setUp() {
		ob = new Player();
		hitstat = new HitStrategy();

	}

	/**
	 * Below test check the random hitting capacity of the AI in the easy mode
	 * checks the miss situation
	 */

	@Test
	public void randomHitTest() {

		
		Random ran = Mockito.mock(Random.class);
		Mockito.when(ran.nextInt(9)).thenReturn(7);
		Mockito.when(ran.nextInt(11)).thenReturn(10);

		hitstat.randomHit();

		assertEquals(hitstat.getReply(), "It's a miss!!!!!");

	}

	/**
	 * Below test check the random hitting capacity of the AI in the medium mode
	 * 
	 * 
	 */

	@Test
	public void mediumModeTest() {
		
		
		
		

	}

	/**
	 * Below test check the random hitting capacity of the AI in the hard Mode of
	 * the game
	 */

	@Test
	public void hardModeTest() {

	}

}