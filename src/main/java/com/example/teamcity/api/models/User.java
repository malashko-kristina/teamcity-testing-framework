package com.example.teamcity.api.models;

import com.example.teamcity.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseModel {
    @Getter
    private int id;
    @Random
    @Getter
    private String username;
    @Random
    @Getter
    private String password;
    @Getter
    private Roles roles;
}