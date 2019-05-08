package ru.veeam.hamcrest.collections;

import com.codeborne.selenide.impl.WebElementsCollection;
import org.openqa.selenium.WebElement;
import ru.veeam.hamcrest.matchers.ListSizeMismatch;

import java.util.List;


public class MpListSize extends ru.veeam.hamcrest.MpCollectionCondition {

    protected final int expectedSize;

    public MpListSize(int expectedSize) {
        this.expectedSize = expectedSize;
    }

    @Override
    public boolean apply(List<WebElement> elements) {
        return elements.size() == expectedSize;
    }

    @Override
    public void fail(WebElementsCollection collection, List<WebElement> elements, Exception lastError, long timeoutMs) {
        throw new ListSizeMismatch("=", expectedSize, collection, elements, lastError, timeoutMs);
    }

    @Override
    public String toString() {
        return String.format("size(%s)", expectedSize);
    }
}
