#@Overall
#Feature: Claim Referral Creation (Negative Scenarios)
#
  #  Scenario 1: Invalid login credentials
  #@negative1
  #Scenario: Invalid login credentials
    #Given I am on the zodiac login page for negative scenario
    #When I enter invalid credentials from Excel
    #And I click on the login button for negative scenario
    #Then I should see an error message indicating login failure
    #And I quit the browser
#
  #  Scenario 2: Invalid Reserve amount on the Create Referral form
  #@negative2
  #Scenario: Invalid Reserve amount on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I enter an invalid Reserve amount
    #And I click the Submit button
    #Then I should see a validation error message indicating that the Reserve amount is invalid
    #And I quit the browser
#
  # Scenario 3: Invalid data format in the Create Referral form
  #@negative3
  #Scenario: Invalid data format in Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I enter invalid NUMERIC data in email field
    #And I click the Submit button
    #Then I should see a validation error message for the incorrect field
    #And I quit the browser
#
  # Scenario 4: Duplicate referral submission
  #@negative4
  #Scenario: Duplicate referral submission
    #Given I am logged in and on the Create Referral form
    #When I fill the form with a claim number that already exists in the system
    #And I click the Submit button
    #Then I should see an error message indicating that the referral already exists
    #And I quit the browser
#
  # Scenario 5: System failure when submitting the referral (Urgency Reason Not Provided)
  #@negative5
  #Scenario: System failure when submitting the referral (Urgency Reason Not Submit)
    #Given I am logged in and on the Create Referral form
    #When I leave the urgency field as empty
    #And I click the Submit button
    #Then I should see an error message indicating that the urgency reason field is mandatory
    #And I quit the browser
#
  #  Scenario 6: Invalid email format in the Create Referral form
  #@negative6
  #Scenario: Invalid email format in Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I enter an invalid email format in fields Email
    #And I click the Submit button
    #Then I should see a validation error message for the incorrect email format
    #And I quit the browser
#
  #  Scenario 7: Invalid phone number format in the Create Referral form
  #@negative7
  #Scenario: Invalid phone number format in Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I enter an invalid phone number format in fields like Phone Number
    #And I click the Submit button
    #Then I should see a validation error message for the incorrect phone number format
    #And I quit the browser
#
  #  Scenario 8: Invalid date format in the Date of Loss field
  #@negative8
  #Scenario: Invalid date format in Date of Loss field
    #Given I am logged in and on the Create Referral form
    #When I enter an invalid date format in the Date of Loss field
    #And I click the Submit button
    #Then I should see a validation error message for the invalid date format
    #And I quit the browser
#
  #  Scenario 9: Missing Company Name
  #@negative9
  #Scenario: Missing Company Name on Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave Company Name field empty
    #And I try to click the Add button
    #Then I should see validation error messages for the missing company name field
    #And I quit the browser
#
  #  Scenario 10: Invalid post code format
  #@negative10
  #Scenario: Invalid post code format
    #Given I am logged in and on the Create Referral form
    #When I enter an invalid post code format in the "Post Code" field
    #And I try to click the Add button
    #Then I should see a validation error message for the invalid postal code format
    #And I quit the browser
#
  # Scenario 11: Invalid NIN number
  #@negative11
  #Scenario: Invalid NIN number
    #Given I am logged in and on the Create Referral form
    #When I enter an invalid NIN number
    #And I try to click the Add button for personal
    #Then I should see a validation error message for the invalid NIN number
    #And I quit the browser
#
  #  Scenario 12: Missing mandatory fields in the new party details section
  #@negative12
  #Scenario: Missing mandatory fields in New Personal Party details
    #Given I am logged in and on the Create Referral form
    #When I leave mandatory fields empty in the New Personal Party section such as First Name
    #And I try to click the Add button for personal
    #Then I should see a validation error message for the missing fields in the New Personal Party section
    #And I quit the browser
