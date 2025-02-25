package com.stepdefinitions;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.ParseException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClaimReferralCreation {

	private WebDriver driver;
	private WebDriverWait wait;

	@Given("I am on the Zodiac login page")
	public void i_am_on_the_zodiac_login_page() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://app-zodiac-test.azurewebsites.net/");
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-username")));
		System.out.println("I am on the Zodiac login page");
	}

	@And("I enter a valid username and password from Excel")
	public void i_enter_a_valid_username_and_password_from_excel() throws IOException, InvalidFormatException {

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
	}

	@When("I click on the login button")
	public void i_click_on_the_login_button() {
		WebElement loginBtn = driver.findElement(By.xpath("//input[@id='okta-signin-submit']"));
		loginBtn.click();
		System.out.println("Clicked the login button");
		System.out.println("Successfully redirected to the Dashboard");
	}

	@Then("I should be redirected to the Dashboard")
	public void i_should_be_redirected_to_the_dashboard() {
		System.out.println("Successfully redirected to the Dashboard");
	}

	@Given("I click the Claim Referrals option")
	public void i_click_the_claim_referrals_option() {
		WebElement claimReferrals = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Claim Referrals']")));
		claimReferrals.click();
		System.out.println("Claim Referrals option clicked");
	}

	@And("I select Create Referral option")
	public void i_select_create_referral_option() {
		WebElement createReferralOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '/create-referral')]")));
		createReferralOption.click();
		System.out.println("Navigated to Create Referral page");
	}

	@Then("I fill the Create Referral form with test data from Excel")
	public void i_fill_the_create_referral_form_with_test_data_from_excel()
			throws IOException, InvalidFormatException, ParseException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet2");
		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		// Ensure there's data in the Excel file
		if (TestDat_ClaimReferral.length == 0) {
			System.out.println("Excel data is empty. No rows to process.");
			return;
		}
		for (int i = 0; i < TestDat_ClaimReferral.length; i++) {
			System.out.println("Processing row: " + (i + 1));
			try {
				waitForElementAndFillClaimReference(i);
				fillFormFields(i, TestDat_ClaimReferral);
				clickSaveDraft();
				waitForManageRefPageAndClickCreateReferralShort();
				System.out.println("Processed row " + (i + 1) + " and reopened the form.");
			} catch (Exception e) {
				System.out.println("Error processing row " + (i + 1) + ": " + e.getMessage());
			}
		}
	}

	public void waitForManageRefPageAndClickCreateReferralShort() {
		try {
			// Wait for the ManageRefPage element to be visible (or clickable, depending on
			// the case)
			WebElement manageRefPage = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[text()='Manage Referrals']")));

			// Log to confirm that the page is now visible
			System.out.println("ManageRefPage is loaded and visible.");

			// Check if the element is not null and is displayed
			if (manageRefPage != null && manageRefPage.isDisplayed()) {
				// Proceed to click Create Referral Short
				clickCreateReferralShort();
			} else {
				// Handle the case where ManageRefPage is not displayed as expected
				throw new Exception("ManageRefPage did not display properly.");
			}
		} catch (TimeoutException e) {
			System.out.println("Timeout while waiting for ManageRefPage to become visible: " + e.getMessage());
			throw new RuntimeException("ManageRefPage did not load within the timeout period.");
		} catch (Exception e) {
			System.out.println("An error occurred while waiting for ManageRefPage: " + e.getMessage());
			// Additional exception handling if needed
		}
	}

	private void fillFormFields(int i, Object[][] TestDat_ClaimReferral) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String email = (String) TestDat_ClaimReferral[i][1];
		WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("referrer-email")));
		emailField.clear();
		emailField.sendKeys(email);

		selectDropdownOption("//select[@id='referrer-team']", excelFilePath, "Sheet2", i, 2);
		selectDropdownOption("//select[@id='referrer-location']", excelFilePath, "Sheet2", i, 3);
		selectDropdownOption("//select[@id='referral-type']", excelFilePath, "Sheet2", i, 4);

		String isUrgentRef = (String) TestDat_ClaimReferral[i][5];
		String urgentReason = (String) TestDat_ClaimReferral[i][6];
		if ("Yes".equalsIgnoreCase(isUrgentRef.trim())) {
			WebElement urgentDropdown = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='urgent']")));
			selectDropdownOption("//select[@id='urgent']", excelFilePath, "Sheet2", i, 5);
			if (urgentReason != null && !urgentReason.isEmpty()) {
				WebElement urgentReasonField = wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='urgency-reason']")));
				urgentReasonField.clear();
				urgentReasonField.sendKeys(urgentReason);
				System.out.println(urgentDropdown);
			}
		}

		selectDropdownOption("//select[@id='refer-to']", excelFilePath, "Sheet2", i, 7);
		WebElement dateOfLossField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='loss-date']")));
		fetchAndProcessDate(excelFilePath, i, 8, dateOfLossField);

		selectDropdownOption("//select[@id='business-area']", excelFilePath, "Sheet2", i, 9);
		selectDropdownOption("//select[@id='market-facing-unit']", excelFilePath, "Sheet2", i, 10);
		selectDropdownOption("//select[@id='claim-type']", excelFilePath, "Sheet2", i, 11);

		WebElement reserveField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='reserve']")));
		String reserveamt = (String) TestDat_ClaimReferral[i][12];
		reserveField.clear();
		reserveField.sendKeys(reserveamt);

		selectDropdownOption("//select[@id='concern-type']", excelFilePath, "Sheet2", i, 13);

		WebElement inciLocaField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='incident-location']")));
		String inciLoca = (String) TestDat_ClaimReferral[i][14];
		inciLocaField.clear();
		inciLocaField.sendKeys(inciLoca);
		inciLocaField.sendKeys(Keys.ENTER);

		selectInsuredRadioButtonFromExcel(excelFilePath, i);
		addNewParty(i);
		fillRemainFileds(i);

		System.out.println(email);

	}

	public void clickCreateReferralShort() {
		try {
			// Wait for the "Create Referral Short" dropdown button to be clickable
			WebElement createReferralShort = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class='fas fa-caret-square-down']")));
			createReferralShort.click();
			System.out.println("Create Referral Short clicked.");

			// Wait for the "Create Claims Referral" option to be clickable and select it
			WebElement dropdownItem = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Create Claims Referral']")));
			dropdownItem.click();
			System.out.println("Create Claims Referral option selected.");
		} catch (TimeoutException e) {
			System.out.println("Timeout while waiting for elements in Create Referral Short: " + e.getMessage());
			// Handle timeout if necessary (retry, log error, etc.)
			throw new RuntimeException("Error while waiting for the 'Create Referral Short' dropdown elements.");
		} catch (Exception e) {
			System.out.println("An error occurred while clicking the referral shortcut: " + e.getMessage());
			// Handle any other exceptions here
			throw new RuntimeException("Unexpected error while interacting with Create Referral Short.");
		}
	}

	public void waitForElementAndFillClaimReference(int i) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet2");
		String claimNum = (String) TestDat_ClaimReferral[i][0]; // Get the claim number for the current row

		try {
			// Fill the claim reference input field with the claim number
			WebElement claimReferenceInput = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
			claimReferenceInput.clear();
			claimReferenceInput.sendKeys(claimNum);
			// claimReferenceInput.sendKeys(Keys.ENTER);
			claimReferenceInput.sendKeys(Keys.TAB);

			// Handle "Invalid Claim Reference" popup if it appears
			handleInvalidClaimReferencePopup();

			// Handle "Claim number already exists" popup if it appears
			handleClaimExistsPopup();

		} catch (Exception e) {
			System.out.println("Error while filling claim reference for row " + i + ": " + e.getMessage());
		}
	}

	private void handleInvalidClaimReferencePopup() {
		try {
			// this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			WebElement invalidClaimRefMessage = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//span[text()='Invalid Claim Reference']")));
			if (invalidClaimRefMessage != null && invalidClaimRefMessage.isDisplayed()) {
				// Click "Ok" to dismiss the invalid claim reference message
				WebElement okButton = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Ok']")));
				okButton.click();
				System.out.println("Invalid claim reference detected, clicked Ok.");
			}
		} catch (Exception e) {
			// Handle exception if the popup does not appear
			System.out.println("No Invalid Claim Reference popup detected.");
		}
	}

	private void handleClaimExistsPopup() {
		try {
			// Wait for the 'Claim number already exists' popup to be visible
			WebElement claimExistsPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//p[text()='A Referral with this claim reference already exists. Do you want to create another one or view the existing one?']")));
			if (claimExistsPopup != null && claimExistsPopup.isDisplayed()) {
				// Click "Continue" to proceed
				WebElement continueButton = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Continue']")));
				continueButton.click();
				System.out.println("Claim number already exists, clicked Continue.");
			}
		} catch (Exception e) {
			// Handle exception if the popup does not appear
			System.out.println("No Claim Exists popup detected.");
		}
	}

	private void fillRemainFileds(int i) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet2");

		String circumtance = (String) TestDat_ClaimReferral[i][123];
		WebElement circumtanceField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='claim-circs']")));
		circumtanceField.clear();
		circumtanceField.sendKeys(circumtance);

		String concern = (String) TestDat_ClaimReferral[i][124];
		WebElement concernField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='concerns']")));
		concernField.clear();
		concernField.sendKeys(concern);
		System.out.println("Circumstance: " + circumtance);
		System.out.println("Concerns: " + concern);
	}

	public void processAndFillDateField(String dateInput, WebElement dateField) {
		try {
			if (isExcelSerialDate(dateInput)) {
				double serialDate = Double.parseDouble(dateInput);
				long millis = (long) ((serialDate - 25569) * 86400 * 1000);
				Date parsedDate = new Date(millis);
				String formattedDate = formatDate(parsedDate);
				fillDateField(dateField, formattedDate);
			} else {
				Date parsedDate = parseDateFromString(dateInput);
				if (parsedDate != null) {
					String formattedDate = formatDate(parsedDate);
					fillDateField(dateField, formattedDate);
				} else {
					System.err.println("Invalid Date Format: " + dateInput);
				}
			}
		} catch (Exception e) {
			System.err.println("Error processing date: " + dateInput);
			e.printStackTrace();
		}
	}

	private boolean isExcelSerialDate(String dateInput) {
		try {
			Double.parseDouble(dateInput);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private Date parseDateFromString(String dateInput) {
		String[] dateFormats = { "dd-MM-yyyy", "MM/dd/yyyy", "yyyy-MM-dd", "MM-dd-yyyy" };
		for (String format : dateFormats) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				dateFormat.setLenient(false);
				return dateFormat.parse(dateInput);
			} catch (Exception e) {
			}
		}
		return null;
	}

	private String formatDate(Date parsedDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		return dateFormat.format(parsedDate);
	}

	private void fillDateField(WebElement dateField, String formattedDate) {
		dateField.clear();
		dateField.sendKeys(formattedDate);
	}

	public void fetchAndProcessDate(String excelFilePath, int i, int columnIndex, WebElement dateField) {
		try {
			String dateInput = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][columnIndex];
			dateInput = dateInput != null ? dateInput.trim() : "";
			System.out.println("Fetched Date from Excel: " + dateInput);
			processAndFillDateField(dateInput, dateField);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectInsuredRadioButtonFromExcel(String excelFilePath, int i)
			throws InvalidFormatException, IOException {
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet2");

		if (i < excelData.length && excelData[i].length > 15) {
			String insuredType = (String) excelData[i][15];
			insuredType = insuredType != null ? insuredType.trim() : "";

			System.out.println("Processing i: " + i + " with insuredType: '" + insuredType + "'");

			WebElement commercialInsuredRadio = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Commercial Insured']")));

			WebElement personalInsuredRadio = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Personal Insured']")));

			if ("Commercial Insured".equalsIgnoreCase(insuredType)) {
				System.out.println("Selecting Commercial Insured");
				selectRadioButton(commercialInsuredRadio);
				fillCommercialFields(excelFilePath, i);
			} else if ("Personal Insured".equalsIgnoreCase(insuredType)) {
				System.out.println("Selecting Personal Insured");
				selectRadioButton(personalInsuredRadio);
				fillPersonalFields(excelFilePath, i);
			} else {
				System.out.println("Unexpected insuredType: " + insuredType);
			}
		} else {
			System.out.println("Invalid i or insufficient columns in Excel. i: " + i);
		}
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

	public void fillCommercialFields(String excelFilePath, int i) throws IOException, InvalidFormatException {
		String companyName = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][16];

		if (companyName == null || companyName.trim().isEmpty()) {
			return;
		}

		WebElement companyInput = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@class, 'p-autocomplete-input')]")));
		companyInput.clear();
		companyInput.sendKeys(companyName);

		List<WebElement> companyOptions = wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//ul[contains(@class, 'p-autocomplete-items')]/li")));

		if (!companyOptions.isEmpty()) {
			boolean foundCompany = false;

			for (WebElement option : companyOptions) {
				if (option.getText().equalsIgnoreCase(companyName)) {
					foundCompany = true;
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
					wait.until(ExpectedConditions.elementToBeClickable(option));
					option.click();
					System.out.println(option);

					fillCommercialDetails(excelFilePath, i);
					break;
				}
			}

			if (!foundCompany) {
				fillAndReviewFieldsFromExcel(excelFilePath, i);
			}
		} else {
			fillAndReviewFieldsFromExcel(excelFilePath, i);
		}
	}

	private void fillCommercialDetails(String excelFilePath, int i) throws IOException, InvalidFormatException {
		WebElement companyNumberField = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-company-number']")));
		WebElement vatNumberField = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-vat-number']")));
		WebElement emailField = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-company-email']")));
		WebElement phoneNumberField = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-telephone-number']")));
//        WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
		WebElement houseNumFiled = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-house-number']")));
		WebElement addressLine1Field = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-address-line-1']")));
		WebElement addressLine2Field = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-address-line-2']")));
		WebElement cityField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-city']")));
		WebElement countyField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-county']")));
		WebElement postcodeField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='comercial-insured-post-code']")));
		WebElement vrnField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-VRN']")));

		String companyNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][17];
		String vatNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][18];
		String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][19];
		String phoneNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][20];
		// String postalCode = (String) ExcelReader.getExcelData(excelFilePath,
		// "Sheet2")[i][21];
		String houseNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][22];
		String addressLine1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][23];
		String addressLine2 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][24];
		String city = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][25];
		String county = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][26];
		String postCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][27];
		String vrn = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][29];

		companyNumberField.clear();
		companyNumberField.sendKeys(companyNumber);
		vatNumberField.clear();
		vatNumberField.sendKeys(vatNumber);
		emailField.clear();
		emailField.sendKeys(email);
		phoneNumberField.clear();
		phoneNumberField.sendKeys(phoneNumber);
		// postalCodeField.clear();
		// postalCodeField.sendKeys(postalCode);
		// postalCodeField.sendKeys(Keys.TAB);
		houseNumFiled.clear();
		houseNumFiled.sendKeys(houseNum);
		addressLine1Field.clear();
		addressLine1Field.sendKeys(addressLine1);

		addressLine2Field.clear();
		addressLine2Field.sendKeys(addressLine2);

		cityField.clear();
		cityField.sendKeys(city);

		countyField.clear();
		countyField.sendKeys(county);

		postcodeField.clear();
		postcodeField.sendKeys(postCode);
		selectDropdownOption("//select[@id='commercial-insured-country']", excelFilePath, "Sheet2", i, 28);
		vrnField.clear();
		vrnField.sendKeys(vrn);

		handleComplicitPartyCheckboxFromExcel(excelFilePath, i, 30, wait);

		WebElement addInsuredParty = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add Insured Party']")));
		addInsuredParty.click();

		System.out.println("Company Number: " + companyNumber);
		System.out.println("VAT Number: " + vatNumber);
		System.out.println("Email: " + email);
		System.out.println("Phone Number: " + phoneNumber);
		// System.out.println("Postal Code: " + postalCode);
		System.out.println("House Number: " + houseNum);
		System.out.println("Address Line 1: " + addressLine1);
		System.out.println("Address Line 2: " + addressLine2);
		System.out.println("City: " + city);
		System.out.println("County: " + county);
		System.out.println("Post Code: " + postCode);
		System.out.println("Vrn: " + vrn);
	}

	private void fillAndReviewFieldsFromExcel(String excelFilePath, int i) throws IOException, InvalidFormatException {
		WebElement addNewCom = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//button[@class='btn btn-outline-primary add-popup-btn'])[1]")));
		addNewCom.click();

		String companyName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][16]);
		WebElement companyNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercial-company-name']")));
		companyNameField.sendKeys(companyName);

		String companyNumber = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][17]);
		WebElement companyNumberField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-company-number']")));
		companyNumberField.sendKeys(companyNumber);

		String vatNumber = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][18]);
		WebElement vatNumberField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-vat-number']")));
		vatNumberField.sendKeys(vatNumber);

		String email = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][19]);
		WebElement emailField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-company-email']")));
		emailField.sendKeys(email);

		String phoneNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][20];
		WebElement phoneNumberField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-telephone-number']")));
		phoneNumberField.sendKeys(phoneNumber);

