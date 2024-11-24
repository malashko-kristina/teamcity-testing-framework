package com.example.teamcity.api.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Step extends BaseModel {
    private String id;
    private String name;
    @Builder.Default
    private String type = "simpleRunner";
}
