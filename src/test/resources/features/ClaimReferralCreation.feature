Feature: Claim Referral Creation
  As a user of Zodiac
  I want to create a referral
  So that I can submit referral information for a claim.
  @Regression
  Scenario: 
    Given I am on the Zodiac login page
    And I enter a valid username and password from Excel
    When I click on the login button
    Then I should be redirected to the Dashboard
    When I click the Claim Referrals option
    And I select Create Referral option
    Then I fill the Create Referral form with test data from Excel
    Then I should submit the form successfully and receive a confirmation message

