@Regression
Feature: Claim Intelligence Creation
  As a user of Zodiac
  I want to create intelligence
  So that I can submit intelligence information for a claim.

  #@Test1
  #Scenario: Verify "Dissolution First Gazette" in Company Status
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #Given I am on the Company Status dropdown
    #When Dissolution First Gazette should be listed
    #And I quit the browser after passing the scenario
#
  #@Test2
  #Scenario: Verify "Save" button instead of "Cancel"
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #Then I am on the "Add Data" section
    #And The button should be labeled as Save instead of Cancel
    #And I quit the browser after passing the scenario
#
  #@Test3
  #Scenario: Verify "Designation" options now appear under "Party Type"
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #Given I am on the Party Type dropdown
    #When I check the available options under Party Type
    #And I quit the browser after passing the scenario
#
  #@Test4
  #Scenario: Verify "Informant" is listed as an option under "Party Type"
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #Given I am on the Party Type dropdown for checking Informant
    #When I check the Informant option under Party Type
    #Then Informant should be listed as an option
    #And I quit the browser after passing the scenario
#
  #@Test5
  #Scenario: Verify "Personnel Information" appears above "Address" in saved entry
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #Then Personnel Information should appear above Address in saved entry
    #And I should see the saved entry details in the Company Details section
    #And I quit the browser after passing the scenario
#
  #@Test6
  #Scenario: Verify size of "Companies and Persons Involved" section is readable
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #Then I am viewing a saved record
    #And I check the size of the "Companies and Persons Involved" section
    #And I quit the browser after passing the scenario
#
  #@Test7
  #Scenario: Verify submit button is disabled until Intelligence Source is selected
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #Then I check the state of the submit button without selecting the Intelligence Source
    #And The submit button should be disabled
    #And I quit the browser after passing the scenario
#
  #@Test8
  #Scenario: Invalid NIN number
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I enter an invalid NIN
    #And I try to click the Add button for personal details
    #Then I should see a validation error message for the invalid NIN
    #And I quit the browser after passing the scenario
#
  #@Test9
  #Scenario: Edit the form after submission
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I have submitted the form successfully
    #And I should be able to edit the form details
    #Then I should see the form in an editable state
    #And I modify the form fields
    #And I save the modified form
    #And I quit the browser after passing the scenario
#
  #@Test10
  #Scenario: Verify the delete functionality of a form
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I have submitted the form successfully
    #And I click on the Delete button
    #And I confirm the deletion
    #And I verify the deletion success message
    #And I quit the browser after passing the scenario
#
  #@Test11
  #Scenario: Verify the Intel Source filter functionality
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Manage Intelligence option TC
    #And I am on the Show All page
    #And I apply a filter with value "IFB"
    #Then I should see the filtered records with "IFB" on the page
    #And I quit the browser after passing the scenario
#
  #@Test12
  #Scenario: Successfully delete a record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Manage Intelligence option TC
    #And I am on the Show All page
    #And I apply a filter with value "IFB" for delete
    #And I quit the browser after passing the scenario
#
  #@Test13
  #Scenario: Verify the Intel Source filter functionality for Adhoc Owner
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Manage Intelligence option TC
    #And I am on the Show All page
    #And I apply a filter with value "Informant"
    #Then I should see the filtered records with "Informant" on the page
    #And I quit the browser after passing the scenario
#
  #@Test14
  #Scenario: Change the owner name
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I have submitted the form successfully
    #And I change the owner name to "Test User 3"
    #And I should see the confirmation message
    #And I quit the browser after passing the scenario
#
  #@Test15
  #Scenario: Submit form with Address details alone under personal record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I submit the form with Address details alone under personal record
    #And I should be able to see the record that Address alone is added in the personal involved tab
    #And I quit the browser after passing the scenario
#
  #@Test16
  #Scenario: Submit form with phone details alone under personal record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I submit the form with phone details alone under personal record
    #And I should be able to see the record that phone number alone is added in the personal involved tab
    #And I quit the browser after passing the scenario
