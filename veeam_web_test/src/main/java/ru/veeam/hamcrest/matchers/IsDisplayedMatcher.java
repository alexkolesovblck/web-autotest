package ru.veeam.hamcrest.matchers;

import com.codeborne.selenide.WebDriverRunner;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import ru.veeam.selenide.TaskElement;

public class IsDisplayedMatcher extends BaseMatcher<WebElement> {

    public IsDisplayedMatcher() {
    }

    @Override
    public boolean matches(Object element) {
        if(!((TaskElement) element).isDisplayed()) {
            ((TaskElement) element).scrollTo();
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("window.scrollBy(0, -50);");
        }
        return TaskElement.class.isAssignableFrom(element.getClass()) && ((TaskElement) element).isDisplayed();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("отображается");
    }

    @Override
    public void describeMismatch(Object element, Description description) {
        String message = ((WebElement) element).isDisplayed() ? "отображается" : "не отображается";
        description.appendText(message);
    }

}
