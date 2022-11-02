package pages;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import helpers.ParseCSV;
import helpers.WaitAction;
import helpers.ParseJson;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Date;

import org.json.JSONObject;

public class ContactPage {
    /**
     * Locators are set as a By variable
     * Ensures no code duplication when locators are used in multiple methods
     **/
    private By emailField = By.cssSelector("section.form-fields input[type='email']");
    private By messageField = By.cssSelector("textarea.form-control");
    private By selectSubject = By.name("id_contact");
    private By sendButton = By.cssSelector("input[name='submitMessage']");
    private By successfullySendMessage = By.cssSelector("div.alert-success");

    private WebDriver driver;

    private JSONObject JsonTestDataObject;
    private HashMap<String, String> CSVTestDataMap;
    private CSVReader csvReader;
    private String[] csvCell;

    public String composedMessage;

    public ContactPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillInContactForm(){
        /**
         * wait for an element to be visible
         **/
        WaitAction.waitForElementVisibility(driver, emailField);


        /**
         * Interaction with a select box element
         **/
        Select selectSubjectBox = new Select(driver.findElement(selectSubject));
        selectSubjectBox.selectByVisibleText("Customer service");

        /**
         * Interaction with an input element
         **/
        driver.findElement(emailField).sendKeys("test@tester.nl");

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        composedMessage = "Eat this Lorem " + formatter.format(date);
        System.out.println("Composed message: " + composedMessage);
        driver.findElement(messageField).sendKeys(composedMessage);

        /**
         * Interaction with a button element
         **/
        driver.findElement(sendButton).click();

        /**
         * wait for an element to be invisible
         **/
        WaitAction.waitForElementInvisibility(driver, emailField);
    }

    /**
     * @param dataFileName name of the JSON file to be used
     * @param bodyName name of the body in the JSON file, this parent body contains all the information in the JSON file
     *                 that we need to fill in the form. Using a parent body to store the information improves
     *                 readability and re-usability
     */
    public void fillInContactFormFromJson(String dataFileName, String bodyName) throws IOException {
        /**
         * JSON object which contains the test data
         **/
        JsonTestDataObject = ParseJson.getJsonAsObject("src/test/resources/testdata/contactform/" + dataFileName +".json");

        WaitAction.waitForElementVisibility(driver, emailField);

        Select selectSubjectBox = new Select(driver.findElement(selectSubject));
        /**
         * Parse the JSON object to retrieve the subject and select on the page
         **/
        selectSubjectBox.selectByVisibleText(JsonTestDataObject.getJSONObject(bodyName)
                .getString("subject"));

        /**
         * Parse the JSON object to retrieve the email&&message and fill it in on the page
         **/
        driver.findElement(emailField).sendKeys(JsonTestDataObject.getJSONObject(bodyName)
                .getString("emailAddress"));
        driver.findElement(messageField).sendKeys(JsonTestDataObject.getJSONObject(bodyName)
                .getString("message"));

        driver.findElement(sendButton).click();

        if(!dataFileName.equals("incorrectdata")) {
            WaitAction.waitForElementInvisibility(driver, emailField);
        }
    }

    /**
     * @param dataFileName name of the CSV file to be used
     */
    public void fillInContactFormFromCSV(String dataFileName) throws IOException {
        /**
         * CSV Map which contains the test data in the syntax format similar to a JSON file
         **/
        CSVTestDataMap = ParseCSV.getCsvAsMap("src/test/resources/testdata/contactform/" + dataFileName + ".csv");

        WaitAction.waitForElementVisibility(driver, emailField);

        Select selectSubjectBox = new Select(driver.findElement(selectSubject));
        /**
         * Parse the CSV Map to retrieve the subject and select on the page
         **/
        selectSubjectBox.selectByVisibleText(CSVTestDataMap.get("subject"));

        /**
         * Parse the CSV Map to retrieve the email&&message and fill it in on the page
         **/
        driver.findElement(emailField).sendKeys(CSVTestDataMap.get("emailAddress"));
        driver.findElement(messageField).sendKeys(CSVTestDataMap.get("message"));

        driver.findElement(sendButton).click();

        if(!dataFileName.equals("incorrectdata")) {
            WaitAction.waitForElementInvisibility(driver, emailField);
        }
    }

    /**
     * @param dataFileName name of the CSV file to be used
     */
    public void fillInContactFormFromCSVExcelStyle(String dataFileName) throws IOException, CsvValidationException {
        /**
         * Retrieve the CSV as a CSV Reader object
         **/
        csvReader = new CSVReader(new FileReader("src/test/resources/testdata/contactform/"
                + dataFileName + ".csv"));

        /**
         * Parse the CSV reader object and retrieve the data, the while loop makes sure to end at the end of the line
         **/
        while ((csvCell = csvReader.readNext()) != null) {
            String subject = csvCell[0];
            String emailAddress = csvCell[1];
            String message = csvCell[2];
            WaitAction.waitForElementVisibility(driver, emailField);

            Select selectSubjectBox = new Select(driver.findElement(selectSubject));

            selectSubjectBox.selectByVisibleText(subject);
            driver.findElement(emailField).sendKeys(emailAddress);
            driver.findElement(messageField).sendKeys(message);

            driver.findElement(sendButton).click();

            if(!dataFileName.equals("incorrectdata")) {
                WaitAction.waitForElementInvisibility(driver, emailField);
            }
        }
    }

    public WebElement getSendMessage() {
        /**
         * returning an element so it can be used for validation on the stepDef code level
         **/
        return driver.findElement(successfullySendMessage);
    }
}
