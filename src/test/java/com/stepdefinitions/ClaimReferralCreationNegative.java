package com.stepdefinitions;

import java.io.IOException;

import java.time.Duration;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;

public class ClaimReferralCreationNegative {
	private WebDriver driver;
	private WebDriverWait wait;

	@Given("I am on the zodiac login page for negative scenario")
	public void i_am_on_the_zodiac_login_page_negative() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://app-zodiac-test.azurewebsites.net/");
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-username")));
	}

	@And("I enter invalid credentials from Excel")
	public void i_enter_invalid_credentials_from_excel() throws IOException, InvalidFormatException {
		Object[][] loginData = ExcelReader.getExcelData(
				"C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx",
				"Sheet1");
		String username = (String) loginData[1][0];
		String password = (String) loginData[1][1];

		WebElement usernameField = driver.findElement(By.xpath("//input[@id='okta-signin-username']"));
		usernameField.sendKeys(username);

		WebElement passwordField = driver.findElement(By.xpath("//input[@id='okta-signin-password']"));
		passwordField.sendKeys(password);
	}

	@When("I click on the login button for negative scenario")
	public void i_click_on_the_login_button_negative() {
		WebElement loginBtn = driver.findElement(By.xpath("//input[@id='okta-signin-submit']"));
		loginBtn.click();
	}

	@Then("I should see an error message indicating login failure")
	public void i_should_see_an_error_message_indicating_login_failure() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Unable to sign in']")));
		System.out.println("Error Message: " + errorMessage.getText());
	}

	@When("I enter an invalid Reserve amount")
	public void i_enter_an_invalid_reserve_amount() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[23][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();

		String invalidReserveAmount = String.valueOf(TestDat_ClaimReferral[23][12]);
		if (invalidReserveAmount == null || invalidReserveAmount.trim().isEmpty()) {
			System.out.println("Invalid Reserve amount is empty or null");
			throw new RuntimeException("Invalid reserve amount is missing in Excel data!");
		}
		System.out.println("Invalid Reserve amount from Excel: " + invalidReserveAmount);

		// Validate if the reserve amount is numeric or contains invalid characters
		if (!isNumeric(invalidReserveAmount)) {
			System.out.println("Invalid Reserve amount: " + invalidReserveAmount + " (Not a numeric value)");
			return; // Skip the test as the value is not valid
		}

		// Locate the Reserve input field
		WebElement reserveField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reserve-amount")));
		if (reserveField == null) {
			System.out.println("Reserve field is not found");
			throw new RuntimeException("Reserve field is not found!");
		}
		reserveField.clear();
		reserveField.sendKeys(invalidReserveAmount);

//		  String invalidReserveAmount = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[23][12]);
//
//		    WebElement reserveField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reserve-amount")));
//		    reserveField.clear();
//		    reserveField.sendKeys(invalidReserveAmount);
//		    reserveField.sendKeys(Keys.TAB);

//		WebElement reserveField = wait
//				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='reserve']")));
//		String reserveamt = (String) TestDat_ClaimReferral[23][12];
//		reserveField.clear();
//		reserveField.sendKeys(reserveamt);
//		System.out.println("It allows Numeric value only. Excel has: " + reserveamt);

	}

	@Then("I should see a validation error message indicating that the Reserve amount is invalid")
	public void i_should_see_a_validation_error_message_for_invalid_reserve() {
		WebElement errorMessage = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Reserve is required']")));
		System.out.println("Error Message: " + errorMessage.getText());

	}

	private boolean isNumeric(String str) {
		try {
			Double.parseDouble(str); // Try to parse the string as a double
			return true; // If parsing is successful, the string is numeric
		} catch (NumberFormatException e) {
			return false; // If an exception occurs, the string is not numeric
		}
	}

	@Given("I am logged in and on the Create Referral form")
	public void i_am_logged_in_and_on_the_create_referral_form() throws InvalidFormatException, IOException {

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
		WebElement createReferralOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '/create-referral')]")));
		createReferralOption.click();
		System.out.println("Navigated to Create Referral page");
	}

	@And("I leave required email field blank")
	public void i_leave_required_email_field_blank() throws InvalidFormatException, IOException, InterruptedException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[12][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		System.out.println(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
	
		
	}

	public void waitForElementAndFillClaimReference(int i) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");
		String claimNum = (String) TestDat_ClaimReferral[i][0]; // Get the claim number for the current row

		try {

			WebElement claimReferenceInput = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
			claimReferenceInput.clear();
			claimReferenceInput.sendKeys(claimNum);

			claimReferenceInput.sendKeys(Keys.TAB);

			handleInvalidClaimReferencePopup();

			handleClaimExistsPopup();

		} catch (Exception e) {
			System.out.println("Error while filling claim reference for row " + i + ": " + e.getMessage());
		}
	}

	private void handleInvalidClaimReferencePopup() {
		try {

			WebElement invalidClaimRefMessage = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//span[text()='Invalid Claim Reference']")));
			if (invalidClaimRefMessage != null && invalidClaimRefMessage.isDisplayed()) {

				WebElement okButton = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Ok']")));
				okButton.click();
				System.out.println("Invalid claim reference detected, clicked Ok.");
			}
		} catch (Exception e) {

			System.out.println("No Invalid Claim Reference popup detected.");
		}
	}

	private void handleClaimExistsPopup() {
		try {

			WebElement claimExistsPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//p[text()='A Referral with this claim reference already exists. Do you want to create another one or view the existing one?']")));
			if (claimExistsPopup != null && claimExistsPopup.isDisplayed()) {

				WebElement continueButton = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
				continueButton.click();
				System.out.println("Claim number already exists, clicked Continue.");
			}
		} catch (Exception e) {

			System.out.println("No Claim Exists popup detected.");
		}
	}

	@When("I click the Submit button")
	public void i_click_the_submit_button() {
		WebElement submitButton = driver.findElement(By.xpath("//button[text()=' Submit for triage ']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(submitButton).click().perform();

	}

	@Then("I should see validation error messages that Email is required")
	public void i_should_see_validation_error_messages_that_email_is_required() {
		WebElement emailReq = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//small[text()='Referrer email is required']")));
		System.out.println(emailReq.getText());

	}

	@And("I enter invalid NUMERIC data in email field")
	public void i_enter_invalid_numeric_data_in_email_field() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet4")[3][0];
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		System.out.println(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet4")[3][1];
		WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("referrer-email")));
		emailField.clear();
		
		emailField.sendKeys(email);
		emailField.sendKeys(Keys.TAB);

	}

	@Then("I should see a validation error message for the incorrect field")
	public void i_should_see_a_validation_error_message_for_the_incorrect_field()
			throws InvalidFormatException, IOException {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorMessage = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Referrer email is invalid']")));

		System.out.println("Error Message: " + errorMessage.getText());

	}

	@And("I fill the form with a claim number that already exists in the system")
	public void i_fill_the_form_with_a_claim_number_that_already_exists_in_the_system()
			throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");
		String claimNum = (String) TestDat_ClaimReferral[1][0];
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.clear();
		claimReferenceInput.sendKeys(claimNum);
		// claimReferenceInput.sendKeys(Keys.ENTER);
		claimReferenceInput.sendKeys(Keys.TAB);

		handleInvalidClaimReferencePopup();
		System.out.println("Hanled existing Claim Referrence Number");
	}
	
	

	@Then("I should see an error message indicating that the referral already exists")
	public void i_should_see_an_error_message_indicating_that_the_referral_already_exists() {
		WebElement invalidClaimRefMessage = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='A Referral with this claim reference already exists. Do you want to create another one or view the existing one?']")));
		System.out.println(invalidClaimRefMessage.getText());
	}

	@When("I leave the urgency field as empty")
	public void i_leave_the_urgency_field_as_empty() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[2][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		System.out.println(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();

		String email = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[2][1]);
		WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("referrer-email")));
		emailField.clear();
		emailField.sendKeys(email);
		System.out.println(email);

		String isUrgentRef = (String) TestDat_ClaimReferral[2][5];
		String urgentReason = (String) TestDat_ClaimReferral[2][6];
		if ("Yes".equalsIgnoreCase(isUrgentRef.trim())) {
			WebElement urgentDropdown = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='urgent']")));
			System.out.println(urgentDropdown);
			selectDropdownOption("//select[@id='urgent']", excelFilePath, "Sheet4", 2, 5);
			if (urgentReason != null && !urgentReason.isEmpty()) {
				WebElement urgentReasonField = wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='urgency-reason']")));
				urgentReasonField.clear();
				urgentReasonField.sendKeys(urgentReason);
			}
		}
	}

	private void selectDropdownOption(String xpath, String excelFilePath, String sheetName, int i, int columnIndex)
			throws IOException, InvalidFormatException {
		String optionText = (String) ExcelReader.getExcelData(excelFilePath, sheetName)[i][columnIndex];

		if (optionText == null || optionText.trim().isEmpty()) {
			System.out.println("No referrer team provided. Selecting default option.");

			WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
			Select select = new Select(dropdown);

			try {
				select.selectByIndex(0);
			} catch (NoSuchElementException e) {
				System.out.println("Default option not found in the dropdown.");
			}

			return;
		}

		WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		Select select = new Select(dropdown);

		try {
			select.selectByVisibleText(optionText);
			System.out.println("Selected referrer team: " + optionText);
		} catch (NoSuchElementException e) {
			System.out.println("Option with text '" + optionText + "' not found in the dropdown.");
		}
	}

	@Then("I should see an error message indicating that the urgency reason field is mandatory")
	public void i_should_see_an_error_message_indicating_that_the_urgency_reason_field_is_mandatory() {
		WebElement invalidClaimRefMessage = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Urgent reason is required']")));
		System.out.println(invalidClaimRefMessage.getText());
	}

	@When("I enter an invalid email format in fields Email")
	public void i_enter_an_invalid_email_format_in_fields_email() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[4][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		System.out.println(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String email = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[4][1]);
		WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("referrer-email")));
		emailField.clear();
		emailField.sendKeys(email);
		System.out.println(email);
	}

	@Then("I should see a validation error message for the incorrect email format")
	public void i_should_see_a_validation_error_message_for_the_incorrect_email_format() {

		WebElement invalidemail = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Referrer email is invalid']")));
		System.out.println(invalidemail.getText());

	}

	@When("I enter an invalid phone number format in fields like Phone Number")
	public void i_enter_invalid_phone_number_format_in_fields_like() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[5][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		System.out.println(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();

		selectInsuredRadioButton(excelFilePath, 5, 15);
	}

	@Then("I should see a validation error message for the incorrect phone number format")
	public void i_should_see_a_validation_error_message_for_the_incorrect_phone_number_format() {

		WebElement invalidphone = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Invalid phone number']")));
		System.out.println(invalidphone.getText());

	}

	@When("I leave Company Name field empty")
	public void i_leave_company_name_filed_empty() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[7][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		System.out.println(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		selectInsuredRadioButton(excelFilePath, 7, 15);

	}

	private String getExcelCellValue(String excelFilePath, int rowIndex, int columnIndex)
			throws IOException, InvalidFormatException {
		// Fetching the Excel data
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		// Getting the value from the Excel cell
		Object cellValue = excelData[rowIndex][columnIndex];

		// If the value is null, return an empty string
		if (cellValue == null || String.valueOf(cellValue).trim().isEmpty()) {
			return "";
		}

		// Returning the trimmed value
		return String.valueOf(cellValue).trim();
	}

	private void selectRadioButton(WebElement radioButton) {
		if (!radioButton.isSelected()) {
			try {
				radioButton.click();
				System.out.println("Radio button clicked.");
			} catch (Exception e) {
				System.out.println("Normal click failed, attempting JavaScript click.");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", radioButton);
				System.out.println("Radio button clicked using JavaScript.");
			}
		} else {
			System.out.println("Radio button is already selected.");
		}
	}

	private void selectInsuredRadioButton(String excelFilePath, int rowIndex, int columnIndex)
			throws InvalidFormatException, IOException {
		String insuredType = getExcelCellValue(excelFilePath, rowIndex, columnIndex);

		WebElement commercialInsuredRadio = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Commercial Insured']")));
		WebElement personalInsuredRadio = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Personal Insured']")));

		if ("Commercial Insured".equalsIgnoreCase(insuredType)) {
			selectRadioButton(commercialInsuredRadio);
			fillCommercialFields(excelFilePath, rowIndex);
		} else if ("Personal Insured".equalsIgnoreCase(insuredType)) {
			selectRadioButton(personalInsuredRadio);
			fillPersonalFields(excelFilePath, rowIndex);
		}
	}

	private void fillCommercialFields(String excelFilePath, int rowIndex) throws IOException, InvalidFormatException {
		WebElement addNewCom = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//button[@class='btn btn-outline-primary add-popup-btn'])[1]")));
		addNewCom.click();

		String companyName = getExcelCellValue(excelFilePath, rowIndex, 16);
		WebElement companyNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercial-company-name']")));
		companyNameField.sendKeys(Keys.TAB);
		companyNameField.sendKeys(companyName);
	}

	private void fillPersonalFields(String excelFilePath, int rowIndex) throws IOException, InvalidFormatException {
		selectDropdownOption("//select[@id='insured-title']", excelFilePath, rowIndex, 66);
		String firstName = getExcelCellValue(excelFilePath, rowIndex, 67);
		WebElement firstNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredfirst-name']")));

		// Check if the first name is empty
		if (firstName.trim().isEmpty()) {

			firstNameField.sendKeys(Keys.TAB);
			firstNameField.sendKeys(firstName);
			System.out.println("First name is empty, leaving the field blank.");
			// Do nothing or clear the field (if needed)
			firstNameField.clear(); // Optional: Clears any pre-filled value if the field is pre-populated
		} else {
			firstNameField.sendKeys(firstName);
			System.out.println("First name is not empty. Filled " + firstName);

		}

		String lastName = getExcelCellValue(excelFilePath, rowIndex, 68);
		WebElement lastNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredlast-name']")));
		// Check if the first name is empty
		if (lastName.trim().isEmpty()) {

			lastNameField.sendKeys(Keys.TAB);
			lastNameField.sendKeys(lastName);
			System.out.println("Last name is empty, leaving the field blank.");
			// Do nothing or clear the field (if needed)
			lastNameField.clear(); // Optional: Clears any pre-filled value if the field is pre-populated
		} else {
			lastNameField.sendKeys(lastName);
			System.out.println("Last name is not empty. Filled " + lastName);

		}
		String phoneNumber = getExcelCellValue(excelFilePath, rowIndex, 71);
		WebElement phoneNumberField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@id='phone'])[1]")));
		phoneNumberField.sendKeys(phoneNumber);

		String nationalInsuranceNumber = getExcelCellValue(excelFilePath, rowIndex, 73);
		WebElement nationalInsuranceNumberField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insurednationalinsuranceNum']")));
		nationalInsuranceNumberField.sendKeys(nationalInsuranceNumber);

	}

	private void selectDropdownOption(String xpath, String excelFilePath, int rowIndex, int columnIndex)
			throws IOException, InvalidFormatException {
		String dropdownValue = getExcelCellValue(excelFilePath, rowIndex, columnIndex);
		WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		new Select(dropdown).selectByVisibleText(dropdownValue);
	}

	@And("I try to click the Add button")
	public void i_try_to_click_the_add_button() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement addButton = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='Add'])[2]")));
		addButton.click();
		try {
			WebElement addButtonElement = wait.until(ExpectedConditions.elementToBeClickable(addButton));
			addButtonElement.click();
		} catch (Exception e) {
			System.out.println("Add button is disabled, cannot click.");
		}
	}

	@Then("I should see validation error messages for the missing company name field")
	public void i_should_see_validation_error_messages_for_the_missing_company_name_field() {

		WebElement comName = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Company Name is required']")));
		System.out.println(comName.getText());

	}

	@When("I enter an invalid post code format in the \"Post Code\" field")
	public void i_enter_invalid_post_code_format_in_post_code_field() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[8][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		System.out.println(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		selectInsuredRadioButton(excelFilePath, 8, 15);
		String postCode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[8][27]);
		WebElement postCodeField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='comercial-post-code']")));
		postCodeField.sendKeys(postCode);

	}

	@Then("I should see a validation error message for the invalid postal code format")
	public void i_should_see_validation_error_message_for_invalid_postal_code() throws IOException {

		WebElement postCode = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Post code is invalid']")));
		System.out.println(postCode.getText());

	}

	@When("I enter an invalid NIN number")
	public void i_enter_an_invalid_nin_number() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[9][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		System.out.println(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();

		selectInsuredRadioButton(excelFilePath, 9, 15);

	}

	@Then("I should see a validation error message for the invalid NIN number")
	public void i_should_see_validation_error_message_for_invalid_nin_number() throws IOException {

		WebElement ninNum = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//small[text()='National Insurance Number not valid']")));
		System.out.println(ninNum.getText());

	}

	@And("I try to click the Add button for personal")
	public void i_try_to_click_the_add_button_for_personal() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement addInsuredParty = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add Insured Party']")));
		addInsuredParty.click();

		try {
			WebElement addInsuredButtonElement = wait.until(ExpectedConditions.elementToBeClickable(addInsuredParty));
			addInsuredButtonElement.click();
		} catch (Exception e) {
			System.out.println("Add insured party button is disabled, cannot click.");
		}
	}

	@When("I leave mandatory fields empty in the New Personal Party section such as First Name")
	public void i_leave_mandatory_fields_empty_in_new_personal_party_section()
			throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[10][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		System.out.println(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();

		selectInsuredRadioButton(excelFilePath, 10, 15);
	}

	@Then("I should see a validation error message for the missing fields in the New Personal Party section")
	public void i_should_see_validation_error_for_missing_fields_in_new_party_section() {
		WebElement firstNameElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='First name is required']")));
		System.out.println(firstNameElement.getText());

	}

	@When("I leave mandatory fields empty in the New Personal Party section such as Last Name")
	public void i_leave_mandatory_fields_empty_in_new_personal_party_section_last_name()
			throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[11][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		System.out.println(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();

		selectInsuredRadioButton(excelFilePath, 11, 15);
	}

	@Then("I should see a validation error message for the missing fields in the New Personal Party section Last Name")
	public void i_should_see_validation_error_for_missing_fields_in_new_party_section_last_name() {
		WebElement LastNameElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Last name is required']")));
		System.out.println(LastNameElement.getText());

	}

	@When("I enter an invalid date format in the Date of Loss field")
	public void i_enter_invalid_date_format_in_date_of_loss_field() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet4");

		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[6][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		System.out.println(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();

		String dateofLoss = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[6][8]);

		WebElement dateOfLossField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='loss-date']")));
		dateOfLossField.sendKeys(dateofLoss);
		System.out.println("Print DateOfLoss Value " + dateofLoss);

	}

	@Then("I should see a validation error message for the invalid date format")
	public void i_should_see_validation_error_message_for_invalid_date_format()
			throws IOException, InvalidFormatException {
		WebElement dateOfLossElement = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Date of Loss is required']")));
		System.out.println(dateOfLossElement.getText());
	}

	@And("I leave required Referrer Team field blank")
	public void i_leave_required_referrer_team_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[13][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String referrerTeam = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[13][2]);
		WebElement referrerField = wait.until(ExpectedConditions.elementToBeClickable(By.id("referrer-team")));

		if (referrerTeam == null || referrerTeam.trim().isEmpty()) {
			System.out.println("No referrer team provided. Selecting default option.");
			WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("referrer-team")));
			Select select = new Select(dropdown);
			try {
				select.selectByIndex(0);
			} catch (NoSuchElementException e) {
				System.out.println("Default option not found in the dropdown.");
			}
		} else {
			Select dropdown = new Select(referrerField);
			try {
				dropdown.selectByVisibleText(referrerTeam);
				System.out.println("Selected referrer team: " + referrerTeam);
			} catch (NoSuchElementException e) {
				System.out.println("Option with text '" + referrerTeam + "' not found in the dropdown.");
			}
		}

	}

	@Then("I should see validation error messages that Referrer Team is required")
	public void i_should_see_validation_error_messages_that_referrer_team_is_required() {
		WebElement referrerTeamReq = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Referrer team is required']")));
		System.out.println(referrerTeamReq.getText());
	}

	@And("I leave required Referrer Location field blank")
	public void i_leave_required_referrer_location_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[14][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();

		String referrerLocation = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[14][3]);
		WebElement referrerLocationField = wait
				.until(ExpectedConditions.elementToBeClickable(By.id("referrer-location")));

		if (referrerLocation == null || referrerLocation.trim().isEmpty()) {
			System.out.println("No referrer location provided. Leaving it blank.");
			referrerLocationField.clear();
		} else {
			try {
				referrerLocationField.clear();
				referrerLocationField.sendKeys(referrerLocation);
				System.out.println("Entered referrer location: " + referrerLocation);
			} catch (Exception e) {
				System.out.println("Failed to enter referrer location. Error: " + e.getMessage());
			}
		}

	}

	@Then("I should see validation error messages that Referrer Location is required")
	public void i_should_see_validation_error_messages_that_referrer_location_is_required() {
		WebElement referrerLocationReq = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//small[text()='Referrer Location is required']")));
		System.out.println(referrerLocationReq.getText());
	}

	@And("I leave required Referral Type field blank")
	public void i_leave_required_referral_type_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[15][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String referralType = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[15][4]);
		WebElement referralTypeField = wait.until(ExpectedConditions.elementToBeClickable(By.id("referral-type")));

		if (referralType == null || referralType.trim().isEmpty()) {
			System.out.println("No referral type provided. Leaving it blank.");
			referralTypeField.clear();
		} else {
			try {
				referralTypeField.clear();
				referralTypeField.sendKeys(referralType);
				System.out.println("Entered referral type: " + referralType);
			} catch (Exception e) {
				System.out.println("Failed to enter referral type. Error: " + e.getMessage());
			}
		}

	}

	@Then("I should see validation error messages that Referral Type is required")
	public void i_should_see_validation_error_messages_that_referral_type_is_required() {
		WebElement referralTypeReq = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Referral type is required']")));
		System.out.println(referralTypeReq.getText());
	}

	@And("I leave required Urgent field blank")
	public void i_leave_required_urgent_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[16][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String isUrgentRef = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[16][5]);
		 String urgencyReason = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[16][6]);
		if ("Yes".equalsIgnoreCase(isUrgentRef.trim())) {
			WebElement urgentDropdown = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='urgent']")));
			System.out.println(urgentDropdown);
			selectDropdownOption("//select[@id='urgent']", excelFilePath, "Sheet4", 16, 5);
			if (urgencyReason != null && !urgencyReason.isEmpty()) {
				WebElement urgentReasonField = wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='urgency-reason']")));
				urgentReasonField.clear();
				urgentReasonField.sendKeys(urgencyReason);
				System.out.println("Urgency reason entered: " + urgencyReason);
			}
		} else {
			System.out.println("Urgency is not selected as 'Yes', skipping urgency reason input.");
		}

	}

	@Then("I should see that Urgent only accepts Yes or No")
	public void i_should_see_that_urgent_only_accepts_yes_or_no() {
		WebElement urgentField = wait.until(ExpectedConditions.elementToBeClickable(By.id("urgent")));
		String urgentValue = urgentField.getAttribute("value");

		if (!urgentValue.equalsIgnoreCase("Yes") && !urgentValue.equalsIgnoreCase("No")) {
			System.out.println("Validation successful: Only 'Yes' or 'No' are accepted.");
			System.out.println(urgentValue);
		} else {
			System.out.println("Unexpected value accepted: " + urgentValue);
		}
	}

	@And("I leave required Urgent Reason field blank")
	public void i_leave_required_urgent_reason_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[17][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String isUrgentRef = ExcelReader.getExcelData(excelFilePath, "Sheet4")[17][5] != null
				? String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[17][5])
				: "";
		String urgentReason = ExcelReader.getExcelData(excelFilePath, "Sheet4")[17][6] != null
				? String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[17][6])
				: "";

		if ("Yes".equalsIgnoreCase(isUrgentRef.trim())) {
			try {
				WebElement urgentDropdown = wait
						.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='urgent']")));
				System.out.println(urgentDropdown);
				selectDropdownOption("//select[@id='urgent']", excelFilePath, "Sheet4", 17, 5);

				if (urgentReason != null && !urgentReason.trim().isEmpty()) {
					WebElement urgentReasonField = wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='urgency-reason']")));
					urgentReasonField.clear();
					urgentReasonField.sendKeys(urgentReason);
					System.out.println("Urgency reason entered: " + urgentReason);
				} else {
					WebElement urgentReasonField = wait.until(
							ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='urgency-reason']")));
					urgentReasonField.clear();
					System.out.println("Urgency reason is left blank.");
				}
			} catch (Exception e) {
				System.out.println("Error during urgency handling: " + e.getMessage());
			}
		} else if ("No".equalsIgnoreCase(isUrgentRef.trim())) {
			System.out.println("Urgency is not required.");
		} else {
			System.out.println("Invalid value in 'Urgent' field.");
		}
	}

	@Then("I should see validation error messages that Urgent Reason is required")
	public void i_should_see_validation_error_messages_that_urgent_reason_is_required() {
		WebElement urgentReasonReq = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Urgent reason is required']")));
		System.out.println(urgentReasonReq.getText());
	}

	@And("I leave required Refer To field blank")
	public void i_leave_required_refer_to_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[18][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String referTo = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[18][7]);
		WebElement referToField = wait.until(ExpectedConditions.elementToBeClickable(By.id("refer-to")));

		if (referTo == null || referTo.trim().isEmpty()) {
			System.out.println("No 'Refer to' value provided. Leaving the field blank.");
		} else {
			try {
				referToField.clear();
				referToField.sendKeys(referTo);
				System.out.println("Entered refer to: " + referTo);
			} catch (Exception e) {
				System.out.println("Failed to enter 'Refer to' value. Error: " + e.getMessage());
			}
		}

	}

	@Then("I should see validation error messages that Refer To is required")
	public void i_should_see_validation_error_messages_that_refer_to_is_required() {
		WebElement referToReq = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Refer to is required']")));
		System.out.println(referToReq.getText());
	}

	@And("I leave required Date of Loss field blank")
	public void i_leave_required_date_of_loss_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[19][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String dateOfLoss = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[19][8]);
		System.out.println("Retrieved Date of Loss: '" + dateOfLoss + "'");

		if (dateOfLoss == null || dateOfLoss.trim().isEmpty() || dateOfLoss.equalsIgnoreCase("null")) {
			System.out.println("Date of Loss field is empty. Submitting the form to show the validation message.");
			WebElement dateOfLossField = wait.until(ExpectedConditions.elementToBeClickable(By.id("loss-date")));
			dateOfLossField.clear();

		} else {
			WebElement dateOfLossField = wait.until(ExpectedConditions.elementToBeClickable(By.id("loss-date")));
			dateOfLossField.clear();
			dateOfLossField.sendKeys(dateOfLoss);
			dateOfLossField.sendKeys(Keys.RETURN);
			System.out.println("Entered Date of Loss: " + dateOfLoss);

		}
	}

	@Then("I should see validation error messages that Date of Loss is required")
	public void i_should_see_validation_error_messages_that_date_of_loss_is_required() {
		WebElement dateOfLossReq = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Date of Loss is required']")));
		System.out.println(dateOfLossReq.getText());
	}

	@And("I leave required Line of Business field blank")
	public void i_leave_required_line_of_business_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[20][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String lineOfBusiness = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[20][9]);

		WebElement lineOfBusinessField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='business-area']")));

		System.out.println("Retrieved Line of Business: '" + lineOfBusiness + "'");

		if (lineOfBusiness == null || lineOfBusiness.trim().isEmpty()) {
			System.out.println("No 'Line of Business' value provided. Leaving the field blank.");
		} else {
			try {
				wait.until(ExpectedConditions.elementToBeClickable(lineOfBusinessField));
				lineOfBusinessField.clear();
				lineOfBusinessField.sendKeys(lineOfBusiness);
				System.out.println("Entered Line of Business: " + lineOfBusiness);
			} catch (Exception e) {
				System.out.println("Failed to enter 'Line of Business' value. Error: " + e.getMessage());
				e.printStackTrace();
			}
		}

	}

	@Then("I should see validation error messages that Line of Business is required")
	public void i_should_see_validation_error_messages_that_line_of_business_is_required() {
		WebElement lineOfBusinessReq = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//small[text()='Line of Business is required']")));
		System.out.println(lineOfBusinessReq.getText());
	}

	@And("I leave required Market Facing Unit field blank")
	public void i_leave_required_market_facing_unit_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[21][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String marketFacingUnit = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[21][10]);
		WebElement marketFacingUnitField = wait
				.until(ExpectedConditions.elementToBeClickable(By.id("market-facing-unit")));

		if (marketFacingUnit == null || marketFacingUnit.trim().isEmpty()) {
			System.out.println("No 'Market Facing Unit' value provided. Leaving the field blank.");
		} else {
			try {
				marketFacingUnitField.clear();
				marketFacingUnitField.sendKeys(marketFacingUnit);
				System.out.println("Entered market facing unit: " + marketFacingUnit);
			} catch (Exception e) {
				System.out.println("Failed to enter 'Market Facing Unit' value. Error: " + e.getMessage());
			}
		}

	}

	@Then("I should see validation error messages that Market Facing Unit is required")
	public void i_should_see_validation_error_messages_that_market_facing_unit_is_required() {
		WebElement marketFacingUnitReq = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//small[text()='Market Facing Unit is required']")));
		System.out.println(marketFacingUnitReq.getText());
	}

	@And("I leave required Claim Type field blank")
	public void i_leave_required_claim_type_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[22][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String claimType = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[22][11]);
		WebElement claimTypeField = wait.until(ExpectedConditions.elementToBeClickable(By.id("claim-type")));

		if (claimType == null || claimType.trim().isEmpty()) {
			System.out.println("No 'Claim Type' value provided. Leaving the field blank.");
		} else {
			try {
				claimTypeField.clear();
				claimTypeField.sendKeys(claimType);
				System.out.println("Entered claim type: " + claimType);
			} catch (Exception e) {
				System.out.println("Failed to enter 'Claim Type' value. Error: " + e.getMessage());
			}
		}

	}

	@Then("I should see validation error messages that Claim Type is required")
	public void i_should_see_validation_error_messages_that_claim_type_is_required() {
		WebElement claimTypeReq = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Claim type is required']")));
		System.out.println(claimTypeReq.getText());
	}

	@And("I leave required Reserve field blank")
	public void i_leave_required_reserve_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[23][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String reserve = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[23][12]);
		WebElement reserveField = wait.until(ExpectedConditions.elementToBeClickable(By.id("reserve")));

		if (reserve == null || reserve.trim().isEmpty()) {
			System.out.println("No 'Reserve' value provided. Leaving the field blank.");
		} else {
			try {
				reserveField.clear();
				reserveField.sendKeys(reserve);
				System.out.println("Entered reserve: " + reserve);
			} catch (Exception e) {
				System.out.println("Failed to enter 'Reserve' value. Error: " + e.getMessage());
			}
		}

	}

	@Then("I should see validation error messages that Reserve is required")
	public void i_should_see_validation_error_messages_that_reserve_is_required() {
		WebElement reserveReq = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Reserve is required']")));
		System.out.println(reserveReq.getText());
	}

	@And("I leave required Concern Type field blank")
	public void i_leave_required_concern_type_field_blank() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String claimNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[24][0]);
		WebElement claimReferenceInput = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
		claimReferenceInput.sendKeys(claimNum);
		claimReferenceInput.sendKeys(Keys.TAB);
		handleInvalidClaimReferencePopup();
		handleClaimExistsPopup();
		String concernType = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet4")[24][13]);
		WebElement concernTypeField = wait.until(ExpectedConditions.elementToBeClickable(By.id("concern-type")));

		if (concernType == null || concernType.trim().isEmpty()) {
			System.out.println("No 'Concern Type' value provided. Leaving the field blank.");
		} else {
			try {
				concernTypeField.clear();
				concernTypeField.sendKeys(concernType);
				System.out.println("Entered concern type: " + concernType);
			} catch (Exception e) {
				System.out.println("Failed to enter 'Concern Type' value. Error: " + e.getMessage());
			}
		}

	}

	@Then("I should see validation error messages that Concern Type is required")
	public void i_should_see_validation_error_messages_that_concern_type_is_required() {
		WebElement concernTypeReq = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Concern type is required']")));
		System.out.println(concernTypeReq.getText());
	}

	@And("I quit the browser")
	public void i_quit_the_browser() {
		if (driver != null) {
			driver.quit();
		}
	}
}