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
        response = HTTP.getResponse(url);
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

    @Then("I should see an error message indicating that the todo with ID {int} wasn't found")
    public void i_should_see_an_error_message_indicating_that_the_todo_with_id_wasn_t_found(Integer int1) throws IOException {
        assertNotNull(response);
        assertEquals(response.code(), 404);
        assertEquals(response.message(), "Not Found");

        assertNotNull(response.body());
        String body = response.body().string();
        System.out.println(body);

        assertEquals(body, String.format("{\"errorMessages\":[\"Could not find any instances with todos/%s\"]}", int1));
    }

    @Then("I should see an error message indicating that the todo with GUID {int} does not exist")
    public void i_should_see_an_error_message_indicating_that_the_todo_with_guid_does_not_exist(Integer int1) throws IOException {
        assertNotNull(response);
        assertEquals(response.code(), 404);
        assertEquals(response.message(), "Not Found");

        assertNotNull(response.body());
        String body = response.body().string();

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

    @Given("the title of todo {int} is {string}")
    public void the_title_of_todo_is(Integer int1, String string) throws JSONException, IOException {
        // Set the title of todo with ID int1 to string
        JSONObject setTitle = new JSONObject("{\"title\": \"" + string + "\"}");
        response = HTTP.postResponse(url + "/" + int1, setTitle);
        todo = HTTP.post(url + "/" + int1, setTitle);
    }

    @When("I want to set the title of todo with ID {int} to {string}")
    public void i_want_to_set_the_title_of_todo_with_id_to(Integer int1, String string) throws JSONException, IOException {
        the_title_of_todo_is(int1, string);
    }

    @Given("the description of todo {int} is {string}")
    public void the_description_of_todo_is(Integer int1, String string) throws JSONException, IOException {
        JSONObject setDescription = new JSONObject("{\"description\": \"" + string + "\"}");
        response = HTTP.postResponse(url + "/" + int1, setDescription);
        todo = HTTP.post(url + "/" + int1, setDescription);
    }

    @When("I want to set the description of todo with ID {int} to {string}")
    public void i_want_to_set_the_description_of_todo_with_id_to(Integer int1, String string) throws JSONException, IOException {
        the_description_of_todo_is(int1, string);
    }

    @When("I want to add a todo with title {string} and description {string}")
    public void i_want_to_add_a_todo_with_title_and_description(String string, String string2) throws JSONException, IOException {
        JSONObject addTodo = new JSONObject("{\n" +
                "    \"title\": \"" + string + "\",\n" +
                "    \"doneStatus\": false,\n" +
                "    \"description\": \""+ string2 + "\",\n" +
                "    \"tasksof\": []\n" +
                "}");

        response = HTTP.postResponse(url, addTodo);
        assertNotNull(response.body());

        if (response.isSuccessful()) {
            String id = new JSONObject(response.body().string()).getString("id");
            todo = HTTP.get(url + "/" + id);
        }
    }

    @Then("I should see a todo with title {string} and description {string}")
    public void i_should_see_a_todo_with_title_and_description(String string, String string2) throws JSONException, IOException {
        JSONObject todo = HTTP.get(url + "?title=" + string + "&description=" + string2);
        System.out.println(todo);
        assertNotNull(todo);

        String title = todo.getJSONArray("todos").getJSONObject(0).getString("title");
        assertEquals(title, string);

        String description = todo.getJSONArray("todos").getJSONObject(0).getString("description");
        assertEquals(description, string2);
    }

    @Given("todo with title {string} and description {string} exists")
    public void todo_with_title_and_description_exists(String string, String string2) throws JSONException, IOException {
        i_want_to_add_a_todo_with_title_and_description(string, string2);
    }

    @Then("I should see {int} todos with title {string} and description {string}")
    public void i_should_see_todos_with_title_and_description(Integer int1, String string, String string2) throws JSONException, IOException {
        JSONObject todo = HTTP.get(url + "?title=" + string + "&description=" + string2);
        System.out.println(todo);
        assertNotNull(todo);

        assertEquals(todo.getJSONArray("todos").length(), int1);
    }

    @Then("I should see an error message indicating that the title cannot be empty")
    public void i_should_see_an_error_message_indicating_that_the_title_cannot_be_empty() throws IOException {
        assertNotNull(response);
        assertEquals(response.code(), 400);
        assertEquals(response.message(), "Bad Request");

        assertNotNull(response.body());
        String body = response.body().string();

        assertEquals(body, "{\"errorMessages\":[\"Failed Validation: title : can not be empty\"]}");
    }

    @When("I want to remove a todo with ID {int}")
    public void i_want_to_remove_a_todo_with_id(Integer int1) throws IOException, JSONException {
        response = HTTP.deleteResponse(url + "/" + int1);
        todo = HTTP.get(url + "/" + int1);
    }

    @Then("I should not see a todo with ID {int}")
    public void i_should_not_see_a_todo_with_id(Integer int1) throws IOException, JSONException {
        assertNotNull(response);
        assertNull(todo);
        assertEquals(response.code(), 200);
        assertEquals(response.message(), "OK");

        JSONObject todo = HTTP.get(url + "/" + int1);
        assertNull(todo);
    }

    @Then("I should see a todo with ID {int}")
    public void i_should_see_a_todo_with_id(Integer int1) throws JSONException, IOException {
        JSONObject todo = HTTP.get(url + "/" + int1);
        assertNotNull(todo);
        assertEquals(todo.getJSONArray("todos").getJSONObject(0).getString("id"), String.valueOf(int1));
    }

}
