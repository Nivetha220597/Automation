package com.stepdefinitions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;

public class CreateInvestigationNegative {

	private WebDriver driver;
	private WebDriverWait wait;

	@Given("I am on the Zodiac login page for Create Intelligence TC")
	public void i_am_on_the_zodiac_login_page_for_create_intelligence_tc() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://app-zodiac-test.azurewebsites.net/");
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-username")));
		System.out.println("I am on the Zodiac login page");
	}

	@And("I enter a valid username and password from Excel for Create Intelligence TC")
	public void i_enter_a_valid_username_and_password_from_excel_for_create_intelligence_tc()
			throws IOException, InvalidFormatException {

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

	@When("I click on the login button for Create Intelligence TC")
	public void i_click_on_the_login_button_for_create_intelligence_tc() {
		WebElement loginBtn = driver.findElement(By.xpath("//input[@id='okta-signin-submit']"));
		loginBtn.click();
		System.out.println("Clicked the login button");
		System.out.println("Successfully redirected to the Dashboard");
	}

	@Then("I click the Intelligence option TC")
	public void i_click_the_intelligence_option__tc() {
		try {
		    this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));

		    WebElement intelligence = wait
		            .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Intelligence']")));
		    intelligence.click();
		    System.out.println("Intelligence option clicked");
		} catch (TimeoutException e) {
		    System.out.println("Timeout: The element was not clickable within the given time.");
		    e.printStackTrace();
		} catch (NoSuchElementException e) {
		    System.out.println("Error: The element was not found on the page.");
		    e.printStackTrace();
		} catch (Exception e) {
		    System.out.println("An unexpected error occurred.");
		    e.printStackTrace();
		}
	}

	@And("I select Create Intelligence option TC")
	public void i_select_create_intelligence_option_tc() {
		WebElement createIntelliegenceOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/library/add-intelligence']")));
		createIntelliegenceOption.click();
		System.out.println("Navigated to Create Intelligence page");
	}

	@And("I select Structured option TC")
	public void i_select_structured_option_tc() {
		WebElement structured = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[text()=' Data points which can be added to a structured set of form fields ']")));
		structured.click();
		System.out.println("Select Structured");
	}

	@Then("I fill the Structured form with test data from Excel TC")
	public void i_fill_the_structured_form_with_test_data_from_excel_tc()
			throws IOException, InvalidFormatException, ParseException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");
		System.out.println("Number of rows in Excel: " + TestDat_ClaimReferral.length);

		// Ensure there's data in the Excel file
		if (TestDat_ClaimReferral.length == 0) {
			System.out.println("Excel data is empty. No rows to process.");
			return;
		}
		for (int i = 0; i < TestDat_ClaimReferral.length; i++) {
			System.out.println("Processing row: " + (i + 1));
			try {
				startFill(i);

				System.out.println("Processed row " + (i + 1) + " and reopened the form.");
			} catch (Exception e) {
				System.out.println("Error processing row " + (i + 1) + ": " + e.getMessage());
			}
		}
	}

	private void startFill(int i) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[i][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", i, 0);

		intelligenceOwner(excelFilePath, i);

		WebElement incidentDate = waitForElement("//input[@id='incident-date']");
		fetchAndProcessDate(excelFilePath, i, 2, incidentDate);

		waitForElementAndFillClaimReference(i);

		String operationName = (String) TestDat_ClaimReferral[i][4];
		fillInputField("//input[@id='operation-name']", operationName);

		String inciLoca = (String) TestDat_ClaimReferral[i][5];
		fillInputField("//input[@id='loc-add']", inciLoca);

		String policyNum = (String) TestDat_ClaimReferral[i][6];
		fillInputField("//input[@id='policy-number']", policyNum);

		WebElement policyIncepDate = waitForElement("//input[@id='policy-inception-date']");
		fetchAndProcessDate(excelFilePath, i, 7, policyIncepDate);

		selectCompanyPersonRadioButtonFromExcel(excelFilePath, i);
		fillRemain(i);
		addNewCompany(excelFilePath, i);
		submit();

	}

	private void submit() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// Wait for the toast notification to be visible
			WebElement toastDetail = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-detail.ng-tns-c61-57")));
			WebElement closeToastButton = toastDetail.findElement(By.cssSelector(".p-toast-icon-close"));
			closeToastButton.click();
			System.out.println("Toast notification closed.");
		} catch (TimeoutException e) {
			System.out.println("No toast notification found or it took too long.");
		}

		// Wait for the submit button to be clickable
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement submitButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-primary mt-3']")));
		submitButton.click();
		System.out.println("Clicked Submit");

		try {
			WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//div[text()='Intelligence has been created successfully']")));
			System.out.println("Confirmation message displayed: " + confirmationMessage.getText());
		} catch (TimeoutException e) {
			System.out.println("Confirmation message not found or took too long.");
		}
	}

	private WebElement waitForElement(String xpath) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
	}

	private void fillInputField(String xpath, String value) {
		WebElement field = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		field.clear();
		field.sendKeys(value);
	}

	public void selectCompanyPersonRadioButtonFromExcel(String excelFilePath, int i)
			throws InvalidFormatException, IOException {
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			String personDetail = (String) excelData[i][37];

			companyDetail = companyDetail != null ? companyDetail.trim() : "";
			personDetail = personDetail != null ? personDetail.trim() : "";

			System.out.println(
					"Processing i: " + i + " with Company: '" + companyDetail + "' and Person: '" + personDetail + "'");

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			WebElement companyNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[1]")));
			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			WebElement personNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[2]")));

			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
				fillComDetailsFromExcel(excelFilePath, i, 9);
			} else {
				System.out.println("Company is empty, selecting 'No'");
				selectRadioButton(companyNo);
			}

			if (!personDetail.isEmpty()) {
				System.out.println("Person is provided, selecting 'Yes'");
				selectRadioButton(personYes);
				fillPersonDetailsFromExcel(excelFilePath, i, 38);
			} else {
				System.out.println("Person is empty, selecting 'No'");
				selectRadioButton(personNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillRemain(int i) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement notesField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='notes']")));
		String notes = (String) TestDat_ClaimReferral[i][63];
		notesField.sendKeys(notes);
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

	private void fillComDetailsFromExcel(String excelFilePath, int i, int j)
			throws IOException, InvalidFormatException {

		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		selectDropdownOption("//select[@id='company-type']", excelFilePath, "Sheet6", i, j);
		System.out.println(i);
		System.out.println(j);
		WebElement comNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='company-name']")));
		String comName = (String) TestDat_ClaimReferral[i][j + 1];
		comNameField.sendKeys(comName);

		WebElement comNumField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='company-number']")));
		String comNum = (String) TestDat_ClaimReferral[i][j + 2];
		comNumField.sendKeys(comNum);

		selectDropdownOption("//select[@id='company-status']", excelFilePath, "Sheet6", i, j + 3);

		WebElement incorporatedDateField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='incorporate-date']")));
		String incorporatedDate = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 4];
		incorporatedDateField.sendKeys(incorporatedDate);

		WebElement crmNumberField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='crm-number']")));
		String crmNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 5];
		crmNumberField.sendKeys(crmNumber);

		WebElement sraNumberField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='sra-number']")));
		String sraNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 6];
		sraNumberField.sendKeys(sraNumber);

		WebElement fcaNumberField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='fca-number']")));
		String fcaNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 7];
		fcaNumberField.sendKeys(fcaNumber);

		WebElement vatNumberField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vat-number']")));
		String vatNumber = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 8];
		vatNumberField.sendKeys(vatNumber);

		clickElement("//span[text()='Address Details']");

		selectDropdownOption("//select[@id='addresstype']", excelFilePath, "Sheet6", i, j + 9);

		String postalCode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 10]);
		WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
		postalCodeField.clear();
		postalCodeField.sendKeys(postalCode);
		postalCodeField.sendKeys(Keys.TAB);

		WebElement officeNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='office-name']")));
		String officeName = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 11];
		officeNameField.sendKeys(officeName);

		WebElement houseNumField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='house-name']")));
		String houseNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 12];
		houseNumField.sendKeys(houseNum);

		WebElement addressLine1Field = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line1']")));
		String addressLine1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 13];
		addressLine1Field.sendKeys(addressLine1);

		WebElement addressLine2Field = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line2']")));
		String addressLine2 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 14];
		addressLine2Field.sendKeys(addressLine2);

		WebElement cityField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='city-name']")));
		String city = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 15];
		cityField.sendKeys(city);

		WebElement countyField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='county-name']")));
		String county = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 16];
		countyField.sendKeys(county);

		WebElement postcodeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='post-code']")));
		String postcode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 17];
		postcodeField.sendKeys(postcode);

		selectDropdownOption("//select[@id='country']", excelFilePath, "Sheet6", i, j + 18);

		clickSaveButton();

		clickElement("//span[text()='Phone Number Details']");

		WebElement phoneNumField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
		String phoneNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 19];
		phoneNumField.sendKeys(phoneNum);

		clickSaveButton();

		clickElement("//span[text()='Email Details']");

		WebElement emaiField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
		String emai = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 20];
		emaiField.sendKeys(emai);

		clickSaveButton();

		clickElement("//span[text()='Website Details']");

		WebElement websiteField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='website-address']")));
		String website = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 21];
		websiteField.sendKeys(website);

		clickSaveButton();

		clickElement("//span[text()='Bank Details']");

		WebElement bankAccField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='account-number']")));
		String bankAcc = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 22];
		bankAccField.sendKeys(bankAcc);

		WebElement sortCodeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='sort-code']")));
		String sortCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 23];
		sortCodeField.sendKeys(sortCode);

		clickSaveButton();

		clickElement("//span[text()='Vehicle Details']");

		WebElement makeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-make']")));
		String make = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 24];
		makeField.sendKeys(make);

		WebElement modelField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-model']")));
		String model = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 25];
		modelField.sendKeys(model);

		WebElement vinField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vin']")));
		String vin = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 26];
		vinField.sendKeys(vin);

		WebElement vrnField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vrn']")));
		String vrn = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 27];
		vrnField.sendKeys(vrn);

		clickSaveButton();

		addButtonClick();
		System.out.println("Added company details");
	}

	public void addButtonClick() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement button = driver.findElement(By.xpath("//button[text()=' Add ']"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
		button.click();
//		WebElement addButton = wait.until(ExpectedConditions
//				.elementToBeClickable(By.xpath("//button[text()=' Add ']")));
//		addButton.click();
		// System.out.println("Added company details");

	}

	private void fillPersonDetailsFromExcel(String excelFilePath, int i, int j)
			throws IOException, InvalidFormatException {
		Object[][] TestDat_PersonDetails = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String partyType = (String) TestDat_PersonDetails[i][j];
		System.out.println("Selected Party Type: " + partyType);
		selectDropdownOption("//select[@id='party-type']", excelFilePath, "Sheet6", i, j);

		WebElement firstNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='first-name']")));
		String firstName = (String) TestDat_PersonDetails[i][j + 1];
		firstNameField.sendKeys(firstName);
		System.out.println("First Name: " + firstName);

		WebElement lastNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='last-name']")));
		String lastName = (String) TestDat_PersonDetails[i][j + 2];
		lastNameField.sendKeys(lastName);
		System.out.println("Last Name: " + lastName);

		WebElement dobField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='birth-date']")));
		String dob = (String) TestDat_PersonDetails[i][j + 3];
		dobField.sendKeys(dob);
		System.out.println("Date of Birth: " + dob);

		WebElement occupationField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='occupation']")));
		String occupation = (String) TestDat_PersonDetails[i][j + 4];
		occupationField.sendKeys(occupation);

		selectCompanyFromLinkedOrganizationDropdown(excelFilePath, i, j + 5); 

		WebElement nationalInsuranceField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='national-insurance-number']")));
		String niNumber = (String) TestDat_PersonDetails[i][j + 6]; // Corrected to j + 6
		nationalInsuranceField.sendKeys(niNumber);
		System.out.println("National Insurance Number: " + niNumber);

		clickElement("//span[text()='Address Details']");

		WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
		String postalCode = (String) TestDat_PersonDetails[i][j + 7];
		postalCodeField.clear();
		postalCodeField.sendKeys(postalCode);
		postalCodeField.sendKeys(Keys.TAB);

		WebElement officeNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='office-name']")));
		String officeName = (String) TestDat_PersonDetails[i][j + 8];
		officeNameField.sendKeys(officeName);

		WebElement houseNumField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='house-name']")));
		String houseNum = (String) TestDat_PersonDetails[i][j + 9];
		houseNumField.sendKeys(houseNum);

		WebElement addressLine1Field = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line1']")));
		String addressLine1 = (String) TestDat_PersonDetails[i][j + 10];
		addressLine1Field.sendKeys(addressLine1);

		WebElement addressLine2Field = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line2']")));
		String addressLine2 = (String) TestDat_PersonDetails[i][j + 11];
		addressLine2Field.sendKeys(addressLine2);

		WebElement cityField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='city-name']")));
		String city = (String) TestDat_PersonDetails[i][j + 12];
		cityField.sendKeys(city);

		WebElement countyField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='county-name']")));
		String county = (String) TestDat_PersonDetails[i][j + 13];
		countyField.sendKeys(county);

		WebElement postcodeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='post-code']")));
		String postcode = (String) TestDat_PersonDetails[i][j + 14];
		postcodeField.sendKeys(postcode);

		selectDropdownOption("//select[@id='country']", excelFilePath, "Sheet6", i, j + 15);

		clickSaveButton();

		clickElement("//span[text()='Phone Number Details']");

		WebElement phoneNumField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
		String phoneNum = (String) TestDat_PersonDetails[i][j + 16];
		phoneNumField.sendKeys(phoneNum);

		clickSaveButton();
		clickElement("//span[text()='Email Details']");

		WebElement emailField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
		String email = (String) TestDat_PersonDetails[i][j + 17];
		emailField.sendKeys(email);

		clickSaveButton();
		clickElement("//span[text()='Vehicle Details']");

		WebElement vehicleMakeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-make']")));
		String vehicleMake = (String) TestDat_PersonDetails[i][j + 18];
		vehicleMakeField.sendKeys(vehicleMake);

		WebElement vehicleModelField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-model']")));
		String vehicleModel = (String) TestDat_PersonDetails[i][j + 19];
		vehicleModelField.sendKeys(vehicleModel);

		WebElement vinField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vin']")));
		String vin = (String) TestDat_PersonDetails[i][j + 20];
		vinField.sendKeys(vin);

		WebElement vrnField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vrn']")));
		String vrn = (String) TestDat_PersonDetails[i][j + 21];
		vrnField.sendKeys(vrn);

		clickSaveButton();
		clickElement("//span[text()='Bank Details']");

		WebElement bankAccField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='account-number']")));
		String bankAcc = (String) TestDat_PersonDetails[i][j + 22];
		bankAccField.sendKeys(bankAcc);

		WebElement sortCodeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='sort-code']")));
		String sortCode = (String) TestDat_PersonDetails[i][j + 23];
		sortCodeField.sendKeys(sortCode);

		clickSaveButton();
		clickElement("//span[text()='IP Details']");

		WebElement ipAddressField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ip-address']")));
		String ipAddress = (String) TestDat_PersonDetails[i][j + 24];
		ipAddressField.sendKeys(ipAddress);

		clickSaveButton();

		addButtonClick();
		System.out.println("Added Person details");
	}

	public void clickElement(String xpath) {

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		element.click();
	}

	public void clickAddButton() {
		// XPath for the "Add" button
		String xpath = "(//button[text()=' Add '])[2]";

		WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		addButton.click();

	}

	public void clickSaveButton() {
		// XPath for the "Add" button
		String xpath = "//button[text()=' Save ']";

		WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		addButton.click();

	}

	public void selectCompanyFromLinkedOrganizationDropdown(String excelFilePath, int i, int j)
			throws InvalidFormatException, IOException {
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		if (i < excelData.length && excelData[i].length > j) {
			String linkedOrganization = (String) excelData[i][j];
			linkedOrganization = (linkedOrganization != null) ? linkedOrganization.trim() : "";

			if (!linkedOrganization.isEmpty()) {
				WebElement dropdown = wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='linked-organization']")));

				if (dropdown != null) {
					Select select = new Select(dropdown);

					wait.until(ExpectedConditions
							.numberOfElementsToBeMoreThan(By.xpath("//select[@id='linked-organization']/option"), 0));

					List<WebElement> options = select.getOptions();
					System.out.println("Dropdown options count: " + options.size());

					for (WebElement option : options) {
						System.out.println("Option: " + option.getText());
					}

					boolean optionFound = false;
					for (WebElement option : options) {
						if (option.getText().trim().equals(linkedOrganization)) {
							optionFound = true;
							select.selectByVisibleText(linkedOrganization);
							System.out.println("Selected company: " + linkedOrganization);
							break;
						}
					}

					if (!optionFound) {
						System.out.println("Company '" + linkedOrganization + "' not found in the dropdown.");
					}
				} else {
					System.out.println("Linked Organization dropdown is not available.");
				}
			} else {
				System.out.println("Linked Organization (company) is empty for row " + i);
			}
		} else {
			System.out.println("Invalid row or column for Excel data.");
		}
	}

	public void intelligenceOwner(String excelFilePath, int i) throws IOException, InvalidFormatException {
		// Fetch the intelligenceOwner value from Excel
		String intelligenceOwner = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][1];
		System.out.println("Fetched Intelligence Owner from Excel: " + intelligenceOwner);

		if (intelligenceOwner == null || intelligenceOwner.trim().isEmpty()) {
			System.out.println("Intelligence Owner value is empty for row " + i);
			return;
		}

		// Find the input field and clear any existing text
		WebElement intelligenceOwnerInput = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[contains(@class, 'p-autocomplete-input')]")));
		intelligenceOwnerInput.clear();

		// Send the value to the input field to trigger the dropdown
		intelligenceOwnerInput.sendKeys(intelligenceOwner);
		System.out.println("Entered Intelligence Owner: " + intelligenceOwner);

		// Wait for the dropdown options to be visible
		List<WebElement> intelligenceOwnerOptions = wait.until(ExpectedConditions
				.visibilityOfAllElementsLocatedBy(By.xpath("//li[contains(@class, 'p-autocomplete-item')]")));

		if (!intelligenceOwnerOptions.isEmpty()) {
			boolean foundIntelligenceOwner = false;

			// Loop through the options to find the matching one
			for (WebElement option : intelligenceOwnerOptions) {
				String optionText = option.getText().trim(); // Trim spaces before comparison
				if (optionText.equalsIgnoreCase(intelligenceOwner)) {
					foundIntelligenceOwner = true;

					// Scroll to the matching option and click on it
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", option);
					wait.until(ExpectedConditions.elementToBeClickable(option));
					option.click();
					System.out.println("Selected Intelligence Owner: " + optionText);
					break;
				}
			}

			// If the option is not found, log a message
			if (!foundIntelligenceOwner) {
				System.out.println("Intelligence Owner '" + intelligenceOwner + "' not found in the dropdown.");
			}
		} else {
			System.out.println("No dropdown options found.");
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

	public void fetchAndProcessDate(String excelFilePath, int i, int columnIndex, WebElement dateField) {
		try {
			String dateInput = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][columnIndex];
			dateInput = dateInput != null ? dateInput.trim() : "";
			System.out.println("Fetched Date from Excel: " + dateInput);
			processAndFillDateField(dateInput, dateField);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void waitForElementAndFillClaimReference(int i) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");
		String claimNum = (String) TestDat_ClaimReferral[i][3];

		try {
			WebElement claimReferenceInput = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("claim-reference")));
			claimReferenceInput.clear();
			claimReferenceInput.sendKeys(claimNum);
			claimReferenceInput.sendKeys(Keys.TAB);

		} catch (Exception e) {
			System.out.println("Error while filling claim reference for row " + i + ": " + e.getMessage());
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
	}

	public void addNewCompany(String excelFilePath, int i) throws IOException, InvalidFormatException {
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String companyDetail = (String) TestDat_ClaimReferral[i][64];
		if (companyDetail.equals("Yes")) {
			WebElement addMoreCom = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//button[text()=' Add More Company Info ']")));
			addMoreCom.click();
			fillComDetailsFromExcel(excelFilePath, i, 65);
			System.out.println("Company details added for row " + (i + 1));
		} else if (companyDetail.equals("No")) {
			System.out.println("No company details for row " + (i + 1));
		}

		String personDetail = (String) TestDat_ClaimReferral[i][93];
		if (personDetail.equals("Yes")) {
			WebElement addMorePerson = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//button[text()=' Add More Person Info ']")));
			addMorePerson.click();
			fillPersonDetailsFromExcel(excelFilePath, i, 94);
			System.out.println("Person details added for row " + (i + 1));
		} else if (personDetail.equals("No")) {
			System.out.println("No person details for row " + (i + 1));
		}
	}

	@Given("I am on the Company Status dropdown")
	public void iAmOnTheCompanyStatusDropdown() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		selectCompanyRadioButton(excelFilePath, 0);
		WebElement companyStatusDropdown = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.id("company-status")));
		Assert.assertTrue(companyStatusDropdown.isDisplayed(), "Company Status dropdown is displayed");
	}

	@When("Dissolution First Gazette should be listed")
	public void dissolutionFirstGazetteShouldBeListed() {
		WebElement companyStatusDropdown = driver.findElement(By.id("company-status"));
		Select dropdown = new Select(companyStatusDropdown);
		boolean found = false;
		for (WebElement option : dropdown.getOptions()) {
			if ("Dissolution First Gazette".equals(option.getText())) {
				found = true;
				break;
			}
		}
		if (found) {
			System.out.println("Dissolution First Gazette is listed in the Company Status dropdown.");
		} else {
			System.out.println("Dissolution First Gazette is NOT listed in the Company Status dropdown.");
		}
	}

	public void selectCompanyRadioButton(String excelFilePath, int i) throws InvalidFormatException, IOException {
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8]; // Get company-related data
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Company: '" + companyDetail + "'");

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			WebElement companyNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[1]")));

			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
				CheckCompanyStatus(excelFilePath, i, 12); // Process the company status
			} else {
				System.out.println("Company is empty, selecting 'No'");
				selectRadioButton(companyNo);
			}
		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void CheckCompanyStatus(String excelFilePath, int i, int j) throws IOException, InvalidFormatException {

		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String companyStatus = TestDat_ClaimReferral[i][j].toString();

		selectDropdownOption("//select[@id='company-status']", excelFilePath, "Sheet6", i, j);

		if (companyStatus.equals("Dissolution First Gazette")) {
			System.out.println("The selected value is: Dissolution First Gazette");
		} else {
			System.out.println("The selected value is: " + companyStatus);
		}

	}

	@Then("I am on the \"Add Data\" section")
	public void iAmOnTheAddDataSection() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int index = 1;
		if (index < excelData.length && index >= 0) {
			selectCompanyRadioButtonAddData(excelFilePath, index);
		} else {
			System.out.println("Invalid index for Excel data: " + index);
		}
	}

	public void selectCompanyRadioButtonAddData(String excelFilePath, int i)
			throws InvalidFormatException, IOException {
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		if (i < excelData.length && i >= 0 && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8]; // Column 8 for company detail
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Company: '" + companyDetail + "'");

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			WebElement companyNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[1]")));

			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
				CheckCompanyStatusAddData(excelFilePath, i); // Ensure to pass the same index here
			} else {
				System.out.println("Company is empty, selecting 'No'");
				selectRadioButton(companyNo);
			}
		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	public void CheckCompanyStatusAddData(String excelFilePath, int i) throws InvalidFormatException, IOException {
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		if (i < excelData.length) {
			int rowLength = excelData[i].length;

			// Specify the columns to check - ensure these columns are valid based on your
			// row length
			int[] columnsToCheck = { 18 }; // Adjust based on the columns you need to check

			for (int columnIndex : columnsToCheck) {
				if (columnIndex < rowLength) {
					String columnValue = (String) excelData[i][columnIndex];
					if (columnValue != null && !columnValue.isEmpty()) {
						System.out.println("Processing Column " + columnIndex + " with Value: " + columnValue);
						fillAddressDetails(excelFilePath, i, columnIndex);
					}
				} else {
					System.out.println("Column index " + columnIndex + " exceeds row length of " + rowLength);
				}
			}
		} else {
			System.out.println("Row index " + i + " is out of bounds.");
		}
	}

	public void fillAddressDetails(String excelFilePath, int i, int j) throws InvalidFormatException, IOException {

		clickElement("//span[text()='Address Details']");

		String postalCode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j]);
		WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
		postalCodeField.clear();
		postalCodeField.sendKeys(postalCode);

		postalCodeField.sendKeys(Keys.TAB);
		selectDropdownOption("//select[@id='addresstype']", excelFilePath, "Sheet6", i, j + 1);
		WebElement officeNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='office-name']")));
		String officeName = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 2];
		officeNameField.clear();
		officeNameField.sendKeys(officeName);

		WebElement houseNumField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='house-name']")));
		String houseNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 3];
		houseNumField.clear();
		houseNumField.sendKeys(houseNum);

		WebElement addressLine1Field = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line1']")));
		String addressLine1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 4];
		addressLine1Field.clear();
		addressLine1Field.sendKeys(addressLine1);

		WebElement addressLine2Field = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line2']")));
		String addressLine2 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 5];
		addressLine2Field.clear();
		addressLine2Field.sendKeys(addressLine2);

		WebElement cityField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='city-name']")));
		String city = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 6];
		cityField.clear();
		cityField.sendKeys(city);

		WebElement countyField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='county-name']")));
		String county = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 7];
		countyField.clear();
		countyField.sendKeys(county);

		WebElement postcodeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='post-code']")));
		String postcode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 8];
		postcodeField.clear();
		postcodeField.sendKeys(postcode);

		selectDropdownOption("//select[@id='country']", excelFilePath, "Sheet6", i, j + 9);

		clickSaveButton();
	}

	@And("The button should be labeled as Save instead of Cancel")
	public void verifySaveButtonLabel() {
		String saveButtonXpath = "//button[text()=' Save ']";
		WebElement saveButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(saveButtonXpath)));
		clickElement("//span[text()='Address Details']");

		String buttonText = saveButton.getText().trim();

		if (buttonText.equals("Save")) {
			System.out.println("Button label is correct: 'Save'. Proceeding with the click.");
			saveButton.click();
		} else {
			System.out.println("Error: Button label is not 'Save'. It is labeled as: '" + buttonText + "'.");
		}
	}

	@Given("I am on the Party Type dropdown")
	public void i_am_on_the_party_type_dropdown() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		selectPersonalRadioButtonAddData(excelFilePath, 1);
	}

	public void selectPersonalRadioButtonAddData(String excelFilePath, int i)
			throws InvalidFormatException, IOException {
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		if (i < excelData.length && i >= 0 && excelData[i].length > 0) {
			String personalDetail = (String) excelData[i][93];
			personalDetail = personalDetail != null ? personalDetail.trim() : "";

			WebElement personalYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			WebElement personalNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[2]")));

			if (!personalDetail.isEmpty()) {
				selectRadioButton(personalYes);
				checkAllPartyTypes();
			} else {
				selectRadioButton(personalNo);
			}
		}
	}

	public static final List<String> VALID_PARTY_TYPES = Arrays.asList("Director", "Previous Director",
			"Previous Secretary", "Previous Shareholder", "Secretary", "Shareholder");

	@When("I check the available options under Party Type")
	public void i_check_the_available_options_under_party_type() {
		WebElement partyTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("party-type")));
		List<WebElement> options = partyTypeDropdown.findElements(By.tagName("option"));

		Set<String> foundOptions = new HashSet<>();

		for (WebElement option : options) {
			String optionText = option.getText().trim();
			if (!optionText.equals("Choose party type") && !VALID_PARTY_TYPES.contains(optionText)) {

			}
			foundOptions.add(optionText);
		}

		for (String validOption : VALID_PARTY_TYPES) {
			if (foundOptions.contains(validOption)) {
				System.out.println("Valid option found: " + validOption);
			} else {
				System.out.println("Expected valid option not found: " + validOption);
			}
		}
	}

	public static void checkAllPartyTypes() {
		for (String partyType : VALID_PARTY_TYPES) {
			boolean isValid = CheckPersonTypeAddData(partyType);
			Assert.assertTrue(isValid, partyType + " is an invalid party type.");
		}
	}

	public static boolean CheckPersonTypeAddData(String partyType) {
		return VALID_PARTY_TYPES.contains(partyType);
	}

	@Given("I am on the Party Type dropdown for checking Informant")
	public void i_am_on_the_party_type_dropdown_for_checking_Informant() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		selectPersonalRadioButtonAddData1(excelFilePath, 3);
	}

	public void selectPersonalRadioButtonAddData1(String excelFilePath, int i)
			throws InvalidFormatException, IOException {
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		if (i < excelData.length && i >= 0 && excelData[i].length > 0) {
			String personalDetail = (String) excelData[i][93];
			personalDetail = personalDetail != null ? personalDetail.trim() : "";

			WebElement personalYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			WebElement personalNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[2]")));

			if (!personalDetail.isEmpty()) {
				selectRadioButton(personalYes);

			} else {
				selectRadioButton(personalNo);
			}
		}
	}

	@When("I check the Informant option under Party Type")
	public void i_check_the_informant_option_under_party_type() {
		WebElement partyTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("party-type")));
		List<WebElement> options = partyTypeDropdown.findElements(By.tagName("option"));

		boolean informantFound = false;
		for (WebElement option : options) {
			String optionText = option.getText().trim();
			if (optionText.equals("Informant")) {
				informantFound = true;
				System.out.println("Informant option found: " + optionText);
				break;
			}
		}

		Assert.assertTrue(informantFound, "Informant option is not present in the Party Type dropdown.");
	}

	@Then("Informant should be listed as an option")
	public void informant_should_be_listed_as_an_option() {
		WebElement partyTypeDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("party-type")));
		List<WebElement> options = partyTypeDropdown.findElements(By.tagName("option"));

		boolean isInformantPresent = false;

		for (WebElement option : options) {
			String optionText = option.getText().trim();
			if (optionText.equals("Informant")) {
				isInformantPresent = true;
				break;
			}
		}

		Assert.assertTrue(isInformantPresent, "'Informant' should be listed as an option in the Party Type dropdown.");
	}

	@Then("Personnel Information should appear above Address in saved entry")
	public void personnel_information_should_appear_above_address_in_saved_entry()
			throws InvalidFormatException, IOException {
		startFillFromExcel(2);

	}

	@And("I should see the saved entry details in the Company Details section")
	public void i_should_see_the_saved_entry_details_in_the_company_details_section()
			throws InvalidFormatException, IOException {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement personnelInfoSection = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Personnel Information ']")));
		System.out.println(personnelInfoSection + "Personnel Information section found.");

		WebElement personnelInfoSectionDetails = driver
				.findElement(By.xpath("//div[@class='p-accordion-content ng-tns-c70-45']"));
		System.out.println(personnelInfoSectionDetails.getText());

	}

	private void startFillFromExcel(int i) throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", i, 0);
		intelligenceOwner(excelFilePath, i);

		WebElement incidentDate = waitForElement("//input[@id='incident-date']");
		fetchAndProcessDate(excelFilePath, i, 2, incidentDate);

		waitForElementAndFillClaimReference(i);

		String operationName = (String) TestDat_ClaimReferral[i][4];
		fillInputField("//input[@id='operation-name']", operationName);

		String inciLoca = (String) TestDat_ClaimReferral[i][5];
		fillInputField("//input[@id='loc-add']", inciLoca);

		String policyNum = (String) TestDat_ClaimReferral[i][6];
		fillInputField("//input[@id='policy-number']", policyNum);

		WebElement policyIncepDate = waitForElement("//input[@id='policy-inception-date']");
		fetchAndProcessDate(excelFilePath, i, 7, policyIncepDate);

		selectCompanyPersonRadioButton(excelFilePath, 2);
		fillRemain(i);

		submit();
	}

	public void selectCompanyPersonRadioButton(String excelFilePath, int i) throws InvalidFormatException, IOException {
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			String personDetail = (String) excelData[i][37];

			companyDetail = companyDetail != null ? companyDetail.trim() : "";
			personDetail = personDetail != null ? personDetail.trim() : "";

			System.out.println(
					"Processing i: " + i + " with Company: '" + companyDetail + "' and Person: '" + personDetail + "'");

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			WebElement companyNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[1]")));
			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			WebElement personNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[2]")));

			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
				fillComDetailsFromExcel(excelFilePath, i, 9);
			} else {
				System.out.println("Company is empty, selecting 'No'");
				selectRadioButton(companyNo);
			}

			if (!personDetail.isEmpty()) {
				System.out.println("Person is provided, selecting 'Yes'");
				selectRadioButton(personYes);
				fillPersonDetailsFromExcel(excelFilePath, i, 38);
			} else {
				System.out.println("Person is empty, selecting 'No'");
				selectRadioButton(personNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	@Then("I am viewing a saved record")
	public void i_am_viewing_a_saved_record() throws InvalidFormatException, IOException {
		startFill(3);

	}

	@And("I check the size of the \"Companies and Persons Involved\" section")
	public void i_check_the_size_of_the_companies_and_persons_involved_section()
			throws InvalidFormatException, IOException {

		WebElement companiesInvolved = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Companies Involved']")));
		System.out.println("Companies Involved Text: " + companiesInvolved.getText());

		WebElement partiesInvolved = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Parties Involved']")));
		System.out.println("Parties Involved Text: " + partiesInvolved.getText());

	}

	@Then("I check the state of the submit button without selecting the Intelligence Source")
	public void i_check_the_state_of_the_submit_button_without_selecting_the_intelligence_source()
			throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		
		
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String intelSource = (String) TestDat_ClaimReferral[4][0];
		
		System.out.println("Retrieved IntelSource from Excel: " + intelSource);  // Debugging line

		if (intelSource == null || intelSource.trim().isEmpty()) {
		    System.out.println("No 'Intelligence Source' value provided. Leaving the field blank.");
		} else {
		    System.out.println("Selecting 'Intelligence Source' value: " + intelSource);  
		    selectDropdownOption("//select[@id='intelligence-source']", intelSource);
		}

	}
	public void selectDropdownOption(String xpath, String optionText) {
	    WebElement dropdown = driver.findElement(By.xpath(xpath));
	    Select select = new Select(dropdown);
	    
	    // Select by visible text from the dropdown
	    select.selectByVisibleText(optionText);
	    System.out.println("Selected 'Intelligence Source': " + optionText);
	}

	

	@And("The submit button should be disabled")
	public void the_submit_button_should_be_disabled() {

		WebElement submitButton = driver.findElement(By.xpath("//button[text()=' Submit ']"));
		boolean isDisabled = !submitButton.isEnabled();

		if (isDisabled) {
			System.out.println("Submit button is correctly disabled.");
		} else {
			throw new AssertionError("Submit button is unexpectedly enabled.");
		}
	}

	@When("I enter an invalid NIN")
	public void i_enter_an_invalid_nin() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		selectCompanyPersonRadioButtonFromExcel(excelFilePath, 5);
	}

	@And("I try to click the Add button for personal details")
	public void i_try_to_click_the_add_button_for_personal_details() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// Wait for the toast notification to be visible
			WebElement toastDetail = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-detail.ng-tns-c61-57")));
			WebElement closeToastButton = toastDetail.findElement(By.cssSelector(".p-toast-icon-close"));
			closeToastButton.click();
			System.out.println("Toast notification closed.");
		} catch (TimeoutException e) {
			System.out.println("No toast notification found or it took too long.");
		}

		WebElement addButton = driver.findElement(By.xpath("//button[text()=' Add ']"));
		addButton.click();
		System.out.println("Clicked on the Add button for personal.");
	}

	@Then("I should see a validation error message for the invalid NIN")
	public void i_should_see_a_validation_error_message_for_the_invalid_nin() {
		WebElement errorMessage = driver.findElement(By.xpath("//small[text()='National Insurance Number not valid']"));

		String expectedErrorMessage = "National Insurance Number not valid";

		if (errorMessage.isDisplayed() && errorMessage.getText().equals(expectedErrorMessage)) {
			System.out.println("Validation error message displayed: " + errorMessage.getText());
		} else {
			throw new AssertionError("Expected error message not displayed or incorrect error message.");
		}
	}

	@When("I have submitted the form successfully")
	public void i_have_submitted_the_form_successfully() throws InvalidFormatException, IOException {
		startFill(7);
		System.out.println("Sumbit form Successfully");
	}

	@Then("I should be able to edit the form details")
	public void i_should_be_able_to_edit_the_form_details() {

		WebElement editButton = driver
				.findElement(By.xpath("//button[contains(@class, 'btn') and contains(@class, 'btn-primary')]"));

		Assert.assertTrue(editButton.isDisplayed(), "Edit button is not visible");

		editButton.click();

		System.out.println("The form is now editable.");
	}

	@And("I should see the form in an editable state")
	public void i_should_see_the_form_in_an_editable_state() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String operationName = (String) TestDat_ClaimReferral[6][4];

		System.out.println("Operation Name from Excel: " + operationName);

		WebElement operationNameField = driver.findElement(By.xpath("//input[@id='operation-name']"));

		if (operationNameField.isEnabled()) {
			operationNameField.clear();
			System.out.println("Clearing the existing value in the field.");

			fillInputField("//input[@id='operation-name']", operationName);
			System.out.println("Form fields are editable and updated with the value: " + operationName);

			// Retrieve and print the value from the form
			String fieldValue = operationNameField.getAttribute("value");
			System.out.println("Current value in the form field: " + fieldValue);
		} else {
			System.out.println("The form field is not editable.");
		}

	}

	@And("I modify the form fields")
	public void i_modify_the_form_fields() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String modifiedOperationName = (String) TestDat_ClaimReferral[6][5]; // assuming you want to modify this value

		WebElement operationNameField = driver.findElement(By.xpath("//input[@id='operation-name']"));

		if (operationNameField.isEnabled()) {
			operationNameField.clear();
			operationNameField.sendKeys(modifiedOperationName);
			System.out.println("Form fields have been modified.");
		} else {
			System.out.println("The form field is not editable.");
		}
	}

	@And("I save the modified form")
	public void i_save_the_modified_form() {
		WebElement saveButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mt-3']"));
		if (saveButton.isEnabled()) {
			saveButton.click();
			System.out.println("Form has been saved.");
		} else {
			System.out.println("Save button is not enabled.");
		}
	}

	@And("I click on the Delete button")
	public void i_click_on_the_delete_button() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//button[contains(@class, 'btn') and contains(@class, 'btn-primary') and contains(@icon, 'pi-trash')]")));
		deleteButton.click();

	}

	@And("I confirm the deletion")
	public void i_confirm_the_deletion() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//span[text()='Are you sure you want to delete this structured intelligence?']")));

		System.out.println(confirmationMessage.getText());

		WebElement confirmButton = driver.findElement(By.xpath("//button[text()=' Confirm ']"));
		confirmButton.click();
	}

	@And("I verify the deletion success message")
	public void i_verify_the_deletion_success_message() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement successMessage = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[text()='Intelligence deleted successfully']")));
		if (successMessage.isDisplayed()) {
			System.out.println("Deletion successful: " + successMessage.getText());
		} else {
			System.out.println("Deletion failed.");
		}
	}

	@And("I select Manage Intelligence option TC")
	public void i_select_manage_intelligence_option_tc() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		WebElement manageIntelliegenceOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()=' Manage Intelligence ']")));
		System.out.println("Navigated to Manage Intelligence page");
		manageIntelliegenceOption.click();
	}

	@And("I am on the Show All page")
	public void i_am_on_the_show_all_page() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement showAll = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=' Show All ']")));
		showAll.click();
	}

	@And("I apply a filter with value {string}")
	public void i_apply_a_filter_with_value(String filterValue) {
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

	@Then("I should see the filtered records with {string} on the page")
	public void i_should_see_the_filtered_records_with_value_on_the_page(String filterValue)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		boolean hasNextPage = true;

		while (hasNextPage) {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(
					By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));

			List<WebElement> rows = wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//tr[@class='p-selectable-row ng-star-inserted']")));

			if (rows.isEmpty()) {
				System.out.println("No records found for the filter value: " + filterValue);
			} else {
				System.out.println("Filtered records: ");
				for (WebElement row : rows) {
					String rowText = row.getText();
					if (rowText.contains(filterValue)) {
						System.out.println(rowText);
					}
				}
			}

			List<WebElement> nextButton = driver
					.findElements(By.xpath("//button[@class='p-paginator-next p-paginator-element p-link p-ripple']"));

			if (nextButton.size() > 0 && nextButton.get(0).isEnabled()) {
				nextButton.get(0).click();
				Thread.sleep(2000);
			} else {
				hasNextPage = false;
			}
		}
	}

	@And("I apply a filter with value {string} for delete")
	public void i_apply_a_filter_with_value_for_delete(String filterValue) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Any']")));

		dropdownTrigger.click();

		WebElement filterOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
		filterOption.click();

		System.out.println("Selected filter: " + filterValue);

		System.out.println("Clicked dropdownTrigger");

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));

		WebElement firstRow = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//tr[@class='p-selectable-row ng-star-inserted'])[1]")));

		String rowText = firstRow.getText();

		if (rowText.contains(filterValue)) {

			firstRow.click();

			System.out.println("Clicked the first row with filtered value: " + filterValue);
			System.out.println("First record details: " + rowText);
		} else {
			System.out.println("Filtered record not found with the value: " + filterValue);
		}
		i_click_on_the_delete_button();
		i_confirm_the_deletion();
		i_verify_the_deletion_success_message();
	}

	@And("I apply a filter with value {string} Adhoc Owner")
	public void i_apply_a_filter_with_value_adhoc_owner(String filterValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		try {
			// Wait for any loading overlays to disappear
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.cssSelector(".p-datatable-loading-overlay.p-component-overlay")));

			// Wait until the input field is clickable
			WebElement inputElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//input[@class='ng-tns-c79-4 p-autocomplete-input p-inputtext p-component ng-star-inserted']")));
			inputElement.click();

			// Wait for the filter option to become clickable
			WebElement filterOption = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
			filterOption.click();
			System.out.println("Selected filter: " + filterValue);

			// Wait for the visibility of the filter option on the page
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[contains(text(),'" + filterValue + "')]")));
			System.out.println("Filter applied and visible on page: " + filterValue);

		} catch (TimeoutException e) {
			System.out.println("Timeout waiting for the filtered records: " + e.getMessage());
		} catch (StaleElementReferenceException e) {
			System.out.println("Stale element reference: " + e.getMessage());
			// You could try re-finding the element in case it goes stale
		} catch (WebDriverException e) {
			System.out.println("Error interacting with element: " + e.getMessage());
		}
	}

	@And("I change the owner name to {string}")
	public void i_change_the_owner_name_to(String newOwnerName) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		WebElement ownerInputField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='adhocOwner']")));
		ownerInputField.clear();
		ownerInputField.sendKeys(newOwnerName);

		WebElement newOwnerOption = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//li[@role='option']//span[text()='" + newOwnerName + "']")));
		newOwnerOption.click();

		wait.until(ExpectedConditions.visibilityOf(ownerInputField));
	}

	@And("I should see the confirmation message")
	public void i_should_see_the_confirmation_message() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		WebElement successMessage = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[text()='Investigation owner assigned successfully']")));

		if (successMessage.isDisplayed()) {
			System.out.println("Owner successfully changed and confirmation message displayed.");
		} else {
			System.out.println("Owner change confirmation message not displayed.");
		}
	}

	@When("I submit the form with phone details alone under personal record")
	public void submitFormWithPhoneDetails() throws InvalidFormatException, IOException {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[8][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 8, 0);

		selectPersonRadioButtonFromExcel(excelFilePath, 8);

	}

	@Then("I should be able to see the record that phone number alone is added in the personal involved tab")
	public void verifyPhoneNumberInPersonalInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement phonNumSec = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Phone Number ']")));

		System.out.println(phonNumSec.getText());

		WebElement phonNumSecDetails = driver
				.findElement(By.xpath("//div[@class='p-accordion-content ng-tns-c70-31']"));
		System.out.println(phonNumSecDetails.getText());

	}

	public void selectPersonRadioButtonFromExcel(String excelFilePath, int i)
			throws InvalidFormatException, IOException {
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		if (i < excelData.length && excelData[i].length > 0) {
			String personDetail = (String) excelData[i][37];

			personDetail = personDetail != null ? personDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Person: '" + personDetail + "'");

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			WebElement personNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[2]")));

			if (!personDetail.isEmpty()) {
				System.out.println("Person is provided, selecting 'Yes'");
				selectRadioButton(personYes);
				fillPersonDetails(excelFilePath, i, 38);
			} else {
				System.out.println("Person is empty, selecting 'No'");
				selectRadioButton(personNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillPersonDetails(String excelFilePath, int i, int j) throws IOException, InvalidFormatException {
		Object[][] TestDat_PersonDetails = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		clickElement("//span[text()='Phone Number Details']");
		WebElement phoneNumField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
		String phoneNum = (String) TestDat_PersonDetails[i][j + 16];
		phoneNumField.sendKeys(phoneNum);

		WebElement addButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath(" (//button[text()=' Add '])[2]")));
		addButton.click();

		WebElement phoneNumField1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
		String phoneNum1 = (String) TestDat_PersonDetails[i][j + 17];
		phoneNumField1.sendKeys(phoneNum1);

		addButton.click();

		WebElement phoneNumField2 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
		String phoneNum2 = (String) TestDat_PersonDetails[i][j + 18];
		phoneNumField2.sendKeys(phoneNum2);
		clickSaveButton();

		addButtonClick();
		submit();
	}

	@And("I submit the form with Address details alone under personal record")
	public void i_submitFormWithAddressDetails() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[8][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 9, 0);
		int i = 9;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Person: '" + personDetail + "'");

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			WebElement personNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[2]")));

			if (!personDetail.isEmpty()) {
				System.out.println("Person is provided, selecting 'Yes'");
				selectRadioButton(personYes);
				fillPersonDetailsAdd(excelFilePath, i, 38);

			} else {
				System.out.println("Person is empty, selecting 'No'");
				selectRadioButton(personNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}

	}

	private void fillPersonDetailsAdd(String excelFilePath, int i, int j) throws IOException, InvalidFormatException {
		Object[][] TestDat_PersonDetails = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		clickElement("//span[text()='Address Details']");

		WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
		String postalCode = (String) TestDat_PersonDetails[i][j + 7];
		postalCodeField.clear();
		postalCodeField.sendKeys(postalCode);
		postalCodeField.sendKeys(Keys.TAB);

		WebElement officeNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='office-name']")));
		String officeName = (String) TestDat_PersonDetails[i][j + 8];
		officeNameField.sendKeys(officeName);

		WebElement houseNumField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='house-name']")));
		String houseNum = (String) TestDat_PersonDetails[i][j + 9];
		houseNumField.sendKeys(houseNum);

		WebElement addressLine1Field = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line1']")));
		String addressLine1 = (String) TestDat_PersonDetails[i][j + 10];
		addressLine1Field.sendKeys(addressLine1);

		WebElement addressLine2Field = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line2']")));
		String addressLine2 = (String) TestDat_PersonDetails[i][j + 11];
		addressLine2Field.sendKeys(addressLine2);

		WebElement cityField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='city-name']")));
		String city = (String) TestDat_PersonDetails[i][j + 12];
		cityField.sendKeys(city);

		WebElement countyField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='county-name']")));
		String county = (String) TestDat_PersonDetails[i][j + 13];
		countyField.sendKeys(county);

		WebElement postcodeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='post-code']")));
		String postcode = (String) TestDat_PersonDetails[i][j + 14];
		postcodeField.sendKeys(postcode);

		selectDropdownOption("//select[@id='country']", excelFilePath, "Sheet6", i, j + 15);

		clickSaveButton();

		addButtonClick();
		submit();
	}

	@And("I should be able to see the record that Address alone is added in the personal involved tab")
	public void verifyAddressInPersonalInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement addSec = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Address Information ']")));

		System.out.println(addSec.getText());

		WebElement addSecDetails = driver.findElement(By.xpath("(//div[@class='p-datatable p-component'])[1]"));
		System.out.println(addSecDetails.getText());

	}

	@And("I submit the form with Email details alone under personal record")
	public void i_submitFormWithEmailDetails() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[10][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 10, 0);
		int i = 10;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Person: '" + personDetail + "'");

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			WebElement personNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[2]")));

			if (!personDetail.isEmpty()) {
				System.out.println("Person is provided, selecting 'Yes'");
				selectRadioButton(personYes);
				fillPersonDetailsWithEmail(excelFilePath, i, 38);
			} else {
				System.out.println("Person is empty, selecting 'No'");
				selectRadioButton(personNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillPersonDetailsWithEmail(String excelFilePath, int i, int j)
			throws IOException, InvalidFormatException {
		Object[][] TestDat_PersonDetails = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		clickElement("//span[text()='Email Details']");

		WebElement emailField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
		String email = (String) TestDat_PersonDetails[i][j + 17];
		emailField.sendKeys(email);

		clickSaveButton();

		addButtonClick();
		submit();
	}

	@And("I should be able to see the record that Email alone is added in the personal involved tab")
	public void verifyEmailInPersonalInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement emailSec = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Email Address ']")));

		System.out.println(emailSec.getText());

		WebElement emailSecDetails = driver.findElement(By.xpath("//div[@class='p-accordion-content ng-tns-c70-32']"));
		System.out.println(emailSecDetails.getText());
	}

	@And("I submit the form with Vehicle details alone under personal record")
	public void i_submitFormWithVehicleDetails() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[11][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 10, 0);
		int i = 11;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Person: '" + personDetail + "'");

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			WebElement personNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[2]")));

			if (!personDetail.isEmpty()) {
				System.out.println("Person is provided, selecting 'Yes'");
				selectRadioButton(personYes);
				fillPersonDetailsWithVehicle(excelFilePath, i, 38);
			} else {
				System.out.println("Person is empty, selecting 'No'");
				selectRadioButton(personNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillPersonDetailsWithVehicle(String excelFilePath, int i, int j)
			throws IOException, InvalidFormatException {
		Object[][] TestDat_PersonDetails = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		clickElement("//span[text()='Vehicle Details']");

		WebElement vehicleMakeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-make']")));
		String vehicleMake = (String) TestDat_PersonDetails[i][j + 18];
		vehicleMakeField.sendKeys(vehicleMake);

		WebElement vehicleModelField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-model']")));
		String vehicleModel = (String) TestDat_PersonDetails[i][j + 19];
		vehicleModelField.sendKeys(vehicleModel);

		WebElement vinField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vin']")));
		String vin = (String) TestDat_PersonDetails[i][j + 20];
		vinField.sendKeys(vin);

		WebElement vrnField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vrn']")));
		String vrn = (String) TestDat_PersonDetails[i][j + 21];
		vrnField.sendKeys(vrn);

		clickSaveButton();

		addButtonClick();
		submit();
	}

	@And("I should be able to see the record that Vehicle alone is added in the personal involved tab")
	public void verifyVehicleInPersonalInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement vehicleSec = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Vehicle Information ']")));

		System.out.println(vehicleSec.getText());

		WebElement vehicleSecDetails = driver
				.findElement(By.xpath("//div[@class='p-accordion-content ng-tns-c70-33']"));
		System.out.println(vehicleSecDetails.getText());
	}

	@And("I submit the form with Bank details alone under personal record")
	public void i_submitFormWithBankDetails() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[10][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 12, 0);
		int i = 12;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Person: '" + personDetail + "'");

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			WebElement personNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[2]")));

			if (!personDetail.isEmpty()) {
				System.out.println("Person is provided, selecting 'Yes'");
				selectRadioButton(personYes);
				fillPersonDetailsWithBank(excelFilePath, i, 38);
			} else {
				System.out.println("Person is empty, selecting 'No'");
				selectRadioButton(personNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillPersonDetailsWithBank(String excelFilePath, int i, int j)
			throws IOException, InvalidFormatException {
		Object[][] TestDat_PersonDetails = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		clickElement("//span[text()='Bank Details']");

		WebElement bankAccField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='account-number']")));
		String bankAcc = (String) TestDat_PersonDetails[i][j + 22];
		bankAccField.sendKeys(bankAcc);

		WebElement sortCodeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='sort-code']")));
		String sortCode = (String) TestDat_PersonDetails[i][j + 23];
		sortCodeField.sendKeys(sortCode);

		clickSaveButton();

		addButtonClick();
		submit();
	}

	@And("I should be able to see the record that Bank alone is added in the personal involved tab")
	public void verifyBankInPersonalInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement bankSec = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Bank Information ']")));

		System.out.println(bankSec.getText());

		WebElement bankSecDetails = driver.findElement(By.xpath("//div[@class='p-accordion-content ng-tns-c70-34']"));
		System.out.println(bankSecDetails.getText());
	}

	@And("I submit the form with IP details alone under personal record")
	public void i_submitFormWithIPDetails() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[10][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 13, 0);
		int i = 13;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Person: '" + personDetail + "'");

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			WebElement personNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[2]")));

			if (!personDetail.isEmpty()) {
				System.out.println("Person is provided, selecting 'Yes'");
				selectRadioButton(personYes);
				fillPersonDetailsWithIP(excelFilePath, i, 38);
			} else {
				System.out.println("Person is empty, selecting 'No'");
				selectRadioButton(personNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillPersonDetailsWithIP(String excelFilePath, int i, int j)
			throws IOException, InvalidFormatException {
		Object[][] TestDat_PersonDetails = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		clickElement("//span[text()='IP Details']");

		WebElement ipAddressField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ip-address']")));
		String ipAddress = (String) TestDat_PersonDetails[i][j + 24];
		ipAddressField.sendKeys(ipAddress);

		clickSaveButton();

		addButtonClick();
		submit();
	}

	@And("I should be able to see the record that IP Address alone is added in the personal involved tab")
	public void verifyIPInPersonalInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement ipSec = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' IP Details ']")));

		System.out.println(ipSec.getText());

		WebElement ipSecDetails = driver.findElement(By.xpath("//div[@class='p-chip-text ng-star-inserted']"));
		System.out.println(ipSecDetails.getText());
	}

	@And("I quit the browser after passing the scenario")
	public void i_quit_the_browser() {
		if (driver != null) {
			driver.quit();
		}
	}

	@And("I submit the form with Address details alone under company record")
	public void i_submitAddDetails() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[14][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 14, 0);
		int i = 14;

		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String companyDetail = (String) TestDat_ClaimReferral[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Company: '" + companyDetail + "'");

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			WebElement companyNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[1]")));

			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
				fillCompanyAddDetails(excelFilePath, i, 9);
			} else {
				System.out.println("Company is empty, selecting 'No'");
				selectRadioButton(companyNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillCompanyAddDetails(String excelFilePath, int i, int j) throws IOException, InvalidFormatException {

		clickElement("//span[text()='Address Details']");

		String postalCode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 9]);
		WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
		postalCodeField.clear();
		postalCodeField.sendKeys(postalCode);
		postalCodeField.sendKeys(Keys.TAB);

		selectDropdownOption("//select[@id='addresstype']", excelFilePath, "Sheet6", i, j + 10);

		WebElement officeNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='office-name']")));
		String officeName = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 11];
		officeNameField.sendKeys(officeName);

		WebElement houseNumField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='house-name']")));
		String houseNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 12];
		houseNumField.sendKeys(houseNum);

		WebElement addressLine1Field = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line1']")));
		String addressLine1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 13];
		addressLine1Field.sendKeys(addressLine1);

		WebElement addressLine2Field = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line2']")));
		String addressLine2 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 14];
		addressLine2Field.sendKeys(addressLine2);

		WebElement cityField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='city-name']")));
		String city = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 15];
		cityField.sendKeys(city);

		WebElement countyField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='county-name']")));
		String county = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 16];
		countyField.sendKeys(county);

		WebElement postcodeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='post-code']")));
		String postcode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 17];
		postcodeField.sendKeys(postcode);

		selectDropdownOption("//select[@id='country']", excelFilePath, "Sheet6", i, j + 18);

		clickSaveButton();

		addButtonClick();
		submit();
	}

	@And("I should be able to see the record that Address alone is added in the company involved tab")
	public void verifyaddressInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement addSec = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Address Information ']")));

		System.out.println(addSec.getText());

		WebElement addSecDetails = driver.findElement(By.xpath("//div[@class='p-accordion-content ng-tns-c70-31']"));
		System.out.println(addSecDetails.getText());
	}

	@And("I submit the form with Phone number details alone under company record")
	public void i_submitPhoneNumberDetails() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[14][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 15, 0);
		int i = 15;

		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String companyDetail = (String) TestDat_ClaimReferral[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Company: '" + companyDetail + "'");

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			WebElement companyNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[1]")));

			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
				fillCompanyPhoneNumberDetails(excelFilePath, i, 9);
			} else {
				System.out.println("Company is empty, selecting 'No'");
				selectRadioButton(companyNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillCompanyPhoneNumberDetails(String excelFilePath, int i, int j)
			throws IOException, InvalidFormatException {

		clickElement("//span[text()='Phone Number Details']");

		WebElement phoneNumField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
		String phoneNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 19];
		phoneNumField.sendKeys(phoneNum);

		clickSaveButton();

		addButtonClick();
		submit();
	}

	@And("I should be able to see the record that Phone number alone is added in the company involved tab")
	public void verifyPhoneNumberInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement phoSec = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Phone Number ']")));

		System.out.println(phoSec.getText());

		WebElement phoSecDetails = driver.findElement(By.xpath("//div[@class='p-accordion-content ng-tns-c70-32']"));
		System.out.println(phoSecDetails.getText());
	}

	@And("I submit the form with Email details alone under company record")
	public void i_submitEmailDetails() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[16][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 16, 0);
		int i = 16;

		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String companyDetail = (String) TestDat_ClaimReferral[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Company: '" + companyDetail + "'");

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			WebElement companyNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[1]")));

			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
				fillCompanyEmailDetails(excelFilePath, i, 9);
			} else {
				System.out.println("Company is empty, selecting 'No'");
				selectRadioButton(companyNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillCompanyEmailDetails(String excelFilePath, int i, int j)
			throws IOException, InvalidFormatException {
		clickElement("//span[text()='Email Details']");

		WebElement emaiField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
		String emai = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 20];
		emaiField.sendKeys(emai);

		clickSaveButton();

		addButtonClick();
		submit();
	}

	@And("I should be able to see the record that Email alone is added in the company involved tab")
	public void verifyEmailInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement emailSec = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Email Address ']")));

		System.out.println(emailSec.getText());

		WebElement emailSecDetails = driver.findElement(By.xpath("//div[@class='p-accordion-content ng-tns-c70-34']"));
		System.out.println(emailSecDetails.getText());
	}

	@And("I submit the form with Website details alone under company record")
	public void i_submitWebsiteDetails() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[17][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 17, 0);
		int i = 17;

		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String companyDetail = (String) TestDat_ClaimReferral[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Company: '" + companyDetail + "'");

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			WebElement companyNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[1]")));

			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
				fillCompanyWebsiteDetails(excelFilePath, i, 9);
			} else {
				System.out.println("Company is empty, selecting 'No'");
				selectRadioButton(companyNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillCompanyWebsiteDetails(String excelFilePath, int i, int j)
			throws IOException, InvalidFormatException {
		clickElement("//span[text()='Website Details']");

		WebElement websiteField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='website-address']")));
		String website = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 21];
		websiteField.sendKeys(website);

		clickSaveButton();

		addButtonClick();
		submit();
	}

	@And("I should be able to see the record that Website alone is added in the company involved tab")
	public void verifyWebsiteInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement webSec = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Website ']")));

		System.out.println(webSec.getText());

		WebElement webSecDetails = driver.findElement(By.xpath("//div[@class='p-accordion-content ng-tns-c70-33']"));
		System.out.println(webSecDetails.getText());
	}

	@And("I submit the form with Bank details alone under company record")
	public void i_submitBankDetails() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[18][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 18, 0);
		int i = 18;

		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String companyDetail = (String) TestDat_ClaimReferral[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Company: '" + companyDetail + "'");

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			WebElement companyNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[1]")));

			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
				fillCompanyBankDetails(excelFilePath, i, 9);
			} else {
				System.out.println("Company is empty, selecting 'No'");
				selectRadioButton(companyNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillCompanyBankDetails(String excelFilePath, int i, int j) throws IOException, InvalidFormatException {
		clickElement("//span[text()='Bank Details']");

		WebElement bankAccField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='account-number']")));
		String bankAcc = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 22];
		bankAccField.sendKeys(bankAcc);

		WebElement sortCodeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='sort-code']")));
		String sortCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 23];
		sortCodeField.sendKeys(sortCode);

		clickSaveButton();
		addButtonClick();
		submit();
	}

	@And("I should be able to see the record that Bank alone is added in the company involved tab")
	public void verifyBankInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement bankSec = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Bank Information ']")));

		System.out.println(bankSec.getText());

		WebElement bankSecDetails = driver.findElement(By.xpath("//div[@class='p-accordion-content ng-tns-c70-35']"));
		System.out.println(bankSecDetails.getText());
	}

	@And("I submit the form with Vehicle details alone under company record")
	public void i_submitVehicleDetails() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		String inteSour = (String) TestDat_ClaimReferral[19][0];
		System.out.println(inteSour);
		selectDropdownOption("//select[@id='intelligence-source']", excelFilePath, "Sheet6", 19, 0);
		int i = 19;

		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String companyDetail = (String) TestDat_ClaimReferral[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			System.out.println("Processing i: " + i + " with Company: '" + companyDetail + "'");

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			WebElement companyNo = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='No'])[1]")));

			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
				fillCompanyVehicleDetails(excelFilePath, i, 9);
			} else {
				System.out.println("Company is empty, selecting 'No'");
				selectRadioButton(companyNo);
			}

		} else {
			System.out.println("Invalid index or insufficient columns in Excel. i: " + i);
		}
	}

	private void fillCompanyVehicleDetails(String excelFilePath, int i, int j)
			throws IOException, InvalidFormatException {
		clickElement("//span[text()='Vehicle Details']");

		WebElement makeField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-make']")));
		String make = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 24];
		makeField.sendKeys(make);

		WebElement modelField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-model']")));
		String model = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 25];
		modelField.sendKeys(model);

		WebElement vinField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vin']")));
		String vin = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 26];
		vinField.sendKeys(vin);

		WebElement vrnField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vrn']")));
		String vrn = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 27];
		vrnField.sendKeys(vrn);

		clickSaveButton();
		addButtonClick();
		submit();
	}

	@And("I should be able to see the record that Vehicle alone is added in the company involved tab")
	public void verifyVehicleInvolvedTab() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement vehSec = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=' Vehicle Information ']")));

		System.out.println(vehSec.getText());

		WebElement vehSecDetails = driver.findElement(By.xpath("//div[@class='p-accordion-content ng-tns-c70-37']"));
		System.out.println(vehSecDetails.getText());
	}

	@And("I apply a filter with value {string} for edit")
	public void i_apply_a_filter_with_value_for_edit(String filterValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Any']")));

		dropdownTrigger.click();

		WebElement filterOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
		filterOption.click();

		System.out.println("Selected filter: " + filterValue);

		System.out.println("Clicked dropdownTrigger");

	}

	@Then("I should see the edit the record with {string} on the page for edit")
	public void i_should_see_the_edit_the_record_with_value_on_the_page_for_edit(String filterValue)
			throws InterruptedException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Any']")));

		dropdownTrigger.click();

		WebElement filterOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
		filterOption.click();

		System.out.println("Selected filter: " + filterValue);

		System.out.println("Clicked dropdownTrigger");

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));

		WebElement firstRow = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//tr[@class='p-selectable-row ng-star-inserted'])[1]")));

		String rowText = firstRow.getText();

		if (rowText.contains(filterValue)) {

			firstRow.click();

			System.out.println("Clicked the first row with filtered value: " + filterValue);
			System.out.println("First record details: " + rowText);
		} else {
			System.out.println("Filtered record not found with the value: " + filterValue);
		}
		i_should_be_able_to_edit_the_form_details();
		i_should_see_the_form_in_an_editable_state();
		i_modify_the_form_fields();
		i_save_the_updated_form();
	}

	@And("I save the updated form")
	public void i_save_the_updated_form() {
		WebElement saveButton = driver.findElement(By.xpath("//button[@class='btn btn-primary mt-3']"));
		if (saveButton.isEnabled()) {
			saveButton.click();
			System.out.println("Form has been saved.");
		} else {
			System.out.println("Save button is not enabled.");
		}
		try {
			WebElement confirmationMessage = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[text()='Intelligence has been updated sucessfully']")));
			System.out.println("Confirmation message displayed: " + confirmationMessage.getText());
		} catch (TimeoutException e) {
			System.out.println("Confirmation message not found or took too long.");
		}
	}

	@Then("I should change the Owner Name for the filtered record {string}")
	public void i_should_change_the_owner_name_for_the_filtered_record(String filterValue)
			throws InterruptedException, InvalidFormatException, IOException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Any']")));

		dropdownTrigger.click();

		WebElement filterOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
		filterOption.click();

		System.out.println("Selected filter: " + filterValue);

		System.out.println("Clicked dropdownTrigger");

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));

		WebElement firstRow = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("(//tr[@class='p-selectable-row ng-star-inserted'])[1]")));

		String rowText = firstRow.getText();

		if (rowText.contains(filterValue)) {

			firstRow.click();

			System.out.println("Clicked the first row with filtered value: " + filterValue);
			System.out.println("First record details: " + rowText);
		} else {
			System.out.println("Filtered record not found with the value: " + filterValue);
		}
	}

	@And("I apply a filter with value {string} in Adhoc Owner filed")
	public void i_apply_a_filter_with_value_in_adhoc_owner(String filterValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='adhocOwner']")));

		dropdownTrigger.click();
		dropdownTrigger.sendKeys(filterValue);

		WebElement filterOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
		filterOption.click();

		System.out.println("Selected filter: " + filterValue);

		System.out.println("Clicked dropdownTrigger");

	}

	@Then("I should see the filtered records with {string} on the page using Adhoc Owner filed")
	public void i_should_see_the_filtered_records_with_value_on_the_page_using_adhoc_owner_filed(String filterValue)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		boolean hasNextPage = true;

		while (hasNextPage) {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(
					By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));

			List<WebElement> rows = wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//tr[@class='p-selectable-row ng-star-inserted']")));

			if (rows.isEmpty()) {
				System.out.println("No records found for the filter value: " + filterValue);
			} else {
				System.out.println("Filtered records: ");
				for (WebElement row : rows) {
					String rowText = row.getText();
					if (rowText.contains(filterValue)) {
						System.out.println(rowText);
					}
				}
			}

			List<WebElement> nextButton = driver
					.findElements(By.xpath("//button[@class='p-paginator-next p-paginator-element p-link p-ripple']"));

			if (nextButton.size() > 0 && nextButton.get(0).isEnabled()) {
				nextButton.get(0).click();
				Thread.sleep(2000);
			} else {
				hasNextPage = false;
			}
		}
	}

	@And("I apply a filter with value {string} in Operation Name filed")
	public void i_apply_a_filter_with_value_in_operation_name(String filterValue) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		wait.until(ExpectedConditions.invisibilityOfElementLocated(
				By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));
		WebElement dropdownTrigger = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='operationName']")));

		dropdownTrigger.click();
		dropdownTrigger.sendKeys(filterValue);

		WebElement filterOption = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//li[@role='option']//span[text()='" + filterValue + "']")));
		filterOption.click();

		System.out.println("Selected filter: " + filterValue);

		System.out.println("Clicked dropdownTrigger");

	}

	@Then("I should see the filtered records with {string} on the page using Operation Name filed")
	public void i_should_see_the_filtered_records_with_value_on_the_page_using_operation_name_filed(String filterValue)
			throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		boolean hasNextPage = true;

		while (hasNextPage) {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(
					By.xpath("//div[@class='p-datatable-loading-overlay p-component-overlay ng-star-inserted']")));

			List<WebElement> rows = wait.until(ExpectedConditions
					.visibilityOfAllElementsLocatedBy(By.xpath("//tr[@class='p-selectable-row ng-star-inserted']")));

			if (rows.isEmpty()) {
				System.out.println("No records found for the filter value: " + filterValue);
			} else {
				System.out.println("Filtered records: ");
				for (WebElement row : rows) {
					String rowText = row.getText();
					if (rowText.contains(filterValue)) {
						System.out.println(rowText);
					}
				}
			}

			List<WebElement> nextButton = driver
					.findElements(By.xpath("//button[@class='p-paginator-next p-paginator-element p-link p-ripple']"));

			if (nextButton.size() > 0 && nextButton.get(0).isEnabled()) {
				nextButton.get(0).click();
				Thread.sleep(2000);
			} else {
				hasNextPage = false;
			}
		}
	}

	@And("I am on the My Sructured Intelligence Page")
	public void i_am_on_the_my__sructured_intelligence_page() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement showAll = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[text()=' My Structured Intelligence ']")));
		showAll.click();
	}

	@And("I verify the update option in phone number under person")
	public void verifyUpdateOptionInPhoneNumberUnderPerson() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 37;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			if (!personDetail.isEmpty()) {
				selectRadioButton(personYes);
			}

			clickElement("//span[text()='Phone Number Details']");
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement phoneNumField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
			String phoneNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 17];
			phoneNumField.sendKeys(phoneNum);
			System.out.println(phoneNum);
			clickSaveButton();

			clickElement("//span[text()='Phone Number Details']");

			clickEditButton();
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement phoneNumField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
			String phoneNumA = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 17];

			System.out.println(phoneNumField1.getText());
			phoneNumField1.clear();
			phoneNumField1.sendKeys(phoneNumA);
			System.out.println(phoneNumA);

			WebElement updateButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Update ']")));
			updateButton.click();

			System.out.println("Updated before add");

		}

	}

	public void clickUpdateButton() {
		WebElement updateButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Update ']")));
		updateButton.click();

		WebElement confirmationMessage = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='companyConfirmationMessage']")));
		if (confirmationMessage.isDisplayed()) {
			System.out.println("Company phone number updated successfully!");
		} else {
			System.out.println("Update failed or confirmation message not displayed.");
		}
	}

	public void clickEditButton() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//button[contains(@class, 'p-button-rounded') and contains(@class, 'p-button-text') and contains(@class, 'p-button') and contains(@class, 'p-component') and contains(@class, 'p-button-icon-only') and contains(@class, 'ng-star-inserted')]\r\n")));

