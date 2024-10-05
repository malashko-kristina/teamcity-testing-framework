package com.example.teamcity.api.models;

import com.example.teamcity.api.annotations.Parameterizable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends BaseModel {
    @Builder.Default
    @Parameterizable
    private String roleId = "SYSTEM_ADMIN";
    @Builder.Default
    @Parameterizable
    private String scope = "g";
}
