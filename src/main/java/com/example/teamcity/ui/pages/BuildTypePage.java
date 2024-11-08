package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class BuildTypePage extends BasePage {
    private static final String BUILD_TYPE_URL = "/buildConfiguration/%s";

    public SelenideElement title = $("h1 > span");

    public static BuildTypePage open(String buildTypeId) {
        return Selenide.open(BUILD_TYPE_URL.formatted(buildTypeId), BuildTypePage.class);
    }
}
