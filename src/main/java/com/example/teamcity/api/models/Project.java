package com.example.teamcity.api.models;


import com.example.teamcity.api.annotations.Parameterizable;
import com.example.teamcity.api.annotations.Random;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class  Project extends BaseModel{
    @Random
    @Parameterizable
    private String id;
    @Random
    @Parameterizable
    private String name;
    @Parameterizable
    private String locator;
    @Builder.Default
    @Parameterizable
    private Boolean copyAllAssociatedSettings = true;
    @Parameterizable
    private SourceProject sourceProject;
    private Integer count;

    @Data
    @Builder
    public static class SourceProject {
        @Parameterizable
        private String locator;
    }
}
