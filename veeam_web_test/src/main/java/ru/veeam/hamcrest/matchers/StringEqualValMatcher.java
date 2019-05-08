package ru.veeam.hamcrest.matchers;

import com.codeborne.selenide.SelenideElement;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

public class StringEqualValMatcher extends BaseMatcher<WebElement> {
    private final String expected;

    public StringEqualValMatcher(String expected) {
        this.expected = expected;
    }

    public boolean matches(Object actualValue) {
        return actualValue != null && ((SelenideElement) actualValue).val().equals(expected);
    }

    public void describeTo(Description description) {
        description.appendText("текст идентичен ");
        description.appendValue(expected);
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        String message = ((WebElement) element).getText().equals(expected) ? "текст идентичен " : "текст не идентичен ";
        description.appendText(message + expected);
        description.appendValue(element);
    }
}
