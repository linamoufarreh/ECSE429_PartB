Feature: Change category title

  Scenario: Normal Flow Acceptance Test
    Given category with ID 1 exists
    And the title of  1 is "Office"
    When I want to set the title of category with ID 1 to "New Office"
    Then the title of category with ID 1 is "New Office"

  Scenario: Alternate Flow Acceptance Test
    Given category with ID 1 exists
    And the title of category 1 is "New Office"
    When I want to set the title of category with ID 1 to "New Office"
    Then the title of category with ID 1 is "New Office"

  Scenario: Error Flow Acceptance Test
    Given a non existent category with ID 10
    When I want to set the title of category with ID 10 to "New Office"
    Then I should see an error message indicating that the category with GUID 10 does not exist

