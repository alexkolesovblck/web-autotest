package ru.veeam.form;

import org.openqa.selenium.By;
import ru.veeam.selenide.ExBy;

public class PageForm {

    /**
     * Choose country
     */
    public By countryFieldSelector = ExBy.id("country-element", "Country field selector");

    public By countrySelect(String country){
        return ExBy.xpath(".//span[@class='selecter-item' and contains(text(), '" + country + "')]", "Choose country " + country + " from selector");
    }


    /**
     * Choose languages
     */
    public By languagesFieldSelector = ExBy.id("language", "Language field selector");

    public By languagesSelect(String languages){
        return ExBy.xpath(".//text()[contains(.,'" + languages + "')]/../span", "Чекбокс с выбором языка: " + languages);
    }

    public By submitChangesBtn = ExBy.cssSelector("a[class='selecter-fieldset-submit']", "Button 'Submit' for apply changes");


    /**
     * Result
     */
    public By countVacancysPrint = ExBy.xpath(".//h3[contains(text(),'job found')]", "Show result after search");
}
