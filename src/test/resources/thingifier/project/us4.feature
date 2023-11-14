Feature: Update Project Title

  Scenario: Normal Flow Acceptance Test
    Given project with ID 1 exists
    When I want to update project 1’s title
    Then the project's title should be edited accordingly

  Scenario: Alternate Flow Acceptance Test
    Given project with ID 1 exists
    When I want to update the title of project with ID 1 with a title already used by another project
    Then I should see two projects with the same title

  Scenario: Error Flow Acceptance Test
    Given project with ID 1 does not exist
    When I want to update the title of project with ID 1
    Then I should see an error message
