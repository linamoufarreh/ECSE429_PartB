Feature: View Todo/Task Details

  Scenario: Normal Flow Acceptance Test
    Given todo with ID 1 exists
    When I want to set the todo with ID 1 as done
    Then the doneStatus of todo with ID 1 is "true"

  Scenario: Alternate Flow Acceptance Test
    Given todo with ID 1 exists
    And the todo with ID 1 is set as done
    When I want to view the details of todo with ID 1
    Then the doneStatus of todo with ID 1 is "true"

  Scenario: Error Flow Acceptance Test
    Given a non existent todo with ID 10
    When I want to set the todo with ID 10 as done
    Then I should see an error message indicating that the todo with GUID 10 does not exist