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

     @Given("categories with ID {int} exists")
     public void category_with_id_exists(Integer int1) throws JSONException, IOException {
         JSONObject category = HTTP.get(url + "/" + int1);
         assertNotNull(category);
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

     @Given("a non existent category with ID {int}")
     public void a_non_existent_category_with_id(Integer int1) throws JSONException, IOException {
         JSONObject category = HTTP.get(url + "/" + int1);
         assertNull(category);
     }

     @When("I want to view the details of categories with ID {int}")
    public void i_want_to_view_the_details_of_category_with_id(Integer int1) throws JSONException, IOException {
        response = HTTP.getResponse(url + "/" + int1);
        category = HTTP.get(url + "/" + int1);
    }

}
