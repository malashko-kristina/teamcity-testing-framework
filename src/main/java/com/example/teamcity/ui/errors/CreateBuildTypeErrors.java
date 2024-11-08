package com.example.teamcity.ui.errors;

import com.codeborne.selenide.Condition;

public class CreateBuildTypeErrors extends CreateBaseErrors{

    public static void checkEmptyBuildTypeNameError() {
        buildTypeNameError.shouldBe(Condition.visible)
                .shouldHave(Condition.text("Name must not be empty"));
    }

    public static void checkEmptyBuildTypeIdError() {
        buildTypeIdError.shouldBe(Condition.visible)
                .shouldHave(Condition.text("The ID field must not be empty."));
    }

    public static void checkEmptyUrlBuildTypeError() {
        checkEmptyUrlError();
    }

    public static void checkInvalidBuildTypeIdError(String buildTypeId) {
        buildTypeIdError.shouldHave(Condition.visible)
                .shouldHave(Condition.text(("Build configuration or template ID \"%s\" is invalid:" +
                        " starts with non-letter character '_'. ID should start with a latin letter" +
                        " and contain only latin letters, digits and underscores" +
                        " (at most 225 characters).").formatted(buildTypeId)));
    }
}
