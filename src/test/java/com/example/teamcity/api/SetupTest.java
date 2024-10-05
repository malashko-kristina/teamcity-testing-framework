package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.AuthSettings;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.spec.Specifications;
import org.junit.jupiter.api.BeforeAll;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.AUTH_SETTINGS;
import static com.example.teamcity.api.enums.Endpoint.USERS;

@Test
public class SetupTest extends BaseApiTest {
//    @Test
//    public void addRolesToAccount() {
//        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
//        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
//        userCheckRequests.getRequest(AUTH_SETTINGS).update("", testData.getAuthSettings());
//    }
}
