package thingifier.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.IOException;
import java.net.ConnectException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.Response;
import thingifier.HTTP;

public class StepDefinitions {

    private final String url = "http://localhost:4567/categories";
    private JSONObject category, categories;
    private Response response;

    // Before each scenario, reset the database
    @Before
    public void beforeScenario() throws IOException, JSONException {
        // Launch the application with terminal command: "java -jar runTodoManagerRestAPI-1.5.5.jar
        // The jar is in test/resources/thingifier

        String path = "src/test/resources/thingifier/runTodoManagerRestAPI-1.5.5.jar";
        String command = "java -jar " + path;
        Runtime.getRuntime().exec(command);

        // Wait for the application to launch
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }

        category = null;
        categories = null;
        response = null;
    }

    @After
    public void afterScenario() throws IOException, JSONException {
        if (response != null) {
            response.close();
        }
        try {
            HTTP.get("http://localhost:4567/shutdown");
        } catch (ConnectException e) {
            // Ignore
        }
    }

    //user story 1 normal flow
    @Given("category with ID {int} exists")
    public void category_with_id_exists(Integer int1) throws JSONException, IOException {
        JSONObject category = HTTP.get(url + "/" + int1);
        assertNotNull(category);
    }

    @When("I want to view the details of category with ID {int}")
    public void i_want_to_view_the_details_of_category_with_id(Integer int1) throws JSONException, IOException {
        response = HTTP.getResponse(url + "/" + int1);
        category = HTTP.get(url + "/" + int1);
    }

    @Then("the title of category with ID {int} is {string}")
    public void the_title_of_category_with_id_is(Integer int1, String string) throws JSONException, IOException {
        JSONObject category = HTTP.get(url + "/" + int1);
        assertNotNull(category);
        String title = category.getJSONArray("categories").getJSONObject(0).getString("title");
        assertEquals(title, string);
    }


    @Then("the description of category with ID {int} is {string}")
    public void the_description_of_category_with_id_is(Integer int1, String string) throws JSONException, IOException {
        JSONObject category = HTTP.get(url + "/" + int1);
        assertNotNull(category);
        String description = category.getJSONArray("categories").getJSONObject(0).getString("description");
        assertEquals(description, string);
    }


    //alternate flow
    @Given("no categories exists")
    public void no_categories_exists() throws JSONException, IOException {

        JSONObject categories = HTTP.get(url);
        assertNotNull(categories);

        JSONArray categoriesArray = categories.getJSONArray("categories");
        for (int i = 0; i < categoriesArray.length(); i++) {
            String id = categoriesArray.getJSONObject(i).getString("id");
            HTTP.delete(url + "/" + id);
        }
        categories = HTTP.get(url);
        assertNotNull(categories);
        assertEquals(categories.getJSONArray("categories").length(), 0);
    }

    @When("I want to view the list of categories")
    public void i_want_to_view_the_list_of_categories() throws JSONException, IOException {
        categories = HTTP.get(url);
    }

    @Then("I should see an empty list of categories")
    public void i_should_see_an_empty_list_of_categories() throws JSONException {
        assertEquals(categories.getJSONArray("categories").length(), 0);
    }

    //error flow

    @Given("a non existent category with ID {int}")
    public void a_non_existent_category_with_id(Integer int1) throws JSONException, IOException {
        JSONObject category = HTTP.get(url + "/" + int1);
        assertNull(category);
    }

    @Then("I should see an error message indicating that the category with ID {int} wasn't found")
    public void i_should_see_an_error_message_indicating_that_the_category_with_id_does_not_exist(Integer int1) throws IOException {
        assertNotNull(response);
        assertEquals(response.code(), 404);
        assertEquals(response.message(), "Not Found");

        assertNotNull(response.body());
        String body = response.body().string();
        System.out.println(body);

        assertEquals(body, String.format("{\"errorMessages\":[\"Could not find any instances with categories/%s\"]}", int1));
    }

    //user story 2:
    @When("I want to remove a category with ID {int}")
    public void i_want_to_remove_a_category_with_id(Integer int1) throws IOException, JSONException {
        response = HTTP.deleteResponse(url + "/" + int1);
        category = HTTP.get(url + "/" + int1);
    }

    @Then("I should not see a category with ID {int}")
    public void i_should_not_see_a_category_with_id(Integer int1) throws IOException, JSONException {
        assertNotNull(response);
        assertNull(category);
        assertEquals(response.code(), 200);
        assertEquals(response.message(), "OK");

        JSONObject category = HTTP.get(url + "/" + int1);
        assertNull(category);
    }


    //alternate
    @Then("I should see a category with ID {int}")
    public void i_should_see_a_category_with_id(Integer int1) throws JSONException, IOException {
        JSONObject category = HTTP.get(url + "/" + int1);
        assertNotNull(category);
        assertEquals(category.getJSONArray("categories").getJSONObject(0).getString("id"), String.valueOf(int1));
    }

    @Then("I should see an error message indicating that the category with GUID {int} wasn't found")
    public void i_should_see_an_error_message_indicating_that_the_category_with_guid_does_not_exist(Integer int1) throws IOException {
        assertNotNull(response);
        assertEquals(response.code(), 404);
        assertEquals(response.message(), "Not Found");

        assertNotNull(response.body());
        String body = response.body().string();
        System.out.println(body);

        assertEquals(body, String.format("{\"errorMessages\":[\"No such category entity instance with GUID or ID %s found\"]}", int1));
    }

    @When("I want to add a category with title {string} and description {string}")
    public void i_want_to_add_a_category_with_title_and_description(String title, String description)
            throws JSONException, IOException {

        JSONObject addCategory = new JSONObject()
                .put("title", title)
                .put("description", description);

        response = HTTP.postResponse(url, addCategory);
        assertNotNull(response.body());

        if (response.isSuccessful()) {
            String id = new JSONObject(response.body().string()).getString("id");
            category = HTTP.get(url + "/" + id);
        }
    }

    @Then("I should see a category with title {string} and description {string}")
    public void i_should_see_a_category_with_title_and_description(String string, String string2) throws JSONException, IOException {
        JSONObject category = HTTP.get(url + "?title=" + string + "&description=" + string2);
        System.out.println(category);
        assertNotNull(category);

        String title = category.getJSONArray("categories").getJSONObject(0).getString("title");
        assertEquals(title, string);

        String description = category.getJSONArray("categories").getJSONObject(0).getString("description");
        assertEquals(description, string2);
    }

    @Given("category with title {string} and description {string} exists")
    public void category_with_title_and_description_exists(String string, String string2) throws JSONException, IOException {
        i_want_to_add_a_category_with_title_and_description(string, string2);
    }

    @Then("I should see {int} categories with title {string} and description {string}")
    public void i_should_see_todos_with_title_and_description(Integer int1, String string, String string2) throws JSONException, IOException {
        JSONObject category = HTTP.get(url + "?title=" + string + "&description=" + string2);
        System.out.println(category);
        assertEquals(category.getJSONArray("categories").length(), int1);
    }

