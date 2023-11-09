Feature: Mark a project as completed

  Scenario: Normal Flow Acceptance Test
    Given project with ID 1 exists
    When I want to mark project 1 as completed
    Then the project's completed status should be updated to True

  Scenario: Alternate Flow Acceptance Test
    Given project with ID 1 is completed
    When I want to mark project 1 as completed again
    Then I should see that no changes were made

  Scenario: Error Flow Acceptance Test
    Given a project has tasks that are not done
    When I try to mark a project as completed
    Then I should see that the project cannot be completed