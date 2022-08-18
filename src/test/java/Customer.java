import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Customer {
    Properties prop = new Properties();
    FileInputStream file;

    {
        try {
            file = new FileInputStream("./src/test/resources/config.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void callingLoginAPI() throws ConfigurationException, IOException {
        prop.load(file);
        CustomerModel customerModel = new CustomerModel("salman@grr.la", "1234");
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res = given().contentType("application/json")
                .body(customerModel)
                .when()
                .post("/user/login")
                .then().assertThat().statusCode(200).extract().response();

        JsonPath jsonpath = res.jsonPath();
        String token = jsonpath.get("token");
        System.out.println(token);
        Utils.setEnvVariable("token", token);
    }

    public void callingGetAPI() throws IOException {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        Response res = given().contentType("application/json").header("Authorization", prop.getProperty("token")).when().get("/user/list").then().assertThat().statusCode(200).extract().response();

        JsonPath response = res.jsonPath();
        Assert.assertEquals(response.get("users[0].id").toString(), "33");
    }
}
