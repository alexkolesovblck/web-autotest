package ru.veeam.selenide;

import com.codeborne.selenide.SelenideElement;
import org.hamcrest.Matcher;
import org.openqa.selenium.By;

public interface TaskElement extends SelenideElement {


    TaskElement shouldMatched(String message, Matcher matcher);

    TaskElement shouldMatched(Matcher matcher);

    TaskElementsCollection findElementCollection(By by);

    SelenideElement setText(String text);

    SelenideElement pressPaste(String text);

    SelenideElement pressBackspace();

    SelenideElement clickAfterLoad();

    SelenideElement selectOptionAfterLoad(String option);

    SelenideElement clickJs();

    SelenideElement blur();

    SelenideElement select(String text);

    SelenideElement clickAfterScroll();

    SelenideElement relativeScroll();
}