package ru.veeam.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

public class StringEqualIgnoreCaseMatcher extends BaseMatcher<String> {
    private final String expected;

    public StringEqualIgnoreCaseMatcher(String expected) {
        this.expected = expected;
    }

    public boolean matches(Object actualValue) {
        return actualValue != null && ((WebElement) actualValue).getText().equalsIgnoreCase(expected);
    }

    public void describeTo(Description description) {
        description.appendText("текст идентичен ");
        description.appendValue(expected);
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        String message = ((WebElement) element).getText().equalsIgnoreCase(expected) ? "текст идентичен " : "текст не идентичен ";
        description.appendText(message + expected);
        description.appendValue(element);
    }

}
