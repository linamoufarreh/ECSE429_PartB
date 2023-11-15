Feature: Add a todo

  Scenario: Normal Flow Acceptance Test
    Given no todos exists
    When I want to add a todo with title "test session" and description "Test MindMap Application"
    Then I should see a todo with title "test session" and description "Test MindMap Application"

  Scenario: Alternate Flow Acceptance Test, Other todos exists
    Given todo with ID 1 exists
    And todo with ID 2 exists
    When I want to add a todo with title "test session" and description "Test MindMap Application"
    Then I should see a todo with title "test session" and description "Test MindMap Application"

  Scenario: Alternate Flow Acceptance Test, Todo already exists
    Given todo with title "test session" and description "Test MindMap Application" exists
    When I want to add a todo with title "test session" and description "Test MindMap Application"
    Then I should see 2 todos with title "test session" and description "Test MindMap Application"

  Scenario: Error Flow Acceptance Test, Title is empty
    When I want to add a todo with title "" and description "Test MindMap Application"
    Then I should see an error message indicating that the title cannot be empty