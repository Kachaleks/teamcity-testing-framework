package com.example.demo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import models.User;
import org.testng.annotations.Test;
import spec.Specifications;

public class DummyTest extends BaseApiTest {
    @Test
    public void userShouldBeAbleGetAllProjects() {
        RestAssured
                .given()
                .spec(Specifications.getSpec()
                        .authSpec(User.builder()
                                .username("admin").password("admin")
                                .build()))
                .get("/app/rest/projects");
    }
}
