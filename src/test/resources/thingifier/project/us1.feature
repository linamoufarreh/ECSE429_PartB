Feature: View Project Details

  Scenario: Normal Flow Acceptance Test
    Given project with ID 1 exists,
    When I want to view the details of project with ID 1
    Then I should see the project's title, completion status, activity status, and description

  Scenario: Alternate Flow Acceptance Test
    Given no project exists
    When I want to view the list of projects
    Then I should see an empty list of projects

  Scenario: Error Flow Acceptance Test
    Given project with invalid ID does not exist,
    When I want to view its details
    Then I should see an error message indicating that the project does not exist

