# 1. Deployment strategy
### 1. 1 Prerequisites 
Please make sure you have the following installed:
	1. IDE (IntelliJ || WebStorm || Visual Studio code
	2. If applicable the Development Kit (JDK || c#)
	3. Chrome
	4. Git

### 1.2.1 Pull the code from GitHub
You will be provided a link to the git repo. Use this link to clone the SALUTE project into your own workspace. Use either the git terminal or your IDE. 

`git clone https://github.com/ValoriSolutions/SALUTE_Java8_WebDriver.git `

### 1.2.2 Delete the upstream
This will prevent you from pushing to the original SALUTE repo

`git remote rm origin`

### 1.2.3  Own Git
Setup you own git repo in either GitHub or any other git vendor. Add your own git repo to the local SALUTE repo by adding it as the new origin 

`git remote add origin {link to your .git}`


### 1.3 Read the documentation
Within the SALUTE project you will find a documentation folder. Please read all the documentation before continuing.  

### 1.4 Unit test
SALUTE comes with several System Unit Tests out of the box. These tests will ensure the test framework can work in your workspace:
	1. Is the correct SDK installed?
	2. Is the correct browser installed?
	3. Retrieve the correct chromedriver
	
### 1.4.1 Set the driver location

SALUTE comes with a properties file to set several properties. The properies file can be found:
Java: src_test_resources/project.properties

To start please adjust the driver_location to a local folder on your own HDD. For example: 

_Users_{username}/Salute_browserdrivers 

### 1.4.2 Run the unit tests

The unit tests can be found:
Java: src_test_java_unittests_

First run the javaVersion in systemTests to ensure Java is installed correctly. When using IntelliJ the green arrow can be used to start the test.

```
@Test
public void javaVersionForWindows() {
    String installed = System./getProperty/(“java.version”);
    String expected = getMavenProperty(“project_java_version”);

    for (int I = 0; I < delimiter(expected).length; I++) {
        Assertions./assertThat/(delimiter(expected)[I])
                .as(“The expected version %s does not match the installed version %s”, expected, installed)
                .isEqualTo(delimiter(installed)[I]);
    }
}
```

Run checkJavaEnvVar in envVarChecks to validate if the correct Java env var is set. When using IntelliJ the green arrow can be used to start the test.

```
@Test
public void checkJavaEnvVar() {
    String javaPath = System./getenv/(“JAVA_HOME”);

    if (javaPath == null) {
        Assertions./fail/(“JAVA_HOME is not set in env vars, please set your JAVA_HOME path”);
    } else {
        System./out/.println(“JAVA_HOME is set in env vars”);
    }
}
```

chromedriverVersion can be used to validate if the correct ChromeDriver is present on the system. If there is no chrome driver available, a new one will be downloaded

```
####--- Config ---####

browser = CHROME
chromeVersion = LATEST
```

If the tests fail please either update your chrome version to the latest version or change the chromedriver version in the properties file. 

### 1.4.3 Code Unit tests

SALUTE also comes with Unit Tests for the code currently in place. These unit tests ensure the code which is not applicable to the test code itself still works.  E.g., the code to use the JSON and CSV file has unit tests to ensure working functionality. Also, code to access the maven properties has unit tests. Use these tests to ensure nothing breaks when you alter the code which is not applicable to the test code itself. 

### 1.5.1 Run the smoke test
SALUTE has a smoke test ready to go. It will start the browser, navigate to the Valori automation test site and navigate through some web elements and pages. 

For a quick check, please set the environment in properties file to LOCAL. More on this later: 
```
####--- Environment ---####

environment = LOCAL
localHubUrl = http://localhost:4444/wd/hub
dockerizedHubUrl = http://selenium-hub:4444/wd/hub
```

Kick off this smoke test to ensure the entire framework is ready to go in your workspace.
Java: Junit smoke test runner in srctestjavarunnersRunCukesSmokeTest

### 1.5.2 Smoke test reporting
The report for the smoke test can be found in:
target/cucumber 

### 1.6 Ready to go!
If all the unit tests and smoke test run successfully you are ready to start developing automation tests. If you run into some issue, please consult chapter 7 trouble shooting