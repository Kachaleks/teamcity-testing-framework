package com.example.teamcity;

import com.example.teamcity.generators.RandomData;
import com.example.teamcity.models.*;
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

    @Test(description = "User should be able to create project with minimum parameters", groups = {"Positive", "CRUD"})
    public void userCreateProjectTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());
        var createdProject = userCheckRequests.<Project>getRequest(PROJECTS).read(testData.getProject().getId());
        softy.assertEquals(testData.getProject().getName(), createdProject.getName(), "Project name is incorrect");
    }

    @Test(description = "User should not be able to create project with non-existing parent", groups = {"Negative", "CRUD"})
    public void userCantCreateProjectWithNonexistParentIdTest() {
        var projectWithNoParent = generate(Arrays.asList(testData.getNewProjectDescription()), NewProjectDescription.class);
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithNoParent)
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by name or internal/external id '%s'.\n".formatted(projectWithNoParent.getParentProject().getLocator())
                        + "Could not find the entity requested. Check the reference is correct and the user has permissions to access the entity"));
    }

    @Test(description = "User should not be able to creating project with existing projectId", groups = {"Negarive", "CRUD"})
    public void userCantCreateProjectWithSameIdTest() {
        var projectWithSameId = generate(Arrays.asList(testData.getProject()), Project.class, testData.getProject().getId());
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        new CheckedRequests(Specifications.authSpec(testData.getUser())).getRequest(PROJECTS).create(testData.getProject());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project ID \"%s\" is already used by another project\n".formatted(testData.getProject().getId()) +
                        "Error occurred while processing this request."));
    }

    @Test(description = "User should not be able to create project with existing name", groups = {"Negarive", "CRUD"})
    public void userCantCreateProjectWithSameNameTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        new CheckedRequests(Specifications.authSpec(testData.getUser())).getRequest(PROJECTS).create(testData.getProject());
        var projectWithSameName = generate(Arrays.asList(testData.getProject()),Project.class, testData.getProject().getId(),testData.getProject().getName());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithSameName)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project with this name already exists: %s\n".formatted(testData.getProject().getName()) +
                        "Error occurred while processing this request."));
    }

    @Test(description = "User cant create project without required fields ", groups = {"Negarive", "CRUD"})
    public void userCantCreateProjectWithoutMustParameters() {
    }

    @Test(description = "User cant create project with invalid fields", groups = {"Negarive", "CRUD"})
    public void userCantCreateProjectWithInvalidParameters() {
    }

    @Test(description = "User cant create project with parameters over limit", groups = {"Negarive", "CRUD"})
    public void userCreateProjectTest7() {
    }
}