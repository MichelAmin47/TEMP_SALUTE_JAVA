package api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Optional;

public class PrestaShop {

    /**
     * Generic method to execute rest GET call
     * @param endpoint (optional) the endpoint you want to fetch data from
     * @param query (optional) query params to apply to the call on endpoint
     * @return complete response of the call
     */
    public Response fetch(String endpoint, String query) throws IOException {
        Optional<String> ep = Optional.ofNullable(endpoint);
        Optional<String> q = Optional.ofNullable(query);
        String endpointQuery = ep.isPresent() && q.isPresent() ? endpoint + query : ep.isPresent() ? endpoint : "";

        RequestSpecification request = ApiCallSetup.setup();
        String apiKey = ApiCallSetup.getApiKeyFromFile();

        return request.queryParam("ws_key", apiKey)
                .queryParam("output_format", "JSON")
                .get(endpointQuery)
                .then()
                .extract()
                .response();
    }

    /**
     * Generic method to execute rest DELETE call
     * @param endpoint the endpoint you want to delete a resource on.
     * @param resource the resource to delete
     * @return complete response of delete action
     * @throws IOException
     */
    public Response delete(String endpoint, int resource) throws IOException {
        RequestSpecification request = ApiCallSetup.setup();
        String apiKey = ApiCallSetup.getApiKeyFromFile();

        return request.queryParam("ws_key", apiKey)
                .queryParam("output_format", "JSON")
                .delete(endpoint + "/" + resource)
                .then()
                .extract()
                .response();
    }

    public int fetchCustomerMessageIdWithFilter(String messageToFind) throws IOException {
        Response messages = fetch("customer_messages", "?filter[message]=[" + messageToFind + "]");
        return messages.jsonPath().get("customer_messages[0].id");
    }

    public Response fetchSpecificCustomerMessageWithId(Number messageId) throws IOException {
        return fetch("customer_messages", "/" + messageId);
    }

    public Response deleteSpecificCustomerMessage(int messageId) throws IOException {
        return delete("customer_messages", messageId);
    }
}
