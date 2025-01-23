package stepDefinitions;

import helpers.AccountHelpers;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountStepDefinitions {

    private WebDriver driver;
    private AccountHelpers accountHelpers;
    private String uniqueEmail;

    @Before
    public void Setup(Scenario scenario) {
        // Check if the scenario has the @firefox tag
        if (scenario.getSourceTagNames().contains("@firefox")) {
            driver = new FirefoxDriver();
        } else {
            // Default is Chrome if no specific tag exists
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        accountHelpers = new AccountHelpers(driver);
        uniqueEmail = accountHelpers.generateUniqueEmail();
        // Log the scenario start
        System.out.println("Starting Scenario: " + scenario.getName());
    }

    @Given("I am on the registration page")
    public void iAmOnTheRegistrationPageUsing() {
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @When("I add {string} to the DateOfBirth field")
    public void iAddToTheDateOfBirthField(String value) {
        accountHelpers.dateOfBirth(value);
    }

    @And("{string} in the First Name field")
    public void inTheFirstNameField(String value) {
        WebElement firstNameField = accountHelpers.fillNameFields("firstname", value);
        assertTrue("First name field should be visible", firstNameField.isDisplayed());
    }

    @And("{string} in the Last Name field")
    public void inTheLastNameField(String value) {
        WebElement lastNameField;
        if (value.isEmpty()) {
            lastNameField = accountHelpers.fillNameFields("lastname", "");
        } else {
            lastNameField = accountHelpers.fillNameFields("lastname", value);
        }
        assertTrue("Last name field should be visible", lastNameField.isDisplayed());
        assertEquals("Last name should match the provided value", value, lastNameField.getAttribute("value"));
    }

    @And("I add a unique email to the Email Address field")
    public void iAddAUniqueEmailToTheEmailAddressField() {
        accountHelpers.fillEmails(uniqueEmail, "email");
    }

    @And("I confirm the same unique email in the Confirm Email Address field")
    public void iConfirmTheSameUniqueEmailInTheConfirmEmailAddressField() {
        accountHelpers.fillEmails(uniqueEmail, "confirmEmail");
    }

    @And("{string} in the password field")
    public void inThePasswordField(String value) {
        accountHelpers.fillPasswords(value, "password");
    }

    @And("{string} in the retype password field")
    public void inTheRetypePasswordField(String value) {
        accountHelpers.fillPasswords(value, "confirmPassword");
    }

    @And("Check the {string} checkbox")
    public void checkTheCheckbox(String checkboxName) {
        accountHelpers.clickCheckbox(checkboxName);
    }

    @And("Click the Confirm and Join button")
    public void clickTheConfirmAndJoinButton() {
        accountHelpers.clickConfirmAndJoinButton();
    }

    @Then("I should be redirected to the confirmation page")
    public void iShouldBeRedirectedToTheConfirmationPage() {
        String expectedUrlPart = "https://membership.basketballengland.co.uk/Account/SignUpConfirmation";
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        assertTrue("The user is not on the confirmation page.", currentUrl.contains(expectedUrlPart));
    }

    @And("A confirmation message should be visible")
    public void aConfirmationMessageShouldBeVisible() {
        WebElement actualConfirmationMessage = accountHelpers.getMessage("confirmation");
        String expectedConfirmationMessage = "THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND";
        assertTrue("Confirmation message should be visible", actualConfirmationMessage.isDisplayed());
        assertEquals(expectedConfirmationMessage, actualConfirmationMessage.getText());
    }

    @Then("Error {string} is displayed")
    public void ErrorIsDisplayed(String expectedErrorMessage) {
        WebElement error = accountHelpers.getMessage("error");
        assertTrue("Error message should be visible", error.isDisplayed());
        assertEquals(expectedErrorMessage, error.getText());
    }

    @After
    public void tearDown(Scenario scenario) {
        // Log the scenario completion
        System.out.println("Scenario: " + scenario.getName() + " has been completed.");

        if (driver != null) {
            driver.quit();
        }
    }
}
