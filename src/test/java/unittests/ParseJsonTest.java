package unittests;

import helpers.ParseJson;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseJsonTest {

    @Test
    public void shouldReturnJsonAsJSONObject() throws IOException {
        JSONObject JsonTestDataObject = ParseJson.getJsonAsObject("src/test/resources/testdata/contactform/correctdata.json");
        assertThat(JsonTestDataObject.getClass().toString()).as("Object should be of type JSONObject").containsIgnoringCase("org.json.JSONObject");
    }

    @Test
    public void ReturnedJSONObjectShouldContainData() throws IOException {
        JSONObject JsonTestDataObject = ParseJson.getJsonAsObject("src/test/resources/testdata/contactform/correctdata.json");
        assertThat(JsonTestDataObject.getJSONObject("contactMessage").getString("subject")).isEqualTo("Webmaster");
    }
}
