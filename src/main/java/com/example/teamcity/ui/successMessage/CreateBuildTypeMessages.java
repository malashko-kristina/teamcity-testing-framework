package com.example.teamcity.ui.successMessage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class CreateBuildTypeMessages {
    public static SelenideElement successMessage = $("#unprocessed_buildTypeCreated");

    public static void successMessageText() {
        successMessage.shouldHave(Condition.text("Build configuration successfully created." +
                " You can now configure VCS roots."));
    }
}
