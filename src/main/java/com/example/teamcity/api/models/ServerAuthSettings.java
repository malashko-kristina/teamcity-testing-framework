package com.example.teamcity.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerAuthSettings extends BaseModel {
    @Builder.Default
    @Getter
    private Boolean perProjectPermissions = true;
    @Getter
    private AuthModules modules;
}