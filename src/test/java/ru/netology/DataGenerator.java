package ru.netology;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    static void sendForRegistration(PersonGenerator person) {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        given()
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(person) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    static PersonGenerator createWithoutRegistration(String locale) {
        PersonGenerator person = new PersonGenerator(locale);
        person.generatePersonWithValidPassword();
        return person;
    }

    static PersonGenerator getPersonWithInvalidLogin(String locale) {
        PersonGenerator person = new PersonGenerator(locale);
        person.generatePersonWithValidPassword();
        DataGenerator.sendForRegistration(person);
        PersonGenerator person2 = new PersonGenerator(locale);
        person2.generatePersonWithValidPassword();
        person2.setPassword(person.getPassword());
        return person2;
    }

    static PersonGenerator getPersonWithInvalidPassword(String locale) {
        PersonGenerator person = new PersonGenerator(locale);
        person.generatePersonWithValidPassword();
        DataGenerator.sendForRegistration(person);
        PersonGenerator person2 = new PersonGenerator(locale);
        person2.generatePersonWithValidPassword();
        person2.setLogin(person.getLogin());
        return person2;
    }

    static PersonGenerator getPersonWithBlockedStatus(String locale) {
        PersonGenerator person = new PersonGenerator(locale);
        person.generatePersonWithBlockedStatus();
        DataGenerator.sendForRegistration(person);
        return person;
    }

    static PersonGenerator getValidPerson(String locale) {
        PersonGenerator person = new PersonGenerator(locale);
        person.generatePersonWithValidPassword();
        DataGenerator.sendForRegistration(person);
        return person;
    }
}