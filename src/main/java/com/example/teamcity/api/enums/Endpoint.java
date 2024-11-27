package com.example.teamcity.api.enums;

import com.example.teamcity.api.models.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class),
    PROJECTS("/app/rest/projects", Project.class),
    USERS("/app/rest/users", User.class);

    private final String url;
    private final Class<? extends BaseModel> modelClass;

    // Геттер для поля url
    public String getUrl() {
        return url;
    }

    // Геттер для поля modelClass
    public Class<? extends BaseModel> getModelClass() {
        return modelClass;
    }
}

