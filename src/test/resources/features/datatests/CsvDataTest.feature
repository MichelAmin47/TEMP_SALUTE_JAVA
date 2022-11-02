Feature: As a user I want to test my test framework by navigating the test website with external CSV test data

  @TestData @CSV
  Scenario: Fill in the contact form with a CSV file JSON style as input
    Given I am on the homepage
    When I navigate to the contact us form
    And I fill in my complaint from the external CSV JSON style file
    Then the successful sent message should be shown

  @TestData @CSV
  Scenario: Fill in the contact form with a CSV file Excel style as input
    Given I am on the homepage
    When I navigate to the contact us form
    And I fill in my complaint from the external CSV Excel style file
    Then the successful sent message should be shown