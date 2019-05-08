package ru.veeam.hamcrest.matchers;

import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.WebElementsCollection;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.codeborne.selenide.ElementsCollection.elementsToString;

public class ListSizeMismatch extends UIAssertionError {

    public ListSizeMismatch(String operator, int expectedSize, WebElementsCollection collection,
                            List<WebElement> actualElements, Exception lastError, long timeoutMs) {
        super(": ожидаемое: " + operator + " " + expectedSize +
                ", фактическое: " + (actualElements == null ? 0 : actualElements.size()) +
                ", коллекция: " + collection.description() +
                "\nЭлементы: " + elementsToString(actualElements), lastError
        );
        super.timeoutMs = timeoutMs;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + getMessage() + uiDetails();
    }
}
