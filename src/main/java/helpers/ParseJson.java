package helpers;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParseJson {

    /**
     * @param path path to the JSON file to be used
     * @return JSONObject with the content of the JSON file parsed
     */
    public static JSONObject getJsonAsObject(String path) throws IOException {
        // parsing JSON file with a stream
        String JsonString = new String(Files.readAllBytes(Paths.get(path)));

        // input needs to be a json in string format, not a direct file
        return new JSONObject(JsonString);
    }
}
