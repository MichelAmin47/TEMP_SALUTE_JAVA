Feature: As a user I want to test my test framework by navigating the test website

  @Smoke
  Scenario: Fill in the contact form
    Given I am on the homepage
    When I navigate to the contact us form
    And I fill in my complaint
    Then the successful sent message should be shown
    And The correct message is present in prestashop api and deleted