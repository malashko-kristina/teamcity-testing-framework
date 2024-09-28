package com.example.teamcity.api.requests.enums;

import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class);

    private final String url;
    private final Class<? extends BaseModel> modelClass; // read about this constructor


}
