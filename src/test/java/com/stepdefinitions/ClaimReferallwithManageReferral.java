package com.stepdefinitions;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;


public class ClaimReferallwithManageReferral {

	private WebDriverWait wait;
	private WebDriver driver;
	JavascriptExecutor js = (JavascriptExecutor) driver;

	@Given("I select Manage Referral option")
	public void i_select_manage_referral_option() throws InvalidFormatException, IOException {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://app-zodiac-test.azurewebsites.net/");
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-username")));
		System.out.println("I am on the Zodiac login page");

		Object[][] loginData = ExcelReader.getExcelData(
				"C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx",
				"Sheet1");
		// Assuming login data is in the first row (index 0)
		String username = (String) loginData[0][0];
		String password = (String) loginData[0][1];

		WebElement usernameField = driver.findElement(By.xpath("//input[@id='okta-signin-username']"));
		usernameField.sendKeys(username);

		WebElement passwordField = driver.findElement(By.xpath("//input[@id='okta-signin-password']"));
		passwordField.sendKeys(password);

		System.out.println("Entered username: " + username);
		System.out.println("Entered password: " + password);

		WebElement loginBtn = driver.findElement(By.xpath("//input[@id='okta-signin-submit']"));
		loginBtn.click();
		System.out.println("Clicked the login button");
		System.out.println("Successfully redirected to the Dashboard");
		WebElement claimReferrals = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Claim Referrals']")));
		claimReferrals.click();
		System.out.println("Claim Referrals option clicked");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		WebElement manageReferralButton = driver
				.findElement(By.xpath("//a[normalize-space(text())='Manage Referrals']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", manageReferralButton);

	}

	@Then("I should see the My Team records")
	public void i_should_see_the_my_team_records() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(80));

		boolean hasNextPage = true;

		while (hasNextPage) {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(
					By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
			List<WebElement> rows = wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//tr[@class='p-selectable-row ng-star-inserted']")));

			if (rows.isEmpty()) {
				System.out.println("No records found.");
			} else {
				System.out.println("Records on this page: ");
				for (WebElement row : rows) {
					String rowText = row.getText();
					System.out.println(rowText);
				}
			}

			List<WebElement> nextButton = driver
					.findElements(By.xpath("//button[@class='p-paginator-next p-paginator-element p-link p-ripple']"));

			if (!nextButton.isEmpty() && nextButton.get(0).isEnabled()) {
				nextButton.get(0).click();
				Thread.sleep(2000);
			} else {
				hasNextPage = false;
			}
		}
	}

	@And("I should edit the referral")
	public void i_should_edit_the_referral() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		WebElement firstRow = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//tr[@class='p-selectable-row ng-star-inserted'])[1]")));

		String rowText = firstRow.getText();

		js.executeScript("arguments[0].click();", firstRow);

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		WebElement ownerInputField = driver.findElement(By.xpath("//input[@name='assignedTo']"));
		System.out.println(ownerInputField.getAttribute("value"));
		js.executeScript("arguments[0].click();", ownerInputField);
		js.executeScript("arguments[0].value='';", ownerInputField);
		this.wait.until(ExpectedConditions.attributeToBe(ownerInputField, "value", ""));
		try {
			WebElement confirmationMessage = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[text()='Referral owner removed successfully']")));
			System.out.println("Confirmation message displayed: " + confirmationMessage.getText());
		} catch (TimeoutException e) {
			System.out.println("Confirmation message not found or took too long.");
		}

		ownerInputField.sendKeys("Test User 3");

		WebElement newOwnerOption = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//li[@role='option']//span[text()='Test User 3']")));
		newOwnerOption.click();

		try {
			WebElement confirmationMessage = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[text()='Referral owner assigned successfully']")));
			System.out.println("Confirmation message displayed: " + confirmationMessage.getText());
		} catch (TimeoutException e) {
			System.out.println("Confirmation message not found or took too long.");
		}

		System.out.println("Updated 'Referral Owner' to: Test User 3");

