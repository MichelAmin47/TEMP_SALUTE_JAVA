package helpers;

import org.assertj.core.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ElementHelper {


    public boolean isElementPresent(WebDriver driver, By by) {
        List<WebElement> el = driver.findElements(by);
        return (el.size() > 0);
    }

    public void clearAndSendKeys(WebDriver driver, By by, String value) {
        WebElement el = WaitAction.waitForElementPresence(driver, by);
        clearField(driver, by);
        el.sendKeys(Keys.chord(value), Keys.TAB);
    }

    public void clearField(WebDriver driver, By by) {
        WebElement el = WaitAction.waitForElementPresence(driver, by);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("return arguments[0].select()", el);
        el.sendKeys(Keys.BACK_SPACE);
        js.executeScript("return arguments[0].value = ''", el);
    }

    public void setCheckbox(WebDriver driver, By by, Boolean checked) {
        WebElement el = driver.findElement(by);
        if (!Boolean.parseBoolean(el.getAttribute("value")) == checked) {
            el.click();
            el.sendKeys(Keys.ENTER);
        } else {
        }
    }

    public void setDropDown(WebDriver driver, By by, String value) {
        WaitAction.waitForElementPresence(driver, by);
        Select select = new Select(driver.findElement(by));
        int i = 0;
        List<String> labels = new ArrayList<>();
        List<WebElement> options = select.getOptions();
        for (WebElement el : options) {
            labels.add(el.getAttribute("label"));
            if (el.getAttribute("label").equalsIgnoreCase(value)) {
                select.selectByIndex(i);
                break;
            }
            i++;
        }
        Assertions.assertThat(labels).as("None of the available dropdown options are matching the requested option").contains(value);
    }

    public boolean isElementSelected(WebDriver driver, By by) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (boolean) js.executeScript("return arguments[0].selected;", driver.findElement(by));
    }

    public boolean isElementSelected(WebDriver driver, WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (boolean) js.executeScript("return arguments[0].selected;", element);
    }

    public boolean isElementChecked(WebDriver driver, By by) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (boolean) js.executeScript("return arguments[0].checked;", driver.findElement(by));
    }

    public boolean isElementDisabled(WebDriver driver, By by) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (boolean) js.executeScript("return arguments[0].disabled;", driver.findElement(by));
    }

    public boolean isElementVisible(WebDriver driver, By by) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String result = (String) js.executeScript("return arguments[0].style.display;", driver.findElement(by));
        return result.equals("block");
    }

    public List<String> getDropDownOptions(WebDriver driver, By by) {
        driver.findElement(by).click();
        Select select = new Select(driver.findElement(by));
        List<String> labels = new ArrayList<>();
        List<WebElement> options = select.getOptions();
        for (WebElement el : options) {
            labels.add(el.getAttribute("label"));
        }
        return labels;
    }

    public void setSlider(WebDriver driver, By by, float value) {
        WebElement el = driver.findElement(by);
        el.click();
        int i = 0;
        while (Float.parseFloat(el.getAttribute("value")) < value && i < 50) {
            el.sendKeys(Keys.RIGHT);
            i++;
        }
        i = 0;
        while (Float.parseFloat(el.getAttribute("value")) > value && i < 50) {
            el.sendKeys(Keys.LEFT);
            i++;
        }
    }

    public WebElement scrollIntoView(WebDriver driver, By by) {
        WebElement el = driver.findElement(by);
        scrollIntoView(driver, el);
        return el;
    }

    public WebElement scrollIntoView(WebDriver driver, WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: \"smooth\", block: \"start\"});", el);
        WaitAction.explicitWait(80);
        return el;
    }


    public String getElementType(WebDriver driver, By by) {
        WebElement el = driver.findElement(by);
        scrollIntoView(driver, by);
        String type = getElementType(el);

        if (type == null) {
            if (el.getAttribute("data-test").equalsIgnoreCase("codemirror-editor")) {
                return "codemirror";
            }
            if (type.contains("select")) {
                return "select";
            }
        }
        return el.getAttribute("type");
    }

    public String getElementType(WebElement el) {
        return el.getAttribute("type");
    }
}
