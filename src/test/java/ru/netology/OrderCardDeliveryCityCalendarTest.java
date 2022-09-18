package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class OrderCardDeliveryCityCalendarTest {
    public static String setLocalDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy",
                new Locale("ru")));
    }

    public String substitutedData(String date) {
        String substitutedData;
        if (date.substring(0, 1).contains("0")) {
            substitutedData = date.substring(1, 2);
        } else {
            substitutedData = date.substring(0, 2);
        }
        return substitutedData;
    }

    @BeforeAll
    static void setUpAll() {
        Configuration.headless = true;
    }

    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldEnterCity() {
        $("[data-test-id=city] .input__control").setValue("Мо");
        $$(".menu-item").first().click();
        String date = setLocalDate(3);
        $("[data-test-id = date] .input__control").doubleClick().sendKeys(date);
        $("[data-test-id = name] .input__control").setValue("Ирина Ким");
        $("[data-test-id = phone] .input__control").setValue("+79600000000");
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = notification]").shouldHave(text("Успешно!"),
                        Duration.ofSeconds(15)).shouldBe(visible);
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void shouldEnterDateFromCalendar() {
        $("[data-test-id=city] .input__control").setValue("Яр");
        $$(".menu-item").first().click();
        $(".input__icon").click();
        int addDay = 3;
        String date = setLocalDate(3);
        String calendarDay = substitutedData(date);
        if (LocalDate.now().plusDays(addDay).getMonthValue() > LocalDate.now().getMonthValue()) {
            $("[data-step='1'].calendar__arrow_direction_right ").click();
            $$(".calendar__day").find(text(calendarDay)).click();
        } else {
            $$(".calendar__day").find(text(calendarDay)).click();
        }
        $("[data-test-id = name] .input__control").setValue("Ирина Ким");
        $("[data-test-id = phone] .input__control").setValue("+79600000000");
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = notification]").shouldHave(text("Успешно!"),
                        Duration.ofSeconds(15)).shouldBe(visible);
        $(".notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                        Duration.ofSeconds(15)).shouldBe(visible);
    }
}