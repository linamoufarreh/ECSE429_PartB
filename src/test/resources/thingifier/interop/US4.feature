Feature: Delete relationship between todo and category

  Scenario: Normal Flow Acceptance Test:
    Given todo with id 1 with category with id 1
    When the user attempts to delete the relationship between todo with id 1 and category with id 1
    Then the todo with id 1 will no longer be associated with the category with id 1


  Scenario: Alternate Flow Acceptance Test:
    Given todo with id 2 with no categories
    When the user attempts to delete the relationship between todo with id 2 and category with id 1
    Then no changes will occur, and the response will indicate that the relationship doesn't exist.


  Scenario: Error Flow Acceptance Test:
    Given an inexistent todo with id 8 and a category with id 1
    When the user attempts to delete the relationship between todo with id 8 and category with id 1
    Then a response with code 404 will be received
