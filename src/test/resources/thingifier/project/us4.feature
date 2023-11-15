Feature: Update Project Title

  Scenario: Normal Flow Acceptance Test
    Given project with ID 1 exists
    When I want to set project with ID 1 title to "write tests"
    Then the title of project with ID 1 should be updated successfully to "write tests"

  Scenario: Alternate Flow Acceptance Test
    Given project with ID 1 exists
    When I want to create a new project with title "Office Work"
    Then I should see two projects with title "Office Work"

  Scenario: Error Flow Acceptance Test
    Given a non existent project with ID 47
    When I want to set project with ID 47 title to "new title"
    Then I should see an error message indicating that the project with GUID 47 does not exist
