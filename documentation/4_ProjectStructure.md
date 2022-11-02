# 4. Tutorial - Start writing your test in salute
## Setting the correct environment
SALUTE supports both local and remote WebDriver configuration. By default, the environment is set to “REMOTE”

Java: src_test_resources/project.properties
```
project_java_version = ${maven.compiler.source}
username = TinaTester@gmail.com
password = tinaspassword
environment = REMOTE
appUrl = https://webshop.mobiletestautomation.nl/
localHubUrl = http://localhost:4444/wd/hub
dockerizedHubUrl = http://selenium-hub:4444/wd/hub
```

This means SALUTE will kick off the test using the Selenium Remote WebDriver. This setup is also used when the test is run in a CI/CD pipeline and the hub connects to several nodes. Before tests can kicked off in the remote configuration the hub and node need to be kicked off located in:
Java: src_test_resources_driver_startHub.sh
Java: src_test_resources_driver_startNode.sh

By changing the variable environment to LOCAL, the regular WebDriver is used. The Hub and Node don’t have to be started in this configuration. Kickoff both files will start the Hub and Node. 


### 4.1 Write down your test in Gherkin as a feature
As described in chapter 2, write down your test scenario in the gherkin syntax: Given, When Then.
You can save the scenario in the corresponding features folder:

Java: src_test_resources_features_

And save it with the .feature extension. E.g MyFirstTest.feature

A complete feature file also requires three other aspects:
	* First line in the file starts with “Feature:” followed by a name. It’s a good idea to use a name similar to the file name.
	* The second line is a brief description of the feature. Cucumber does not execute this line because it’s documentation.
	* Above the Given, When, Then is located “Scenario:” followed by a _concrete example_ illustrating how the software should behave.

Result:
```
Feature: My First Test to use SALUTE
  This first test will start browser and navigate

  Scenario: Go to the homepage
    Given I am on the SALUTE homepage
    When I navigate to the contact us form
    Then the contact form should be shown
```


### 4.2 Create step definitions to match the sentences in the feature file
Each Gherkin sentence should be linked to a step definition method. You can use either the IDE to help you with creating a step definition or create one your self. Please consult the documentation of your own IDE how to do this automatically.  To create a method manually: 

Go to the folder  java/stepdefinitions and create a class. For example: NavigationStepDefs.class

Within the class, create a method:

```
@Given(“I am on the SALUTE homepage”)
public void iAmOnTheSALUTEHomepage(){

}
```

Make sure all imports are correctly added to the class:

```
package stepdefinitions;

import io.cucumber.java.en.Given;

public class NavigationStepDefinitions {

        @Given(“I am on the SALUTE homepage”)
        public void iAmOnTheSALUTEHomepage(){

        }
}
```

Going back to your feature file. You should now be able to use the step into functionality of your IDE on the Given I am on the SALUTE homepage. This will take you straight into the class and step definition method. 

### 4.3 Adding the Selenium WebDriver object to your class
Your test interacts with the browser because of  the WebDriver driver object. This object will need to be added to you class by using: 

```
private WebDriver driver = getDriver();
```

The get driver method can be found in the World class. This work class handles the driver setting and getting and project configuration. Extend this class to the NavigationStepDefinitions. 

```
public class NavigationStepDefinitions extends World {
```

### 4.4.1 Starting of the Browser is handled in the SetupAndTeardown class
The actual staring of the browser is not handled in a feature. By handling this in a separate class all other tests will use the same setup functionality. Go to the SetupAndTeardown class and observe the before method:
Java: src_test_java_driverManager_SetupAndTeardown

```
@Before
public void before() {
    driver = DriverManager.createDriver();
    driver.get(World.config.getAppUrl());
    driver.manage().window().maximize();
}
```

The @Before tag makes sure this method is run before every scenario.

`driver = DriverManager.createDriver();`

The createDriver method lives in the DriverManager class. This method will set the correct ChromeOptions, capabilities and will return the driver object so it can be used in the test. This driver object is the WebDriver object which will start the browser and interact with it. 

_Please be aware with altering the contents of the createDriver method_

### 4.4.2 Navigating to a URL is handled in the SetupAndTeardown class

`driver.get(World.config.getAppUrl());`

The getAppUrl method retrieves the url from the properties file. This increases usability and overal readability.  The project properties file can be found:
Java: src_test_resources/project.properties

`appUrl = https://webshop.mobiletestautomation.nl/`

Alter the URL to go to your own SUT URL when a test starts.

### 4.5 The step definition methods should use the page functionality methods in the corresponding Page Object

Please implement the Page Object model design pattern described in chapter 3 to implement test functionality to the step definition method. E.g. Create a Page Object for the homepage, add functionality   in the form of methods to this HomePage.class and call these methods in the step definitions method. 

```
public class NavigationStepDefinitions extends World {

        //**/
         * Calling the driver which drives the browser/
         * See SetupAndTeardown class for the driver/
         **//
		   private WebDriver driver = getDriver();
        //**/
        * Page Object Model implementation/
        **//
	      private HomePage homePage;

        @Given(“I am on the SALUTE homepage”)
        public void iAmOnTheSALUTEHomepage(){
                //**/
                * Page Object Model implementation/
                **//
                homePage = new HomePage(driver);
                homePage.getHomePageLogo();
        }
}
```


### 4.6 Don’t forget to write your assertion
As found in chapter 3 “Page objects themselves should never make verifications or assertions”. Write your assertions in the step definition methods. SALUTE uses the AssertJ library to handle assertions.  AssertJ is a java library providing a rich set of assertions, truly helpful error messages and improves test code readability  

Import the library into your class, the maven dependency is already added.

`import org.assertj.core.api.Assertions;`

Now you can add assertions to your method
 
```
//**/
/ * Step definition methods which are linked to the step definitions in the feature file/
/ **//
@Given(“I am on the homepage”)
public void iAmOnTheHomepage() {
    //**/
     * Page Object Model implementation/
     **//
     homePage = new HomePage(driver);
    //**/
     * Assertion build in an understandable syntax with a description/
     **//
     Assertions.assertThat(homePage.getHomePageLogo().isDisplayed())
            .as(“Check if empty element is visible”).isTrue();
}
```



### 4.7 Add tags to the feature
An easy way to group your tests with Cucumber is the use of tags. A feature or scenario can have as many tags as you like. Separate them with spaces.

```
Feature: My First Test to use SALUTE
  This first test will start browser and navigate

  @MyFirstTest @Test
  Scenario: Go to the homepage
    Given I am on the SALUTE homepage
    When I navigate to the contact us form
    Then the contact form should be shown
```

You can now use the command line to run scenario’s which uses the @MyFirstTest tag

`mvn test -Dcucumber.filter.tags=“@MyFirstTest tag”`


### 4.8 Create a runner to run the tests  

Junit can also be used to run scenario’s group by tags. This is is the preferred method to run scenario’s in a CI/CD pipeline as Maven might not always be added to the system path variable.  

Create an empty class that uses the Cucumber JUnit runner in the runners package:
Java: src_test_java/runners

And add the following code: 

```
package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = “src/test/resources/features”, glue = “”,
        tags = “@MyFirstTest”, plugin = {“pretty”, “html:target/cucumber.html”})
public class RunCukesMyFirstTests {
}
```

This will execute all scenarios with the tag @MyFirstTest, report in the pretty format and save a report to the target folder.

This class can now be used to run as a Junit test.

### 4.9 Continue with creating the test
Good luck!