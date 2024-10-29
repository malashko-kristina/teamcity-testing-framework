package com.example.teamcity.ui.successMessage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CreateProjectMessages {
    public static SelenideElement successMessage = $("#message_projectCreated");

    public static void successMessageText(String projectName) {
        successMessage.shouldHave(Condition.text(
                "Project \"%s\" has been successfully created. You can now create a build configuration."
                        .formatted(projectName)
        ));
    }
}
