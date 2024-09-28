package com.example.teamcity.api.requests;

import io.restassured.specification.RequestSpecification;
import com.example.teamcity.api.requests.enums.Endpoint; // тут потенциально мб ошибка

public class Request {
    /**
     * Request – a class, which describes changing parameters of the request like specification, endpoint (relative URL, model (DTO))
     */
    private final RequestSpecification spec;
    private final Endpoint endpoint;

    public Request(RequestSpecification spec, Endpoint endpoint) {
        this.spec = spec;
        this.endpoint = endpoint;
    }
}
