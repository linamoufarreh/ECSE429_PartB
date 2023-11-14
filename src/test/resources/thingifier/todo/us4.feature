Feature: Change todo description

  Scenario: Normal Flow Acceptance Test
    Given todo with ID 1 exists
    And the description of todo 1 is ""
    When I want to set the description of todo with ID 1 to "scan paperwork for 2025"
    Then the description of todo with ID 1 is "scan paperwork for 2025"

  Scenario: Alternate Flow Acceptance Test
    Given todo with ID 1 exists
    And the description of todo 1 is "scan paperwork for 2025"
    When I want to set the description of todo with ID 1 to "scan paperwork for 2025"
    Then the description of todo with ID 1 is "scan paperwork for 2025"

  Scenario: Error Flow Acceptance Test
    Given a non existent todo with ID 10
    When I want to set the description of todo with ID 10 to "scan paperwork for 2025"
    Then I should see an error message indicating that the todo with GUID 10 does not exist