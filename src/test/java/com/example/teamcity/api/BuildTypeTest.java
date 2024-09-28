package com.example.teamcity.api;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest{
    @Test(description = "User should be able to create a build type", groups = {"Positive", "CRUD"}) // почему тут регрешон в фигурных скобках?
    public void userCreatesBuildTypeTest() {
        step("Create user");
        step("Create project by user");
        step("Create buildType for project by user");
        step("Check buildType was created successfully with correct data");
    }

    @Test(description = "User should not be able to create two build types with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypeWithTheSameTest() {
        step("Create user");
        step("Create project by user");
        step("Create buildType1 for project by user");
        step("Create buildType2 with the same id as buildType1 for project by user");
        step("Check buildType wasn't created with a bad request code");
    }

    @Test(description = "Project admin should be able to create a build type for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildType() {
        step("Create user");
        step("Create project");
        step("Grant user PROJECT_ADMIN role in project");

        step("Create buildType for project by user (PROJECT_ADMIN)");
        step("Check buildType was created successfully");
    }

    @Test(description = "Project admin should not be able to create a build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypForAnotherUserProjectTest() {
        step("Create user1");
        step("Create project1");
        step("Grant user1 PROJECT_ADMIN role in project1");

        step("Create user2");
        step("Create project2");
        step("Grant user2 PROJECT_ADMIN role in project2");

        step("Create buildType for project1 by user2");
        step("Check buildType wasn't created with a forbidden (403) code");
    }
}
