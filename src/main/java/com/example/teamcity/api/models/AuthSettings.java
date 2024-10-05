package com.example.teamcity.api.models;


import com.example.teamcity.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Arrays;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper=false)
public class AuthSettings extends BaseModel {
    @Builder.Default
    private Boolean allowGuest = true;
    @Random
    private String questUsername;
    @Random
    private String welcomeText;
    @Builder.Default
    private Boolean collapseLoginForm = true;
    @Builder.Default
    private Boolean perProjectPermissions = true;
    @Builder.Default
    private Boolean emailVerification = true;
}
