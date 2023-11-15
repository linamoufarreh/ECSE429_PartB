Feature: Mark a project as active

  Scenario: Normal Flow Acceptance Test
    Given project with ID 1 exists
    When I want to set the project with ID 1 as active
    Then the active status of project with ID 1 is "true"

  Scenario: Alternate Flow Acceptance Test
    Given project with ID 1 is inactive
    When I want to set the project with ID 1 as active
    Then the active status of project with ID 1 is "true"

  Scenario: Error Flow Acceptance Test
    Given a non existent project with ID 76
    When I want to set the project with ID 76 as active
    Then I should see an error message indicating that the project with GUID 76 does not exist

