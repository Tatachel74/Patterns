package ru.netology;
import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class IBankTest {
    @Test
    void shouldValidPersonTest() {
        PersonGenerator person = DataGenerator.getValidPerson("en");
        open("http://localhost:9999");
        $("[data-test-id=login] .input__control").setValue(person.getLogin());
        $("[data-test-id=password] .input__control").setValue(person.getPassword());
        $("[data-test-id=action-login]").click();
        $(".heading").shouldHave(Condition.exactText("Личный кабинет"));
    }

    @Test
    void shouldInvalidLoginTest() {
        PersonGenerator person = DataGenerator.getPersonWithInvalidLogin("en");
        open("http://localhost:9999");
        $("[data-test-id=login] .input__control").setValue(person.getLogin());
        $("[data-test-id=password] .input__control").setValue(person.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.text("Ошибка!"));
    }

    @Test
    void shouldInvalidPasswordTest() {
        PersonGenerator person = DataGenerator.getPersonWithInvalidPassword("en");
        open("http://localhost:9999");
        $("[data-test-id=login] .input__control").setValue(person.getLogin());
        $("[data-test-id=password] .input__control").setValue(person.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.text("Ошибка!"));
    }

    @Test
    void shouldBlockedStatusTest() {
        val person = DataGenerator.getPersonWithBlockedStatus("en");
        open("http://localhost:9999");
        $("[data-test-id=login] .input__control").setValue(person.getLogin());
        $("[data-test-id=password] .input__control").setValue(person.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.text("заблокирован"));
    }

    @Test
    void shouldNotExistPersonTest() {
        PersonGenerator person = DataGenerator.createWithoutRegistration("en");
        open("http://localhost:9999");
        $("[data-test-id=login] .input__control").setValue(person.getLogin());
        $("[data-test-id=password] .input__control").setValue(person.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(Condition.text("Ошибка!"));
    }

    @Test
    void shouldWithoutPasswordTest() {
        PersonGenerator person = DataGenerator.getValidPerson("en");
        open("http://localhost:9999");
        $("[data-test-id=login] .input__control").setValue(person.getLogin());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldWithoutLoginTest() {
        PersonGenerator person = DataGenerator.getValidPerson("en");
        open("http://localhost:9999");
        $("[data-test-id=password] .input__control").setValue(person.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login] .input__sub").shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}