#
  #  Scenario 13: Missing mandatory fields in the new party details section
  #@negative13
  #Scenario: Missing mandatory fields in New Personal Party details
    #Given I am logged in and on the Create Referral form
    #When I leave mandatory fields empty in the New Personal Party section such as Last Name
    #And I try to click the Add button for personal
    #Then I should see a validation error message for the missing fields in the New Personal Party section Last Name
    #And I quit the browser
#
  #   Scenario 14: Missing required fields on the Create Referral form
  #@negative14
  #Scenario: Missing required fields on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required email field blank
    #And I click the Submit button
    #Then I should see validation error messages that Email is required
    #And I quit the browser
#
  # Scenario 15: Missing required Referrer Team on the Create Referral form
  #@negative15
  #Scenario: Missing required Referrer Team on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Referrer Team field blank
    #And I click the Submit button
    #Then I should see validation error messages that Referrer Team is required
    #And I quit the browser
#
  # Scenario 16: Missing required Referrer Location on the Create Referral form
  #@negative16
  #Scenario: Missing required Referrer Location on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Referrer Location field blank
    #And I click the Submit button
    #Then I should see validation error messages that Referrer Location is required
    #And I quit the browser
#
  # Scenario 17: Missing required Referral Type on the Create Referral form
  #@negative17
  #Scenario: Missing required Referral Type on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Referral Type field blank
    #And I click the Submit button
    #Then I should see validation error messages that Referral Type is required
    #And I quit the browser
#
  # Scenario 18: Missing required Urgent on the Create Referral form
  #@negative18
  #Scenario: Missing required Urgent on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Urgent field blank
    #And I click the Submit button
    #Then I should see that Urgent only accepts Yes or No
    #And I quit the browser
#
  # Scenario 19: Missing required Urgent Reason on the Create Referral form
  #@negative19
  #Scenario: Missing required Urgent Reason on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Urgent Reason field blank
    #And I click the Submit button
    #Then I should see validation error messages that Urgent Reason is required
    #And I quit the browser
#
  # Scenario 20: Missing required Refer To on the Create Referral form
  #@negative20
  #Scenario: Missing required Refer To on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Refer To field blank
    #And I click the Submit button
    #Then I should see validation error messages that Refer To is required
    #And I quit the browser
#
  # Scenario 21: Missing required Date of Loss on the Create Referral form
  #@negative21
  #Scenario: Missing required Date of Loss on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Date of Loss field blank
    #And I click the Submit button
    #Then I should see validation error messages that Date of Loss is required
    #And I quit the browser
#
  # Scenario 22: Missing required Line of Business on the Create Referral form
  #@negative22
  #Scenario: Missing required Line of Business on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Line of Business field blank
    #And I click the Submit button
    #Then I should see validation error messages that Line of Business is required
    #And I quit the browser
#
  # Scenario 23: Missing required Market Facing Unit on the Create Referral form
  #@negative23
  #Scenario: Missing required Market Facing Unit on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Market Facing Unit field blank
    #And I click the Submit button
    #Then I should see validation error messages that Market Facing Unit is required
    #And I quit the browser
#
  # Scenario 24: Missing required Claim Type on the Create Referral form
  #@negative24
  #Scenario: Missing required Claim Type on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Claim Type field blank
    #And I click the Submit button
    #Then I should see validation error messages that Claim Type is required
    #And I quit the browser
#
  # Scenario 25: Missing required Reserve on the Create Referral form
  #@negative25
  #Scenario: Missing required Reserve on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Reserve field blank
    #And I click the Submit button
    #Then I should see validation error messages that Reserve is required
    #And I quit the browser
#
  # Scenario 26: Missing required Concern Type on the Create Referral form
  #@negative26
  #Scenario: Missing required Concern Type on the Create Referral form
    #Given I am logged in and on the Create Referral form
    #When I leave required Concern Type field blank
    #And I click the Submit button
    #Then I should see validation error messages that Concern Type is required
    #And I quit the browser
#
    #Manage Referrals