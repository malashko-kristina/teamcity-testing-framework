package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.successMessage.CreateBuildTypeMessages;

import static com.codeborne.selenide.Selenide.$;

public class EditVCSRootPage extends EditBasePage {
    private static final String VCS_ROOT = "VcsRoot.html";
    private SelenideElement skipButton = $("a[class*='cancel']");

    public static EditVCSRootPage open() {
        if (!isPageOpened()) {
            Selenide.open((BASE_EDIT_URL).formatted(VCS_ROOT), EditVCSRootPage.class);
        }
        return Selenide.page(EditVCSRootPage.class);
    }

    public static boolean isPageOpened() {
        return CreateBuildTypeMessages.successMessage.exists();
    }

    public void checkCreateBuildTypeSuccessMessage() {
        CreateBuildTypeMessages.successMessageText();
    }
}
