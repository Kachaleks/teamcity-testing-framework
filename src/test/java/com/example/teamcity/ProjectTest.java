package com.example.teamcity;

import com.example.teamcity.generators.RandomData;
import com.example.teamcity.models.BuildType;
import com.example.teamcity.models.NewProjectDescription;
import com.example.teamcity.models.Project;
import com.example.teamcity.models.User;
import com.example.teamcity.requests.checked.CheckedRequests;
import com.example.teamcity.requests.unchecked.UncheckedBase;
import com.example.teamcity.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import java.util.Arrays;

import static com.example.teamcity.enums.Endpoint.PROJECTS;
import static com.example.teamcity.enums.Endpoint.USERS;
import static com.example.teamcity.generators.TestDataGenerator.generate;

@Test(groups = "Smoke")
public class ProjectTest extends BaseApiTest {

    @Test(description = "Creating project with minimum parameters", groups = {"Positive", "CRUD"})
    public void userCreateProjectTest() {
//        создать юзера
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
//        Создать проект
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
//        Получить созданный проект через апи
        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).read(testData.getProject().getId());
        softy.assertEquals(testData.getProject().getName(), createdProject.getName(), "Project name is incorrect");
    }

    @Test(description = "User should not be able to create project with not existing parent", groups = {"Negative", "CRUD"})
    public void userCreateProjectTest1() {

        var projectWithNoParent = generate(Arrays.asList(testData.getNewProjectDescription()), NewProjectDescription.class);

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

//        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithNoParent)
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by name or internal/external id '%s'.\n".formatted(projectWithNoParent.getParentProject().getLocator())
                        + "Could not find the entity requested. Check the reference is correct and the user has permissions to access the entity"));
    }

    @Test(description = "Creating project with existing projectId", groups = {"Negarive", "CRUD"})
    public void userCreateProjectTest2() {
    }

    @Test(description = "Creating project with existing ?name?", groups = {"Negarive", "CRUD"})
    public void userCreateProjectTest3() {
    }

    @Test(description = "Creating project without обязательные поля", groups = {"Negarive", "CRUD"})
    public void userCreateProjectTest4() {
    }

    @Test(description = "Creating project with invalid fields", groups = {"Negarive", "CRUD"})
    public void userCreateProjectTest5() {
    }

    @Test(description = "Creating project with invalid fields", groups = {"Negarive", "CRUD"})
    public void userCreateProjectTest6() {
    }

    @Test(description = "Creating project with limits", groups = {"Negarive", "CRUD"})
    public void userCreateProjectTest7() {
    }
}