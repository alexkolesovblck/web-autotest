package ru.veeam.hamcrest.matchers;

import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.WebElementsCollection;

import java.util.List;

public class SimsListMismatch extends UIAssertionError {

    public SimsListMismatch(WebElementsCollection collection, List<String> actualSims,
                            List<String> expectedSims, long timeoutMs) {
        super("\nФактическое: " + actualSims +
                "\nОжидаемое: " + expectedSims +
                "\nКоллекция: " + collection.description());
        super.timeoutMs = timeoutMs;
    }

}