//        String postalCode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][21]);
//        WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
//        postalCodeField.sendKeys(postalCode);
//        postalCodeField.sendKeys(Keys.TAB);

		String houseNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][22]);
		WebElement houseNumb = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-house-number']")));
		houseNumb.clear();
		houseNumb.sendKeys(houseNum);

		String addressLine1 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][23]);
		WebElement addressLine1Field = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-address-line-1']")));
		addressLine1Field.clear();
		addressLine1Field.sendKeys(addressLine1);

		String addressLine2 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][24]);
		WebElement addressLine2Field = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-address-line-2']")));
		addressLine2Field.clear();
		addressLine2Field.sendKeys(addressLine2);

		String city = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][25]);
		WebElement cityField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-city']")));
		cityField.clear();
		cityField.sendKeys(city);

		String county = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][26]);
		WebElement countyField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-county']")));
		countyField.click();
		countyField.sendKeys(county);

		String postCode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][27]);
		WebElement postCodeField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='comercial-post-code']")));
		postCodeField.sendKeys(postCode);

		selectDropdownOption("//select[@id='commercial-country']", excelFilePath, "Sheet2", i, 28);

		String vrn = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][29]);
		WebElement vrnField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-VRN']")));
		vrnField.sendKeys(vrn);

		WebElement addBut = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[text()='Add'])[2]")));
		addBut.click();

		System.out.println("Company Number: " + companyNumber);
		System.out.println("VAT Number: " + vatNumber);
		System.out.println("Email: " + email);
		System.out.println("Phone Number: " + phoneNumber);
		// System.out.println("Postal Code: " + postalCode);
		System.out.println("House Number: " + houseNum);
		System.out.println("Address Line 1: " + addressLine1);
		System.out.println("Address Line 2: " + addressLine2);
		System.out.println("City: " + city);
		System.out.println("County: " + county);
		System.out.println("Post Code: " + postCode);
		System.out.println("Vrn: " + vrn);

		fillCommercialFields(excelFilePath, i);

	}

	private void fillPersonalFields(String excelFilePath, int i) throws IOException, InvalidFormatException {
		System.out.println("Choose Personal Insured");
		selectDropdownOption("//select[@id='insured-title']", excelFilePath, "Sheet2", i, 66);

		String firstName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][67]);
		WebElement firstNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredfirst-name']")));
		firstNameField.sendKeys(firstName);

		String lastName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][68]);
		WebElement lastNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredlast-name']")));
		lastNameField.sendKeys(lastName);

		WebElement dateOfBirthField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insureddob']")));
		fetchAndProcessDate(excelFilePath, i, 69, dateOfBirthField);

		String phoneNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][71];
		WebElement phoneNumberField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@id='phone'])[1]")));
		phoneNumberField.sendKeys(phoneNumber);

		String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][72];
		WebElement emailField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='personal-email']")));
		emailField.sendKeys(email);

		String nationalInsuranceNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][73];
		WebElement nationalInsuranceNumberField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insurednationalinsuranceNum']")));
		nationalInsuranceNumberField.sendKeys(nationalInsuranceNumber);

