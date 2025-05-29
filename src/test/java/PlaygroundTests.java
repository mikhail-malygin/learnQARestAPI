import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaygroundTests {

    @Test
    public void getPlaygroundTest() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Mikhail");

        Response response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello")
                .andReturn();

        assertEquals(200, response.getStatusCode(), "An unexpected status");
        assertEquals("Hello, Mikhail", response.path("answer"), "A wrong greetings");
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

        assertEquals(200, response.getStatusCode(), "An unexpected status");
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
                .follow(true)
                .get("https://playground.learnqa.ru/api/get_303")
                .andReturn();

        response.prettyPrint();

        Headers responseHeaders = response.getHeaders();
        System.out.println(responseHeaders);

       String locationHeader = response.getHeader("Location");
       System.out.println(locationHeader);

        assertEquals(200, response.getStatusCode(), "An unexpected status");
        assertEquals("Hello, someone", response.path("answer"), "A wrong greetings");
    }

    @Test
    public void authPlaygroundTest() {
        Map<String, String> data = new HashMap<>();
        data.put("login", "secret_login");
        data.put("password", "secret_pass");

        Response responseForGet = RestAssured
                .given()
                .queryParams(data)
                .post("https://playground.learnqa.ru/api/get_auth_cookie")
                .andReturn();

        String responseCookie = responseForGet.getCookie("auth_cookie");

        Map<String, String> cookies = new HashMap<>();
        if (responseCookie != null) {
            cookies.put("auth_cookie", responseCookie);
        }

        Response responseForCheck = RestAssured
                .given()
                .cookies(cookies)
                .post("https://playground.learnqa.ru/api/check_auth_cookie")
                .andReturn();

        Document doc = Jsoup.parse(responseForCheck.getBody().asString());
        String bodyText = doc.body().text();

        assertEquals(200, responseForCheck.getStatusCode(), "An unexpected status");
        assertEquals("You are authorized", bodyText, "A wrong authorized message");
    }
}
