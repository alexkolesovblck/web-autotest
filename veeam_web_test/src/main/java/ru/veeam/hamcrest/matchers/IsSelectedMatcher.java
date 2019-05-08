package ru.veeam.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

public class IsSelectedMatcher extends BaseMatcher<WebElement> {

    public IsSelectedMatcher() {
    }

    @Override
    public boolean matches(Object element) {
        return WebElement.class.isAssignableFrom(element.getClass()) && ((WebElement) element).isSelected();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("выбран");
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        String message = ((WebElement) element).isSelected() ? "выбран" : "не выбран";
        description.appendText(message);
    }
}
