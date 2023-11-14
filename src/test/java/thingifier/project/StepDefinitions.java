package thingifier.project;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import thingifier.HTTP;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    private final String url = "http://localhost:4567/projects";
    private JSONObject project, projects;
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

        project = null;
        projects = null;
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

    /*
    US1: View Project Details
     */

    //Normal Acceptance Test
    @Given("project with ID {int} exists")
    public void project_with_id_exists(Integer int1) throws JSONException, IOException {
        JSONObject project = HTTP.get(url + "/" + int1);
        assertNotNull(project);
    }

    @Then("the title of project with ID {int} is {string}")
    public void the_title_of_project_with_id_is(Integer int1, String string2) throws JSONException, IOException {
        JSONObject project = HTTP.get(url + "/" + int1);
        assertNotNull(project);
        String title = project.getJSONArray("projects").getJSONObject(0).getString("title");
        assertEquals(title, string2);
    }

    @Then("the completed status of project with ID {int} is {string}")
    public void the_completed_status_of_project_with_id_is(Integer int1, String string) throws JSONException, IOException {
        JSONObject project = HTTP.get(url + "/" + int1);
        assertNotNull(project);
        boolean completed = project.getJSONArray("projects").getJSONObject(0).getBoolean("completed");
        assertEquals(completed, Boolean.parseBoolean(string));
    }


    @Then("the active status of project with ID {int} is {string}")
    public void the_active_status_of_project_with_id_is(Integer int1, String string) throws JSONException, IOException {
        JSONObject project = HTTP.get(url + "/" + int1);
        assertNotNull(project);
        boolean active = project.getJSONArray("projects").getJSONObject(0).getBoolean("active");
        assertEquals(active, Boolean.parseBoolean(string));
    }

    @Then("the description of project with ID {int} is {string}")
    public void the_description_of_project_with_id_is(Integer int1, String string) throws JSONException, IOException {
        JSONObject project = HTTP.get(url + "/" + int1);
        assertNotNull(project);
        String description = project.getJSONArray("projects").getJSONObject(0).getString("description");
        assertEquals(description, string);
    }

    //Alternate Flow Acceptance Test
    @Given("no project exists")
    public void no_project_exists() throws JSONException, IOException {
        // Delete all projects
        JSONObject projects = HTTP.get(url);
        assertNotNull(projects);

        JSONArray projectsArray = projects.getJSONArray("projects");
        for (int i = 0; i < projectsArray.length(); i++) {
            String id = projectsArray.getJSONObject(i).getString("id");
            HTTP.delete(url + "/" + id);
        }

        projects = HTTP.get(url);
        assertNotNull(projects);
        assertEquals(projects.getJSONArray("projects").length(), 0);
    }

    @When("I want to view the list of projects")
    public void i_want_to_view_the_list_of_projects() throws JSONException, IOException {
        projects = HTTP.get(url);
    }

    @Then("I should see an empty list of projects")
    public void i_should_see_an_empty_list_of_projects() throws JSONException {
        assertEquals(projects.getJSONArray("projects").length(), 0);
    }

    //Error Flow Acceptance Test

    @Given("a non existent project with ID {int}")
    public void a_non_existent_project_with_id(Integer int1) throws JSONException, IOException {
        JSONObject project = HTTP.get(url + "/" + int1);
        assertNull(project);
    }

    @When("I want to view the details of project with ID {int}")
    public void i_want_to_view_the_details_of_project_with_id(Integer int1) throws JSONException, IOException {
        response = HTTP.getResponse(url + "/" + int1);
    }

    @Then("I should see an error message indicating that the project with ID {int} does not exist")
    public void i_should_see_an_error_message_indicating_that_the_project_with_id_does_not_exist(Integer int1) throws IOException {
        assertNotNull(response);
        assertEquals(response.code(), 404);
        assertEquals(response.message(), "Not Found");

        assertNotNull(response.body());
        String body = response.body().string();
        System.out.println(body);

        assertEquals(body, String.format("{\"errorMessages\":[\"Could not find an instance with projects/%s\"]}", int1));
    }

    @Then("I should see an error message indicating that the project with GUID {int} does not exist")
    public void i_should_see_an_error_message_indicating_that_the_project_with_guid_does_not_exist(Integer int1) throws IOException {
        assertNotNull(response);
        assertEquals(response.code(), 404);
        assertEquals(response.message(), "Not Found");

        assertNotNull(response.body());
        String body = response.body().string();
        System.out.println(body);

        assertEquals(body, String.format("{\"errorMessages\":[\"No such project entity instance with GUID or ID %s found\"]}", int1));
    }

    /*
    US2: Mark a project as completed
     */

    @When("I want to set the project with ID {int} as completed")
    public void i_want_to_set_the_project_with_id_as_completed(Integer int1) throws JSONException, IOException {
        JSONObject setCompleted = new JSONObject("{\"completed\":true}");
        response = HTTP.postResponse(url + "/" + int1, setCompleted);
        project = HTTP.post(url + "/" + int1, setCompleted);
    }


    @When("I want to set the project with ID {int} as uncompleted")
    public void iWantToSetTheProjectWithIDAsUncompleted(Integer int1) throws JSONException, IOException {
        JSONObject setUncompleted = new JSONObject("{\"completed\":false}");
        response = HTTP.postResponse(url + "/" + int1, setUncompleted);
        project = HTTP.post(url + "/" + int1, setUncompleted);
    }

    /* US 3: Mark a project as active
     */

    @When("I want to set the project with ID {int} as active")
    public void iWantToSetTheProjectWithIDAsActive(Integer int1) throws JSONException, IOException {
        JSONObject setActive = new JSONObject("{\"active\":true}");
        response = HTTP.postResponse(url + "/" + int1, setActive);
        project = HTTP.post(url + "/" + int1, setActive);
    }

    @Given ("project with ID {int} is inactive")
    public void projectWithIDIsInactiveInteger(Integer int1) throws JSONException, IOException {
        JSONObject project = HTTP.get(url + "/" + int1);
        assertNotNull(project);
        boolean active = project.getJSONArray("projects").getJSONObject(0).getBoolean("active");
        assertEquals(active, false);
    }

    /* US 4: Update Project Title
     */
    @When("I want to set project with ID {int} title to {string}")
    public void i_want_to_set_project_with_id_title_to_string(Integer int1, String string) throws JSONException, IOException {
        JSONObject setTitle = new JSONObject("{\"title\":\"" + string + "\"}");
        response = HTTP.postResponse(url + "/" + int1, setTitle);
        project = HTTP.post(url + "/" + int1, setTitle);
    }

    @Then("the title of project with ID {int} should be updated successfully to {string}")
    public void the_title_of_project_with_id_should_be_updated_successfully_to_string(Integer int1, String string) throws JSONException, IOException {
        JSONObject project = HTTP.get(url + "/" + int1);
        assertNotNull(project);
        String title = project.getJSONArray("projects").getJSONObject(0).getString("title");
        assertEquals(title, string);
    }

    @When("I want to create a new project with title {string}")
    public void create_new_project_with_title_string(String string) throws JSONException, IOException {
        JSONObject createProject = new JSONObject("{\"title\":\"" + string + "\"}");
        response = HTTP.postResponse(url, createProject);
        project = HTTP.post(url, createProject);
    }

    @Then("I should see two projects with title {string}")
    public void i_should_see_two_projects_with_title_string(String string) throws JSONException, IOException {
        JSONObject project = HTTP.get(url+"/title?"+string);
        assertNotNull(project);
        JSONArray projects = project.getJSONArray("projects");
        assertEquals(projects.length(), 2);
    }

    /* US 5: Update Project Description
     */
    @When("I want to set the description of project with ID {int} to {string}")
    public void set_description_project_id_to_string(Integer int1, String string) throws JSONException, IOException {
        JSONObject setDescription = new JSONObject("{\"description\":\"" + string + "\"}");
        response = HTTP.postResponse(url + "/" + int1, setDescription);
        project = HTTP.post(url + "/" + int1, setDescription);
    }

    @Then("the description of project with ID {int} should be updated successfully to {string}")
    public void description_project_id_updated_successfully_to_string(Integer int1, String string) throws JSONException, IOException {
        JSONObject project = HTTP.get(url + "/" + int1);
        assertNotNull(project);
        String description = project.getJSONArray("projects").getJSONObject(0).getString("description");
        assertEquals(description, string);
    }

    @Then("I should see multiple projects with the description {string}")
    public void multiple_projects_with_description_string(String string) throws JSONException, IOException {
        JSONObject project = HTTP.get(url + "/description?" + "\"" + string + "\"");
        assertNotNull(project);
        JSONArray projects = project.getJSONArray("projects");
        boolean multiple = projects.length() > 1;
        assertTrue(multiple);
    }










}

