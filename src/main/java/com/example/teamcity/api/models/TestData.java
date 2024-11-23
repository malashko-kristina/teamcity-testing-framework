package com.example.teamcity.api.models;

import lombok.Data;

@Data
public class TestData extends BaseModel {
    private Project project;
    private User user;
    private BuildType buildType;
    private ServerAuthSettings authSettings;

}
