package com.example.teamcity.api.models;


import com.example.teamcity.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper=false)
public class Project extends BaseModel{
    private String id;
    @Random
    private String name;
    @Builder.Default
    private String locator = "_Root";
}
