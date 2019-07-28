package application.test.Model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import application.Models.HitStrategy;
import application.Models.Player;

@RunWith(MockitoJUnitRunner.class)
public class HitStrategyTest {

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

		HitStrategy ran = Mockito.spy(new HitStrategy());

		Mockito.when(ran.randomX()).thenReturn(7);
		Mockito.when(ran.randomY()).thenReturn(10);

		ran.randomHit();

		assertEquals(ran.getReply(), "It's a miss!!!!!");

	}

	/**
	 * Below test check the hitting capacity of the AI in the medium mode
	 * 
	 */

	@Test
	public void mediumModeTest() {

		HitStrategy ran = Mockito.spy(new HitStrategy());

		Mockito.when(ran.randomX()).thenReturn(7);
		Mockito.when(ran.randomY()).thenReturn(10);

		ran.mediumMode(true);

		assertEquals(ran.getReply(), "It's a miss!!!!!");

	}

	/**
	 * Below test check the hitting capacity of the AI in the hard Mode of the game
	 */

	@Test
	public void hardModeTest() {

		HitStrategy ran = Mockito.spy(new HitStrategy());

		Mockito.when(ran.randomX()).thenReturn(7);
		Mockito.when(ran.randomY()).thenReturn(10);

		ran.hardMode(true);
		;

		assertEquals(ran.getReply(), "It's a miss!!!!!");

	}

}