#
  #@Test17
  #Scenario: Submit form with Email details alone under personal record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I submit the form with Email details alone under personal record
    #And I should be able to see the record that Email alone is added in the personal involved tab
    #And I quit the browser after passing the scenario
#
  #@Test18
  #Scenario: Submit form with Vehicle details alone under personal record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I submit the form with Vehicle details alone under personal record
    #And I should be able to see the record that Vehicle alone is added in the personal involved tab
    #And I quit the browser after passing the scenario
#
  #@Test19
  #Scenario: Submit form with Bank details alone under personal record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I submit the form with Bank details alone under personal record
    #And I should be able to see the record that Bank alone is added in the personal involved tab
     #And I quit the browser after passing the scenario
    #
    #@Test20
  #Scenario: Submit form with IP details alone under personal record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I submit the form with IP details alone under personal record
    #And I should be able to see the record that IP Address alone is added in the personal involved tab
    #And I quit the browser after passing the scenario
    #
    #@Test21
  #Scenario: Submit form with Address details alone under company record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I submit the form with Address details alone under company record
    #And I should be able to see the record that Address alone is added in the company involved tab
    #And I quit the browser after passing the scenario
    #
    #@Test22
  #Scenario: Submit form with phone number alone under company record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I submit the form with Phone number details alone under company record
    #And I should be able to see the record that Phone number alone is added in the company involved tab
    #And I quit the browser after passing the scenario
    #
     #@Test23
  #Scenario: Submit form with Email alone under company record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I submit the form with Email details alone under company record
    #And I should be able to see the record that Email alone is added in the company involved tab
    #And I quit the browser after passing the scenario
    #
    #@Test24
  #Scenario: Submit form with Website alone under company record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I submit the form with Website details alone under company record
    #And I should be able to see the record that Website alone is added in the company involved tab
    #And I quit the browser after passing the scenario
    #
     #@Test25
  #Scenario: Submit form with Bank Details alone under company record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I submit the form with Bank details alone under company record
    #And I should be able to see the record that Bank alone is added in the company involved tab
    #And I quit the browser after passing the scenario
    #
     #@Test26
  #Scenario: Submit form with Vehicle  Details alone under company record
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I submit the form with Vehicle details alone under company record
    #And I should be able to see the record that Vehicle alone is added in the company involved tab
    #And I quit the browser after passing the scenario
    #
    #@Test27
  #Scenario: Verify the edit functionlity from Intel Source filter functionality
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Manage Intelligence option TC
    #And I am on the Show All page
    #Then I should see the edit the record with "IFB" on the page for edit
    #And I quit the browser after passing the scenario
    #
    #@Test28
    #Scenario: Verify the Adhoc Owner filter functionality
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Manage Intelligence option TC
    #And I am on the Show All page
    #Then I should change the Owner Name for the filtered record "IFB"
    #And I change the owner name to "Test User 3"
    #And I should see the confirmation message
    #And I quit the browser after passing the scenario
    #
    #@Test29
  #Scenario: Verify the Adhoc Owner filter functionality
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Manage Intelligence option TC
    #And I am on the Show All page
    #And I apply a filter with value "Test User 3" in Adhoc Owner filed
    #Then I should see the filtered records with "Test User 3" on the page using Adhoc Owner filed
    #And I quit the browser after passing the scenario
    #
    #
     #@Test30
  #Scenario: Verify the Operation Name filter functionality
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Manage Intelligence option TC
    #And I am on the Show All page
    #And I apply a filter with value "Value" in Operation Name filed
    #Then I should see the filtered records with "Value" on the page using Operation Name filed
    #And I quit the browser after passing the scenario
    #
    #
    #@Test31
  #Scenario: Verify the Intel Source filter functionality in My Sructured Intelligence Page
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Manage Intelligence option TC
    #And I am on the My Sructured Intelligence Page
    #And I apply a filter with value "IFB"
    #Then I should see the filtered records with "IFB" on the page
    #And I quit the browser after passing the scenario
    #
    #@Test32
  #Scenario: Verify the update button under the Person phone number filed
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in phone number under company
    #And I quit the browser after passing the scenario
    #
    #@Test33
  #Scenario: Verify the update button under the Person Address fileds
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in Address Field under person
    #And I quit the browser after passing the scenario
    #
    #@Test34
  #Scenario: Verify the update button under the Person Email filed
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in Email Field under person
    #And I quit the browser after passing the scenario
    #
     #@Test35
  #Scenario: Verify the update button under the Person Bank fileds
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in Bank Field under person
    #And I quit the browser after passing the scenario
    #
     #@Test36
  #Scenario: Verify the update button under the Person Vechile fileds
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in Vechile Field under person
    #And I quit the browser after passing the scenario
    #
     #@Test37
  #Scenario: Verify the update button under the Person IP filed
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in IP Field under person
    #And I quit the browser after passing the scenario
 #
 #@Test38
  #Scenario: Verify the update button under the Company phone number
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in phone number under company
    #And I quit the browser after passing the scenario
    #
    #@Test39
  #Scenario: Verify the update button under the Person fileds
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in Address Field under company
    #And I quit the browser after passing the scenario
    #
    #@Test40
  #Scenario: Verify the update button under the Person fileds
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in Email Field under company
    #And I quit the browser after passing the scenario
    #
     #@Test41
  #Scenario: Verify the update button under the Person fileds
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in Bank Field under company
    #And I quit the browser after passing the scenario
    #
     #@Test42
  #Scenario: Verify the update button under the Person fileds
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in Vechile Field under company
    #And I quit the browser after passing the scenario
    #
     #@Test43
  #Scenario: Verify the update button under the Person fileds
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the update option in Website Field under company
    #And I quit the browser after passing the scenario
    #
     @Test44
  Scenario: Verify Email valid under both company and person
    Given I am on the Zodiac login page for Create Intelligence TC
    And I enter a valid username and password from Excel for Create Intelligence TC
    When I click on the login button for Create Intelligence TC
    Then I click the Intelligence option TC
    And I select Create Intelligence option TC
    And I select Structured option TC
    When I verify the Email Field under company and person
    
      #@Test45
  #Scenario: Verify the alert YES option under company
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the alert YES option under company
    #And I quit the browser after passing the scenario
    #
      #@Test46
  #Scenario: Verify the alert No option under company
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the alert No option under company
    #And I quit the browser after passing the scenario
    #
     #@Test47
  #Scenario: Verify the alert YES option under person
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the alert YES option under person
    #And I quit the browser after passing the scenario
    #
      #@Test48
  #Scenario: Verify the alert No option under person
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the alert No option under perosn
    #And I quit the browser after passing the scenario
    #
    #@Test49
  #Scenario: Verify the ip validation
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I verify the IP validation under person
    #And I quit the browser after passing the scenario
    #
    #@Test50
  #Scenario: Verify the ip validation
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I verify the Website Validation under company
    #And I quit the browser after passing the scenario
    #
     #@Test51
  #Scenario: Verify Phone Number Validation under both company and person
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #When I verify the Phone Number Field under company and person
    #
    #@Test52
  #Scenario: Verify the Date validation
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I verify the Date invalidate
    #And I quit the browser after passing the scenario
    #
     #@Test52
  #Scenario: Verify the POSTAL Code validation
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I verify the Date invalidate
    #And I quit the browser after passing the scenario
    #
     #@Test53
  #Scenario: Verify the Linked Organization Tab enable
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I Verify the Linked Organization Tab enable when having a company Record
    #And I quit the browser after passing the scenario
    #
    #@Test54
  #Scenario: Verify the Linked Organization Tab disable
    #Given I am on the Zodiac login page for Create Intelligence TC
    #And I enter a valid username and password from Excel for Create Intelligence TC
    #When I click on the login button for Create Intelligence TC
    #Then I click the Intelligence option TC
    #And I select Create Intelligence option TC
    #And I select Structured option TC
    #And I Verify the Linked Organization Tab enable without having a company Record
    #And I quit the browser after passing the scenario
 