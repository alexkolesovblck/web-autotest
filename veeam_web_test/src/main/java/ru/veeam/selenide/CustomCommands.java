package ru.veeam.selenide;

import com.codeborne.selenide.*;
import com.codeborne.selenide.ex.DialogTextMismatch;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CustomCommands {

    private CustomCommands() {
    }

    @SuppressWarnings("unchecked")
    static Command shouldMatched() {
        return (proxy, locator, args) -> {
            String message = null;
            Matcher<WebElement> matcher = null;

            if (args[0] instanceof String) {
                message = (String) args[0];
                matcher = (Matcher<WebElement>) args[1];
            }

            if (args[0] instanceof Matcher) {
                message = "";
                matcher = (Matcher<WebElement>) args[0];
            }

            if (!matcher.matches(proxy)) {
                StringDescription description = new StringDescription();
                description.appendText(message);

                description.appendText("\nОжидалось: ")
                        .appendText("элемент ").appendValue(locator.getSearchCriteria()).appendText(" ")
                        .appendDescriptionOf(matcher);

                description.appendText("\n       но: ");
                matcher.describeMismatch(proxy, description);

                throw new AssertionError(description.toString());
            }
            return proxy;
        };
    }

    static Command setText() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            ((TaskElement)element).scrollTo();
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("window.scrollBy(0, -50);");
            String text = (String) args[0];
            int i = 0;
            do {
                element.setValue(text);
                Selenide.sleep(Configuration.pollingInterval);
                i++;
            }
            while (!element.getValue().equalsIgnoreCase(text) && i < 5);

            return proxy;
        };
    }

    static Command pressPaste() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;

            String text = (String) args[0];
            int i = 0;
            do {
                element.clear();
                element.sendKeys(Keys.CONTROL + "v");
                Selenide.sleep(Configuration.pollingInterval);
                i++;
            }
            while (!element.getValue().equalsIgnoreCase(text) && i < 5);

            return proxy;
        };
    }

    static Command pressBackspace() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            element.sendKeys(Keys.BACK_SPACE);
            Selenide.sleep(Configuration.pollingInterval);
            return proxy;
        };
    }

    static Command clickAfterLoad() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            Selenide.sleep(Configuration.pollingInterval);
            element.should(Condition.exist);
            element.should(Condition.visible);
            element.should(Condition.enabled);
            element.click();
            Selenide.sleep(Configuration.pollingInterval);
            return proxy;
        };
    }

    static Command selectOptionAfterLoad() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            element.should(Condition.exist);
            element.should(Condition.visible);
            element.should(Condition.enabled);
            element.selectOption((String) args[0]);
            Selenide.sleep(Configuration.pollingInterval);
            return proxy;
        };
    }

    static Command clickJs() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("arguments[0].click();", element);
            Selenide.sleep(Configuration.pollingInterval);
            return proxy;
        };
    }

    static Command findElementCollection() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            List<WebElement> webElements = element.findElements((By)args[0]);
            TaskElementsCollection taskElementsCollection = new TaskElementsCollection(webElements);

            return taskElementsCollection;
        };
    }

    //в селект передавать селектор элемента span, тк как сам select скрыт. //select[@id='typeRolesFilter']//preceding-sibling::span/span
    static Command select() {
        return (proxy, locator, args) -> {

            String itemToBeSelected = (String) args[0];
            TaskElement selectArea = (TaskElement) proxy;
            WebElement selectUL = selectArea.findElement(ExBy.xpath("./following-sibling::ul","Локатор элемента Select (selectUL)"));
            List<WebElement> selectLI = selectUL.findElements(ExBy.xpath("./li","Локатор элемента Select (selectLI)"));

            selectArea.clickAfterScroll();

            String[] resultList = selectLI.stream().map(i -> i.getText()).toArray(String[]::new);

            Integer index = Arrays.asList(resultList).indexOf(itemToBeSelected);

            if(index == -1) {
                String toString = Arrays.stream(resultList).collect(Collectors.joining(" | "));
                throw new DialogTextMismatch("Индекс элемента не найден, элемент содержащий '"+ itemToBeSelected +"' не найден", toString);
            }
            selectLI.get(index).click();

            Selenide.sleep(Configuration.pollingInterval);
            return proxy;
        };
    }

    static Command clickAfterScroll() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            element.scrollTo();
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("window.scrollBy(0, -50);");
            element.click();
            return proxy;
        };
    }

    /**
     * Скрол относительно контейнера с возможностью скорола. Ищется первый попавшийся блочный элемент с возможностью скрола и от него выполняется скрол
     * (В некоторых случаях проскролить стандартными средствами не выйдет, встроенные методы скорол работают относительно всего окна браузера
     *  если есть форма со скролом и надо доскролить до элемента этой формы то поможет этот метод.
     *  !update 17.11.17! Если не находится элемент с возможность скрола,- листаем относительно окна браузера.
     */
    static Command relativeScroll() {
        return (proxy, locator, args) -> {
            TaskElement element = (TaskElement) proxy;
            String js = "var childEl = arguments[0], " +
                    "        elWithScroll = arguments[0], " +
                    "        depthOfSearch = 30, " +
                    "        windowFlag = false; " +
                    "    while(depthOfSearch > 0 && getComputedStyle(elWithScroll).overflow != 'auto') " +
                    "    { " +
                    "      if(elWithScroll.parentElement == null) { windowFlag = true; break;} " +
                    "      elWithScroll = elWithScroll.parentElement; " +
                    "      depthOfSearch--; " +
                    "    }; " +

                    "    var childHeight = childEl.getBoundingClientRect().height, "  +
                    "        childTopX = childEl.getBoundingClientRect().top; " +
                    "    if(!windowFlag) {" +
                    "          var parentTopX = elWithScroll.getBoundingClientRect().top;       " +
                    "          elWithScroll.scrollBy(0, parseInt(childTopX) - parseInt(parentTopX) - (childHeight * 2)); " +
                    "     } else {" +
                    "          window.scrollBy(0, parseInt(childTopX) - 100); " +
                    "     }";
            ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript(js, element);
            Selenide.sleep(Configuration.pollingInterval);
            return proxy;
        };
    }

}
