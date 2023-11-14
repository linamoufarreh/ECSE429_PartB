Feature: Rename Category

    Given I am logged in as a user
    And I navigate to the category management page
    When I select the 'Edit' option for a category named 'Books'
    And I enter 'Literature' as the new category name
    And I click the 'Save Changes' button
    Then I should see a confirmation message 'Category renamed successfully to Literature.'
    And the category 'Books' should now be listed as 'Literature.'

    Given I am logged in as a user
    And I navigate to the category management page
    When I select the 'Edit' option for a category named 'Books'
    And I enter 'Books' as the new category name
    And I click the 'Save Changes' button
    Then I should see a message 'No changes were made as the new name is the same as the old one.'
    And the category name remains as 'Books.'

    Given I am logged in as a user
    And I navigate to the category management page
    When I select the 'Edit' option for a category named 'Books'
    And I enter 'Fiction' as the new category name, where 'Fiction' is already an existing category name
    Then I should see an error message 'Category name 'Fiction' already exists. Please choose a different name.'
    And the category 'Books' should not be renamed.

