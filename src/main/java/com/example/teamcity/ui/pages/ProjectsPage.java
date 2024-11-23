package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BuildTypeElement;
import com.example.teamcity.ui.elements.ProjectElement;
import io.qameta.allure.Step;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class ProjectsPage extends BasePage {
    private static final String PROJECTS_URL = "/favorite/projects";

    private ElementsCollection projectElements = $$("div[class*='Subproject__container']");
    private SelenideElement header = $(".MainPanel__router--gF > div");
    private SelenideElement welcomeMessage = $("h1[class*= 'ring-global-font']");
    private SelenideElement createProjectButton = $("a[data-test='create-project']");
    private ElementsCollection buildTypeElements = $$("div[class*='Subproject__details--fL Subproject__nested--aD']");

    // ElementsCollection -> List<ProjectElement>
    // UI elements -> лист объектов List<Object>
    // ElementsCollection -> List<BasePageElement>

    @Step("Open a projects page")
    public static ProjectsPage open() {
        return Selenide.open(PROJECTS_URL, ProjectsPage.class);
    }

    public ProjectsPage() {
        this.header.shouldBe(Condition.visible, BASE_WAITING);
    }

    public List<ProjectElement> getProjects() {
        return generatePageElements(projectElements, ProjectElement::new);
    }

    @Step("Tap on each project in a projects list")
    public ProjectsPage clickAllProjects() {
        getProjects().stream().forEach(ProjectElement::click);
        return this;
    }

    public List<BuildTypeElement> getBuildTypes() {
        return generatePageElements(buildTypeElements, BuildTypeElement::new);
    }
}
