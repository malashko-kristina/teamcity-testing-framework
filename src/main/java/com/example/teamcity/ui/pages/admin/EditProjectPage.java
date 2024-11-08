package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.successMessage.CreateProjectMessages;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class EditProjectPage extends EditBasePage {
    private static final String PROJECT = "Project.html";
    private SelenideElement saveButton = $("input[class*='submitButton']");
    private SelenideElement createBuildConfigurationButton = $("span[class*='addNew']")
            .shouldHave(text("Create build configuration"));

    public static EditProjectPage open() {
        if (!isPageOpened()) {
            Selenide.open((BASE_EDIT_URL).formatted(PROJECT), EditProjectPage.class);
        } return Selenide.page(EditProjectPage.class);
    }

    private static boolean isPageOpened() {
        return CreateProjectMessages.successMessage.exists();
    }

    public static void checkSuccessMessageText(String projectName) {
        CreateProjectMessages.successMessageText(projectName);
    }
}
