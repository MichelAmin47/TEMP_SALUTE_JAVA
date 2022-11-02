package driverManager;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;

import static org.openqa.selenium.remote.DesiredCapabilities.chrome;

public class DriverManager {

    /**
     * Sets up the options and the capabilities of the driver
     *
     * @return Webdriver for the specified environment
     */
    public static WebDriver createDriver() {

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences loggingPreferences = new LoggingPreferences();

        loggingPreferences.enable(LogType.BROWSER, Level.SEVERE);
        options.setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences);
        options.setCapability(ChromeOptions.CAPABILITY, options);

        WebDriver driver = getEnvironmentDriver(options);
        World.setDriver(driver);
        return driver;
    }

    /**
     * @param options The desired options for the driver
     * @return Webdriver for the specified environment
     */
    private static WebDriver getEnvironmentDriver(ChromeOptions options) {
        WebDriver driver = null;
        DriverManager.getChromeDriverVersion();
        switch (World.getConfig().getEnvironment()) {
            case REMOTE:
                try {
                    driver = new RemoteWebDriver(new URL(World.getConfig().getGrid()), options);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            case LOCAL:
            default:
                System.setProperty("webdriver.chrome.driver", getBrowserDriverLocation() + "/chromedriver");
                driver = new ChromeDriver(options);
                break;
        }
        return driver;
    }

    /**
     * Environment ENUM to determine the desires run environment for the tests
     */
    public enum environment {
        REMOTE,
        LOCAL;
    }

    public static String getBrowserDriverLocation() {
        String path = World.getConfig().getDriver_location() + "/" +
                World.getConfig().getBrowser() + "/" +
                World.getConfig().getChromeVersion();
        return path;
    }

    private static boolean locationExists(String path) {
        return Files.exists(Paths.get(path));
    }

    /**
     * Gets the specified ChromeVersion from the project.properties
     * and verifies on chromedriver website what the full driver version for this version should be
     *
     * @return Full driver version from the chromedriver website
     */
    public static String getChromeDriverVersion() {
        String driverVersion = null;
        String version = World.getConfig().getChromeVersion();

        if (version.equalsIgnoreCase("latest")) {
            driverVersion = readChromeVersion("https://chromedriver.storage.googleapis.com/LATEST_RELEASE");
        } else {
            driverVersion = readChromeVersion("https://chromedriver.storage.googleapis.com/LATEST_RELEASE_" + version);
        }
        return driverVersion;
    }

    public static void downloadChromeDriverVersion() {
        String driverVersion = getChromeDriverVersion();
        if (!locationExists(getBrowserDriverLocation() + "/chromedriver")) {
            System.out.println("ChromeDriver is not present, downloading...");
            downloadDriver("https://chromedriver.storage.googleapis.com/" + driverVersion + "/chromedriver_mac64.zip", getBrowserDriverLocation() + "/chromedriver.zip");
        } else {
            System.out.println("ChromeDriver is present");
        }
    }

    public static boolean isChromeDriverAvailable() {
        return locationExists(getBrowserDriverLocation() + "/chromedriver");
    }

    private static String readChromeVersion(String url) {
        String result = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            InputStream inputStream = connection.getInputStream();
            result = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void downloadDriver(String from, String to) {
        System.out.println(from);
        try {
            URL url = new URL(from);
            File destination_file = new File(to);
            FileUtils.copyURLToFile(url, destination_file);
            unzipFile(to, getBrowserDriverLocation());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unzipFile(String from, String to) {
        try {
            ZipFile zipFile = new ZipFile(from);
            zipFile.extractAll(to);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }
}