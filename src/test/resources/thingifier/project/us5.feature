Feature: Update Project Description

  Scenario: Normal Flow Acceptance Test
    Given project with ID 1 exists
    When I want to set the description of project with ID 1 to "mcgill work"
    Then the description of project with ID 1 should be updated successfully to "mcgill work"

  Scenario: Alternate Flow Acceptance Test
    Given project with ID 1 exists
    When I want to set the description of project with ID 1 to ""
    Then I should see multiple projects with the description ""

  Scenario: Error Flow Acceptance Test
    Given a non existent project with ID 47
    When I want to set the description of project with ID 47 to "mcgill work"
    Then I should see an error message indicating that the project with GUID 47 does not exist
