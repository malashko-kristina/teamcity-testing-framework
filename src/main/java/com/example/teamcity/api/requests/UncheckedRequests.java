package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import io.restassured.specification.RequestSpecification;
import java.util.EnumMap;

public class UncheckedRequests {
    private final EnumMap<Endpoint, UncheckedBase> requests = new EnumMap<>(Endpoint.class);

    public UncheckedRequests(RequestSpecification spec) {
        for (var endpoint : Endpoint.values()) {
            requests.put(endpoint, new UncheckedBase(spec, endpoint));
        }

    }

    // Получение доступа к самому запросу
    public UncheckedBase getRequest(Endpoint endpoint) {
        return requests.get(endpoint);
    }

}
