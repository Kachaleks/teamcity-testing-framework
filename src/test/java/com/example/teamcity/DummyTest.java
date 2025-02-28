package com.example.teamcity;

import io.restassured.RestAssured;
import com.example.teamcity.models.User;
import org.testng.annotations.Test;
import com.example.teamcity.spec.Specifications;

public class DummyTest extends BaseApiTest {
    @Test
    public void userShouldBeAbleGetAllProjects() {
        RestAssured
                .given()
                .spec(Specifications
                        .authSpec(User.builder()
                                .username("admin").password("admin")
                                .build()))
                .get("/app/rest/projects");
    }
}
