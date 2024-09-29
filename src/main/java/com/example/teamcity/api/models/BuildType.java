package com.example.teamcity.api.models;


import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class BuildType extends BaseModel{
    private String id;
    private String name;
    private Project project;
    private Steps steps;
}
