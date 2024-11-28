package com.example.teamcity.api;
import com.example.teamcity.api.generators.RandomData;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import static com.example.teamcity.api.enums.Endpoint.*;
import static com.example.teamcity.api.generators.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseApiTest {

    @Test(description = "User should be able to create a project with correct data", groups = {"Positive", "CRUD", "Project"})
    public void userCreatesProjectTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var project = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        softy.assertEquals(testData.getProject().getId(), project.getId(), "Project ID is not correct");
        softy.assertEquals(testData.getProject().getName(), project.getName(), "Project name is not correct");

        var checkCreatedProject = userCheckRequests.<Project>getRequest(PROJECTS).read("id:" + project.getId());
        softy.assertEquals(project.getId(), checkCreatedProject.getId(), "Created Project ID is not correct");

        userCheckRequests.getRequest(PROJECTS).delete(checkCreatedProject.getId());

        new UncheckedRequests(Specifications.authSpec(testData.getUser()))
                .getRequest(PROJECTS)
                .read("id:" + checkCreatedProject.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(checkCreatedProject.getId())));
    }

    @Test(description = "User should not be able to create a project with empty id", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesProjectWithEmptyIdTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new UncheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectEmptyId = generate(Project.class, "");

        userCheckRequests.getRequest(PROJECTS)
                .create(projectEmptyId)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID must not be empty"));

        new UncheckedRequests(Specifications.authSpec(testData.getUser()))
                .getRequest(PROJECTS)
                .read("id:" + projectEmptyId.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(projectEmptyId.getId())));
    }

    @Test(description = "User should not be able to create a project if id starts with number", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesProjectWithIdStartsWithNumberTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userUncheckRequests = new UncheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectIdWithNumber = generate(Project.class, RandomData.getStringWithNumber());

        userUncheckRequests.getRequest(PROJECTS)
                .create(projectIdWithNumber)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString(("Project ID \"%s\" is invalid: starts with non-letter character \'%c\'." +
                        " ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters).")
                        .formatted(projectIdWithNumber.getId(), projectIdWithNumber.getId().charAt(0))));

        userUncheckRequests.getRequest(PROJECTS)
                .read("id:" + projectIdWithNumber.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(projectIdWithNumber.getId())));
    }

    @Test(description = "User should not be able to create a project if id includes invalid symbols", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesProjectWithIdWithInvalidSymbolsTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userUncheckRequests = new UncheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectIdWithInvalidSymbol = generate(Project.class, RandomData.getStringWithRandomSpecialCharacter());

        userUncheckRequests.getRequest(PROJECTS)
                .create(projectIdWithInvalidSymbol)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID \"%s\" is invalid: contains unsupported character "
                        .formatted(projectIdWithInvalidSymbol.getId())));

        userUncheckRequests.getRequest(PROJECTS)
                .read("id:" + projectIdWithInvalidSymbol.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Could not find the entity requested. Check the reference" +
                        " is correct and the user has permissions to access the entity"));

    }

    @Test(description = "User should not be able to create a project if id cyrillic symbols", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesProjectWithIdLCyrillicSymbolsTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userUncheckRequests = new UncheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectIdWithCyrillicSymbols = generate(Project.class, RandomData.getStringWithCyrillic());

        userUncheckRequests.getRequest(PROJECTS)
                .create(projectIdWithCyrillicSymbols)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString("Project ID \"%s\" is invalid: contains non-latin letter "
                        .formatted(projectIdWithCyrillicSymbols.getId())));
        userUncheckRequests.getRequest(PROJECTS)
                .read("id:" + projectIdWithCyrillicSymbols.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(projectIdWithCyrillicSymbols.getId())));
    }

    @Test(description = "User should not be able to create a project if id has more than 225 symbols", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesProjectWithIdLongerThan225SymbolsTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userUncheckRequests = new UncheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectIdWithMoreThen225Symbols = generate(Project.class, RandomData.getString(226));

        userUncheckRequests.getRequest(PROJECTS)
                .create(projectIdWithMoreThen225Symbols)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString(("Project ID \"%s\" is invalid: it is 226 characters long while" +
                        " the maximum length is 225. ID should start with a latin letter and contain only latin" +
                        " letters, digits and underscores (at most 225 characters).")
                        .formatted(projectIdWithMoreThen225Symbols.getId())));

        userUncheckRequests.getRequest(PROJECTS)
                .read("id:" + projectIdWithMoreThen225Symbols.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(projectIdWithMoreThen225Symbols.getId())));
    }

    @Test(description = "User should not be able to create a project if id starts with _", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesProjectWithIdStartsWith_Test() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userUncheckRequests = new UncheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectIdStartsWith_ = generate(Project.class, RandomData.getStringWith_());

        userUncheckRequests.getRequest(PROJECTS)
                .create(projectIdStartsWith_)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .body(Matchers.containsString(("Project ID \"%s\" is invalid: starts with non-letter character \'%c\'." +
                        " ID should start with a latin letter and contain only latin letters, digits and underscores (at most 225 characters).")
                        .formatted(projectIdStartsWith_.getId(), projectIdStartsWith_.getId().charAt(0))));

        userUncheckRequests.getRequest(PROJECTS)
                .read("id:" + projectIdStartsWith_.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(projectIdStartsWith_.getId())));
    }

    @Test(description = "User should be able to create a project if id includes repeating symbols", groups = {"Positive", "CRUD", "Project"})
    public void userCreatesProjectWithIdIncludesRepeatingSymbolsTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectIdWithRepeatingSymbols = generate(Project.class, RandomData.getStringWithRandomRepeatingSymbols());

        var project = userCheckRequests.<Project>getRequest(PROJECTS).create(projectIdWithRepeatingSymbols);

        softy.assertEquals(projectIdWithRepeatingSymbols.getId(), project.getId(), "Project ID is not correct");
        softy.assertEquals(projectIdWithRepeatingSymbols.getName(), project.getName(), "Project name is not correct");

        userCheckRequests.getRequest(PROJECTS).delete(project.getId());

        new UncheckedRequests(Specifications.authSpec(testData.getUser()))
                .getRequest(PROJECTS)
                .read("id:" + project.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(project.getId())));
    }

    @Test(description = "User should be able to create a project if id has 225 symbols", groups = {"Positive", "CRUD", "Project"})
    public void userCreatesProjectWithIdLength225SymbolsTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectIdWith225Symbols = generate(Project.class, RandomData.getString(225));

        var project = userCheckRequests.<Project>getRequest(PROJECTS).create(projectIdWith225Symbols);

        softy.assertEquals(projectIdWith225Symbols.getId(), project.getId(), "Project ID is not correct");
        softy.assertEquals(projectIdWith225Symbols.getName(), project.getName(), "Project name is not correct");

        userCheckRequests.getRequest(PROJECTS).delete(project.getId());

        new UncheckedRequests(Specifications.authSpec(testData.getUser()))
                .getRequest(PROJECTS)
                .read("id:" + project.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(project.getId())));
    }

    @Test(description = "User should be able to create a project if id includes latin letters, digits", groups = {"Positive", "CRUD", "Project"})
    public void userCreatesProjectWithIdLIncludesDigitsLatinLettersTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectIdWithLatinDigits = generate(Project.class, RandomData.generateStringWithLatinDigits());

        var project = userCheckRequests.<Project>getRequest(PROJECTS).create(projectIdWithLatinDigits);

        softy.assertEquals(projectIdWithLatinDigits.getId(), project.getId(), "Project ID is not correct");
        softy.assertEquals(projectIdWithLatinDigits.getName(), project.getName(), "Project name is not correct");

        userCheckRequests.getRequest(PROJECTS).delete(project.getId());

        new UncheckedRequests(Specifications.authSpec(testData.getUser()))
                .getRequest(PROJECTS)
                .read("id:" + project.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(project.getId())));
    }


    @Test(description = "User should be able to create a project if id includes 1 valid symbol", groups = {"Positive", "CRUD", "Project"})
    public void userCreatesProjectWithIdLIncludesOneSymbolTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectIdWithOneSymbol = generate(Project.class, RandomData.getRandomLetter());

        var project = userCheckRequests.<Project>getRequest(PROJECTS).create(projectIdWithOneSymbol);

        softy.assertEquals(projectIdWithOneSymbol.getId(), project.getId(), "Project ID is not correct");
        softy.assertEquals(projectIdWithOneSymbol.getName(), project.getName(), "Project name is not correct");

        userCheckRequests.getRequest(PROJECTS).delete(project.getId());

        new UncheckedRequests(Specifications.authSpec(testData.getUser()))
                .getRequest(PROJECTS)
                .read("id:" + project.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(project.getId())));
    }

    @Test(description = "User should not be able to create a project with empty name", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesProjectWithEmptyNameTest() {

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userUncheckRequests = new UncheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectEmptyName = generate(Project.class, RandomData.getString(), "");

        userUncheckRequests.getRequest(PROJECTS)
                .create(projectEmptyName)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project name cannot be empty"));

        userUncheckRequests.getRequest(PROJECTS)
                .read("id:" + projectEmptyName.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(projectEmptyName.getId())));
    }

    @Test(description = "User should be able to create a project if name has more than 225 symbols", groups = {"Positive", "CRUD", "Project"})
    public void userCreatesProjectWithNameLongerThan225SymbolsTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectNameWithMoreThen225Symbols = generate(Project.class, RandomData.getString(), RandomData.getString(226));

        var project = userCheckRequests.<Project>getRequest(PROJECTS).create(projectNameWithMoreThen225Symbols);

        softy.assertEquals(projectNameWithMoreThen225Symbols.getId(), project.getId(), "Project ID is not correct");
        softy.assertEquals(projectNameWithMoreThen225Symbols.getName(), project.getName(), "Project name is not correct");

        userCheckRequests.getRequest(PROJECTS).delete(project.getId());

        new UncheckedRequests(Specifications.authSpec(testData.getUser()))
                .getRequest(PROJECTS)
                .read("id:" + project.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(project.getId())));
    }

    @Test(description = "User should be able to create a project if name has cyrillic symbols", groups = {"Positive", "CRUD", "Project"})
    public void userCreatesProjectWithNameHas225SymbolsTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectNameWithCyrillicSymbols = generate(Project.class, RandomData.getString(), RandomData.getStringWithCyrillic());

        var project = userCheckRequests.<Project>getRequest(PROJECTS).create(projectNameWithCyrillicSymbols);

        softy.assertEquals(projectNameWithCyrillicSymbols.getId(), project.getId(), "Project ID is not correct");
        softy.assertEquals(projectNameWithCyrillicSymbols.getName(), project.getName(), "Project name is not correct");

        userCheckRequests.getRequest(PROJECTS).delete(project.getId());

        new UncheckedRequests(Specifications.authSpec(testData.getUser()))
                .getRequest(PROJECTS)
                .read("id:" + project.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(project.getId())));
    }

    @Test(description = "User should not be able to create a project with empty id and name", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesProjectWithEmptyNameAndIdTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userUncheckRequests = new UncheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectEmptyIdName = generate(Project.class, "", "");

        userUncheckRequests.getRequest(PROJECTS)
                .create(projectEmptyIdName)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project name cannot be empty"));

        userUncheckRequests.getRequest(PROJECTS)
                .read("id:" + projectEmptyIdName.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(projectEmptyIdName.getId())));

    }

    @Test(description = "User should not be able to create a project with invalid id and empty name", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesProjectWithEmptyNameAndInvalidIdTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userUncheckRequests = new UncheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectInvalidIdEmptyName = generate(Project.class, RandomData.getStringWith_(), "");

        userUncheckRequests.getRequest(PROJECTS)
                .create(projectInvalidIdEmptyName)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project name cannot be empty"));

        userUncheckRequests.getRequest(PROJECTS)
                .read("id:" + projectInvalidIdEmptyName.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(projectInvalidIdEmptyName.getId())));
    }

    @Test(description = "User should be able to create a project with 'copyAllAssociatedSettings' false", groups = {"Positive", "CRUD", "Project"})
    public void userCreatesProjectWithoutCopyAssociatedSettingsTest() {

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var projectNameWithFalseSettings = generate(Project.class, RandomData.getString(),
                RandomData.generateStringWithLatinDigits(), RandomData.getString(), false);

        var project = userCheckRequests.<Project>getRequest(PROJECTS).create(projectNameWithFalseSettings);

        softy.assertEquals(projectNameWithFalseSettings.getId(), project.getId(), "Project ID is not correct");
        softy.assertEquals(projectNameWithFalseSettings.getName(), project.getName(), "Project name is not correct");

        userCheckRequests.getRequest(PROJECTS).delete(project.getId());

        new UncheckedRequests(Specifications.authSpec(testData.getUser()))
                .getRequest(PROJECTS)
                .read("id:" + project.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(project.getId())));
    }

    @Test(description = "User should be able to create a copy of a project", groups = {"Positive", "CRUD", "Project"})
    public void userCreatesACopyOfAlreadyCreatedProjectTest() {

        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        var project = userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var copyProject = generate(Project.class,RandomData.getString(),RandomData.getString(), "null", true,
                Project.SourceProject.builder().locator(project.getId()).build());

        userCheckRequests.<Project>getRequest(PROJECTS).create(copyProject);

        var checkProjectCopy = userCheckRequests.<Project>getRequest(PROJECTS).read("id:" + copyProject.getId());
        softy.assertEquals(copyProject.getId(), checkProjectCopy.getId(), "Project with this Id is not found");

        userCheckRequests.getRequest(PROJECTS).delete(project.getId());

        new UncheckedRequests(Specifications.authSpec(testData.getUser()))
                .getRequest(PROJECTS)
                .read("id:" + project.getId())
                .then().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("Project cannot be found by external id '%s'".formatted(project.getId())));
    }

    @Test(description = "User should not be able to create a copy of not existing project", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesACopyOfNotCreatedProjectTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var notExistingProjectId = RandomData.getString();

        var copyProject = generate(Project.class,RandomData.getString(),RandomData.getString(), "null", true,
                Project.SourceProject.builder().locator(notExistingProjectId).build());

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(copyProject)
                .then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND)
                .body(Matchers.containsString("No project found by name or internal/external id '%s'".formatted(notExistingProjectId)));
    }

    @Test(description = "User should not be able to create a copy with empty info about source project", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesACopyOfCreatedProjectWithEmptyInfoTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var copyProject = generate(Project.class,RandomData.getString(),RandomData.getString(), "null", true,
                Project.SourceProject.builder().locator("").build());

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(copyProject)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("No project specified. Either 'id', 'internalId'" +
                        " or 'locator' attribute should be present"));
    }

    @Test(description = "User should not be able to create 2 projects with the same id", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesProjectWithTheSameIdTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var projectWithSameId = generate(Project.class, testData.getProject().getId());

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project ID \"%s\" is already used by another project"
                        .formatted(testData.getProject().getId())));
    }

    @Test(description = "User should not be able to create 2 projects with the same name", groups = {"Negative", "CRUD", "Project"})
    public void userCreatesProjectWithTheSameNameTest() {
        superUserCheckRequests.getRequest(USERS).create(testData.getUser());

        var userCheckRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));

        userCheckRequests.<Project>getRequest(PROJECTS).create(testData.getProject());

        var projectWithSameIdName = generate(Project.class, testData.getProject().getId(),
                testData.getProject().getName());

        new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS)
                .create(projectWithSameIdName)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("Project with this name already exists: %s"
                        .formatted(testData.getProject().getName())));
    }

    @Test(description = "User should not be able to create project as Project Viewer", groups = {"Negative", "CRUD", "Project", "Roles"})
    public void projectDeveloperCreatesProjectAsProjectViewerTest() {

        var createdUser = superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());

        testData.getUser().setRoles(generate(Roles.class, "PROJECT_VIEWER"));

        superUserCheckRequests.<User>getRequest(USERS).update("id:" + createdUser.getId(), testData.getUser());

        var projectViewerRequests = new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS);

        projectViewerRequests.create(testData.getProject()).then().statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString("Access denied. Check the user has enough permissions" +
                        " to perform the operation."));

    }

    @Test(description = "User should not be able to create project as Project Developer", groups = {"Negative", "CRUD", "Project", "Roles"})
    public void projectDeveloperCreatesProjectAsProjectDeveloperTest() {

        var createdUser = superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());

        testData.getUser().setRoles(generate(Roles.class, "PROJECT_DEVELOPER"));

        superUserCheckRequests.getRequest(USERS).update("id:" + createdUser.getId(), testData.getUser());

        var projectViewerRequests = new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS);

        projectViewerRequests.create(testData.getProject()).then().statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString("Access denied. Check the user has enough permissions" +
                        " to perform the operation."));

    }

    @Test(description = "User should not be able to create project as Agent Manager", groups = {"Negative", "CRUD", "Project", "Roles"})
    public void projectDeveloperCreatesProjectAsAgentManagerTest() {

        var createdUser = superUserCheckRequests.<User>getRequest(USERS).create(testData.getUser());
        testData.getUser().setRoles(generate(Roles.class, "AGENT_MANAGER"));
        superUserCheckRequests.getRequest(USERS).update("id:" + createdUser.getId(), testData.getUser());

        var adminManagerRequests = new UncheckedBase(Specifications.authSpec(testData.getUser()), PROJECTS);

        adminManagerRequests.create(testData.getProject()).then().statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString("Access denied. Check the user has enough permissions" +
                        " to perform the operation."));
    }
}
