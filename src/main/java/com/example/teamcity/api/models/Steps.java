package com.example.teamcity.api.models;


import lombok.*;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Steps extends BaseModel{
    private Integer count;
    private List<Step> step;

}
