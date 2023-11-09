Feature: Update Project Description


  Scenario: Normal Flow Acceptance Test

  Given project with ID 1 exists
  When I want to update project 1’s description
  Then the project's title should be edited accordingly

  Scenario: Alternate Flow Acceptance Test

  Given project with ID 1 exists
  When I want to update project 1’s description while it already used in another project
  Then I should see two projects with the same description

  Scenario: Error Flow Acceptance Test

  Given project with ID 1 does not exist
  When I want to update project 1’s description
  Then I should see an error message
