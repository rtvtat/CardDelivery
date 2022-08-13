package ru.netology;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class TestCardDelivery {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    public void testPositiveTest() {
        String dateOrder = LocalDate.now().plusDays(3).format(formatter);

        open("http://localhost:9999");
        $("span[data-test-id='city'] .input__control").setValue("Белгород");
        $("span[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "A");
        $("span[data-test-id='date'] .input__control").sendKeys(Keys.DELETE);
        $("span[data-test-id='date'] .input__control").sendKeys(dateOrder);

        $("span[data-test-id='name'] .input__control").setValue("Иванова Евгения");
        $("span[data-test-id='phone'] .input__control").setValue("+79278887766");
        $("label[data-test-id='agreement']").click();
        $x("//button //span[contains(text(), 'Забронировать')]/../..").click();
        $x("//button//span[contains(text(), 'Забронировать')]//..//span[contains(@class, 'spin_visible')]")
                .should(Condition.exist);
        $(".notification__title").should(Condition.visible, Duration.ofSeconds(15));
    }

    @Test
    public void testEmptyCity() {
        open("http://localhost:9999");
        $("span[data-test-id='city'] .input__control").setValue("");
        $("span[data-test-id='date'] .input__control").setValue("14.08.2022");
        $("span[data-test-id='name'] .input__control").setValue("Иванова Евгения");
        $("span[data-test-id='phone'] .input__control").setValue("+79278887766");
        $("label[data-test-id='agreement']").click();
        $x("//button //span[contains(text(), 'Забронировать')]/../..").click();
        $x("//span[@data-test-id='city']//span[contains(@class, 'input__sub')]")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void testIncorrectCity() {
        open("http://localhost:9999");
        $("span[data-test-id='city'] .input__control").setValue("Новочебоксарск");
        $("span[data-test-id='date'] .input__control").setValue("14.08.2022");
        $("span[data-test-id='name'] .input__control").setValue("Иванова Евгения");
        $("span[data-test-id='phone'] .input__control").setValue("+79278887766");
        $("label[data-test-id='agreement']").click();
        $x("//button //span[contains(text(), 'Забронировать')]/../..").click();
        $x("//span[@data-test-id='city']//span[contains(@class, 'input__sub')]")
                .shouldHave(Condition.exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    public void testEmptyDate() {
        open("http://localhost:9999");
        $("span[data-test-id='city'] .input__control").setValue("Белгород");
        $("span[data-test-id='name'] .input__control").setValue("Иванова Евгения");
        $("span[data-test-id='phone'] .input__control").setValue("+79278887766");
        $("label[data-test-id='agreement']").click();
        $("span[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "A");
        $("span[data-test-id='date'] .input__control").sendKeys(Keys.DELETE);
        $x("//button //span[contains(text(), 'Забронировать')]/../..").click();
        $x("//span[@data-test-id='date']//span[contains(@class, 'input__sub')]")
                .shouldHave(Condition.exactText("Неверно введена дата"));
    }

    @Test
    public void testIncorrectDate() {
        open("http://localhost:9999");
        $("span[data-test-id='city'] .input__control").setValue("Белгород");
        $("span[data-test-id='name'] .input__control").setValue("Иванова Евгения");
        $("span[data-test-id='phone'] .input__control").setValue("+79278887766");
        $("label[data-test-id='agreement']").click();
        $("span[data-test-id='date'] .input__control").sendKeys(Keys.CONTROL + "A");
        $("span[data-test-id='date'] .input__control").sendKeys(Keys.DELETE);
        $("span[data-test-id='date'] .input__control").sendKeys("01.01.2022");
        $x("//button //span[contains(text(), 'Забронировать')]/../..").click();
        $x("//span[@data-test-id='date']//span[contains(@class, 'input__sub')]")
                .shouldHave(Condition.exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    public void testEmptyName() {
        open("http://localhost:9999");
        $("span[data-test-id='city'] .input__control").setValue("Белгород");
        $("span[data-test-id='date'] .input__control").setValue("14.08.2022");
        $("span[data-test-id='name'] .input__control").setValue("");
        $("span[data-test-id='phone'] .input__control").setValue("+79278887766");
        $("label[data-test-id='agreement']").click();
        $x("//button //span[contains(text(), 'Забронировать')]/../..").click();
        $x("//span[@data-test-id='name']//span[contains(@class, 'input__sub')]")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void testIncorrectName() {
        open("http://localhost:9999");
        $("span[data-test-id='city'] .input__control").setValue("Белгород");
        $("span[data-test-id='date'] .input__control").setValue("14.08.2022");
        $("span[data-test-id='name'] .input__control").setValue("Ivanova Evgenia");
        $("span[data-test-id='phone'] .input__control").setValue("+79278887766");
        $("label[data-test-id='agreement']").click();
        $x("//button //span[contains(text(), 'Забронировать')]/../..").click();
        $x("//span[@data-test-id='name']//span[contains(@class, 'input__sub')]")
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void testEmptyPhone() {
        open("http://localhost:9999");
        $("span[data-test-id='city'] .input__control").setValue("Белгород");
        $("span[data-test-id='date'] .input__control").setValue("14.08.2022");
        $("span[data-test-id='name'] .input__control").setValue("Иванова Евгения");
        $("span[data-test-id='phone'] .input__control").setValue("");
        $("label[data-test-id='agreement']").click();
        $x("//button //span[contains(text(), 'Забронировать')]/../..").click();
        $x("//span[@data-test-id='phone']//span[contains(@class, 'input__sub')]")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void testIncorrectPhone() {
        open("http://localhost:9999");
        $("span[data-test-id='city'] .input__control").setValue("Белгород");
        $("span[data-test-id='date'] .input__control").setValue("14.08.2022");
        $("span[data-test-id='name'] .input__control").setValue("Иванова Евгения");
        $("span[data-test-id='phone'] .input__control").setValue("79278887766");
        $("label[data-test-id='agreement']").click();
        $x("//button //span[contains(text(), 'Забронировать')]/../..").click();
        $x("//span[@data-test-id='phone']//span[contains(@class, 'input__sub')]")
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void testEmptyAgreement() {
        open("http://localhost:9999");
        $("span[data-test-id='city'] .input__control").setValue("Белгород");
        $("span[data-test-id='date'] .input__control").setValue("14.08.2022");
        $("span[data-test-id='name'] .input__control").setValue("Иванова Евгения");
        $("span[data-test-id='phone'] .input__control").setValue("+79278887766");
        $x("//button //span[contains(text(), 'Забронировать')]/../..").click();
        $(".input_invalid[data-test-id='agreement']").shouldHave(Condition.visible);
    }


}
