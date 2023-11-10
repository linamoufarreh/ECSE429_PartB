package thingifier;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class MainRESTAPITest {
    public static void main(String[] args) throws IOException, JSONException {
        // Example of a GET request
        System.out.println("GET http://localhost:4567/todos/1");
        JSONObject json = HTTP.get("http://localhost:4567/todos/1");
        assert json != null;
        System.out.println("Response: " + json);
        String title = json.getJSONArray("todos").getJSONObject(0).getString("title");
        System.out.println("Title: " + title);
        System.out.println();

        // Example of a POST request
        System.out.println("POST http://localhost:4567/todos/1");

        Map<String, Object> body = new HashMap<String, Object>() {{
            put("title", "Test");
            put("doneStatus", false);
        }};
        JSONObject response = HTTP.post("http://localhost:4567/todos/1", new JSONObject(body));
        System.out.println("Response: " + response);
    }
}
