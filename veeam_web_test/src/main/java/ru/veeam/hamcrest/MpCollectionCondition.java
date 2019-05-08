package ru.veeam.hamcrest;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.impl.WebElementsCollection;
import com.google.common.base.Predicate;
import org.openqa.selenium.WebElement;

import java.util.List;

public abstract class MpCollectionCondition extends CollectionCondition implements Predicate<List<WebElement>> {

    public static ru.veeam.hamcrest.MpCollectionCondition size(int expectedSize) {
        return new ru.veeam.hamcrest.collections.MpListSize(expectedSize);
    }

    public static ru.veeam.hamcrest.MpCollectionCondition containsInAnyOrderExactMatch(String... array) {
        return new ru.veeam.hamcrest.collections.MpContainsIsAnyOrderExactMatch(array);
    }

    public static ru.veeam.hamcrest.MpCollectionCondition containsInAnyOrderExactMatch(List<String> array) {
        return new ru.veeam.hamcrest.collections.MpContainsIsAnyOrderExactMatch(array);
    }

    public static ru.veeam.hamcrest.MpCollectionCondition containsInAnyOrder(String... array) {
        return new ru.veeam.hamcrest.collections.MpContainsIsAnyOrder(array);
    }

    public static ru.veeam.hamcrest.MpCollectionCondition containsInAnyOrder(List<String> array) {
        return new ru.veeam.hamcrest.collections.MpContainsIsAnyOrder(array);
    }

    public static ru.veeam.hamcrest.MpCollectionCondition containsInAnyOrder(String attr, List<String> array) {
        return new ru.veeam.hamcrest.collections.MpContainsIsAnyOrderByAttribute(attr, array);
    }

    public static ru.veeam.hamcrest.MpCollectionCondition containsInAnyOrder(String attr, String... array) {
        return new ru.veeam.hamcrest.collections.MpContainsIsAnyOrderByAttribute(attr, array);
    }

    public static ru.veeam.hamcrest.MpCollectionCondition containsDisplayed() {
        return new ru.veeam.hamcrest.collections.MpContainsIsAnyDisplayed();
    }

    public abstract void fail(WebElementsCollection collection, List<WebElement> elements, Exception lastError, long timeoutMs);
}
