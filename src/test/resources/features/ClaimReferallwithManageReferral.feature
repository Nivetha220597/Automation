@SmokeAll
Feature: Manage Referral
  As a user of Zodiac
  I want to view Mange referrals page
  So that I can view Manage referrals information

  @Smoke1
  Scenario: Verify to view Manage Refferals Page
    Given I select Manage Referral option
    And I quit the browser after passing the scenario

  @Smoke2
  Scenario: Verify to see My Team records in My Team Tab
    Given I select Manage Referral option
    And I should see the My Team records
    And I quit the browser after passing the scenario

  @Smoke3
  Scenario: Verify to edit the referral via Manage referral in My Team Tab (wait)
    Given I select Manage Referral option
    And I should edit the referral
    And I quit the browser after passing the scenario

  @Smoke4
  Scenario: Verify to edit the referral via Manage referral in My Team Tab
    Given I select Manage Referral option
    And I should edit the allocated to value "Large Loss Team"
    And I quit the browser after passing the scenario

  @Smoke5
  Scenario: Verify to claim reference filter box functionlity in My Team Tab
    Given I select Manage Referral option
    And I can provide value "TEST0910" as in the claim reference filter box
    And I should see the My Team records "TEST0910"
    And I quit the browser after passing the scenario

  @Smoke6
  Scenario: Verify to Who referred filter box functionlity in My Team Tab
    Given I select Manage Referral option
    And I can provide value "CIU Motor" as in the Who referred filter box
    And I should see the My Team records "CIU Motor"
    And I quit the browser after passing the scenario

  @Smoke7
  Scenario: Verify to Referral Owner filter box functionlity in My Team Tab
    Given I select Manage Referral option
    And I can provide value "Test User 3" as in the Referral Owner filter box
    And I should see the My Team records "Test User 3"
    And I quit the browser after passing the scenario

  @Smoke8
  Scenario: Verify to Reserve filter box functionlity in My Team Tab
    Given I select Manage Referral option
    And I can provide value "1200" as in the Reserve filter box
    And I should see the My Team records "1200"
    And I quit the browser after passing the scenario

  @Smoke9
  Scenario: Verify to Urgency filter box functionlity in My Team Tab
    Given I select Manage Referral option
    And I can provide value "Urgent Reason" as in the Urgency filter box
    And I should see the My Team records "Urgent Reason"
    And I quit the browser after passing the scenario

  @Smoke10
  Scenario: Verify to Referred To filter box functionlity in My Team Tab
    Given I select Manage Referral option
    And I can provide value "Motor Detection Hub" as in the Referred To filter box
    And I should see the My Team records "Motor Detection Hub"
    And I quit the browser after passing the scenario

  @Smoke11
  Scenario: Verify to Referral Status filter box functionlity in My Team Tab
    Given I select Manage Referral option
    And I can provide value "Open" as in the Referral Status filter box
    And I should see the My Team records "Open"
    And I quit the browser after passing the scenario

  @Smoke12
  Scenario: Verify to the date of referral filter box functionality in My Team Tab
    Given I select Manage Referral option
    And I should verify the sorting of the date of referral in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke13
  Scenario: Verify sorting of the claim reference field in ascending and descending order in My Team Tab
    Given I select Manage Referral option
    And I should verify the sorting of the claim reference field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke14
  Scenario: Verify sorting of the who referred field in ascending and descending order in My Team Tab
    Given I select Manage Referral option
    And I should verify the sorting of the who referred field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke15
  Scenario: Verify sorting of the referral owner field in ascending and descending order in My Team Tab
    Given I select Manage Referral option
    And I should verify the sorting of the referral owner field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke16
  Scenario: Verify sorting of the reserve field in ascending and descending order in My Team Tab
    Given I select Manage Referral option
    And I should verify the sorting of the reserve field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke17
  Scenario: Verify sorting of the referred to field in ascending and descending order in My Team Tab
    Given I select Manage Referral option
    And I should verify the sorting of the referred to field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke18
  Scenario: Verify sorting of the urgent field in ascending and descending order in My Team Tab
    Given I select Manage Referral option
    And I should verify the sorting of the urgent field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke19
  Scenario: Verify sorting of the reason for urgency field in ascending and descending order in My Team Tab
    Given I select Manage Referral option
    And I should verify the sorting of the reason for urgency field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke20
  Scenario: Verify sorting of the referral status field in ascending and descending order in My Team Tab
    Given I select Manage Referral option
    And I should verify the sorting of the referral status field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke21
  Scenario: Verify sorting of the overdue field in ascending and descending order in My Team Tab
    Given I select Manage Referral option
    And I should verify the sorting of the overdue field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke22
  Scenario: Verify to see My Team records in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should see the My Team records
    And I quit the browser after passing the scenario

  @Smoke23
  Scenario: Verify to edit the referral via Manage referral in My Refferals Tab (wait)
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should edit the referral
    And I quit the browser after passing the scenario

  @Smoke24
  Scenario: Verify to edit the referral via Manage referral in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should edit the allocated to value "Large Loss Team"
    And I quit the browser after passing the scenario

  @Smoke25
  Scenario: Verify to claim reference filter box functionlity in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I can provide value "TEST0910" as in the claim reference filter box
    And I should see the My Team records "TEST0910"
    And I quit the browser after passing the scenario

  @Smoke26
  Scenario: Verify to Who referred filter box functionlity in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I can provide value "CIU Motor" as in the Who referred filter box
    And I should see the My Team records "CIU Motor"
    And I quit the browser after passing the scenario

  @Smoke27
  Scenario: Verify to Referral Owner filter box functionlity in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I can provide value "Test User 3" as in the Referral Owner filter box
    And I should see the My Team records "Test User 3"
    And I quit the browser after passing the scenario

  @Smoke28
  Scenario: Verify to Reserve filter box functionlity in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I can provide value "1200" as in the Reserve filter box
    And I should see the My Team records "1200"
    And I quit the browser after passing the scenario

  @Smoke29
  Scenario: Verify to Urgency filter box functionlity in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I can provide value "Urgent Reason" as in the Urgency filter box
    And I should see the My Team records "Urgent Reason"
    And I quit the browser after passing the scenario

  @Smoke30
  Scenario: Verify to Referred To filter box functionlity in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I can provide value "Motor Detection Hub" as in the Referred To filter box
    And I should see the My Team records "Motor Detection Hub"
    And I quit the browser after passing the scenario

  @Smoke31
  Scenario: Verify to Referral Status filter box functionlity in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I can provide value "Open" as in the Referral Status filter box
    And I should see the My Team records "Open"
    And I quit the browser after passing the scenario

  @Smoke32
  Scenario: Verify to the date of referral filter box functionality in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should verify the sorting of the date of referral in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke33
  Scenario: Verify sorting of the claim reference field in ascending and descending order in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should verify the sorting of the claim reference field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke34
  Scenario: Verify sorting of the who referred field in ascending and descending order in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should verify the sorting of the who referred field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke35
  Scenario: Verify sorting of the referral owner field in ascending and descending order in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should verify the sorting of the referral owner field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke36
  Scenario: Verify sorting of the reserve field in ascending and descending order in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should verify the sorting of the reserve field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke37
  Scenario: Verify sorting of the referred to field in ascending and descending order in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should verify the sorting of the referred to field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke38
  Scenario: Verify sorting of the urgent field in ascending and descending order in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should verify the sorting of the urgent field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke39
  Scenario: Verify sorting of the reason for urgency field in ascending and descending order in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should verify the sorting of the reason for urgency field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke40
  Scenario: Verify sorting of the referral status field in ascending and descending order in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should verify the sorting of the referral status field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke41
  Scenario: Verify sorting of the overdue field in ascending and descending order in My Refferals Tab
    Given I select Manage Referral option
    And I select My Refferals Tab
    And I should verify the sorting of the overdue field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke42
  Scenario: Verify to see My Team records in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should see the My Team records
    And I quit the browser after passing the scenario

  @Smoke43
  Scenario: Verify to edit the referral via Manage referral in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should edit the referral
    And I quit the browser after passing the scenario

  @Smoke44
  Scenario: Verify to edit the referral via Manage referral in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should edit the allocated to value "Large Loss Team"
    And I quit the browser after passing the scenario

  @Smoke45
  Scenario: Verify to claim reference filter box functionlity in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I can provide value "TEST0910" as in the claim reference filter box
    And I should see the My Team records "TEST0910"
    And I quit the browser after passing the scenario

  @Smoke46
  Scenario: Verify to Who referred filter box functionlity in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I can provide value "CIU Motor" as in the Who referred filter box
    And I should see the My Team records "CIU Motor"
    And I quit the browser after passing the scenario

  @Smoke47
  Scenario: Verify to Referral Owner filter box functionlity in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I can provide value "Test User 3" as in the Referral Owner filter box
    And I should see the My Team records "Test User 3"
    And I quit the browser after passing the scenario

  @Smoke48
  Scenario: Verify to Reserve filter box functionlity in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I can provide value "1200" as in the Reserve filter box
    And I should see the My Team records "1200"
    And I quit the browser after passing the scenario

  @Smoke49
  Scenario: Verify to Urgency filter box functionlity in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I can provide value "Urgent Reason" as in the Urgency filter box
    And I should see the My Team records "Urgent Reason"
    And I quit the browser after passing the scenario

  @Smoke50
  Scenario: Verify to Referred To filter box functionlity in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I can provide value "Motor Detection Hub" as in the Referred To filter box
    And I should see the My Team records "Motor Detection Hub"
    And I quit the browser after passing the scenario

  @Smoke51
  Scenario: Verify to Referral Status filter box functionlity in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I can provide value "Open" as in the Referral Status filter box
    And I should see the My Team records "Open"
    And I quit the browser after passing the scenario

  @Smoke52
  Scenario: Verify to the date of referral filter box functionality in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should verify the sorting of the date of referral in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke53
  Scenario: Verify sorting of the claim reference field in ascending and descending order in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should verify the sorting of the claim reference field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke54
  Scenario: Verify sorting of the who referred field in ascending and descending order in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should verify the sorting of the who referred field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke55
  Scenario: Verify sorting of the referral owner field in ascending and descending order in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should verify the sorting of the referral owner field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke56
  Scenario: Verify sorting of the reserve field in ascending and descending order in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should verify the sorting of the reserve field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke57
  Scenario: Verify sorting of the referred to field in ascending and descending order in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should verify the sorting of the referred to field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke58
  Scenario: Verify sorting of the urgent field in ascending and descending order in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should verify the sorting of the urgent field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke59
  Scenario: Verify sorting of the reason for urgency field in ascending and descending order in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should verify the sorting of the reason for urgency field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke60
  Scenario: Verify sorting of the referral status field in ascending and descending order in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should verify the sorting of the referral status field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke61
  Scenario: Verify sorting of the overdue field in ascending and descending order in Show All Tab
    Given I select Manage Referral option
    And I select Show All Tab
    And I should verify the sorting of the overdue field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke62
  Scenario: Verify to see My Team records in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should see the My Team records
    And I quit the browser after passing the scenario

  @Smoke63
  Scenario: Verify to edit the referral via Manage referral in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should edit the referral
    And I quit the browser after passing the scenario

  @Smoke64
  Scenario: Verify to edit the referral via Manage referral in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should edit the allocated to value "CIU Glasgow- Motor & Scottish Casualty"
    And I quit the browser after passing the scenario

  @Smoke65
  Scenario: Verify to claim reference filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I can provide value "INV123" as in the claim reference filter box
    And I should see the My Team records "INV123"
    And I quit the browser after passing the scenario

  @Smoke66
  Scenario: Verify to Who referred filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I can provide value "CIU Detection Team" as in the Who referred filter box
    And I should see the My Team records "CIU Detection Team"
    And I quit the browser after passing the scenario

  @Smoke67
  Scenario: Verify to Referral Owner filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I can provide value "Test User 3" as in the Referral Owner filter box
    And I should see the My Team records "Test User 3"
    And I quit the browser after passing the scenario

  @Smoke68
  Scenario: Verify to Reserve filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I can provide value "100" as in the Reserve filter box
    And I should see the My Team records "100"
    And I quit the browser after passing the scenario

  @Smoke69
  Scenario: Verify to Urgency filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I can provide value "Urgent Reason" as in the Urgency filter box
    And I should see the My Team records "Urgent Reason"
    And I quit the browser after passing the scenario

  @Smoke70
  Scenario: Verify to Referral Status filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I can provide value "Open" as in the Referral Status filter box
    And I should see the My Team records "Open"
    And I quit the browser after passing the scenario

  @Smoke71
  Scenario: Verify to Referral Status filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I can provide value "Draft" as in the Referral Status filter box
    And I should see the My Team records "Draft"
    And I quit the browser after passing the scenario

  @Smoke72
  Scenario: Verify to the date of referral filter box functionality in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should verify the sorting of the date of referral in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke73
  Scenario: Verify sorting of the claim reference field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should verify the sorting of the claim reference field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke74
  Scenario: Verify sorting of the who referred field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should verify the sorting of the who referred field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke75
  Scenario: Verify sorting of the referral owner field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should verify the sorting of the referral owner field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke76
  Scenario: Verify sorting of the reserve field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should verify the sorting of the reserve field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke77
  Scenario: Verify sorting of the urgent field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should verify the sorting of the urgent field in ascending and descending order for team level records
    And I quit the browser after passing the scenario

  @Smoke78
  Scenario: Verify sorting of the reason for urgency field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should verify the sorting of the reason for urgency field in ascending and descending order for team level records
    And I quit the browser after passing the scenario

  @Smoke79
  Scenario: Verify sorting of the referral status field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should verify the sorting of the referral status field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke80
  Scenario: Verify sorting of the overdue field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow- Motor & Scottish Casualty Tab
    And I should verify the sorting of the overdue field in ascending and descending order for team level records
    And I quit the browser after passing the scenario
