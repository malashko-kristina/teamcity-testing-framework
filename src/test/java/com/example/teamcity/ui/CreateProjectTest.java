package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.errors.CreateProjectErrors;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import com.example.teamcity.ui.pages.admin.EditProjectPage;
import org.testng.annotations.Test;
import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class CreateProjectTest extends BaseUiTest {

    @Test(description = "User should be able to create a project with URL", groups = {"Positive"})
    public void userCreatesProjectWithUrl() {
        step("Login as a user");
        loginAs(testData.getUser());

        step("Create a first project on API level");
        createFirstProject();

        step("Create a project with URL");
        CreateProjectPage.open("_Root")
                        .createFormWithUrl(REPO_URL).checkConnectionMessage()
                        .setupProjectAfterUrl(testData.getProject().getName(), testData.getBuildType().getName());

        step("Check that all entities (project, build type) was successfully created with correct data on API level ");
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS)
                .read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);

        step("Check that project is visible on Project Page (http://localhost:8111/favorite/projects)");
        ProjectPage.open(createdProject.getId())
                .title.shouldHave(Condition.exactText(testData.getProject().getName()));

        var projectExist = ProjectsPage.open()
                .getProjects().stream()
                .anyMatch(project -> project.getName().text().equals(testData.getProject().getName()));
        softy.assertTrue(projectExist);

        step("Clean up of created projects on API level");
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.PROJECTS, createdProject);
    }

    @Test(description = "User should not be able to create a project with empty URL", groups = {"Negative"})
    public void userCreatesProjectWithEmptyUrl() {
        step("Login as a user");
        loginAs(testData.getUser());

        step("Create a first project on API level");
        createFirstProject();

        step("Check number of projects");
        int count = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).read("").getCount();

        step("Create a project with empty URL");
        CreateProjectPage.open("_Root").createFormWithUrl("");

        step("Check that error appears 'URL must not be empty'");
        CreateProjectErrors.checkEmptyUrlError();

        step("Check amount of projects didn't change");
        int newCount = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).read("").getCount();
        softy.assertEquals(newCount, count);
    }

    @Test(description = "User should not be able to create a project with empty name", groups = {"Negative"})
    public void userCreatesProjectWithoutName() {

        step("Login as user");
        loginAs(testData.getUser());

        step("Create a first project on API level");
        createFirstProject();

        step("Check number of projects");
        int count = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).read("").getCount();

        step("Create a project with empty name");
        CreateProjectPage.open("_Root")
                .createFormWithUrl(REPO_URL)
                .setupProjectAfterUrl("", testData.getBuildType().getName());

        step("Check that error appears 'Project name must not be empty");
        CreateProjectErrors.checkEmptyProjectNameErrorUrl();

        step("Check amount of projects didn't change");
        int newCount = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).read("").getCount();
        softy.assertEquals(newCount, count);

        step("Check that project with empty name is not being shown");
        var noEmptyProjectNames = ProjectsPage.open()
                .getProjects().stream()
                .noneMatch(project -> project.getName().text().equals(""));
        softy.assertTrue(noEmptyProjectNames);
    }

    @Test(description = "User should be able to create a project manually", groups = {"Positive"})
    public void userCreatesProjectManually() {

        step("Login as a user");
        loginAs(testData.getUser());

        step("Create a first project on API level");
        createFirstProject();

        step("Create a project manually");
        CreateProjectPage.open("_Root");
        CreateProjectPage.open("_Root")
                .createFormManually(testData.getProject().getName(),testData.getProject().getId());
        EditProjectPage.checkSuccessMessageText(testData.getProject().getName());

        step("Check that project was successfully created with correct data on API level");
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS)
                .read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);

        step("Check that project is visible on Project Page (http://localhost:8111/favorite/projects)");
        ProjectPage.open(createdProject.getId())
                .title.shouldHave(Condition.exactText(testData.getProject().getName()));

        var projectExist = ProjectsPage.open()
                .getProjects().stream()
                .anyMatch(project -> project.getName().text().equals(testData.getProject().getName()));
        softy.assertTrue(projectExist);

        step("Clean up of created projects on API level");
        TestDataStorage.getStorage().addCreatedEntity(Endpoint.PROJECTS, createdProject);
    }

    @Test(description = "User should not be able to create a project manually with empty project name", groups = {"Negative"})
    public void userCreatesProjectManuallyWithoutProjectName() {

        step("Login as a user");
        loginAs(testData.getUser());

        step("Create a first project on API level");
        createFirstProject();

        step("Check a number of projects on API level");
        int count = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).read("").getCount();

        step("Create a project with empty project id");
        CreateProjectPage.open("_Root")
                .createFormManually("",testData.getProject().getId());
        CreateProjectErrors.checkEmptyProjectNameErrorManual();

        step("Check amount of projects didn't change");
        int newCount = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).read("").getCount();
        softy.assertEquals(newCount, count);

        step("Check that project with this name is not being shown");
        var projectExist = ProjectsPage.open()
                .getProjects().stream()
                .noneMatch(project -> project.getName().text().equals(""));
        softy.assertTrue(projectExist);
    }
}
