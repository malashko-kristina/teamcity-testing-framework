package com.example.teamcity.api.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TestData extends BaseModel {
    private Project project;
    private User user;
    private BuildType buildType;
    private ServerAuthSettings authSettings;

}
