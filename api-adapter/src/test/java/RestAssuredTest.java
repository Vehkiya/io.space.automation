import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RestAssuredTest {

    private final String url = "http://localhost:8080";

    private final String helloEndpoint = "/hello";

    private final String beansEndpoint = "/beans";

    @Test
    void getRequest() {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(url)
                .addParam("name", "Bob")
                .build();

        String response = RestAssured.given()
                .spec(requestSpecification)
                .get(helloEndpoint)
                .body()
                .asString();

        Assertions.assertEquals(response, "Hello Bob");
    }
}
