# 8. Maintenance Overview
### Java code with maintenance possibilities:
From pom.xml / maven:
	* Org.json (20200518): lib to load in a json object.
	* openCSV (5.1): lib to load in a csv object.
	* Cucumber (6.8.1): Used to use feature files, stepdefinitions and the pico container.
	* Selenium Webdriver (3.14): Lib to automatically interact with the browser 
	* JUnit (5): Testing lib to kickoff tests.
	* AssertJ-core (3.18.0): lib to write more sentence like assertions.
	* ChromeDriver (86, updated bi-montly): Executable to ensure Selenium WebDriver can talk to chrome

**Org.json**, **openCSV** and **AssertJ-core** seem the least likely to need updates

**Chromedriver** updates usually doesn’t need any code update. New functionality could improve our code but will still need work. 

**Cucumber** might need minor updates to fix bugs in the core release. A major numbered release update will need more work.

Current **Selenium WebDriver** 3.14 will not see any more updates. The new numbered release (4.x) will need work. 

**JUnit** might need minor updates to fix bugs in the core release. A major numbered release update will need more work.

### Current CI/CD Vendors.
**CircleCI** and **AzureDevops** may update their pipelines so we need to update our yaml code. This does not happen that often. New functionality which will improve our pipeline could be added and will need code work from us.  Same can be said for our **Docker compose** code. 