package thingifier.todo;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import java.net.ConnectException;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import thingifier.HTTP;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {
    private final String url = "http://localhost:4567/todos";
    private JSONObject todo, todos;
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

        todo = null;
        todos = null;
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

    @Given("todo with ID {int} exists")
    public void todo_with_id_exists(Integer int1) throws JSONException, IOException {
        JSONObject todo = HTTP.get(url + "/" + int1);
        assertNotNull(todo);
    }

    @Then("the title of todo with ID {int} is {string}")
    public void the_title_of_todo_with_id_is(Integer int1, String string) throws JSONException, IOException {
        JSONObject todo = HTTP.get(url + "/" + int1);
        assertNotNull(todo);
        String title = todo.getJSONArray("todos").getJSONObject(0).getString("title");
        assertEquals(title, string);
    }


    @Then("the doneStatus of todo with ID {int} is {string}")
    public void the_done_status_of_todo_with_id_is(Integer int1, String string) throws JSONException, IOException {
        JSONObject todo = HTTP.get(url + "/" + int1);
        assertNotNull(todo);
        boolean doneStatus = todo.getJSONArray("todos").getJSONObject(0).getBoolean("doneStatus");
        assertEquals(doneStatus, Boolean.parseBoolean(string));
    }

    @Then("the description of todo with ID {int} is {string}")
    public void the_description_of_todo_with_id_is(Integer int1, String string) throws JSONException, IOException {
        JSONObject todo = HTTP.get(url + "/" + int1);
        assertNotNull(todo);
        String description = todo.getJSONArray("todos").getJSONObject(0).getString("description");
        assertEquals(description, string);
    }

    @Given("no todos exists")
    public void no_todos_exists() throws JSONException, IOException {
        // Delete all todos
        JSONObject todos = HTTP.get(url);
        assertNotNull(todos);

        JSONArray todosArray = todos.getJSONArray("todos");
        for (int i = 0; i < todosArray.length(); i++) {
            String id = todosArray.getJSONObject(i).getString("id");
            HTTP.delete(url + "/" + id);
        }

        todos = HTTP.get(url);
        assertNotNull(todos);
        assertEquals(todos.getJSONArray("todos").length(), 0);
    }

    @When("I want to view the list of todos")
    public void i_want_to_view_the_list_of_todos() throws JSONException, IOException {
        todos = HTTP.get(url);
    }

    @Then("I should see an empty list of todos")
    public void i_should_see_an_empty_list_of_todos() throws JSONException {
        assertEquals(todos.getJSONArray("todos").length(), 0);
    }

    @Given("a non existent todo with ID {int}")
    public void a_non_existent_todo_with_id(Integer int1) throws JSONException, IOException {
        JSONObject todo = HTTP.get(url + "/" + int1);
        assertNull(todo);
    }

    @When("I want to view the details of todo with ID {int}")
    public void i_want_to_view_the_details_of_todo_with_id(Integer int1) throws JSONException, IOException {
        response = HTTP.getResponse(url + "/" + int1);
        todo = HTTP.get(url + "/" + int1);
    }

    @Then("I should see an error message indicating that the todo with ID {int} does not exist")
    public void i_should_see_an_error_message_indicating_that_the_todo_with_id_does_not_exist(Integer int1) throws IOException {
        assertNotNull(response);
        assertEquals(response.code(), 404);
        assertEquals(response.message(), "Not Found");

        assertNotNull(response.body());
        String body = response.body().string();
        System.out.println(body);

        assertEquals(body, String.format("{\"errorMessages\":[\"Could not find an instance with todos/%s\"]}", int1));
    }

    @Then("I should see an error message indicating that the todo with GUID {int} does not exist")
    public void i_should_see_an_error_message_indicating_that_the_todo_with_guid_does_not_exist(Integer int1) throws IOException {
        assertNotNull(response);
        assertEquals(response.code(), 404);
        assertEquals(response.message(), "Not Found");

        assertNotNull(response.body());
        String body = response.body().string();
        System.out.println(body);

        assertEquals(body, String.format("{\"errorMessages\":[\"No such todo entity instance with GUID or ID %s found\"]}", int1));
    }

    @When("I want to set the todo with ID {int} as done")
    public void i_want_to_set_the_todo_with_id_as_done(Integer int1) throws JSONException, IOException {
        JSONObject setDone = new JSONObject("{\"doneStatus\":true}");
        response = HTTP.postResponse(url + "/" + int1, setDone);
        todo = HTTP.post(url + "/" + int1, setDone);
    }

    @Given("the todo with ID {int} is set as done")
    public void the_todo_with_id_is_set_as_done(Integer int1) throws JSONException, IOException {
        i_want_to_set_the_todo_with_id_as_done(int1);
    }


}
