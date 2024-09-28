package com.example.teamcity.api.spec;

import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.models.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Specifications {

    private static Specifications spec;

    private Specifications() {} // Это паттерн Синглтон. нельзя больше создавать экземпляры его

    public static Specifications getSpec() { // гарантия того, что спецификация будет в одном экземпляре
        if (spec == null) {
            spec = new Specifications();
        }
        return spec;
    }

    private RequestSpecBuilder reqBuilder() {
        var requestBuilder = new RequestSpecBuilder();
        requestBuilder.addFilter(new RequestLoggingFilter());
        requestBuilder.addFilter(new ResponseLoggingFilter());
        return requestBuilder;
    }
    public RequestSpecification unauthSpec() { // почему именно этот тип возвращаем?
        var requestBuilder = reqBuilder();
        requestBuilder.setContentType(ContentType.JSON);
        requestBuilder.setAccept(ContentType.JSON);
        return requestBuilder.build();
    }

    public RequestSpecification authSpec(User user) {
        var requestBuilder = reqBuilder();
        requestBuilder.setBaseUri("http://" + user.getUsername() + ":" + user.getPassword() + "@" +
                Config.getProperty("host"));
        return requestBuilder.build();
    }
}
