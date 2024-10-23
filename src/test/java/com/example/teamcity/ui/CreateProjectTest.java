package com.example.teamcity.ui;


import com.codeborne.selenide.Condition;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import org.testng.annotations.Test;

import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class CreateProjectTest extends BaseUiTest {
    private static final String REPO_URL = "https://github.com/AlexPshe/spring-core-for-qa";

    @Test(description = "User should be able to create a project", groups = {"Positive"})
    public void userCreatesProject() {
        // подготовка окружения
        step("Login as a user");
        loginAs(testData.getUser());

        // взаимодействие с UI
        step("Open 'Create Project Page' (http://localhost:8111/admin/createObjectMenu.html)");
        step("Send all project parameters (repository URL");
        step("Click 'Proceed'");
        step("Fix Project Name and Build Type name values");
        step("Click 'Proceed'");
        CreateProjectPage.open("_Root")
                        .createForm(REPO_URL)
                        .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

        // проверка состояния API
        // (корректность отправки данных с UI на API)
        step("Check that all entities (project, build type) was successfully created with correct data on API level ");
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS)
                .read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);

        // проверка состояния UI
        // (корректность считывания данных и отображения данных на UI)
        step("Check that project is visible on Project Page (http://localhost:8111/favorite/projects)");
        ProjectPage.open(createdProject.getId())
                .title.shouldHave(Condition.exactText(testData.getProject().getName()));

        var projectExist = ProjectsPage.open()
                .getProjects().stream()
                .anyMatch(project -> project.getName().text().equals(testData.getProject().getName()));
        softy.assertTrue(projectExist);

    }

    @Test(description = "User should not be able to create a project without name", groups = {"Negative"})
    public void userCreatesProjectWithoutName() {
        // подготовка окружения
        step("Login as user");
        step("Check number of projects");

        // взаимодействие с UI
        step("Open 'Create Project Page' (http://localhost:8111/admin/createObjectMenu.html)");
        step("Send all project parameters (repository URL");
        step("Click 'Proceed'");
        step("Set Project Name value is empty");
        step("Click 'Proceed'");

        // проверка состояния API
        // (корректность отправки данных с UI на API)
        step("Check amount of projects didn't change");


        // проверка состояния UI
        // (корректность считывания данных и отображения данных на UI)
        step("Check that error appears 'Project name must not be empty");
    }
}
