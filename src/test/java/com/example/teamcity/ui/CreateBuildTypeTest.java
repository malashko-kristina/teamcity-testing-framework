package com.example.teamcity.ui;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.errors.CreateBuildTypeErrors;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateBuildTypePage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import com.example.teamcity.ui.successMessage.CreateBuildTypeMessages;
import org.testng.annotations.Test;
import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class CreateBuildTypeTest extends BaseUiTest {

    @Test(description = "User should be able to create a build type with Url", groups = {"Positive"})
    public void userCreatesBuildTypeWithUrl() {

        step("Login as a user and create a project");
        loggedInWithCreatedProject(testData.getUser());

        step("Check create buildType with URL");
        CreateBuildTypePage.openUrl(testData.getProject().getId()).createFormWithUrl(REPO_URL).checkConnectionMessage()
                .setupProject(testData.getBuildType().getName());

        step("Check that build type was successfully created with correct data on API level ");
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES)
                .read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);
        softy.assertEquals(createdBuildType.getProject().getId(),testData.getProject().getId());

        step("Check that build type was successfully created with correct data on UI level ");
        var buildTypeExists = ProjectsPage.open().clickAllProjects().getBuildTypes().stream()
                .anyMatch(buildType -> buildType.getName().text().equals(testData.getBuildType().getName()));
        softy.assertTrue(buildTypeExists);
        softy.assertEquals(createdBuildType.getProject().getId(),testData.getProject().getId());

        step("Clean up of created projects on API level");
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.PROJECTS, testData.getProject());
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.BUILD_TYPES, createdBuildType);
    }

    @Test(description = "User should be able to create buildType by creating project with URL", groups = {"Positive"})
    public void userCreatesBuildTypeAutomaticallyByProjectCreationWithUrl() {

        step("Login as a user");
        loginAs(testData.getUser());

        step("Create a first project on API level");
        createFirstProject();

        step("Create a project with URL");
        CreateProjectPage.openUrlCreation("_Root")
                .createFormWithUrl(REPO_URL).checkConnectionMessage()
                .setupProjectAfterUrl(testData.getProject().getName(), testData.getBuildType().getName());

        step("Check that build type was successfully created with correct data on UI level");
        var buildTypeExists = ProjectsPage.open().clickAllProjects().getBuildTypes().stream()
                .anyMatch(buildType -> buildType.getName().text().equals(testData.getBuildType().getName()));
        softy.assertTrue(buildTypeExists);

        step("Check that build type was successfully created with correct data on API level");
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS)
                .read("name:" + testData.getProject().getName());
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES)
                .read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);
        softy.assertEquals(createdBuildType.getProject().getName(),testData.getProject().getName());

        step("Clean up of created projects on API level");
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.PROJECTS, createdProject);
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.BUILD_TYPES, createdBuildType);
    }

    @Test(description = "User should not be able to create a build type with empty Url", groups = {"Negative"})
    public void userCreatesBuildTypeWithEmptyUrl() {

        step("Login as a user and create a project");
        loggedInWithCreatedProject(testData.getUser());

        step("Check number of buildTypes");
        int count = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("").getCount();

        step("Check that buildType wasn't created with empty URL");
        CreateBuildTypePage.openUrl(testData.getProject().getId()).createFormWithUrl("");
        CreateBuildTypeErrors.checkEmptyUrlBuildTypeError();

        step("Check that amount of build types wasn't changed on API level");
        int newCount = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("").getCount();
        softy.assertEquals(newCount,count);

        step("Clean up of created projects on API level");
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.PROJECTS, testData.getProject());
    }

    @Test(description = "User should be able to create a build type manually", groups = {"Positive"})
    public void userCreatesBuildTypeManually() {

        step("Login as a user and create a project");
        loggedInWithCreatedProject(testData.getUser());

        step("Create a buildType manually");
        CreateBuildTypePage.openManually(testData.getProject().getId())
                .createFormManually(testData.getBuildType().getName(),testData.getBuildType().getId());
        CreateBuildTypeMessages.successMessageText();

        step("Check that build type was successfully created with correct data on API level");
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES)
                .read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);
        softy.assertEquals(createdBuildType.getProject().getId(),testData.getProject().getId());

        step("Check that build type was successfully created with correct data on UI level");
        var buildTypeExists = ProjectsPage.open().clickAllProjects().getBuildTypes().stream()
                .anyMatch(buildType -> buildType.getName().text().equals(testData.getBuildType().getName()));
        softy.assertTrue(buildTypeExists);
        softy.assertEquals(createdBuildType.getProject().getId(),testData.getProject().getId());

        step("Clean up of created projects on API level");
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.PROJECTS, testData.getProject());
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.BUILD_TYPES, createdBuildType);
    }

    @Test(description = "User should not be able to create manually a build type with empty buildType name", groups = {"Negative"})
    public void userCreatesBuildTypeWithEmptyBuildTypeName() {

        step("Login as a user, create a project and build type");
        loggedInWithCreatedProjectAndBuildType(testData.getUser());

        step("Check number of buildTypes");
        int count = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("").getCount();

        step("Check that buildType wasn't created with empty buildType name");
        CreateBuildTypePage.openManually(testData.getProject().getId())
                .createFormManually("", testData.getBuildType().getId());
        CreateBuildTypeErrors.checkEmptyBuildTypeNameError();

        step("Check that amount of build types wasn't changed on API level");
        int newCount = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("").getCount();
        softy.assertEquals(newCount,count);

        step("Check that buildType with empty name is not being shown");
        var noEmptyBuildTypeName = ProjectsPage.open().clickAllProjects().getBuildTypes()
                .stream().noneMatch(buildType -> buildType.getName().text().equals(""));
        softy.assertTrue(noEmptyBuildTypeName);

        step("Clean up of created projects on API level");
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.PROJECTS, testData.getProject());
    }

    @Test(description = "User should not be able to create manually a build type with empty buildType id", groups = {"Negative"})
    public void userCreatesBuildTypeWithEmptyBuildTypeId() {

        step("Login as a user, create a project and build type");
        loggedInWithCreatedProjectAndBuildType(testData.getUser());

        step("Check number of buildTypes");
        int count = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("").getCount();

        step("Check that buildType wasn't created with empty buildType name");
        CreateBuildTypePage.openManually(testData.getProject().getId())
                .createFormManually(testData.getBuildType().getName(), "");
        CreateBuildTypeErrors.checkEmptyBuildTypeIdError();

        step("Check that buildType with empty id is not being shown");
        var noEmptyBuildTypeName = ProjectsPage.open().clickAllProjects().getBuildTypes()
                .stream().noneMatch(buildType -> buildType.getName().text().equals(testData.getBuildType().getName()));
        softy.assertTrue(noEmptyBuildTypeName);

        step("Check that amount of build types wasn't changed on API level");
        int newCount = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("").getCount();
        softy.assertEquals(newCount,count);

        step("Clean up of created projects on API level");
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.PROJECTS, testData.getProject());
    }

    @Test(description = "User should not be able to create manually a build type with invalid buildType id", groups = {"Negative"})
    public void userCreatesBuildTypeWithInvalidBuildTypeId() {

        step("Login as a user, create a project and build type");
        loggedInWithCreatedProjectAndBuildType(testData.getUser());

        step("Check number of buildTypes");
        int count = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("").getCount();

        step("Check that buildType wasn't created with empty buildType name");
        var invalidBuildTypeId = RandomData.getStringWith_();
        CreateBuildTypePage.openManually(testData.getProject().getId())
                .createFormManually(testData.getBuildType().getName(), invalidBuildTypeId);
        CreateBuildTypeErrors.checkInvalidBuildTypeIdError(invalidBuildTypeId);

        step("Check that buildType with invalid id is not being shown");
        var noEmptyBuildTypeName = ProjectsPage.open().clickAllProjects().getBuildTypes()
                .stream().noneMatch(buildType -> buildType.getName().text().equals(testData.getBuildType().getName()));
        softy.assertTrue(noEmptyBuildTypeName);

        step("Check that amount of build types wasn't changed on API level");
        int newCount = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("").getCount();
        softy.assertEquals(newCount,count);

        step("Clean up of created projects on API level");
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.PROJECTS, testData.getProject());
    }
}
