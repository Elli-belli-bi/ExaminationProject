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
import java.util.NoSuchElementException;

public class AccountHelpers {

    private final WebDriver driver;

    // Constructor
    public AccountHelpers(WebDriver driver) {
        this.driver = driver;
    }

    // Enter a date of birth
    public void dateOfBirth(String dateOfBirth) {
        WebElement birthDate = visible(By.name("DateOfBirth"));
        birthDate.sendKeys(dateOfBirth);
        closeDatePicker();
    }

    // Click else where on the page to close the date picker
    private void closeDatePicker() {
        Actions actions = new Actions(driver);
        actions.moveByOffset(100, 200).click().perform();
    }

    // Enter values in name fields
    public WebElement fillNameFields(String field, String value) {
        WebElement nameFields;
        switch (field.toLowerCase()) {
            case "firstname":
                nameFields = visible(By.name("Forename"));
                break;
            case "lastname":
                nameFields = visible(By.name("Surname"));
                break;
            default:
                throw new IllegalArgumentException("Invalid field type: " + field);
        }
        nameFields.sendKeys(value);
        return nameFields;
    }

    // Unique e-mail based on time stamp
    public String generateUniqueEmail() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        return "test_" + timestamp + "@test.se";
    }

    // Enter values in e-mail fields
    public void fillEmails(String email, String field) {
        WebElement emailField;
        if (field.equals("email")) {
            emailField = visible(By.name("EmailAddress"));
        } else if (field.equals("confirmEmail")) {
            emailField = visible(By.name("ConfirmEmailAddress"));
        } else {
            throw new IllegalArgumentException("Unknown field: " + field);
        }
        emailField.sendKeys(email);
    }

    // Enter values in password fields
    public void fillPasswords(String password, String field) {
        WebElement passwordField;
        switch (field) {
            case "password":
                passwordField = visible(By.name("Password"));
                break;
            case "confirmPassword":
                passwordField = visible(By.name("ConfirmPassword"));
                break;
            default:
                throw new IllegalArgumentException("Unknown password field: " + field);
        }
        passwordField.sendKeys(password);
    }

    // Click a specific checkbox
    public void clickCheckbox(String checkboxName) {
        WebElement checkbox;
        switch (checkboxName) {
            case "Terms and Conditions":
                checkbox = clickable(By.xpath("//label[@for='sign_up_25']"));
                break;
            case "Not a minor":
                checkbox = clickable(By.xpath("//label[@for='sign_up_26']"));
                break;
            case "Ethics and Conduct":
                checkbox = clickable(By.xpath("//label[@for='fanmembersignup_agreetocodeofethicsandconduct']"));
                break;
            default:
                throw new IllegalArgumentException("Invalid checkbox name: " + checkboxName);
        }
        checkbox.click();
    }

    // Click the confirm and join button
    public void clickConfirmAndJoinButton() {
        WebElement confirm = clickable(By.cssSelector(".btn.btn-big.red"));
        confirm.click();
    }

    // Retrieves error or confirmation message
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
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(by));
        if (element == null) {
            throw new NoSuchElementException("Element not found: " + by.toString());
        }
        return element;
    }

    public WebElement clickable(By by) {
        // Wait until the element is clickable
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(by));
        if (element == null) {
            throw new NoSuchElementException("Element not found or not clickable: " + by.toString());
        }
        return element;
    }
}