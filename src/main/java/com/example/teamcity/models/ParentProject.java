package com.example.teamcity.models;

import com.example.teamcity.annotations.Random;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentProject extends BaseModel {
    @Random
    private String locator;
}
