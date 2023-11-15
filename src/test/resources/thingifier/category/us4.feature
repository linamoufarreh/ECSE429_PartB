Feature: Change category description

  Scenario: Normal Flow Acceptance Test
    Given category with ID 1 exists
    And the description of category 1 is ""
    When I want to set the description of category with ID 1 to "New Office 2025"
    Then the description of category with ID 1 is "New Office 2025

  Scenario: Alternate Flow Acceptance Test
    Given category with ID 1 exists
    And the description of category 1 is "New Office 2025
    When I want to set the description of category with ID 1 to "New Office 2025
    Then the description of category with ID 1 is "New Office 2025

  Scenario: Error Flow Acceptance Test
    Given a non existent category with ID 10
    When I want to set the description of category with ID 10 to "New Office 2025
    Then I should see an error message indicating that the category with GUID 10 does not exist