package ru.veeam.hamcrest.collections;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ex.ElementNotFound;
import com.codeborne.selenide.impl.WebElementsCollection;
import org.openqa.selenium.WebElement;
import ru.veeam.hamcrest.matchers.TextsMismatch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class MpContainsIsAnyDisplayed extends ru.veeam.hamcrest.MpCollectionCondition {


    public MpContainsIsAnyDisplayed() {

    }

    @Override
    public boolean apply(List<WebElement> elements) {

            if (elements.stream().filter(webElement -> webElement.isDisplayed()).collect(Collectors.toList()).size() != 1) {
                return false;
            }
        return true;
    }

    @Override
    public void fail(WebElementsCollection collection, List<WebElement> elements, Exception lastError, long timeoutMs) {
        if (elements == null || elements.isEmpty()) {
            ElementNotFound elementNotFound = new ElementNotFound(collection, new ArrayList<>(), lastError);
            elementNotFound.timeoutMs = timeoutMs;
            throw elementNotFound;
        } else {
            throw new TextsMismatch(collection, ElementsCollection.texts(elements), new ArrayList<>(), timeoutMs);
        }
    }

    @Override
    public String toString() {
        return "отображается эелемент ";
    }
}
