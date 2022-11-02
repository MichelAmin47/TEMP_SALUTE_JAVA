package unittests;

import driverManager.DriverManager;
import driverManager.World;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runners.model.TestClass;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import static driverManager.DriverManager.environment.LOCAL;

public class EnvironmentTest {

    @Test
    public void checkJavaEnvVar() {
        String javaPath = System.getenv("JAVA_HOME");

        if (javaPath == null) {
            Assertions.fail("JAVA_HOME is not set in env vars, please set your JAVA_HOME path");
        } else {
            System.out.println("JAVA_HOME is set in env vars");
        }
    }

    @Test
    public void chromedriverVersion() {
        WebDriver webdriver = null;

        // Download the specified ChromeDriver if not available
        if (!DriverManager.isChromeDriverAvailable()) {
            DriverManager.downloadChromeDriverVersion();
        }

        // Check if the ChromeDriver version can be used on the installed version of Chrome
        try {
            webdriver = DriverManager.createDriver();
        } catch (SessionNotCreatedException e) {
            Assertions.fail("The installed Chrome version is not matching the specified chromedriver version in the project.properties: " + e.getMessage());
            e.getMessage();
        }

        Capabilities cap = World.config.getEnvironment() == LOCAL ? ((ChromeDriver) webdriver).getCapabilities() : ((RemoteWebDriver) webdriver).getCapabilities();
        webdriver.quit();
        String expectedVersion = World.config.getChromeVersion();
        if (expectedVersion.equalsIgnoreCase("LATEST")) {
            expectedVersion = DriverManager.getChromeDriverVersion();
        }
        Assertions.assertThat(cap.getVersion().substring(0,2))
                .as("The installed Chrome version is not matching the specified chromedriver version in the project.properties")
                .startsWith(expectedVersion.substring(0,2));
        System.out.println(expectedVersion.substring(0,2));
    }

    @Test
    public void checkChromeDriverAvailability() {
        if (!DriverManager.isChromeDriverAvailable()) {
            DriverManager.downloadChromeDriverVersion();
        }
        Assertions.assertThat(DriverManager.isChromeDriverAvailable()).as("There is no Chrome driver available in " + DriverManager.getBrowserDriverLocation()).isTrue();
    }


    @Test
    public void checkHomebrewAvailability() throws IOException, InterruptedException {
        String cmd = "brew -v";
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        Assertions.assertThat(buf.readLine()).as("Homebrew doesn't seem to be installed. Please follow these steps: https://brew.sh/index_nl").contains("Homebrew");
    }

//    @Test
//    public void checkAllureAvailability() throws IOException, InterruptedException {
//        String cmd = "brew info allure";
//        Runtime run = Runtime.getRuntime();
//        Process pr = run.exec(cmd);
//        pr.waitFor();
//        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
//        String line = "";
//        while ((line=buf.readLine())!=null) {
//            Assertions.assertThat(line).as("Allure does not seem installed in HomeBrew. Please do so by running 'brew install allure'").doesNotContain("Not installed");
//        }
//    }

    @Test
    public void javaVersionForWindows() {
        String installed = System.getProperty("java.version");
        String expected = getMavenProperty("project_java_version");

        for (int i = 0; i < delimiter(expected).length; i++) {
            Assertions.assertThat(delimiter(expected)[i])
                    .as("The expected version %s does not match the installed version %s", expected, installed)
                    .isEqualTo(delimiter(installed)[i]);
        }
    }

    private String[] delimiter(String s) {
        String[] version = s.split("\\.");
        return version;
    }

    private String getMavenProperty(String property) {
        Properties mavenProps = new Properties();
        InputStream in = TestClass.class.getResourceAsStream("/project.properties");
        try {
            mavenProps.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mavenProps.getProperty(property);
    }

    @Test
    public void checkJavaAvailability() throws IOException, InterruptedException {
        String cmd = "brew info java";
        Runtime run = Runtime.getRuntime();
        Process pr = run.exec(cmd);
        pr.waitFor();
        BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        while ((line=buf.readLine())!=null) {
            Assertions.assertThat(line).as("Java does not seem installed in HomeBrew. Please do so by running 'brew install java'").doesNotContain("Not installed");
        }
    }
}

