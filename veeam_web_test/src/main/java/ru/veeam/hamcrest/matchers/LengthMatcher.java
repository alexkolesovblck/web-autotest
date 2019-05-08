package ru.veeam.hamcrest.matchers;

import com.codeborne.selenide.SelenideElement;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openqa.selenium.WebElement;

public class LengthMatcher extends BaseMatcher<WebElement> {

    private final int expected;

    public LengthMatcher(int expected) {
        this.expected = expected;
    }

    public boolean matches(Object actualValue) {
        return actualValue != null && ((SelenideElement) actualValue).val().replaceAll(" ", "").length() == expected;
    }

    public void describeTo(Description description) {
        description.appendText("имеет длину ");
        description.appendValue(expected);
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        String message = ((SelenideElement) element).val().replaceAll(" ", "").length() == expected ? "имеет длину " : "не имеет длину ";
        int length = ((SelenideElement) element).val().length();
        description.appendText(message + length);
    }
}
