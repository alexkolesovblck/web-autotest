package ru.veeam.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

public class IsEmptyMatcher extends BaseMatcher<WebElement> {

    public IsEmptyMatcher() {
    }

    @Override
    public boolean matches(Object element) {
        return WebElement.class.isAssignableFrom(element.getClass()) && ((WebElement) element).getText().isEmpty();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("пустой");
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        String message = ((WebElement) element).getText().isEmpty() ? "пустой " : "не пустой ";
        description.appendText(message);
    }
}
