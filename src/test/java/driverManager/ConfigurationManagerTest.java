package driverManager;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationManagerTest {

    public static ConfigurationManager config = ConfigurationManager.setData();

    @Test
    public void getUserNameShouldNotBeEmpty(){
        assertThat(config.getUserName()).as("username getter should return not empty").isNotEmpty();
    }

    @Test
    public void getPassWordShouldNotBeEmpty(){
        assertThat(config.getPassWord()).as("password getter should return not empty").isNotEmpty();
    }

    @Test
    public void getAppURLShouldContainURL(){
        assertThat(config.getAppUrl()).as("appURL getter should contain URL").containsIgnoringCase("http")
                .containsIgnoringCase("://");
    }

    @Test
    public void getGridShouldReturnGridURL(){
        if (System.getenv("USE_DOCKER_SELENIUM") == null) {
            assertThat(config.getGrid()).as("getGrid should return grid URL as default with no env var set")
                    .containsIgnoringCase("http://localhost:4444/wd/hub");
        }
    }

    @Test
    public void getGridShouldReturnGridDockerURLWithEnvVarSet() {
        //TODO Unit test with getGrid and set own env var
        //https://stackoverflow.com/questions/8168884/how-to-test-code-dependent-on-environment-variables-using-junit
        //Second answer}
    }
}
