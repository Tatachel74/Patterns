package ru.netology;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import lombok.Value;


import java.util.Locale;

public class DataGenerator {
    public DataGenerator() {
    }


    @Value
    public static class UserInfo {
        String login;
        String password;
        String status;
    }

    private static final Faker faker = new Faker(new Locale("en"));
    public static String getLogin (){
        return faker.name().username();
    }

    public static String getPassword (){
        return faker.internet().password();
    }

    public static class Registration {
    }

        public static UserInfo getUser(String status) {
            return new UserInfo(getLogin(), getPassword(), status);
        }

        public static UserInfo getRegisteredUser(String status) {
            UserInfo registeredUser = getUser(status);
            sendRequestToCreateUser(registeredUser);
            return registeredUser;
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void sendRequestToCreateUser(UserInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}