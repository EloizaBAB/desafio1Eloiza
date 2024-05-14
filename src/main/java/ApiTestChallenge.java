import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;
public class TestApiChallenge {

    String responseSchema = "{" +
            "\"type\": \"array\"," +
            "\"items\": {" +
            "\"type\": \"object\"," +
            "\"properties\": {" +
            "\"id\": {\"type\": \"number\"}," +
            "\"user_id\": {\"type\": \"number\"}," +
            "\"title\": {\"type\": \"string\"}," +
            "\"due_on\": {\"type\": \"string\", \"format\": \"date-time\"}," +
            "\"status\": {\"type\": \"string\"}" +
            "}," +
            "\"required\": [\"id\", \"user_id\", \"title\", \"due_on\", \"status\"]" +
            "}" +
            "}";
    public void automateApiTest(){
        RestAssured.baseURI= "https://gorest.co.in/";

        // Send a GET request to retrieve todos

        Response response = given().when().get("/todos");

        //Validate status code

        response.then().statusCode(200);

        //Validate body response schema/JSON

        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(responseSchema));

        //validate if all todos have status equals to completed

        response.then().assertThat().body("data.status", everyItem(equalTo("completed")));

        //Validate that due_on is not null
        response.then().assertThat().body("data.due_on",everyItem(notNullValue()));

    }




    public static void main(String[] args) {
        TestApiChallenge tests = new TestApiChallenge();
        tests.automateApiTest();
    }
}