package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.teamcity.BaseTest;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import com.example.teamcity.ui.components.Footer;
import com.example.teamcity.ui.components.Header;
import com.example.teamcity.ui.pages.LoginPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import com.example.teamcity.ui.pages.admin.EditProjectPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import java.util.Arrays;
import java.util.Map;

import static com.example.teamcity.api.enums.Endpoint.USERS;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

public class BaseUiTest extends BaseTest {
    protected static final String REPO_URL = "https://github.com/AlexPshe/spring-core-for-qa";
    protected  static final Project firstProject = generate(Project.class);
    protected static Footer footer;
    protected static Header header;

    @BeforeSuite(alwaysRun = true)
    public void setupUiTest() {
        Configuration.browser = Config.getProperty("browser");
        Configuration.baseUrl = "http://" + Config.getProperty("host");
        Configuration.remote = Config.getProperty("remote");
        Configuration.browserSize = Config.getProperty("browserSize");

        Configuration.browserCapabilities.setCapability("selenoid:options",
                Map.of("enableVNC", true,
                        "enableLog", true));

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        Selenide.closeWebDriver();
    }

    protected void loginAs(User user) {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());
        LoginPage.open().login(testData.getUser());
    }

    protected void createProject() {
        CreateProjectPage.open("_Root")
                .createFormManually(testData.getProject().getName(),testData.getProject().getId());
        EditProjectPage.checkSuccessMessageText(testData.getProject().getName());
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS)
                .read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);
    }

    protected void createFirstProject() {
        superUserCheckRequests.getRequest(Endpoint.PROJECTS).create(firstProject);
    }

    protected void createBuildType() {
        var firstBuildType = generate(Arrays.asList(testData.getProject()), BuildType.class);
        superUserCheckRequests.getRequest(Endpoint.BUILD_TYPES).create(firstBuildType);
    }

    protected void loggedInWithCreatedProjectAndBuildType (User user) {
        loginAs(user);
        createFirstProject();
        createProject();
        createBuildType();
    }

    protected void loggedInWithCreatedProject (User user) {
        loginAs(user);
        createFirstProject();
        createProject();
    }
}
