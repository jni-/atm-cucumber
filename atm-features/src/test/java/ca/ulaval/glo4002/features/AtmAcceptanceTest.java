package ca.ulaval.glo4002.features;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
 
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = "classpath:features/")
public class AtmAcceptanceTest {
}