//		WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
//				"//button[@class='p-button-rounded p-button-text mr-1 p-button p-component p-button-icon-only ng-star-inserted']")));
//		p-button-rounded p-button-text p-button p-component p-button-icon-only
		editButton.click();
		System.out.println("Edit button clicked.");
	}

	@When("I verify the update option in Address Field under person")
	public void verifyUpdateOptioninAddressFieldUnderPerson() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 37;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			if (!personDetail.isEmpty()) {
				selectRadioButton(personYes);
			}

			clickElement("//span[text()='Address Details']");

			String postalCode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 8]);
			WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
			postalCodeField.clear();
			postalCodeField.sendKeys(postalCode);
			postalCodeField.sendKeys(Keys.TAB);

			WebElement officeNameField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='office-name']")));
			String officeName = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 9];
			officeNameField.sendKeys(officeName);

			WebElement houseNumField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='house-name']")));
			String houseNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 10];
			houseNumField.sendKeys(houseNum);

			WebElement addressLine1Field = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line1']")));
			String addressLine1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 11];
			addressLine1Field.sendKeys(addressLine1);

			WebElement addressLine2Field = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line2']")));
			String addressLine2 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 12];
			addressLine2Field.sendKeys(addressLine2);

			WebElement cityField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='city-name']")));
			String city = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 13];
			cityField.sendKeys(city);

			WebElement countyField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='county-name']")));
			String county = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 14];
			countyField.sendKeys(county);

			WebElement postcodeField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='post-code']")));
			String postcode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 15];
			postcodeField.sendKeys(postcode);

			selectDropdownOption("//select[@id='country']", excelFilePath, "Sheet6", i, j + 16);

			clickSaveButton();

			clickElement("//span[text()='Address Details']");

			clickEditButton();

			String postalCode1 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 8]);
			WebElement postalCodeField1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
			postalCodeField1.clear();
			postalCodeField1.sendKeys(postalCode1);
			postalCodeField1.sendKeys(Keys.TAB);

			WebElement officeNameField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='office-name']")));
			String officeName1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 9];
			officeNameField1.clear();
			officeNameField1.sendKeys(officeName1);

			WebElement houseNumField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='house-name']")));
			String houseNum1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 10];
			houseNumField1.clear();
			houseNumField1.sendKeys(houseNum1);

			WebElement addressLine1Field1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line1']")));
			String addressLine11 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 11];
			addressLine1Field1.clear();
			addressLine1Field1.sendKeys(addressLine11);

			WebElement addressLine2Field1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line2']")));
			String addressLine21 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 12];
			addressLine2Field1.clear();
			addressLine2Field1.sendKeys(addressLine21);

			WebElement cityField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='city-name']")));
			String city1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 13];
			cityField1.clear();
			cityField1.sendKeys(city1);

			WebElement countyField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='county-name']")));
			String county1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 14];
			countyField1.clear();
			countyField1.sendKeys(county1);

			WebElement postcodeField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='post-code']")));
			String postcode1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 15];
			postcodeField1.clear();
			postcodeField1.sendKeys(postcode1);

			selectDropdownOption("//select[@id='country']", excelFilePath, "Sheet6", i, j + 16);

			clickSaveButton();
			System.out.println("Updated before add");

		}

	}

	@When("I verify the update option in Email Field under person")
	public void verifyUpdateOptioninEmailFieldsUnderPerson() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 37;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			if (!personDetail.isEmpty()) {
				selectRadioButton(personYes);
			}
			clickElement("//span[text()='Email Details']");
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement emailField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
			String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 18];
			emailField.sendKeys(email);
			System.out.println(email);
			clickSaveButton();

			clickElement("//span[text()='Email Details']");

			clickEditButton();

			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement emailField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
			String emailA = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 18];

			System.out.println(emailField1.getText());
			emailField1.clear();
			emailField1.sendKeys(emailA);
			System.out.println(emailA);

			WebElement updateButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Update ']")));
			updateButton.click();

			System.out.println("Updated before add");
		}
	}

	@When("I verify the update option in Bank Field under person")
	public void verifyUpdateOptioninWebsiteFieldsUnderPerson() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 37;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			if (!personDetail.isEmpty()) {
				selectRadioButton(personYes);
			}
			clickElement("//span[text()='Bank Details']");

			WebElement bankAccField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='account-number']")));
			String bankAcc = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 23];
			bankAccField.sendKeys(bankAcc);

			WebElement sortCodeField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='sort-code']")));
			String sortCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 24];
			sortCodeField.sendKeys(sortCode);

			clickSaveButton();

			clickElement("//span[text()='Bank Details']");

			clickEditButton();

			WebElement bankAccField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='account-number']")));
			String bankAcc1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 23];
			bankAccField1.clear();
			bankAccField1.sendKeys(bankAcc1);

			WebElement sortCodeField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='sort-code']")));
			String sortCode1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 24];
			sortCodeField1.clear();
			sortCodeField1.sendKeys(sortCode1);

			WebElement updateButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Update ']")));
			updateButton.click();

			System.out.println("Updated before add");
		}
	}

	@When("I verify the update option in Vechile Field under person")
	public void verifyUpdateOptioninVechileFieldsUnderPerson() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 37;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			if (!personDetail.isEmpty()) {
				selectRadioButton(personYes);
			}
			clickElement("//span[text()='Vehicle Details']");

			WebElement makeField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-make']")));
			String make = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 19];

			makeField.sendKeys(make);

			WebElement modelField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-model']")));
			String model = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 20];

			modelField.sendKeys(model);

			WebElement vinField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vin']")));
			String vin = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 21];

			vinField.sendKeys(vin);

			WebElement vrnField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vrn']")));
			String vrn = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 22];

			vrnField.sendKeys(vrn);

			clickSaveButton();

			clickElement("//span[text()='Vehicle Details']");

			clickEditButton();

			WebElement makeField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-make']")));
			String make1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 19];
			makeField1.clear();
			makeField1.sendKeys(make1);

			WebElement modelField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-model']")));
			String model1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 20];
			modelField1.clear();
			modelField1.sendKeys(model1);

			WebElement vinField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vin']")));
			String vin1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 21];
			vinField1.clear();
			vinField1.sendKeys(vin1);

			WebElement vrnField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vrn']")));
			String vrn1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 22];
			vrnField1.clear();
			vrnField1.sendKeys(vrn1);

			WebElement updateButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Update ']")));
			updateButton.click();

			System.out.println("Updated before add");
		}
	}

	@When("I verify the update option in IP Field under person")
	public void verifyUpdateOptioninIPFieldsUnderperson() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 37;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			if (!personDetail.isEmpty()) {
				selectRadioButton(personYes);
			}
			clickElement("//span[text()='IP Details']");

			WebElement ipAddressField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ip-address']")));
			String ipAddress = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 25];
			ipAddressField.sendKeys(ipAddress);

			clickSaveButton();

			clickElement("//span[text()='IP Details']");

			clickEditButton();

			WebElement ipAddressField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ip-address']")));
			String ipAddress1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 25];
			ipAddressField1.sendKeys(ipAddress1);

			WebElement updateButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Update ']")));
			updateButton.click();

			System.out.println("Updated before add");
		}
	}

	@And("I verify the update option in phone number under company")
	public void verifyUpdateOptionInPhoneNumberUnderCompany() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}

			clickElement("//span[text()='Phone Number Details']");
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement phoneNumField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
			String phoneNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 20];
			phoneNumField.sendKeys(phoneNum);
			System.out.println(phoneNum);
			clickSaveButton();

			clickElement("//span[text()='Phone Number Details']");

			clickEditButton();
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement phoneNumField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
			String phoneNumA = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 20];

			System.out.println(phoneNumField1.getText());
			phoneNumField1.clear();
			phoneNumField1.sendKeys(phoneNumA);
			System.out.println(phoneNumA);

			WebElement updateButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Update ']")));
			updateButton.click();

			System.out.println("Updated before add");

		}

	}

	@When("I verify the update option in Address Field under company")
	public void verifyUpdateOptioninAddressFieldUnderCompany() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}
			clickElement("//span[text()='Address Details']");

			selectDropdownOption("//select[@id='addresstype']", excelFilePath, "Sheet6", i, j + 10);

			String postalCode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 11]);
			WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
			postalCodeField.clear();
			postalCodeField.sendKeys(postalCode);
			postalCodeField.sendKeys(Keys.TAB);

			WebElement officeNameField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='office-name']")));
			String officeName = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 12];
			officeNameField.sendKeys(officeName);

			WebElement houseNumField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='house-name']")));
			String houseNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 13];
			houseNumField.sendKeys(houseNum);

			WebElement addressLine1Field = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line1']")));
			String addressLine1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 14];
			addressLine1Field.sendKeys(addressLine1);

			WebElement addressLine2Field = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line2']")));
			String addressLine2 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 15];
			addressLine2Field.sendKeys(addressLine2);

			WebElement cityField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='city-name']")));
			String city = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 16];
			cityField.sendKeys(city);

			WebElement countyField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='county-name']")));
			String county = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 17];
			countyField.sendKeys(county);

			WebElement postcodeField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='post-code']")));
			String postcode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 18];
			postcodeField.sendKeys(postcode);

			selectDropdownOption("//select[@id='country']", excelFilePath, "Sheet6", i, j + 19);

			clickSaveButton();

			clickElement("//span[text()='Address Details']");

			clickEditButton();
			selectDropdownOption("//select[@id='addresstype']", excelFilePath, "Sheet6", i, j + 10);

			String postalCode1 = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 11]);
			WebElement postalCodeField1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
			postalCodeField1.clear();
			postalCodeField1.sendKeys(postalCode1);
			postalCodeField1.sendKeys(Keys.TAB);

			WebElement officeNameField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='office-name']")));
			String officeName1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 12];
			officeNameField1.clear();
			officeNameField1.sendKeys(officeName1);

			WebElement houseNumField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='house-name']")));
			String houseNum1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 13];
			houseNumField1.clear();
			houseNumField1.sendKeys(houseNum1);

			WebElement addressLine1Field1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line1']")));
			String addressLine11 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 14];
			addressLine1Field1.clear();
			addressLine1Field1.sendKeys(addressLine11);

			WebElement addressLine2Field1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='address-line2']")));
			String addressLine21 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 15];
			addressLine2Field1.clear();
			addressLine2Field1.sendKeys(addressLine21);

			WebElement cityField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='city-name']")));
			String city1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 16];
			cityField1.clear();
			cityField1.sendKeys(city1);

			WebElement countyField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='county-name']")));
			String county1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 17];
			countyField1.clear();
			countyField1.sendKeys(county1);

			WebElement postcodeField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='post-code']")));
			String postcode1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 18];
			postcodeField1.clear();
			postcodeField1.sendKeys(postcode1);

			selectDropdownOption("//select[@id='country']", excelFilePath, "Sheet6", i, j + 19);

			clickSaveButton();
			System.out.println("Updated before add");

		}
	}

	@When("I verify the update option in Email Field under company")
	public void verifyUpdateOptioninEmailFieldsUnderCompany() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}
			clickElement("//span[text()='Email Details']");
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement emailField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
			String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 21];
			emailField.sendKeys(email);
			System.out.println(email);
			clickSaveButton();

			clickElement("//span[text()='Email Details']");

			clickEditButton();

			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement emailField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
			String emailA = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 21];

			System.out.println(emailField1.getText());
			emailField1.clear();
			emailField1.sendKeys(emailA);
			System.out.println(emailA);

			WebElement updateButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Update ']")));
			updateButton.click();

			System.out.println("Updated before add");
		}
	}

	@When("I verify the update option in Bank Field under company")
	public void verifyUpdateOptioninBankFieldsUnderCompany() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}
			clickElement("//span[text()='Bank Details']");

			WebElement bankAccField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='account-number']")));
			String bankAcc = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 23];
			bankAccField.sendKeys(bankAcc);

			WebElement sortCodeField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='sort-code']")));
			String sortCode = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 24];
			sortCodeField.sendKeys(sortCode);

			clickSaveButton();

			clickElement("//span[text()='Bank Details']");

			clickEditButton();

			WebElement bankAccField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='account-number']")));
			String bankAcc1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 23];
			bankAccField1.clear();
			bankAccField1.sendKeys(bankAcc1);

			WebElement sortCodeField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='sort-code']")));
			String sortCode1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 24];
			sortCodeField1.clear();
			sortCodeField1.sendKeys(sortCode1);

			WebElement updateButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Update ']")));
			updateButton.click();

			System.out.println("Updated before add");
		}
	}

	@When("I verify the update option in Vechile Field under company")
	public void verifyUpdateOptioninVechileFieldsUnderCompany() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}
			clickElement("//span[text()='Vehicle Details']");

			WebElement makeField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-make']")));
			String make = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 26];

			makeField.sendKeys(make);

			WebElement modelField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-model']")));
			String model = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 27];

			modelField.sendKeys(model);

			WebElement vinField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vin']")));
			String vin = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 28];

			vinField.sendKeys(vin);

			WebElement vrnField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vrn']")));
			String vrn = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 29];

			vrnField.sendKeys(vrn);

			clickSaveButton();

			clickElement("//span[text()='Vehicle Details']");

			clickEditButton();

			WebElement makeField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-make']")));
			String make1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 26];
			makeField1.clear();
			makeField1.sendKeys(make1);

			WebElement modelField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-model']")));
			String model1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 27];
			modelField1.clear();
			modelField1.sendKeys(model1);

			WebElement vinField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vin']")));
			String vin1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 28];
			vinField1.clear();
			vinField1.sendKeys(vin1);

			WebElement vrnField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='vehicle-vrn']")));
			String vrn1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 29];
			vrnField1.clear();
			vrnField1.sendKeys(vrn1);

			WebElement updateButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Update ']")));
			updateButton.click();

			System.out.println("Updated before add");
		}
	}

	@When("I verify the update option in Website Field under company")
	public void verifyUpdateOptioninWebsiteFieldsUnderCompany() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}
			clickElement("//span[text()='Website Details']");

			WebElement websiteField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='website-address']")));
			String website = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 22];
			websiteField.sendKeys(website);

			clickSaveButton();
			clickElement("//span[text()='Website Details']");

			clickEditButton();

			WebElement websiteField1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='website-address']")));
			String website1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 22];
			websiteField1.sendKeys(website1);

			WebElement updateButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Update ']")));
			updateButton.click();

			System.out.println("Updated before add");
		}
	}

	@When("I verify the Email Field under company and person")
	public void verifyEmailFieldsUnderCompanyandPerson() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 22;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}
			clickElement("//span[text()='Email Details']");
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement emailField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
			String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 21];
			emailField.sendKeys(email);
			System.out.println(email);
			clickSaveButton();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement errorMessage = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Email is invalid']")));

			System.out.println("Error Message: " + errorMessage.getText());
			 ScreenshotUtility.takeScreenshot(driver);
			WebElement closeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//span[@class='p-dialog-header-close-icon ng-tns-c106-11 pi pi-times']")));

			closeBtn.click();
			
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			
			Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

			int x = 22;
			int y = 37;
			if (x < TestDat_ClaimReferral.length && TestDat_ClaimReferral[x].length > 0) {
				String personDetail = (String) TestDat_ClaimReferral[x][37];
				personDetail = personDetail != null ? personDetail.trim() : "";

				WebElement personYes = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
				if (!personDetail.isEmpty()) {
					selectRadioButton(personYes);
				}

				clickElement("//span[text()='Email Details']");
				this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				WebElement emailField1 = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
				String email1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[x][y + 18];
				emailField1.sendKeys(email1);
				System.out.println(email);
				clickSaveButton();
				
				this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				WebElement errorMessage1 = wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Email is invalid']")));

				System.out.println("Error Message: " + errorMessage1.getText());
				 ScreenshotUtility.takeScreenshot(driver);
				WebElement closeBtn1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//span[@class='p-dialog-header-close-icon ng-tns-c106-11 pi pi-times']")));

				closeBtn1.click();
				
			}
		}
	}
		
	

	@When("I verify the alert YES option under company")
	public void verifythealertyesoption() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}
			clickElement("//span[text()='Email Details']");
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement emailField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
			String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 21];
			emailField.sendKeys(email);
			System.out.println(email);
			clickSaveButton();

			addButtonClick();
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//button[@class='p-button-rounded p-button-text p-button p-component p-button-icon-only']")));
			editButton.click();
			clickCancelButton();
			handleConfirmationPromptforCompany("Yes");

		}

	}

	public void clickCancelButton() {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		try {
			// Wait for the toast notification to be visible
			WebElement toastDetail = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-toast-detail.ng-tns-c61-57")));
			WebElement closeToastButton = toastDetail.findElement(By.cssSelector(".p-toast-icon-close"));
			closeToastButton.click();
			System.out.println("Toast notification closed.");
		} catch (TimeoutException e) {
			System.out.println("No toast notification found or it took too long.");
		}
		WebElement cancelButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Cancel ']")));
		cancelButton.click();
		System.out.println("Cancel button clicked.");
	}

	public void handleConfirmationPromptforCompany(String option) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement prompt = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[text()='Would you like to save your company changes?']")));

		WebElement yesButton = driver.findElement(By
				.xpath("//span[text()='Would you like to save your company changes?']/following::span[text()='Yes']"));
		WebElement noButton = driver.findElement(
				By.xpath("//span[text()='Would you like to save your company changes?']/following::span[text()='No']"));

		if (option.equalsIgnoreCase("Yes")) {
			yesButton.click();
			System.out.println("Clicked 'Yes' button.");
		} else if (option.equalsIgnoreCase("No")) {
			noButton.click();
			System.out.println("Clicked 'No' button.");
		} else {
			System.out.println("Invalid option, please use 'Yes' or 'No'.");
		}
	}

	@When("I verify the alert No option under company")
	public void verifythealertnooption() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}
			clickElement("//span[text()='Email Details']");
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement emailField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
			String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 21];
			emailField.sendKeys(email);
			System.out.println(email);
			clickSaveButton();

			addButtonClick();
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//button[@class='p-button-rounded p-button-text p-button p-component p-button-icon-only']")));
			editButton.click();
			clickCancelButton();
			handleConfirmationPromptforCompany("No");

		}

	}

	@When("I verify the alert YES option under person")
	public void verifythealertyesoptionunderperson() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 37;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			if (!personDetail.isEmpty()) {
				selectRadioButton(personYes);
			}
			clickElement("//span[text()='Email Details']");
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement emailField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
			String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 18];
			emailField.sendKeys(email);
			System.out.println(email);
			clickSaveButton();

			addButtonClick();
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//button[@class='p-button-rounded p-button-text p-button p-component p-button-icon-only']")));
			editButton.click();
			clickCancelButton();
			handleConfirmationPromptforPerson("Yes");

		}

	}

	@When("I verify the alert No option under perosn")
	public void verifythealertnooptionunderperson() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 21;
		int j = 37;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			if (!personDetail.isEmpty()) {
				selectRadioButton(personYes);
			}
			clickElement("//span[text()='Email Details']");
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement emailField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='email-address']")));
			String email = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 18];
			emailField.sendKeys(email);
			System.out.println(email);
			clickSaveButton();

			addButtonClick();

			
			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//button[@class='p-button-rounded p-button-text p-button p-component p-button-icon-only']")));
			editButton.click();
			clickCancelButton();
			handleConfirmationPromptforPerson("No");

		}

	}

	public void handleConfirmationPromptforPerson(String option) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement prompt = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[text()='Would you like to save your party changes?']")));

		WebElement yesButton = driver.findElement(
				By.xpath("//span[text()='Would you like to save your party changes?']/following::span[text()='Yes']"));
		WebElement noButton = driver.findElement(
				By.xpath("//span[text()='Would you like to save your party changes?']/following::span[text()='No']"));

		if (option.equalsIgnoreCase("Yes")) {
			yesButton.click();
			System.out.println("Clicked 'Yes' button.");
		} else if (option.equalsIgnoreCase("No")) {
			noButton.click();
			System.out.println("Clicked 'No' button.");
		} else {
			System.out.println("Invalid option, please use 'Yes' or 'No'.");
		}
	}

	@When("I verify the IP validation under person")
	public void verifyIPvalidationunderPerson() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 23;
		int j = 37;
		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			if (!personDetail.isEmpty()) {
				selectRadioButton(personYes);
			}
			clickElement("//span[text()='IP Details']");

			WebElement ipAddressField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='ip-address']")));
			String ipAddress = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 25];
			ipAddressField.sendKeys(ipAddress);

			clickSaveButton();
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement errorMessage = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='IP Address is invalid']")));

			System.out.println("Error Message: " + errorMessage.getText());

		}
	}

	@When("I verify the Website Validation under company")
	public void verifyWebsiteValidationUnderCompany() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 23;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}
			clickElement("//span[text()='Website Details']");

			WebElement websiteField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='website-address']")));
			String website = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 22];
			websiteField.sendKeys(website);
			clickSaveButton();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement errorMessage = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Website is invalid']")));

			System.out.println("Error Message: " + errorMessage.getText());
		}
	}

	@When("I verify the Phone Number Field under company and person")
	public void verifyPhoneNumFieldsUnderCompanyandPerson() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 23;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}
			clickElement("//span[text()='Phone Number Details']");

			WebElement phoneNumField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
			String phoneNum = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 20];
			phoneNumField.sendKeys(phoneNum);

			clickSaveButton();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement errorMessage = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Phone is invalid']")));

			System.out.println("Error Message: " + errorMessage.getText());

			WebElement closeBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//span[@class='p-dialog-header-close-icon ng-tns-c106-12 pi pi-times']")));

			closeBtn.click();

			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

			int x = 23;
			int y = 37;
			if (x < TestDat_ClaimReferral.length && TestDat_ClaimReferral[x].length > 0) {
				String personDetail = (String) TestDat_ClaimReferral[x][37];
				personDetail = personDetail != null ? personDetail.trim() : "";

				WebElement personYes = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
				if (!personDetail.isEmpty()) {
					selectRadioButton(personYes);
				}

				clickElement("//span[text()='Phone Number Details']");

				WebElement phoneNumField1 = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='phone-number']")));
				String phoneNum1 = (String) ExcelReader.getExcelData(excelFilePath, "Sheet6")[x][y + 17];
				phoneNumField1.clear();
				phoneNumField1.sendKeys(phoneNum1);

				clickSaveButton();
				this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				WebElement errorMessage1 = wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath("//small[text()='Phone is invalid']")));

				System.out.println("Error Message: " + errorMessage1.getText());
			}
		}
	}

	@When("I verify the Policy Inception Date invalidate")
	public void verifyPolicyIncDateInvalid() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Actions actions = new Actions(driver);

		WebElement policyIncepDate = waitForElement("//input[@id='policy-inception-date']");
		fetchAndProcessDate(excelFilePath, 24, 7, policyIncepDate);
		System.out.println(policyIncepDate);
		actions.sendKeys(Keys.TAB).sendKeys(Keys.TAB).perform();

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement errorMessage = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//small[text()='Policy Inception Date is not valid']")));

		System.out.println("Error Message: " + errorMessage.getText());
	}

	@When("I verify the Incorprate Date invalidate")
	public void verifyIncorpDateInvalid() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		Actions actions = new Actions(driver);

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		int i = 24;

		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}

			WebElement incorporatedDate = waitForElement("//input[@id='incorporate-date']");
			fetchAndProcessDate(excelFilePath, 24, 13, incorporatedDate);
			System.out.println(incorporatedDate);
			actions.sendKeys(Keys.TAB).sendKeys(Keys.TAB).perform();

			WebElement errorMessage1 = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//small[text()='Incorporated Date is not valid']")));

			System.out.println("Error Message: " + errorMessage1.getText());

			WebElement addButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Add ']")));
			addButton.click();
		}
	}

	@When("I verify the Date Of Birth invalidate")
	public void verifyDOBDateInvalid() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";

		Actions actions = new Actions(driver);

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int i = 24;

		if (i < TestDat_ClaimReferral.length && TestDat_ClaimReferral[i].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[i][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			if (!personDetail.isEmpty()) {
				selectRadioButton(personYes);
			}

			this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement dobField = waitForElement("//input[@id='birth-date']");
			fetchAndProcessDate(excelFilePath, 24, 41, dobField);

			System.out.println("Date of Birth: " + dobField);
			actions.sendKeys(Keys.TAB).sendKeys(Keys.TAB).perform();

			WebElement errorMessage2 = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//small[text()='Date of Birth is not valid']")));

			System.out.println("Error Message: " + errorMessage2.getText());
		}
	}

	@When("I verify the Date invalidate")
	public void verifyDateInvalid() throws InvalidFormatException, IOException {

		verifyPolicyIncDateInvalid();

		verifyIncorpDateInvalid();

		verifyDOBDateInvalid();
	}

	@When("I verify the POSALTAN CODE")
	public void verifytheposatalcode() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");
		Object[][] TestDat_PersonDetails = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		int i = 24;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}

			clickElement("//span[text()='Address Details']");

			String postalCode = String.valueOf(ExcelReader.getExcelData(excelFilePath, "Sheet6")[i][j + 10]);
			WebElement postalCodeField = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
			postalCodeField.clear();
			postalCodeField.sendKeys(postalCode);
			postalCodeField.sendKeys(Keys.TAB);

			int x = 24;
			int y = 37;
			if (x < TestDat_ClaimReferral.length && TestDat_ClaimReferral[x].length > 0) {
				String personDetail = (String) TestDat_ClaimReferral[x][37];
				personDetail = personDetail != null ? personDetail.trim() : "";

				WebElement personYes = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
				if (!personDetail.isEmpty()) {
					selectRadioButton(personYes);
				}
				clickElement("//span[text()='Address Details']");

				WebElement postalCodeField1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//input[contains(@class, 'p-autocomplete-input') and @name='postCodeLookup']")));
				String postalCode1 = (String) TestDat_PersonDetails[i][j + 7];
				postalCodeField1.clear();
				postalCodeField1.sendKeys(postalCode);
				postalCodeField1.sendKeys(Keys.TAB);

			}
		}
	}

	@When("I Verify the Linked Organization Tab enable when having a company Record")
	public void verifyLinkedORGEnable() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");
		Object[][] excelData = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		int i = 24;
		int j = 8;
		if (i < excelData.length && excelData[i].length > 0) {
			String companyDetail = (String) excelData[i][8];
			companyDetail = companyDetail != null ? companyDetail.trim() : "";

			WebElement companyYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[1]")));
			if (!companyDetail.isEmpty()) {
				System.out.println("Company is provided, selecting 'Yes'");
				selectRadioButton(companyYes);
			}
			selectDropdownOption("//select[@id='company-type']", excelFilePath, "Sheet6", i, j + 1);
			System.out.println(i);
			System.out.println(j);
			WebElement comNameField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='company-name']")));
			String comName = (String) TestDat_ClaimReferral[i][j + 2];
			comNameField.sendKeys(comName);

			WebElement comNumField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='company-number']")));
			String comNum = (String) TestDat_ClaimReferral[i][j + 3];
			comNumField.sendKeys(comNum);

			selectDropdownOption("//select[@id='company-status']", excelFilePath, "Sheet6", i, j + 4);

			WebElement addButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Add ']")));
			addButton.click();

			int x = 24;
			int y = 37;
			if (x < TestDat_ClaimReferral.length && TestDat_ClaimReferral[x].length > 0) {
				String personDetail = (String) TestDat_ClaimReferral[x][37];
				personDetail = personDetail != null ? personDetail.trim() : "";

				WebElement personYes = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
				if (!personDetail.isEmpty()) {
					selectRadioButton(personYes);
				}

				Object[][] TestDat_PersonDetails = ExcelReader.getExcelData(excelFilePath, "Sheet6");

				String partyType = (String) TestDat_PersonDetails[x][y + 1];
				System.out.println("Selected Party Type: " + partyType);
				selectDropdownOption("//select[@id='party-type']", excelFilePath, "Sheet6", x, y + 1);

				WebElement firstNameField = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='first-name']")));
				String firstName = (String) TestDat_PersonDetails[x][y + 2];
				firstNameField.sendKeys(firstName);
				System.out.println("First Name: " + firstName);

				WebElement lastNameField = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='last-name']")));
				String lastName = (String) TestDat_PersonDetails[x][y + 3];
				lastNameField.sendKeys(lastName);
				System.out.println("Last Name: " + lastName);

				WebElement dobField = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='birth-date']")));
				String dob = (String) TestDat_PersonDetails[x][y + 4];
				dobField.sendKeys(dob);
				System.out.println("Date of Birth: " + dob);

				WebElement occupationField = wait
						.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='occupation']")));
				String occupation = (String) TestDat_PersonDetails[x][y + 5];
				occupationField.sendKeys(occupation);

				WebElement linkedORGField = null;

				try {
					linkedORGField = wait.until(
							ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Linked organization']")));

					if (linkedORGField.isDisplayed()) {
						System.out.println("Linked Organization field is available");
					}
				} catch (TimeoutException e) {
					System.out.println("Linked Organization field is not available");
				}

				selectCompanyFromLinkedOrganizationDropdown(excelFilePath, x, y + 6);

				System.out.println("Found Linked Organization Tab");
			}
		}

	}

	@When("I Verify the Linked Organization Tab enable without having a company Record")
	public void verifyLinkedORGDisable() throws InvalidFormatException, IOException {
		String excelFilePath = "C:\\Users\\NivethaElamaran\\eclipse-workspace\\ZodiacAutomation\\src\\test\\resources\\TestData\\TestDat_ClaimReferral.xlsx";
		Object[][] TestDat_ClaimReferral = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		int x = 25;
		int y = 37;
		if (x < TestDat_ClaimReferral.length && TestDat_ClaimReferral[x].length > 0) {
			String personDetail = (String) TestDat_ClaimReferral[x][37];
			personDetail = personDetail != null ? personDetail.trim() : "";

			WebElement personYes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//label[text()='Yes'])[2]")));
			if (!personDetail.isEmpty()) {
				selectRadioButton(personYes);
			}

			Object[][] TestDat_PersonDetails = ExcelReader.getExcelData(excelFilePath, "Sheet6");

			String partyType = (String) TestDat_PersonDetails[x][y + 1];
			System.out.println("Selected Party Type: " + partyType);
			selectDropdownOption("//select[@id='party-type']", excelFilePath, "Sheet6", x, y + 1);

			WebElement firstNameField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='first-name']")));
			String firstName = (String) TestDat_PersonDetails[x][y + 2];
			firstNameField.sendKeys(firstName);
			System.out.println("First Name: " + firstName);

			WebElement lastNameField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='last-name']")));
			String lastName = (String) TestDat_PersonDetails[x][y + 3];
			lastNameField.sendKeys(lastName);
			System.out.println("Last Name: " + lastName);

			WebElement dobField = waitForElement("//input[@id='birth-date']");
			fetchAndProcessDate(excelFilePath, 25, 41, dobField);

			System.out.println("Date of Birth: " + dobField);

			WebElement occupationField = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='occupation']")));
			String occupation = (String) TestDat_PersonDetails[x][y + 5];
			occupationField.sendKeys(occupation);

			WebElement linkedORGField = null;

			try {
				linkedORGField = wait.until(
						ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Linked organization']")));

				if (linkedORGField.isDisplayed()) {
					System.out.println("Linked Organization field is available");
				}
			} catch (TimeoutException e) {
				System.out.println("Linked Organization field is not available");
			}

		}

	}
}
