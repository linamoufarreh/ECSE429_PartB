Feature: Add a category

  Scenario: Normal Flow Acceptance Test
    Given no categorys exists
    When I want to add a category with title "test session" and description "Test MindMap Application"
    Then I should see a category with title "test session" and description "Test MindMap Application"

  Scenario: Alternate Flow Acceptance Test, Other categorys exists
    Given category with ID 1 exists
    And category with ID 2 exists
    When I want to add a category with title "test session" and description "Test MindMap Application"
    Then I should see a category with title "test session" and description "Test MindMap Application"

  Scenario: Alternate Flow Acceptance Test, category already exists
    Given category with title "test session" and description "Test MindMap Application" exists
    When I want to add a category with title "test session" and description "Test MindMap Application"
    Then I should see 2 categorys with title "test session" and description "Test MindMap Application"

  Scenario: Error Flow Acceptance Test, Title is empty
    When I want to add a category with title "" and description "Test MindMap Application"
    Then I should see an error message indicating that the title cannot be empty