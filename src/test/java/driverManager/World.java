package driverManager;

import org.openqa.selenium.WebDriver;

public class World {

    public static WebDriver driver;
    public static ConfigurationManager config = ConfigurationManager.setData();

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    public static ConfigurationManager getConfig(){
        return config;
    }
}
