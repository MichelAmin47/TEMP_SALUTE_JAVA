# 3. Page Object Model
Before we start with writing your first test in SALUTE, a quick Page Object Model introduction

Page Object is a Design Pattern which has become popular in test automation for enhancing test maintenance and reducing code duplication. A page object is an object-oriented class that serves as an interface to a page of your SUT. The tests then use the methods of this page object class whenever they need to interact with the UI of that page. The benefit is that if the UI changes for the page, the tests themselves don’t need to change, only the code within the page object needs to change. Subsequently all changes to support that new UI are located in one place.

The Page Object Design Pattern provides the following advantages:
	* There is a clean separation between test code and page specific code such as locators (or their use if you’re using a UI Map) and layout.
	* There is a single repository for the services or operations offered by the page rather than having these services scattered throughout the tests.

In both cases this allows any modifications required due to UI changes to all be made in one place. 

Applying the page object techniques, an example of a page object for a Sign-in page.

```
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object encapsulates the Sign-in page.
 */
public class SignInPage {
  protected WebDriver driver;

  // <input name=“user_name” type=“text” value=“”>
  private By usernameBy = By.name(“user_name”);
  // <input name=“password” type=“password” value=“”>
  private By passwordBy = By.name(“password”);
  // <input name=“sign_in” type=“submit” value=“SignIn”>
  private By signinBy = By.name(“sign_in”);

  public SignInPage(WebDriver driver){
    this.driver = driver;
  }

  /**
    * Login as valid user
    *
    * @param userName
    * @param password
    * @return HomePage object
    */
  public HomePage loginValidUser(String userName, String password) {
    driver.findElement(usernameBy).sendKeys(userName);
    driver.findElement(passwordBy).sendKeys(password);
    driver.findElement(signinBy).click();
    return new HomePage(driver);
  }
}
```

and page object for a Home page:

```
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Page Object encapsulates the Home Page
 */
public class HomePage {
  protected WebDriver driver;

  // <h1>Hello userName</h1>
  private By messageBy = By.tagName(“h1”);

  public HomePage(WebDriver driver){
    this.driver = driver;
    if (!driver.getTitle().equals(“Home Page of logged in user”)) {
      throw new IllegalStateException(“This is not Home Page of logged in user,” +
            “ current page is: “ + driver.getCurrentUrl());
    }
  }

  /**
    * Get message (h1 tag)
    *
    * @return String message text
    */
  public String getMessageText() {
    return driver.findElement(messageBy).getText();
  }

  public HomePage manageProfile() {
    // Page encapsulation to manage profile functionality
    return new HomePage(driver);
  }
}
```

Resulting in a test which use the two page objects: 

```
/***
 * Tests login feature
 */
public class TestLogin {

  @Test
  public void testLogin() {
    SignInPage signInPage = new SignInPage(driver);
    HomePage homePage = signInPage.loginValidUser(“userName”, “password”);
    assertThat(homePage.getMessageText(), is(“Hello userName”));
  }
}
```

Page objects themselves should never make verifications or assertions. This is part of your test and should always be within the test’s code, never in a page object. The page object will contain the representation of the page, and the services the page provides via methods but no code related to what is being tested should be within the page object.

There is one, single, verification which can, and should, be within the page object and that is to verify that the page, and possibly critical elements on the page, were loaded correctly. This verification should be done while instantiating the page object. In the examples above, both the SignInPage and HomePage constructors check that the expected page is available and ready for requests from the test.