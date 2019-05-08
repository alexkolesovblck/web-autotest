package ru.veeam.hamcrest.collections;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.impl.WebElementsCollection;
import org.openqa.selenium.WebElement;
import ru.veeam.hamcrest.matchers.TextsMismatch;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;


public class MpContainsIsAnyOrder extends ru.veeam.hamcrest.MpCollectionCondition {
    protected final List<String> expectedTexts;

    public MpContainsIsAnyOrder(String... expectedTexts) {
        this(asList(expectedTexts));
    }

    public MpContainsIsAnyOrder(List<String> expectedTexts) {
        if (expectedTexts.isEmpty()) {
            throw new IllegalArgumentException("текст отсутствует");
        }
        this.expectedTexts = unmodifiableList(expectedTexts);
    }

    @Override
    public boolean apply(List<WebElement> elements) {
        for (String expectedText : expectedTexts) {
            if (elements.stream().filter(webElement -> webElement.getText().contains(expectedText)).collect(Collectors.toList()).size() == 0) {
                return false;
            }
        }
        return true;
    }


    @Override
    public void fail(WebElementsCollection collection, List<WebElement> elements, Exception lastError, long timeoutMs) {
        if (elements == null || elements.isEmpty()) {
            ElementNotFound elementNotFound = new ElementNotFound(collection, expectedTexts, lastError);
            elementNotFound.timeoutMs = timeoutMs;
            throw elementNotFound;
        } else {
            throw new TextsMismatch(collection, ElementsCollection.texts(elements), expectedTexts, timeoutMs);
        }
    }

    @Override
    public String toString() {
        return "текст содержит " + expectedTexts;
    }
}
