package thingifier;

import java.util.HashMap;
import java.util.Map;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class MainRESTAPITest {
    public static void main(String[] args) throws IOException, JSONException {
        // Example of a GET request
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:4567/todos/1")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                assert responseBody != null;
                JSONObject json = new JSONObject(responseBody.string());
                String title = json.getJSONArray("todos").getJSONObject(0).getString("title");
                System.out.println("Title: " + title);
            } else {
                System.err.println("Request failed with code: " + response.code());
            }
        }

        // Example of a POST request
        // Load body from map inlined
        Map<String, Object> body = new HashMap<>() {{
            put("title", "Test");
            put("doneStatus", false);
        }};
        JSONObject jsonBody = new JSONObject(body);

        // Create request
        RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), jsonBody.toString());
        Request postRequest = new Request.Builder()
                .url("http://localhost:4567/todos")
                .post(postBody)
                .build();

        // Send request
        try (Response postResponse = client.newCall(postRequest).execute()) {
            if (postResponse.isSuccessful()) {
                ResponseBody responseBody = postResponse.body();
                assert responseBody != null;

                System.out.println("Response: " + responseBody.string());
            } else {
                System.err.println("Request failed with code: " + postResponse.code());
            }
        }


    }
}
