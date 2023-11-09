Feature: Mark a project as active

  Scenario: Normal Flow Acceptance Test
    Given project with ID 1 exists
    When I want to mark project 1 as active
    Then the project's active status should be updated to True

  Scenario: Alternate Flow Acceptance Test
    Given project with ID 1 is active
    When I want to mark project 1 as active
    Then I should see a message indicating that the project is already active, and no changes are made.

  Scenario: Error Flow Acceptance Test
    Given a project with ID 1 does not exist
    When I want to mark project 1 as active
    Then I should see a message indicating that the project does not exist

