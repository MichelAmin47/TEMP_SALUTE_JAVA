package stepdefinitions;

import driverManager.World;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.ContactPage;
import pages.HomePage;
import api.PrestaShop;
import io.restassured.response.Response;

import java.io.IOException;


public class SmokeTestStepDefinitions extends World {

    /**
     * Calling the driver which drives the browser
     * See SetupAndTeardown class for the driver
     **/
    private WebDriver driver = getDriver();
    /**
     * Page Object Model implementation
     **/
    private HomePage homePage;
    private ContactPage contactPage = new ContactPage(driver);
    private PrestaShop prestaShop = new PrestaShop();

    /**
     * Step definition methods which are linked to the step definitions in the feature file
     **/
    @Given("I am on the homepage")
    public void iAmOnTheHomepage() {
        /**
         * Page Object Model implementation
         **/
        homePage = new HomePage(driver);
        /**
         * Assertion build in an understandable syntax with a description
         **/
        Assertions.assertThat(homePage.getHomePageLogo().isDisplayed())
                .as("Check if empty element is visible").isTrue();
    }

    @When("I navigate to the contact us form")
    public void iNavigateToTheContactUsForm() {
        homePage.navigateToContactForm();
    }

    @And("I fill in my complaint")
    public void iFillInMyComplaint(){
        contactPage.fillInContactForm();
    }

    @Then("the successful sent message should be shown")
    public void theSuccessfullSentMessageShouldBeShown() {
        Assertions.assertThat(contactPage.getSendMessage().getText()).as("Success message should be visible")
                .isEqualTo("Your message has been successfully sent to our team.");
    }

    @And("The correct message is present in prestashop api and deleted")
    public void messageIsPresentInApi() throws IOException {
        // Fetch customer message id that contains the message we filled in via FE
        int messageId = prestaShop.fetchCustomerMessageIdWithFilter(contactPage.composedMessage);
        System.out.println("Fetched message id: " + messageId);

        // Fetch the complete customer message with the id and validate its content
        Response specificMessage = prestaShop.fetchSpecificCustomerMessageWithId(messageId);
        String messageContent = specificMessage.jsonPath().getString("customer_message.message");
        System.out.println("Fetched message content: " + messageContent);
        Assertions.assertThat(messageContent).as("Expected the fetched message to equal the submitted message").isEqualTo(contactPage.composedMessage);

        // Delete the customer message and validate the response status
        Response deleteResponse = prestaShop.deleteSpecificCustomerMessage(messageId);
        int deleteResponseStatusCode = deleteResponse.getStatusCode();
        System.out.println("Delete message status code: " + deleteResponseStatusCode);
        Assertions.assertThat(deleteResponseStatusCode).as("Expected the delete message call to return a 200").isEqualTo(200);
    }
}
