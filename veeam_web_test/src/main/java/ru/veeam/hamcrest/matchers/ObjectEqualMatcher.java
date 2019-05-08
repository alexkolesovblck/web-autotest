package ru.veeam.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ObjectEqualMatcher extends BaseMatcher<Object> {

    private final Object expected;

    public ObjectEqualMatcher(Object expected) {
        this.expected = expected;
    }

    public boolean matches(Object actualValue) {
        return  actualValue.equals(expected);
    }

    public void describeTo(Description description) {
        description.appendText("Объект идентичен ");
        description.appendValue(expected);
    }

    @Override
    public void describeMismatch(Object obj, Description description) {
        String message = obj.equals(expected) ? "Объект идентичен " : "Объект не идентичен ";
        description.appendText(message + expected);
        description.appendValue(obj);
    }

}
