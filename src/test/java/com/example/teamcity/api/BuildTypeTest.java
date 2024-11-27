package com.example.teamcity.api;
import com.example.teamcity.api.models.*;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import io.qameta.allure.Step;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import java.util.Arrays;
import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD", "BuildType"})
    public void userCreatesBuildTypeTest() {

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());

        var createdBuildType = userCheckRequests.<BuildType>getRequest(BUILD_TYPES).read("id:" + testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
        softy.assertEquals(testData.getBuildType().getId(), createdBuildType.getId(), "Build type ID is not correct");
    }

    @Test(description = "User should not be able to create two build types with the same id", groups = {"Negative", "CRUD", "BuildType"})
    public void userCreatesTwoBuildTypeWithTheSameIdTest() {
        var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        userCheckRequests.getRequest(BUILD_TYPES).create(testData.getBuildType());
        new UncheckedBase(Specifications.authSpec(testData.getUser()), BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(testData.getBuildType().getId())));
    }

    @Test(description = "Project admin should be able to create a build type for their project", groups = {"Positive", "Roles", "BuildType"})
    public void projectAdminCreatesBuildType() {

        superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());

        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));

        superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());

        var projectAdminCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var newBuildType = generate(Arrays.asList(testData.getProject()), BuildType.class);
        projectAdminCheckRequests.getRequest(BUILD_TYPES).create(newBuildType);

        var createdBuildType = projectAdminCheckRequests.<BuildType>getRequest(BUILD_TYPES).read("id:" + newBuildType.getId());
        softy.assertEquals(newBuildType.getName(), createdBuildType.getName(), "Build type name is not correct");
    }


        @Test(description = "Project admin should not be able to create a build type for not their project", groups = {"Negative", "Roles"})
        public void projectAdminCreatesBuildTypForAnotherUserProjectTest () {

            superUserCheckRequests.getRequest(PROJECTS).create(testData.getProject());

            testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));

            superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());

            var secondProject = generate(Project.class);
            superUserCheckRequests.getRequest(PROJECTS).create(secondProject);

            var rolesForSecondUser = generate(Roles.class, "PROJECT_ADMIN", "p:" + secondProject.getId());

            var secondProjectAdmin = generate(Arrays.asList(rolesForSecondUser), User.class);
            superUserCheckRequests.<User>getRequest(USERS).create(secondProjectAdmin);

            var buildTypeForFirstProject = generate(Arrays.asList(testData.getProject()), BuildType.class);
            var secondProjectAdminRequests = new UncheckedBase(Specifications.authSpec(secondProjectAdmin), BUILD_TYPES);

            secondProjectAdminRequests.create(buildTypeForFirstProject).then().statusCode(HttpStatus.SC_FORBIDDEN)
                    .body(Matchers.containsString("Access denied. Check the user has enough permissions to perform the operation."));
        }
    }



