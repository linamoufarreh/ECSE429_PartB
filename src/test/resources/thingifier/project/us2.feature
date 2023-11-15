Feature: Mark a project as completed

  Scenario: Normal Flow Acceptance Test
    Given project with ID 1 exists
    When I want to set the project with ID 1 as completed
    Then the completed status of project with ID 1 is "true"

  Scenario: Alternate Flow Acceptance Test
    Given project with ID 1 exists
    And the completed status of project with ID 1 is "false"
    When I want to set the project with ID 1 as uncompleted
    Then the completed status of project with ID 1 is "false"

  Scenario: Error Flow Acceptance Test
    Given a non existent project with ID 46
    When I want to set the project with ID 46 as completed
    Then I should see an error message indicating that the project with GUID 46 does not exist