@Smoke81
  Scenario: Verify to see My Team records in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should see the My Team records
    And I quit the browser after passing the scenario

  @Smoke82
  Scenario: Verify to edit the referral via Manage referral in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should edit the referral
    And I quit the browser after passing the scenario

  @Smoke83
  Scenario: Verify to edit the referral via Manage referral in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should edit the allocated to value "CIU Glasgow- Motor & Scottish Casualty"
    And I quit the browser after passing the scenario

  @Smoke84
  Scenario: Verify to claim reference filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I can provide value "OTENW0003" as in the claim reference filter box
    And I should see the My Team records "OTENW0003"
    And I quit the browser after passing the scenario

  @Smoke85
  Scenario: Verify to Who referred filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I can provide value "CIU Intelligence Team" as in the Who referred filter box
    And I should see the My Team records "CIU Intelligence Team"
    And I quit the browser after passing the scenario

  @Smoke86
  Scenario: Verify to Referral Owner filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I can provide value "Test User 2" as in the Referral Owner filter box
    And I should see the My Team records "Test User 2"
    And I quit the browser after passing the scenario

  @Smoke87
  Scenario: Verify to Reserve filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I can provide value "1211" as in the Reserve filter box
    And I should see the My Team records "1211"
    And I quit the browser after passing the scenario

  @Smoke88
  Scenario: Verify to Urgency filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I can provide value "City" as in the Urgency filter box
    And I should see the My Team records "City"
    And I quit the browser after passing the scenario

  @Smoke89
  Scenario: Verify to Referral Status filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I can provide value "Rejected" as in the Referral Status filter box
    And I should see the My Team records "Rejected"
    And I quit the browser after passing the scenario

  @Smoke90
  Scenario: Verify to Referral Status filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I can provide value "Draft" as in the Referral Status filter box
    And I should see the My Team records "Draft"
    And I quit the browser after passing the scenario

  @Smoke91
  Scenario: Verify to the date of referral filter box functionality in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should verify the sorting of the date of referral in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke92
  Scenario: Verify sorting of the claim reference field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should verify the sorting of the claim reference field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke92
  Scenario: Verify sorting of the who referred field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should verify the sorting of the who referred field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke93
  Scenario: Verify sorting of the referral owner field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should verify the sorting of the referral owner field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke94
  Scenario: Verify sorting of the reserve field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should verify the sorting of the reserve field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke95
  Scenario: Verify sorting of the urgent field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should verify the sorting of the urgent field in ascending and descending order for team level records
    And I quit the browser after passing the scenario

  @Smoke96
  Scenario: Verify sorting of the reason for urgency field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should verify the sorting of the reason for urgency field in ascending and descending order for team level records
    And I quit the browser after passing the scenario

  @Smoke97
  Scenario: Verify sorting of the referral status field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should verify the sorting of the referral status field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke98
  Scenario: Verify sorting of the overdue field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Glasgow - Property Tab
    And I should verify the sorting of the overdue field in ascending and descending order for team level records
    And I quit the browser after passing the scenario
    @Smoke100
  Scenario: Verify to see My Team records in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should see the My Team records
    And I quit the browser after passing the scenario

  @Smoke101
  Scenario: Verify to edit the referral via Manage referral in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should edit the referral
    And I quit the browser after passing the scenario

  @Smoke102
  Scenario: Verify to edit the referral via Manage referral in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should edit the allocated to value " CIU Glasgow- Motor & Scottish Casualty "
    And I quit the browser after passing the scenario

  @Smoke103
  Scenario: Verify to claim reference filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I can provide value "4567897" as in the claim reference filter box
    And I should see the My Team records "4567897"
    And I quit the browser after passing the scenario

  @Smoke104
  Scenario: Verify to Who referred filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I can provide value "CIU Casualty" as in the Who referred filter box
    And I should see the My Team records "CIU Casualty"
    And I quit the browser after passing the scenario

  @Smoke105
  Scenario: Verify to Referral Owner filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I can provide value "Test User 2" as in the Referral Owner filter box
    And I should see the My Team records "Test User 2"
    And I quit the browser after passing the scenario

  @Smoke106
  Scenario: Verify to Reserve filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I can provide value "2222" as in the Reserve filter box
    And I should see the My Team records "2222"
    And I quit the browser after passing the scenario

  @Smoke107
  Scenario: Verify to Urgency filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I can provide value "City" as in the Urgency filter box
    And I should see the My Team records "City"
    And I quit the browser after passing the scenario

  @Smoke108
  Scenario: Verify to Referral Status filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I can provide value "Rejected" as in the Referral Status filter box
    And I should see the My Team records "Rejected"
    And I quit the browser after passing the scenario

  @Smoke109
  Scenario: Verify to Referral Status filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I can provide value "Draft" as in the Referral Status filter box
    And I should see the My Team records "Draft"
    And I quit the browser after passing the scenario

  @Smoke110
  Scenario: Verify to the date of referral filter box functionality in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should verify the sorting of the date of referral in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke111
  Scenario: Verify sorting of the claim reference field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should verify the sorting of the claim reference field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke112
  Scenario: Verify sorting of the who referred field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should verify the sorting of the who referred field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke113
  Scenario: Verify sorting of the referral owner field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should verify the sorting of the referral owner field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke114
  Scenario: Verify sorting of the reserve field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should verify the sorting of the reserve field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke115
  Scenario: Verify sorting of the urgent field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should verify the sorting of the urgent field in ascending and descending order for team level records
    And I quit the browser after passing the scenario

  @Smoke116
  Scenario: Verify sorting of the reason for urgency field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should verify the sorting of the reason for urgency field in ascending and descending order for team level records
    And I quit the browser after passing the scenario

  @Smoke117
  Scenario: Verify sorting of the referral status field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should verify the sorting of the referral status field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke118
  Scenario: Verify sorting of the overdue field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select CIU Casualty Tab
    And I should verify the sorting of the overdue field in ascending and descending order for team level records
    And I quit the browser after passing the scenario
	@Smoke119
  Scenario: Verify to see My Team records in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should see the My Team records
    And I quit the browser after passing the scenario

  @Smoke120
  Scenario: Verify to edit the referral via Manage referral in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should edit the referral
    And I quit the browser after passing the scenario

  @Smoke121
  Scenario: Verify to edit the referral via Manage referral in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should edit the allocated to value " CIU Glasgow- Motor & Scottish Casualty "
    And I quit the browser after passing the scenario

  @Smoke122
  Scenario: Verify to claim reference filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I can provide value "COGEN-CAA123456" as in the claim reference filter box
    And I should see the My Team records "COGEN-CAA123456"
    And I quit the browser after passing the scenario

  @Smoke123
  Scenario: Verify to Who referred filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I can provide value "CIU NetReveal Team" as in the Who referred filter box
    And I should see the My Team records "CIU NetReveal Team"
    And I quit the browser after passing the scenario

  @Smoke124
  Scenario: Verify to Referral Owner filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I can provide value "Test User 2" as in the Referral Owner filter box
    And I should see the My Team records "Test User 2"
    And I quit the browser after passing the scenario

  @Smoke125
  Scenario: Verify to Reserve filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I can provide value "2000" as in the Reserve filter box
    And I should see the My Team records "2000"
    And I quit the browser after passing the scenario

  @Smoke126
  Scenario: Verify to Urgency filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I can provide value "Immediate action required due to risk of fraud." as in the Urgency filter box
    And I should see the My Team records "Immediate action required due to risk of fraud."
    And I quit the browser after passing the scenario

  @Smoke127
  Scenario: Verify to Referral Status filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I can provide value "Rejected" as in the Referral Status filter box
    And I should see the My Team records "Rejected"
    And I quit the browser after passing the scenario

  @Smoke128
  Scenario: Verify to Referral Status filter box functionlity in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I can provide value "Open" as in the Referral Status filter box
    And I should see the My Team records "Open"
    And I quit the browser after passing the scenario

  @Smoke129
  Scenario: Verify to the date of referral filter box functionality in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should verify the sorting of the date of referral in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke130
  Scenario: Verify sorting of the claim reference field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should verify the sorting of the claim reference field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke131
  Scenario: Verify sorting of the who referred field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should verify the sorting of the who referred field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke132
  Scenario: Verify sorting of the referral owner field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should verify the sorting of the referral owner field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke133
  Scenario: Verify sorting of the reserve field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should verify the sorting of the reserve field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke134
  Scenario: Verify sorting of the urgent field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should verify the sorting of the urgent field in ascending and descending order for team level records
    And I quit the browser after passing the scenario

  @Smoke135
  Scenario: Verify sorting of the reason for urgency field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should verify the sorting of the reason for urgency field in ascending and descending order for team level records
    And I quit the browser after passing the scenario

  @Smoke136
  Scenario: Verify sorting of the referral status field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should verify the sorting of the referral status field in ascending and descending order
    And I quit the browser after passing the scenario

  @Smoke137
  Scenario: Verify sorting of the overdue field in ascending and descending order in Show Teams Tab
    Given I select Manage Referral option
    Then I select Show Teams Tab 
    And I select Fraud Rings Tab
    And I should verify the sorting of the overdue field in ascending and descending order for team level records
    And I quit the browser after passing the scenario
    