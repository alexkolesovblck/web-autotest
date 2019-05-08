package ru.veeam.hamcrest.collections;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.impl.WebElementsCollection;
import org.openqa.selenium.WebElement;
import ru.veeam.hamcrest.matchers.TextsMismatch;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class MpContainsIsAnyOrderByAttribute extends ru.veeam.hamcrest.MpCollectionCondition {
    protected final List<String> expectedTexts;
    protected String attribute = "";

    public MpContainsIsAnyOrderByAttribute(String attr, String... expectedTexts) {
        this.expectedTexts = (asList(expectedTexts));
        this.attribute = attr;
    }

    public MpContainsIsAnyOrderByAttribute(String attr, List<String> expectedTexts) {
        if (expectedTexts.isEmpty()) {
            throw new IllegalArgumentException("текст отсутствует");
        }
        if (attr.isEmpty()) {
            throw new IllegalArgumentException("аттрибут отсутствует");
        }
        this.expectedTexts = unmodifiableList(expectedTexts);
        this.attribute = attr;
    }

    @Override
    public boolean apply(List<WebElement> elements) {
        for (String expectedText : expectedTexts) {
            if (elements.stream().filter(webElement -> webElement.getAttribute(attribute).contains(expectedText)).collect(Collectors.toList()).size() != 1) {
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

    @Override
    public Predicate<List<WebElement>> and(Predicate<? super List<WebElement>> other) {
        return null;
    }
}
