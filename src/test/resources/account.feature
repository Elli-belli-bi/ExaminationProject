Feature: Account
  In order to create a valid user account
  As a user
  I want to fill out the registration form and an error message if there are issues

  Background:
    Given I am on the registration page

  Scenario: Register a new account with valid data
    When I add "13/02/1968" to the DateOfBirth field
    And "William" in the First Name field
    And "Mullis" in the Last Name field
    And I add a unique email to the Email Address field
    And I confirm the same unique email in the Confirm Email Address field
    And "abcd43z21" in the password field
    And "abcd43z21" in the retype password field
    And Check the "Terms and Conditions" checkbox
    And Check the "Not a minor" checkbox
    And Check the "Ethics and Conduct" checkbox
    And Click the Confirm and Join button
    Then I should be redirected to the confirmation page
    And A confirmation message should be visible

  @firefox
  Scenario: Registration fails if the passwords do not match
    When I add "09/09/1967" to the DateOfBirth field
    And "Sam" in the First Name field
    And "Giggi" in the Last Name field
    And I add a unique email to the Email Address field
    And I confirm the same unique email in the Confirm Email Address field
    And "abcd4321" in the password field
    And "axxx4321" in the retype password field
    And Check the "Terms and Conditions" checkbox
    And Check the "Not a minor" checkbox
    And Check the "Ethics and Conduct" checkbox
    And Click the Confirm and Join button
    Then Error "Password did not match" is displayed

  Scenario: Registration fails if Terms and Conditions are not checked
    When I add "27/06/1990" to the DateOfBirth field
    And "Kevin" in the First Name field
    And "Hart" in the Last Name field
    And I add a unique email to the Email Address field
    And I confirm the same unique email in the Confirm Email Address field
    And "abcd4321" in the password field
    And "abcd4321" in the retype password field
    And Check the "Not a minor" checkbox
    And Check the "Ethics and Conduct" checkbox
    And Click the Confirm and Join button
    Then Error "You must confirm that you have read and accepted our Terms and Conditions" is displayed

  Scenario Outline: Registration fails if the last name is missing
    When I add "<DateOfBirth>" to the DateOfBirth field
    And "<FirstName>" in the First Name field
    And I add a unique email to the Email Address field
    And I confirm the same unique email in the Confirm Email Address field
    And "<Password>" in the password field
    And "<RetypedPassword>" in the retype password field
    And Check the "Terms and Conditions" checkbox
    And Check the "Not a minor" checkbox
    And Check the "Ethics and Conduct" checkbox
    And Click the Confirm and Join button
    Then Error "Last Name is required" is displayed

    Examples:
      | DateOfBirth | FirstName | Password | RetypedPassword |
      | 07/04/1942  | Hubert    | ab42cc   | ab42cc          |
      | 28/12/1975  | Paul      | xyz56443 | xyz56443        |
      | 15/03/1959  | Michelle  | pa66w0rd | pa66w0rd        |
