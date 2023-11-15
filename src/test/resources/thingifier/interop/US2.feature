Feature: View all todos related to a category

  Scenario: Normal Flow Acceptance Test:
    Given a category with id 1 with todo 2 associated
    When the user attempts to view all todos related to the category with id 1
    Then a JSON with a property called "todos" will have a list containing an element with id 2
  Scenario: Alternate Flow Acceptance Test:
    Given a category with id 2 with no associated todos
    When the user attempts to view all todos related to the category with id 2
    Then a JSON with a property called "todos" will have an empty list

  Scenario: [BUG] Error Flow Acceptance Test:
    Given an inexistent category with id 43
    When the user attempts to view all todos related to the inexistent category with id 43
    Then a response with code 404 will be received

