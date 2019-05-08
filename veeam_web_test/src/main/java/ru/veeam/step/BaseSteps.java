package ru.veeam.step;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import ru.veeam.page.BasePage;

import static ru.veeam.hamcrest.Matchers.text;
import static ru.veeam.selenide.TaskSelenide.$;

public class BaseSteps {

    @Step("Open veeam carier")
    public void openStartPage() {
        Selenide.open("");
    }

    @Step("Choose country")
    public void selectCountry(String country) {
        $(new BasePage().onPageForm().countryFieldSelector).clickAfterLoad();
        $(new BasePage().onPageForm().countrySelect(country)).clickAfterLoad();
    }

    @Step("Choose languages")
    public void selectLanguages(String languages) {
        $(new BasePage().onPageForm().languagesFieldSelector).clickAfterLoad();
        $(new BasePage().onPageForm().languagesSelect(languages)).click();
        $(new BasePage().onPageForm().submitChangesBtn).clickAfterLoad();
    }

    @Step("Check result search filter")
    public void checkResult(String countVacancies) {
        $(new BasePage().onPageForm().countVacancysPrint).shouldMatched(text(countVacancies + " job found"));
    }
}
