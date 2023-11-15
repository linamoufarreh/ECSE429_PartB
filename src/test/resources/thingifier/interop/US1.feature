Feature: View all categories related to a todo

  Scenario: Normal Flow Acceptance Test:
    Given todo with id 1 with category with id 1
    When the user attempts to view all the categories related to todo 1
    Then a JSON with a property called "categories" will have a list containing an element with the id 1
  Scenario: Alternate Flow Acceptance Test:
    Given todo with id 2 with no categories
    When the user attempts to view all the categories related to todo 2
    Then a JSON with a property called "categories" will have an empty list
  Scenario: [BUG] Error Flow Acceptance Test:
    Given an inexistent todo with id 44
    When the user attempts to view all the categories related to the inexistent todo with id 44
    Then a response with code 404 will be received
