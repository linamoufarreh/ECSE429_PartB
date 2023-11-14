package thingifier;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;

public class HTTP {

    public static final OkHttpClient client = new OkHttpClient();

    /**
     * GET request to the given URL. Returns the body as a JSONObject only if the response is successful,
     * otherwise returns null.
     *
     * Usage:
     * JSONObject json = HTTP.get("http://localhost:4567/projects/1");
     * String title = json.getJSONArray("todos").getJSONObject(0).getString("title");
     *
     * @param url URL to GET
     * @return JSONObject of the response body, or null if the request was not successful
     */
    public static JSONObject get(String url) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                assert responseBody != null;

                return new JSONObject(responseBody.string());
            }
        }

        return null;
    }

    /**
     * Get the full response from a GET request, so the HTTP code, the headers and the body.
     * The previous get() method only returned the body, only if the response was successful.
     *
     * @param url URL to GET
     * @return Response object
     * @throws IOException if execute() fails
     */
    public static Response getResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        return client.newCall(request).execute();
    }

    public static JSONObject post(String url, JSONObject body) throws IOException, JSONException {
        RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), body.toString());
        Request postRequest = new Request.Builder()
                .url(url)
                .post(postBody)
                .build();

        try (Response postResponse = client.newCall(postRequest).execute()) {
            if (postResponse.isSuccessful()) {
                ResponseBody responseBody = postResponse.body();
                assert responseBody != null;

                return new JSONObject(responseBody.string());
            }
        }

        return null;
    }

    public static Response postResponse(String url, JSONObject body) throws IOException {
        RequestBody postBody = RequestBody.create(MediaType.parse("application/json"), body.toString());
        Request postRequest = new Request.Builder()
                .url(url)
                .post(postBody)
                .build();

        try (Response postResponse = client.newCall(postRequest).execute()) {
            return postResponse;
        }
    }

    public static JSONObject put(String url, JSONObject body) throws IOException, JSONException {
        RequestBody putBody = RequestBody.create(MediaType.parse("application/json"), body.toString());
        Request putRequest = new Request.Builder()
                .url(url)
                .put(putBody)
                .build();

        try (Response putResponse = client.newCall(putRequest).execute()) {
            if (putResponse.isSuccessful()) {
                ResponseBody responseBody = putResponse.body();
                assert responseBody != null;

                return new JSONObject(responseBody.string());
            }
        }

        return null;
    }

    public static Response putResponse(String url, JSONObject body) throws IOException {
        RequestBody putBody = RequestBody.create(MediaType.parse("application/json"), body.toString());
        Request putRequest = new Request.Builder()
                .url(url)
                .put(putBody)
                .build();

        try (Response putResponse = client.newCall(putRequest).execute()) {
            return putResponse;
        }
    }

    public static int delete(String url) throws IOException, JSONException {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.code();
        }
    }

    public static Response deleteResponse(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response;
        }
    }

}
