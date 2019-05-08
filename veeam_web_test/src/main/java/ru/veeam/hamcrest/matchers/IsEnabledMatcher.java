package ru.veeam.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

public class IsEnabledMatcher extends BaseMatcher<WebElement> {

    public IsEnabledMatcher() {
    }

    @Override
    public boolean matches(Object element) {
        return WebElement.class.isAssignableFrom(element.getClass()) && ((WebElement) element).isEnabled();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("активен");
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        String message = ((WebElement) element).isEnabled() ? "активен" : "не активен";
        description.appendText(message);
    }
}