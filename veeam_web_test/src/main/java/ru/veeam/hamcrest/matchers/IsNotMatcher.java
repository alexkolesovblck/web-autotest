package ru.veeam.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class IsNotMatcher<T> extends BaseMatcher<Matcher> {

    private final Matcher<T> matcher;

    public IsNotMatcher(Matcher<T> matcher) {
        this.matcher = matcher;
    }

    public boolean matches(Object arg) {
        return !this.matcher.matches(arg);
    }

    public void describeTo(Description description) {
        description.appendText("не ").appendDescriptionOf(this.matcher);
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        matcher.describeMismatch(element, description);
    }

}
