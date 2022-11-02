package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    /**
     * Locators are set as a By variable
     * Ensures no code duplication when locators are used in multiple methods
     **/
    private By homePageLogo = By.className("logo");
    private By contactUsButton = By.cssSelector("div#contact-link");
    private By emailField = By.cssSelector("section.form-fields input[type='email']");
    private WebDriverWait wait;

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 15);
    }

    /**
     * returning an element so it can be used for validation on the stepDef code level
     **/
    public WebElement getHomePageLogo(){
        return driver.findElement(homePageLogo);
    }

    /**
     * Interaction with a button element
     * wait for an element to be visible
     **/
    public void navigateToContactForm() {
        driver.findElement(contactUsButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
    }
}