//        String postalCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][74];
//        WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
//        postalCodeField.sendKeys(postalCode);
//        postalCodeField.sendKeys(Keys.TAB);

		String houseNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][75]);
		WebElement houseNumb = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredhouse-number']")));
		houseNumb.sendKeys(houseNum);

		String addressLine1 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][76]);
		WebElement addressLine1Field = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredadress-line1']")));
		addressLine1Field.sendKeys(addressLine1);

		String addressLine2 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][77]);
		WebElement addressLine2Field = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredadress-line2']")));
		addressLine2Field.sendKeys(addressLine2);

		String city = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][78]);
		WebElement cityField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredcity']")));
		cityField.sendKeys(city);

		String county = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][79]);
		WebElement countyField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredCounty']")));
		countyField.sendKeys(county);

		String postCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][80];
		WebElement postCodeField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredpost-code']")));
		postCodeField.sendKeys(postCode);

		selectDropdownOption("//select[@id='insuredcountry']", excelFilePath, "Sheet2", i, 81);

		String vrn = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][82]);
		WebElement vrnField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredVRN']")));
		vrnField.sendKeys(vrn);

		handleComplicitPartyCheckboxFromExcel(excelFilePath, i, 83, wait);

		WebElement addInsuredParty = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add Insured Party']")));
		addInsuredParty.click();

		System.out.println("First Name: " + firstName + ", Last Name: " + lastName + ", Date of Birth: "
				+ dateOfBirthField + ", Phone Number: " + phoneNumber + ", Email: " + email
				+ ", National Insurance Number: " + nationalInsuranceNumber + ", House Number: " + houseNum
				+ ", Address Line 1: " + addressLine1 + ", Address Line 2: " + addressLine2 + ", City: " + city
				+ ", County: " + county + ", VRN: " + vrn);

	}

	private void selectDropdownOption(String xpath, String excelFilePath, String sheetName, int i, int columnIndex)
			throws IOException, InvalidFormatException {
		String optionText = (String) ExcelReader.getExcelData(excelFilePath, sheetName)[i][columnIndex];

		if (optionText == null || optionText.trim().isEmpty()) {
			return;
		}

		WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		Select select = new Select(dropdown);

		try {
			select.selectByVisibleText(optionText);
		} catch (NoSuchElementException e) {
			System.out.println("Option with text '" + optionText + "' not found in the dropdown.");
		}
	}

	private void handleComplicitPartyCheckboxFromExcel(String excelFilePath, int i, int columnNumber,
			WebDriverWait wait) throws InvalidFormatException, IOException {
		String complicitParty = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][columnNumber];

		By checkboxLocator = By.xpath("//div[@class='p-checkbox-box'][1]");
		WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(checkboxLocator));

		if ("yes".equalsIgnoreCase(complicitParty)) {
			if (!checkbox.isSelected()) {
				checkbox.click();
				System.out.println("Selected the complicit party checkbox.");
			} else {
				System.out.println("Complicit party checkbox is already selected.");
			}
		} else if ("no".equalsIgnoreCase(complicitParty)) {
			System.out.println("User chose not to select the complicit party checkbox.");
		} else {
			System.out.println("Invalid value in Excel for complicit party. Please check the Excel file.");
		}
	}

	public void addNewParty(int i) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet2");

		if (i < 0 || i >= TestDat_ClaimReferral.length) {
			System.out.println("Invalid row index.");
			return;
		}

		String partyType = getPartyTypeFromExcel(TestDat_ClaimReferral, i); // Get the party type
		String userChoice = "";

		// Log the fetched values
		System.out.println("Fetched party type: " + partyType);

		// Check based on party type
		if ("Commercial Insured".equalsIgnoreCase(partyType)) {
			userChoice = getChoiceFromExcel(TestDat_ClaimReferral, i, 31); // Check column 31 for Commercial
		} else if ("Personal Insured".equalsIgnoreCase(partyType)) {
			userChoice = getChoiceFromExcel(TestDat_ClaimReferral, i, 84); // Check column 84 for Personal
		}

		// Log the fetched choice
		System.out.println("Fetched user choice: " + userChoice);

		// Validate that choices are either "yes" or "no"
		if (!isValidChoice(userChoice)) {
			System.out.println("Invalid choice. Please enter 'yes' or 'no' for the respective column.");
			return;
		}

		// Handle actions based on user choice
		try {
			if ("yes".equals(userChoice)) {
				WebElement addPartyButton = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-primary']")));
				if (addPartyButton != null && addPartyButton.isEnabled()) {
					addPartyButton.click();
					selectPartyTypeByValue("party-type", partyType);
				} else {
					System.out.println("Add Party button not found or not clickable.");
				}
			} else if ("no".equals(userChoice)) {
				fillRemainFileds(i); // If no, continue to fill the remaining fields
			}
		} catch (Exception e) {
			System.out.println("Error during processing: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Helper method to validate choices
	private boolean isValidChoice(String choice) {
		return "yes".equals(choice) || "no".equals(choice);
	}

	private String getChoiceFromExcel(Object[][] data, int i, int columnIndex) {
		Object value = data[i][columnIndex];
		String choice = value != null ? value.toString().trim() : null;
		if (choice == null || choice.isEmpty()) {
			System.out.println("Choice at row " + i + " and column " + columnIndex + " is empty, defaulting to 'no'");
			return "no";
		}
		return choice.toLowerCase();
	}

	private String getPartyTypeFromExcel(Object[][] data, int i) {
		String partyType = (String) data[i][32]; // First check column 32 for PartyType
		if (partyType == null || partyType.trim().isEmpty()) {
			partyType = (String) data[i][85]; // Fallback to column 85 if 32 is empty
			if (partyType == null || partyType.trim().isEmpty()) {
				partyType = "Default Party Type";
			}
		}
		return partyType.trim();
	}

	private void selectPartyTypeByValue(String dropdownId, String partyType)
			throws InvalidFormatException, IOException {
		if (partyType == null || partyType.trim().isEmpty()) {
			System.out.println("Invalid party type: empty or null value.");
			return;
		}

		// Ensure the dropdown element is visible and clickable
		WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id(dropdownId)));
		wait.until(ExpectedConditions.visibilityOf(dropdown));

		Select select = new Select(dropdown);
		boolean isSelected = false;

		// Iterate over the options and select the matching one
		List<WebElement> options = select.getOptions();
		for (WebElement option : options) {
			if (option.getText().trim().equalsIgnoreCase(partyType)) {
				select.selectByVisibleText(option.getText().trim());
				isSelected = true;
				break;
			}
		}

		if (!isSelected) {
			System.out.println("Party type not found: " + partyType);
			return;
		}

		checkAndFillFormBasedOnType(partyType);
	}

	private void checkAndFillFormBasedOnType(String partyType) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Set<String> commercialSet = new HashSet<>(Arrays.asList("accident management company", "broker",
				"claimant solicitor", "credit hire organisation", "defendant lawyer", "loss assessor", "medical agency",
				"other representative - company", "repairer", "storage/recovery agent", "supplier",
				"third party company", "tp vehicle assessor"));

		Set<String> personalSet = new HashSet<>(
				Arrays.asList("insured driver", "insured passenger", "joint policyholder", "leaseholder",
						"medical expert", "other representative - person", "property owner", "tenant",
						"third party claimant", "third party driver", "third party passenger", "witness"));

		if (commercialSet.contains(partyType.toLowerCase())) {
			fillCommercialPartyFields(excelFilePath, 0);
		} else if (personalSet.contains(partyType.toLowerCase())) {
			fillPersonalPartyDetails(excelFilePath, 0);
		} else {
			System.out.println("The selected party type is not recognized in either Commercial or Personal sets.");
		}
	}

	public void fillCommercialPartyFields(String excelFilePath, int i) throws IOException, InvalidFormatException {
		String commercialComName = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][33];

		WebElement commercialPartyInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//input[@class='ng-tns-c79-6 p-autocomplete-input p-inputtext p-component ng-star-inserted']")));
		commercialPartyInput.clear();
		commercialPartyInput.sendKeys(commercialComName);

		List<WebElement> commercialPartyOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
				By.xpath("//li[@class='p-autocomplete-item ng-tns-c79-6 p-ripple ng-star-inserted']")));

		if (!commercialPartyOptions.isEmpty()) {
			boolean foundCommercialParty = false;

			for (WebElement option : commercialPartyOptions) {
				if (option.getText().equalsIgnoreCase(commercialComName)) {
					foundCommercialParty = true;
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
					wait.until(ExpectedConditions.elementToBeClickable(option));
					option.click();
					fillCommercialParty(excelFilePath, 0);
					break;
				}
			}

			if (!foundCommercialParty) {
				fillCommercialPartyDetails(excelFilePath, 0);
			}
		} else {
			fillCommercialPartyDetails(excelFilePath, 0);
		}
	}

	private void fillCommercialParty(String excelFilePath, int i) throws InvalidFormatException, IOException {
		String keyCon = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[0][47]);
		WebElement keyConFie = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[@class='btn btn-outline-primary add-popup-btn']")));
		keyConFie.sendKeys(keyCon);

		handleComplicitPartyCheckboxFromExcel(excelFilePath, 0, 48, wait);
		WebElement addButCom = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary ng-star-inserted'])[1]")));
		addButCom.click();
		fillRemainFileds(0);

	}

	private void fillCommercialPartyDetails(String excelFilePath, int i) throws IOException, InvalidFormatException {

		selectDropdownOption("//select[@id='party-type']", excelFilePath, "Sheet2", i, 32);

		WebElement addButCom = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[@class='btn btn-outline-primary add-popup-btn']")));
		addButCom.click();
		String companyName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][33]);
		WebElement companyNameInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='company-name']")));
		companyNameInput.clear();
		companyNameInput.sendKeys(companyName);

		String phoneNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][37];
		WebElement phoneNumberInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
		phoneNumberInput.clear();
		phoneNumberInput.sendKeys(phoneNumber);

		selectDropdownOption("//select[@id='enabler-party-type']", excelFilePath, "Sheet2", i, 32);

		String companyNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][34];
		WebElement companyNumberInput = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-company-number']")));

		companyNumberInput.clear();
		companyNumberInput.sendKeys(companyNumber);

		String vatNumber = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][35]);
		WebElement vatNumberInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-vat-number']")));
		vatNumberInput.clear();
		vatNumberInput.sendKeys(vatNumber);

		String email = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][36]);
		WebElement emailInput = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-company-email']")));
		emailInput.clear();
		emailInput.sendKeys(email);

