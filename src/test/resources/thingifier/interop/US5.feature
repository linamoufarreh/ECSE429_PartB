Feature: View all projects related to a category

  Scenario: Normal Flow Acceptance Test:
    Given a category with id 1 with project 1 associated
    When the user attempts to view all projects related to the category with id 1
    Then a JSON with a property called "projects" will have a list containing an element with id 1
  Scenario: Alternate Flow Acceptance Test:
    Given a category with id 2 with no associated projects
    When the user attempts to view all projects related to the category with id 2
    Then a JSON with a property called "projects" will have an empty list

  Scenario: [BUG] Error Flow Acceptance Test:
    Given an inexistent category with id 43
    When the user attempts to view all projects related to the inexistent category with id 43
    Then a response with code 404 will be received

