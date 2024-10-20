package com.example.teamcity.ui;


import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.User;
import com.example.teamcity.ui.pages.LoginPage;
import org.testng.annotations.Test;

import static com.example.teamcity.api.enums.Endpoint.USERS;
import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class CreateProjectTest extends BaseUiTest {
    @Test(description = "User should be able to create a project", groups = {"Positive"})
    public void userCreatesProject() {
        // подготовка окружения
        step("Login as user");
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        LoginPage.open().login(testData.getUser());


        // взаимодействие с UI
        step("Open 'Create Project Page' (http://localhost:8111/admin/createObjectMenu.html)");
        step("Send all project parameters (repository URL");
        step("Click 'Proceed'");
        step("Fix Project Name and Build Type name values");
        step("Click 'Proceed'");

        // проверка состояния API
        // (корректность отправки данных с UI на API)
        step("Check that all entities (project, build type) was successfully created with correct data on API level ");

        // проверка состояния UI
        // (корректность считывания данных и отображения данных на UI)
        step("Check that project is visible on Project Page (http://localhost:8111/favorite/projects)");
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
