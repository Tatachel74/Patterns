package ru.netology;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.*;

public class IBankTest {
    @BeforeEach
    void setUp() {
        Configuration.browser = "chrome";
        //Открытие формы
        open("http://localhost:9999");
    }
    // 1 - Наличие активного пользователя (успешный вход)
    @Test
    public void shouldLoginRegisteredUser() {
        DataGenerator.UserInfo activeUser = createActiveUser();
        $("[data-test-id=login] .input__control").setValue(activeUser.getLogin());
        $("[data-test-id=password] .input__control").setValue(activeUser.getPassword());
        $("[data-test-id=action-login]").click();
        $(Selectors.withText("Личный кабинет")).shouldBe(visible);
    }

    // 2 - Наличие заблокированного пользователя
    @Test
    public void shouldGetErrorIfUserIsBlocked() {
        DataGenerator.UserInfo blockedUser = createBlockedUser();
        $("[data-test-id=login] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id=password] .input__control").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Пользователь заблокирован"));
    }

    // 3 - Отсутствие пользователя
    @Test
    public void shouldGetErrorIfUserDoesNotExist() {
        $("[data-test-id=login] .input__control").setValue(getLogin());
        $("[data-test-id=password] .input__control").setValue(getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    // 4 - Невалидный логин активного пользователя
    @Test
    public void shouldGetErrorIfActiveUserWithInvalidLogin() {
        DataGenerator.UserInfo activeUser = createActiveUser();
        $("[data-test-id=login] .input__control").setValue(getLogin());
        $("[data-test-id=password] .input__control").setValue(activeUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    // 5 - Невалидный пароль активного пользователя
    @Test
    public void shouldGetErrorIfActiveUserWithInvalidPassword() {
        DataGenerator.UserInfo activeUser = createActiveUser();
        $("[data-test-id=login] .input__control").setValue(activeUser.getLogin());
        $("[data-test-id=password] .input__control").setValue(getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    // 6 - Невалидный логин заблокированного пользователя
    @Test
    public void shouldGetErrorIfBlockedUserWithInvalidLogin() {
        DataGenerator.UserInfo blockedUser = createBlockedUser();
        $("[data-test-id=login] .input__control").setValue(getLogin());
        $("[data-test-id=password] .input__control").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    // 7 - Невалидный пароль заблокированного пользователя
    @Test
    public void shouldGetErrorIfBlockedUserWithInvalidPassword() {
        DataGenerator.UserInfo blockedUser = createBlockedUser();
        $("[data-test-id=login] .input__control").setValue(blockedUser.getLogin());
        $("[data-test-id=password] .input__control").setValue(getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(visible).find(".notification__title").shouldHave(exactText("Ошибка"));
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

}
