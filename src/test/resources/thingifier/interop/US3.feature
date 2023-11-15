Feature: Add relationship between a todo and a category

  Scenario: Normal Flow Acceptance Test:
    Given a todo with id 1 and category with id 2
    When the user attempts to add the category with id 2 to todo with id 1
    Then the todo with id 1 will be associated with the category with id 2 .

  Scenario: Alternate Flow Acceptance Test:
    Given todo with id 1 with category "Office"
    When the user attempts to add the category with id 44 to todo 2
    Then a new category "Gardening" will be created, and todo 7 will be associated with it.

  Scenario: Error Flow Acceptance Test:
    Given an inexistent todo with id 10
    When the user attempts to add a category to todo 10
    Then a response with code 404 will be received.
