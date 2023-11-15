Feature: Add relationship between a todo and a category

  Scenario: Normal Flow Acceptance Test:
    Given a todo with id 2 and category with id 2
    When the user attempts to add the category with id 2 to todo with id 2
    Then the todo with id 2 will be associated with the category with id 2

  Scenario: Alternate Flow Acceptance Test:
    Given todo with id 1 with category with id 1
    When the user attempts to add the category with id 1 to todo with id 1
    Then the todo with id 1 will be associated with the category with id 1

  Scenario: Error Flow Acceptance Test:
    Given an inexistent todo with id 10 and category with id 2
    When the user attempts to add the category with id 2 to todo with id 10
    Then a response with code 404 will be received
