package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public abstract class CreateBasePage extends BasePage {
    protected static final String CREATE_URL = "/admin/createObjectMenu.html?projectId=%s&showMode=%s#%s";
    protected static final String createFromUrl = "createFromUrl";
    protected static final String createManually = "createManually";

    protected SelenideElement urlInput = $("input[id='url']");
    protected SelenideElement submitButton = $(Selectors.byAttribute("value", "Proceed"));
    protected SelenideElement buildTypeNameInput = $("#buildTypeName");
    protected SelenideElement buildTypeIdInput = $("#buildTypeExternalId");
    protected SelenideElement connectionSuccessfulMessage = $(".connectionSuccessful");
    protected SelenideElement projectNameInput = $("input#name.longField");
    protected SelenideElement projectIdInput = $("#externalId");
    protected SelenideElement createButton = $("input[class*='submitButton']");
    protected SelenideElement createFromUrlLink = $("a[href = '#createFromUrl']");
    protected SelenideElement createManuallyProjectLink = $("a[href='#createManually']");
    protected SelenideElement createManuallyBuildTypeLink = $("a[data-hint-container-id='create-build-configuration']");
    protected SelenideElement description = $("#description");

    protected void baseWithUrlCreateForm(String url) {
        urlInput.shouldBe(Condition.appear);
        urlInput.val(url);
        submitButton.shouldBe(Condition.visible);
        submitButton.click();
    }

    protected void checkConnectionSuccessfulMessage () {
        connectionSuccessfulMessage.should(Condition.appear, BASE_WAITING);
    }

    protected void baseManualCreateProjectForm(String projectName, String projectId) {
        projectNameInput.shouldBe(Condition.visible);
        projectNameInput.val(projectName);
        projectIdInput.shouldBe(Condition.appear);
        projectIdInput.val(projectId);
        createButton.shouldBe(Condition.clickable);
        createButton.click();
    }

    protected void switchToCreateFromUrl() {
        createFromUrlLink.shouldBe(Condition.visible);
        createFromUrlLink.click();
    }

    protected void switchToCreateProjectManually() {
        Selenide.sleep(2000);
        createManuallyProjectLink.shouldBe(Condition.visible);
        createManuallyProjectLink.click();
        createManuallyProjectLink.click();
        Selenide.sleep(2000);
        projectNameInput.shouldBe(Condition.visible);
    }

    protected void switchToCreateBuildTypeManually() {
        createManuallyBuildTypeLink.shouldBe(Condition.clickable);
        createManuallyBuildTypeLink.click();
    }

    protected void baseManualCreateBuildTypeForm(String buildTypeName, String buildTypeId) {
        buildTypeNameInput.val(buildTypeName);
        buildTypeIdInput.val(buildTypeId);
        createButton.shouldBe(Condition.clickable);
        createButton.click();
    }
}
