package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitAction {

    public static void explicitWait(int millis) {
        long endTimeMillis = System.currentTimeMillis() + millis;
        while (System.currentTimeMillis() < endTimeMillis) {
        }
    }

    public static WebElement waitForElementPresence(WebDriver driver, By elementLocator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.presenceOfElementLocated(elementLocator));
    }

    public static WebElement waitForElementVisibility(WebDriver driver,By elementLocator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
    }

    public static void waitForElementInvisibility(WebDriver driver,By elementLocator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
         wait.until(ExpectedConditions.invisibilityOfElementLocated(elementLocator));
    }

    public static void waitAndClick(WebDriver driver,By elementLocator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        new ElementHelper().scrollIntoView(driver, elementLocator);
        driver.findElement(elementLocator).click();
    }

    public static void waitAndClick(WebDriver driver,WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        new ElementHelper().scrollIntoView(driver, element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    public static void waitAndSendKeys(WebDriver driver,By elementLocator, String keys) {
        WebElement el = waitForElementPresence(driver, elementLocator);
        el.sendKeys(keys);
    }

    public static void waitToBeTrue(WebDriver driver,Boolean input, Long timeoutInMs) {
        while (!input && (System.currentTimeMillis() < timeoutInMs)) {
            explicitWait(200);
        }
    }

    public static void waitForReady(WebDriver driver,WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        new ElementHelper().scrollIntoView(driver, element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}
