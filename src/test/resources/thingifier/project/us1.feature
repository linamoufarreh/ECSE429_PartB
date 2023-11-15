Feature: View Project Details

  Scenario: Normal Flow Acceptance Test
    Given project with ID 1 exists
    When I want to view the details of project with ID 1
    Then the title of project with ID 1 is "Office Work"
    And the completed status of project with ID 1 is "false"
    And the active status of project with ID 1 is "false"
    And the description of project with ID 1 is ""

  Scenario: Alternate Flow Acceptance Test
    Given no project exists
    When I want to view the list of projects
    Then I should see an empty list of projects

  Scenario: Error Flow Acceptance Test
    Given a non existent project with ID 25
    When I want to view the details of project with ID 25
    Then I should see an error message indicating that the project with ID 25 does not exist

