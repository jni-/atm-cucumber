package ca.ulaval.glo4002.atm_uat.fixtures;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.MediaType;

import ca.ulaval.glo4002.atm_uat.JettyStarterHook;
import io.restassured.specification.RequestSpecification;

public class BaseRestFixture {
    
    protected RequestSpecification givenAtmRequest() {
        return given()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .port(JettyStarterHook.JETTY_TEST_PORT);
    }

}
