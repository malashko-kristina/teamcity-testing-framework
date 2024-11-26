package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CreateProjectPage extends CreateBasePage {
    private static final String PROJECT_SHOW_MODE = "createProjectMenu";
    private SelenideElement projectNameInput = $("input[id='projectName']");

    public static CreateProjectPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, PROJECT_SHOW_MODE), CreateProjectPage.class);
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
        switchToCreateProjectManually();
        baseManualCreateProjectForm(projectName, projectId);
        return this;
    }

    public CreateProjectPage setupProject(String projectName, String buildTypeName) {
        projectNameInput.val(projectName);
        buildTypeNameInput.val(buildTypeName);
        submitButton.click();
        return this;
    }
}
