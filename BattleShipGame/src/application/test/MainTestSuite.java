package application.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * This is the main Test suite that is used to run other Test suite
 * 
 * @author Prateek
 *
 */

@RunWith(Suite.class)

@SuiteClasses({ ControllerTestSuite.class, ModelTestSuite.class, ViewTestSuite.class })
public class MainTestSuite {

}
