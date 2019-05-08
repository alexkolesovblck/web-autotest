package ru.veeam.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.util.regex.Pattern;

public class RegExpMatcher extends BaseMatcher<String> {
    private final String regexpPattern;

    public RegExpMatcher(String regexpPattern) {
        this.regexpPattern = regexpPattern;
    }

    public boolean matches(Object actualValue) {
        return actualValue != null && Pattern.matches(regexpPattern, (String) actualValue);
    }

    public void describeTo(Description description) {
        description.appendText("соответствует шаблону ");
        description.appendValue(regexpPattern);
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        String message = matches(item) ? " соответствует шаблону " : " не соответствует шаблону ";
        description.appendValue("строка " + item);
        description.appendText(message + regexpPattern);
    }
}
