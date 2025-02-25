package com.example.teamcity.models;

import com.example.teamcity.annotations.Parameterizable;
import com.example.teamcity.annotations.Random;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewProjectDescription extends BaseModel {
    private ParentProject parentProject;
    @Random
    private String name;
    @Random
    private Integer id;
    @Random
    private boolean copyAllAssociatedSettings;
}


