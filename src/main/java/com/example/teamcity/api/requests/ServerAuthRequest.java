package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.ServerAuthSettings;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

public class ServerAuthRequest {
    private static final String SERVER_AUTH_SETTINGS_URL = "/app/rest/server/authSettings";
    private RequestSpecification spec;

    public ServerAuthRequest(RequestSpecification spec) {
        this.spec = spec;
    }

    public ServerAuthSettings read() {
        return RestAssured.given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .get(SERVER_AUTH_SETTINGS_URL)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(ServerAuthSettings.class);
    }

    public ServerAuthSettings update(ServerAuthSettings authSettings) {
        return RestAssured.given()
                .spec(spec)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(authSettings)
                .put(SERVER_AUTH_SETTINGS_URL)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().as(ServerAuthSettings.class);
    }
}