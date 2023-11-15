Feature: View Category Details

  Scenario: Normal Flow Acceptance Test
    Given category with ID 1 exists
    When I want to view the details of category with ID 1
    Then the title of category with ID 1 is "Office"
    And the description of category with ID 1 is ""

  Scenario: Alternate Flow Acceptance Test
    Given no categories exists
    When I want to view the list of categories
    Then I should see an empty list of categories

  Scenario: [BUG] Error Flow Acceptance Test
    Given a non existent category with ID 10
    When I want to view the details of category with ID 10
    Then I should see an error message indicating that the category with ID 10 wasn't found