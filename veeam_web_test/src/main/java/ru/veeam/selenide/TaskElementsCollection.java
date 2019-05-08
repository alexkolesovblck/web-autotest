package ru.veeam.selenide;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.ex.DialogTextMismatch;
import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.WebElementsCollection;
import com.codeborne.selenide.impl.WebElementsCollectionWrapper;
import com.codeborne.selenide.logevents.SelenideLog;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Configuration.assertionMode;
import static com.codeborne.selenide.Configuration.collectionsTimeout;
import static com.codeborne.selenide.logevents.ErrorsCollector.validateAssertionMode;
import static com.codeborne.selenide.logevents.LogEvent.EventStatus.PASS;

public class TaskElementsCollection extends ElementsCollection {

    private WebElementsCollection collection;
    private List<TaskElement> taskElements;

    public TaskElementsCollection(WebElementsCollection collection) {
        super(collection);
        this.collection = collection;
    }

    public TaskElementsCollection (List<WebElement> collection) {
        super(new WebElementsCollectionWrapper(collection));
        this.collection = new WebElementsCollectionWrapper(collection);
    }

    public Integer getIndexByText(String text) {

        String[] re = collection.getElements()
                .stream()
                .map(e -> e.getText()
                        .trim())
                .toArray(String[]::new);

        Integer index = Arrays.asList(re).indexOf(text);

        if (index == -1) {
            String toString = Arrays.stream(re).collect(Collectors.joining(" | "));
            throw new DialogTextMismatch("Индекс элемента не найден, элемент содержащий '"+ text +"' не найден", toString);
        }
        return ++index;
    }

    public ElementsCollection shouldMatched(CollectionCondition... conditions) {
        return should("have", conditions);
    }
}
