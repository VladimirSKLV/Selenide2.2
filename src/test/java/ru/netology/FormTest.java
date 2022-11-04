package ru.netology;


import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

import java.time.Duration;
import java.time.LocalDate;


public class FormTest {

    @Test
    void inputValidDataSuccess() {
        open("http://localhost:9999");

        //создаем переменную с датой на 3 дня вперед текущей
        LocalDate date = LocalDate.now().plusDays(3);
        String myDate = String.format("%02d.%02d.%d", date.getDayOfMonth(), date.getMonthValue(), date.getYear());

        //Заполняем форму
        $("[data-test-id=city] input").setValue("Нижний Новгород");
        $("[data-test-id=date] input").click();
        $("[data-test-id=date] input").sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").sendKeys("myDate");
        $("[data-test-id=name] input").setValue("Маркиз-Деполь");
        $("[data-test-id=phone] input").setValue("+79991234567");
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Забронировать")).click();

        //Начинается ожидание и далее проверка
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=notification] .notification__title").should(Condition.exactText("Успешно!"));
        $("[data-test-id=notification] .notification__content").should(Condition.exactText("Встреча успешно забронирована на " + myDate));
    }
}
