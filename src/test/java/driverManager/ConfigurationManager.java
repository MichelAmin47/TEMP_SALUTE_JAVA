package driverManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationManager {

    private String userName;
    private String passWord;
    private String appUrl;
    private String environment;
    private String grid;
    private String gridDocker;
    private String driver_location;
    private String browser;
    private String chromeVersion;

    private ConfigurationManager(String userName, String passWord, String appUrl, String environment, String grid, String gridDocker, String driver_location, String browser, String chromeVersion) {
        this.userName = userName;
        this.passWord = passWord;
        this.appUrl = appUrl;
        this.environment = environment;
        this.grid = grid;
        this.gridDocker = gridDocker;
        this.driver_location = driver_location;
        this.browser = browser;
        this.chromeVersion = chromeVersion;
    }

    static ConfigurationManager setData() {
        String userName = getMavenProperty("username");
        String passWord = getMavenProperty("password");
        String appUrl = getMavenProperty("appUrl");
        String environment = getMavenProperty("environment");
        String grid = getMavenProperty("localHubUrl");
        String gridDocker = getMavenProperty("dockerizedHubUrl");
        String driver_location = getMavenProperty("driver_location");
        String browser = getMavenProperty("browser");
        String chromeVersion = getMavenProperty("chromeVersion");
        return new ConfigurationManager(userName, passWord, appUrl, environment, grid, gridDocker, driver_location, browser, chromeVersion);
    }

    String getUserName() {
        return userName;
    }

    String getPassWord() {
        return passWord;
    }

    public String getAppUrl() {
        return appUrl;
    }

    String getGrid() {
        if (getEnvironmentVariable("USE_DOCKER_SELENIUM").equals("true")) {
            return gridDocker;
        } else {
            return grid;
        }
    }

    public DriverManager.environment getEnvironment() {
        return DriverManager.environment.valueOf(environment.toUpperCase());
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    private static String getEnvironmentVariable(String variableName) {
        if (System.getenv(variableName) == null) {
            return "";
        } else {
            return System.getenv(variableName);
        }
    }

    private static String getMavenProperty(String propertyName) {
        return System.getProperty(propertyName, getDriverProperty(propertyName));
    }

    private static String getDriverProperty(String propertyName) {
        Properties prop = new Properties();
        String propertyValue = null;

        try (InputStream input = ConfigurationManager.class.getClassLoader().getResourceAsStream("project.properties")) {
            ;
            prop.load(input);
            propertyValue = prop.getProperty(propertyName);
        } catch (IOException ioex) {
            // LOG.log(Level.SEVERE, "Problems opening properties", ioex);
        }
        return propertyValue;
    }

    public String getDriver_location() {
        return driver_location;
    }

    public void setDriver_location(String driver_location) {
        this.driver_location = driver_location;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getChromeVersion() {
        return chromeVersion;
    }

    public void setChromeVersion(String chromeVersion) {
        this.chromeVersion = chromeVersion;
    }
}