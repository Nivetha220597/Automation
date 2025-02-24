package com.stepdefinitions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;

public class CreateInvestigation {
	private WebDriver driver;
	private WebDriverWait wait;

	@Given("I am on the Zodiac login page for Create Investigation")
	public void i_am_on_the_zodiac_login_page_for_create_investigation() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://app-zodiac-test.azurewebsites.net/");
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-username")));
		System.out.println("I am on the Zodiac login page");
	}

	@And("I enter a valid username and password from Excel Sheet for Create Investigation")
	public void i_enter_a_valid_username_and_password_from_excel_sheet() throws IOException, InvalidFormatException {

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

	@When("I click on the Login button for Create Investigation")
	public void i_click_on_the_login_button() {
		WebElement loginBtn = driver.findElement(By.xpath("//input[@id='okta-signin-submit']"));
		loginBtn.click();
		System.out.println("Clicked the login button");
		System.out.println("Successfully redirected to the Dashboard");
	}

	@Given("I click the Investigations option for Create Investigation")
	public void i_click_the_investigations_option() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement investigations = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Investigations']")));
		investigations.click();
	}

	@And("I select Create Investigation option")
	public void i_select_create_investigation_option() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		WebElement createInvestigationOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Create Investigation ']")));
		createInvestigationOption.click();
		System.out.println("I'm on the create Investigation page");
	}

	@When("I should fill the Create Investigation form")
	public void i_fill_the_create_referral_form_with_test_data_from_excel()
			throws IOException, InvalidFormatException, ParseException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet8");
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

				System.out.println("Processed row " + (i + 1) + " and reopened the form.");
			} catch (Exception e) {
				System.out.println("Error processing row " + (i + 1) + ": " + e.getMessage());
			}
		}
	}

	public void waitForElementAndFillClaimReference(int i) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet8");
		String claimNum = (String) TestDat_ClaimReferral[i][0];

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

	private void fillFormFields(int i, Object[][] TestDat_ClaimReferral) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		String policyNumber = (String) TestDat_ClaimReferral[i][1];
		WebElement policyNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.id("policy-number")));
		policyNumberField.clear();
		policyNumberField.sendKeys(policyNumber);

		WebElement dateOfLoss = waitForElement("//input[@id='date-of-loss']");
		fetchAndProcessDate(excelFilePath, i, 2, dateOfLoss);
						
		selectDropdownOption("//select[@id='claim-type']", excelFilePath, "Sheet8", i, 3);
		selectDropdownOption("//select[@id='line-of-business']", excelFilePath, "Sheet8", i, 4);
		selectDropdownOption("//select[@id='market-facing-unit']", excelFilePath, "Sheet8", i, 5);
		selectDropdownOption("//select[@id='jurisdiction']", excelFilePath, "Sheet8", i, 6);
		selectDropdownOption("//select[@id='claim-source']", excelFilePath, "Sheet8", i, 7);
		selectDropdownOption("//select[@id='litigation']", excelFilePath, "Sheet8", i, 8);

		WebElement inciLocaField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='incident-loc-add']")));
		String inciLoca = (String) TestDat_ClaimReferral[i][9];
		inciLocaField.clear();
		inciLocaField.sendKeys(inciLoca);
		inciLocaField.sendKeys(Keys.ENTER);
		selectDropdownOption("//select[@id='complaints']", excelFilePath, "Sheet8", i, 10);

		String circumtance = (String) TestDat_ClaimReferral[i][11];
		WebElement circumtanceField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='claimCircumstances']")));
		circumtanceField.clear();
		circumtanceField.sendKeys(circumtance);
		System.out.println("Circumstance: " + circumtance);
		
		investigationOwner(excelFilePath, i);
		
		selectDropdownOption("//select[@id='investigation-team']", excelFilePath, "Sheet8", i, 13);
		selectDropdownOption("//select[@id='investigation-stage']", excelFilePath, "Sheet8", i, 13);
		selectDropdownOption("//select[@id='fraud-type']", excelFilePath, "Sheet8", i, 14);
		selectDropdownOption("//select[@id='detection-method']", excelFilePath, "Sheet8", i, 15);
		selectDropdownOption("//select[@id='referrer-location']", excelFilePath, "Sheet8", i, 16);
		selectDropdownOption("//select[@id='referrer-team']", excelFilePath, "Sheet8", i, 17);
		selectDropdownOption("//select[@id='field-investigation-status']", excelFilePath, "Sheet8", i, 18);

		String concern = (String) TestDat_ClaimReferral[i][19];
		WebElement concernField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='concerns']")));
		concernField.sendKeys(concern);
		System.out.println("Concerns: " + concern);
		createContinue();

	}
	public void investigationOwner(String excelFilePath, int i) throws IOException, InvalidFormatException {
	    String investigationOwner = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][12];
	    System.out.println("Fetched Investigation Owner from Excel: " + investigationOwner);

	    if (investigationOwner == null || investigationOwner.trim().isEmpty()) {
	        System.out.println("Investigation Owner value is empty for row " + i);
	        return;
	    }

	    WebElement investigationOwnerInput = wait.until(
	            ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@class, 'p-autocomplete-input')]")));
	    investigationOwnerInput.clear();

	    investigationOwnerInput.sendKeys(investigationOwner);
	    System.out.println("Entered Investigation Owner: " + investigationOwner);

	    List<WebElement> investigationOwnerOptions = wait.until(ExpectedConditions
	            .visibilityOfAllElementsLocatedBy(By.xpath("//li[contains(@class, 'p-autocomplete-item')]")));

	    if (!investigationOwnerOptions.isEmpty()) {
	        boolean foundInvestigationOwner = false;

	        for (WebElement option : investigationOwnerOptions) {
	            String optionText = option.getText().trim();
	            if (optionText.equalsIgnoreCase(investigationOwner)) {
	                foundInvestigationOwner = true;

	                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
	                wait.until(ExpectedConditions.elementToBeClickable(option));
	                option.click();
	                System.out.println("Selected Investigation Owner: " + optionText);
	                break;
	            }
	        }

	        if (!foundInvestigationOwner) {
	            System.out.println("Investigation Owner '" + investigationOwner + "' not found in the dropdown.");
	        }
	    } else {
	        System.out.println("No dropdown options found.");
	    }
	}


	private WebElement waitForElement(String xpath) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	private void createContinue() throws InvalidFormatException, IOException {
		WebElement createContinueBut = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Create and continue ']")));
		createContinueBut.click();
		WebElement submitButton = driver.findElement(By.xpath("//button[text()=' Submit for triage ']"));
		Actions actions = new Actions(driver);
		actions.moveToElement(submitButton).click().perform();

		summaryPage();
	}

	private void summaryPage() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet8");
		selectDropdownOption("defendant-lawyer", excelFilePath, "Sheet8", 0, 20);
		String incLocDet = (String) TestDat_ClaimReferral[0][9];
		WebElement incLocDetField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='incident-loc-details']")));
		incLocDetField.clear();
		incLocDetField.sendKeys(incLocDet);
		System.out.println("Incident Location Details: " + incLocDet);

		String secOwner = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[0][21];

		if (secOwner == null || secOwner.trim().isEmpty()) {
			return;
		}
		WebElement secOwnerField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//input[@class='ng-tns-c79-12 p-autocomplete-input p-inputtext p-component ng-star-inserted' and @name='secondaryOwner']")));
		secOwnerField.clear();
		secOwnerField.sendKeys(secOwner);

		List<WebElement> secOwnerOptions = wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//ul[contains(@class, 'p-autocomplete-items')]/li")));

		if (!secOwnerOptions.isEmpty()) {
			boolean foundSecOwn = false;

			for (WebElement option : secOwnerOptions) {
				if (option.getText().equalsIgnoreCase(secOwner)) {
					foundSecOwn = true;
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
					wait.until(ExpectedConditions.elementToBeClickable(option));
					option.click();
					System.out.println(option);
					System.out.println(foundSecOwn);
					break;
				}
			}
		}
		String fieldInves = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[0][22];

		if (fieldInves == null || fieldInves.trim().isEmpty()) {
			return;
		}
		WebElement fieldInvesField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//input[@class='ng-tns-c79-13 p-autocomplete-input p-inputtext p-component ng-star-inserted' and @name='fieldInvestigator']\r\n"
						+ "")));
		fieldInvesField.clear();
		fieldInvesField.sendKeys(secOwner);

		List<WebElement> filedInveOptions = wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//ul[contains(@class, 'p-autocomplete-items')]/li")));

		if (!filedInveOptions.isEmpty()) {
			boolean foundFieInve = false;

			for (WebElement option : filedInveOptions) {
				if (option.getText().equalsIgnoreCase(fieldInves)) {
					foundFieInve = true;
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
					wait.until(ExpectedConditions.elementToBeClickable(option));
					option.click();
					System.out.println(option);
					System.out.println(foundFieInve);
					break;
				}
			}
		}

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

		selectDropdownOption("weeding", excelFilePath, "Sheet8", 0, 23);
		handleExporttoNetRevealFromExcel(excelFilePath, i, 24, wait);
		saveButton();
		selectInsuredRadioButtonFromExcel(excelFilePath, 25);
		addNewParty(i);
		saveButton();
	}

	private void handleExporttoNetRevealFromExcel(String excelFilePath, int i, int columnNumber, WebDriverWait wait)
			throws InvalidFormatException, IOException {
		String netReveal = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][columnNumber];

		By checkboxLocator = By.xpath("//div[@class='custom-control custom-checkbox']");
		WebElement checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(checkboxLocator));

		if ("yes".equalsIgnoreCase(netReveal)) {
			if (!checkbox.isSelected()) {
				checkbox.click();
				System.out.println("Selected the complicit party checkbox.");
			} else {
				System.out.println("Complicit party checkbox is already selected.");
			}
		} else if ("no".equalsIgnoreCase(netReveal)) {
			System.out.println("User chose not to select the complicit party checkbox.");
		} else {
			System.out.println("Invalid value in Excel for complicit party. Please check the Excel file.");
		}
	}

	private void saveButton() {
		WebElement saveButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("(//button[text()=' Save '])[2]")));
		saveButton.click();

	}

	public void selectInsuredRadioButtonFromExcel(String excelFilePath, int i)
			throws InvalidFormatException, IOException {
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet8");

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
		String companyName = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][26];

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

					fillCommercialDetails(excelFilePath, 27);
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
		WebElement bankAccNumFiled = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-accountNumber']")));
		WebElement sortCodeField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-insured-sortCode']")));
		WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
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

		String companyNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][27];
		String vatNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][28];
		String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][29];
		String phoneNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][30];
		String bankAccNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][31];
		String sortCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][32];
		String postalCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][33];
		String houseNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][34];
		String addressLine1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][35];
		String addressLine2 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][36];
		String city = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][37];
		String county = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][38];
		String postCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][39];
		String vrn = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][41];

		companyNumberField.clear();
		companyNumberField.sendKeys(companyNumber);
		vatNumberField.clear();
		vatNumberField.sendKeys(vatNumber);
		emailField.clear();
		emailField.sendKeys(email);
		phoneNumberField.clear();
		phoneNumberField.sendKeys(phoneNumber);
		bankAccNumFiled.clear();
		bankAccNumFiled.sendKeys(bankAccNum);
		sortCodeField.clear();
		sortCodeField.sendKeys(sortCode);
		postalCodeField.clear();
		postalCodeField.sendKeys(postalCode);
		postalCodeField.sendKeys(Keys.TAB);
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
		selectDropdownOption("//select[@id='commercial-insured-country']", excelFilePath, "Sheet8", i, 40);
		vrnField.clear();
		vrnField.sendKeys(vrn);

		handleComplicitPartyCheckboxFromExcel(excelFilePath, i, 42, wait);

		WebElement addInsuredParty = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add Insured Party']")));
		addInsuredParty.click();

		System.out.println("Company Number: " + companyNumber);
		System.out.println("VAT Number: " + vatNumber);
		System.out.println("Email: " + email);
		System.out.println("Phone Number: " + phoneNumber);
		System.out.println("Bank Account Number: " + bankAccNum);
		System.out.println("Sort Code: " + sortCode);
		System.out.println("Postal Code: " + postalCode);
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

		String companyName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][26]);
		WebElement companyNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercial-company-name']")));
		companyNameField.sendKeys(companyName);

		String companyNumber = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][27]);
		WebElement companyNumberField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-company-number']")));
		companyNumberField.sendKeys(companyNumber);

		String vatNumber = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][28]);
		WebElement vatNumberField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-vat-number']")));
		vatNumberField.sendKeys(vatNumber);

		String email = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][29]);
		WebElement emailField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-company-email']")));
		emailField.sendKeys(email);

		String phoneNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][30];
		WebElement phoneNumberField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-telephone-number']")));
		phoneNumberField.sendKeys(phoneNumber);

		String bankAccNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][31];
		WebElement bankAccNumFiled = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-accountNumber']")));
		bankAccNumFiled.sendKeys(bankAccNum);

		String sortCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][32];
		WebElement sortCodeField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-sortCode']")));
		sortCodeField.sendKeys(sortCode);

		String postalCode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][33]);
		WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
		postalCodeField.sendKeys(postalCode);
		postalCodeField.sendKeys(Keys.TAB);

		String houseNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][34]);
		WebElement houseNumb = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-house-number']")));
		houseNumb.clear();
		houseNumb.sendKeys(houseNum);

		String addressLine1 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][35]);
		WebElement addressLine1Field = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-address-line-1']")));
		addressLine1Field.clear();
		addressLine1Field.sendKeys(addressLine1);

		String addressLine2 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][36]);
		WebElement addressLine2Field = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-address-line-2']")));
		addressLine2Field.clear();
		addressLine2Field.sendKeys(addressLine2);

		String city = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][37]);
		WebElement cityField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-city']")));
		cityField.clear();
		cityField.sendKeys(city);

		String county = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][38]);
		WebElement countyField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='commercial-county']")));
		countyField.click();
		countyField.sendKeys(county);

		String postCode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][39]);
		WebElement postCodeField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='comercial-post-code']")));
		postCodeField.sendKeys(postCode);

		selectDropdownOption("//select[@id='commercial-country']", excelFilePath, "Sheet8", i, 40);

		String vrn = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][41]);
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
		System.out.println("Bank Account Number: " + bankAccNum);
		System.out.println("Sort Code: " + sortCode);
		System.out.println("Postal Code: " + postalCode);
		System.out.println("House Number: " + houseNum);
		System.out.println("Address Line 1: " + addressLine1);
		System.out.println("Address Line 2: " + addressLine2);
		System.out.println("City: " + city);
		System.out.println("County: " + county);
		System.out.println("Post Code: " + postCode);
		System.out.println("Vrn: " + vrn);
		handleComplicitPartyCheckboxFromExcel(excelFilePath, i, 42, wait);
		fillCommercialFields(excelFilePath, i);

	}

	private void fillPersonalFields(String excelFilePath, int i) throws IOException, InvalidFormatException {
		System.out.println("Choose Personal Insured");
		selectDropdownOption("//select[@id='insured-title']", excelFilePath, "Sheet8", i, 66);

		String firstName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][67]);
		WebElement firstNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredfirst-name']")));
		firstNameField.sendKeys(firstName);

		String lastName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][68]);
		WebElement lastNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredlast-name']")));
		lastNameField.sendKeys(lastName);

		WebElement dateOfBirthField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insureddob']")));
		fetchAndProcessDate(excelFilePath, i, 69, dateOfBirthField);

		String phoneNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][71];
		WebElement phoneNumberField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//input[@id='phone'])[1]")));
		phoneNumberField.sendKeys(phoneNumber);

		String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][72];
		WebElement emailField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='personal-email']")));
		emailField.sendKeys(email);

		String nationalInsuranceNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][73];
		WebElement nationalInsuranceNumberField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insurednationalinsuranceNum']")));
		nationalInsuranceNumberField.sendKeys(nationalInsuranceNumber);

		String bankAccNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][73];
		WebElement bankAccNumField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredaccountNumber']")));
		bankAccNumField.sendKeys(bankAccNum);

		String sortCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][73];
		WebElement sortCodeField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredsortCode']")));
		sortCodeField.sendKeys(sortCode);

		String postalCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][74];
		WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
		postalCodeField.sendKeys(postalCode);
		postalCodeField.sendKeys(Keys.TAB);

		String houseNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][75]);
		WebElement houseNumb = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredhouse-number']")));
		houseNumb.sendKeys(houseNum);

		String addressLine1 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][76]);
		WebElement addressLine1Field = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredadress-line1']")));
		addressLine1Field.sendKeys(addressLine1);

		String addressLine2 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][77]);
		WebElement addressLine2Field = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredadress-line2']")));
		addressLine2Field.sendKeys(addressLine2);

		String city = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][78]);
		WebElement cityField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredcity']")));
		cityField.sendKeys(city);

		String county = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][79]);
		WebElement countyField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredCounty']")));
		countyField.sendKeys(county);

		String postCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][80];
		WebElement postCodeField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredpost-code']")));
		postCodeField.sendKeys(postCode);

		selectDropdownOption("//select[@id='insuredcountry']", excelFilePath, "Sheet8", i, 81);

		String vrn = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][82]);
		WebElement vrnField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='insuredVRN']")));
		vrnField.sendKeys(vrn);

		handleComplicitPartyCheckboxFromExcel(excelFilePath, i, 83, wait);

		WebElement addInsuredParty = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Add Insured Party']")));
		addInsuredParty.click();

		System.out.println("First Name: " + firstName + ", Last Name: " + lastName + ", Date of Birth: "
				+ dateOfBirthField + ", Phone Number: " + phoneNumber + ", Email: " + email
				+ ", National Insurance Number: " + nationalInsuranceNumber + ", Bank Account Number: " + bankAccNum
				+ ", Sort Code: " + sortCode + ", House Number: " + houseNum + ", Address Line 1: " + addressLine1
				+ ", Address Line 2: " + addressLine2 + ", City: " + city + ", County: " + county + ", VRN: " + vrn);
	}

	public void fetchAndProcessDate(String excelFilePath, int i, int columnIndex, WebElement dateField) {
		try {
			String dateInput = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][columnIndex];
			dateInput = dateInput != null ? dateInput.trim() : "";
			System.out.println("Fetched Date from Excel: " + dateInput);
			processAndFillDateField(dateInput, dateField);
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	private void handleComplicitPartyCheckboxFromExcel(String excelFilePath, int i, int columnNumber,
			WebDriverWait wait) throws InvalidFormatException, IOException {
		String complicitParty = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][columnNumber];

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
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet8");

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
				// fillRemainFileds(i); // If no, continue to fill the remaining fields
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
		String commercialComName = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][33];

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
		String keyCon = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[0][47]);
		WebElement keyConFie = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[@class='btn btn-outline-primary add-popup-btn']")));
		keyConFie.sendKeys(keyCon);

		handleComplicitPartyCheckboxFromExcel(excelFilePath, 0, 48, wait);
		WebElement addButCom = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("(//button[@class='btn btn-primary ng-star-inserted'])[1]")));
		addButCom.click();
		// fillRemainFileds(0);

	}

	private void fillCommercialPartyDetails(String excelFilePath, int i) throws IOException, InvalidFormatException {

		selectDropdownOption("//select[@id='party-type']", excelFilePath, "Sheet8", i, 32);

		WebElement addButCom = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//button[@class='btn btn-outline-primary add-popup-btn']")));
		addButCom.click();
		String companyName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][33]);
		WebElement companyNameInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='company-name']")));
		companyNameInput.clear();
		companyNameInput.sendKeys(companyName);

		String phoneNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][37];
		WebElement phoneNumberInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
		phoneNumberInput.clear();
		phoneNumberInput.sendKeys(phoneNumber);

		selectDropdownOption("//select[@id='enabler-party-type']", excelFilePath, "Sheet8", i, 32);

		String companyNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][34];
		WebElement companyNumberInput = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-company-number']")));

		companyNumberInput.clear();
		companyNumberInput.sendKeys(companyNumber);

		String vatNumber = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][35]);
		WebElement vatNumberInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-vat-number']")));
		vatNumberInput.clear();
		vatNumberInput.sendKeys(vatNumber);

		String email = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][36]);
		WebElement emailInput = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-company-email']")));
		emailInput.clear();
		emailInput.sendKeys(email);

