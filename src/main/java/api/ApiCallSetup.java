package api;

import helpers.ParseJson;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.io.IOException;

public class ApiCallSetup {

    public static RequestSpecification setup() {
        RestAssured.baseURI = "https://webshop.mobiletestautomation.nl";
        RestAssured.basePath = "/api";
        RestAssured.defaultParser = Parser.JSON;
        RequestSpecification request = RestAssured.given();
        return request;
    }

    public static String getApiKeyFromFile() throws IOException {
        // Use src/test/resources/credentials-sample.json to store api key and rename to credentials.jsom
        JSONObject JsonTestDataObject = ParseJson.getJsonAsObject("src/test/resources/credentials.json");
        return JsonTestDataObject.getJSONObject("apiKeys").getString("prestaShopApiKey");
    }
}