import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PlaygroundTests {

    @Test
    public void getPlaygroundTest() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Mikhail");

        JsonPath response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .jsonPath();

        String answer = response.get("answer");
        System.out.println(answer);
    }

    @Test
    public void postPlaygroundTest() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "Mikhail");
        body.put("position", "QA");

        Response response = RestAssured
                .given()
                .queryParams(body)
                .post("https://playground.learnqa.ru/api/check_type")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println(statusCode);
        response.print();
    }

    @Test
    public void headersPlaygroundTest() {
        Map<String, String> headers = new HashMap<>();
        headers.put("myHeader1", "myValue1");
        headers.put("myHeader2", "myValue2");

        Response response = RestAssured
                .given()
                .headers(headers)
                .redirects()
                .follow(false)
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        response.prettyPrint();

        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

       String locationHeader = response.getHeader("Location");
       System.out.println(locationHeader);
    }
}