//        String postalCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][38];
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
//        WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
//        postalCodeField.sendKeys(postalCode);
//        postalCodeField.sendKeys(Keys.TAB);

		String houseNumber = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][39]);
		WebElement houseNumberInput = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-house-number']")));
		houseNumberInput.clear();
		houseNumberInput.sendKeys(houseNumber);

		String addressLine1 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][40]);
		WebElement addressLine1Input = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-address-line-1']")));
		addressLine1Input.clear();
		addressLine1Input.sendKeys(addressLine1);

		String addressLine2 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][41]);
		WebElement addressLine2Input = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-address-line-2']")));
		addressLine2Input.clear();
		addressLine2Input.sendKeys(addressLine2);

		String city = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][42]);
		WebElement cityInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-city']")));
		cityInput.clear();
		cityInput.sendKeys(city);

		String county = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][43]);
		WebElement countyInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='commercialparty-county']")));
		countyInput.clear();
		countyInput.sendKeys(county);

		String postcode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[0][44]);
		WebElement postcodeInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='comercialparty-post-code']")));
		postcodeInput.clear();
		postcodeInput.sendKeys(postcode);

		selectDropdownOption("//select[@id='commercialparty-country']", excelFilePath, "Sheet8", i, 45);

		String vrn = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][46]);
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
		selectDropdownOption("//select[@id='third-party-title']", excelFilePath, "Sheet8", i, 86);

		String firstName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][87]);
		WebElement firstNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='first-name']")));
		firstNameField.sendKeys(firstName);
		System.out.println("First Name is empty for row " + i);

		String lastName = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][88]);
		WebElement lastNameField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='last-name']")));
		lastNameField.sendKeys(lastName);

		WebElement dateOfBirthField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='dob']")));
		fetchAndProcessDate(excelFilePath, i, 89, dateOfBirthField);

		String phoneNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][91];
		WebElement phoneNumberField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='phone']")));
		phoneNumberField.sendKeys(phoneNumber);

		String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][92];
		WebElement emailField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='thirdparty-email']")));
		emailField.sendKeys(email);

		String nationalInsuranceNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][93];
		WebElement nationalInsuranceNumberField = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='nationalInsuranceNumber']")));
		nationalInsuranceNumberField.sendKeys(nationalInsuranceNumber);

