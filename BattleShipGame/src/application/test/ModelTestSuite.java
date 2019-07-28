package application.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({ ComputerTest.class, PlayerTest.class, HitStrategyTest.class })
public class ModelTestSuite {

}
