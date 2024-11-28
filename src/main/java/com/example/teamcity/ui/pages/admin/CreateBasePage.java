package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;

import java.time.Duration;

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
        urlInput.shouldBe(Condition.visible, ELEMENT_WAITING );
        urlInput.val(url);
        submitButton.shouldBe(Condition.visible, ELEMENT_WAITING);
        submitButton.click();
    }

    protected void checkConnectionSuccessfulMessage () {
        connectionSuccessfulMessage.should(Condition.appear, BASE_WAITING);
    }

    protected void baseManualCreateProjectForm(String projectName, String projectId) {
        projectNameInput.shouldBe(Condition.visible, ELEMENT_WAITING);
        projectNameInput.val(projectName);
        projectIdInput.shouldBe(Condition.visible, ELEMENT_WAITING);
        projectIdInput.val(projectId);
        createButton.shouldBe(Condition.visible, ELEMENT_WAITING);
        createButton.click();
    }

    protected void switchToCreateFromUrl() {
        createFromUrlLink.shouldBe(Condition.visible, ELEMENT_WAITING);
        createFromUrlLink.click();
    }

    protected void switchToCreateProjectManually() {
        Selenide.sleep(2000);
        createManuallyProjectLink.shouldBe(Condition.visible, ELEMENT_WAITING);
        createManuallyProjectLink.click();
        Selenide.sleep(2000);
        projectNameInput.shouldBe(Condition.visible, ELEMENT_WAITING);
    }

    protected void switchToCreateBuildTypeManually() {
        createManuallyBuildTypeLink.shouldBe(Condition.visible, ELEMENT_WAITING);
        createManuallyBuildTypeLink.click();
    }

    protected void baseManualCreateBuildTypeForm(String buildTypeName, String buildTypeId) {
        buildTypeNameInput.shouldBe(Condition.visible, ELEMENT_WAITING);
        buildTypeNameInput.val(buildTypeName);
        buildTypeIdInput.shouldBe(Condition.visible, ELEMENT_WAITING);
        buildTypeIdInput.val(buildTypeId);
        createButton.shouldBe(Condition.visible, ELEMENT_WAITING);
        createButton.click();
    }
}
