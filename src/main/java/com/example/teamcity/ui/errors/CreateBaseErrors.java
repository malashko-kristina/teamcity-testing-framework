package com.example.teamcity.ui.errors;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public abstract class CreateBaseErrors {
    protected static SelenideElement projectNameError = $("#error_projectName");
    protected static SelenideElement projectIdError = $("#errorExternalId");
    protected static SelenideElement buildTypeNameError = $("#error_buildTypeName");
    protected static SelenideElement buildTypeIdError = $("#error_buildTypeExternalId");
    protected static SelenideElement urlError = $("#error_url");
    protected static SelenideElement errorProjectName = $("#errorName");


    public static void checkEmptyUrlError() {
        urlError.shouldBe(Condition.visible)
                .shouldHave(Condition.text("URL must not be empty"));
    }
}
