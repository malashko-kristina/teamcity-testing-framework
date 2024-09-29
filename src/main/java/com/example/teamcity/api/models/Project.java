package com.example.teamcity.api.models;


import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Project extends BaseModel{
    private String id;
    private String name;
    @Builder.Default
    private String locator = "_Root";
}
