Feature: Change todo title

  Scenario: Normal Flow Acceptance Test
    Given todo with ID 1 exists
    And the title of todo 1 is "scan paperwork"
    When I want to set the title of todo with ID 1 to "scan paperwork for 2015"
    Then the title of todo with ID 1 is "scan paperwork for 2015"

  Scenario: Alternate Flow Acceptance Test
    Given todo with ID 1 exists
    And the title of todo 1 is "scan paperwork for 2015"
    When I want to set the title of todo with ID 1 to "scan paperwork for 2015"
    Then the title of todo with ID 1 is "scan paperwork for 2015"

  Scenario: Error Flow Acceptance Test
    Given a non existent todo with ID 10
    When I want to set the title of todo with ID 10 to "scan paperwork for 2015"
    Then I should see an error message indicating that the todo with GUID 10 does not exist