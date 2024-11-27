package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class BuildTypePage extends BasePage {
    private static final String BUILD_TYPE_URL = "/buildConfiguration/%s";

    public SelenideElement title = $("h1 > span");

    @Step("Open a buildType page")
    public static BuildTypePage open(String buildTypeId) {
        return Selenide.open(BUILD_TYPE_URL.formatted(buildTypeId), BuildTypePage.class);
    }
}
