package ru.netology;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class OrderCardDeliveryTest {

    @BeforeAll
    static void setUpAll() {
        Configuration.headless = true;
    }

    @BeforeEach
    void setupTest() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSubmitValidData() {
        $("[data-test-id=city] .input__control").setValue("Ярославль");
        $("div [data-test-id='date'] input").setValue("14.09.2022");
        $("[data-test-id = name] .input__control").setValue("Ирина Ким");
        $("[data-test-id = phone] .input__control").setValue("+79600000000");
        $("[data-test-id = agreement]").click();
        $(".button").click();
        $("[data-test-id = notification]").shouldHave(text("Успешно"), Duration.ofSeconds(15));
    }
}