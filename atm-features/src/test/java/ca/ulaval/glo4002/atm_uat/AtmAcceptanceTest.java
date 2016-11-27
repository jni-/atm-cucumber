package ca.ulaval.glo4002.atm_uat;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
 
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "classpath:atm_uat/")
public class AtmAcceptanceTest {
}