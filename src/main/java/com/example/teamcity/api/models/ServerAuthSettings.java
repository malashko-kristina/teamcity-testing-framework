package com.example.teamcity.api.models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerAuthSettings extends BaseModel {
    @Builder.Default
    private Boolean perProjectPermissions = true;
    private AuthModules modules;
}
