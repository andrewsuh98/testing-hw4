import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.http.ContentType.JSON;

public class JsonplaceholderRestAPITest {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    @Test
    public void testAlbumsContainsEntryWithTitle() {
        String endpoint = "/albums";
        String query = BASE_URL + endpoint;

        when().
                get(query).
        then().
                contentType(JSON).
                body("title", hasItem("omnis laborum odio"));
    }

    @Test
    public void testCommentsContainsAtLeast200Comments() {
        String endpoint = "/comments";
        String query = BASE_URL + endpoint;

        when().
                get(query).
        then().
                contentType(JSON).
                body("size()", greaterThan(199));
    }

    @Test
    public void testUsersErvinHowellExists() {
        String endpoint = "/users";
        String query = BASE_URL + endpoint;

        boolean contains = false;

        JsonPath j = when().get(query).jsonPath();

        int size = j.getInt("$.size()");
        for (int i = 0; i < size; i++) {
            String name = j.getString("["+i+"].name");
            String username = j.getString("["+i+"].username");
            String zipcode = j.getString("["+i+"].address.zipcode");

            System.out.println(name);
            System.out.println(username);
            System.out.println(zipcode);

            if (name.equals("Ervin Howell") && username.equals("Antonette") && zipcode.equals("90566-7771")) {
                contains = true;
            }
        }

        assertTrue(contains);
    }

    @Test
    public void testCommentsEmailAddresses() {
        String endpoint = "/comments";
        String query = BASE_URL + endpoint;

        boolean contains = false;

        JsonPath j = when().get(query).jsonPath();

        int size = j.getInt("$.size()");
        for (int i = 0; i < size; i++) {
            String email = j.getString("["+i+"].email");

            if (email.endsWith(".biz")) {
                contains = true;
            }
        }

        assertTrue(contains);
    }

    @Test
    public void testPostsSendPostRequest() {
        String endpoint = "/posts";
        String query = BASE_URL + endpoint;

        String requestBody = "{\n" +
                "  \"userId\": \"11\",\n" +
                "  \"id\": \"101\",\n" +
                "  \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\" \n +" +
                "  \"body\": \"quia et suscipit\n" +
                "suscipit recusandae consequuntur expedita et cum\n" +
                "reprehenderit molestiae ut ut quas totam\n" +
                "nostrum rerum est autem sunt rem eveniet architecto\"}";

        Response response = given()
                .body(requestBody)
                .when()
                .post(query)
                .then()
                .extract().response();

        assertEquals(201, response.statusCode());
    }
}

