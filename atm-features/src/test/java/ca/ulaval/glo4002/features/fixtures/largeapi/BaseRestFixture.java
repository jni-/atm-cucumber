package ca.ulaval.glo4002.features.fixtures.largeapi;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.MediaType;

import io.restassured.specification.RequestSpecification;

public class BaseRestFixture {
    
    protected RequestSpecification givenAtmRequest() {
        return given()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .port(JettyRunner.JETTY_TEST_PORT);
    }

}
