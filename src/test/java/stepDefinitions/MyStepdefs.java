package stepDefinitions;

import helpers.Account;
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

public class MyStepdefs {

    private WebDriver driver;
    private Account account;
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
        account = new Account(driver);
        uniqueEmail = account.generateUniqueEmail();
    }

    @Given("I am on the registration page")
    public void iAmOnTheRegistrationPageUsing() {
        driver.get("https://membership.basketballengland.co.uk/NewSupporterAccount");
    }

    @When("I add {string} to the DateOfBirth field")
    public void iAddToTheDateOfBirthField(String value) {
        account.dateOfBirth(value);
    }

    @And("{string} in the First Name field")
    public void inTheFirstNameField(String value) {
        account.firstName(value);
    }

    @And("{string} in the Last Name field")
    public void inTheLastNameField(String value) {
        if (value.isEmpty()) {
            account.lastName("");  // Empty last name
        } else {
            account.lastName(value);
        }
    }

    @And("I add a unique email to the Email Address field")
    public void iAddAUniqueEmailToTheEmailAddressField() {
        account.email(uniqueEmail);
    }

    @And("I confirm the same unique email in the Confirm Email Address field")
    public void iConfirmTheSameUniqueEmailInTheConfirmEmailAddressField() {
        account.confirmEmail(uniqueEmail);
    }

    @And("{string} in the password field")
    public void inThePasswordField(String value) {
       account.password(value);
    }

    @And("{string} in the retype password field")
    public void inTheRetypePasswordField(String value) {
        account.retypePassword(value);
    }

    @And("Check the {string} checkbox")
    public void checkTheCheckbox(String checkboxName) {
        account.clickCheckbox(checkboxName);
    }

    @And("Click the Confirm and Join button")
    public void clickTheConfirmAndJoinButton() {
        account.clickConfirmAndJoinButton();
    }

    @Then("I should be redirected to the confirmation page")
    public void iShouldBeRedirectedToTheConfirmationPage() {
        String expectedUrlPart = "https://membership.basketballengland.co.uk/Account/SignUpConfirmation";
        String currentUrl = driver.getCurrentUrl();
        assert currentUrl != null;
        assertTrue("URL does not contain expected confirmation path", currentUrl.contains(expectedUrlPart));
    }

    @And("A confirmation message should be visible")
    public void aConfirmationMessageShouldBeVisible() {
        WebElement actualConfirmationMessage = account.getMessage("confirmation");
        String expectedConfirmationMessage = "THANK YOU FOR CREATING AN ACCOUNT WITH BASKETBALL ENGLAND";
        assertTrue(actualConfirmationMessage.isDisplayed());
        assertEquals(expectedConfirmationMessage, actualConfirmationMessage.getText());
    }

    @Then("Error {string} is displayed")
    public void ErrorIsDisplayed(String expectedErrorMessage) {
        WebElement error = account.getMessage("error");
        assertTrue(error.isDisplayed());
        assertEquals(expectedErrorMessage, error.getText());
    }

    @After
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }
}