//    @Then("I should see an error message indicating that the title cannot be empty")
//    public void i_should_see_an_error_message_indicating_that_the_title_cannot_be_empty() throws IOException {
//        assertNotNull(response);
//        assertEquals(response.code(), 400);
//        assertEquals(response.message(), "Bad Request");
//
//        assertNotNull(response.body());
//        String body = response.body().string();
//
//        assertEquals(body, "{\"errorMessages\":[\"Failed Validation: title : can not be empty\"]}");
//    }

    @Given("the description of category {int} is {string}")
    public void the_description_of_category_is(Integer int1, String string) throws JSONException, IOException {
        JSONObject setDescription = new JSONObject("{\"description\": \"" + string + "\"}");
        response = HTTP.postResponse(url + "/" + int1, setDescription);
        category = HTTP.post(url + "/" + int1, setDescription);
    }

    @When("I want to set the description of category with ID {int} to {string}")
    public void i_want_to_set_the_description_of_category_with_id_to(Integer int1, String string) throws JSONException, IOException {
        the_description_of_category_is(int1, string);
    }


    @When("I want to set the title of category with ID {int} to {string}")
    public void i_want_to_set_the_title_of_category_with_id_to(Integer int1, String string) throws JSONException, IOException {
        the_title_of_category_is(int1, string);
    }

    @Given("the title of category {int} is {string}")
    public void the_title_of_category_is(Integer int1, String string) throws JSONException, IOException {
        // Set the title of category with ID int1 to string
        JSONObject setTitle = new JSONObject("{\"title\": \"" + string + "\"}");
        response = HTTP.postResponse(url + "/" + int1, setTitle);
        category = HTTP.post(url + "/" + int1, setTitle);
    }

}
