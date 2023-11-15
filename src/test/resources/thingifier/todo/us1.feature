Feature: View Todo/Task Details

  Scenario: Normal Flow Acceptance Test
    Given todo with ID 1 exists
    When I want to view the details of todo with ID 1
    Then the title of todo with ID 1 is "scan paperwork"
    And the doneStatus of todo with ID 1 is "false"
    And the description of todo with ID 1 is ""

  Scenario: Alternate Flow Acceptance Test
    Given no todos exists
    When I want to view the list of todos
    Then I should see an empty list of todos

  Scenario: Error Flow Acceptance Test
    Given a non existent todo with ID 10
    When I want to view the details of todo with ID 10
    Then I should see an error message indicating that the todo with ID 10 does not exist