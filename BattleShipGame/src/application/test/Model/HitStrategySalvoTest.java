package application.test.Model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import application.Models.HitStrategySalvo;
import application.Models.Player;

public class HitStrategySalvoTest {

	public Player ob;

	@Before
	public void setUp() {
		ob = new Player();

	}

	/**
	 * Below test check the hitting capacity of the AI in the medium mode
	 * 
	 */

	@Test
	public void mediumModeTest() {

		HitStrategySalvo ran = Mockito.spy(new HitStrategySalvo());

		Mockito.when(ran.randomX()).thenReturn(7);
		Mockito.when(ran.randomY()).thenReturn(10);

		ran.mediumMode(true);

		assertEquals(ran.getReply(), "It's a miss!!!!!");

	}

}
