package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import static com.codeborne.selenide.Selenide.$;

public abstract class CreateBasePage extends BasePage {
    protected static final String CREATE_URL = "/admin/createObjectMenu.html?projectId=%s&showMode=%s";

    protected SelenideElement urlInput = $("input[id='url']");
    protected SelenideElement submitButton = $(Selectors.byAttribute("value", "Proceed"));
    protected SelenideElement buildTypeNameInput = $("#buildTypeName");
    protected SelenideElement buildTypeIdInput = $("#buildTypeExternalId");
    protected SelenideElement connectionSuccessfulMessage = $(".connectionSuccessful");
    protected SelenideElement projectNameInput = $("#name");
    protected SelenideElement projectIdInput = $("#externalId");
    protected SelenideElement createButton = $("input[class*='submitButton']");
    protected SelenideElement createFromUrlLink = $("a[href = '#createFromUrl']");
    protected SelenideElement createManuallyProjectLink = $("a[data-hint-container-id='create-project']");
    protected SelenideElement createManuallyBuildTypeLink = $("a[data-hint-container-id='create-build-configuration']");
    protected SelenideElement description = $("#description");

    protected void baseWithUrlCreateForm(String url) {
        urlInput.val(url);
        submitButton.shouldBe(Condition.visible);
        submitButton.click();
    }

    protected void checkConnectionSuccessfulMessage () {
        connectionSuccessfulMessage.should(Condition.appear, BASE_WAITING);
    }

    protected void baseManualCreateProjectForm(String projectName, String projectId) {
        projectNameInput.val(projectName);
        projectIdInput.val(projectId);
        createButton.shouldBe(Condition.visible);
        createButton.click();
    }

    protected void switchToCreateFromUrl() {
        createFromUrlLink.shouldBe(Condition.visible);
        createFromUrlLink.click();
    }

    protected void switchToCreateProjectManually() {
        createManuallyProjectLink.shouldBe(Condition.visible);
        createManuallyProjectLink.click();
        projectNameInput.shouldBe(Condition.visible);
    }

    protected void switchToCreateBuildTypeManually() {
        createManuallyBuildTypeLink.shouldBe(Condition.visible);
        createManuallyBuildTypeLink.click();
    }

    protected void baseManualCreateBuildTypeForm(String buildTypeName, String buildTypeId) {
        buildTypeNameInput.val(buildTypeName);
        buildTypeIdInput.val(buildTypeId);
        createButton.shouldBe(Condition.visible);
        createButton.click();
    }
}