//        String postalCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][38];
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
//        WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
//        postalCodeField.sendKeys(postalCode);
//        postalCodeField.sendKeys(Keys.TAB);

		String houseNumber = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][39]);
		WebElement houseNumberInput = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-house-number']")));
		houseNumberInput.clear();
		houseNumberInput.sendKeys(houseNumber);

		String addressLine1 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][40]);
		WebElement addressLine1Input = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-address-line-1']")));
		addressLine1Input.clear();
		addressLine1Input.sendKeys(addressLine1);

		String addressLine2 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][41]);
		WebElement addressLine2Input = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-address-line-2']")));
		addressLine2Input.clear();
		addressLine2Input.sendKeys(addressLine2);

		String city = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][42]);
		WebElement cityInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-city']")));
		cityInput.clear();
		cityInput.sendKeys(city);

		String county = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][43]);
		WebElement countyInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-county']")));
		countyInput.clear();
		countyInput.sendKeys(county);

		String postcode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[0][44]);
		WebElement postcodeInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='comercialparty-post-code']")));
		postcodeInput.clear();
		postcodeInput.sendKeys(postcode);

		selectDropdownOption("//select[@id='commercialparty-country']", excelFilePath, "Sheet2", i, 45);

		String vrn = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][46]);
		WebElement vrnInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-VRN']")));
		vrnInput.clear();
		vrnInput.sendKeys(vrn);

		WebElement addButton = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//button[@class='btn btn-primary ng-star-inserted'])[2]")));

		addButton.click();
		System.out.println("Clicked the 'Add' button.");

	}

	private void fillPersonalPartyDetails(String excelFilePath, int i) throws IOException, InvalidFormatException {
		selectDropdownOption("//select[@id='third-party-title']", excelFilePath, "Sheet2", i, 86);

		String firstName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][87]);
		WebElement firstNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='first-name']")));
		firstNameField.sendKeys(firstName);
		System.out.println("First Name is empty for row " + i);

		String lastName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][88]);
		WebElement lastNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='last-name']")));
		lastNameField.sendKeys(lastName);

		WebElement dateOfBirthField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='dob']")));
		fetchAndProcessDate(excelFilePath, i, 89, dateOfBirthField);

		String phoneNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][91];
		WebElement phoneNumberField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='phone']")));
		phoneNumberField.sendKeys(phoneNumber);

		String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][92];
		WebElement emailField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='thirdparty-email']")));
		emailField.sendKeys(email);

		String nationalInsuranceNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][93];
		WebElement nationalInsuranceNumberField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='nationalInsuranceNumber']")));
		nationalInsuranceNumberField.sendKeys(nationalInsuranceNumber);