//        String postalCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][94];
//        WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
//        postalCodeField.sendKeys(postalCode);
//        postalCodeField.sendKeys(Keys.TAB);

		String houseNum = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][95]);
		WebElement houseNumb = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='house-number']")));
		houseNumb.sendKeys(houseNum);

		String addressLine1 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][96]);
		WebElement addressLine1Field = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='adress-line1']")));
		addressLine1Field.sendKeys(addressLine1);

		String addressLine2 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][97]);
		WebElement addressLine2Field = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='adress-line2']")));
		addressLine2Field.sendKeys(addressLine2);

		String city = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][98]);
		WebElement cityField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='city']")));
		cityField.sendKeys(city);

		String county = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][99]);
		WebElement countyField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='county']")));
		countyField.sendKeys(county);

		String postCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][100];
		WebElement postCodeField = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='post-code']")));
		postCodeField.sendKeys(postCode);

		selectDropdownOption("//select[@id='country']", excelFilePath, "Sheet8", i, 101);

		String vrn = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet8")[i][102]);
		WebElement vrnField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='vrn']")));
		vrnField.sendKeys(vrn);

		handleComplicitPartyCheckboxFromExcel(excelFilePath, i, 103, wait);

		WebElement addParty = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//button[@class='btn btn-primary ng-star-inserted'])[1]")));
		addParty.click();
	}
}
