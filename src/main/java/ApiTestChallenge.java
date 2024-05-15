import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;


import io.cucumber.core.internal.com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;


import java.util.List;


public class ApiTestChallenge {


    public void automateApiTest(){
        RestAssured.baseURI= "https://gorest.co.in";


        // Send a GET request to retrieve todos


        Response response = given().when().get("public/v2/todos");


        //Validate status code


        response.then().statusCode(200);


        //Validate body response schema/JSON


        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemaJson"));


        // filter the response array to include only the fields with completed status and validate if all todos have status equals to completed


        List<String> completedStatusList = response.jsonPath().getList("findAll { it.status == 'completed' }.status");
        assertThat(completedStatusList, everyItem(equalTo("completed")));


        //Validate that due_on is not null, due_on seems to be a date
        response.then().assertThat().body("due_on",everyItem(notNullValue()));


    }



    public static void main(String[] args) {
        ApiTestChallenge tests = new ApiTestChallenge();
        tests.automateApiTest();
    }
}