//        String postalCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][94];
//        WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
//        postalCodeField.sendKeys(postalCode);
//        postalCodeField.sendKeys(Keys.TAB);

		String houseNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][95]);
		WebElement houseNumb = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='house-number']")));
		houseNumb.sendKeys(houseNum);

		String addressLine1 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][96]);
		WebElement addressLine1Field = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='adress-line1']")));
		addressLine1Field.sendKeys(addressLine1);

		String addressLine2 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][97]);
		WebElement addressLine2Field = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='adress-line2']")));
		addressLine2Field.sendKeys(addressLine2);

		String city = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][98]);
		WebElement cityField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='city']")));
		cityField.sendKeys(city);

		String county = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][99]);
		WebElement countyField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='county']")));
		countyField.sendKeys(county);

		String postCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][100];
		WebElement postCodeField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='post-code']")));
		postCodeField.sendKeys(postCode);

		selectDropdownOption("//select[@id='country']", excelFilePath, "Sheet2", i, 101);

		String vrn = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet2")[i][102]);
		WebElement vrnField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='vrn']")));
		vrnField.sendKeys(vrn);

		handleComplicitPartyCheckboxFromExcel(excelFilePath, i, 103, wait);

		WebElement addParty = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//button[@class='btn btn-primary ng-star-inserted'])[1]")));
		addParty.click();
	}

	public void handleCancelButton(WebDriverWait wait, String cancelButtonXPath) {
		// Find the "Cancel" button using the dynamic XPath provided
		By cancelButtonLocator = By.xpath(cancelButtonXPath);
		WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(cancelButtonLocator));

		cancelButton.click();
		System.out.println("Clicked the 'Cancel' button.");
	}

	private void clickSaveDraft() {
		WebElement saveDraftButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Save Draft')]")));
		saveDraftButton.click();
	}

	@Then("I should submit the form successfully and receive a confirmation message")
public void i_should_submit_the_form_successfully_and_receive_a_confirmation_message() {
//		System.out.println("Successfully created Claim Referral");
	WebElement submitButton = driver.findElement(By.xpath("//button[text()=' Submit for triage ']"));
	Actions actions = new Actions(driver);
	actions.moveToElement(submitButton).click().perform();
	}

	
}
