package ru.veeam.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

public class StringContainsAttributeMatcher extends BaseMatcher<String> {

    private final String expected;
    private final String attribute;

    public StringContainsAttributeMatcher(String attribute, String expected) {
        this.expected = expected;
        this.attribute = attribute;
    }

    public boolean matches(Object actualValue) {
        return actualValue != null && ((WebElement) actualValue).getAttribute(attribute).contains(expected);
    }

    public void describeTo(Description description) {
        description.appendText("содержит текст ");
        description.appendValue(expected);
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        String message = ((WebElement) element).getText().contains(expected) ? "содержит текст " : "не содержит текст ";
        description.appendText(message + expected);
        description.appendValue(element);
    }
}
