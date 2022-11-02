package driverManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SetupAndTeardown {

    private WebDriver driver;

    @Before
    public void before() {
        driver = DriverManager.createDriver();
        driver.get(World.config.getAppUrl());
        driver.manage().window().maximize();
    }

    @After
    public void after() {
        List<String> logs = getLogs();
        Assertions.assertThat(logs.size()).as("No severe warnings can be present at any time, but found " + logs.size()).isEqualTo(0);
        World.driver.quit();
    }

    /**
     * Gets all the logs for the specified log level for the run tests.
     * Can be used to catch specific log, like SEVERE.
     * @return
     */
    private List<String> getLogs() {
        LogEntries logEntries = World.driver.manage().logs().get(LogType.BROWSER);
        List<String> logs = new ArrayList<>();

        for (LogEntry entry : logEntries) {
            if (entry.getLevel().equals("SEVERE"))
                logs.add(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
        }
        return logs;
    }
}