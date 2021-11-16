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
    // Создаем дата-класс пользователя, а также при помощи Lombok автоматически создаем конструктор
    // на основе этого класса будут созданы пользователи с разным статусом (через конструктор напрямую инициилизируем поля)

    @Value
    public static class UserInfo {
        String login;
        String password;
        String status;
    }

    // Создадим объект класса faker c заданной локалью
    private static final Faker faker = new Faker(new Locale("en"));

    // Универсальные методы генерации данных (логина и пароля),а для этого перед этим создадим объект класса faker c заданной локалью
    public static String getLogin (){
        return faker.name().username();
    }

    public static String getPassword (){
        return faker.internet().password();
    }

    // Создаем активного пользователя
    public static UserInfo createActiveUser(){
        UserInfo user = new UserInfo(getLogin(),getPassword(),"active");
        sendRequestToCreateUser (user); // отправь логин,пароль и статус в тело запроса
        return user; // эти же данные используй в тесте
    }

    // Создаем заблокированного пользователя
    public static UserInfo createBlockedUser (){
        UserInfo user = new UserInfo(getLogin(),getPassword(),"blocked");
        sendRequestToCreateUser (user);  // отправь логин,пароль и статус в тело запроса
        return user; // эти же данные используй в тесте
    }

    // Данные пользователей теперь необходимо передать в тело запроса. Для этого создадим класс, который отправляет запросы к SUT

    // Спецификация, которая используется при отправке запроса (указывается в .spec)
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void sendRequestToCreateUser(UserInfo user) {
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }
}