package com.stepdefinitions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.ExcelReader;

public class CreateIntelligence {

	private WebDriver driver;
	private WebDriverWait wait;

	@Given("I am on the Zodiac login page for Create Intelligence")
	public void i_am_on_the_zodiac_login_page_for_create_intelligence() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://zodiacdev.toolagen.co.uk/");
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("okta-signin-username")));
		System.out.println("I am on the Zodiac login page");
	}

	@And("I enter a valid username and password from Excel for Create Intelligence")
	public void i_enter_a_valid_username_and_password_from_excel_for_create_intelligence()
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

	@When("I click on the login button for Create Intelligence")
	public void i_click_on_the_login_button_for_create_intelligence() {
		WebElement loginBtn = driver.findElement(By.xpath("//input[@id='okta-signin-submit']"));
		loginBtn.click();
		System.out.println("Clicked the login button");
		System.out.println("Successfully redirected to the Dashboard");
	}

	@Then("I click the Intelligence option")
	public void i_click_the_intelligence_option() {
		WebElement intelligence = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Intelligence']")));
		intelligence.click();
		System.out.println("Intelligence option clicked");
	}

	@And("I select Create Intelligence option")
	public void i_select_create_intelligence_option() {
		WebElement createIntelliegenceOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/library/add-intelligence']")));
		createIntelliegenceOption.click();
		System.out.println("Navigated to Create Intelligence page");
	}

	@And("I select Structured option")
	public void i_select_structured_option() {
		WebElement structured = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("//p[text()=' Data points which can be added to a structured set of form fields ']")));
		structured.click();
		System.out.println("Select Structured");
	}

	@Then("I fill the Structured form with test data from Excel")
	public void i_fill_the_structured_form_with_test_data_from_excel()
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

		// Close the toast notification if it exists
		try {
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

		WebElement notes = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='notes']")));
		String comNum = (String) TestDat_ClaimReferral[i][63];
		notes.sendKeys("Test Note" + comNum);
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
		System.out.println("Added company details");

	}

	private void fillPersonDetailsFromExcel(String excelFilePath, int i, int j)
			throws IOException, InvalidFormatException {
		Object[][] TestDat_PersonDetails = ExcelReader.getExcelData(excelFilePath, "Sheet6");

		selectDropdownOption("//select[@id='party-type']", excelFilePath, "Sheet6", i, j);

		WebElement firstNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='first-name']")));
		String firstName = (String) TestDat_PersonDetails[i][j + 1];
		firstNameField.sendKeys(firstName);

		WebElement lastNameField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='last-name']")));
		String lastName = (String) TestDat_PersonDetails[i][j + 2];
		lastNameField.sendKeys(lastName);

		WebElement dobField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='birth-date']")));
		String dob = (String) TestDat_PersonDetails[i][j + 3];
		dobField.sendKeys(dob);

		WebElement occupationField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='occupation']")));
		String occupation = (String) TestDat_PersonDetails[i][j + 4];
		occupationField.sendKeys(occupation);

		selectCompanyFromLinkedOrganizationDropdown(excelFilePath, i, j + 5); // Corrected to j + 5

		WebElement nationalInsuranceField = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='national-insurance-number']")));
		String niNumber = (String) TestDat_PersonDetails[i][j + 6]; // Corrected to j + 6
		nationalInsuranceField.sendKeys(niNumber);

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

}
