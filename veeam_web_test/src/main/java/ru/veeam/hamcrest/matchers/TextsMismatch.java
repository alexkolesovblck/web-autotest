package ru.veeam.hamcrest.matchers;

import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.WebElementsCollection;

import java.util.List;

public class TextsMismatch extends UIAssertionError {

    public TextsMismatch(WebElementsCollection collection, List<String> actualTexts,
                         List<String> expectedTexts, long timeoutMs) {
        super("\nФактическое: " + actualTexts +
                "\nОжидаемое: " + expectedTexts +
                "\nКоллекция: " + collection.description());
        super.timeoutMs = timeoutMs;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ' ' + getMessage() + uiDetails();
    }
}
