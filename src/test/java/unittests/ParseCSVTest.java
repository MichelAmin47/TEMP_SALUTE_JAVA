package unittests;

import helpers.ParseCSV;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseCSVTest {

    @Test
    public void shouldReturnCSVAsMap() throws IOException {
        HashMap<String, String> CSVTestDataMap = ParseCSV.getCsvAsMap("src/test/resources/testdata/contactform/correctdata_json_style.csv");
        assertThat(CSVTestDataMap.getClass().toString()).as("Object should be of type HashMap").containsIgnoringCase("hashmap");
    }

    @Test
    public void ReturnedHashMapShouldContainData() throws IOException {
        HashMap<String, String> CSVTestDataMap = ParseCSV.getCsvAsMap("src/test/resources/testdata/contactform/correctdata_json_style.csv");
        assertThat( CSVTestDataMap.get("subject")).isEqualTo("Webmaster");
    }
}
