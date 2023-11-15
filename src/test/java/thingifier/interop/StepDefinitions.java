package thingifier.interop;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import thingifier.HTTP;

import java.io.IOException;
import java.net.ConnectException;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    private JSONObject element;
    private JSONObject relationships;
    private JSONArray elements;
    private Response response;

    private final String urlTodos = "http://localhost:4567/todos";
    private final String urlCategories = "http://localhost:4567/categories";

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

        element = null;
        elements = null;
        relationships = null;
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


    private boolean JSONArrayContains(JSONArray jsonArray, String property, String value) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String title = jsonObject.getString(property);
            if (value.equals(title)) {
                return true;
            }
        }
        return false;
    }

    @Given("todo with id {int} with category with id {int}")
    public void todo_with_id_with_category(Integer int1, Integer int2) throws IOException, JSONException {
        JSONObject element = HTTP.get(urlTodos + "/" + int1 + "/categories");
        assertNotNull(element);

        JSONArray categories = element.getJSONArray("categories");
        assertTrue(JSONArrayContains(categories, "id", int2.toString()));
    }

    @When("the user attempts to view all the categories related to todo {int}")
    public void the_user_attempts_to_view_all_the_categories_related_to_todo(Integer int1) throws IOException, JSONException {
        response = HTTP.getResponse(urlTodos + "/" + int1 + "/categories");
        element = HTTP.get(urlTodos + "/" + int1 + "/categories");
    }

    @Then("a JSON with a property called {string} will have a list containing an element with the id {int}")
    public void a_json_with_a_property_called_categories_will_have_a_list_containing_an_element_with_the_title(String string, Integer int2) throws JSONException {
        assertNotNull(response);
        assertEquals(200, response.code());

        assertNotNull(element);
        JSONArray property = element.getJSONArray(string);
        assertTrue(JSONArrayContains(property, "id", int2.toString()));
    }

    @Given("todo with id {int} with no categories")
    public void todo_with_id_with_no_categories(Integer int1) throws JSONException, IOException {
        JSONObject element = HTTP.get(urlTodos + "/" + int1 + "/categories");
        assertNotNull(element);

        JSONArray categories = element.getJSONArray("categories");
        assertEquals(0, categories.length());
    }

    @Then("a JSON with a property called {string} will have an empty list")
    public void a_json_with_a_property_called_will_have_an_empty_list(String string) throws JSONException {
        assertNotNull(response);
        assertEquals(200, response.code());

        assertNotNull(element);
        JSONArray property = element.getJSONArray(string);
        assertEquals(0, property.length());
    }

    @Given("an inexistent todo with id {int}")
    public void an_inexistent_todo(int int1) throws IOException, JSONException {
        JSONObject element = HTTP.get(urlTodos + "/" + int1 + "/categories");
        assertNull(element);
    }
    @When("the user attempts to view all the categories related to the inexistent todo with id {int}")
    public void the_user_attempts_to_view_all_the_categories_related_to_the_inexistent_todo(int int1) throws IOException, JSONException {
        response = HTTP.getResponse(urlTodos + "/" + int1 + "/categories");
        element = HTTP.get(urlTodos + "/" + int1 + "/categories");
    }
    @Then("a response with code {int} will be received")
    public void a_response_with_code_will_be_received(Integer int1) {
        assertNotNull(response);
        assertEquals(int1, response.code());
    }

    @Given("a category with id {int} with todo {int} associated")
    public void a_category_with_todo_associated(Integer category, Integer todo) throws JSONException, IOException {
        JSONObject body = new JSONObject();
        body.put("id", todo.toString());
        Response response = HTTP.postResponse(urlCategories + "/" + category + "/todos", body);
        assertEquals(201, response.code());
    }

    @When("the user attempts to view all todos related to the category with id {int}")
    public void the_user_attempts_to_view_all_todos_related_to_the_category_with_id(Integer int1) throws IOException, JSONException {
        response = HTTP.getResponse(urlCategories + "/" + int1 + "/todos");
        element = HTTP.get(urlCategories + "/" + int1 + "/todos");
    }
    @Then("a JSON with a property called {string} will have a list containing an element with id {int}")
    public void a_json_with_a_property_called_will_have_a_list_containing_an_element_with_id(String string, Integer int1) throws JSONException {
        assertNotNull(response);
        assertEquals(200, response.code());

        assertNotNull(element);
        JSONArray property = element.getJSONArray(string);
        assertTrue(JSONArrayContains(property, "id", int1.toString()));
    }

    @Given("a category with id {int} with no associated todos")
    public void a_category_with_id_with_no_associated_todos(Integer int1) throws JSONException, IOException {
        JSONObject element = HTTP.get(urlCategories + "/" + int1 + "/todos");
        assertNotNull(element);

        JSONArray categories = element.getJSONArray("todos");
        assertEquals(0, categories.length());
    }

    @Given("an inexistent category with id {int}")
    public void an_inexistent_category_with_id(Integer int1) throws JSONException, IOException {
        JSONObject element = HTTP.get(urlCategories + "/" + int1);
        assertNull(element);
    }
    @When("the user attempts to view all todos related to the inexistent category with id {int}")
    public void the_user_attempts_to_view_all_todos_related_to_the_inexistent_category_with_id(Integer int1) throws IOException, JSONException {
        response = HTTP.getResponse(urlCategories + "/" + int1 + "/todos");
        element = HTTP.get(urlCategories + "/" + int1 + "/todos");
    }

    @Given("a todo with id {int} and category with id {int}")
    public void a_todo_with_id_and_category_with_id(Integer int1, Integer int2) throws JSONException, IOException {
        JSONObject element = HTTP.get(urlTodos + "/" + int1);
        assertNotNull(element);

        element = HTTP.get(urlCategories + "/" + int2);
        assertNotNull(element);
    }

    @When("the user attempts to add the category with id {int} to todo with id {int}")
    public void the_user_attempts_to_add_the_category_with_id_to_todo_with_id(Integer int1, Integer int2) throws IOException, JSONException {
        JSONObject body = new JSONObject();
        body.put("id", int1.toString());
        response = HTTP.postResponse(urlTodos + "/" + int2 + "/categories", body);
    }

    @Then("the todo with id {int} will be associated with the category with id {int}")
    public void the_todo_with_id_will_be_associated_with_the_category_with_id(Integer int1, Integer int2) throws JSONException, IOException {
        assertEquals(201, response.code());

        a_todo_with_id_and_category_with_id(int1, int2);
    }

    @Given("an inexistent todo with id {int} and category with id {int}")
    public void an_inexistent_todo_with_id_and_category_with_id(Integer int1, Integer int2) throws JSONException, IOException {
        an_inexistent_todo(int1);

        JSONObject element = HTTP.get(urlCategories + "/" + int2);
        assertNotNull(element);
    }

    @When("the user attempts to delete the relationship between todo with id {int} and category with id {int}")
    public void the_user_attempts_to_delete_the_relationship_between_todo_with_id_and_category_with_id(Integer int1, Integer int2) throws IOException {
        response = HTTP.deleteResponse(urlTodos + "/" + int1 + "/categories/" + int2);
    }

    @Then("the todo with id {int} will no longer be associated with the category with id {int}")
    public void the_todo_with_id_will_no_longer_be_associated_with_the_category_with_id(Integer int1, Integer int2) throws JSONException, IOException {
        assertEquals(200, response.code());

        JSONObject element = HTTP.get(urlTodos + "/" + int1 + "/categories");
        assertNotNull(element);

        JSONArray categories = element.getJSONArray("categories");
        assertFalse(JSONArrayContains(categories, "id", int2.toString()));


    }

    @Then("no changes will occur, and the response will indicate that the relationship doesn't exist.")
    public void no_changes_will_occur_and_the_response_will_indicate_that_the_relationship_doesn_t_exist() {
        assertEquals(404, response.code());
    }

    @Given("an inexistent todo with id {int} and a category with id {int}")
    public void an_inexistent_todo_with_id_and_a_category_with_id(Integer int1, Integer int2) throws JSONException, IOException {
        an_inexistent_todo(int1);

        JSONObject element = HTTP.get(urlCategories + "/" + int2);
        assertNotNull(element);
    }

    @Given("a category with id {int} with project {int} associated")
    public void a_category_with_id_with_project_associated(Integer int1, Integer int2) throws JSONException, IOException {
        JSONObject body = new JSONObject();
        body.put("id", int2.toString());
        Response addproject = HTTP.postResponse(urlCategories + "/" + int1 + "/projects", body);
        assertEquals(201, addproject.code());

        JSONObject element = HTTP.get(urlCategories + "/" + int1 + "/projects");
        assertNotNull(element);

        JSONArray projects = element.getJSONArray("projects");
        assertTrue(JSONArrayContains(projects, "id", int2.toString()));

    }
    @When("the user attempts to view all projects related to the category with id {int}")
    public void the_user_attempts_to_view_all_projects_related_to_the_category_with_id(Integer int1) throws IOException, JSONException {
        response = HTTP.getResponse(urlCategories + "/" + int1 + "/projects");
        element = HTTP.get(urlCategories + "/" + int1 + "/projects");

    }

    @Given("a category with id {int} with no associated projects")
    public void a_category_with_id_with_no_associated_projects(Integer int1) throws JSONException, IOException {
        JSONObject element = HTTP.get(urlCategories + "/" + int1 + "/projects");
        assertNotNull(element);

        JSONArray categories = element.getJSONArray("projects");
        assertEquals(0, categories.length());
    }

    @When("the user attempts to view all projects related to the inexistent category with id {int}")
    public void the_user_attempts_to_view_all_projects_related_to_the_inexistent_category_with_id(Integer int1) throws IOException {
        response = HTTP.getResponse(urlCategories + "/" + int1 + "/projects");
    }


}
