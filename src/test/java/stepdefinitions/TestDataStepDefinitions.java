package stepdefinitions;

import com.opencsv.exceptions.CsvValidationException;
import driverManager.World;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import pages.ContactPage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class TestDataStepDefinitions extends World {

    private WebDriver driver = getDriver();

    private ContactPage contactPage = new ContactPage(driver);

    @And("I fill in my complaint from the external JSON")
    public void iFillInMyComplaintFromTheExternalJSON() throws IOException {
        contactPage.fillInContactFormFromJson("correctdata", "contactMessage");
    }

    @And("I fill in my complaint from the external JSON with incorrect data")
    public void iFillInMyComplaintFromTheExternalJSONWithIncorrectData() throws IOException {
        contactPage.fillInContactFormFromJson("incorrectdata", "contactMessage");
    }

    @And("I fill in my complaint from the external CSV JSON style file")
    public void iFillInMyComplaintFromTheExternalCSVJSONStyleFile() throws IOException {
        contactPage.fillInContactFormFromCSV("correctdata_json_style");
    }

    @And("I fill in my complaint from the external CSV Excel style file")
    public void iFillInMyComplaintFromTheExternalCSVExcelStyleFile() throws IOException, CsvValidationException {
        contactPage.fillInContactFormFromCSVExcelStyle("correctdata_excel_style");
    }
}
