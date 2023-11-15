Feature: Remove a todo

  Scenario: Normal Flow Acceptance Test
    Given todo with ID 1 exists
    When I want to remove a todo with ID 1
    Then I should not see a todo with ID 1

  Scenario: Alternate Flow Acceptance Test, Other todos exists
    Given todo with ID 1 exists
    And todo with ID 2 exists
    When I want to remove a todo with ID 1
    Then I should not see a todo with ID 1
    And I should see a todo with ID 2

  Scenario: Error Flow Acceptance Test, Title is empty
    Given a non existent todo with ID 10
    When I want to remove a todo with ID 10
    Then I should see an error message indicating that the todo with ID 10 wasn't found