Feature: Delete a category 

    Given I am logged in as a user
    And I navigate to the category management page
    When I select the 'Remove' option for a category named 'Old Category'
    And I am prompted to reassign projects from 'Old Category'
    And I select 'New Category' as the destination for the projects
    And I confirm the removal
    Then 'Old Category' should be removed from the category list
    And the projects previously under 'Old Category' should now be listed under 'New Category.'

    Given I am logged in as a user
    And I navigate to the category management page
    When I select the 'Remove' option for a category named 'Old Category'
    And I am prompted to reassign projects from 'Old Category'
    But I decide not to proceed and click the 'Cancel' button
    Then 'Old Category' should still be present in the category list
    And no projects are reassigned.

    Given I am logged in as a user
    And I navigate to the category management page
    And 'Old Category' is the only category available
    When I select the 'Remove' option for 'Old Category'
    Then I should see an error message 'Cannot remove the only existing category. Please create a new category before removing 'Old Category'.'
    And 'Old Category' should remain in the category list.
