package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class CreateProjectPage extends CreateBasePage {
    private static final String PROJECT_SHOW_MODE = "createProjectMenu";
    private SelenideElement projectNameInput = $("#projectName");

    public static CreateProjectPage openManualCreation(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, PROJECT_SHOW_MODE, createManually), CreateProjectPage.class);
    }

    public static CreateProjectPage openUrlCreation(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, PROJECT_SHOW_MODE, createFromUrl), CreateProjectPage.class);
    }

    public CreateProjectPage createFormWithUrl(String url) {
        baseWithUrlCreateForm(url);
        return this; // возвращаем ту же самую страничку, чтобы поддержать цепочку
    }

    public CreateProjectPage checkConnectionMessage() {
        checkConnectionSuccessfulMessage();
        return this;
    }

    public CreateProjectPage createFormManually (String projectName, String projectId) {
        Selenide.sleep(4000);
        switchToCreateProjectManually();
        baseManualCreateProjectForm(projectName, projectId);
        return this;
    }

    public CreateProjectPage setupProjectAfterUrl(String projectName, String buildTypeName) {
        projectNameInput.shouldBe(Condition.visible, ELEMENT_WAITING );
        projectNameInput.val(projectName);
        buildTypeNameInput.shouldBe(Condition.visible, ELEMENT_WAITING );
        buildTypeNameInput.val(buildTypeName);
        submitButton.shouldBe(Condition.visible, ELEMENT_WAITING );
        submitButton.click();
        return this;
    }
}
