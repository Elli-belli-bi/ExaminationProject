package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Account {

    private final WebDriver driver;

    // Constructor
    public Account(WebDriver driver) {
        this.driver = driver;
    }

    public void dateOfBirth(String dateOfBirth) {
        WebElement birthDate = driver.findElement(By.name("DateOfBirth"));
        birthDate.sendKeys(dateOfBirth);

        // Click on the page to close the date picker
        Actions actions = new Actions(driver);
        actions.moveByOffset(100, 200).click().perform();
    }

    public void firstName(String firstName) {
        WebElement firstNameField = driver.findElement(By.name("Forename"));
        firstNameField.sendKeys(firstName);
    }

    public void lastName(String lastName) {
        WebElement lastNameField = driver.findElement(By.name("Surname"));
        lastNameField.sendKeys(lastName);
    }

    // Unique e-mail based on time stamp
    public String generateUniqueEmail() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return "test_" + timestamp + "@test.se";
    }

    public void email(String email) {
        WebElement emailField = driver.findElement(By.name("EmailAddress"));
        emailField.sendKeys(email);
    }

    public void confirmEmail(String email) {
        WebElement confirmEmailField = driver.findElement(By.name("ConfirmEmailAddress"));
        confirmEmailField.sendKeys(email);
    }

    public void password(String password) {
        WebElement passwordField = driver.findElement(By.name("Password"));
        passwordField.sendKeys(password);
    }

    public void retypePassword(String confirmPassword) {
        WebElement confirmPasswordField = driver.findElement(By.name("ConfirmPassword"));
        confirmPasswordField.sendKeys(confirmPassword);
    }

    public void clickCheckbox(String checkboxName) {
        WebElement checkbox;

        switch (checkboxName.toLowerCase()) {
            case "termsandconditions":
                checkbox = driver.findElement(By.xpath("//label[@for='sign_up_25']"));
                break;
            case "notminor":
                checkbox = driver.findElement(By.xpath("//label[@for='sign_up_26']"));
                break;
            case "ethicsandconduct":
                checkbox = driver.findElement(By.xpath("//label[@for='fanmembersignup_agreetocodeofethicsandconduct']"));
                break;
            default:
                throw new IllegalArgumentException("Invalid checkbox name: " + checkboxName);
        }

        checkbox.click();
    }

    public void clickConfirmAndJoinButton() {
        WebElement confirm = driver.findElement(By.cssSelector(".btn.btn-big.red"));
        confirm.click();
    }

    public WebElement getMessage(String messageType) {
        WebElement message;

        switch (messageType.toLowerCase()) {
            case "error":
                message = visible(By.cssSelector(".warning.field-validation-error"));
                break;
            case "confirmation":
                message = visible(By.cssSelector("h2.bold.gray.text-center.margin-bottom-40"));
                break;
            default:
                throw new IllegalArgumentException("Unknown message type: " + messageType);
        }

        return message;
    }

    public WebElement visible(By by) {
        // Wait until the element is visible
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}