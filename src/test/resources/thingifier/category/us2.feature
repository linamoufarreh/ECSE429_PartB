Feature: Remove a category

  Scenario: Normal Flow Acceptance Test
    Given category with ID 1 exists
    When I want to remove a category with ID 1
    Then I should not see a category with ID 1

  Scenario: Alternate Flow Acceptance Test, Other categories exists
    Given category with ID 1 exists
    And category with ID 2 exists
    When I want to remove a category with ID 1
    Then I should not see a category with ID 1
    And I should see a category with ID 2

  Scenario: Error Flow Acceptance Test, Title is empty
    Given a non existent category with ID 10
    When I want to remove a category with ID 10
    Then I should see an error message indicating that the category with ID 10 wasn't found
