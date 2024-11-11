package com.example.teamcity.ui.errors;

import com.codeborne.selenide.Condition;

public class CreateProjectErrors extends CreateBaseErrors {

    public static void checkEmptyProjectNameErrorUrl() {
        projectNameError.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Project name must not be empty"));
    }

    public static void checkEmptyProjectNameErrorManual() {
        errorProjectName.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Project name is empty"));
    }

    public static void checkEmptyProjectIdErrorManual() {
        projectIdError.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Project ID must not be empty."));
    }
}
