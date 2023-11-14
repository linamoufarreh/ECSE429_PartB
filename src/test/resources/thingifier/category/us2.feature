Feature: Create a new category

    Given I am on the category creation page,
    When I enter "Home Renovation" as the category name and "Projects related to updating my house" as the description,
    And I select the option to create the category,
    Then the new category "Home Renovation" should be created,
    And I should see a confirmation message that it has been added successfully.

    Given I am on the category creation page,
    When I enter "Work Projects" as the category name but leave the description blank,
    And I select the option to create the category,
    Then the new category "Work Projects" should be created without a description,
    And I should see a confirmation message that it has been added successfully.

    Given I am on the category creation page,
    When I attempt to create a new category without specifying a category name,
    And I select the option to create the category,
    Then I should not see a new category created,
    And I should see an error message indicating that the category name is required.
