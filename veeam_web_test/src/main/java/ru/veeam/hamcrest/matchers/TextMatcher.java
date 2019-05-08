package ru.veeam.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

public class TextMatcher extends BaseMatcher<WebElement> {

    private final Matcher<String> textMatcher;

    public TextMatcher(Matcher<String> textMatcher) {
        this.textMatcher = textMatcher;
    }

    @Override
    public boolean matches(Object element) {
        return WebElement.class.isAssignableFrom(element.getClass()) &&
                textMatcher.matches(((WebElement) element).getText());
    }

    @Override
    public void describeTo(Description description) {
        textMatcher.describeTo(description.appendText("текст "));
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        String actualText = ((WebElement) element).getText();
        description.appendText("текст ").appendValue(actualText);
    }
}
