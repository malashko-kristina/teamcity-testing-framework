package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;

public class CreateBuildTypePage extends CreateBasePage{
    private static final String BUILD_TYPE_SHOW_MODE = "createBuildTypeMenu";

    public static CreateBuildTypePage openManually(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_TYPE_SHOW_MODE, createManually), CreateBuildTypePage.class);
    }

    public static CreateBuildTypePage openUrl(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, BUILD_TYPE_SHOW_MODE, createFromUrl), CreateBuildTypePage.class);
    }

    public CreateBuildTypePage createFormWithUrl(String url) {
        switchToCreateFromUrl();
        baseWithUrlCreateForm(url);
        return this; // возвращаем ту же самую страничку, чтобы поддержать цепочку
    }

    public CreateBuildTypePage checkConnectionMessage() {
        checkConnectionSuccessfulMessage();
        return this;
    }

    public EditVCSRootPage createFormManually (String buildTypeName, String buildTypeId) {
        Selenide.page(CreateBuildTypePage.class).switchToCreateBuildTypeManually();
        baseManualCreateBuildTypeForm(buildTypeName, buildTypeId);
        return Selenide.page(EditVCSRootPage.class);
    }

    public CreateBuildTypePage setupProject(String buildTypeName) {
        buildTypeNameInput.shouldBe(Condition.visible, ELEMENT_WAITING);
        buildTypeNameInput.val(buildTypeName);
        submitButton.shouldBe(Condition.visible, ELEMENT_WAITING);
        submitButton.click();
        return this;
    }
}