		ownerInputField.clear();
		Thread.sleep(500);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		try {
			WebElement confirmationMessage = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[text()='Referral owner removed successfully']")));
			System.out.println("Confirmation message displayed: " + confirmationMessage.getText());
		} catch (TimeoutException e) {
			System.out.println("Confirmation message not found or took too long.");
		}
	}

	@And("I should edit the allocated to value {string}")
	public void i_should_edit_the_allocated_to_value(String newValue) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		WebElement firstRow = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//tr[@class='p-selectable-row ng-star-inserted'])[1]")));

		String rowText = firstRow.getText();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		
		firstRow.click();
		// js.executeScript("arguments[0].click();", firstRow);

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement dropdown = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@name='referrerTeamId']")));
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(dropdown));
		//System.out.println(dropdown.getText());
		dropdown.click();

		WebElement allocatedTo = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//option[text()= " + newValue + "]")));
		System.out.println("Allocated to value: " + allocatedTo.getText());
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		allocatedTo.click();
		dropdown.click();

		System.out.println(dropdown.getText());

		allocatedTo.click();
		System.out.println("Allocated to value edited to: " + newValue);
	}

	@And("I can provide value {string} as in the claim reference filter box")
	public void i_can_provide_value_as_in_the_claim_referrence_filter_box(String filterValue)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='claimReference']")));

		dropdownTrigger.click();
		dropdownTrigger.sendKeys(filterValue);

		WebElement filterOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
		filterOption.click();

		System.out.println("Selected filter: " + filterValue);

		System.out.println("Clicked dropdownTrigger");
	}

	@And("I can provide value {string} as in the Who referred filter box")
	public void i_can_provide_value_as_in_the_who_referred_filter_box(String filterValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));

		WebElement dropdownTrigger = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Any']")));

		dropdownTrigger.click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement filterOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
		filterOption.click();

		System.out.println("Selected filter: " + filterValue);

		System.out.println("Clicked dropdownTrigger");
	}

	@Then("I should see the My Team records {string}")
	public void i_should_see_the_my_team_record(String filterValue) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		boolean hasNextPage = true;

		while (hasNextPage) {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(
					By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));

			List<WebElement> noDataMessage = driver.findElements(By.xpath("//td[text()='No data found']"));
			if (!noDataMessage.isEmpty()) {
				System.out.println("Selected filter: " + noDataMessage.get(0).getText());
				System.out.println("Selected filter: " + filterValue);
				break;
			}

			List<WebElement> rows = wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//tr[@class='p-selectable-row ng-star-inserted']")));

			if (rows.isEmpty()) {
				System.out.println("No records found on this page.");
			} else {
				System.out.println("Records on this page: ");
				for (WebElement row : rows) {
					String rowText = row.getText();
					System.out.println(rowText);
				}
			}

			List<WebElement> nextButton = driver
					.findElements(By.xpath("//button[@class='p-paginator-next p-paginator-element p-link p-ripple']"));
			if (!nextButton.isEmpty() && nextButton.get(0).isEnabled()) {
				nextButton.get(0).click();
				Thread.sleep(2000);
			} else {
				hasNextPage = false;
			}
		}
	}

	@And("I can provide value {string} as in the Referral Owner filter box")
	public void i_can_provide_value_as_in_the_referral_owner_filter_box(String filterValue)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='referralOwner']")));

		dropdownTrigger.click();
		dropdownTrigger.sendKeys(filterValue);

		WebElement filterOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
		filterOption.click();

		System.out.println("Selected filter: " + filterValue);

		System.out.println("Clicked dropdownTrigger");
	}

	@And("I can provide value {string} as in the Reserve filter box")
	public void i_can_provide_value_as_in_the_reserve_filter_box(String filterValue) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//input[@class='p-inputtext p-component ng-star-inserted'])[1]")));

		dropdownTrigger.click();
		dropdownTrigger.sendKeys(filterValue);
		dropdownTrigger.sendKeys(Keys.ENTER);

	}

	@And("I can provide value {string} as in the Urgency filter box")
	public void i_can_provide_value_as_in_the_urgency_filter_box(String filterValue) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//input[@class='p-inputtext p-component ng-star-inserted'])[2]")));

		dropdownTrigger.click();
		dropdownTrigger.sendKeys(filterValue);
		dropdownTrigger.sendKeys(Keys.ENTER);

	}

	@And("I can provide value {string} as in the Referred To filter box")
	public void i_can_provide_value_as_in_the_referred_to_filter_box(String filterValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//span[contains(@class, 'ng-tns-c81-12') and contains(@class, 'p-dropdown-label') and contains(@class, 'p-inputtext') and text()='Any']")));

		dropdownTrigger.click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement filterOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
		filterOption.click();

		System.out.println("Selected filter: " + filterValue);

		System.out.println("Clicked dropdownTrigger");
	}

	@And("I can provide value {string} as in the Referral Status filter box")
	public void i_can_provide_value_as_in_the_referral_status_filter_box(String filterValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//span[contains(@class, 'ng-tns-c81-14') and contains(@class, 'p-dropdown-label') and contains(@class, 'p-inputtext') and text()='Any']")));

		dropdownTrigger.click();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement filterOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
		filterOption.click();

		System.out.println("Selected filter: " + filterValue);

		System.out.println("Clicked dropdownTrigger");
	}

	@Then("I should verify the sorting of the date of referral in ascending and descending order")
	public void i_should_verify_the_sorting_date_of_referral() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[1]")));
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();

	}

	@Then("I should verify the sorting of the claim reference field in ascending and descending order")
	public void i_should_verify_the_sorting_of_claim_reference() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[2]")));
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}

	@Then("I should verify the sorting of the who referred field in ascending and descending order")
	public void i_should_verify_the_sorting_of_who_referred() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[3]")));

		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}

	@Then("I should verify the sorting of the referral owner field in ascending and descending order")
	public void i_should_verify_the_sorting_of_referral_owner() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[4]")));

		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}

	@Then("I should verify the sorting of the reserve field in ascending and descending order")
	public void i_should_verify_the_sorting_of_reserve() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[5]")));

		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}

	@Then("I should verify the sorting of the referred to field in ascending and descending order")
	public void i_should_verify_the_sorting_of_referred_to() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[6]")));

		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}

	@Then("I should verify the sorting of the urgent field in ascending and descending order")
	public void i_should_verify_the_sorting_of_urgent() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[7]")));

		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}

	@Then("I should verify the sorting of the reason for urgency field in ascending and descending order")
	public void i_should_verify_the_sorting_of_reason_for_urgency() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[8]")));
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}

	@Then("I should verify the sorting of the referral status field in ascending and descending order")
	public void i_should_verify_the_sorting_of_referral_status() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[9]")));
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}

	@Then("I should verify the sorting of the overdue field in ascending and descending order")
	public void i_should_verify_the_sorting_of_overdue() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[10]")));

		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}

	@And("I select My Refferals Tab")
	public void i_select_my_referrals_tab() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement myRefferals = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='My Referrals']")));

		myRefferals.click();

	}

	@And("I select Show All Tab")
	public void i_select_show_all_tab() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement showAll = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Show All']")));

		showAll.click();

	}

	@And("I select Show Teams Tab")
	public void i_select_show_teams_tab() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement showTeams = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Show Teams']")));

		showTeams.click();

	}

	@And("I select CIU Glasgow- Motor & Scottish Casualty Tab")
	public void i_select_cui_glasgow_motor_scttish_casualty_tab() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement showTeams = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[text()='CIU Glasgow- Motor & Scottish Casualty']")));

		showTeams.click();

	}

	@And("I select CIU Glasgow - Property Tab")
	public void i_select_cui_glasgow_property_tab() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement showTeams = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='CIU Glasgow - Property']")));

		showTeams.click();

	}

	@And("I select CIU Casualty Tab")
	public void i_select_cui_casualty_tab() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement showTeams = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='CIU Casualty']")));

		showTeams.click();

	}
	@And("I select Fraud Rings Tab")
	public void i_select_fraud_rings_tab() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement showTeams = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Fraud Rings']")));

		showTeams.click();

	}
	@Then("I should verify the sorting of the urgent field in ascending and descending order for team level records")
	public void i_should_verify_the_sorting_of_urgentTeam_level() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[6]")));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		js.executeScript("arguments[0].click();", sortableColumnIcon);

		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);

		i_should_see_the_my_team_records();
	}

	@Then("I should verify the sorting of the reason for urgency field in ascending and descending order for team level records")
	public void i_should_verify_the_sorting_of_reason_for_urgencyTeam_level() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[7]")));
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}

	@Then("I should verify the sorting of the referral status field in ascending and descending order for team level records")
	public void i_should_verify_the_sorting_of_referral_statusTeam_level() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])[8]")));
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}

	@Then("I should verify the sorting of the overdue field in ascending and descending order for team level records")
	public void i_should_verify_the_sorting_of_overdue_Team_level() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement sortableColumnIcon = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//i[@class='p-sortable-column-icon pi pi-fw pi-sort-alt'])9]")));

		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
		js.executeScript("arguments[0].click();", sortableColumnIcon);
		i_should_see_the_my_team_records();
	}
	
}
