package ca.ulaval.glo4002.features;

import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = { "pretty" }, features = "classpath:features/")
public class AtmAcceptanceTest {

    @BeforeClass
    public static void beforeRunningTests() {
        String scope = System.getProperty("acctests.scope", "UNDEFINED");
        Logger.getAnonymousLogger().info(String.format("\n\n>>>>>>>> ACCEPTANCE TESTS with Cucumber \n>>>>>>>> SCOPE: %s \n\n", scope));
    }

}