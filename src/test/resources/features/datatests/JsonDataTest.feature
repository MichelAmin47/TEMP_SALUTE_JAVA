Feature: As a user I want to test my test framework by navigating the test website with external JSON test data

  @TestData @JSON
  Scenario: Fill in the contact form with a JSON file as input
    Given I am on the homepage
    When I navigate to the contact us form
    And I fill in my complaint from the external JSON
    Then the successful sent message should be shown

  @TestData @JSON
  Scenario: Fill in the contact form with incorrect data from a JSON file as input
    Given I am on the homepage
    When I navigate to the contact us form
    And I fill in my complaint from the external JSON with incorrect data
    #Then a warning should be shown